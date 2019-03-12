package com.example.demo.multi.springBoot.cxf.service.impl;

import com.example.demo.multi.springBoot.cxf.service.CalculatorWs;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/3/7 14:46
 * @modefied by
 */
@Stateless
@WebService(
        portName = "CalculatorPort",
        serviceName = "CalculatorService",
        targetNamespace = "http://superbiz.org/wsdl",
        endpointInterface = "com.example.demo.multi.springBoot.cxf.service.CalculatorWs")
public class Calculator implements CalculatorWs {

    public int sum(int add1, int add2) {
        return add1 + add2;
    }

    public int multiply(int mul1, int mul2) {
        return mul1 * mul2;
    }
}
