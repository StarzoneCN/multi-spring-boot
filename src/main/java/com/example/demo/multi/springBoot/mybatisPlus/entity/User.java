package com.example.demo.multi.springBoot.mybatisPlus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.example.demo.multi.springBoot.mybatisPlus.enums.PhoneEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author LiHongxing
 * @since 2018-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField(strategy = FieldStrategy.IGNORED)
    private Integer age;

    private String email;

    @TableLogic
    private Boolean delFlag;

    private PhoneEnum mobile;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime operateTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
