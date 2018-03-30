package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.controller.util.HdfsUtil;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootApplication
public class DemoMultiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoMultiSpringBootApplication.class, args);
	}

	@Bean
	public FileSystem fileSystem() throws InterruptedException, IOException, URISyntaxException {
		return HdfsUtil.fileSystem();
	}
}
