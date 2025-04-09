package com.example.springboot.repository;

import com.example.springboot.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author mwj
 */
@Repository
public interface SongRepository extends JpaRepository<Song, String> {

}
