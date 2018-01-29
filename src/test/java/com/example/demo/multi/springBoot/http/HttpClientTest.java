package com.example.demo.multi.springBoot.http;

import com.example.demo.multi.springBoot.util.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试http链接
 *
 * @author: LiHongxing
 * @date: Create in 2018-01-29 9:10
 * @modefied:
 */
public class HttpClientTest {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static String sendGET(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);

        System.out.println("GET Response Status:: " + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        String responseStr = response.toString();
//        httpClient.close();
        return responseStr;
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("username", "hongxing");

        Map<String, File> files = new HashMap<>(2);
        files.put("file", new File("C:\\Users\\starz\\Desktop\\temp/aaaa.txt"));

        String responseStr = HttpUtils.postFile("http://localhost/hello/post", params, files);
        System.out.println(responseStr);
    }
}
