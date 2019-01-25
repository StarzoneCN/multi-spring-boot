package com.example.demo.multi.springBoot.controller;

import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/8/12 18:49
 * @modefied:
 */
@RestController
@RequestMapping("/")
public class IndexController {
    private static final String MSG_APP_WELCOME = "欢迎来到Spring-Boot的世界！";
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(IndexController.class);
    @RequestMapping
    public String index(){
        LOGGER.debug("[DEBUG    ]: hahahaha");
        LOGGER.info("INFO: hahahaha");
        return MSG_APP_WELCOME;
    }

    @RequestMapping("log/show")
    public String log(String logStr){
        LOGGER.debug(logStr);
        LOGGER.info(logStr);
        LOGGER.warn(logStr);
        LOGGER.error(logStr);
        LOGGER.fatal(logStr);
        return "success";
    }
}
