package com.example.springboot.repository;

import com.example.springboot.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author mwj
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist,String> {

    @Query("select p from Playlist p left join fetch p.songs where p.id = :id")
    Optional<Playlist> findByIdWithSongs(@Param("id") String id);
}
