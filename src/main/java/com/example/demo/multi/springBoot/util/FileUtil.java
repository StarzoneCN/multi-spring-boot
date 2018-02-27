package com.example.demo.multi.springBoot.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具
 *
 * @author: LiHongxing
 * @date: Create in 2018-02-27 14:56
 * @modefied:
 */
public class FileUtil extends FileUtils {

    /**
     * 新建文件
     *
     * @param url 文件路径
     * @param cover 如果文件存在，是否覆盖原文件；true-删除原文件并新建，false-返回已存在文件
     */
    public static File createNewFile(String url, boolean cover) throws IOException {
        File file = new File(url);
        if (file.exists()) {
            if (cover) {
                if (file.delete()) {
                    boolean successCreating = file.createNewFile();
                    if (successCreating) {
                        return file;
                    }
                    throw new IOException("Fail to create file which url is " + url);
                } else {
                    throw new IOException("Fail to delete file which url is " + url);
                }
            } else {
                return file;
            }
        } else {
            File directory = file.getParentFile();
            if (!directory.exists()) {
                directory.mkdir();
            }
            boolean successCreating = file.createNewFile();
            if (successCreating) {
                return file;
            }
            throw new IOException("Fail to create file which url is " + url);
        }
    }
}
