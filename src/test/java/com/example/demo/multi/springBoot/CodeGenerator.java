package com.example.demo.multi.springBoot;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

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
    private static final String moduleName = "sys";
    private static final String[] include_tables = {"^sys_.*"};
    private static final String logicDeleteField = "del_flag";
    private static final String author = "LiHongxing";
    private static final String sourcesDir = "/src/main/java";
    private static final String serviceName = "%sService";

    private static final String databaseUrl = "jdbc:mysql://localhost:3306/4mybatis_plus?seUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static final String databaseUsername = "root";
    private static final String databasePassword = "3";

    private static final String entityPackageName = "entity";
    private static final String servicePackageName = "service";
    private static final String serviceImplPackageName = "service.impl";
    private static final String mapperPackageName = "mapper";
    private static final String xmlPackageName = "mapper.xml";
    private static final String controllerPackageName = "controller";

    private static final String mainPackagePath = "com.example.demo.multi.springBoot.mybatisPlus";
    private static final String mainPackageDir = "com/example/demo/multi/springBoot/mybatisPlus";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        /*全局配置*/
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + sourcesDir);
        gc.setAuthor(author);
        gc.setOpen(false);
        /* 打开二级缓存，因为自动生成的代码操作的基本都是单表，对于单表，使用二级缓存是比较明智的选择 */
        gc.setEnableCache(true);
        gc.setFileOverride(true);
        /* 定义service文件名，“%s”表示entityName，也就是实体名，“%S（大写）”表示大写的实体名，如：UserInfo→USERINFO */
        /* entity、 mapper、xml、serviceImpl、controller都可采用类似的方法自定义名称*/
        gc.setServiceName(serviceName);
        autoGenerator.setGlobalConfig(gc);

        /*数据源配置*/
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(databaseUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(driverClassName);
        dsc.setUsername(databaseUsername);
        dsc.setPassword(databasePassword);
        autoGenerator.setDataSource(dsc);

        /*包配置*/
        PackageConfig pc = new PackageConfig();
        if (StringUtils.isNotBlank(moduleName)) {
            pc.setModuleName(moduleName);
        }
        pc.setParent(mainPackagePath);
        /*设置每个模块的包名*/
        if (StringUtils.isNotBlank(entityPackageName)) {
            pc.setEntity(entityPackageName);
        }
        if (StringUtils.isNotBlank(servicePackageName)) {
            pc.setService(servicePackageName);
        }
        if (StringUtils.isNotBlank(serviceImplPackageName)) {
            pc.setServiceImpl(serviceImplPackageName);
        }
        if (StringUtils.isNotBlank(mapperPackageName)) {
            pc.setMapper(mapperPackageName);
        }
        if (StringUtils.isNotBlank(xmlPackageName)) {
            pc.setXml(xmlPackageName);
        }
        if (StringUtils.isNotBlank(controllerPackageName)) {
            pc.setController(controllerPackageName);
        }
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
                String xmlFileName = tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                StringBuffer sb = new StringBuffer();
                sb.append(gc.getOutputDir()).append("/").append(mainPackageDir).append("/");
                if (StringUtils.isNotBlank(pc.getModuleName())){
                    sb.append(pc.getModuleName()).append("/");
                }
                sb.append(StringUtils.replace(xmlPackageName, ".", "/")).append("/");
                sb.append(xmlFileName);
                /*自定义xml文件名称*/
                return sb.toString();
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
        strategy.setInclude(include_tables);
        /* 实体公共字段 */
        // strategy.setSuperEntityColumns("id");
        // strategy.setControllerMappingHyphenStyle(true);
        /*表格前缀*/
        strategy.setTablePrefix(pc.getModuleName() + "_");
        /*去除“is_”前缀*/
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.entityTableFieldAnnotationEnable(true);
        strategy.setLogicDeleteFieldName(logicDeleteField);
        autoGenerator.setStrategy(strategy);
        /*设置模板*/
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        autoGenerator.execute();
    }
}
