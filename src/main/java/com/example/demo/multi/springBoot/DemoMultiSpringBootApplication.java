package com.example.demo.multi.springBoot;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableApolloConfig({"application", "native", "TEST2.common.reids"})
@SpringBootApplication
public class DemoMultiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMultiSpringBootApplication.class, args);
	}
}
