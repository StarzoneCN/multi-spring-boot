package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.entity.Member;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 测试member
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/30 14:31
 * @modefied:
 */
@RestController
@RequestMapping("member")
public class MemberController {

    @RequestMapping("valid")
    public String valid(@Valid Member member) {
        return "验证通过";
    }
}
