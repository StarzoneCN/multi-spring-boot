package com.example.demo.multi.springBoot.mybatisPlus.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/10/25 14:20
 * @modefied:
 */
@Setter
@Getter
@Accessors(chain = true)
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String email;
    @TableLogic
    private Boolean delFlag;
}
