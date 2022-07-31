# 项目搭建

## 问题

问题：2020.1版本以后  git标签页取代Version Control  标签页，导致其下没有default changelist,默认在git [commit](https://so.csdn.net/so/search?q=commit&spm=1001.2101.3001.7020)时才会看到 

解决方法： Settings/Version Control/Commit将Use non-modal commit interface取消勾选 



问题：Logon failed, use ctrl+c to cancel basic credential prompt

解决：第一次是输入[github](https://so.csdn.net/so/search?q=github&spm=1001.2101.3001.7020)的登陆用户名和密码 ，第二次是输入 usernuserame（用户名） 和生成的tokens（密码） 。这两个信息在账户settings/developer settings/personal access tokes/generate new token.在note下填入username同时勾选权限，然后生成token 



问题：[OpenSSL](https://so.csdn.net/so/search?q=OpenSSL&spm=1001.2101.3001.7020) SSL_read: SSL_ERROR_SYSCALL, errno 10054Failed to connect to github 

1. git config http.sslVerify "false" 
2. git config --global --unset http.proxy 



## 日志修改

1. sb 2.3以前能识别logback,xml 之后只能识别**logback-spring.xml**

```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- 修改一下路径 ./指代当前项目-->
    <property name="PATH" value="./log"></property>

    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
<!--            <Pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %highlight(%-5level) %blue(%-50logger{50}:%-4line) %thread %green(%-18X{LOG_ID}) %msg%n</Pattern>-->
            <Pattern>%d{dd HH:ss.SSS} %highlight(%-5level) %blue(%-30logger{30}:%-4line) %thread %green(%-18X{LOG_ID}) %msg%n</Pattern>
        </encoder>
    </appender>

    <appender name="TRACE_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/trace.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/trace.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-18X{LOG_ID}) %msg%n</pattern>
        </layout>
    </appender>

    <appender name="ERROR_FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${PATH}/error.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <FileNamePattern>${PATH}/error.%d{yyyy-MM-dd}.%i.log</FileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
        <layout>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-50logger{50}:%-4line %green(%-18X{LOG_ID}) %msg%n</pattern>
        </layout>
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>ACCEPT</onMatch>
            <onMismatch>DENY</onMismatch>
        </filter>
    </appender>

    <root level="ERROR">
        <appender-ref ref="ERROR_FILE" />
    </root>

    <root level="TRACE">
        <appender-ref ref="TRACE_FILE" />
    </root>

    <root level="INFO">
        <appender-ref ref="STDOUT" />
    </root>
</configuration>

```

2. 新增控制台输出，如启动成功等说明

```
    public static final  Logger LOG=LoggerFactory.getLogger(WikiKnowledgeHubApplication.class);
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(WikiKnowledgeHubApplication.class, args);
        Environment env = context.getEnvironment();
        LOG.info("启动成功");
        //配置文件没配server.port为null而非默认值
        LOG.info("端口： {}",env.getProperty("server.port"));
    }
```

3. 自定义banner

   在classpath下 新建banner.txt

   patorjk.com 自定义banner样式

## http client

idea 自带的http 测试接口工具(**需要项目启动**)

模块下建一个http目录，新建xxx.http(gtr live template/http request自带的代码块生成命令)

```
test.http:

GET http://localhost:8080/test/get
Accept: application/json

###

POST http://localhost:8080/test/post HTTP/1.1
#Content-Type: application/x-www-form-urlencoded
#id=99&content=new-element    不空行是header  空行是query or data
Content-Type: application/json

{
"name":"jinjianou"
}

> {%
client.test("test-post",function() {
  console.log(JSON.stringify(response.body));
  client.assert(response.status===200,"请求失败")
})
 %}

###
```



## 配置文件

properties和yaml互相转化  toyaml.com  

bootstrap.properties 用于动态配置，线上实时修改实时生效，一般可配合nacos使用。**但单个sb不会读取该配置吗，要sc架构下的sb应用才会读**





# 后端架构完善与接口开发

1. 数据库准备

   - 本地

     - UTF后的数字代表编码的最小单位，如UTF-8表示最小单位1字节 

       ASCII字节（0x00-0x7F）， 非ASCII字符的多字节串   第一个字节总是在0xC0到0XFD的范围里 ，并指出这个字符包含多少个字节；其余字节都在0x80到0xBF范围里 (多字节)

       UTF-8编码字符理论上可以最多到4个字节长 

       **数据库里的utf8是伪utf8，固定三个字节，不支持表情**

       **utf8mb4是真正的utf8,支持表情**

     - 新建专门访问这个库的用户

       用户名

       主机

       ​	% 别的机器也能通过这个账户访问

       ​	localhost 只有本地能访问

       插件

       密码

       权限-添加权限

       Navicat mysql报错 1142 - SELECT command denied to user ‘xxx‘@‘localhost‘ for table ‘user‘

       ​	通过root给新用户的服务器权限

   - 阿里云

     云服务器（Elastic Compute Service，ECS）的标准定义是指一种简单高效、安全可靠、处理能力可弹性伸缩的计算服务。 

2. idea关联数据库(可选)

   1. idea配置数据库连接

      这里schema与database等价

      console可以编写执行sql

   2. 数据库插件使用

   3. 增加数据库脚本

      1配好之后，可以在sql右键execute

3. mybatis

4. mybatis generator

5. 接口开发

   - 电子书表结构设计

   ```
   drop table if exists ebook;
   create table ebook(
   	id int primary key auto_increment,
   	title varchar(50) comment '名称',
   	cover varchar(500) comment '封面',
   	content_count int comment '课程小节数',
   	view_count int comment '阅读数' default 0,
   	vote_count int comment '点赞数' default 0,
   	description varchar(200) comment '课程描述',
   	big_category_id int,
   	detatil_category_id int
   )engine=innodb,default charset=utf8,comment='线上教程';
   
   insert into ebook(title,content_count,description)
   values('JavaScript 入门教程',80,'零基础学习 Javascript'),
   ('Vue 入门教程',39,'零基础入门 Vue 开发'),
   ('TypeScript 入门教程',38,'使用 TypeScript 进行 OOP 编程'),
   ('Html5 入门教程',25,'通向 WEB 技术世界的钥匙'),
   ('Java 入门教程',50,'面向就业的最佳首选语言'),
   ('Android 入门教程',59,'零基础 Android 入门，精华知识点提取');
   
   
   
   ```

   - BeanUtils

     copyProperties(source,target)

     ```
     public List<EBookResp> getEbooksByReqParam(EbookEeq ebookEeq){
             EbookExample example = new EbookExample();
             EbookExample.Criteria criteria = example.createCriteria();
             if (!StringUtils.isBlank(ebookEeq.getTitle())) {
                 criteria.andTitleLike("%"+ebookEeq.getTitle()+"%");
             }
             if (!StringUtils.isBlank(ebookEeq.getDescription())) {
                 example.or().andDescriptionLike("%"+ebookEeq.getDescription()+"%");
             }
             List<Ebook> ebookList = ebookMapper.selectByExample(example);
             List<EBookResp> eBookResps = new ArrayList<>();
             for (Ebook ebook : ebookList) {
                 EBookResp eBookResp = new EBookResp();
                 BeanUtils.copyProperties(ebook,eBookResp);
                 eBookResps.add(eBookResp);
             }
             return eBookResps;
         }
     ```

     封装类 CopyUtils

     ```
     public class CopyUtils {
         //复制单值
         public static <T> T copy(Object source,Class<T> clazz){
             if(source==null){
                 return null;
             }
             T obj;
             try {
                 obj = clazz.newInstance();
             } catch (Exception e) {
                 e.printStackTrace();
                 return null;
             }
             BeanUtils.copyProperties(source,obj);
             return obj;
         }
     
         //复制list
         public static <T> List<T> copyList(List source, Class<T> clazz){
             List<T> rs = new ArrayList<>();
             if(!CollectionUtils.isEmpty(source)){
                 for (Object element : source) {
                     rs.add(copy(element,clazz));
                 }
             }
             return rs;
         }
     }public class CopyUtils {
         //复制单值
         public static <T> T copy(Object source,Class<T> clazz){
             if(source==null){
                 return null;
             }
             T obj;
             try {
                 obj = clazz.newInstance();
             } catch (Exception e) {
                 e.printStackTrace();
                 return null;
             }
             BeanUtils.copyProperties(source,obj);
             return obj;
         }
     
         //复制list
         public static <T> List<T> copyList(List source, Class<T> clazz){
             List<T> rs = new ArrayList<>();
             if(!CollectionUtils.isEmpty(source)){
                 for (Object element : source) {
                     rs.add(copy(element,clazz));
                 }
             }
             return rs;
         }
     }
     
     ```

## 半自动和全自动ORM

 区别在于用户需不需要定义SQL 语句

hibernate用户不需要，都封装好了，所以是全自动

mybatis则不然

 

### Vue3+Vue Cli项目搭建

Ant design of vue

https://2x.antdv.com/docs/vue/introduce-cn/ 支持vue3

https://element-plus.org/zh-CN/guide/quickstart.html  支持vue3

https://www.cnblogs.com/jin-zhe/p/9985436.html