# multi-spring-boot
在spring-boot基础上添加实现各种功能（每个分支代表一个学习研究方向）
* master: 新建的spring-boot项目，包含web模块
* 在不同环境下运行：
    ```markdown
    java -jar LearnSpringBoot-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
    或者
    -Dspring.profiles.active=prod
    或者
    在基本配置文件（application.yml)中配置spring.profiles.active=dev 
    ```
    还可以使用`@Profile("dev")`注解选择不同的service实现（如：例子中的`ProfileService`接口）