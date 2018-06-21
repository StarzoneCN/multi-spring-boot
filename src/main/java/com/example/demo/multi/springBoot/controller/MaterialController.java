package com.example.demo.multi.springBoot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/14 15:13
 * @modefied:
 */
@RestController
@RequestMapping("md")
public class MaterialController {

    @RequestMapping("cbl")
    public List<Map<String, Object>> getComboBoxList(){
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i=1; i<11; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", i);
            map.put("text", "index-" + i);
            list.add(map);
        }
        return list;
    }
}
