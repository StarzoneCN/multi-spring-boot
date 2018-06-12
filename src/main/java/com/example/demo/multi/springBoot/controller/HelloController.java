package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.entity.User;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("user")
    public User getUser(){
       User user = new User();
       user.setAge(17);
       user.setName("shuiling");
       user.setAddr("guangdongmaoming");
       user.setGender(2);
       user.setRemarks("dongrendemeirener");
       return user;
    }

    @RequestMapping("addUser")
    public String addUser(@RequestBody User user){
        System.out.println(user);
        return user.toString();
    }
}
