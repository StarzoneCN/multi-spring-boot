package com.example.demo.multi.springBoot.controller;

import com.example.demo.multi.springBoot.controller.service.HdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * 分布式文件系统hdfs接口类
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/28 15:08
 * @modefied:
 */
@RestController
@RequestMapping("hdfs")
public class HdfsController {
    @Autowired private HdfsService hdfsService;

    @RequestMapping("ls")
    public List<String> ls(String dir) {
        try {
            List<String> filenames = hdfsService.ls(dir);
            return filenames == null ? Collections.emptyList() : filenames;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @RequestMapping("upload")
    public String upload() {
        File file = new File("C:\\Users\\starzonecn\\Desktop\\temp/temp.xml");
        try {
            hdfsService.upload(file, "/doc/temp.xml");
        } catch (IOException e) {
            return e.getMessage();
        }
        return "success";
    }
}
