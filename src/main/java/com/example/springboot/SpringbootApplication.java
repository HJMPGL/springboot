package com.example.springboot;

import com.example.springboot.controller.PlayBackController;
import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.User;
import com.example.springboot.repository.PlaylistRepository;
import com.example.springboot.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * @author mwj
 */
@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }


    @Bean
    public ApplicationRunner initialize(
            PlayBackController playBackController,
            PlaylistRepository playlistRepository,
            UserRepository userRepository
    )
    {
        return args -> {
            User user = userRepository.findById("304f65c7-9bf0-4137-a9f3-dd5bdc33edab").orElseThrow(()->new RuntimeException("用户不存在"));
            System.out.println("你好! " + user.getName());
            String playlistId = userRepository.findPlaylistIdByUserId(user.getId());
            Playlist playlist = playlistRepository.findByIdWithSongs(playlistId).orElseThrow(()->new RuntimeException("播放列表不存在"));
            playBackController.play(playlist);
        };
    }
}

