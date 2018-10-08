package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public User getUser() {
        User user = new User();
        user.setAge(17);
        user.setName("shuiling");
        user.setAddr("guangdongmaoming");
        user.setGender(2);
        user.setRemarks("dongrendemeirener");
        return user;
    }

    @RequestMapping("users")
    public List<User> getUsers(Integer count, HttpServletRequest request, Integer pageIndex, Integer pageSize) {
        System.out.println(request.getParameter("pageIndex"));
        System.out.println(request.getParameter("pageSize"));
        count = count == null ? 100 : count;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setAge(17);
            user.setName("shuiling");
            user.setAddr("guangdongmaoming");
            user.setGender(2);
            user.setRemarks("<div style=\"color:yellow;\">dongrendemeirener</div>");
            user.setBirthday("1993-12-" + i%28);
            users.add(user);
        }
        return users;
    }

    @RequestMapping("users2")
    public Map<String, Object> getUsers2(Integer count) {
        count = count == null ? 100 : count;
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(i + 1);
            user.setAge(17);
            user.setName("shuiling");
            user.setAddr("guangdongmaoming");
            user.setGender(2);
            user.setRemarks("<div style=\"color:red;\">dongrendemeirener</div>");
            user.setBirthday("1993-12-" + i%28);
            users.add(user);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", users);
        map.put("total", 10000);
        map.put("pageIndex", 3);
        map.put("pageSize", 200);
        return map;
    }

    @RequestMapping("addUser")
    public String addUser(@RequestBody User user) {
        System.out.println(user);
        return user.toString();
    }

}
