package com.example.demo.multi.springBoot.dto;

import lombok.Data;

@Data
public class LoginDto {
    /*账号*/
    private String account;
    /*密码*/
    private String password;
    /*验证码*/
    private String rand;
}
