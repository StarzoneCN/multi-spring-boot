package com.example.demo.multi.springBoot.proxy.handler;

import com.example.demo.multi.springBoot.proxy.service.BusynessInterface;
import com.example.demo.multi.springBoot.proxy.service.impl.BusynessInterfaceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author: LiHongxing
 * @date: Create in 2018-03-07 13:32
 * @modefied:
 */
public class BusynessInvocationHandler implements InvocationHandler {

    private BusynessInterface busynessInterface = new BusynessInterfaceImpl();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke ....");
        Object reObj = method.invoke(busynessInterface, args);
        System.out.println("after invoke ....");
        return reObj;
    }

    public void print() {
        System.out.println("this is print() method of BusynessInvocationHandler ... ");
    }
}
