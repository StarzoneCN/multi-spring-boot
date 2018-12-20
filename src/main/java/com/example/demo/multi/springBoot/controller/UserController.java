package com.example.demo.multi.springBoot.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.multi.springBoot.mybatisPlus.entity.PartOfUser;
import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.mybatisPlus.service.UserService;
import com.example.demo.multi.springBoot.mybatisPlus.vo.CommonResponse;
import com.example.demo.multi.springBoot.mybatisPlus.vo.UserResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LiHongxing
 * @since 2018-10-26
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final String MSG_USER_NOT_EXISTS = "用户不存在";
    private static final String MSG_FAIL_TO_QUERY = "查询失败";

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public CommonResponse<User> getById(@PathVariable Integer id){
        CommonResponse<User> commonResponse = new CommonResponse<>();
        User user = userService.getById(id);
        if (user != null){
            commonResponse.setSuccess(true);
            commonResponse.setData(user);
            return commonResponse;
        }
        commonResponse.setSuccess(false);
        commonResponse.setMessage(MSG_USER_NOT_EXISTS);
        return commonResponse;
    }

    @GetMapping("get/by/name")
    public CommonResponse<User> getByName(String name){
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("name", name);
        List<User> list = userService.getByMap(paramMap);
        CommonResponse commonResponse = new CommonResponse();
        if (list.size() > 0){
            commonResponse.setSuccess(true);
            commonResponse.setData(list);
            return commonResponse;
        }
        commonResponse.setSuccess(false);
        commonResponse.setMessage(MSG_USER_NOT_EXISTS);
        return commonResponse;
    }

    @DeleteMapping("remove/by/ids")
    public Integer removeBatchIds(Integer ... id){
        return userService.removeBatchIds(Arrays.asList(id));
    }

    @GetMapping("orderByNameAndAge")
    public CommonResponse orderByNameAndAge(){
        CommonResponse c = new CommonResponse();
        try {
            List list = userService.sortByNameAndAge();
            c.setSuccess(true);
            c.setData(list);
            return c;
        } catch (Exception e) {
            c.setSuccess(false);
            c.setMessage(MSG_FAIL_TO_QUERY);
            return c;
        }
    }

    @GetMapping("page")
    public IPage getPage(Integer pageIndex, Integer pageSize){
        Page<User> page = new Page();
        page.setCurrent(pageIndex);
        page.setSize(pageSize);
        page.setOptimizeCountSql(true);
        return userService.getUsersPage(page);
    }


    @GetMapping("refresh/{intParam}")
    public CommonResponse testXmlRefresh(@PathVariable Integer intParam){
        CommonResponse commonResponse = new CommonResponse();
        User user = userService.getByIdWhetherDeleteOrNot(intParam);
        if (null != user){
            commonResponse.setSuccess(true);
            commonResponse.setData(user);
            return commonResponse;
        }
        commonResponse.setSuccess(false);
        commonResponse.setMessage(MSG_USER_NOT_EXISTS);
        return commonResponse;
    }

    @RequestMapping("selectPartOfUser")
    public PartOfUser selectPartOfUser(Integer id){
        return userService.selectPartOfUser(id);
    }

    @PostMapping("new")
    public CommonResponse newUser(User user){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setSuccess(userService.save(user));
        commonResponse.setData(user.getId());
        return commonResponse;
    }

    @PostMapping("update")
    public CommonResponse updateById(User user){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setSuccess(userService.update(user, new UpdateWrapper<>(user)));
        return commonResponse;
    }


    @RequestMapping("test/list/objs/{id}")
    public List<User> testListObjs(Integer id){
        QueryWrapper<User> condition = new QueryWrapper(new User().setId(id), new String[]{"id", "name", "age", "create_time"});
        return userService.list(condition);
    }

    @RequestMapping("getByModelId")
    public User getByModelId(Integer id){
        User user = new User();
        user.setId(id);
        return user.selectById();
    }

    @RequestMapping("query/map/{id}")
    public Map<String, Object> selectUserMapById(@PathVariable Integer id){
        return userService.selectUserMapById(id);
    }
}
