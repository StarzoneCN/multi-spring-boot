package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.constant.ResponseCodeEnum;
import com.example.demo.multi.springBoot.service.TestService;
import com.example.demo.multi.springBoot.vo.CommonResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Li Hongxing
 * @description: Hello world！
 * @date: Created in 2017/12/05 12:13
 * @modified:
 */
@RestController
@RequestMapping("hello")
@Scope(value = "prototype")
public class HelloController {
    @Autowired
    private TestService testService;

   @RequestMapping
    public String index() {
       testService.test();
       return "Let's go ! Conquer the world from here! [" + this.hashCode() + "]\nTestService.hashCode: "
               + testService.hashCode();
    }

    @GetMapping("test")
    public CommonResponseVo<String> test(){
        CommonResponseVo<String> vo = new CommonResponseVo<>();
        vo.setCode(ResponseCodeEnum.SUCCESS);
       return vo;
    }
}
