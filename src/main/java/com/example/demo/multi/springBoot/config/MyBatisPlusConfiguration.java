package com.example.demo.multi.springBoot.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
// import com.baomidou.mybatisplus.extension.spring.MybatisMapperRefresh;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

/**
 * mybatis-plus配置类
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/10/25 20:30
 * @modefied:
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.example.demo.multi.springBoot.*.mapper")
public class MyBatisPlusConfiguration {

    /*逻辑删除*/
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /*分页支持*/
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /*热加载*/
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    private String mapperLocations = "mybatis/mapper/*Mapper.xml";
    private boolean enabled = true;

    // @Bean
  /*  public MybatisMapperRefresh mybatisMapperRefresh(){
        *//** MybatisMapperRefresh构造参数说明：
         * sqlSessionFactory:session工厂
         * mapperLocations:mapper匹配路径
         * enabled:是否开启动态加载  默认:false
         * delaySeconds:项目启动延迟加载时间  单位：秒  默认:10s
         * sleepSeconds:刷新时间间隔  单位：秒 默认:20s
         *//*
        return new MybatisMapperRefresh(getResources(), sqlSessionFactory, 10, 4,  enabled);
    }*/

    private Resource[] getResources(){
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return resolver.getResources(mapperLocations);
        } catch (IOException e) {
            System.out.println("mapperLocations配置有误");
            e.printStackTrace();
        }
        return null;
    }
}
