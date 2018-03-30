package com.example.demo.multi.springBoot.controller.service.impl;

import com.example.demo.multi.springBoot.controller.service.HdfsService;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HDFS操作接口
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/28 15:27
 * @modefied:
 */
@Service
public class HdfsServiceImpl implements HdfsService {
    private final static String MSG_FILE_NOT_EXSITS = "File not exists!";
    private final static String MSG_FAIL_TO_READ_FILE = "Fail to read file!";
    @Autowired private FileSystem fileSystem;

    @Override
    public List<String> ls(String dir) throws IOException {
        Path path = new Path(dir);
        FileStatus[] fss = fileSystem.listStatus(path);
        if (fss.length > 0) {
            List<String> fileNames = new ArrayList<>();
            for (FileStatus fileStatus : fss) {
                fileNames.add(fileStatus.toString());
            }
            return fileNames;
        }
        return null;
    }

    @Override
    public String upload(File file, String path) throws IOException {
        return upload(file, path, true);
    }

    @Override
    public String upload(File file, String path, boolean overwritten) throws IOException {
        if (!file.exists())
            return MSG_FILE_NOT_EXSITS;
        try {
            upload(new FileInputStream(file), path, overwritten);
        } catch (FileNotFoundException e) {
            return MSG_FAIL_TO_READ_FILE;
        }
        return null;
    }

    @Override
    public void upload(InputStream is, String path, boolean overwritten) throws IOException {
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path(path));
        int i;
        try {
            while ((i = is.read()) != -1) {
                fsDataOutputStream.write(i);
            }
        } finally {
            is.close();
            fsDataOutputStream.close();
            System.out.println("Success uploading file!");
        }
    }
}
