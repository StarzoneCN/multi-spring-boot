package com.example.demo.multi.springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DemoMultiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMultiSpringBootApplication.class, args);
	}
}
