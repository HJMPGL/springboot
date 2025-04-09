package com.example.springboot.controller;

import com.example.springboot.entity.Song;
import com.example.springboot.service.LrcParser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author mwj
 */
@Controller
@Data
public class LyricsRenderer {
    @Autowired
    private LrcParser lrcParser;

    private volatile boolean running = false;
    /**
     * 时间监视线程
     */
    private Thread timeMonitorThread;
    /**
     * 歌词渲染线程
     */
    private Thread renderThread;

    /**
     * 启动时 时间戳
     */
    private Long playBackStartTime;
    /**
     * 共享队列
     */
    private BlockingQueue<String> lyricQueue = new LinkedBlockingQueue<>();

    @SuppressWarnings("AlibabaAvoidManuallyCreateThread")
    public void start (Song song){
        stop();
        lyricQueue.clear();
        Map<Long,String> lrc = lrcParser.parse(song.getLrcPath());
        /**
         * 将歌词时间点排序并转换为队列
         */
        PriorityQueue<Long> timePoints = new PriorityQueue<>(lrc.keySet());

        timeMonitorThread = new Thread(()->{
            running = true;
            /**
             * 线程开始
             */
            playBackStartTime = System.currentTimeMillis();
            while (running && !timePoints.isEmpty()) {
                /**
                 * 下一个歌词时间点
                 */
                Long nextTime = timePoints.peek();

                /**
                 * start后到现在开始的时间
                 */
                Long elapsedTime = System.currentTimeMillis() - playBackStartTime;


                if (elapsedTime >= nextTime) {
                    try {
                        /**
                         * 将应该渲染的歌词放到共享栈
                         */
                        lyricQueue.put(lrc.get(nextTime));
                        timePoints.poll();
                    } catch (InterruptedException e) {
                        System.out.println("111");
                        throw new RuntimeException(e);
                    }
                } else {
                    /**
                     * 动态计算休眠时间（剩余时间的一半，最小1ms）
                     */
                    Long sleepTime = Math.max(1,(nextTime-elapsedTime)/2);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });

        renderThread = new Thread(()->{
            try {
                while (running||!lyricQueue.isEmpty()) {
                    /**
                     * 渲染共享栈中的歌词
                     */
                    renderLine(lyricQueue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        timeMonitorThread.start();
        renderThread.start();
    }

    public void stop(){
        running = false;
        if(timeMonitorThread!=null) {
            timeMonitorThread.interrupt();
        }
        if (renderThread!=null) {
            renderThread.interrupt();
        }
        lyricQueue.clear();
    }

    public void renderLine(String line){
        System.out.println(line);
    }
}
