package com.example.demo.multi.springBoot;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.multi.springBoot.service.SimpleService;
import com.example.demo.multi.springBoot.service.impl.SimpleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/29 14:12:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootWebTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private SimpleService simpleService;

    @LocalServerPort
    private int port;

    @Before
    public void setup() {
        given(this.simpleService.timeString()).willReturn("long long ago ...");
    }

    @Test
    public void test() {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(
                "/nowTimeString", String.class);
        System.out.println("responseEntity：" + JSONObject.toJSONString(responseEntity));
        System.out.println("this's port is " + port);
    }
}
