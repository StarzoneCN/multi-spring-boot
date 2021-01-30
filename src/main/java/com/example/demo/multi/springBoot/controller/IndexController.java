package com.example.demo.multi.springBoot.controller;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/12 18:49
 * @modefied:
 */
@RestController
@RequestMapping("/")
public class IndexController {

    private static final String MSG_APP_WELCOME = "欢迎来到Spring-Boot的世界！";

    @RequestMapping
    public String index(){
        return MSG_APP_WELCOME;
    }



    @RequestMapping("authorizationCode")
    public String getCodeAndRequetToken(HttpServletRequest request, String code){
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("code", code);
        params.add("client_id","client");
        params.add("client_secret","123456");
        params.add("grant_type","authorization_code");
        params.add("redirect_uri","http://localhost:81/authorizationCode");
        params.add("scope","read");

        HttpHeaders headers = new HttpHeaders();
//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        //  执行HTTP请求
        ResponseEntity<String> response = restTemplate.exchange("http://localhost/zhlawoffice/oauth/token", HttpMethod.POST, requestEntity, String.class);
        System.out.println(response.getBody());
        return "no token";
    }
}
