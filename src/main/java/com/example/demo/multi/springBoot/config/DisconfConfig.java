package com.example.demo.multi.springBoot.config;

import com.baidu.disconf.client.DisconfMgrBean;
import com.baidu.disconf.client.DisconfMgrBeanSecond;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sun.security.pkcs11.Secmod;

/**
 * disconf整合
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/23 10:24
 * @modefied:
 */
@Configuration
public class DisconfConfig {

    @Bean(destroyMethod = "destroy")
    public DisconfMgrBean getDisconfMgrBean(){
        DisconfMgrBean dmb = new DisconfMgrBean();
        dmb.setScanPackage("com.example.demo.multi.springBoot");
        return dmb;
    }

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public DisconfMgrBeanSecond getDisconfMgrBeanSecond(){
        return new DisconfMgrBeanSecond();
    }
}
