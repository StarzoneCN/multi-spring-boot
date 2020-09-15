package com.example.demo.multi.springBoot;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2020-06-11 13:29:08
 * @modefied:
 */
@Data
public class TestBigdecimalDTO {

    private BigDecimal price;

    private Date tDate;
}
