# Mybatis

## 介绍

* 框架：整个或部分系统的可重用设计的解决方案

* jdbc的问题:为什么不用DriverManager.registerDriver(new Driver) 

* The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.

* Orm object relational mapping:简单说就是将数据库表和实体类及其属性关联起来

* mysql driver:jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf8&**useSSL=false&serverTimezone=UTC**&rewriteBatchedStatements=true

* xml里面特殊字符需要转义 如  

  &lt; 小于号 

     &gt; 大于号 

     &amp; 和 

     &apos; ' 单引号 

     &quot; " 双引号



## 入门

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
   <!--   not necessarily to add classpath:-->
    <properties resource="jdbc.properties"/>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
                <dataSource type="POOLED">
                    <property name="driver" value="${driver}"/>
                    <property name="url" value="${url}"/>
                    <property name="username" value="${username}"/>
                    <property name="password" value="${password}"/>
                </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="org/example/mybatis/User2.xml"/>
    </mappers>
</configuration>
```

使用和指定语句的参数和返回值相匹配的接口（比如 BlogMapper.class），现在你的代码不仅更清晰，更加类型安全，还不用担心可能出错的字符串字面值以及强制类型转换。 

```
    private static SqlSessionFactory ssFactory;

    @BeforeClass
    public static void config() throws IOException {
        String resource = "mybatis-configuration.xml";
        //或者classLoader.getResourceAsStream (只适合类路径)
        //Resources.getResourceAsStream适合类路径（不需要写classpath）或file://等
        InputStream is = Resources.getResourceAsStream(resource); 
        ssFactory = new SqlSessionFactoryBuilder().build(is);
    }

    @Test
    public void testConfiguration(){
        try(SqlSession sqlSession= ssFactory.openSession()){
            User2Mapper user2Mapper = sqlSession.getMapper(User2Mapper.class);
            List<User2> user2s = user2Mapper.selectById(4);
            user2s.forEach(System.out::println);
        }
    }
```

```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="org.example.mybatis.mapper.User2Mapper">
    <select id="selectById" resultType="org.example.mybatis.entity.User2">
        select * from user2 where id &lt; #{id}
    </select>
</mapper>
```

## XML配置

![1](C:\Users\Administrator\Desktop\复习\素材\pic\mybatis\1.jpg)

* properties

  这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置。例如：

  ```
  <properties resource="org/mybatis/example/config.properties">
    <property name="username" value="dev_user"/>
    <property name="password" value="F2Fa3!33TYyg"/>
  </properties>
  ```

  设置好的属性可以在整个配置文件中用来替换需要动态配置的属性值。比如:

  ```
  <dataSource type="POOLED">
    <property name="driver" value="${driver}"/>
    <property name="url" value="${url}"/>
    <property name="username" value="${username}"/>
    <property name="password" value="${password}"/>
  </dataSource>	
  ```

  通过方法参数传递的属性具有最高优先级(直接在dataSource里配置的)，resource/url 属性中指定的配置文件次之，最低优先级的则是 properties 元素中指定的属性(properties里配置的property属性)。 

* setting

  | cacheEnabled                     | 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。     | true \| false                                                | true                                                  |
  | -------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ----------------------------------------------------- |
  | lazyLoadingEnabled               | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 `fetchType` 属性来覆盖该项的开关状态。 | true \| false                                                | false                                                 |
  | aggressiveLazyLoading            | 开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载（参考 `lazyLoadTriggerMethods`)。 | true \| false                                                | false （在 3.4.1 及之前的版本中默认为 true）          |
  | multipleResultSetsEnabled        | 是否允许单个语句返回多结果集（需要数据库驱动支持）。         | true \| false                                                | true                                                  |
  | useColumnLabel                   | 使用列标签代替列名。实际表现依赖于数据库驱动，具体可参考数据库驱动的相关文档，或通过对比测试来观察。 | true \| false                                                | true                                                  |
  | useGeneratedKeys                 | 允许 JDBC 支持自动生成主键，需要数据库驱动支持。如果设置为 true，将强制使用自动生成主键。尽管一些数据库驱动不支持此特性，但仍可正常工作（如 Derby）。 | true \| false                                                | False                                                 |
  | autoMappingBehavior              | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示关闭自动映射；PARTIAL 只会自动映射没有定义嵌套结果映射的字段。 FULL 会自动映射任何复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL                                          | PARTIAL                                               |
  | autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或未知属性类型）的行为。`NONE`: 不做任何反应`WARNING`: 输出警告日志（`'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'` 的日志等级必须设置为 `WARN`）`FAILING`: 映射失败 (抛出 `SqlSessionException`) | NONE, WARNING, FAILING                                       | NONE                                                  |
  | defaultExecutorType              | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（PreparedStatement）； BATCH 执行器不仅重用语句还会执行批量更新。 | SIMPLE REUSE BATCH                                           | SIMPLE                                                |
  | defaultStatementTimeout          | 设置超时时间，它决定数据库驱动等待数据库响应的秒数。         | 任意正整数                                                   | 未设置 (null)                                         |
  | defaultFetchSize                 | 为驱动的结果集获取数量（fetchSize）设置一个建议值。此参数只可以在查询设置中被覆盖。 | 任意正整数                                                   | 未设置 (null)                                         |
  | defaultResultSetType             | 指定语句默认的滚动策略。（新增于 3.5.2）                     | FORWARD_ONLY \| SCROLL_SENSITIVE \| SCROLL_INSENSITIVE \| DEFAULT（等同于未设置） | 未设置 (null)                                         |
  | safeRowBoundsEnabled             | 是否允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为 false。 | true \| false                                                | False                                                 |
  | safeResultHandlerEnabled         | 是否允许在嵌套语句中使用结果处理器（ResultHandler）。如果允许使用则设置为 false。 | true \| false                                                | True                                                  |
  | **mapUnderscoreToCamelCase**     | ****是否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。 | true \| false****                                            | **False**                                             |
  | localCacheScope                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用和加速重复的嵌套查询。 默认值为 SESSION，会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地缓存将仅用于执行语句，对相同 SqlSession 的不同查询将不会进行缓存。 | SESSION \| STATEMENT                                         | SESSION                                               |
  | jdbcTypeForNull                  | 当没有为参数指定特定的 JDBC 类型时，空值的默认 JDBC 类型。 某些数据库驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType 常量，常用值：NULL、VARCHAR 或 OTHER。              | OTHER                                                 |
  | lazyLoadTriggerMethods           | 指定对象的哪些方法触发一次延迟加载。                         | 用逗号分隔的方法列表。                                       | equals,clone,hashCode,toString                        |
  | defaultScriptingLanguage         | 指定动态 SQL 生成使用的默认脚本语言。                        | 一个类型别名或全限定类名。                                   | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver |
  | defaultEnumTypeHandler           | 指定 Enum 使用的默认 `TypeHandler` 。（新增于 3.4.5）        | 一个类型别名或全限定类名。                                   | org.apache.ibatis.type.EnumTypeHandler                |
  | callSettersOnNulls               | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这在依赖于 Map.keySet() 或 null 值进行初始化时比较有用。注意基本类型（int、boolean 等）是不能设置成 null 的。 | true \| false                                                | false                                                 |
  | returnInstanceForEmptyRow        | 当返回行的所有列都是空时，MyBatis默认返回 `null`。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集（如集合或关联）。（新增于 3.4.2） | true \| false                                                | false                                                 |
  | logPrefix                        | 指定 MyBatis 增加到日志名称的前缀。                          | 任何字符串                                                   | 未设置                                                |
  | logImpl                          | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J \| LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING \| STDOUT_LOGGING \| NO_LOGGING | 未设置                                                |
  | proxyFactory                     | 指定 Mybatis 创建可延迟加载对象所用到的代理工具。            | CGLIB \| JAVASSIST                                           | JAVASSIST （MyBatis 3.3 以上）                        |
  | vfsImpl                          | 指定 VFS 的实现                                              | 自定义 VFS 的实现的类全限定名，以逗号分隔。                  | 未设置                                                |
  | useActualParamName               | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的项目必须采用 Java 8 编译，并且加上 `-parameters` 选项。（新增于 3.4.1） | true \| false                                                | true                                                  |
  | configurationFactory             | 指定一个提供 `Configuration` 实例的类。 这个被返回的 Configuration 实例用来加载被反序列化对象的延迟加载属性值。 这个类必须包含一个签名为`static Configuration getConfiguration()` 的方法。（新增于 3.2.3） | 一个类型别名或完全限定类名。                                 | 未设置                                                |
  | shrinkWhitespacesInSql           | 从SQL中删除多余的空格字符。请注意，这也会影响SQL中的文字字符串。 (新增于 3.5.5) | true \| false                                                | false                                                 |
  | defaultSqlProviderType           | Specifies an sql provider class that holds provider method (Since 3.5.6). This class apply to the `type`(or `value`) attribute on sql provider annotation(e.g. `@SelectProvider`), when these attribute was omitted. | A type alias or fully qualified class name                   | Not set                                               |

<Settings>

​	<setting name='a' value='a'/>

​	....

</settings>

* typealias

  也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如：

  ```
  <typeAliases>
    <package name="domain.blog"/>
  </typeAliases>
  ```

  每一个在包 `domain.blog` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `domain.blog.Author` 的别名为 `author （大小写其实使可以的)`；若有注解，则别名为其注解值。见下面的例子：

  ```
  @Alias("author")
  public class Author {
      ...
  }
  ```

下面是一些为常见的 Java 类型内建的类型别名。它们都是不区分大小写的，注意，为了应对原始类型的命名重复，采取了特殊的命名风格。

It's likely that neither a Result Type nor a Result Map was specified.

| 别名       | 映射的类型 |
| ---------- | ---------- |
| _byte      | byte       |
| _long      | long       |
| _short     | short      |
| _int       | int        |
| _integer   | int        |
| _double    | double     |
| _float     | float      |
| _boolean   | boolean    |
| string     | String     |
| byte       | Byte       |
| long       | Long       |
| short      | Short      |
| int        | Integer    |
| integer    | Integer    |
| double     | Double     |
| float      | Float      |
| boolean    | Boolean    |
| date       | Date       |
| decimal    | BigDecimal |
| bigdecimal | BigDecimal |
| object     | Object     |
| map        | Map        |
| hashmap    | HashMap    |
| list       | List       |
| arraylist  | ArrayList  |
| collection | Collection |
| iterator   | Iterator   |

* typeHandlers

  MyBatis 在设置预处理语句（PreparedStatement）中的参数或从结果集中取出一个值时， 都会用类型处理器将获取到的值以合适的方式转换成 Java 类型。下表描述了一些**默认**的类型处理器。

   

  | 类型处理器                   | Java 类型                       | JDBC 类型                                                    |
  | ---------------------------- | ------------------------------- | ------------------------------------------------------------ |
  | `BooleanTypeHandler`         | `java.lang.Boolean`, `boolean`  | 数据库兼容的 `BOOLEAN`                                       |
  | `ByteTypeHandler`            | `java.lang.Byte`, `byte`        | 数据库兼容的 `NUMERIC` 或 `BYTE`                             |
  | `ShortTypeHandler`           | `java.lang.Short`, `short`      | 数据库兼容的 `NUMERIC` 或 `SMALLINT`                         |
  | `IntegerTypeHandler`         | `java.lang.Integer`, `int`      | 数据库兼容的 `NUMERIC` 或 `INTEGER`                          |
  | `LongTypeHandler`            | `java.lang.Long`, `long`        | 数据库兼容的 `NUMERIC` 或 `BIGINT`                           |
  | `FloatTypeHandler`           | `java.lang.Float`, `float`      | 数据库兼容的 `NUMERIC` 或 `FLOAT`                            |
  | `DoubleTypeHandler`          | `java.lang.Double`, `double`    | 数据库兼容的 `NUMERIC` 或 `DOUBLE`                           |
  | `BigDecimalTypeHandler`      | `java.math.BigDecimal`          | 数据库兼容的 `NUMERIC` 或 `DECIMAL`                          |
  | `StringTypeHandler`          | `java.lang.String`              | `CHAR`, `VARCHAR`                                            |
  | `ClobReaderTypeHandler`      | `java.io.Reader`                | -                                                            |
  | `ClobTypeHandler`            | `java.lang.String`              | `CLOB`, `LONGVARCHAR`                                        |
  | `NStringTypeHandler`         | `java.lang.String`              | `NVARCHAR`, `NCHAR`                                          |
  | `NClobTypeHandler`           | `java.lang.String`              | `NCLOB`                                                      |
  | `BlobInputStreamTypeHandler` | `java.io.InputStream`           | -                                                            |
  | `ByteArrayTypeHandler`       | `byte[]`                        | 数据库兼容的字节流类型                                       |
  | `BlobTypeHandler`            | `byte[]`                        | `BLOB`, `LONGVARBINARY`                                      |
  | `DateTypeHandler`            | `java.util.Date`                | `TIMESTAMP`                                                  |
  | `DateOnlyTypeHandler`        | `java.util.Date`                | `DATE`                                                       |
  | `TimeOnlyTypeHandler`        | `java.util.Date`                | `TIME`                                                       |
  | `SqlTimestampTypeHandler`    | `java.sql.Timestamp`            | `TIMESTAMP`                                                  |
  | `SqlDateTypeHandler`         | `java.sql.Date`                 | `DATE`                                                       |
  | `SqlTimeTypeHandler`         | `java.sql.Time`                 | `TIME`                                                       |
  | `ObjectTypeHandler`          | Any                             | `OTHER` 或未指定类型                                         |
  | `EnumTypeHandler`            | Enumeration Type                | VARCHAR 或任何兼容的字符串类型，用来存储枚举的名称（而不是索引序数值） |
  | `EnumOrdinalTypeHandler`     | Enumeration Type                | 任何兼容的 `NUMERIC` 或 `DOUBLE` 类型，用来存储枚举的序数值（而不是名称）。 |
  | `SqlxmlTypeHandler`          | `java.lang.String`              | `SQLXML`                                                     |
  | `InstantTypeHandler`         | `java.time.Instant`             | `TIMESTAMP`                                                  |
  | `LocalDateTimeTypeHandler`   | `java.time.LocalDateTime`       | `TIMESTAMP`                                                  |
  | `LocalDateTypeHandler`       | `java.time.LocalDate`           | `DATE`                                                       |
  | `LocalTimeTypeHandler`       | `java.time.LocalTime`           | `TIME`                                                       |
  | `OffsetDateTimeTypeHandler`  | `java.time.OffsetDateTime`      | `TIMESTAMP`                                                  |
  | `OffsetTimeTypeHandler`      | `java.time.OffsetTime`          | `TIME`                                                       |
  | `ZonedDateTimeTypeHandler`   | `java.time.ZonedDateTime`       | `TIMESTAMP`                                                  |
  | `YearTypeHandler`            | `java.time.Year`                | `INTEGER`                                                    |
  | `MonthTypeHandler`           | `java.time.Month`               | `INTEGER`                                                    |
  | `YearMonthTypeHandler`       | `java.time.YearMonth`           | `VARCHAR` 或 `LONGVARCHAR`                                   |
  | `JapaneseDateTypeHandler`    | `java.time.chrono.JapaneseDate` | `DATE`                                                       |

  ```
  <resultMap id="userMap" type="User2">
      <id property="id" column="id" javaType="java.lang.Integer" jdbcType="INTEGER"/>
      <result property="name" column="name" javaType="java.lang.String" jdbcType="VARCHAR"/>
      <result property="user3Id" column="user3_id"/>
  </resultMap>
  ```

  * 自定义枚举值typeHandler

    ```
    <typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="java.math.RoundingMode"/>
    
    
     <result property="roundingMode" column="roundingMode" typeHandler="org.apache.ibatis.type.EnumOrdinalTypeHandler"/>
    
    ```

* ObjectFactory

  每次 MyBatis 创建结果对象的新实例时，它都会使用一个对象工厂（ObjectFactory）实例来完成实例化工作。 默认的对象工厂需要做的仅仅是实例化目标类，要么通过默认无参构造方法，要么通过存在的参数映射来调用带有参数的构造方法。 如果想覆盖对象工厂的默认行为，可以通过创建自己的对象工厂来实现 

  ```
  public class ExampleObjectFactory extends DefaultObjectFactory {
    public Object create(Class type) {
      return super.create(type);
    }
      @Override
      public <T> T create(Class<T> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
          return super.create(type, constructorArgTypes, constructorArgs);
      }
    public void setProperties(Properties properties) {
      super.setProperties(properties);
    }
    public <T> boolean isCollection(Class<T> type) {
      return Collection.class.isAssignableFrom(type);
    }}
  ```

  ```
  <objectFactory type="org.example.mybatis.ExampleObjectFactory">
    <property name="someProperty" value="100"/>
  </objectFactory>
  ```

* plugin

  MyBatis 允许你在映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的方法调用包括：

  - Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
  - ParameterHandler (getParameterObject, setParameters)
  - ResultSetHandler (handleResultSets, handleOutputParameters)
  - StatementHandler (prepare, parameterize, batch, update, query)

* environments

  ```
  <environments default="development">
    <environment id="development">
      <transactionManager type="JDBC">
        <property name="..." value="..."/>
      </transactionManager>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${username}"/>
        <property name="password" value="${password}"/>
      </dataSource>
    </environment>
  </environments>
  ```

  

在 MyBatis 中有两种类型的事务管理器（也就是 type="[JDBC|MANAGED]"）： 

- JDBC – 这个配置直接使用了 JDBC 的提交和回滚设施，它依赖从数据源获得的连接来管理事务作用域。

- MANAGED – 这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接。然而一些容器并不希望连接被关闭，因此需要将 closeConnection 属性设置为 false 来阻止默认的关闭行为。例如: 

  <transactionManager type="MANAGED">  <property name="closeConnection" value="false"/></transactionManager>

- 如果你正在使用 Spring + MyBatis，则没有必要配置事务管理器，因为 **Spring 模块会使用自带的管理器来覆盖前面的配置**。 



有三种内建的数据源类型（也就是 type="[UNPOOLED|POOLED|JNDI]"）：

**UNPOOLED**– 这个数据源的实现会**每次请求**时打开和关闭连接。虽然有点慢，但对那些数据库连接可用性要求不高的简单应用程序来说，是一个很好的选择。 性能表现则依赖于使用的数据库，对某些数据库来说，使用连接池并不重要，这个配置就很适合这种情形。UNPOOLED 类型的数据源仅仅需要配置以下 5 种属性：

- `driver` – 这是 **JDBC 驱动的 Java 类全限定名**（并不是 JDBC 驱动中可能包含的数据源类）。
- `url` – 这是数据库的 JDBC URL 地址。
- `username` – 登录数据库的用户名。
- `password` – 登录数据库的密码。
- `defaultTransactionIsolationLevel` – 默认的连接事务隔离级别。
- `defaultNetworkTimeout` – 等待数据库操作完成的默认网络超时时间（单位：毫秒）。查看 `java.sql.Connection#setNetworkTimeout()` 的 API 文档以获取更多信息。

作为可选项，你也可以传递属性给数据库驱动。只需在属性名加上“driver.”前缀即可，例如：

- `driver.encoding=UTF8`

这将通过 DriverManager.getConnection(url, driverProperties) 方法传递值为 `UTF8` 的 `encoding` 属性给数据库驱动。

**POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这种处理方式很流行，能使并发 Web 应用快速响应请求。

除了上述提到 UNPOOLED 下的属性外，还有更多属性用来配置 POOLED 的数据源：

- `poolMaximumActiveConnections` – 在任意时间可存在的活动（正在使用）连接数量，默认值：10
- `poolMaximumIdleConnections` – 任意时间可能存在的空闲连接数。
- `poolMaximumCheckoutTime` – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
- `poolTimeToWait` – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。
- `poolMaximumLocalBadConnectionTolerance` – 这是一个关于**坏连接容忍度**的底层设置， 作用于每一个尝试从缓存池获取连接的线程。 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 `poolMaximumIdleConnections` 与 `poolMaximumLocalBadConnectionTolerance` 之和。 默认值：3（新增于 3.4.5）
- `poolPingQuery` – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动出错时返回恰当的错误消息。
- `poolPingEnabled` – 是否启用侦测查询。若开启，需要设置 `poolPingQuery` 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
- `poolPingConnectionsNotUsedFor` – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。

**JNDI** – 这个数据源实现是为了能在如 EJB 或应用服务器这类容器中使用，**容器可以集中或在外部配置数据源**，然后放置一个 JNDI 上下文的数据源引用。这种数据源配置只需要两个属性：

- `initial_context` – 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）。这是个可选属性，如果忽略，那么将会直接从 InitialContext 中寻找 data_source 属性。
- `data_source` – 这是引用数据源实例位置的上下文路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。

和其他数据源配置类似，可以通过添加前缀“env.”直接把属性传递给 InitialContext。比如：

- `env.encoding=UTF8`

这就会在 InitialContext 实例化时往它的构造方法传递值为 `UTF8` 的 `encoding` 属性。

你可以通过实现接口 `org.apache.ibatis.datasource.DataSourceFactory` 来使用第三方数据源实现：

```
public interface DataSourceFactory {
  void setProperties(Properties props);
  DataSource getDataSource();
}
```



* databaseIdProvider 数据库厂商标识

  ```
  <databaseIdProvider type="DB_VENDOR">
    <property name="SQL Server" value="sqlserver"/>
    <property name="DB2" value="db2"/>
    <property name="Oracle" value="oracle" />
  </databaseIdProvider>
  ```

在提供了属性别名时，databaseIdProvider 的 DB_VENDOR 实现会将 databaseId 设置为数据库产品名与属性中的名称第一个相匹配的值，如果没有匹配的属性，将会设置为 “null”。 在这个例子中，如果 `getDatabaseProductName()` 返回“Oracle (DataDirect)”，databaseId 将被设置为“oracle”。 

* mappers

```
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
</mappers>
```

```
<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
 </mappers>
```

```
<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
   </mappers>
```

```
<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

注意 **使用inteface和package时必须保证interface在相同类路径下有同名的mapper xml** 否则绑定失败

**打包的时候inteface和package会放在同一个文件夹内**

compact middle packages 可以让目录形成多级

解决方案：

```xml
xmlns:mybatis="http://mybatis.org/schema/mybatis-spring"
http://mybatis.org/schema/mybatis-spring
http://mybatis.org/schema/mybatis-spring.xsd

<mybatis:scan base-package="com.mybatis.x.mappers" />
<mybatis:scan> 元素提供了下列的属性来自定义扫描过程：
 annotation : 扫描器将注册所有的在 base-package 包内并且匹配指定注解的映射器 Mapper 接口。
 factory - ref : 当 Spring 上 下 文 中 有 多 个 SqlSessionFactory 实 例 时 ， 需 要 指 定 某 一 特 定 的
SqlSessionFactory 来创建映射器 Mapper 接口。正常情况下，只有应用程序中有一个以上的数据源
才会使用。
 marker - interface : 扫描器将注册在 base-package 包中的并且继承了特定的接口类的映射器 Mapper 接
口
 template - ref : 当 Spring 上下文中有多个 SqlSessionTemplate 实例时，需要指定某一特定的
SqlSessionTemplate 来创建映射器 Mapper 接口。 正常情况下，只有应用程序中有一个以上的数据源
才会使用。

 name-generator:BeannameGenerator 类的完全限定类名，用来命名检测到的组件。

    
 @MapperScan 和<mybatis:scan/>工作方式相同，并且也提供了对应的自定义选项。 
 Use this annotation to register MyBatis mapper interfaces when using Java Config. It performs when same work as MapperScannerConfigurer via MapperScannerRegistrar.

 @MapperScan("org.example.servletContainer.mapper")
public class RootWebConfig {

    @Order(1)
    @Bean
    public DataSource dataSource(){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
        ds.setUsername("c##ot");
        ds.setPassword("orcl123456");
        return ds;
    }


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
        ssfb.setDataSource(dataSource);
        ssfb.setMapperLocations(new 		PathMatchingResourcePatternResolver().getResources("classpath*:mappers/**/*.xml"));
        return ssfb.getObject();
    }
	或者
    spring.datasource.driverClassName='xxx'
    ...
    mybatis.mapperLocations='xxx'
    
}
```

error: Invalid default: public abstract java.lang.Class org.mybatis.spring.annotation.MapperScan.factoryBean()

​	必须加入spring-jdbc依赖

error: **oracle sql不能加分号**

ant风格：该风格源自Apache的Ant项目 ，仅用于匹配路径or目录 

| 通配符 | 说明                    |
| ------ | ----------------------- |
| ?      | 匹配任何单字符          |
| *      | 匹配0或者任意数量的字符 |
| **     | 匹配0或者更多的目录     |

## XML映射器

SQL 映射文件只有很少的几个顶级元素（按照应被定义的顺序列出）：

- `cache` – 该命名空间的缓存配置。
- `cache-ref` – 引用其它命名空间的缓存配置。
- `resultMap` – 描述如何从数据库结果集中加载对象，是最复杂也是最强大的元素。
- ~~`parameterMap` – 老式风格的参数映射。此元素已被废弃，并可能在将来被移除！请使用行内参数映射。文档中不会介绍此元素。~~
- `sql` – 可被其它语句引用的可重用语句块。
- `insert` – 映射插入语句。
- `update` – 映射更新语句。
- `delete` – 映射删除语句。
- `select` – 映射查询语句。

基本不使用:

| **`statementType`** | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| ------------------- | ------------------------------------------------------------ |
| `resultSetType`     | FORWARD_ONLY，SCROLL_SENSITIVE, SCROLL_INSENSITIVE 或 DEFAULT（等价于 unset） 中的一个，默认值为 unset （依赖数据库驱动）。 |
| `databaseId`        | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |
| `resultOrdered`     | 这个设置仅针对嵌套结果 select 语句：如果为 true，将会假设包含了嵌套结果集或是分组，当返回一个主结果行时，就不会产生对前面结果集的引用。 这就使得在获取嵌套结果集的时候不至于内存不够用。默认值：`false`。 |
| `resultSets`        | 这个设置仅适用于多结果集的情况。它将列出语句执行后返回的结果集并赋予每个结果集一个名称，多个名称之间以逗号分隔。 |

insert/update/delete

| 属性                   | 描述                                                         |
| ---------------------- | ------------------------------------------------------------ |
| `id`                   | 在命名空间中唯一的标识符，可以被用来引用这条语句。           |
| `parameterType`        | 将会传入这条语句的参数的类全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler）推断出具体传入语句的参数，默认值为未设置（unset）。 |
| ~~`parameterMap`~~     | ~~用于引用外部 parameterMap 的属性，目前已被废弃。请使用行内参数映射和 parameterType 属性。~~ |
| `flushCache`           | 将其设置为 true 后，只要语句被调用，都会导致本地缓存和二级缓存被清空，默认值：（对 insert、update 和 delete 语句）true。 |
| `timeout`              | 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖数据库驱动）。 |
| `statementType`        | 可选 STATEMENT，PREPARED 或 CALLABLE。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED。 |
| **`useGeneratedKeys`** | **（仅适用于 insert 和 update）这会令 MyBatis 使用 JDBC 的 getGeneratedKeys 方法来取出由数据库内部生成的主键（比如：像 MySQL 和 SQL Server 这样的关系型数据库管理系统的自动递增字段），默认值：false。** |
| `keyProperty`          | （仅适用于 insert 和 update）**指定能够唯一识别对象的属性，MyBatis 会使用 getGeneratedKeys 的返回值或 insert 语句的 selectKey 子元素设置它的值**，默认值：未设置（`unset`）。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `keyColumn`            | （仅适用于 insert 和 update）**设置生成键值在表中的列名**，在某些数据库（像 PostgreSQL）中，当主键列不是表中的第一列的时候，是必须设置的。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `databaseId`           | 如果配置了数据库厂商标识（databaseIdProvider），MyBatis 会加载所有不带 databaseId 或匹配当前 databaseId 的语句；如果带和不带的语句都有，则不带的会被忽略。 |

如果你的数据库支持自动生成主键的字段（比如 MySQL 和 SQL Server），那么你可以设置 useGeneratedKeys=”true”，然后再把 keyProperty 设置为目标属性就 OK 了 (就是**把keyProperty设置到parameterType对应的字段上**)

```
   <insert id="insert2List"
           useGeneratedKeys="true"  keyProperty="id">
    insert into user2 (roundingMode) values
        <foreach collection="list" item="item" separator=",">
               (#{item.roundingMode, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler})
           </foreach>
</insert> //会在parameterType 这里也就是item中找属性为id的
或者
 <insert id="insert2List"
            useGeneratedKeys="true"  keyColumn="id">
        insert into user2 (roundingMode) values
        <foreach collection="roundingModes" item="item" separator=",">
            (#{item, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler})
        </foreach>
    </insert>  //这种方式并不能在java程序中获取最新自增值
```

**默认sqlSession是不提交的 所以必须带上sqlSession.commit();**

selectKey 元素的属性 

| 属性            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| `keyProperty`   | `selectKey` 语句结果应该被设置到的目标属性。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `keyColumn`     | 返回结果集中生成列属性的列名。如果生成列不止一个，可以用逗号分隔多个属性名称。 |
| `resultType`    | 结果的类型。通常 MyBatis 可以推断出来，但是为了更加准确，写上也不会有什么问题。MyBatis 允许将任何简单类型用作主键的类型，包括字符串。如果生成列不止一个，则可以使用包含期望属性的 Object 或 Map。 |
| `order`         | 可以设置为 `BEFORE` 或 `AFTER`。如果设置为 `BEFORE`，那么它首先会生成主键，设置 `keyProperty` 再执行插入语句。如果设置为 `AFTER`，那么先执行插入语句，然后是 `selectKey` 中的语句 - 这和 Oracle 数据库的行为相似，在插入语句内部可能有嵌入索引调用。 |
| `statementType` | 和前面一样，MyBatis 支持 `STATEMENT`，`PREPARED` 和 `CALLABLE` 类型的映射语句，分别代表 `Statement`, `PreparedStatement` 和 `CallableStatement` 类型。 |

对于不支持自动生成主键列的数据库和可能不支持自动生成主键的 JDBC 驱动 (**支持的也可以使用**,**不建议实际使用** 由于`LAST_INSERT_ID`函数基于客户端独立原则。这意味着特定客户端的`LAST_INSERT_ID`函数返回的值是该客户端生成的值。这样就确保**每个客户端可以获得自己的唯一ID**    ) 

```
<insert id="insert2List" parameterType="User2">
    <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
        select LAST_INSERT_ID()
    </selectKey>
    insert into user2 (roundingMode) values
    <foreach collection="list" item="item" separator=",">
        (#{item.roundingMode, typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler})
    </foreach>
</insert>
```

### sql

```
<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
```

```
<select id="selectUsers" resultType="map">
  select
    <include refid="userColumns"><property name="alias" value="t1"/></include>,
    <include refid="userColumns"><property name="alias" value="t2"/></include>
  from some_table t1
    cross join some_table t2
</select>
```



 ### 参数

* 原始类型或简单数据类型 

  可以省略

* 对象

```
<insert id="insertUser" parameterType="User">
  insert into users (id, username, password)
  values (#{id}, #{username}, #{password})
</insert>
```

* ```
  #{age,javaType=int,jdbcType=NUMERIC,typeHandler=MyTypeHandler}
  ```

JDBC 要求，如果一个列允许使用 null 值，并且会使用值为 null 的参数，就必须要指定 JDBC 类型（jdbcType） 不过默认是有转化 的

* 对于数值类型，还可以设置 `numericScale` 指定小数点后保留的位数。

  ```
  #{height,javaType=double,jdbcType=NUMERIC,numericScale=2}
  ```

* mode 属性允许你指定 `IN`，`OUT` 或 `INOUT` 参数 

  

### 字符串替换

```
@Select("select * from user where ${column} = #{value}")
User findByColumn(@Param("column") String column, @Param("value") String value);
```

其中 `${column}` 会被直接替换，而 `#{value}` 会使用 `?` 预处理 



### 结果映射

* **resultType** /resultMap **必须要指定 哪怕是基本数据类型**

* ResultType MyBatis 会在幕后自动创建一个 `ResultMap` 

* ResultMap 的属性列表 

  | 属性          | 描述                                                         |
  | ------------- | ------------------------------------------------------------ |
  | `id`          | 当前命名空间中的一个唯一标识，用于标识一个结果映射。         |
  | `type`        | 类的完全限定名, 或者一个类型别名（关于内置的类型别名，可以参考上面的表格）。 |
  | `autoMapping` | 如果设置这个属性，MyBatis 将会为本结果映射开启或者关闭自动映射。 这个属性会覆盖全局的属性 autoMappingBehavior。默认值：未设置（unset）。 |

  * constructor

     - 用于在实例化类时，注入结果到构造方法中（需要实体类有对应构造方法）

    - `idArg` - ID 参数；标记出作为 ID 的结果可以帮助提高整体性能
    - `arg` - 将被注入到构造方法的一个普通结果

  * `id` – 一个 ID 结果；标记出作为 ID 的结果可以帮助提高整体性能

  * `result` – 注入到字段或 JavaBean 属性的普通结果

  * `association` – 一个复杂类型的关联；许多结果将包装成这种类型 (一对一)

    - 嵌套结果映射 – 关联可以是 `resultMap` 元素，或是对其它结果映射的引用

  * `collection` – 一个复杂类型的集合 (一对多)

    - 嵌套结果映射 – 集合可以是 `resultMap` 元素，或是对其它结果映射的引用

  * `discriminator` – 使用结果值来决定使用哪个 `resultMap` /`resultType`

    ​	`case` – 基于某些值的结果映射 

    - 嵌套结果映射 – `case` 也是一个结果映射，因此具有相同的结构和元素；或者引用其它的结果映射



```
<!-- 非常复杂的语句 -->
<select id="selectBlogDetails" resultMap="detailedBlogResultMap">
  select
       B.id as blog_id,
       B.title as blog_title,
       B.author_id as blog_author_id,
       A.id as author_id,
       A.username as author_username,
       A.password as author_password,
       A.email as author_email,
       A.bio as author_bio,
       A.favourite_section as author_favourite_section,
       P.id as post_id,
       P.blog_id as post_blog_id,
       P.author_id as post_author_id,
       P.created_on as post_created_on,
       P.section as post_section,
       P.subject as post_subject,
       P.draft as draft,
       P.body as post_body,
       C.id as comment_id,
       C.post_id as comment_post_id,
       C.name as comment_name,
       C.comment as comment_text,
       T.id as tag_id,
       T.name as tag_name
  from Blog B
       left outer join Author A on B.author_id = A.id
       left outer join Post P on B.id = P.blog_id
       left outer join Comment C on P.id = C.post_id
       left outer join Post_Tag PT on PT.post_id = P.id
       left outer join Tag T on PT.tag_id = T.id
  where B.id = #{id}
</select>
```

```
<!-- 非常复杂的结果映射 -->
<resultMap id="detailedBlogResultMap" type="Blog">
  <constructor>
    <idArg column="blog_id" javaType="int"/>
  </constructor>
  <result property="title" column="blog_title"/>
  <association property="author" javaType="Author">
    <id property="id" column="author_id"/>
    <result property="username" column="author_username"/>
    <result property="password" column="author_password"/>
    <result property="email" column="author_email"/>
    <result property="bio" column="author_bio"/>
    <result property="favouriteSection" column="author_favourite_section"/>
  </association>
  <collection property="posts" ofType="Post">
    <id property="id" column="post_id"/>
    <result property="subject" column="post_subject"/>
    <association property="author" javaType="Author"/>
    <collection property="comments" ofType="Comment">
      <id property="id" column="comment_id"/>
    </collection>
    <collection property="tags" ofType="Tag" >
      <id property="id" column="tag_id"/>
    </collection>
    <discriminator javaType="int" column="draft">
      <case value="1" resultType="DraftPost"/>
    </discriminator>
  </collection>
</resultMap>
```



​	Id 和 Result 的属性 

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `property`    | 映射到列结果的字段或属性。如果 JavaBean 有这个名字的属性（property），会先使用该属性。否则 MyBatis 将会寻找给定名称的字段（field）。 无论是哪一种情形，你都可以使用常见的点式分隔形式进行复杂属性导航。 比如，你可以这样映射一些简单的东西：“username”，或者映射到一些复杂的东西上：“address.street.number”。 |
| `column`      | 数据库中的列名，或者是列的别名。一般情况下，这和传递给 `resultSet.getString(columnName)` 方法的参数一样。 |
| `javaType`    | 一个 Java 类的全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。 |
| `jdbcType`    | JDBC 类型，所支持的 JDBC 类型参见这个表格之后的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可以为空值的列指定这个类型。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的全限定名，或者是类型别名。 |



构造器:

```
public class User {
   //...
   public User(Integer id, String username, int age) {
     //...
  }
//...
}
```

```
<constructor>
   <idArg column="id" javaType="int" name="id" />
   <arg column="age" javaType="_int" name="age" />
   <arg column="username" javaType="String" name="username" />
</constructor>
```

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `column`      | 数据库中的列名，或者是列的别名。一般情况下，这和传递给 `resultSet.getString(columnName)` 方法的参数一样。 |
| `javaType`    | 一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。 |
| `jdbcType`    | JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可能存在空值的列指定这个类型。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。 |
| `select`      | **用于加载复杂类型属性的映射语句的 ID，它会从 column 属性中指定的列检索数据，作为参数传递给此 select 语句**。具体请参考关联元素。 |
| `resultMap`   | 结果映射的 ID，可以将嵌套的结果集映射到一个合适的对象树中。 它可以作为使用额外 select 语句的替代方案。它可以将多表连接操作的结果映射成一个单一的 `ResultSet`。这样的 `ResultSet` 将会将包含重复或部分数据重复的结果集。为了将结果集正确地映射到嵌套的对象树中，MyBatis 允许你 “串联”结果映射，以便解决嵌套结果集的问题。想了解更多内容，请参考下面的关联元素。 |
| `name`        | 构造方法形参的名字。从 3.4.3 版本开始，通过指定具体的参数名，你可以以任意顺序写入 arg 元素。参看上面的解释。 |

**注意:一个属性不能通过name来指定,否则会报错**



### 关联

MyBatis 有两种不同的方式加载关联：

- 嵌套 Select 查询：通过执行另外一个 SQL 映射语句来加载期望的复杂类型。
- 嵌套结果映射：使用嵌套的结果映射来处理连接结果的重复子集

| 属性          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| `property`    | 映射到列结果的字段或属性。如果用来匹配的 JavaBean 存在给定名字的属性，那么它将会被使用。否则 MyBatis 将会寻找给定名称的字段。 无论是哪一种情形，你都可以使用通常的点式分隔形式进行复杂属性导航。 比如，你可以这样映射一些简单的东西：“username”，或者映射到一些复杂的东西上：“address.street.number”。 |
| `javaType`    | 一个 Java 类的完全限定名，或一个类型别名（关于内置的类型别名，可以参考上面的表格）。 如果你映射到一个 JavaBean，MyBatis 通常可以推断类型。然而，如果你映射到的是 HashMap，那么你应该明确地指定 javaType 来保证行为与期望的相一致。 |
| `jdbcType`    | JDBC 类型，所支持的 JDBC 类型参见这个表格之前的“支持的 JDBC 类型”。 只需要在可能执行插入、更新和删除的且允许空值的列上指定 JDBC 类型。这是 JDBC 的要求而非 MyBatis 的要求。如果你直接面向 JDBC 编程，你需要对可能存在空值的列指定这个类型。 |
| `typeHandler` | 我们在前面讨论过默认的类型处理器。使用这个属性，你可以覆盖默认的类型处理器。 这个属性值是一个类型处理器实现类的完全限定名，或者是类型别名。 |

#### 关联的嵌套 Select 查询

| 属性        | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| `column`    | 数据库中的列名，或者是列的别名。一般情况下，这和传递给 `resultSet.getString(columnName)` 方法的参数一样。 注意：在使用复合主键的时候，你可以使用 `column="{prop1=col1,prop2=col2}"` 这样的语法来指定多个传递给嵌套 Select 查询语句的列名。这会使得 `prop1` 和 `prop2` 作为参数对象，被设置为对应嵌套 Select 语句的参数。 |
| `select`    | 用于加载复杂类型属性的映射语句的 ID，它**会从 column 属性指定的列中检索数据，作为参数传递给目标 select 语句**。 具体请参考下面的例子。注意：在使用复合主键的时候，你可以使用 `column="{prop1=col1,prop2=col2}"` 这样的语法来指定多个传递给嵌套 Select 查询语句的列名。这会使得 `prop1` 和 `prop2` 作为参数对象，被设置为对应嵌套 Select 语句的参数。 |
| `fetchType` | 可选的。有效值为 `lazy` 和 `eager`。 指定属性后，将在映射中忽略全局配置参数 `lazyLoadingEnabled`，使用属性的值。 |

```
<resultMap id="blogResult" type="Blog">
  <association property="author" column="author_id" javaType="Author" select="selectAuthor"/>
</resultMap>

<select id="selectBlog" resultMap="blogResult">
  SELECT * FROM BLOG WHERE ID = #{id}
</select>

<select id="selectAuthor" resultType="Author">
  SELECT * FROM AUTHOR WHERE ID = #{id}
</select>
先用传进来的id查到BLOG表数据，在用BLOG表中author_id去 author表中拿到所有数据 复制给JavaBean对象
```

或者（**以下两种方法需要保证两个表中的字段不能同名，不然会用外层的该字段数据**）

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author" column="blog_author_id" javaType="Author" resultMap="authorResult"/>
</resultMap>

<resultMap id="authorResult" type="Author">
  <id property="id" column="author_id"/>
  <result property="username" column="author_username"/>
  <result property="password" column="author_password"/>
  <result property="email" column="author_email"/>
  <result property="bio" column="author_bio"/>
</resultMap>
```

或者

> ```
> <resultMap id="blogResult" type="Blog">
>   <id property="id" column="blog_id" />
>   <result property="title" column="blog_title"/>
>   <association property="author" javaType="Author">
>     <id property="id" column="author_id"/>
>     <result property="username" column="author_username"/>
>     <result property="password" column="author_password"/>
>     <result property="email" column="author_email"/>
>     <result property="bio" column="author_bio"/>
>   </association>
> </resultMap>
> ```

| `columnPrefix` | 当连接多个表时，你可能会不得不使用列别名来避免在 `ResultSet` 中产生重复的列名。指定 columnPrefix 列名前缀允许你将带有这些前缀的列映射到一个外部的结果映射中。 详细说明请参考后面的例子。 |
| -------------- | ------------------------------------------------------------ |
|                |                                                              |

```
<resultMap id="blogResult" type="Blog">
  <id property="id" column="blog_id" />
  <result property="title" column="blog_title"/>
  <association property="author"
    resultMap="authorResult" />
  <association property="coAuthor"
    resultMap="authorResult"
    columnPrefix="co_" />
</resultMap>
```

```
<select id="selectBlog" resultMap="blogResult">
  select
    B.id            as blog_id,
    B.title         as blog_title,
    A.id            as author_id,
    A.username      as author_username,
    A.password      as author_password,
    A.email         as author_email,
    A.bio           as author_bio,
    CA.id           as co_author_id,
    CA.username     as co_author_username,
    CA.password     as co_author_password,
    CA.email        as co_author_email,
    CA.bio          as co_author_bio
  from Blog B
  left outer join Author A on B.author_id = A.id
  left outer join Author CA on B.co_author_id = CA.id
  where B.id = #{id}
</select>
```



关联的多结果集 ResultSet 现在不用了

### 集合

和关联基本相同 注意: javaType="ArrayList" => ofType="Post"



### 鉴别器(有问题 基本不用)

```
<resultMap id="vehicleResult" type="Vehicle">
  <id property="id" column="id" />
  <result property="vin" column="vin"/>
  <result property="year" column="year"/>
  <result property="make" column="make"/>
  <result property="model" column="model"/>
  <result property="color" column="color"/>
  <discriminator javaType="int" column="vehicle_type">
    <case value="1" resultType="carResult">
      <result property="doorCount" column="door_count" />
    </case>
    <case value="2" resultType="truckResult">
      <result property="boxSize" column="box_size" />
      <result property="extendedCab" column="extended_cab" />
    </case>
    <case value="3" resultType="vanResult">
      <result property="powerSlidingDoor" column="power_sliding_door" />
    </case>
    <case value="4" resultType="suvResult">
      <result property="allWheelDrive" column="all_wheel_drive" />
    </case>
  </discriminator>
</resultMap>
```



### 自动映射

当自动映射查询结果时，MyBatis 会获取结果中返回的列名并在 Java 类中查找相同名字的属性（忽略大小写）。 这意味着如果发现了 *ID* 列和 *id* 属性，MyBatis 会将列 *ID* 的值赋给 *id* 属性。

通常数据库列使用大写字母组成的单词命名，单词间用下划线分隔；而 Java 属性一般遵循驼峰命名法约定。为了在这两种命名方式之间启用自动映射，需要将 `mapUnderscoreToCamelCase` 设置为 true（默认是false）。

```
<settings>
    <setting name="mapUnderscoreToCamelCase" value="true"/>
</settings>
```

默认autoMapping true



## 日志系统

![2](C:\Users\Administrator\Desktop\复习\素材\pic\mybatis\2.jpg)

基础库升到slf4j标准  

* log4j+slf4j-log4j12

  classpath log4j.properties

  \#    全局配置

  log4j.rootCategory=ERROR, stdout

  \#    Mybatis 日志配置

  log4j.logger.【tk.mybatis.simple.mapper】[mapper路径]=TRACE

  \#    控制台输出配置

  log4j.appender.stdout = org.apache.log4j.ConsoleAppender
  log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
  log4j.appender.stdout.layout.ConversionPattern = %-20d{yyyy-MM-dd HH\:mm\:ss}[%-5p](%c) (%F\:%L) - %m%n

* log4j(version 2.+)+log4j-slf4j-impl

* logback

## 缓存

* 适合用缓存的: 经常查询，更改频率较低的且数据的正确与否对结果影响不大的

* 一级缓存: 当执行查询后 结果会存到sqlsession为我们提供的区域中(Map)，当我们再次执行**相同的查询**是,mybatis首先会在sqlsession里查询是不是有，有就直接拿来用.清除就用sqlsession.clearCacehe

  * 默认开启
  * 增删改操作会让一级缓存失效
  * sqlsession.commit()会导致清空缓存 等同于sqlsession.clearCacehe

* 二级缓存: 二级缓存是namespace级别的   要启用全局的二级缓存，只需要在你的 SQL 映射文件中添加一行(<setting name="CacheEnabled" value="true"/>(默认true，只影响第二级缓存))：

  ```
  <cache/>
  ```

  以上两种共同作用开启二级缓存

  工作机制

  * 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中；
  * **如果当前会话关闭了，这个会话对应的一级缓存就没了；但是我们想要的是，会话关闭了，一级缓存中的数据被保存到二级缓存中；**
  * 新的会话查询信息，就可以从二级缓存中获取内容；
  * 不同的mapper(比如UserMapper，AccountMapper)查出的数据会放在自己对应的缓存（map）中

  

  效果:

  - 映射语句文件中的所有 select 语句的结果将会被缓存。
  - 映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存(**刷新和失效只是在以及缓存失效，移到二级缓存中重新生效**)。
  - 缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。
  - 缓存不会定时进行刷新（也就是说，没有刷新间隔）。
  - 缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
  - 缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。

```
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>
```

* 但你可能会想要在多个命名空间中共享相同的缓存配置和实例。要实现这种需求，你可以使用 cache-ref 元素来引用另一个缓存。

```
<cache-ref namespace="com.someone.application.data.SomeMapper"/>
```



## 动态sql

* if

  ```
    <if test="title != null">
      AND title like #{title}
    </if>
  ```

* ### choose、when、otherwise

  ```
   <choose>
      <when test="title != null">
        AND title like #{title}
      </when>
      <when test="author != null and author.name != null">
        AND author_name like #{author.name}
      </when>
      <otherwise>
        AND featured = 1
      </otherwise>
    </choose>
  ```

* ### where trim set

  ***where* 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，*where* 元素也会将它们去除。** 

  

  以通过自定义 trim 元素来定制 *where* 元素的功能。比如，和 *where* 元素等价的自定义 trim 元素为：

  ```
  <trim prefix="WHERE" prefixOverrides="AND |OR ">
    ...
  </trim>
  ```

​      suffixOverrides 表示最后一个

​	P.S: 遍历map的时候 需要写collection='map' 直接传参数 可以直接写对应的key

​		单个参数 list 没用@Param 默认 list   map可以直接取#{Property}(多个参数时 ${map3.id})

​		传入多个参数是 没用@Param 则默认 arg1, arg0, param1, param2



​	用于动态更新语句的类似解决方案叫做 *set*。*set* 元素可以用于动态包含需要更新的列，忽略其它不更新的列。比如：

```
<update id="updateAuthorIfNecessary">
  update Author
    <set>
      <if test="username != null">username=#{username},</if>
      <if test="password != null">password=#{password},</if>
      <if test="email != null">email=#{email},</if>
      <if test="bio != null">bio=#{bio}</if>
    </set>
  where id=#{id}
</update>
```



* ### foreach

   你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 *foreach*。当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。 

* ### script

  ```
  @Update({"<script>",
        "update Author",
        "  <set>",
        "    <if test='username != null'>username=#{username},</if>",
        "    <if test='password != null'>password=#{password},</if>",
        "    <if test='email != null'>email=#{email},</if>",
        "    <if test='bio != null'>bio=#{bio}</if>",
        "  </set>",
        "where id=#{id}",
        "</script>"})
      void updateAuthorValues(Author author);
  ```

* ### bind

  `bind` 元素允许你在 OGNL 表达式以外创建一个变量，并将其绑定到当前的上下文。比如：

  ```
  <select id="selectBlogsLike" resultType="Blog">
    <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
    SELECT * FROM BLOG
    WHERE title LIKE #{pattern}
  </select>
  ```

* ### databaseId

  ```
  <databaseIdProvider type="DB_VENDOR">
      <property name="MySQL" value="mysql"/>
      <property name="DB2" value="db2"/>
  </databaseIdProvider>
  ```

```
<if test="_databaseId=='mysql'">
    select * from User
    <where>
        <if test="a!=null">
            AND sex=#{a}
        </if>
        <if test="b!=null">
            AND address like #{b}
        </if>
    </where>
</if>
```



## javaApI

#### SqlSessionFactoryBuilder

SqlSessionFactory build(InputStream inputStream)

SqlSessionFactory build(InputStream inputStream, String environment)

SqlSessionFactory build(InputStream inputStream, Properties properties)

SqlSessionFactory build(InputStream inputStream, String env, Properties props)

SqlSessionFactory build(Configuration config)



#### SqlSessionFactory

SqlSessionFactory 有六个方法创建 SqlSession 实例。通常来说，当你选择其中一个方法时，你需要考虑以下几点：

- **事务处理**：你希望在 session 作用域中使用事务作用域，还是使用自动提交（auto-commit）？隔离级别?（对很多数据库和/或 JDBC 驱动来说，等同于关闭事务支持）
- **数据库连接**：你希望 MyBatis 帮你从已配置的数据源获取连接，还是使用自己提供的连接？
- **语句执行**：你希望 MyBatis 复用 PreparedStatement 和/或批量更新语句（包括插入语句和删除语句）吗？

基于以上需求，有下列已重载的多个 openSession() 方法供使用。

```
SqlSession openSession()
SqlSession openSession(boolean autoCommit)
SqlSession openSession(Connection connection)
SqlSession openSession(TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType, TransactionIsolationLevel level)
SqlSession openSession(ExecutorType execType) //SIMPLE,REUSE,BATCH
SqlSession openSession(ExecutorType execType, boolean autoCommit) 
SqlSession openSession(ExecutorType execType, Connection connection)
Configuration getConfiguration();
```

默认的 openSession() 方法没有参数，它会创建具备如下特性的 SqlSession：

- 事务作用域将会开启（也就是不自动提交）。

- 将由当前环境配置的 DataSource 实例中获取 Connection 对象。

- 事务隔离级别将会使用驱动或数据源的默认设置。

- 预处理语句不会被复用，也不会批量处理更新。

  

- `ExecutorType.SIMPLE`：该类型的执行器没有特别的行为。它为每个语句的执行创建一个新的预处理语句。
- `ExecutorType.REUSE`：该类型的执行器会复用预处理语句。
- `ExecutorType.BATCH`：该类型的执行器会批量执行所有更新语句，如果 SELECT 在多个更新中间执行，将在必要时将多条更新语句分隔开来，以方便理解。



## 基于注解

```
 @Results(id="selectById",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "name",property = "name",javaType =String.class,jdbcType= JdbcType.VARCHAR),
            @Result(column = "user3_id",property = "user3Id"),
            @Result(column = "roundingMode",property = "roundingMode",typeHandler = EnumOrdinalTypeHandler.class),
            @Result(property="author",column="author_id",javaType= Author.class
                    ,one =@One(resultMap = "org.example.mybatis.mapper.User2Mapper.authorRes"))

    })
    @Select("select * from user2 where id=${map3.id}")
```

```
    <resultMap id="authorRes" type="Author">
        <id property="id" column="author_id"/>
    </resultMap>
```

