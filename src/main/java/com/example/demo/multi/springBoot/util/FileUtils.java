package com.example.demo.multi.springBoot.util;

import java.io.*;

/**
 * @author: LiHongxing
 * @date: Create in 2018-01-29 10:03
 * @modefied:
 */
public class FileUtils {

    public static String readFileAsStr(File textFile) throws IOException {
        Reader reader = new FileReader(textFile);
        BufferedReader br  = new BufferedReader(reader);
        StringBuffer sb = new StringBuffer();
        do {
            sb.append(br.readLine());
        } while (br.read() != -1);
        return sb.toString();
    }
}
