package com.example.springboot.controller;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.User;
import com.example.springboot.repository.PlaylistRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mwj
 */
@RestController
@RequestMapping("/api")
public class PlayerApiController {

    @Autowired
    private PlayBackController playBackController;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;
    @Autowired
    private ShareService shareService;

    @GetMapping("/play")
    public ResponseEntity<String> play() {
        User user = userRepository.findById("304f65c7-9bf0-4137-a9f3-dd5bdc33edab").orElseThrow(()->new RuntimeException("用户不存在"));
        System.out.println("你好! " + user.getName());
        String playlistId = userRepository.findPlaylistIdByUserId(user.getId());
        Playlist playlist = playlistRepository.findByIdWithSongs(playlistId).orElseThrow(()->new RuntimeException("播放列表不存在"));
        playBackController.play(playlist);
        return ResponseEntity.ok("播放中");
    }
    @GetMapping("/next")
    public ResponseEntity<String> next() {
        playBackController.handleCommand("NEXT");
        return ResponseEntity.ok("已切歌");
    }

    @GetMapping("/mode")
    public ResponseEntity<String> mode(){
        playBackController.handleCommand("MODE");
        return ResponseEntity.ok("已切换为："+playBackController.getPlaylist().getPlayMode());
    }

}