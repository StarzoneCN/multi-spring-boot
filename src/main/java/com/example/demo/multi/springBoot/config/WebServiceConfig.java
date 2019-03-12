package com.example.demo.multi.springBoot.config;

import javax.xml.ws.Endpoint;

import com.example.demo.multi.springBoot.cxf.service.impl.Calculator;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/3/7 14:47
 * @modefied by
 */
@Configuration
public class WebServiceConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, new Calculator());
        endpoint.publish("/Calculator");
        return endpoint;
    }
}
