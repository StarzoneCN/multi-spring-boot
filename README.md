# 项目架构详细介绍
> `application-*.properties`和`application-*.yml`：
>   * 由于@Value注解不能解析yaml文件，所以增加*.properties文件；
>   * 建议: 主要配置还是放在`application-*.yml`文件中；
## 1. Redis
### 1.1 配置
* dev环境：application-dev.yml
  ```yml
  spring:
    redis:
      host: 192.168.3.128
      port: 6379
      database: 1
  ```
* produce环境：application-produce.yml
  ```yml
  spring:
    redis:
      host: 192.168.3.128
      port: 6379
      database: 1
  ```

### 1.2 应用
由于RedisTemplate<K,V>是泛型，所以使用redis时，分两种情况
  * RedisTemplate<String, String>：在`RedisConfiguration`配置类中，向IOC注入了`ValueOperations`、`ZSetOperations`、`SetOperations`、`ListOperations`等redis操作封装类（beanName为`string**Operations`）
    ```java
    /* 按beanName引入, beanName为：string**Operations */
    @Autowired
    @Qualifier("stringValueOperations")
    private ValueOperations valueOperations;
    ```
  * RedisTemplate<K,V>：此情况下，如果执行redis的set命令，`RedisTemplate`会先将`K/V`进行序列化，然后再保存进redis，比如（name=10）redis中最终的结果就是`\xAc\xED\x00\x05t\x00\x04name`=`\xAC\xED\x00\x05t\x00\x0210`。
  因为此时是泛型，不能注入到IOC容器中，所以`<K,V>`非`<String, String>`的情况，自行new一个`RedisTemplate`对象
    ```java
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    public RedisTemplate<Integer, Integer> redisTemplate() {
        RedisTemplate template = new RedisTemplate<Integer, Integer>();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }
    ```

## 3. Filter
* 相关类：`AuthFilter`
### 3.1 过滤地址
```java
@WebFilter(urlPatterns = {"需要执行过滤器的路径1", "路径2"})
public class TokenFilter implements Filter {

}
```


## 4. Mybatis/plus
更多关于MP的知识，请移步[Mybatis-Plus知识分享][]或[Mybatis-Plus官方文档][]
### 4.1 每类文件的存放位置：
项目中使用`Dao`表示数据库层，所以数据库相关的类一般放在`dao`包下，如果项目中包含多个模块（module），且数据库表也有模块的概念，则表对应的各种类型文件放在`dao.moduleName`子包里：
* `mapper.java`/`mapper.xml`: `主包路径.dao[.moduleName].mapper`
* `**Dao.java`: 数据库操作service层，存放位置为`主包路径.dao[.moduleName]`
* table实体类：`主包路径.dao[.moduleName].entity`

### 4.2 删除
> **重要**：如无特殊情况，所有表只做逻辑删除

### 4.3 枚举注入
参考文档：[通用枚举][]
枚举类位置：
```yaml
mybatis-plus:
    # 支持统配符 * 或者 ; 分割
    typeEnumsPackage: 主包路径.constant.Enum
```


## 5. 日期/时间
> **注意**： 本系统所有日期/时间，使用jdk8的日期时间框架（java.time.\*）

* 请使用项目工具类：`com.example.demo.multi.springBoot.util.DateTimeUtils`





















[Mybatis-Plus知识分享]: http://confluence.admin.bluemoon.com.cn/display/SCM/Mybatis-plus
[Mybatis-Plus官方文档]: https://baomidou.gitee.io/mybatis-plus-doc/#/quick-start
[通用枚举]: https://mp.baomidou.com/guide/enum.html#通用枚举
