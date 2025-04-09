package com.example.springboot.service.strategy;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.Song;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author mwj
 */
@Data
@Service
public class RandomStrategy implements PlayStrategy{

    @Override
    public Song nextSong(Playlist playlist) {
        List <Song> songs = playlist.getSongs();
        return songs.get(new Random().nextInt(songs.size()));
    }
}
