package com.example.demo.multi.springBoot.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisMapperRefresh;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
    private String mapperLocations = "classpath:mybatis/mapper/*Mapper.xml";
    private boolean enabled = true;

    // @Bean
    public MybatisMapperRefresh mybatisMapperRefresh(){
        return new MybatisMapperRefresh(new Resource[]{new ClassPathResource(mapperLocations)}, sqlSessionFactory,  enabled);
    }
}
