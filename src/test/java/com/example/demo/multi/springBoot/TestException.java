package com.example.demo.multi.springBoot;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.Matchers.startsWith;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/10/29 11:52:32
 */
public class TestException {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testNull() {
        String[] names ={"testA","testB"} ;
        thrown.expect(ArrayIndexOutOfBoundsException.class);
        thrown.expectMessage("2");
        thrown.expectMessage(startsWith("2")); //匹配器
        System.out.print(names[2]); //抛出数组越界异常
    }
}
