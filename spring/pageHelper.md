## mybatis-spring

第一种配置：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                        http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
                        http://www.springframework.org/schema/context
                        http://www.springframework.org/schema/context/spring-context-3.0.xsd">
   <bean class="com.zaxxer.hikari.HikariDataSource" id="dataSource">
       <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
       <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/quartz?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true"/>
       <property name="username" value="root"/>
       <property name="password" value="123456"/>
   </bean>

   <bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactory">
       <property name="dataSource" ref="dataSource"/>
       <property name="configLocation" value="classpath:mybatis-config.xml"/>
       <property name="mapperLocations" value="classpath:META-INF/mapping/*.xml"/>
       <property name="typeAliasesPackage" value="org.example.mybatis.domain"/>
   </bean>

    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName"  value="sqlSessionFactory"/>
        <property name="basePackage" value="org.example.mybatis.mapper"/>
    </bean>

</beans>
```

* 分析

```
public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware
/*BeanDefinitionRegistryPostProcessor that searches recursively starting from a base package for interfaces and registers them as {@code MapperFactoryBean}.*/

public class MapperFactoryBean<T> extends SqlSessionDaoSupport implements FactoryBean<T>
class SqlSessionDaoSupport
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    if (this.sqlSessionTemplate == null || sqlSessionFactory != this.sqlSessionTemplate.getSqlSessionFactory()) {
      this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    }
  }
/*这里，spring容器中某个MapperFactoryBean的字段需要依赖注入SqlSessionFactory实例的时候，会调用SqlSessionFactoryBean(BeanFactory)的getObject方法，返回SqlSessionFactory实例。
*/
或者
/*
显示调用getBeanO(T.class)也会调用FactoryBean<T>的getObject方法
<bean class="org.example.mybatis.config.MyFactoryBean" id="user"/>
getBean("user"):User getBean("&user"):FactoryBean<User>
*/
public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory, ExecutorType executorType,PersistenceExceptionTranslator exceptionTranslator) {

    notNull(sqlSessionFactory, "Property 'sqlSessionFactory' is required");
    notNull(executorType, "Property 'executorType' is required");

    this.sqlSessionFactory = sqlSessionFactory;
    this.executorType = executorType;
    this.exceptionTranslator = exceptionTranslator;
    this.sqlSessionProxy = (SqlSession) newProxyInstance(
        SqlSessionFactory.class.getClassLoader(),
        new Class[] { SqlSession.class },
        new SqlSessionInterceptor());
  }
/*sqlSessionProxy就是sqlsession的代理对象*/
```

第二种配置

MapperScan或 \<mybatis:mapper-scan>

```java
 @Bean
     public SqlSessionFactory sqlSessionFactory() throws Exception {
       SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
       sessionFactory.setDataSource(dataSource());
       return sessionFactory.getObject();
     }



    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MapperMethod> methodCache) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }
```





## mybatis-generator

```java
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.4.0</version>
</dependency>
```

* 日志

```
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.0-alpha1</version>
</dependency>
<dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-slf4j-impl</artifactId>
    <version>2.12.1</version>
</dependency>
 <dependency>
     <groupId>org.slf4j</groupId>
     <artifactId>slf4j-nop</artifactId>
     <version>2.0.0-alpha0</version>
</dependency>
```

```
appenders = console

appender.console.type = Console
appender.console.name = STDOUT
appender.console.layout.type = PatternLayout
appender.console.layout.pattern = %d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n

rootLogger.level = debug
rootLogger.appenderRefs = stdout
rootLogger.appenderRef.stdout.ref = STDOUT
```

* mybatis-generator.xml:

  具体参数查看(https://blog.csdn.net/weixin_44368212/article/details/109283097)

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <context id="DB2Tables" targetRuntime="MyBatis3">
        <commentGenerator>
            <!-- 是否去除自动生成的注释 -->
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>
        <!-- Mysql数据库连接的信息：驱动类、连接地址、用户名、密码 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/pageHelper?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true"
                        userId="root"
                        password="123456">
        </jdbcConnection>
        <!-- Oracle数据库
            <jdbcConnection driverClass="oracle.jdbc.OracleDriver"
                connectionURL="jdbc:oracle:thin:@127.0.0.1:1521:yycg"
                userId="yycg"
                password="yycg">
            </jdbcConnection>
        -->

        <!-- 默认为false，把JDBC DECIMAL 和NUMERIC类型解析为Integer，为true时
        把JDBC DECIMAL 和NUMERIC类型解析为java.math.BigDecimal -->
        <javaTypeResolver >
            <property name="forceBigDecimals" value="false" />
        </javaTypeResolver>

        <!-- targetProject：生成POJO类的位置 -->
        <javaModelGenerator targetPackage="org.example.mybatis.domain" targetProject=".\src\main\java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
            <!-- 从数据库返回的值被清理前后的空格 -->
            <property name="trimStrings" value="true" />
        </javaModelGenerator>

        <!-- targetProject：mapper映射文件生成的位置 -->
        <sqlMapGenerator targetPackage="META-INF.mapping"  targetProject=".\src\main\resources">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </sqlMapGenerator>

        <!-- targetProject：mapper接口生成的的位置 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="org.example.mybatis.mapper"  targetProject=".\src\main\java">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="false" />
        </javaClientGenerator>

        <!-- 指定数据表 -->
        <table schema="" tableName="generate_demo"/>

        <!-- 有些表的字段需要指定java类型
        <table schema="DB2ADMIN" tableName="ALLTYPES" domainObjectName="Customer" >
          <property name="useActualColumnNames" value="true"/>
          <generatedKey column="ID" sqlStatement="DB2" identity="true" />
          <columnOverride column="DATE_FIELD" property="startDate" />
          <ignoreColumn column="FRED" />
          <columnOverride column="LONG_VARCHAR_FIELD" jdbcType="VARCHAR" />
        </table> -->

    </context>
</generatorConfiguration>
```

* Generator

```
List<String> warnings = new ArrayList<String>();
boolean overwrite = true;
Reader configReader = Resources.getResourceAsReader("mybatis-generator.xml");
ConfigurationParser cp = new ConfigurationParser(warnings);
Configuration config = cp.parseConfiguration(configReader);
DefaultShellCallback callback = new DefaultShellCallback(overwrite);
MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
myBatisGenerator.generate(null);
```

* 使用

```
@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:application.xml")
public class CriateCriteriaTest {
    @Autowired
    private GenerateDemoMapper mapper;

    @Test
    public void test(){
        GenerateDemoExample ex = new GenerateDemoExample();
        
        //andFieldOperator
        //ex.createCriteria().andNameLike("%"+"A"+"%");
        
        //ex.setOrderByClause("id desc");
        
        List<GenerateDemo> res = mapper.selectByExample(ex);
        System.out.println(res);
    }
}
```

## PageHelper

* 分页方式
  * 物理分页

    依赖的是某一物理实体，这个物理实体就是数据库，比如MySQL数据库提供了limit关键字，程序员只需要编写带有limit关键字的SQL语句，数据库返回的就是分页结果

  * 逻辑分页/内存分页

    数据库返回全部数据，通过代码获取分页数据。常见的操作是一次性从数据库中查询出	全部数据并存储到list中，因为list集合存取有序，再根据索引获取指定范围的数据

  * 对比
    * 数据库负担 1>2
    * 内存负担 1<2
    * 实时性  1>2
    * 适用场景 2是数据量不大，数据稳定的场景 1反之

* 支持的物理分页数据库

  ```java
  <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>5.3.0</version>
  </dependency>
  ```

  ```
  registerDialectAlias(String alias, Class<? extends Dialect> dialectClass) 
  Dialect方言
  
  registerDialectAlias("hsqldb", HsqldbDialect.class);
          registerDialectAlias("h2", HsqldbDialect.class);
          registerDialectAlias("phoenix", HsqldbDialect.class);
          registerDialectAlias("postgresql", PostgreSqlDialect.class);
          registerDialectAlias("mysql", MySqlDialect.class);
          registerDialectAlias("mariadb", MySqlDialect.class);
          registerDialectAlias("sqlite", MySqlDialect.class);
          registerDialectAlias("herddb", HerdDBDialect.class);
          registerDialectAlias("oracle", OracleDialect.class);
          registerDialectAlias("oracle9i", Oracle9iDialect.class);
          registerDialectAlias("db2", Db2Dialect.class);
          registerDialectAlias("informix", InformixDialect.class);
          registerDialectAlias("informix-sqli", InformixDialect.class);
          registerDialectAlias("sqlserver", SqlServerDialect.class);
          registerDialectAlias("sqlserver2012", SqlServer2012Dialect.class);
          registerDialectAlias("derby", SqlServer2012Dialect.class);
          registerDialectAlias("dm", OracleDialect.class);
          registerDialectAlias("edb", OracleDialect.class);
          registerDialectAlias("oscar", OscarDialect.class);
          registerDialectAlias("clickhouse", MySqlDialect.class);
          registerDialectAlias("highgo", HsqldbDialect.class);
          registerDialectAlias("xugu", HsqldbDialect.class);
          registerDialectAlias("impala", HsqldbDialect.class);
          registerDialectAlias("firebirdsql", FirebirdDialect.class);
          registerAutoDialectAlias("old", DefaultAutoDialect.class);
          registerAutoDialectAlias("hikari", HikariAutoDialect.class);
          registerAutoDialectAlias("druid", DruidAutoDialect.class);
          registerAutoDialectAlias("tomcat-jdbc", TomcatAutoDialect.class);
          registerAutoDialectAlias("dbcp", DbcpAutoDialect.class);
          registerAutoDialectAlias("c3p0", C3P0AutoDialect.class);
          registerAutoDialectAlias("default", 			DataSourceNegotiationAutoDialect.class);
  ```

  ```
  setProperties(Properties properties)｛
  this.initAutoDialectClass(properties);
  ....
  this.initDialectAlias(Properties properties)
  ｝
  
   private void initDialectAlias(Properties properties) {
          String dialectAlias = properties.getProperty("dialectAlias");
                 if (StringUtil.isNotEmpty(dialectAlias)) {
              String[] alias = dialectAlias.split(";");
              ....
   ｝
  通过配置properties.dialectAlias来新增支持数据库,数据库之间用;隔开
  ```

* 步骤

  1. 新版拦截器是 `com.github.pagehelper.PageInterceptor`，`com.github.pagehelper.PageHelper` 现在是一个特殊的 `dialect` 实现类，是分页插件的默认实现类

     在Mybatic-config.xml中添加

     ```
     <!-- 
         plugins在配置文件中的位置必须符合要求，否则会报错，顺序如下:
         properties?, settings?, 
         typeAliases?, typeHandlers?, 
         objectFactory?,objectWrapperFactory?, 
         plugins?, 
         environments?, databaseIdProvider?, mappers?
     -->
     <plugins>
         <!-- com.github.pagehelper为PageHelper类所在包名 -->
         <plugin interceptor="com.github.pagehelper.PageInterceptor">
             <!-- 使用下面的方式配置参数，后面会有所有的参数介绍 -->
             <property name="param1" value="value1"/>
     	</plugin>
     </plugins>
     ```

     * 分页插件可选参数

       * dialect : 默认情况下会使用 PageHelper 方式进行分页，如果想要实现自己的分页逻辑，可以实现 `Dialect`(`com.github.pagehelper.Dialect`) 接口，然后配置该属性为实现类的全限定名称

         **dialect 配置后以下参数失效**

       * `helperDialect`：分页插件会自动检测当前的数据库链接，自动选择合适的分页方式。 你可以配置`helperDialect`属性来指定分页插件使用哪种方言。配置时，可以使用下面的缩写值：
         `oracle`,`mysql`,`mariadb`,`sqlite`,`hsqldb`,`postgresql`,`db2`,`sqlserver`,`informix`,`h2`,`sqlserver2012`,`derby`
         **特别注意：**使用 SqlServer2012 数据库时，需要手动指定为 `sqlserver2012`，否则会使用 SqlServer2005 的方式进行分页。
         你也可以实现 `AbstractHelperDialect`，然后配置该属性为实现类的全限定名称即可使用自定义的实现方法。

       * `offsetAsPageNum`：默认值为 `false`，该参数对使用 `RowBounds` 作为分页参数时有效。 当该参数设置为 `true` 时，会将 `RowBounds` 中的 `offset` 参数当成 `pageNum` 使用，可以用页码和页面大小两个参数进行分页。

       * `rowBoundsWithCount`：默认值为`false`，该参数对使用 `RowBounds` 作为分页参数时有效。 当该参数设置为`true`时，使用 `RowBounds` 分页会进行 count 查询。

       * `pageSizeZero`：默认值为 `false`，当该参数设置为 `true` 时，如果 `pageSize=0` 或者 `RowBounds.limit = 0` 就会查询出全部的结果（相当于没有执行分页查询，但是返回结果仍然是 `Page` 类型）。

       * `reasonable`：分页合理化参数，默认值为`false`。当该参数设置为 `true` 时，`pageNum<=0` 时会查询第一页， `pageNum>pages`（超过总数时），会查询最后一页。默认`false` 时，直接根据参数进行查询。

       * `params`：为了支持`startPage(Object params)`方法，增加了该参数来配置参数映射，用于从对象中根据属性名取值， 可以配置 `pageNum,pageSize,count,pageSizeZero,reasonable`，不配置映射的用默认值， 默认值为`pageNum=pageNum;pageSize=pageSize;count=countSql;reasonable=reasonable;pageSizeZero=pageSizeZero`。

       * `supportMethodsArguments`：支持通过 Mapper 接口参数来传递分页参数，默认值`false`，分页插件会从查询方法的参数值中，自动根据上面 `params` 配置的字段中取值，查找到合适的值时就会自动分页。 使用方法可以参考测试代码中的 `com.github.pagehelper.test.basic` 包下的 `ArgumentsMapTest` 和 `ArgumentsObjTest`。

       * `autoRuntimeDialect`：默认值为 `false`。设置为 `true` 时，允许在运行时根据多数据源自动识别对应方言的分页 （不支持自动选择`sqlserver2012`，只能使用`sqlserver`），用法和注意事项参考下面的**场景五**。

       * `closeConn`：默认值为 `true`。当使用运行时动态数据源或没有设置 `helperDialect` 属性自动获取数据库类型时，会自动获取一个数据库连接， 通过该属性来设置是否关闭获取的这个连接，默认`true`关闭，设置为 `false` 后，不会关闭获取的连接，这个参数的设置要根据自己选择的数据源来决定。

       * `aggregateFunctions`(5.1.5+)：默认为所有常见数据库的聚合函数，允许手动添加聚合函数（影响行数），所有以聚合函数开头的函数，在进行 count 转换时，会套一层。其他函数和列会被替换为 count(0)，其中count列可以自己配置。

       **注意：当 `offsetAsPageNum=false` 的时候，由于 `PageNum` 问题，`RowBounds`查询的时候 `reasonable` 会强制为 `false`。使用 `PageHelper.startPage` 方法不受影响。**

     * 配置参数

       ##### 场景一

       如果你仍然在用类似ibatis式的命名空间调用方式，你也许会用到`rowBoundsWithCount`， 分页插件对`RowBounds`支持和 MyBatis 默认的方式是一致，默认情况下不会进行 count 查询，如果你想在分页查询时进行 count 查询， 以及使用更强大的 `PageInfo` 类，你需要设置该参数为 `true`。

       **注：** `PageRowBounds` 想要查询总数也需要配置该属性为 `true`。

       ##### 场景二

       如果你仍然在用类似ibatis式的命名空间调用方式，你觉得 `RowBounds` 中的两个参数 `offset,limit` 不如 `pageNum,pageSize` 容易理解， 你可以使用 `offsetAsPageNum` 参数，将该参数设置为 `true` 后，`offset`会当成 `pageNum` 使用，`limit` 和 `pageSize` 含义相同。

       ##### 场景三

       如果觉得某个地方使用分页后，你仍然想通过控制参数查询全部的结果，你可以配置 `pageSizeZero` 为 `true`， 配置后，当 `pageSize=0` 或者 `RowBounds.limit = 0` 就会查询出全部的结果。

       ##### 场景四

       如果你分页插件使用于类似分页查看列表式的数据，如新闻列表，软件列表， 你希望用户输入的页数不在合法范围（第一页到最后一页之外）时能够正确的响应到正确的结果页面， 那么你可以配置 `reasonable` 为 `true`，这时如果 `pageNum<=0` 会查询第一页，如果 `pageNum>总页数` 会查询最后一页。

       ##### 场景五

       如果你在 Spring 中配置了动态数据源，并且连接不同类型的数据库，这时你可以配置 `autoRuntimeDialect` 为 `true`，这样在使用不同数据源时，会使用匹配的分页进行查询。 这种情况下，你还需要特别注意 `closeConn` 参数，由于获取数据源类型会获取一个数据库连接，所以需要通过这个参数来控制获取连接后，是否关闭该连接。 默认为 `true`，有些数据库连接关闭后就没法进行后续的数据库操作。而有些数据库连接不关闭就会很快由于连接数用完而导致数据库无响应。所以在使用该功能时，特别需要注意你使用的数据源是否需要关闭数据库连接。

       当不使用动态数据源而只是自动获取 `helperDialect` 时，数据库连接只会获取一次，所以不需要担心占用的这一个连接是否会导致数据库出错，但是最好也根据数据源的特性选择是否关闭连接。

  2. 使用

     * 方式一

       ```
       //rowBoundsWithCount=true  支持count查询
       // offset从0开始偏移量 pagenum从1开始 想要pagenum offsetAsPageNum=true
       //PageRowBounds extends RowBounds 可以获得查询总数
       PageRowBounds rowBounds=new PageRowBounds(2, 10);
       List<GenerateDemo> lst  = sqlsession.selectList("org.example.mybatis.mapper.GenerateDemoMapper.selectByAll", null,rowBounds);
       System.out.println(rowBounds.getCount());
       lst.forEach(System.out::println);
       ```

     * 方式二 推荐

       ```
       //PageHelper.startPage/PageHelper.offsetPage之后的第一个MyBatis 查询方法会被进行分页
       //获取第1页，10条内容，默认查询总数count
       //支持 ServletRequest(url?pageNum=1&pageSize=10),Map,POJO 对象，需要配合 params 参数
       PageHelper.startPage(new HashMap<String,Object>(){{
           put("pageNum",2);
           put("pageSize",10);
       }});
       //实际返回page<E>类型
       List<GenerateDemo> lst = mapper.selectByAll();
       //后面的不会被分页，除非再次调用PageHelper.startPage
       List<GenerateDemo> lst2 = mapper.selectByAll();
       lst.forEach(System.out::println);
       System.out.println(((Page)lst).getPages());
       ```

       with pageInfo

       ```
       PageHelper.startPage(new HashMap<String,Object>(){{
           put("pageNum",2);
           put("pageSize",10);
       }});
       List<GenerateDemo> lst = mapper.selectByAll();
       PageInfo<GenerateDemo> page = new PageInfo<>(lst);
       //PageInfo包含了非常全面的分页属性
       System.out.println( page.getPageNum());
       System.out.println(page.getPageSize());
       System.out.println( page.getStartRow());
       System.out.println( page.getEndRow());
       System.out.println( page.getTotal());
       System.out.println( page.getPages());
       System.out.println( page.isIsFirstPage());
       System.out.println( page.isIsLastPage());
       System.out.println( page.getPrePage());
       System.out.println( page.getNextPage());
       ```

       with count

       ```
       Page page = PageHelper.startPage(-1, 8, false);
       ```
     
  
  自定义count sql count=true时  params=count=countSql(默认)
  
       ```
       <select id="selectByAll_COUNT"  resultType="Long">
         select 20 from Generate_Demo
       </select>
     ```
  
   * 方式三 参数方式
  
     ```
       <property name="supportMethodsArguments" value="true"/>
       <property name="params" value="pageNum=pageNumKey;pageSize=pageSizeKey;"/>
       ```
         
       ```
       List<GenerateDemo> selectByAll( @Param("pageNumKey") int pageNum,
                                       @Param("pageSizeKey") int pageSize);
       或
       List<User> selectByPageNumSize(User user); user同时包含pageNumKey/pageSizeKey
       ```
  
     * 方式四 ISelect
     
       ```
       Page<User> page = PageHelper.startPage(2, 10).doSelectPage(new ISelect() {
           @Override
           public void doSelect() {
               mapper.selectByAll();
           }
       });
       ```

3. 安全性分析

   ##### 	1. 使用 `RowBounds` 和 `PageRowBounds` 参数方式是极其安全的

   ##### 	2. 使用参数方式是极其安全的

   ##### 	3. 使用 ISelect 接口调用是极其安全的

   4. **只要你可以保证在 `PageHelper` 方法调用后紧跟 MyBatis 查询方法，这就是安全的**

      `PageHelper` 方法使用了静态的 `ThreadLocal` 参数，分页参数和线程是绑定的。因为 `PageHelper` 在 `finally`  代码段中自动清除了 `ThreadLocal` 存储的对象。

      ```java
      PageInterceptor
      public Object intercept(Invocation invocation) throws Throwable{
              try {....}
             finally {
                  if(dialect != null){
                      dialect.afterAll();
                  }
              }
      
      PageHelper
       public void afterAll() {
              //这个方法即使不分页也会被执行，所以要判断 null
              AbstractHelperDialect delegate = autoDialect.getDelegate();
              if (delegate != null) {
                  delegate.afterAll();
                  autoDialect.clearDelegate();
              }
              clearPage();
          }    
      ```

       存在PageHelper 生产了一个分页参数，但是没有被消费，这个参数就会一直保留在这个线程上。当这个线程再次被使用时，就可能导致不该分页的方法去消费这个分页参数，这就产生了莫名其妙的分页

### ThreadLocal

The *TheadLocal* construct allows us to store data that will be **accessible only** by **a specific thread**

```java 
ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();
```

we can think that *ThreadLocal* stores data inside of a map – with the threadLocal as the key

```
    ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 3, 1, TimeUnit.MINUTES,
            new LinkedBlockingQueue());
    Runnable runnable=new ThreadLocalWithContext("1");
for (int i = 0; i < 10; i++) {
        poolExecutor.execute(runnable);
        }
        poolExecutor.shutdown();

@Override
public void run() {
        if(userContext.get()==null) {
        userContext.set(new Context(userId));
        }
        System.out.println( userContext.get());
        }
```

注意：**Since the application didn't perform the necessary cleanups last time, it may re-use the same *ThreadLocal* data for the new request** 

If we submit our requests to **this implementation of ExecutorService（ThreadLocalAwareThreadPool）,** then we can be sure that using ThreadLocal and thread pools **won't introduce safety hazards（危害）** for our application.

```
class ThreadLocalAwareThreadPool extends ThreadPoolExecutor{
    public ThreadLocalAwareThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        // before running anything using the borrowed thread
//        System.out.println("====before execute runnable=====");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        //after executing our logic
        //call remove ThreadLocal
//        System.out.println("====after execute runnable=====");
        if(r instanceof ThreadLocalWithContext){
            ThreadLocalWithContext context= (ThreadLocalWithContext)r;
            context.cleanUpLocal();
//            System.out.println("clean up success");
        }

    }
}
```

### ThreadPoolExecutor

* corePoolSize the number of threads to keep in the pool, **even if they are idle**, unless allowCoreThreadTimeOut is set。决定是新建线程还是放到workQueue中

* maximumPoolSize – the maximum number of threads to allow in the pool   由它本身和workQueue决定线程池中最大线程数

* keepAliveTime – **when the number of threads is greater than the core**, this is the maximum time that excess idle threads will wait for new tasks before terminating.

* workQueue e – the queue to use for holding tasks before they are executed. This queue will hold only the Runnable tasks submitted by the execute method.

  * SynchronousQueue

    A blocking queue in which each insert operation must wait for a corresponding remove operation by another thread, and vice versa（反之亦然）. A synchronous queue does not have any internal capacity, not even a capacity of one.

    **插入或者消费线程会阻塞，只有有一对插入和发送线程匹配上，才同时退出。**

  * ArrayBlockingQueue 有界队列

  * LinkedBlockingQueue  “无界”队列

    在这种情况下maximumPoolSize这个参数是无效的

  * PriorityBlockingQueue 无界队列

* threadFactory – the factory to use when the executor creates a new thread

* handler – the handler to use when execution is blocked because the thread bounds and queue capacities are reached  线程池的工作队列已满并且线程池数目达到maximumPoolSize，还有新的任务就采取拒绝措施

  ```
  ThreadPoolExecutor.AbortPolicy:　　　　　　　　 // 丢弃任务并抛出RejectedExecutionException异常。
  ThreadPoolExecutor.DiscardPolicy：　　　　　　　// 也是丢弃任务，但是不抛出异常。
  ThreadPoolExecutor.DiscardOldestPolicy：　　  // 丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
  ThreadPoolExecutor.CallerRunsPolicy：　　　　  // 由调用线程处理该任务
  ```

* execute方法

  1. If fewer than corePoolSize threads are running, try to
     start a new thread with the given command as its first
     task
  2. If a task can be successfully queued, then we still need
     to double-check whether we should have added a thread
     (because existing ones died since last checking)
  3. If we cannot queue task, then we try to add a newthread.

### 插件

application.xml 添加

```
<bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactory">
....
 <property name="plugins">
            <array>
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <value>
                            helperDialect=mysql
                            reasonable=true
                            supportMethodsArguments=true
                            params=count=countSql
                            autoRuntimeDialect=true
                        </value>
                    </property>
                </bean>
            </array>
       </property>
</bean>
```







