package com.example.demo.multi.springBoot;

import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan
@EnableDubboConfig
@SpringBootApplication
public class DemoMultiSpringBootApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DemoMultiSpringBootApplication.class, args);
	}
}
