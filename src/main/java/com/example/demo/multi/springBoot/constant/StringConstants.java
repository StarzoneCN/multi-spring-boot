package com.example.demo.multi.springBoot.constant;

public class StringConstants {
    private StringConstants(){}

    public static final String SSO_RESPONSE_SUCCESS_FLAG = "isSuccess";
    public static final String SSO_RESPONSE_CODE_FLAG = "responseCode";
    public static final String SSO_RESPONSE_MSG_FLAG = "responseMsg";

    /*登录验证码在redis中的key的前缀*/
    public static final String RAND_PREFIX_OF_REDIS_KEY = "key_rand_";

    public static final String RAND_KEY_IN_SESSION = "rand";
    /*session在redis中的key的前缀*/
    public static final String SESSION_PREFIX_OF_REDIS_KEY = "portal_token_";
}
