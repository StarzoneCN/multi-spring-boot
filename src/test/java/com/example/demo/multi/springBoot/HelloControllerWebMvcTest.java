package com.example.demo.multi.springBoot;

import com.example.demo.multi.springBoot.controller.HelloController;
import com.example.demo.multi.springBoot.service.SimpleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/29 15:21:50
 */
@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerWebMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SimpleService simpleService;

    @Test
    public void testIndex() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("/hello")
                    .accept(MediaType.TEXT_PLAIN))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Let's go ! Conquer the world from here!"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
