package com.example.demo.multi.springBoot.http;

import java.io.File;
import java.io.IOException;

/**
 * @author: LiHongxing
 * @date: Create in 2018-01-29 15:49
 * @modefied:
 */
public class FileTest {

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

    public static void main(String[] args) {
        File file = new File("C:\\Users\\starz\\Desktop\\temp/aaaa.txt");
        System.out.println(file.getAbsolutePath());
    }
}
