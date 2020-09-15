package com.example.demo.multi.springBoot.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.multi.springBoot.vo.WxIndexCheckVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 微信基本接口，如token对接
 *
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-06-02 19:23:03
 * @modefied:
 */
@RestController
@RequestMapping("wx")
public class WxBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxBaseController.class);
    private static final String TOKEN_WX = "fdjsafe432914if3i2ijfew";

    @GetMapping
    public String index(WxIndexCheckVO vo){
        LOGGER.info("WxIndexCheckVO: " + JSON.toJSONString(vo));
        List<String> params = new ArrayList<>(4);
        if (StringUtils.isBlank(vo.getTimestamp())
                || StringUtils.isBlank(vo.getNonce())
                || StringUtils.isBlank(vo.getSignature())) {
            return null;
        }
        params.add(vo.getTimestamp());
        params.add(vo.getNonce());
        params.add(TOKEN_WX);
        Collections.sort(params);
        StringBuilder paramsSb = new StringBuilder();
        for (String param : params) {
            paramsSb.append(param);
        }
        LOGGER.info("paramsSb: " + paramsSb.toString());
        // String computedSignature =  DigestUtils.sha1Hex(paramsSb.toString());
        // LOGGER.info("computedSignature: " + computedSignature);
        // if (vo.getSignature().equals(computedSignature)){
            return vo.getEchostr();
        // }
        // return null;
    }


    @PostMapping
    public String event(String params){
        LOGGER.info(params);
        return "";
    }
}
