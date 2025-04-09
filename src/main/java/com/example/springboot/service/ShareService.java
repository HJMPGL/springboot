package com.example.springboot.service;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.User;
import com.example.springboot.repository.PlaylistRepository;
import com.example.springboot.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author mwj
 */
@Data
@Service
public class ShareService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaylistRepository playlistRepository;

//    @Transactional//自动管理事务
    public String sharePlaylist(User user, Playlist playlist){
        /**
         * 生成唯一url
         */
        String url = UUID.randomUUID().toString();
        /**
         * 更新user
         */
        user.sharePlaylist(url,playlist);
        /**
         * 更新（插入）数据库
         */
//        userRepository.save(user);
        userRepository.saveAndFlush(user);

        return url;
    }
//    @Transactional//自动管理事务
    public Playlist getPlaylistByUrl(String url){
        return userRepository.findBySharedUrl(url);
    }
}
