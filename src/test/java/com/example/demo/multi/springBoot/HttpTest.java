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
        String htmlStr = HttpUtil.get("http://kaijiang.500.com/shtml/ssq/18042.shtml", Charset.forName("UTF-8"));
        System.out.println(htmlStr);

        Document doc = Jsoup.parse(htmlStr);
        Elements elements = doc.getElementsByClass("iSelectList");
        for (Element element : elements) {
            Elements aChildren = element.children();
            for (Element child : aChildren) {
                String href = child.attr("href");
                System.out.println(child.text());
                break;
            }
        }
    }
}
