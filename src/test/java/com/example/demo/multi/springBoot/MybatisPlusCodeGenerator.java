package com.example.demo.multi.springBoot;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.example.demo.multi.springBoot.util.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: MP代码生成工具
 * @author: Li Hongxing
 * @email: lihongxing@bluemoon.com.cn
 * @create: 2019-05-20 11:37:02
 * @modefied:
 **/
public class MybatisPlusCodeGenerator {
    private static final String moduleName = "auth";
    private static final String[] include_tables = {"user"}; // 需要生成代码的表格，支持正则
    private static final String fieldPrefix = "is"; // 字段前缀
    private static final String logicDeleteField = "del_flag";
    private static final String author = "Li Hongxing";
    private static final String sourcesDir = "/src/java/java";
    private static final String classpath = "/src/java/resources";
    private static final String serviceName = "%sDao";
    private static final String serviceImplName = "%sDaoImpl";

    private static final String databaseUrl = "jdbc:mysql://localhost:3306/mybatisplus?seUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private static final String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static final String databaseUsername = "root";
    private static final String databasePassword = "3";

    private static final String entityPackageName = "entity";
    private static final String servicePackageName = "dao";
    private static final String serviceImplPackageName = "dao.impl";
    private static final String mapperPackageName = "mapper";
    private static final String xmlPackageName = "mapper";
    private static final String controllerPackageName = "controller";

    private static final String mainPackagePath = "com.example.demo.multi.springBoot.dao";
    private static final String mainPackageDir = "com/example/demo/multi/springBoot/dao";

    @Test
    public void generate() {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        /*全局配置*/
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + sourcesDir);
        gc.setAuthor(author);
        /*是否打开输出目录*/
        gc.setOpen(false);
        /* 打开二级缓存，因为自动生成的代码操作的基本都是单表，对于单表，使用二级缓存是比较明智的选择 */
        gc.setEnableCache(true);
        gc.setFileOverride(true);
        /* 定义service文件名，“%s”表示entityName，也就是实体名，“%S（大写）”表示大写的实体名，如：UserInfo→USERINFO */
        /* entity、 mapper、xml、serviceImpl、controller都可采用类似的方法自定义名称*/
        gc.setServiceName(serviceName);
        gc.setServiceImplName(serviceImplName);
        /*是否开启activeRecord模式*/
        gc.setActiveRecord(false);
        /*是否生成baseResultMap*/
        gc.setBaseResultMap(true);
        /* 设置日期字段类型 */
        gc.setDateType(DateType.TIME_PACK);
        autoGenerator.setGlobalConfig(gc);

        /*数据源配置*/
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(databaseUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(driverClassName);
        dsc.setUsername(databaseUsername);
        dsc.setPassword(databasePassword);
        /*设置库查询sql*/
        dsc.setDbQuery(new MySqlQuery());
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

        // 注入配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {

            /**
             * 设置mapper.xml文件全路径
             * @param tableInfo
             * @return
             */
            @Override
            public String outputFile(TableInfo tableInfo) {
                String xmlFileName = tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                StringBuffer sb = new StringBuffer();
                sb.append(projectPath).append(sourcesDir).append("/").append(mainPackageDir);
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
        injectionConfig.setFileCreate((configBuilder, fileType, filePath) -> {
            switch (fileType) {
                case CONTROLLER:
                    return false;
                default:
                    return true;
            }
        });
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        /*是否跳过视图*/
        strategy.setSkipView(true);
        /*数据库表映射到实体的命名策略*/
        strategy.setNaming(NamingStrategy.underline_to_camel);
        /*数据库表字段映射到实体的命名策略, 未指定按照 naming 执行*/
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        /*配置实体类的超类*/
        // strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        /*使用restful风格*/
        strategy.setRestControllerStyle(true);
        /*配置controller的超类*/
        // strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        /* 包含的表名，也可以使用exclude（排除表名），但是include/exclude只能二选一 */
        /*支持正则*/
        strategy.setInclude(include_tables);
        /*表格前缀，这会自动去掉前缀，如：表名sys_user, 生成的实体是User*/
        strategy.setTablePrefix(pc.getModuleName() + "_");
        /*效果与tablePrefix类似*/
        strategy.setFieldPrefix(fieldPrefix + "_");
        /*去除“is_”前缀*/
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        /*(实体父类)公共字段，如果设置，那么entity中就不会生成这些字段的代码*/
        // strategy.setSuperEntityColumns("create_time");
        /*是否将表的字段名以常量的形式，放在entity类中*/
        strategy.setEntityColumnConstant(true);
        /*是否使用builder模式，如果使用了lombok模式，entityBuilderModel设置将失效*/
        strategy.setEntityBuilderModel(false);
        /*是否使用lombok工具*/
        strategy.setEntityLombokModel(true);
        strategy.entityTableFieldAnnotationEnable(true);
        /*逻辑删除标志字段*/
        strategy.setLogicDeleteFieldName(logicDeleteField);
        /*自动填充配置*/
        strategy.setTableFillList(new ArrayList<TableFill>(2){{
            this.add(new TableFill("create_time", FieldFill.INSERT));
        }});
        autoGenerator.setStrategy(strategy);
        /*设置模板*/
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());

        autoGenerator.execute();
    }
}
