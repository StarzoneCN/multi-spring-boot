package com.example.demo.multi.springBoot.mybatisPlus.service.impl;

import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.mybatisPlus.mapper.UserMapper;
import com.example.demo.multi.springBoot.mybatisPlus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LiHongxing
 * @since 2018-10-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private UserMapper userMapper;

    @Override
    public List<User> getByMap(Map<String, Object> map){
        return userMapper.selectByMap(map);
    }

    @Override
    public int removeBatchIds(Collection ids){
        return userMapper.deleteBatchIds(ids);
    }
}
