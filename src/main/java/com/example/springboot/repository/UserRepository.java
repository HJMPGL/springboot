package com.example.springboot.repository;

import com.example.springboot.entity.Playlist;
import com.example.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author mwj
 */
@Repository
public interface UserRepository extends JpaRepository<User,String> {

    @Query(value = """
                    select 
                        p.*
                    from 
                        Playlist p
                        inner join user_share_urls usu on p.playlist_id = usu.share_urls_playlist_id
                    where 
                        usu.share_urls_key = :url
                    """
            ,nativeQuery = true)
    Playlist findBySharedUrl(@PathVariable("url") String url);

    @Query(value = """
                select up.playlists_playlist_id
                from
                    user_playlists up
                where
                    up.user_user_id = :id
                """
    ,nativeQuery = true)
    String findPlaylistIdByUserId(@PathVariable("id") String id);

    @Override
    @Query(value = """
                select u.*
                from 
                    User u
                where 
                    u.user_id = :id
                """
    ,nativeQuery = true)
    User getById(@PathVariable("id") String id);

    @Query(value = """
                select usu.share_urls_key
                from user_share_urls usu
                where usu.user_user_id = :id
            """
    ,nativeQuery = true)
    String getUuidByUserId(@PathVariable("id") String id);

}

