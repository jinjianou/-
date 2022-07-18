# Spring

![4](C:\Users\Administrator\Desktop\复习\素材\pic\spring\4.jpg)

![5](C:\Users\Administrator\Desktop\复习\素材\pic\spring\5.jpg)

Environment:System+spring
BeanFactory:反射创建对象，并反射配置属性并在生命周期内进行多次回调





* BeanFactory vs FactoryBean

  BeanFactory是接口，提供了IOC容器最基本的形式，给具体的IOC容器的实现提供了规范，

  FactoryBean也是接口，为IOC容器中Bean的实现提供了更加灵活的方式，FactoryBean在IOC容器的基础上给Bean的实现加上了一个简单工厂模式和装饰模式(如果想了解装饰模式参考：[修饰者模式(装饰者模式，Decoration)](https://www.cnblogs.com/aspirant/p/9083082.html) 我们可以在getObject()方法中灵活配置。其实在Spring源码中有很多FactoryBean的实现类

* **有状态对象(Stateful Bean)** ：就是有实例变量的对象，可以保存数据，是非线程安全的。 

  **无状态对象(Stateless Bean)**：就是没有实例变量的对象，不能保存数据，是不变类，是线程安全的。 

  Spring使用ThreadLocal解决线程安全问题。我们知道在一般情况下，只有无状态的Bean才可以在多线程环境下共享，在Spring中，绝大部分Bean都可以声明为singleton作用域。就是因为Spring对一些Bean（如RequestContextHolder、TransactionSynchronizationManager、LocaleContextHolder等）中非线程安全状态采用ThreadLocal进行处理，让它们也成为线程安全的状态，因此有状态的Bean就可以在多线程中共享了。

* Prototype creates a brand new instance everytime you call getBean on the ApplicationContext. Whereas for Request, only one instance is created for an HttpRequest. (在一次Http请求中可以有多个getBean)

* ThreadLocal

  This class provides thread-local variables. These variables differ from their normal counterparts(同行) in that each thread that accesses one (via its get or set method) has its own, independently initialized copy of the variable. ThreadLocal instances are typically private static fields in classes that wish to associate state with a thread (e.g., a user ID or Transaction ID).

* 

# SpringBoot 概念

![6](C:\Users\Administrator\Desktop\复习\素材\pic\spring\6.jpg)



## 为什么使用sb

1. 使开发变得简单，提供了丰富的可快速集成的解决方案
2. 使配置变得简单，提供了丰富的 Starters 
3.  使部署变得简单，其本身内嵌启动容器 ，且结合jekins、dokcer自动化运维变得简单
4.  使监控变得简单，自带监控组件Actuator 

## 四大核心特性

1. 自动装配： 简单配置甚至0配置即可运行项目

   模块之间基于接口编程（而非实现类硬编码）

   通过spi机制（为某个接口寻找服务实现）实现

   

2. 起步依赖：场景启动器

3. Actuator：指标监控

4. cli

# Hello world

```java
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.4.RELEASE</version>
    </parent>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
```

## 依赖文件分析

```
spring-boot-starter-parent
	build>resources&pluginManagement  不涉及dependencies
	<resource>
        <directory>${basedir}/src/main/resources</directory>
        <filtering>true</filtering>
        <includes>
          <include>**/application*.yml</include>
          <include>**/application*.yaml</include>
          <include>**/application*.properties</include>
        </includes>
      </resource>

spring-boot-dependencies---parent of spring-boot-starter-parent
	dependencyManagement+pluginManagement

spring-boot-starter 四大核心特性之一
	dependencies:spring-boot+spring-boot-autoconfigure+spring-boot-starter-logging+jakarta.annotation-api+spring-core+snakeyaml

	stater组件 以功能为维度，维护对应jar包的版本依赖；导入组件时，将jar包所有依赖导入
	官方组件 spring-boot-starter-xxx
	第三方  xxx-spring-boot-starter
	
spring-boot-starter-web
	dependencies:spring-boot-starter+spring-boot-starter-json+spring-boot-starter-tomcat+spring-web+spring-webmvc
```

1.  basedir是maven内置属性

   ```
   ${project.build.sourceDirectory}:项目的主源码目录，默认为src/main/java/.
   ${project.build.testSourceDirectory}:项目的测试源码目录，默认为/src/test/java/.
   ${project.build.directory}:项目构建输出目录，默认为target/.
   ${project.outputDirectory}:项目主代码编译输出目录，默认为target/classes/.
   ${project.testOutputDirectory}:项目测试代码编译输出目录，默认为target/testclasses/.
   ${project.groupId}:项目的groupId.
   ${project.artifactId}:项目的artifactId.
   ${project.version}:项目的version,于${version}等价 
   ${project.build.finalName}:项目打包输出文件的名称，默认 为${project.artifactId}${project.version}.
   ```

   \${project.basedir} 同${basedir} 项目根目录即含有pom.xml文件的目录 

     <properties> 是自定义属性

2. 不添加 <filtering>true</filtering>

   打包时能替换文件名，但不能替换文件里面的标识符，启动项目时报错

   如<include>application-de${active_yml}.yml</include>  ->  application-dev.yml



​	添加 <filtering>true</filtering>

​	打包时能替换文件名，也能替换文件里面的标识符

## 热部署

深层原理是使用了两个ClassLoader，**一个Classloader加载那些不会改变的类（第三方Jar包）**，**另一个ClassLoader加载会更改的类**，称为restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间。 

```
<!--添加热部署-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
    <scope>true</scope>
</dependency>
```

```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
    <!--fork:如果没有该项配置,整个devtools不会起作用-->
    <!--复制当前进程（包括进程在内存里的堆栈数据）为1个新的镜像  两个进程是互不影响-->
        <fork>true</fork>
    </configuration>
 </plugin>
```

File-settings-compiler 勾选 Build Project automatically

ctrl+shift+alt+/ 选中 registry 勾选 Compiler autoMake allow when app run 

在新的页面刷新

## @SpringApplication分析

![7](C:\Users\Administrator\Desktop\复习\素材\pic\spring\7.jpg)

* AutoConfigurationImportSelector implements DeferredImportSelector（延迟导入选择器）

  ```
  @Import
  	Indicates one or more component classes to import  equivalent to the <import/> element in Spring XML
  ImportSelector 
  determine which @Configuration class(es) should be imported based on a given selection criteria
  	selectImports(AnnotationMetadata importingClassMetadata) class(es) should be imported based on the AnnotationMetadata of the importing @Configuration class.
  ```

  ```
  @Override
  public String[] selectImports(AnnotationMetadata annotationMetadata) {
     // 判断SpringBoot是否开启自动配置
     if (!isEnabled(annotationMetadata)) {
        return NO_IMPORTS;
     }
     // 获取需要被引入的自动配置信息
     AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(annotationMetadata);
     return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
  }
  
  ```

  

  isEnabled

  ```
  protected boolean isEnabled(AnnotationMetadata metadata) {
     if (getClass() == AutoConfigurationImportSelector.class) {
        // 若调用该方法的类是AutoConfigurationImportSelector，那么就获取EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY的值，默认为true
        return getEnvironment().getProperty(EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY, Boolean.class, true);
     }
     return true;
  }
  
  ```

  EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY= "spring.boot.enableautoconfiguration"; 

  

  getAutoConfigurationEntry

  ```
  protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
     // 判断是否开启自动配置
     if (!isEnabled(annotationMetadata)) {
        return EMPTY_ENTRY;
     }
     // 获取@EnableAutoConfiguration注解的属性
     AnnotationAttributes attributes = getAttributes(annotationMetadata);
     // 从spring.factories文件中获取配置类的全限定名数组
     List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
     // 去重
     configurations = removeDuplicates(configurations);
     // 获取注解中exclude或excludeName排除的类集合
     Set<String> exclusions = getExclusions(annotationMetadata, attributes);
     // 检查被排除类是否可以实例化，是否被自动配置所使用，否则抛出异常
     checkExcludedClasses(configurations, exclusions);
     // 去除被排除的类
     configurations.removeAll(exclusions);
     // 使用spring.factories配置文件中配置的过滤器对自动配置类进行过滤
     configurations = getConfigurationClassFilter().filter(configurations);
     // 抛出事件
     fireAutoConfigurationImportEvents(configurations, exclusions);
     return new AutoConfigurationEntry(configurations, exclusions);
  }
  
  ```

  

  getCandidateConfigurations

* ```java
  String factoryTypeName = factoryType.getName();
          return (List)loadSpringFactories(classLoaderToUse).getOrDefault(factoryTypeName, Collections.emptyList());
  ```

  loadSpringFactories读到了spring-boot/spring-boot-autoConfigure/spring-beans下 META-INF/factories

  ​	Enumeration<URL> urls = classLoader.getResources(FACTORIES_RESOURCE_LOCATION);

  通过getOrDefault过滤类型为factoryTypeName即EnableAutoConfiguration

  

  removeDuplicates

  ```
  return new ArrayList<>(new LinkedHashSet<>(list));
  ```

  

  getExclusions

  ```
  		excluded.addAll(asList(attributes, "exclude"));
  		excluded.addAll(Arrays.asList(attributes.getStringArray("excludeName")));
  		excluded.addAll(getExcludeAutoConfigurationsProperty());
  ```

  EnableAutoConfiguration的exclude&excludeName属性 以及spring.autoconfigure.exclude

  

  checkExcludedClasses

  ```
  for (String exclusion : exclusions) {
     if (ClassUtils.isPresent(exclusion, getClass().getClassLoader()) && !configurations.contains(exclusion)) {
        invalidExcludes.add(exclusion);
     }
  }
  		if (!invalidExcludes.isEmpty()) {
  		//throw error
  			handleInvalidExcludes(invalidExcludes);
  		}
  ```

  

  getConfigurationClassFilter().filter(configurations);

  ```
  			List<AutoConfigurationImportFilter> filters = getAutoConfigurationImportFilters();
  				return SpringFactoriesLoader.loadFactories(AutoConfigurationImportFilter.class, this.beanClassLoader);
  
  
  this.configurationClassFilter = new 
  			ConfigurationClassFilter(this.beanClassLoader, filters);
  
  return this.configurationClassFilter
  
  filter
  for (AutoConfigurationImportFilter filter : this.filters) {
  				boolean[] match = filter.match(candidates, this.autoConfigurationMetadata);
  				for (int i = 0; i < match.length; i++) {
  					if (!match[i]) {
  						candidates[i] = null;
  						skipped = true;
  					}
  				}
  			}
  			
  			if (!skipped) {
  				return configurations;
  			}
  			List<String> result = new ArrayList<>(candidates.length);
  			for (String candidate : candidates) {
  				if (candidate != null) {
  					result.add(candidate);
  				}
  			}
  			return result;
  ```

  this.autoConfigurationMetadata来自META-INF/spring-autoconfigure-metadata.properties

  最后在进行一次过滤getConfigurationClassFilter().filter(configurations) 

  ```
  org.springframework.boot.autoconfigure.AutoConfigurationImportFilter=\
  org.springframework.boot.autoconfigure.condition.OnBeanCondition,\
  org.springframework.boot.autoconfigure.condition.OnClassCondition,\
  org.springframework.boot.autoconfigure.condition.OnWebApplicationCondition
  ```

  根据我们添加的starter和上述condition filter

  注意：Enumeration<URL> urls = classLoader.getResources(FACTORIES_RESOURCE_LOCATION);

  ​		拿到的是文件路径 如jar:file:/C:/Users/Administrator/.m2/repository/io/springfox/springfox-boot-starter/3.0.0/springfox-boot-starter-3.0.0.jar!/META-INF/spring.factories

  ​	xxx.factories相当于  xxx.properties




​	fireAutoConfigurationImportEvents(configurations, exclusions);



* FilterType枚举值分析

  * ANNOTATION 根据注解类型进行过滤
  * ASSIGNABLE_TYPE 给定类型（本身及子类）
  * ASPECTJ  按照ASPECTJ表达式 
  * REGEX 按照正则
  * CUSTOMER 自定义  实现org.springframework.core.type.filter.TypeFilter 

* componentScan原理

  ![8](C:\Users\Administrator\Desktop\复习\素材\pic\spring\8.jpg)

* diagrams 查看包依赖图标

* HTTPEncodingAutoConfiguration debug=true调试

  ```
  @Configuration(proxyBeanMethods = false)
  Specify whether {@code @Bean} methods should get proxied in order to enforce bean lifecycle behavior e.g. to return shared singleton bean instances even in case of direct {@code @Bean} method calls in user code.
  CGLIB subclass processing
  boolean proxyBeanMethods() default true；
  false会每次都取调用Bean方法创建实例而不是去IOC容器中找
  
  
  @EnableConfigurationProperties(ServerProperties.class)
  Enable support for @ConfigurationProperties annotated beans
  @ConfigurationProperties
  if you want to bind and validate some external Properties
  
  @ConditionalOnxxx
  	@Condition(xxx.class)
  Spring Boot includes a number of @Conditional annotations that you can reuse in your own code by annotating @Configuration classes or individual @Bean methods. These annotations include:
  
  Class Conditions
  
  Bean Conditions
  
  Property Conditions
  
  Resource Conditions
  
  Web Application Conditions
  
  SpEL Expression Conditions
  
  
  @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
  @ConditionalOnClass(CharacterEncodingFilter.class)
  @ConditionalOnProperty(prefix = "server.servlet.encoding", value = "enabled", matchIfMissing = true)  == server.servlet.encoding.enabled=false 没配置就默认true
  ```

  ```jav
  @ConfigurationProperties(prefix = "server", ignoreUnknownFields = true)
  public class ServerProperties 
  默认读取 Spring's Environment 如application.xxx
  可以通过@PropertySource注解指定自定义配置文件路径 添加到 Spring's Environment. 
  ```

  ```
  properties(ServerProperties).getServlet().getEncoding().getCharset()  server.servlet.encoding.charset
  ```

* 使用spring.io初始化的时候

  * 父maven pom 子maven project

  *  需要修改子模块的parent

  * resources目录结构

    ![9](C:\Users\Administrator\Desktop\复习\素材\pic\spring\9.jpg)

## 配置文件

doc参考 2.4.0的版本 https://docs.spring.io/spring-boot/docs/2.4.0/reference/html/spring-boot-features.html#boot-features-external-config-enabling

* properties 扁平k=v

* yml 树形结构

  * 基本语法

    ![10](C:\Users\Administrator\Desktop\复习\素材\pic\spring\10.jpg)

  * 例子

    ```
    user:
      username: jinjianou
      password: 123456
      hobbies: [唱歌,跳舞]
    #    - 唱歌
    #    - 跳舞
      depedencies: {
         a: 1,
         b: s
      }
      address:
        id: 1
        address: 浙江
    ```

  ### profile的配置与激活

  1. 配置

     - 在application中配置

       spring.profiles.active=dev  # 对应application-dev文件

       

       在cspring-boot-maven-plugin  configuration中配置(可选)

       ```
       <build>
               <plugins>
                   <plugin>
                       <groupId>org.springframework.boot</groupId>
                       <artifactId>spring-boot-maven-plugin</artifactId>
                       <configuration>
                           <profiles>
                               <profile>dev</profile>
                               <profile>test</profile>
                           </profiles>
                       </configuration>
                   </plugin>
               </plugins>
           </build>
       ```

       

  2. 激活(spring)

     虚拟机参数 在VM options指定: -Dspring.profiles.active=dev 

     命令行参数 java-jar xxx.jar --spring.profiles.active=dev 

     

     mvn spring-boot:run -Dspring-boot.run.profiles=test

  ###  加载顺序 

  * So the first block will copy all `application*.(yml|yaml|properties)` files to the output folder, and will interpolate(插入)variables. And the second block will copy all other files, without interpolating variables. 

    ```
    <resource>
    <filtering>true</filtering>
    <directory>${basedir}/src/main/resources</directory>
    <includes>
    <include>**/application*.yml</include>
    <include>**/application*.yaml</include>
    <include>**/application*.properties</include>
    </includes>
    </resource>
    <resource>
    <directory>${basedir}/src/main/resources</directory>
    <excludes>
    <exclude>**/application*.yml</exclude>
    <exclude>**/application*.yaml</exclude>
    <exclude>**/application*.properties</exclude>
    </excludes>
    </resource>
    ```

  * first由jar包外向jar包内进行寻找，second  resources/config/  third优先加载待profile的(application-{profile}.*,**profile需要初始的配置如application.yml开启选择的profile**)，再加载不带profile的，

    同一级别的加载顺序和版本有关  

    2.1.4 properties>yml>yaml

    2.4.0   yaml>yml>properties

    

    一般采用resources/resources+config/命令行（如--spring.config.location=D:\config/ 不会跟默认的配置文件互补）这三种方式

    ​			

  * 外部配置的优先级

    1. Default properties (specified by setting `SpringApplication.setDefaultProperties`) 与高级别互补

       ```
       application.setDefaultProperties(Properties|Map)
       
       Properties.load
       ```

    2. [`@PropertySource`](https://docs.spring.io/spring/docs/5.3.1/javadoc-api/org/springframework/context/annotation/PropertySource.html) annotations on your `@Configuration` classes. Please note that such property sources are not added to the `Environment` until the application context is being refreshed. This is too late to configure certain properties such as `logging.*` and `spring.main.*` which are read before refresh begins.

    3. Config data (such as `application.properties` files) 约定

    4. A `RandomValuePropertySource` that has properties only in `random.*`.

    5. OS environment variables.

       通过配置文件路径的方式，不会跟约定配置文件互补

       a. idea

       默认去找该文件夹下约定的配置文件

       ![11](C:\Users\Administrator\Desktop\复习\素材\pic\spring\11.jpg)

       b. windows

       set spring.config.location=D:\config/

       java -jar ...

    6. Java System properties (`System.getProperties()`).

    7. JNDI attributes from `java:comp/env`.

    8. `ServletContext` init parameters.

    9. `ServletConfig` init parameters.

    10. Properties from `SPRING_APPLICATION_JSON` (inline JSON embedded in an environment variable or system property).

    11. Command line arguments.

    12. `properties` attribute on your tests. Available on [`@SpringBootTest`](https://docs.spring.io/spring-boot/docs/2.4.0/api/org/springframework/boot/test/context/SpringBootTest.html) and the [test annotations for testing a particular slice of your application](https://docs.spring.io/spring-boot/docs/2.4.0/reference/html/spring-boot-features.html#boot-features-testing-spring-boot-applications-testing-autoconfigured-tests).

    13. [`@TestPropertySource`](https://docs.spring.io/spring/docs/5.3.1/javadoc-api/org/springframework/test/context/TestPropertySource.html) annotations on your tests.

    14. [Devtools global settings properties](https://docs.spring.io/spring-boot/docs/2.4.0/reference/html/using-spring-boot.html#using-boot-devtools-globalsettings) in the `$HOME/.config/spring-boot` directory when devtools is active.

  * 属性注入

    * spel  ${.....} 一个个注入

    * @ConfigurationProperties(prefix="....") 绑定配置文件

      Annotation for externalized configuration. Add this to a class definition or a @Bean method in a @Configuration class if you want to bind and validate some external Properties (e.g. from a .properties file).

      * 支持松散绑定

      | Property                            | Note                                                         |
      | ----------------------------------- | ------------------------------------------------------------ |
      | `acme.my-project.person.first-name` | Kebab case, which is recommended for use in `.properties` and `.yml` files. |
      | `acme.myProject.person.firstName`   | Standard camel case syntax.                                  |
      | `acme.my_project.person.first_name` | Underscore notation, which is an alternative format for use in `.properties` and `.yml` files. |
      | `ACME_MYPROJECT_PERSON_FIRSTNAME`   | Upper case format, which is recommended when using system environment variables. |

      * ![12](C:\Users\Administrator\Desktop\复习\素材\pic\spring\12.jpg)

        前者虽然不支持spel 但支持${xx.xx}引用

      * 配置随机值

        ```yam
        my:
          secret: "${random.value}"
          number: "${random.int}"
          bignumber: "${random.long}"
          uuid: "${random.uuid}"
          number-less-than-ten: "${random.int(10)}"
          number-in-range: "${random.int[1024,65536]}"
          
          使用时不需要加“”
        ```

      * 配置验证

        JSR-303  javax.validation   @Validated 

        * **ensure that a compliant JSR-303 implementation is on your classpath** and then add constraint annotations to your fields 

          **spring-boot-starter-validation** 

        

    * 测试 @SpringBootTest

      * @BootstrapWith defines class-level metadata that is used to determine how to bootstrap the Spring TestContext Framework

      * @ExtendWith

      * 必须保证test的层级目录和@SpringBootApplication的相同

      * ​    spring boot 2.2之前使用的是 Junit4 org.junit.Test

        ​    spring boot 2.2之后使用的是 Junit5 org.junit.jupiter.api.Test

  ## 日志系统

  ### java

  ![14](C:\Users\Administrator\Desktop\复习\素材\pic\spring\14.jpg)

  ### SpringBoot

  ![15](C:\Users\Administrator\Desktop\复习\素材\pic\spring\15.jpg)

  ![16](C:\Users\Administrator\Desktop\复习\素材\pic\spring\16.jpg)



## Web开发

* springMVC快速使用

  ```java
  @RestController
  @RequestMapping("/user")
  ```

  * 调用rest http 通过RestTemplate 堵塞式

    Synchronous client to **perform HTTP requests, exposing a simple, template method API** over underlying HTTP client libraries such as the JDK HttpURLConnection, Apache HttpComponents, and others

    The auto-configured `RestTemplateBuilder` ensures that sensible **`HttpMessageConverters` are applied to `RestTemplate` instances**  HttpMessageConverters会将请求对象->jsonStr，接受方需要加上@requestBody

    **尽量减少直接依赖同一项目下别的模块（减少耦合，同时防止配置文件的冲突）**

    allow parallel run

    ```
    private final RestTemplate restTemplate;
    /*when a class without non-args constructor,springBoot will
    * autoWired the parameter for the constructor
    * add @Autowired specific constructor to instance
     */
    public RestDemoController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    @GetMapping("/{id}")
    public Result rest(@PathVariable Integer id){
        // 1st url  2nd response classType 3rd parameter
       return this.restTemplate.getForObject(
                "http://localhost:8088/user/{id}"
                    , Result.class,id);
    }
    ```
    ![19](C:\Users\Administrator\Desktop\复习\素材\pic\spring\19.jpg)

    **@RequestParam bean 或者 bean 都能拿到params和body(前提是k-v，json不行)**

    ​	 推荐 RequestParam @RequestParam json @RequestBody

    **Sping-boot-starter-json使用的jackson**

       解决null也显示

    * 类上 @JsonInclude(JsonInclude.Include.NON_NULL)

    * 全局 application.* 

      * JacksonAutoConfiguration.Jackson2ObjectMapperBuilderCustomizerConfiguration

        spring.jackson.defaultPropertyInclusion=NON_NULL

  * webclient  都可以调用远程服务 依赖webflux(响应式无堵塞)

  * 工具：

    * postman

    * apipost

    * MockMvc

      由spring-test提供，实现了对Http请求的模拟，能够直接使用网络的形式，转换到对controller的调用，是的测试**不依赖网络环境**（不需要项目启动web容器），测试速度快，同时提供了一套验证工具。

      @SpringBootTest 

      @AutoConfigureWebTestClient 

      * class MockMvc :all
      * class WebTestClient: focus on  web layer (WebFlux  )

      ```java
      @SpringBootTest
      @AutoConfigureMockMvc //专注于mockMvc 依赖junit5
      public class MockMvcTest {
      
          @Autowired
          private MockMvc mockMvc;
      
          @Test
          public void test() throws Exception {
              //发起模拟请求，不需要启动web应用
              mockMvc.perform(
                      //get MockHttpServletRequestBuilder
                      //'url' should start with a path or be a complete HTTP
                      //注意所在的模块 及  restTemplate请求的服务器是否alive
                      MockMvcRequestBuilders.get("/rest/{id}",1)
                      //  设置响应文本类型
                      .accept(MediaType.APPLICATION_JSON)
      //                .param(key,value)   k-v对形式
        .contentType(MediaType.APPLICATION_JSON)、、	//  设置请求文本类型
                  .content(obj）//请求的paramString
      
              ).andExpect(MockMvcResultMatchers.status().isOk()) //200
              .andExpect(
                      MockMvcResultMatchers.jsonPath("$.data.address") //$代表responseBody
                      .value("zhe")
              )
              .andDo(MockMvcResultHandlers.print());
          }
      }
      ```

  * swagger(开发环境使用)

    **自动生成接口文档的工具**

    * codegen 生成html和cwiki
    * ui 可视化页面+接口测试
    * editor 类似markdown编辑器，可实时预览
    * inspector postman
    * hub 集成上述所有功能

    步骤：

    1. 导入依赖

       ```
       	<dependency>
       		<groupId>io.springfox</groupId>
       		<artifactId>springfox-boot-starter</artifactId>
       		<version>3.0.0</version>
       	</dependency>
       ```

    2. 在启动类上加上@EnableOpenApi

    3. 配置各种需要加文档注释的地方 可选

       * 类 @Api tags

         Marks a **class** as a Swagger resource.

       * 方法 @ApiOperation

         Describes an **operation or typically a HTTP method** against a specific path

         If tags() is not used, this value will be used to set the tag for the operations described by this resource. Otherwise, the value will be ignored.

         tags会新建一栏 类似 类@Api

       * ApiImplicitParam Represents a single **parameter** in an API Operation.  

         ApiImplicitParams A wrapper to allow a list of multiple ApiImplicitParam objects.

         ```
         @ApiImplicitParam(name = "A", value = "used by A", defaultValue = "A", dataTypeClass = String.class，required=true,paramType="body"),
         paramType：path（@PathVariable）, query, body, header or form
         swagger不允许get/head有body（推荐） postman可以 
         ```

         Map @RequestParam将会使得queryString全部映射到Map without@RequestParam 无法映射

       * ApiResponse Describes **a possible response of an operation**.
         ApiResponses A wrapper to allow a list of multiple ApiResponse objects.

         ```
         @ApiResponse(code = 200,message = "test success",response = String.class),
         ```

       * @ApiModel **Provides additional information about Swagger models**（classe）。Classes will be introspected（省略） automatically as they are used as types in operations, but you may want to manipulate the structure of the models **用在实体类上 配合@ApiModelProperty使用 一般用作方法参数 schemas中有展示（加上@RequestParam无法展示字段）**

         ```
         @ApiModel(value="TestController2",parent=Object.class)
         ```

       * error： **Resolver error at paths./test.get.parameters.1.schema.$ref**
         Your API definition is fine. This error is a bug/limitation of Swagger UI's $ref resolver - sometimes it fails on long $ref + allOf/oneOf/anyOf chains, recursive schemas, circular references, or a combination thereof.

       * 接收xml

         <?xml version="1.0" encoding="utf-8"?>
         <B>
           <id>0</id>
           <title>string</title>
           <author>string</author>
         </B>

         ```
         @Data
         @JacksonXmlRootElement(localName = "B")
         public class XMLRequestEntity {
             @JacksonXmlProperty(localName ="id")
             private Integer id;
             @JacksonXmlProperty(localName ="title")
             private String title;
             @JacksonXmlProperty(localName ="author")
             private String author;
         }
         ```

       ```
       @PostMapping(value = "/test",consumes = {"application/xml"})
       public String test(@RequestBody XMLRequestEntity B, String A){
       ```

    4. project_path/swagger-ui/ **notice tail /**

       API信息配置：

    ```
    @Configuration
    public class SwaggerConfig {
        @Bean
        public Docket createRestApi(){
            return new Docket(DocumentationType.OAS_30)
                    .apiInfo(creatApiInfo())
                    .groupName("开发者001") //分组 多个分组就多个Docket bean实例
                    //过滤
                    .select()
    //                .apis(RequestHandlerSelectors.basePackage("org.jinjianou.myspringbootstarter1.controllers"))
                    .paths(PathSelectors.ant("/user/**"))
                    .build()
                    .enable(true) //swagger开关
                    ;
        }
    
        @Bean
        public Docket createRestApi2(){
            return new Docket(DocumentationType.OAS_30)
                    .apiInfo(creatApiInfo())
                    .groupName("开发者002") //分组 多个分组就多个Docket bean实例
                    //过滤
                    .select()
    //                .apis(RequestHandlerSelectors.basePackage("org.jinjianou.myspringbootstarter1.controllers"))
                    .paths(PathSelectors.ant("/test/**"))
                    .build()
                    .enable(true) //swagger开关
                    ;
        }
    
    
        private ApiInfo creatApiInfo(){
            return new ApiInfo("MySwagger Info"
                    ,"MySwagger Info api documentation"
                    ,"3.0"
                    ,"http://www.baidu.com"
                    ,new Contact("金建欧","http://www.baidu.com","jinjianou@163.com")
                    ,"Apache 2.0"
                    ,"https://www.baidu.com"
                    ,new ArrayList()
            );
        }
    }
    @Configuration
    public class SwaggerConfig {
        @Bean
        public Docket createRestApi(){
            return new Docket(DocumentationType.OAS_30)
                    .apiInfo(creatApiInfo());
        }
    
        private ApiInfo creatApiInfo(){
            return new ApiInfo("MySwagger Info"
                    ,"MySwagger Info api documentation"
                    ,"3.0"
                    ,"http://www.baidu.com"
                    ,new Contact("金建欧","http://www.baidu.com","jinjianou@163.com")
                    ,"Apache 2.0"
                    ,"https://www.baidu.com"
                    ,new ArrayList()
            );
        }
    }
    ```

* springMVC自动配置原理分析

  ​	springBoot为spring mvc提供了自动配置，在spring默认值之上添加了以下功能：

  * ContentNegotiatingViewResolverBeanNameViewResolver
    * @AutoConfigureAfter（classes[]） 表示**本类bean晚于**classes实例化 order只针对spring.factories文件下的自动配置类生效 
  * 支持提供静态资源，包括对WebJars的支持
  * 自动注册Converter,GenericConverter,Fottatter
  * 支持HttpMessageConveter
  * 自动注册MessageCodesResolver
  * 静态index.html支持
  * 自动使用ConfigurableWebBindingInitializer

* 定制springMVC的自动配置

* springBoot嵌入式servlet容器







# 集成Mybatis

## 依赖

```
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.2</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.20</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```



## 配置

```
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/houdunren?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true
mybatis:
  mapper-locations: classpath:org/jinjianou/myspringbootstarter1/mapper/*.xml
  type-aliases-package: org.jinjianou.myspringbootstarter1.domain
  
logging:
  level:
    root: info
    org:
      jinjianou:
        myspringbootstarter1:
          dao: debug
```



## 接口和mapper文件

1. 接口上增加@Mapper注解

2. 建立配置中的mapper-locations目录，并添加xml(**namespace必须与接口一致**，文件名可以不同)

3. service/controller

   

## 问题

1.  Unknown character set index for field '255' received from server.

   原因：mysql版本高 mysql-connector-java版本过低
   MYSQL 5.5 之前， UTF8 编码只支持1-3个字节;从MYSQL5.5开始，可支持4个字节UTF编码utf8mb4;
   升级mysql-connector-java
   characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true

2. Content-Type: application/xhtml+xml

   原因：早期更多是xml,目前默认先返回xml

   ```
   produces = {MediaType.APPLICATION_JSON_VALUE}
   ```

   此外 Accept-Encoding: gzip, deflate, br 
   Http压缩,属于http内容编码的一种
   Accept-Language: en-US,en;q=0.9,zh-CN;q=0.7
   浏览器能够接受 en-US, en 和 zh-CN 三种语言，其中 en-US 的权重最高 ( q 最高为1，最低为 0)，