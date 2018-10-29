package com.example.demo.multi.springBoot.mybatisPlus.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 手机运营商
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/10/29 19:12
 * @modefied:
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PhoneEnum implements IEnum {

    CMCC("10086", "中国移动"),
    CUCC("10010", "中国联通"),
    CT("10000", "中国电信");

    private String value;
    private String desc;

    PhoneEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @JsonValue
    public String getDesc(){
        return this.desc;
    }
}
