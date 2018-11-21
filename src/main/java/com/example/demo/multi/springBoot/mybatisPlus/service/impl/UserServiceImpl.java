package com.example.demo.multi.springBoot.mybatisPlus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.mybatisPlus.mapper.UserMapper;
import com.example.demo.multi.springBoot.mybatisPlus.service.UserService;
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

    @Override
    public List sortByNameAndAge(){
        QueryWrapper qw = new QueryWrapper<User>();
        qw.orderByAsc("name");
        qw.orderByDesc("age");
        qw.orderByAsc("email");
        return userMapper.selectList(qw);
    }

    /**
     * 这个分页插件（mybati-plus）是查询出所有的，然后再在内存中分页，性能不好
     * @param page
     * @return
     */
    @Override
    public IPage<User> getUsersPage(IPage<User> page){
        page = userMapper.selectPage(page, null);
        return page;
    }

    /**
     * 根据id查询用户，不管是否已经被逻辑删除
     * @param id 用户id
     * @return 用户对象
     */
    @Override
    public User getByIdWhetherDeleteOrNot(Integer id){
        return userMapper.selectByIdCustomized(id);
    }
}
