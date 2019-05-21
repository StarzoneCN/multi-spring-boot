package com.example.demo.multi.springBoot.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @description:
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2019-05-20 15:03:11
 * @modefied:
 **/

public class User {
    private int id;
    private String name;
    private Boolean male;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }
}
