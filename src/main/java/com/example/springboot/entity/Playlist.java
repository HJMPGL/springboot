package com.example.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import static com.example.springboot.entity.Playlist.PlayMode.ORDER;
import static com.example.springboot.entity.Playlist.PlayMode.SHUFFLE;

/**
 * @author mwj
 */
@Entity
@Data
@Table(name = "playlist")
public class Playlist {
    @Column(name = "playlist_id")
    @Id
    private String id;
    @Column(name = "playlist_name")
    private String name;
    @Column(name = "current_index")
    private Integer currentIndex;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Song> songs = new ArrayList<>();
    @Column(name = "play_mode")
    private PlayMode playMode;
    public enum PlayMode{
        /*
            顺序播放
         */
        ORDER,
        /*
            随机播放
         */
        SHUFFLE
    }
    @Override
    public String toString(){
        String mode = playMode == ORDER?"顺序播放":"随机播放";
        String songsName = "";
        for(int i = 0;i<getSongs().size();i++){
            songsName+=(getSongs().get(i)+"\n");
        }
        return name+ " | "+mode+"\n"+"歌单列表:\n"+songsName;
    }
    public void switchPlayMode(){
        if(playMode == ORDER){
            playMode = SHUFFLE;
        }
        else {
            playMode = ORDER;
        }
    }
}
