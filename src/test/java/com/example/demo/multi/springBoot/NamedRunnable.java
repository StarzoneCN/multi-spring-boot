package com.example.demo.multi.springBoot;

import java.time.LocalDateTime;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-06-19 20:19:40
 * @modefied:
 */
public class NamedRunnable implements Runnable {


    private String name;

    public NamedRunnable(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(LocalDateTime.now().getSecond() + "s：Execute Task (name = " + this.name + ")");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
