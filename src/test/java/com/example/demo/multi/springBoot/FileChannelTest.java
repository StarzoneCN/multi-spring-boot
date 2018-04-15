package com.example.demo.multi.springBoot;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/13 15:31
 * @modefied:
 */
public class FileChannelTest {

    @Test
    public void readFile() throws IOException {
        String filePath = "C:\\Users\\starzonecn\\Desktop\\temp/test.txt";
        System.out.println(readFileToString(filePath, Charset.forName("utf-8")));
    }


    public static String readFileToString(String filePath, Charset charset) throws IOException {
        try(FileInputStream in = new FileInputStream(filePath);
            FileChannel channel = in.getChannel()
        ){
            long fileSize = channel.size();
            int bufferSize = 1024;
            if (fileSize < 1024){
                bufferSize = (int)fileSize;
            }
            StringBuilder builder = new StringBuilder((int)(fileSize/2));

            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            CharBuffer charBuffer = CharBuffer.allocate(bufferSize/2);
            CharsetDecoder decoder = charset.newDecoder();
            while (channel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                CoderResult rel;
                do{
                    rel = decoder.decode(byteBuffer,charBuffer,false);
                    charBuffer.flip();

                    builder.append(charBuffer.array(),0,charBuffer.limit());
                    charBuffer.clear();
                }while (rel.isOverflow());
                byteBuffer.compact();
            }

            byteBuffer.flip();
            decoder.decode(byteBuffer,charBuffer,true);
            charBuffer.flip();
            builder.append(charBuffer.array(),0,charBuffer.limit());
            charBuffer.clear();

            return builder.toString();
        }
    }
}
