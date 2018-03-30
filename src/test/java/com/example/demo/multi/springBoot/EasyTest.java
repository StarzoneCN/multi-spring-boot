package com.example.demo.multi.springBoot;

import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/30 10:52
 * @modefied:
 */
public class EasyTest {

    @Test
    public void test() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "hongxing");
        System.out.println(JSONUtil.parseFromMap(map).toStringPretty());
    }
}
