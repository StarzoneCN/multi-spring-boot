package com.example.demo.multi.springBoot;

import cn.hutool.core.collection.CollectionUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/16 0:56
 * @modefied:
 */
public class EasyTest {

    @Test
    public void testStr(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println(CollectionUtil.join(list, "-"));
    }
}
