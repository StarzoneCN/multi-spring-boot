package com.example.demo.multi.springBoot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/28 13:51:37
 */
@Component
public class C {
    private A a;

    public C(A a){
        this.a = a;
    }
}
