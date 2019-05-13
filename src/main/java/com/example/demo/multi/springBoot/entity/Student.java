package com.example.demo.multi.springBoot.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Student")
@Data
public class Student {

    public enum Gender {
        MALE, FEMALE
    }

    @Id
    private String id;
    private String name;
    private Gender gender;
    private int grade;
    private RedisConnectionFactory redisConnectionFactory;
}