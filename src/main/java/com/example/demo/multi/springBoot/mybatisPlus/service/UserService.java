package com.example.demo.multi.springBoot.mybatisPlus.service;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LiHongxing
 * @since 2018-10-26
 */
public interface UserService extends IService<User> {

    List<User> getByMap(Map<String, Object> map);

    int removeBatchIds(Collection ids);
}
