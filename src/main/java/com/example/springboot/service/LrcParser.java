package com.example.springboot.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mwj
 */
@Service
@Data
public class LrcParser {

    public static String path = "src/main/java/com/example/springboot/material/";

    private Long timeParse(String s){
        String minutes,seconds,hundredths;
        minutes = s.substring(1,3);
        seconds = s.substring(4,6);
        hundredths = s.substring(7,9);
        Long M = Long.parseLong(minutes);
        Long S = Long.parseLong(seconds);
        Long H = Long.parseLong(hundredths);
        Long result = M*60*1000+S*1000+H*10;
        return result;
    }
    private String lrcParse(String s){
        return s.substring(10);
    }

    /**
     * lrc解析器
     * @param lrcPath
     * @return Map<Long,String>
     */
    public Map<Long,String> parse(String lrcPath){
        lrcPath = path+lrcPath+".lrc";
        Map<Long,String> parseMap = new HashMap<>();
        String sb;
        String lrc;
        Long time;
        try(BufferedReader br = new BufferedReader(new FileReader(lrcPath))){
            while ((sb = br.readLine())!=null){
                time = timeParse(sb);
                lrc = lrcParse(sb);
                parseMap.put(time,lrc);
            }
        }catch (IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }
        return parseMap;
    }

}
