package com.example.demo.multi.springBoot.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 用户实体（测试）
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/3/30 14:12
 * @modefied:
 */
public class Member {

    /*这些注解都是hibernate-validator的*/
    @NotEmpty(message = "{member.account.null}")
    private String account;
    @NotEmpty(message = "{member.realName.null}")
    @Length(max = 10, message = "{member.realName.tooLang}")
    private String realName;
    private boolean man;
    private String address;
    private String phone;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
