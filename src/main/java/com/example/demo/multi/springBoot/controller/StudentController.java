package com.example.demo.multi.springBoot.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.multi.springBoot.dao.StudentRepository;
import com.example.demo.multi.springBoot.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/{id}")
    public Student getById(@PathVariable String id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()){
            return optionalStudent.get();
        }
        return new Student();
    }

    @PostMapping("save")
    public String save(@RequestBody Student student){
        if (!StringUtils.hasText(student.getId())){
            return "student对象的id不能为空";
        }
        studentRepository.save(student);
        return "success";
    }
}
