package com.example.demo.multi.springBoot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/28 11:43
 * @modefied:
 */
@Configuration
@ImportResource(locations = "classpath:applicationContext.xml")
public class XmlConfiguration {
}
