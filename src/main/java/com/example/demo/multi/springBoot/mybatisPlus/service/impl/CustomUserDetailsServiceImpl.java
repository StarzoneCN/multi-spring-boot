package com.example.demo.multi.springBoot.mybatisPlus.service.impl;

import com.example.demo.multi.springBoot.mybatisPlus.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * security而建立
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/11/21 22:47
 * @modefied:
 */
@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null; // todo
    }
}
