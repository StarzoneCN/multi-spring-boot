package com.example.demo.multi.springBoot;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @description:
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2019-05-20 14:20:21
 * @modefied:
 **/
public class SwaggerConfiguration {

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("这是啥")
                .description("这是啥的描述")
                .contact(new Contact("联系人", "联系人网站地址", "联系人邮箱"))
                .license("没有协议")
                .licenseUrl("没有协议地址")
                .version("版本号")
                .build();
    }
}
