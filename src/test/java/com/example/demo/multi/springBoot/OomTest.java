package com.example.demo.multi.springBoot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/8 14:16
 * @modefied:
 */
public class OomTest {

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        for (int i=0; i<100; i++){
            addPerson(personList, 1000_000);
        }
    }

    private static void addPerson(List<Person> personList, int count){
        Random random = new Random();
        while (count-- > 0){
            Person p = new Person();
            p.setName(new Random().toString());
            p.setMoney(random.nextInt());
            personList.add(p);
        }
    }

    private static class Person{
        private String name;
        private int money;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }
    }
}

