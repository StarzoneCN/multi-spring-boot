package com.example.demo.multi.springBoot.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 处理http请求
 *
 * @author: LiHongxing
 * @date: Create in 2018-01-29 9:42
 * @modefied:
 */
public class HttpUtils {

    private static final String USER_AGENT = "Mozilla/5.0";

    public static HttpEntity makeMultipartEntity(Map<String, String> params, final Map<String, File> files, Map<String, InputStream> iss) {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); //如果有SocketTimeoutException等情况，可修改这个枚举

        //builder.setCharset(Charset.forName("UTF-8"));
        //不要用这个，会导致服务端接收不到参数

        if (!ObjectUtils.isEmpty(params) && params.size() > 0) {
            for (Entry<String, String> p : params.entrySet()) {
                builder.addTextBody(p.getKey(), p.getValue(), ContentType.TEXT_PLAIN.withCharset("UTF-8"));
            }
        }

        if (files != null && files.size() > 0) {
            Set<Entry<String, File>> entries = files.entrySet();
            for (Entry<String, File> entry : entries) {
                builder.addPart(entry.getKey(), new FileBody(entry.getValue()));
            }
        }

        if (iss != null && iss.size() > 0) {
            Set<Entry<String, InputStream>> entries = iss.entrySet();
            for (Entry<String, InputStream> entry : entries) {
                builder.addBinaryBody(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    public static String postFile(String url, Map<String, String> params, final Map<String, File> files) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = postFile(httpClient, url, params, files);
        String responseStr = getResponseStr(httpResponse);
        httpClient.close();
        return responseStr;
    }

    public static String postInputStreams(String url, Map<String, String> params, Map<String, InputStream> iss) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpResponse httpResponse = postInputSteams(httpClient, url, params, iss);
        String responseStr = getResponseStr(httpResponse);
        httpClient.close();
        return responseStr;
    }

    public static HttpResponse postFile(HttpClient httpClient, String url, Map<String, String> params, final Map<String, File> files) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("User-Agent", USER_AGENT);

        HttpEntity entity = makeMultipartEntity(params, files, null);
        httpPost.setEntity(entity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        return httpResponse;
    }

    public static HttpResponse postInputSteams(HttpClient httpClient, String url, Map<String, String> params, Map<String, InputStream> iss) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("User-Agent", USER_AGENT);

        HttpEntity entity = makeMultipartEntity(params, null, iss);
        httpPost.setEntity(entity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        return httpResponse;
    }

    public static String getResponseStr(HttpResponse httpResponse) throws IOException {
        System.out.println("POST Response Status:: " + httpResponse.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

        String inputLine;
        StringBuffer sb = new StringBuffer();

        while ((inputLine = reader.readLine()) != null) {
            sb.append(inputLine);
        }
        reader.close();

        String responseStr = sb.toString();
        return responseStr;
    }
}
