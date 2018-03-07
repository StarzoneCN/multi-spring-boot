package com.example.demo.multi.springBoot.proxy;

import com.example.demo.multi.springBoot.proxy.service.BusynessInterface;
import com.example.demo.multi.springBoot.proxy.handler.BusynessInvocationHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @author: LiHongxing
 * @date: Create in 2018-03-07 13:51
 * @modefied:
 */
public class JdkProxyTest {

    @Test
    public void testJdkProxy() {
        Class[] interfaces = {BusynessInterface.class};
        BusynessInterface bi = (BusynessInterface)Proxy.newProxyInstance(BusynessInterface.class.getClassLoader(), interfaces, new BusynessInvocationHandler());
        System.out.println(bi.benefits());;
    }
}
