package com.example.demo.multi.springBoot.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Optional;

public enum ResponseCodeEnum {
    SUCCESS(0, "Request is successful"),
    BAD_PARAMS(-1, "Parameter violation"),
    BAD_PERMISSION(-40003, "Lack of Permissions"),
    FAIL_2_EXE(-10001, "Fail to execute request method");

    private static final String MSG_FAIL_TO_INSTANCE = "code({0})";
    /*执行结果代码*/
    private int code;
    /*执行结果提示语*/
    private String msg;
    ResponseCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    @JsonValue
    public int code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }

    public static ResponseCodeEnum instance(int code){
        Optional<ResponseCodeEnum> optional =  Arrays.stream(ResponseCodeEnum.values())
                .filter(e -> e.code() == code).findAny();
        if (optional.isPresent()){
            return optional.get();
        }
        throw new EnumConstantNotPresentException(ResponseCodeEnum.class,
                MessageFormat.format(MSG_FAIL_TO_INSTANCE, code));
    }
}
