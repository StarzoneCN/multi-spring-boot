# multi-spring-boot
在spring-boot基础上添加实现各种功能（每个分支代表一个学习研究方向）
* master: 新建的spring-boot项目，包含web模块
* Test实体对应的表格是测试用表，目的是搭建起持久框架，方便试验事务。可根据Test实体建立表格

#### 总结：<br/>
* RuntimeException能够触发事务回滚
* 如果既想回滚事务，又想返回错误提示，可以使用<br/>
`TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();`<br/>
手动触发事务回滚<br/>
<br/>
* MANDATORY策略：当事务回滚时会抛出IllegalTransactionStateException;
* REQUIRES_NEW策略：如果RE异常没有在调用方（方法）中捕获（try...catch...）调用方的事务也会被触发