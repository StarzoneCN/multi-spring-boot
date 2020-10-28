package com.example.demo.multi.springBoot.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/12 18:49
 * @modefied:
 */
@RestController
public class IndexController {
    private static final String MSG_APP_WELCOME = "欢迎来到Spring-Boot的世界！";

    @RequestMapping
    public String index(){
        return MSG_APP_WELCOME;
    }

    @PostMapping("dto")
    public String getDto(ParamsDTO paramsDTO){
        System.out.println("接收到数据：" + JSONObject.toJSONString(paramsDTO));
        return "success";
    }

    public static class ParamsDTO {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
