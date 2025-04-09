package com.example.springboot.service.strategy;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.Song;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * @author mwj
 */
@Data
@Service
public class OrderedStrategy implements PlayStrategy{

    @Override
    public Song nextSong(Playlist playlist) {
        int index = playlist.getCurrentIndex();
        if (index < playlist.getSongs().size() - 1) {
            playlist.setCurrentIndex(index + 1);
            return playlist.getSongs().get(index + 1);
        } else {
            return null;
        }
    }
}
