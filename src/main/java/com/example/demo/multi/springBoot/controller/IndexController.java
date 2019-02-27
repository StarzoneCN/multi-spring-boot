package com.example.demo.multi.springBoot.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/12 18:49
 * @modefied:
 */
@RestController
@RequestMapping("/")
public class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private static final String MSG_APP_WELCOME = "欢迎来到Spring-Boot的世界！";

    @RequestMapping
    public String index(){
        return MSG_APP_WELCOME;
    }

    @RequestMapping("show/log")
    public String showLog(String logStr){
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", System.currentTimeMillis());
        map.put("msg", logStr);
        LOGGER.error(JSONObject.toJSONString(map));
        return JSONObject.toJSONString(map);
    }
}
