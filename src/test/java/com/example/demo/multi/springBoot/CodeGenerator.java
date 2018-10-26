package com.example.demo.multi.springBoot;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis-plus代码生成器
 *
 * @author: LiHongxing
 * @email: lihongxing@bluemoon.com.cn
 * @date: Create in 2018/10/26 8:54
 * @modefied:
 */
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        /*全局配置*/
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/test/java");
        gc.setAuthor("LiHongxing");
        gc.setOpen(false);
        /* 打开二级缓存，因为自动生成的代码操作的基本都是单表，对于单表，使用二级缓存是比较明智的选择 */
        gc.setEnableCache(true);
        gc.setFileOverride(true);
        gc.setServiceName("%Service");
        autoGenerator.setGlobalConfig(gc);

        /*数据源配置*/
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/4mybatis_plus?seUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("3");
        autoGenerator.setDataSource(dsc);

        /*包配置*/
        PackageConfig pc = new PackageConfig();
        // pc.setModuleName("模块名");
        pc.setParent("com.example.demo.multi.springBoot");
        /*设置每个模块的包名*/
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setXml("mapper.xml");
        pc.setController("controller");
        autoGenerator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                /*自定义xml文件名称*/
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        /*配置实体类的超类*/
        // strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        /*是否使用lombok工具*/
        strategy.setEntityLombokModel(true);
        /*使用restful风格*/
        strategy.setRestControllerStyle(true);
        /*配置controller的超类*/
        // strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        /* 包含的表名，也可以使用exclude（排除表名），但是include/exclude只能二选一 */
        /*支持正则*/
        strategy.setInclude("user");
        /* 实体公共字段 */
        // strategy.setSuperEntityColumns("id");
        // strategy.setControllerMappingHyphenStyle(true);
        /*表格前缀*/
        // strategy.setTablePrefix(pc.getModuleName() + "_");
        /*去除“is_”前缀*/
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.entityTableFieldAnnotationEnable(true);
        strategy.setLogicDeleteFieldName("del_flag");
        autoGenerator.setStrategy(strategy);
        /*设置模板*/
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        autoGenerator.execute();
    }
}
