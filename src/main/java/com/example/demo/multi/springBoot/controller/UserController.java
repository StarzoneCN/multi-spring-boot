package com.example.demo.multi.springBoot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("{id}")
    public String id(@PathVariable Integer id){

        return "{id: " + id + ", name: \"Tom\"}";
    }
}
