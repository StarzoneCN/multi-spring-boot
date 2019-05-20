package com.example.demo.multi.springBoot.config.mybatis;

import com.baomidou.mybatisplus.extension.spring.MybatisMapperRefresh;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

import static com.example.demo.multi.springBoot.constant.StringConstants.MYBATIS_MAPPER_LOCATION;

/**
 * @description: mybatis plus 的dev环境配置;
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2019-05-20 9:47:04
 * @modefied:
 **/
@Configuration
@Profile("dev")
public class MybatisPlusDevConfiguration {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;
    /**
     * 延迟加载时间
     */
    @Value("${mybatis-plus.delay-seconds}")
    private int delaySeconds;
    /**
     * 刷新间隔时间
     */
    @Value("${mybatis-plus.sleep-seconds}")
    private int sleepSeconds;
    /**
     * 刷新间隔时间
     */
    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;

    /**
     * 支持mapper.xml的热加载，只应用于开发环境
     */
    @Bean
    @Primary
    public MybatisMapperRefresh mybatisMapperRefresh(){
        Resource[] resources = new Resource[1];
        resources[0] = new ClassPathResource(MYBATIS_MAPPER_LOCATION);
        MybatisMapperRefresh mybatisMapperRefresh = new MybatisMapperRefresh(resources,
                sqlSessionFactory, true);
        return mybatisMapperRefresh;
    }

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
