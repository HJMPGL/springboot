package com.example.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 存储歌曲基础信息及歌词文件路径
 */

/**
 * @author mwj
 */
@Entity
@Data
public class Song {
    /**
     * 唯一标识符（UUID）
     */
    @Column(name = "song_id")
    @Id
    private String id;
    /**
     * 歌曲名称（非空）
     */
    @Column(name = "song_title")
    private String title;
    /**
     * 时长（单位：秒，最小值1）
     */
    @Column(name = "song_duration")
    private Integer duration;
    /**
        LRC歌词文件绝对路径
     */
    @Column(name = "song_lrc_path")
    private String lrcPath;

    @Override
    public String toString(){
        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        long remainingSeconds = duration - TimeUnit.MINUTES.toSeconds(minutes);
        String result = minutes + "分" + remainingSeconds + "秒";
        return title+" - "+result;
    }
}
