package com.example.demo.multi.springBoot;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author: Li Hongxing
 * @description: bean测试样例
 * @date: Created in 2017/12/08 10:12
 * @modified:
 */
/*默认是单例模式，可以通过@scope="singleton"设置，其他模式：prototype、request、session、global session*/
@Component
@Scope("prototype")
public class BeanExample {

    public static final String BEAN_NAME = "BeanExample";

    private String prop1;

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }
}
