package com.example.demo.multi.springBoot.controller;


import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.mybatisPlus.service.UserService;
import com.example.demo.multi.springBoot.mybatisPlus.vo.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiHongxing
 * @since 2018-10-26
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final String MSG_USER_NOT_EXISTS = "用户不存在";

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public CommonResponse<User> getById(@PathVariable Integer id){
        CommonResponse<User> commonResponse = new CommonResponse<>();
        User user = userService.getById(id);
        if (user != null){
            commonResponse.setSuccess(true);
            commonResponse.setData(user);
            return commonResponse;
        }
        commonResponse.setSuccess(false);
        commonResponse.setMessage(MSG_USER_NOT_EXISTS);
        return commonResponse;
    }

    @GetMapping("by/name")
    public CommonResponse<User> getByName(String name){
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("name", name);
        List<User> list = userService.getByMap(paramMap);
        CommonResponse commonResponse = new CommonResponse();
        if (list.size() > 0){
            commonResponse.setSuccess(true);
            commonResponse.setData(list);
            return commonResponse;
        }
        commonResponse.setSuccess(false);
        commonResponse.setMessage(MSG_USER_NOT_EXISTS);
        return commonResponse;
    }
}
