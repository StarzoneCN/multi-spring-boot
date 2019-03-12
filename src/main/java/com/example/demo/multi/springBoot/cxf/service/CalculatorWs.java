package com.example.demo.multi.springBoot.cxf.service;

import javax.jws.WebService;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/3/7 14:45
 * @modefied by
 */
@WebService(targetNamespace = "http://superbiz.org/wsdl")
public interface CalculatorWs {

    public int sum(int add1, int add2);

    public int multiply(int mul1, int mul2);
}