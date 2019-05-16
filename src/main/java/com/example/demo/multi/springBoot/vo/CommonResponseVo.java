package com.example.demo.multi.springBoot.vo;

import com.example.demo.multi.springBoot.constant.ResponseCodeEnum;
import lombok.Data;

@Data
public class CommonResponseVo<T> {

    /**
     * 执行结果代码，为了便于维护，请将所有返回码保存在
     * {@link com.example.demo.multi.springBoot.constant.ResponseCodeEnum}中
     **/
    private Enum<ResponseCodeEnum> code;
    /*提示语*/
    private String msg;
    /*响应数据*/
    private Object data;
}
