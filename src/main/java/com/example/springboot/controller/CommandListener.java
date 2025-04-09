package com.example.springboot.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

/**
 * @author mwj
 */
@Controller
@Data
public class CommandListener implements ApplicationRunner {
    @Autowired
    private PlayBackController playBackController;

    private Thread listenerThread;

    @Override
    public void run(ApplicationArguments args) {
        listenerThread = new Thread(()->{
            while (true){
                Scanner scanner = new Scanner(System.in);
                String s = scanner.nextLine().trim();
                playBackController.handleCommand(s);
            }
        });
        listenerThread.start();
    }
}
