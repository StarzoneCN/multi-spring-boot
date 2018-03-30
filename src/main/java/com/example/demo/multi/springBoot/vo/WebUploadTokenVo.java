package com.example.demo.multi.springBoot.vo;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/30 10:30
 * @modefied:
 */
public class WebUploadTokenVo {

    private String appId;
    private String token;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "WebUploadTokenVo{" +
                "appId='" + appId + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
