package com.example.demo.multi.springBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/28 13:51:15
 */
@Component
public class A {
    private B b;

    public A(B b){
        this.b = b;
    }
}
