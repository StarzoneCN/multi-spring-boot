package com.example.demo.multi.springBoot.websocket;

import javax.websocket.*;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/6/28 11:14
 * @modefied:
 */
public class MessageDecoder implements Encoder.Text<String> {

    @Override
    public String encode(String str) throws EncodeException {
        return str + "|had been encoded";
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
