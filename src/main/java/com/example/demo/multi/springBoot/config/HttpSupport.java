package com.example.demo.multi.springBoot.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/2/27 15:43
 * @modefied by
 */
@Configuration
public class HttpSupport {

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(8442);
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }
}
