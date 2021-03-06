package com.example.demo.multi.springBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DemoMultiSpringBootApplication {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoMultiSpringBootApplication.class, args);

		/*添加换行*/
	}
}
