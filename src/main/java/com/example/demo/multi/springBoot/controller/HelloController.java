package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.util.FileUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author: Li Hongxing
 * @description: Hello world！
 * @date: Created in 2017/12/05 12:13
 * @modified:
 */
@RestController
@RequestMapping("hello")
public class HelloController {

   @RequestMapping
    public String index() {
        return "Conquer the world from here!";
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String processPost(String username, MultipartFile file) {
        System.out.println(username);
        try {
            System.out.println(StreamUtils.copyToString(file.getInputStream(), Charset.forName("utf8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public String processGet() {
        return "success";
    }
}
