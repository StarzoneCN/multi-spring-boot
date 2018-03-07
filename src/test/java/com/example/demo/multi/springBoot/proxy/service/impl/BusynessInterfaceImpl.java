package com.example.demo.multi.springBoot.proxy.service.impl;

import com.example.demo.multi.springBoot.proxy.service.BusynessInterface;

/**
 * @author: LiHongxing
 * @date: Create in 2018-03-07 14:01
 * @modefied:
 */
public class BusynessInterfaceImpl implements BusynessInterface {

    @Override
    public int benefits() {
        System.out.println("benefits of BusynessInterfaceImpl ... ");
        return 100 * 10000;
    }
}
