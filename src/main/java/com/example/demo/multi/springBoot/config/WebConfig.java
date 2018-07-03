package com.example.demo.multi.springBoot.config;

import com.example.demo.multi.springBoot.websocket.EchoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.socket.server.support.WebSocketHttpRequestHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/28 15:04
 * @modefied:
 */
@Configuration
public class WebConfig {

    @Bean
    public SimpleUrlHandlerMapping handlerMapping() {

        Map<String, Object> urlMap = new HashMap<String, Object>();
        urlMap.put("/echo", new WebSocketHttpRequestHandler(new EchoHandler()));

        SimpleUrlHandlerMapping hm = new SimpleUrlHandlerMapping();
        hm.setUrlMap(urlMap);
        return hm;
    }
}
