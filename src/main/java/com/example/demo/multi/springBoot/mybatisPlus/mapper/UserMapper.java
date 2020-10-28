package com.example.demo.multi.springBoot.mybatisPlus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.multi.springBoot.mybatisPlus.entity.PartOfUser;
import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LiHongxing
 * @since 2018-10-26
 */
public interface UserMapper extends BaseMapper<User> {

    User selectByIdCustomized(Integer id);

    PartOfUser selectPartOfUser(Integer id);

    Map<String, Object> selectUserMapById(Integer id);

    List<User> selectAll();
}