package com.example.demo.multi.springBoot.mybatisPlus.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.example.demo.multi.springBoot.mybatisPlus.enums.PhoneEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiHongxing
 * @since 2018-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private Integer age;

    private String email;

    @TableLogic
    private Boolean delFlag;

    private PhoneEnum mobile;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    private Date operateTime;
}
