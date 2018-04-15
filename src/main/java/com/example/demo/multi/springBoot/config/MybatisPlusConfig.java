package com.example.demo.multi.springBoot.config;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.SqlExplainInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Configuration
public class MybatisPlusConfig {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private MybatisProperties properties;

    @Autowired
    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() {
        MybatisSqlSessionFactoryBean mybatisPlusFactory = new MybatisSqlSessionFactoryBean();
        mybatisPlusFactory.setDataSource(dataSource);
        mybatisPlusFactory.setVfs(SpringBootVFS.class);
        if (StringUtils.hasText(this.properties.getConfigLocation())) {
            mybatisPlusFactory.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
        }
        mybatisPlusFactory.setConfiguration(properties.getConfiguration());
        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisPlusFactory.setConfiguration(mc);
        if (this.databaseIdProvider != null) {
            mybatisPlusFactory.setDatabaseIdProvider(this.databaseIdProvider);
        }
        if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
            mybatisPlusFactory.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
        }
        if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
            mybatisPlusFactory.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
        }
        if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
            mybatisPlusFactory.setMapperLocations(this.properties.resolveMapperLocations());
        }

        SqlExplainInterceptor sqlExplainInterceptor = new SqlExplainInterceptor();
        sqlExplainInterceptor.setStopProceed(false);
        PaginationInterceptor pagination = new PaginationInterceptor();
        // 具体参考自己设置，参考 xml 参数说明或源码注释
        mybatisPlusFactory.setPlugins(new Interceptor[]{
                pagination,
                sqlExplainInterceptor
        });
        return mybatisPlusFactory;
    }

}
