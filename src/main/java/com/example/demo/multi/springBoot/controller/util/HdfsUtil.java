package com.example.demo.multi.springBoot.controller.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * HDFS工具
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/28 15:33
 * @modefied:
 */
public class HdfsUtil {
    public static final String USER_OF_HDFS = "root";
    public static final String URI_OF_HDFS = "hdfs://192.168.76.129:9000";

    public static FileSystem fileSystem() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        return FileSystem.get(new URI(URI_OF_HDFS), conf, USER_OF_HDFS);
    }
}
