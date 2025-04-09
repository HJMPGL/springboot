package com.example.springboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mwj
 */
@Entity
@Data
@Table(name = "user")
public class User {
    @Column(name = "user_id")
    @Id
    private String id;
    @Column(name = "user_name")
    private String name;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Playlist>playlists = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_share_urls",
            joinColumns = @JoinColumn(name = "user_user_id"),
            inverseJoinColumns = @JoinColumn(name = "share_urls_playlist_id")
    )
    @MapKeyColumn(name = "share_urls_key")
    private Map<String,Playlist> shareUrls = new HashMap<>();

    @Override
    public String toString(){
        return "用户："+name;
    }
//    @Transactional
    public void sharePlaylist(String url,Playlist playlist){
        shareUrls.put(url,playlist);
    }
}
