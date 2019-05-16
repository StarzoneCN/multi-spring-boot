package com.example.demo.multi.springBoot.dto;

import lombok.Data;

@Data
public class ResetPassDto {
    private String mobileNO;
    private String verifyCode;
    private String newPassword;
}
