package com.example.springboot.service.strategy;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.Song;

/**
 * @author mwj
 */
public interface PlayStrategy  {
    /**
     * 下一首歌
     * @param playlist
     * @return
     */
    Song nextSong(Playlist playlist);
}
