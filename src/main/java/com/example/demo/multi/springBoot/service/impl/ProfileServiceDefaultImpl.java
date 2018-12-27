package com.example.demo.multi.springBoot.service.impl;

import com.example.demo.multi.springBoot.service.ProfileService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * dev环境实现
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/5/4 11:28
 * @modefied:
 */
@Service
public class ProfileServiceDefaultImpl implements ProfileService {

    @Override
    public String printSimpleName() {
        return this.getClass().getSimpleName();
    }
}
