package com.example.demo.multi.springBoot.vo;

import lombok.Data;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-06-02 19:31:02
 * @modefied:
 */
@Data
public class WxIndexCheckVO {

    /**
     * 微信加密签名
     */
    private String signature;

    /**
     * 时间戳
     */
    private String timestamp;

    /**
     * 随机数
     */
    private String nonce;

    /**
     * 随机字符串
     */
    private String echostr;
}
