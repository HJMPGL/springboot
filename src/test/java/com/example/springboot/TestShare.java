package com.example.springboot;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.User;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.ShareService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TestShare {
    @Autowired
    private ShareService shareService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Commit
    @Transactional
    public void test(){
        User user = userRepository.getById("304f65c7-9bf0-4137-a9f3-dd5bdc33edab");
        System.out.println(user);
        Playlist playlist = user.getPlaylists().get(0);
        System.out.println(playlist);
        System.out.println(shareService.sharePlaylist(user, playlist));
    }
}
