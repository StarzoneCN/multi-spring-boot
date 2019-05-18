package com.example.demo.multi.springBoot.constant;

public class StringConstants {
    private StringConstants(){}

    public static final String SSO_RESPONSE_SUCCESS_FLAG = "isSuccess";
    public static final String SSO_RESPONSE_CODE_FLAG = "responseCode";
    public static final String SSO_RESPONSE_MSG_FLAG = "responseMsg";

    public static final String THE_RESPONESE_CODE_IS_NOT_EXISTS = "响应码（code={0}）未维护";

    /*登录验证码在redis中的key的前缀*/
    public static final String RAND_PREFIX_OF_REDIS_KEY = "key_rand_";

    public static final String RAND_KEY_IN_SESSION = "rand";
    /*session在redis中的key的前缀*/
    public static final String SESSION_PREFIX_OF_REDIS_KEY = "portal_token_";
    /*系统异常*/
    public static final String SYS_EXCEPTION = "系统异常";
    public static final String SYS_EXCEPTION_WITH_COLON = "系统异常：";
}
