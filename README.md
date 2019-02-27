# multi-spring-boot

在 spring-boot 基础上添加实现各种功能（每个分支代表一个学习研究方向）

- master: 新建的 spring-boot 项目，包含 web 模块

## 1. 当前分支

_使用undertow简单实现http2_

### 1.1 搭建步骤

1.  包引入：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
        <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-undertow</artifactId>
</dependency>
```

2. application.yml
```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:keystore.jks
    key-store-password: 密码
    key-password: 密码
    protocol: TLSv1.2
  http2:
    enabled: true
  use-forward-headers: true
```

3. 生成keystore
```shell
keytool -importkeystore -srckeystore C:\projects\test\multi-spring-boot\src\main\resources/keystore.jks -destkeystore C:\projects\test\multi-spring-boot\src\main\resources/keysto
re.jks -deststoretype pkcs12
```

4. 开启HTTP2以及server push功能
```java
@Configuration
public class Http2Config {

    @Bean
    UndertowServletWebServerFactory undertowServletWebServerFactory() {
        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.addBuilderCustomizers(
                builder -> {
                    builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                            .setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH,true);
                });

        return factory;
    }
}
```

5. 编写controller
略

6. 访问地址
浏览器访问controller，如：`https://localhost:8443`
