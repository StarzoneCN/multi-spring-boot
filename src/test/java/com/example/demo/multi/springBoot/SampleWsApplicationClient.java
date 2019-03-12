package com.example.demo.multi.springBoot;

import org.apache.cxf.staxutils.StaxUtils;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @created: 2019/3/7 14:33
 * @modefied by
 */
public class SampleWsApplicationClient {

    @Test
    public void test() throws MalformedURLException {
        String address = "http://localhost:8080/Service/Hello";
        String request = "<q0:sayHello xmlns:q0=\"http://service.ws.sample/\"><myname>Elan</myname></q0:sayHello>";

        StreamSource source = new StreamSource(new StringReader(request));
        Service service = Service.create(new URL(address + "?wsdl"),
                new QName("http://service.ws.sample/" , "HelloService"));
        Dispatch<Source> disp = service.createDispatch(new QName("http://service.ws.sample/" , "HelloPort"),
                Source.class, Service.Mode.PAYLOAD);

        Source result = disp.invoke(source);
        String resultAsString = StaxUtils.toString(result);
        System.out.println(resultAsString);
    }
}
