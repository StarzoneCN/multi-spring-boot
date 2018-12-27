package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * profile测试控制器
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/4 11:31
 * @modefied:
 */
@RestController
@RequestMapping("profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("current")
    public String currentProfile(){
        return profileService.printSimpleName();
    }
}
