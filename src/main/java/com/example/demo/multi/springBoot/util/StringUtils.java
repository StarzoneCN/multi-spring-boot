package com.example.demo.multi.springBoot.util;

import cn.hutool.core.util.StrUtil;
import com.example.demo.multi.springBoot.entity.Ssq;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/4/18 22:50
 * @modefied:
 */
public class StringUtils extends StrUtil {

    public static String assemblySsq2Str(Ssq ssq) {
        return ssq.getR0()
                + "-" + ssq.getR1()
                + "-" + ssq.getR2()
                + "-" + ssq.getR3()
                + "-" + ssq.getR4()
                + "-" + ssq.getR5()
                + "+" + ssq.getB0();
    }
}
