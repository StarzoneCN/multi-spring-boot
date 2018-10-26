package com.example.demo.multi.springBoot.mybatisPlus.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/10/26 21:23
 * @modefied:
 */
@Getter
@Setter
public class CommonResponse<T> {

    /*如果成功，返回数据*/
    private T data;
    /*请求是否成功*/
    private Boolean success;
    /*提示信息*/
    private String message;
}
