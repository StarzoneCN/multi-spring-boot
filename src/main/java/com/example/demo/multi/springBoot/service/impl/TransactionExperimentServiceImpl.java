package com.example.demo.multi.springBoot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.multi.springBoot.mybatisPlus.entity.User;
import com.example.demo.multi.springBoot.mybatisPlus.service.UserService;
import com.example.demo.multi.springBoot.service.TransactionExperimentService;
import com.example.demo.multi.springBoot.service.TransactionExperimentServiceTwo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.NOT_SUPPORTED;

/**
 * @creator: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @createTime: 2020/9/15 14:57:07
 */
@Service
public class TransactionExperimentServiceImpl implements TransactionExperimentService {
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionExperimentServiceTwo transactionExperimentServiceTwo;
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    @Transactional
    public void firstUpdateOne(){
        QueryWrapper<User> qw = new QueryWrapper<User>().eq("name", "tom");
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠3秒后");
        userService.updateById(new User().setId(3).setName("one"));
//        userService.list(); // 代码B
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
   @Transactional
    public void secondUpdateOne(){
        try {
            Thread.sleep(4 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userService.getById(3);
        User u = new User().setId(3).setName("second");
        userService.updateById(u);
       /* System.out.println("second 更新成功");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession);
        List<User> users = sqlSession.selectList("com.example.demo.multi.springBoot.mybatisPlus.mapper.UserMapper.selectAll");
//        users = userService.selectAll();
        users.forEach(user -> System.out.println(user.getName() + "---------------------------"));*/
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void methodOne(){
        System.out.println("methodOne：" + TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println(Thread.currentThread().getName());
        User u = userService.getById(225);
        System.out.println(Thread.currentThread().getName() + "-----------------1");
        u.setName("m1");
        userService.updateById(u);

        u.setId(226);
        userService.updateById(u);
        System.out.println(Thread.currentThread().getName() + "-----------------2");
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        u.setId(3);
        userService.updateById(u);
        System.out.println(Thread.currentThread().getName() + "-----------------3");
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void methodTwo(){
        System.out.println("m2----" + System.currentTimeMillis());
        User u = userService.getById(3);
        u.setName("m2");
        System.out.println(Thread.currentThread().getName() + "-----------------A");
        userService.updateById(u);
        try {
            Thread.sleep(15_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        u.setId(225);
        System.out.println(Thread.currentThread().getName() + "-----------------B");
        userService.updateById(u);
    }

    @Transactional(propagation = NOT_SUPPORTED)
    public void methodThree(){
        System.out.println("methodThree：" + TransactionSynchronizationManager.getCurrentTransactionName());
        User u = new User().setName("methodThree").setAge(1);
        userService.save(u);
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User getOneById(int id){
        System.out.println("进入方法getOneById-------------------------------");
        User user = userService.getById(id);
        try {
            System.out.println("getOneById阻塞开始-------------------------------");
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getOneById阻塞结束-------------------------------");
        return user;
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void testRepeatedlyRead(){
        System.out.println("testRepeatedlyRead：" + TransactionSynchronizationManager.getCurrentTransactionName());
        List<User> users = userService.selectAll();
        users.forEach(u -> System.out.println(u.getName() + "---------------------------"));

        System.out.println("休眠开始。。。。。");
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("休眠结束。。。。。");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println(sqlSession);
        users = sqlSession.selectList("com.example.demo.multi.springBoot.mybatisPlus.mapper.UserMapper.selectAll");
//        users = userService.selectAll();
        users.forEach(u -> System.out.println(u.getName() + "---------------------------"));
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public List<User> getByName(String name){
        System.out.println("进入方法getByName---------------------------");
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("name", name);
        List<User> users = userService.list(qw);
        try {
            System.out.println("阻塞2s---------------------------------");
            Thread.sleep(2_000);
            System.out.println("阻塞结束---------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void  updateById(User user){
        System.out.println("进入方法updateById---------------------------------");
        if (user.getId() == null || user.getId() < 1){
            return;
        }
        userService.updateById(user);
        System.out.println("updateById执行成功---------------------------------");
    }
}
