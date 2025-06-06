package com.example.springboot.controller;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.Song;
import com.example.springboot.entity.User;
import com.example.springboot.repository.PlaylistRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.ShareService;
import com.example.springboot.service.strategy.OrderedStrategy;
import com.example.springboot.service.strategy.PlayStrategy;
import com.example.springboot.service.strategy.RandomStrategy;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author mwj
 */
@Controller
@Data
public class PlayBackController {
    private PlayStrategy currentStrategy;
    private Song currentSong;
    private volatile boolean running = false;
    private Thread playBackThread;
    private Playlist playlist;
    private User user;

    @Autowired
    private LyricsRenderer lyricsRenderer;
    @Autowired
    private OrderedStrategy orderedStrategy;
    @Autowired
    private RandomStrategy randomStrategy;
    @Autowired
    private ShareService shareService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

    public void play(Playlist playlist){
        // 终止旧线程（如果存在）
        if (playBackThread != null && playBackThread.isAlive()) {
            playBackThread.interrupt();
        }
        /**
         * 播放要先有一个策略
         * 找到当前的歌
         * 开始
         */
        /**
         * 初始化
         */
        synchronized (this) {
//            this.playlist = playlist;
            running = true;
            if (playlist.getPlayMode() == Playlist.PlayMode.ORDER) {
                currentStrategy = orderedStrategy;
            } else {
                currentStrategy = randomStrategy;
            }
            currentSong = currentStrategy.nextSong(playlist);
        }

        /**
         * 开始线程
         */
        playBackThread = new Thread(()->{
            while (running && currentSong != null) {
                lyricsRenderer.start(currentSong);
                try {
                    Thread.sleep(currentSong.getDuration() *1000L);
                } catch (InterruptedException e) {
                    break;
                }
                synchronized (this) {
                    currentSong = currentStrategy.nextSong(playlist);
                }
            }
            synchronized (this) {
                running = false;
            }
        });
        playBackThread.start();
    }
    private void switchMode(Playlist playlist){
        synchronized (this) {
            playlist.switchPlayMode();
            if (playlist.getPlayMode() == Playlist.PlayMode.ORDER) {
                currentStrategy = orderedStrategy;
            } else {
                currentStrategy = randomStrategy;
            }
        }
    }

    private void init(){
        if(user == null){
            user = userRepository.findById("304f65c7-9bf0-4137-a9f3-dd5bdc33edab").orElseThrow(()->new RuntimeException("用户不存在"));
        }
        if(playlist == null){
            String playlistId = userRepository.findPlaylistIdByUserId(user.getId());
            playlist = playlistRepository.findByIdWithSongs(playlistId).orElseThrow(()->new RuntimeException("播放列表不存在"));
        }
    }

    private boolean isUUID(String input) {
        String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
        return input.matches(UUID_REGEX);
    }
    public void handleCommand(String command){
        synchronized (this) {
            if ("n".equals(command)) {
                init();
                lyricsRenderer.stop();
                currentSong = currentStrategy.nextSong(playlist);
                if (currentSong == null) {
                    running = false;
                    return;
                }
                lyricsRenderer.start(currentSong);
            } else if ("m".equals(command)) {
                init();
                switchMode(playlist);
                String mode = playlist.getPlayMode() == Playlist.PlayMode.ORDER?"顺序播放":"随机播放";
                System.out.println("当前播放模式为："+mode);
            } else if ("e".equals(command)) {
                lyricsRenderer.stop();
                running = false;
                if (playBackThread != null) {
                    playBackThread.interrupt();
                }
            } else if("s".equals(command)) {
                init();
                System.out.println(shareService.sharePlaylist(user, playlist));
            } else if("p".equals(command)){
                init();
                play(playlist);
            } else if(isUUID(command)){
                init();
                Playlist playlist2 = shareService.getPlaylistByUrl(command);
                System.out.println(playlist2.toString());
            }
        }
    }
}
