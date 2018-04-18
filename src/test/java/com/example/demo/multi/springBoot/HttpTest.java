package com.example.demo.multi.springBoot;

import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.nio.charset.Charset;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/15 23:27
 * @modefied:
 */
public class HttpTest {

    @Test
    public void testHttp() {
        String htmlStr = HttpUtil.get("http://f.apiplus.net/ssq-20.json", Charset.forName("UTF-8"));
        System.out.println(htmlStr);
    }
}
