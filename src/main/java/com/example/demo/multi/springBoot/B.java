package com.example.demo.multi.springBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/28 13:51:26
 */
@Component
public class B {
    private C c;

    public B(C c){
        this.c = c;
    }
}
