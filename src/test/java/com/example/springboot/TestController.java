package com.example.springboot;

import com.example.springboot.controller.PlayBackController;
import com.example.springboot.entity.Playlist;
import com.example.springboot.repository.PlaylistRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TestController {
    @Autowired
    private PlayBackController playBackController;

    @Autowired
    private PlaylistRepository playlistRepository;
    @Test
    @Transactional
    public void Test() throws InterruptedException {
        Playlist playlist;

        playlist = playlistRepository.getById("69ddcab8-dee9-46be-b70c-7ef139d1d44c");

        playBackController.play(playlist);

        Thread.sleep(300000);
    }
}
