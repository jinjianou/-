# 为什么要分库分表

如果业务量剧增(qps)，数据库可能会出现性能瓶颈，这时候我们就需要考虑拆分数据库。  提升性能,提高可用性

1. 换数据库 缓存 Nosql
2. 慢sql,索引,字段
3. 读写分离
4. 分库分表

# 什么是分库分表

单库单表的数据分散到n个库或n个表



# 分库分表的几种方式

 分库(垂直|水平):

​	垂直:按业务拆分总库->多个库

​	水平拆库:按照某种规则 将某个库拆成相同结构的若干个库

 分表(垂直|水平):

​	mysql单表最大容量是4GB(3.22以前)/64pb(对记录数没限制),推荐500w行或2GB考虑分表



​	垂直:按列拆分(确定关联列),本质也是按业务拆分

​	水平: 按照某种规则 将某个表拆成相同结构的若干个表(保证均衡)



垂直 特点:

1. 每个库(表)结构不一样
2. 每个库(表)数据至少有一列是一样的
3. 每个库(表)的并集是全量数据

垂直 优点:

1. 拆分后业务清晰(专库专用)
2. 数据维护简单

垂直 缺点:

1. 单表数据量大,写读压力大(读写速度变慢) 

   磁盘 扇区 512字节 文件系统 块 4K  innodb存储引擎 页 16K

   **当一个表的索引无法放入到内存中会导致性能下降**(需要把部分索引装载到磁盘中，此时出现了性能的明显下降)，而与实际记录的条数无关





水平特点:

1. 每个库(表)结构一样
2. 每个库(表)数据是不一样的
3. 每个库(表)的并集是全量数据

水平优点:

1. 单库(表)数据量保持一定的数据量,有助于性能的提升
2. 提高了系统的稳定性和负载能力

水平缺点:

1. 数据扩容有难度,维护量大
2.  分片事务的一致性的问题 ,部分业务无法关联jion,只能通过java程序接口调用



场景:

​	订单数据库应该怎么拆分? 分情况:

​	前端(我的订单 订单详情 以我为中心) userid

​	后台(统计 以订单为维度) orderid





# 分库分表会带来什么问题

1. 分布式事务
2. 跨库join
3. 分布式全局唯一id





# 批量导入导出

通过JDBC执行[sql语句](https://so.csdn.net/so/search?q=sql语句&spm=1001.2101.3001.7020)时，**update和delete执行sql的语句是一条一条发往数据库执行**

但是！**数据库的处理速度是很快，单次吞吐量是很大，执行效率极高**

**addBatch()是把若干sql语句装载到一起，然后一次性传送到数据库执行，即是批量处理sql数据的**。

## 批量插入

方式一: 统一插   >30min

```
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pagehelper?characterEncoding=UTF-8&useUnicode=true&useSSL=false&serverTimezone=UTC"
,"root","123456");
PreparedStatement statement = conn.prepareStatement("insert into t_vue(title) values(?)");
long start = System.currentTimeMillis();
for (int i = 0; i < 50000; i++) {
    statement.setString(1,String.valueOf(i));
    statement.addBatch();
}
System.out.println(System.currentTimeMillis()-start);
statement.executeBatch();
System.out.println(System.currentTimeMillis()-start);
```

方式二: 拆分插  >30min

```
for (int j = 0; j < 50; j++) {
    for (int i = 0; i < 1000; i++) {
        statement.setString(1,String.valueOf(j*1000+i));
        statement.addBatch();
    }
    statement.executeBatch();
}
```

发现并没有明显的提升,经过分析

1. addBatch后并没有清空导致内存中数据不断增加 statement.clear();

   源码分析

   ```
   this.batchedArgs.add(batch);//batch:ClientPreparedQueryBindings   batchedArgs List
   
   
   ```

   

2. executeBatch

   ```
      if (!this.batchHasPlainStatements && (Boolean)this.rewriteBatchedStatements.getValue()) {
                           if (this.getParseInfo().canRewriteAsMultiValueInsertAtSqlLevel()) {
                               var3 = this.executeBatchedInserts(batchTimeout);
                               return var3;
                           }
   
                           if (!this.batchHasPlainStatements && this.query.getBatchedArgs() != null && this.query.getBatchedArgs().size() > 3) {
                               var3 = this.executePreparedBatchAsMultiStatement(batchTimeout);
                               return var3;
                           }
                       }
   
                       var3 = this.executeBatchSerially(batchTimeout);
                       return var3;
   ```

   分为以下三种情况

   1. !this.batchHasPlainStatements && (Boolean)this.rewriteBatchedStatements.getValue() 
      - canRewriteAsMultiValueInsertAtSqlLevel -> executeBatchedInserts
      - !canRewriteAsMultiValueInsertAtSqlLevel&&!this.batchHasPlainStatements && this.query.getBatchedArgs() != null && this.query.getBatchedArgs().size() > 3  ->  executePreparedBatchAsMultiStatement

   b. else 

   ​		executeBatchSerially

   由此可知,没有rewriteBatchedStatements相当于走单行串行插入,效率极低

   **url 添加&rewriteBatchedStatements=true**

   走executeBatchedInserts

   Rewrites the already prepared statement into  multi-value insert statement of 'statementsPerBatch'

    就是拼装成insert into xxx(xxx) values(xxx),(xxx)....

   ```
   for (int j = 0; j < 50; j++) {
       for (int i = 0; i < 1000; i++) {
           statement.setString(1,String.valueOf(j*1000+i));
           statement.addBatch();
       }
       statement.executeBatch();
       statement.clearBatch();
   }
   ```

   47s

   不拆分依旧>30min