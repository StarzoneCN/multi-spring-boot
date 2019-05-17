# 项目架构详细介绍
### 1. Redis
#### 1.1 配置
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

#### 1.2 应用
* 默认配置：由于RedisTemplate<K,V>是泛型，所以使用redis时，分两种情况
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
