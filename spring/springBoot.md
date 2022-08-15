*(Java API forRESTful WebServicesSpring

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
    <!--依赖不传递->
    <optional>true</optional> 
    <scope>runtime</scope>
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

# 配置文件

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

        前者虽然不支持spel 但支持${xx.xx}引用,如: 

      * 配置随机值

        ```yam
        my:
          secret: "${random.value}"
          number: "${random.int}"
          bignumber: "${random.long}"
          uuid: "${random.uuid}"
          number-less-than-ten: "${random.int(10)}"
          number-in-range: "${random.int[1024,65536]}"
          
        ```

      * 配置验证

        JSR-303  javax.validation.xxx   

        * **ensure that a compliant JSR-303 implementation is on your classpath** and then add constraint annotations to your fields 

          **spring-boot-starter-validation**  @Validated 

        

    * 测试 @SpringBootTest

      * @BootstrapWith defines class-level metadata that is used to determine how to bootstrap the Spring TestContext Framework

      * @ExtendWith

      * 必须保证test的层级目录和@SpringBootApplication的相同

      * ​    spring boot 2.2之前使用的是 Junit4 org.junit.Test

        ​    spring boot 2.2之后使用的是 Junit5 org.junit.jupiter.api.Test

# 日志系统

## java

![14](C:\Users\Administrator\Desktop\复习\素材\pic\spring\14.jpg)

### 桥接模式

	通过提供抽象化和实现化之间的桥接结构，来实现二者的解耦
一个类存在两个（或多个）独立变化的维度，且这两个（或多个）维度都需要独立进行扩展
比如点咖啡,有容量(大中小)杯,糖度(加糖,不加糖)等等维度,且独立变化,如果通过类继承,需要2*3类,而且后期随着维度增多,代码量越来越冗余,不够灵活

![1](.\springBoot.assets\1.jpg)

- **抽象化(Abstraction)角色**：抽象化给出的定义，并保存一个对实现化对象的引用。
- **修正抽象化(RefinedAbstraction)角色**：扩展抽象化角色，改变和修正父类对抽象化的定义。
- **实现化(Implementor)角色**：这个角色给出实现化角色的接口，但不给出具体的实现。必须指出的是，这个接口不一定和抽象化角色的接口定义相同，实际上，这两个接口可以非常不一样。实现化角色应当只给出底层操作，而抽象化角色应当只给出基于底层操作的更高一层的操作。
- **具体实现化(ConcreteImplementor)角色**：这个角色给出实现化角色接口的具体实现。

抽象化角色就像是一个水杯的手柄，而实现化角色和具体实现化角色就像是水杯的杯身。手柄控制杯身，这就是此模式别名“柄体”的来源。

将咖啡的容量作为**抽象化Abstraction**，而咖啡口味为**实现化Implementor**

1. 创建抽象化部分以及抽象化修正 (容量)

```
//抽象化Abstraction
public abstract class Coffee {
    //桥接实现化
    protected ICoffeeAdditives additives;
    public Coffee(ICoffeeAdditives additives){
        this.additives=additives;
    }
    public abstract void orderCoffee(int count);
}
//RefinedAbstraction
abstract class RefinedCoffee extends Coffee{

    public RefinedCoffee(ICoffeeAdditives additives) {
        super(additives);
    }

    public void checkQuality(){
        Random ran = new Random();
        System.out.println(String.format("%s 添加%s", additives.getClass().getSimpleName(),ran.nextBoolean()?"正常":"太多"));
    }
}
//Specific
class LargeCoffee extends RefinedCoffee{

    public LargeCoffee(ICoffeeAdditives additives){
        super(additives);
    }
    @Override
    public void orderCoffee(int count) {
        System.out.println(String.format("大杯咖啡 %d 杯", count));
    }
}
```

2. 创建实现化 (口味)

   ```
   //implementor
   public interface ICoffeeAdditives {
       void doSomething();
   }
   
   //ConcreteImplementor
   class  Milk implements ICoffeeAdditives{
   
       @Override
       public void doSomething() {
           System.out.println("加奶");
       }
   }
   class  Sugar implements ICoffeeAdditives{
   
       @Override
       public void doSomething() {
           System.out.println("加糖");
       }
   }
   ```

3. 测试

   ```
           LargeCoffee largeWithMilkCoffee = new LargeCoffee(new Milk());
           largeWithMilkCoffee.orderCoffee(2);
           largeWithMilkCoffee.checkQuality();
   ```

### 适配器模式

适配器模式（Adapter Pattern）是作为两个不兼容的接口之间的桥梁
这种模式涉及到一个单一的类，该类负责加入独立的或不兼容的接口功能。举个真实的例子，读卡器是作为内存卡和笔记本之间的适配器。您将内存卡插入读卡器，再将读卡器插入笔记本，这样就可以通过笔记本来读取内存卡。比如电源适配器,将220v交流电转成5-20v直流电

适配器模式中涉及到的三个角色：

1、Target（目标抽象类）：目标抽象类定义**客户所需接口**，可以是一个抽象类或接口，也可以是具体类。5-20v

2、Adaptee（适配者类）：适配者即被适配的角色，它定义了一个已经存在的接口，这个接口需要适配，适配者类一般是一个**具体类**，包含了客户希望使用的业务方法。220v

3、Adapter（适配器类）：通过包装一个需要适配的对象(或继承)，把原接口转换成目标接口。电源适配器

### ![2](.\springBoot.assets\2.jpg)

```
target:
public interface ElectricalEquipment {
    void voltage();
}

adaptee:
public class Power {
    public void specificVoltage(){
        System.out.println("power使用220v");
    }
}

ObjectAdapter
public class PowerAdapter implements ElectricalEquipment{
    protected Power power;
    public PowerAdapter(Power power){
        this.power=power;
    }

    @Override
    public void voltage() {
        power.specificVoltage();
    }
}

client:
        PowerAdapter adapter = new PowerAdapter(new Power());
        adapter.voltage();
```

比如 log4j-over-slf4j 桥接方式 app ---> log4j-over-slf4j --->slf4j-api    

​			slf4j(RefinedAbstraction)log4j(ConcreteImplementor)

比如 slf4j-log4j12   slf4j-->slf4j-log4j12-->log4j

## springboot

![15](C:\Users\Administrator\Desktop\复习\素材\pic\spring\15.jpg)



![16](C:\Users\Administrator\Desktop\复习\素材\pic\spring\16.jpg)

**JCL的全称为: `Jakarta commons-logging`**, 是**apache**公司提供的一个抽象的日志框架, 并不提供日志功能

**JUL的全称为`java.util.logging`**, 由名字可知，它为jdk自带的一个日志工具

### 日志级别

trace debug info warn error fatal off 

sb默认info

```
 logging.level.<logger-name>=<level>
```

1. logger 有两种级别：

　　　　一种是 root

　　　　一种是普通的 logger

​	logger 有三个属性：

- name：用来指定此 logger 约束的**某一个包**或者具体的某一个类 
- level：用来设置打印级别
- addtivity：是否向上级 logger 传递打印信息，为true的时候会打印2次以上的日志。默认是 true。

　　每个 logger 都有对应的父级关系，它通过包名来决定父级关系，root 是最高级的父元素。

```
 <logger name="org.springframework" level="INFO">
 或
 <root level="all">
            <appender-ref ref="Console"/>
            <appender-ref ref="RollingFileDebug"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileWarn"/>
            <appender-ref ref="RollingFileError"/>
        </root>
```

2.  Appenders节点

   常见的有三种子节点:Console、RollingFile、File. 

```
 <!--这个输出控制台的配置 target SYSTEM_OUT或SYSTEM_ERR-->
        <console name="Console" target="SYSTEM_OUT">
            <!--输出日志的格式 不设置默认 %m%n.-->
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
        </console>
```

```
<!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定（true追加），这个也挺有用的，适合临时测试用-->
        <File name="log" fileName="log/test.log" append="false">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} %-5level %class{36} %L %M - %msg%xEx%n"/>
        </File>
```

```
<!-- 这里的日志类似流一样的进入 RollingFileInfo ， 然后经过 ThresholdFilter 进行过滤。       
                因为经过ThresholdFilter的时候，如果有匹配项，则直接打印日志了，所以需要把高等级的日志级别放在前面
                并且，onMatch属性设置为DENY，过滤掉高等级的日志；onMismatch设置为NEUTRAL，把低等级的日志放行，
                到我们想要的级别后，onMache设为ACCEPT，获取到日志，并onMismatch设置为DENY，丢弃低等级日志，并执行结束-->
        <RollingFile name="RollingFileALL" fileName="${sys:user.home}/logs/count-dispather/default/info.log"
                     filePattern="${sys:user.home}/logs/count-dispather/default/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
            <Filters>
                   <!--  控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch） -->
                <ThresholdFilter level="OFF" onMatch="DENY" onMismatch="NEUTRAL"/>
                <ThresholdFilter level="INFO" onMatch="ACCEPT" onMismatch="DENY"/>
            </Filters>
            <PatternLayout pattern="[%d{HH:mm:ss:SSS}] [%p] - %l - %m%n"/>
            <！--  指定滚动日志的策略，就是什么时候进行新建日志文件输出日志-->
            <Policies>
            	<！--基于时间的滚动策略，interval属性用来指定多久滚动一次，默认是1 hour。modulate=true用来调整时间：比如现在是早上3am，interval是4，那么第一次滚动是在4am，接着是8am，12am...而不是7am.-->
                <TimeBasedTriggeringPolicy/>
        	<!--基于指定文件大小的滚动策略，size属性用来定义每个日志文件的大小.-->
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
             <!-- DefaultRolloverStrategy属性如不设置，则默认为最多同一文件夹下7个文件，这里设置了20 -->
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
```



### 日志格式

 <!--输出格式布局，每个转换说明符以百分号(%)开头，'%'后面的转换字符有如下:-->
            <!--
            p (level) 日志级别
            c（logger） Logger的Name
            C (class) Logger调用者的全限定类名 ***
            d (date) 日期
            highlight 高亮颜色
            l (location) 调用位置 ***
            L (line) 行号
            m (msg/message) 输出的内容
            M (methode) 调用方法 ***
            maker marker的全限定名
            n 输出平台相关的换行符,如'\n' '\r\n'
            pid (processId) 进程ID
            level （p）日志级别
            r JVM启动后经过的微秒
            t (tn/thread/threadName) 线程名称
            T (tid/threadId) 线程ID
            tp (threadPriority) 线程优先级
            x (NDC) 线程Context堆栈
            -->

%logger 输出logger名称，因为Root Logger没有名称，所以没有输出

默认

```
%d{yyyy-MM-dd HH:mm:ss.SSS} -%5p ${PID:-} [%15.15t] %-40.40logger{39} : %m%n
```

```
2022-07-19 09:35:53.034  INFO 16180 --- [           main] .s.d.r.c.RepositoryConfigurationDelegate : Multiple Spring Data modules found, entering strict repository configuration mode!
```



自定义格式

```
logging.pattern.[console|file]
```



文件路径

```
logging.file.[path|name]
```



### 注解

先安装lombok

```
@Slf4j`和`@Log4j2` 建议在开发过程中默认选择：`@Slf4j
@Target({ElementType.TYPE})

相当于： private static Logger log = LoggerFactory.getLogger(WeixinPayService.class);
```



### 框架切换

​	默认logback 

   比如想切换成log4j2

1. 添加log4j2,并且移除sb中logback的依赖

   ```
   <!-- 切换日志框架-->
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter</artifactId>
               <exclusions>
                   <exclusion>
                       <groupId>org.springframework.boot</groupId>
                       <artifactId>spring-boot-starter-logging</artifactId>
                   </exclusion>
               </exclusions>
           </dependency>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-log4j2</artifactId>
           </dependency>
   ```

   

2. 在resources中创建log4j2-spring.xml

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <!--设置log4j2的自身log级别为warn -->
   <configuration status="warn">
       <properties>
           <!--这里配置的是日志存到文件中的路径-->
           <!-- 父项目根目录下-->
           <Property name="log_path">logs</Property>
       </properties>
       <appenders>
           <console name="Console" target="SYSTEM_OUT">
               <PatternLayout pattern="[%d{yyyy-MM-dd HH:mm:ss.SSS}][%t][%c(类):%L(行)] %m%n"/>
           </console>
           <!-- 这里配置了普通日志的格式和存入文件的路径 -->
           <RollingFile name="RollingFileInfo" fileName="${log_path}/info.log"
                        filePattern="${log_path}/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
               <Filters>
                   <ThresholdFilter level="INFO"/>
                   <ThresholdFilter level="WARN" onMatch="DENY" onMismatch="NEUTRAL"/>
               </Filters>
               <PatternLayout pattern="[%d][%t][%c(类):%L(行)] %m%n"/>
               <!--  <PatternLayout pattern="[%d][%t] -5level %m%n"/> -->
               <Policies>
                   <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                   <SizeBasedTriggeringPolicy size="5 MB"/>
               </Policies>
           </RollingFile>
           <!-- 这里配置了警告日志的格式和存入文件的路径 -->
           <RollingFile name="RollingFileWarn" fileName="${log_path}/warn.log"
                        filePattern="${log_path}/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log">
               <Filters>
                   <ThresholdFilter level="WARN"/>
                   <ThresholdFilter level="ERROR" onMatch="DENY" onMismatch="NEUTRAL"/>
               </Filters>
               <PatternLayout pattern="[%d][%t][%c(类):%L(行)] %m%n"/>
               <!-- <PatternLayout pattern="[%d][%t][%p][%c:%L] %m%n"/> -->
               <Policies>
                   <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                   <SizeBasedTriggeringPolicy size="5 MB"/>
               </Policies>
           </RollingFile>
           <RollingFile name="RollingFileError" fileName="${log_path}/error.log"
                        filePattern="${log_path}/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
               <ThresholdFilter level="ERROR"/>
               <PatternLayout pattern="[%d][%t][%c(类):%L(行)] %m%n"/>
               <!--  <PatternLayout pattern="[%d][%t]  %-5level %m%n"/> -->
               <Policies>
                   <TimeBasedTriggeringPolicy interval="1" modulate="true"/>
                   <SizeBasedTriggeringPolicy size="5 MB"/>
               </Policies>
           </RollingFile>
           <!-- 配置mongdb appender -->
       </appenders>
       <loggers>
           <!-- 过滤redis重连日志 -->
           <logger name="io.lettuce.core.protocol" level="ERROR">
               <appender-ref ref="RollingFileError" />
           </logger>
           <!--过滤掉spring的一些无用的debug信息-->
   <!--        <logger name="org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport" level="Error"></logger>-->
   
           <root level="INFO">
               <appender-ref ref="Console"/><!-- 配置控制台输出日志 -->
               <appender-ref ref="RollingFileInfo"/><!-- 配置普通日志 -->
               <appender-ref ref="RollingFileWarn"/><!-- 配置警告日志 -->
               <appender-ref ref="RollingFileError"/><!-- 配置异常日志 -->
           </root>
       </loggers>
   </configuration>
   
   ```

   

# Web开发

## springMVC快速使用

```java
@RestController
@RequestMapping("/user")
```

### 调用rest http 通过RestTemplate 堵塞式

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

**@RequestParam bean 或者 bean 都能拿到params和request body(前提是k-v，json对象不行)**

​	 推荐  @RequestParam

				1. application/x-www-form-urlencoded编码的内容
				2. multipart/form-data (表单上传的)


```
 @RequestBody

	一般用来处理 Content-Type: 为application/json
```
**Sping-boot-starter-json使用的jackson**

   解决null也显示

* 类上 @JsonInclude(JsonInclude.Include.NON_NULL)

* 全局 application.* 

  * JacksonAutoConfiguration.Jackson2ObjectMapperBuilderCustomizerConfiguration

    spring.jackson.defaultPropertyInclusion=NON_NULL

### restTemplate api

**Get**

**getForEntity**

public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)

>  uriVariables指的是pathParam  /{id}

public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)

public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType)



**getForObject**

该方法可以理解为对 getForEntity 的进一步封装，
 它通过 HttpMessageConverterExtractor 对 HTTP 的请求响应体 body内容进行对象转换， 实现请求直接返回包装好的对象内容



**POST**

**postForEntity**

- `public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables)`
- `public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request, Class<T> responseType, Map<String, ?> uriVariables)`
- `public <T> ResponseEntity<T> postForEntity(URI url, @Nullable Object request, Class<T> responseType)`

> request参数， 该参数可以是一个普通对象， 也可以是一个HttpEntity对象。 如果是
>  一个**普通对象**， 而非HttpEntity对象的时候， RestTemplate会将请求对象转换为一
>  个HttpEntity对象来处理， 其中Object就是 request 的类型， request内容会被视
>  作完整的**body**来处理；而如果 request 是一个**HttpEntity对象**， 那么就会被当作一
>  个完成的HTTP请求对象来处理， 这个 request 中不仅**包含了body的内容， 也包含了
>  header的内容**。

​	   LinkedMultiValueMap key1=value1&key2=value2

​		HashMap  {"key1":"value1"}

> 



**PostForObject**

该方法跟getForObject方法类似， 它的作用是简化postForEntity的后续处理。 通过直接将请求响应的body内容包装成对象来返回使用。



**Put**

```
public void put(String url, @Nullable Object request, Object... uriVariables)
public void put(String url, @Nullable Object request, Map<String, ?> uriVariables)
public void put(URI url, @Nullable Object request)
```

PUT请求可以通过put方法调用，put方法的参数和前面介绍的postForObject方法的参数基本一致，只是put方法没有返回值而已。



**delete**

```
public void delete(String url, Object... uriVariables)
public void delete(String url, Map<String, ?> uriVariables)
public void delete(URI url)
```

### webclient  

都可以调用远程服务 依赖webflux(响应式无堵塞)

### 工具：

* postman

* apipost

  api接口管理

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

### swagger(开发环境使用)

**自动生成接口文档的工具**

* swagger-codegen 生成html和cwiki
* swagger-ui 可视化页面+接口测试
* swagger-editor 类似markdown编辑器，可实时预览
* swagger-inspector postman
* swagger-hub 集成上述所有功能

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

     This is the only way to define parameters when using Servlets  or other non-JAX-RS environments.

     JAX-RS: Java API forRESTful WebServices

     Map @RequestParam将会使得queryString全部映射到Map without@RequestParam 无法映射

   * ApiResponse Describes **a possible response of an operation**.
     ApiResponses A wrapper to allow a list of multiple ApiResponse objects.

     ```
     @ApiResponse(code = 200,message = "test success",response = String.class),
     ```

   * @ApiModel **Provides additional information about Swagger models**（classes）。Classes will be introspected（省略） automatically as they are used as types in operations, but you may want to manipulate the structure of the models **用在实体类上 配合@ApiModelProperty使用 该类一般用作方法参数 schemas中有展示（加上@RequestParam无法展示字段）**

     ```
     @ApiModel(value="TestController2",parent=Object.class)
     ```

   * error： **Resolver error at paths./test.get.parameters.1.schema.$ref**
     Your API definition is fine. This error is a bug/limitation of Swagger UI's $ref resolver - sometimes it fails on long $ref + allOf/oneOf/anyOf chains, recursive schemas, circular references, or a combination thereof.

#### project_path/swagger-ui/ **notice tail /**

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

## 接收xml

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



# springMVC自动配置原理分析

​	springBoot为spring mvc提供了自动配置，在spring默认值之上添加了以下功能：

1. ContentNegotiatingViewResolver  BeanNameViewResolver

   The ContentNegotiatingViewResolver does not resolve views itself, but delegates to other ViewResolvers. 

   
   
   BeanNameViewResolver 根据返回的视图名称找到对应的viewBean
   
   ```
       @RequestMapping("/demo4")
       public String demo4() {
           return "testBeanView"; //必须是小写 且必须是@Controller
       }
   ```
   
   ```
   @Component
   public class TestBeanView implements View {
       @Override
       public String getContentType() {
           return "text/html";
       }
   
       @Override
       public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
           response.getWriter().write("welcome to beanView page");
       }
   }
   ```
   
   同样地, ExcelViewResolver   extends AbstractXlsxView
   
   不过在之前要配置对应的ViewResolver
   
   
   
   ​	@AutoConfigureAfter（classes[]） 表示**本类bean晚于**classes实例化 order只针对spring.factories文件下的自动配置类生效 
   
   **默认restcontroller成功请求不走viewResolver**

2. 支持提供静态资源，包括对WebJars的支持

   ​	之前的静态资源需要放行配置,sb不同配置,只需要将资源放在约定的static目录

   ```
   registration.addResourceLocations(this.resourceProperties.getStaticLocations());
   CLASSPATH_RESOURCE_LOCATIONS = { "classpath:/META-INF/resources/",
   				"classpath:/resources/", "classpath:/static/", "classpath:/public/" };
   ```

   首页 index.html

   

   自定义资源目录

   ```
   @ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
   public class ResourceProperties extends Resources {
   ```

   

   WebJars: client-side web libraries (e.g. jQuery & Bootstrap) packaged into JAR (Java Archive) files.

   ```
   addResourceHandler(registry, "/webjars/**", "classpath:/META-INF/resources/webjars/");
   ```

   urlpattern是/webjars/xx 会去classpath:/META-INF/resources/webjars/找对应的资源

3. 自动注册Converter,GenericConverter,Formatter

   ```java
   		@Bean
   		@Override
   		public FormattingConversionService mvcConversionService() {
   			Format format = this.mvcProperties.getFormat();
   			WebConversionService conversionService = new WebConversionService(new DateTimeFormatters()
   					.dateFormat(format.getDate()).timeFormat(format.getTime()).dateTimeFormat(format.getDateTime()));
   			addFormatters(conversionService);
   			return conversionService;
   		}
   ```

   ```.format
   spring.mvc.format.xxx
   ```
   
   或者 自定义一个formatter
   
   ```
   public static void addBeans(FormatterRegistry registry, ListableBeanFactory beanFactory)
   ```



4. 支持HttpMessageConveter

   HttpMessageConveter

   Strategy interface for converting from and to HTTP requests and responses.

5. 自动注册MessageCodesResolver

   MessageCodesResolver: 

   Strategy interface for building message codes from validation error codes. Used by DataBinder to build the codes list for ObjectErrors and FieldErrors.

6. 静态index.html支持

```
		public InternalResourceViewResolver defaultViewResolver() {
			InternalResourceViewResolver resolver = new InternalResourceViewResolver();
			resolver.setPrefix(this.mvcProperties.getView().getPrefix());
			resolver.setSuffix(this.mvcProperties.getView().getSuffix());
			return resolver;
		}
```

spring.mvc.view.prefix=/     //默认在/static/等目录下

spring.mvc.view.suffix=.html

7. 自动使用ConfigurableWebBindingInitializer
   ConfigurableWebBindingInitializer

   Convenient WebBindingInitializer for declarative configuration in a Spring application context. Allows for reusing pre-configured initializers with multiple controller/handlers.

   初始化WebDataBinder( data binding from web request parameters to JavaBean objects)，将请求的参数转化为对应的JavaBean，并且会结合类型、格式转换等API一起使用

##　自定义覆盖

* conditionOnBean 自定义需要的bean覆盖原有webmvcAutoConfiguration

* addViewController 

  ​	Configure simple automated controllers pre-configured with the response status code and/or a view to render the response body. 

```
@Configuration
public class MyMvcAutoConfiguration implements WebMvcConfigurer {
    @Override
    public void  addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/myView").setViewName("index");
    }

}
```

* configurePathMatch(PathMatchConfigurer configurer) 

   PathMatchConfigurer  给路径匹配增加属性,比如尾部/

  但不会影响addViewControllers配置的path

  ```
      @Override
      public void configurePathMatch(PathMatchConfigurer configurer) {
          configurer.setUseTrailingSlashMatch(true);
      }
  ```

*  拦截器

  ```
      @Override
      public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor()
                  .addPathPatterns("/**")
                  .excludePathPatterns("/pages/*.html");
  
      }
  ```

  

* 跨域

  跨域，是指浏览器不能执行其他网站的脚本。它是由**浏览器的同源策略**造成的，是浏览器对JavaScript实施的安全限制。

  狭义的同源就是指域名、协议、端口均为相同。

  局部 @CrossOrigin

  全局设置 CORS

  ```
      @Override
      public void addCorsMappings(CorsRegistry registry) {
                 registry.addMapping("/user/**")
                  .allowedOrigins("http://localhost:81")
                  .allowedMethods("GET","POST","PUT","DELETE");
      }
  ```

### 自定义原理

​	WebmvcAutoConfigutation ->WebMvcAutoConfigurationAdapter->EnableWebMvcConfiguration->DelegatingWebMvcConfiguration

``` java
private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();


	@Autowired(required = false)
	public void setConfigurers(List<WebMvcConfigurer> configurers) {
		if (!CollectionUtils.isEmpty(configurers)) {
			this.configurers.addWebMvcConfigurers(configurers);
		}
	}
```

​	WebMvcConfigurerComposite 同样实现了WebMvcConfigurer接口,相应的操作实际上是循环webMvcConfigurers,如

```
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		for (WebMvcConfigurer delegate : this.delegates) {
			delegate.addViewControllers(registry);
		}
	}·
```



**注意：**

1. **@Import是要晚于bean创建和@EnableConfigurationProperties的，所以configurers包含WebMvcAutoConfigurationAdapter**
2. **@Import的class如果不在Application的扫描路径下，会去classpath下扫描**



@EnableWebMvc

Adding this annotation to an @Configuration class imports the Spring MVC configuration from WebMvcConfigurationSupport,

由于WebMvcAutoConfiguration @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)

而 @EnableWebMvc @Import(DelegatingWebMvcConfiguration.class)

class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport

因此注解了@EnableWebMvc会使得WebMvcAutoConfiguration配置全部失效





# JSON

Spring Boot provides integration with three JSON mapping libraries:

- Gson
- Jackson
- JSON-B

Jackson is the preferred and default library.



1. @JsonIgnore

   ```
   the logical property is to be ignored
   ```

2. @JsonFormat

   默认时间戳(或者这种"2022-07-20T08:28:00.997+00:00")  如pattern="yyyy-MM-dd"

3. @JsonInclude(JsonInclude.Include.xxx)

4. @JsonProperty

   ```
   can be used to define a non-static
    * method as a "setter" or "getter" for a logical property
    * (depending on its signature),
    * or non-static object field to be used (serialized, deserialized) as
    * a logical property.
   ```

   javaBean的字段取个新名字展示在json串



自定义json序列化与反序列化

@JsonComponet

```
@JsonComponent
public class UserJsonConversion {
    public static class Serializer extends JsonObjectSerializer<User> {

        @Override
        protected void serializeObject(User value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeObjectField("username",value.getUsername());
        }
    }
    public static class Deserializer extends JsonObjectDeserializer<User> {

        @Override
        protected User deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) throws IOException {
            return null;
        }
    }
}
```

# 国际化

1. 添加国际化资源文件

   在resources下新建i18n目录

   新建message.properties/message_en_US.properties/message_zh_CN.properties

2. 配置MessageResource

   ```
   @Configuration(proxyBeanMethods = false)
   @ConditionalOnMissingBean(name = AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME, search = SearchStrategy.CURRENT)
   @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
   @Conditional(ResourceBundleCondition.class) //spring.messages.basename=i18n.message(不能写成/i18n/message)
   @EnableConfigurationProperties  //Enable support for @ConfigurationProperties annotated beans
   public class MessageSourceAutoConfiguration
   ```

3. 解析请求头的accept-language 或者 解析url参数中?local=

   sb默认Accept-Header

   spring.web.locale可以设置默认locale

   ```
   @Bean
   @ConditionalOnMissingBean(name = DispatcherServlet.LOCALE_RESOLVER_BEAN_NAME)
   public LocaleResolver localeResolver() {
   			if (this.webProperties.getLocaleResolver() == WebProperties.LocaleResolver.FIXED) {
   				return new FixedLocaleResolver(this.webProperties.getLocale());
   			}
   			if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
   				return new FixedLocaleResolver(this.mvcProperties.getLocale());
   			}
   			AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
   			Locale locale = (this.webProperties.getLocale() != null) ? this.webProperties.getLocale()
   					: this.mvcProperties.getLocale();
   			localeResolver.setDefaultLocale(locale);
   			return localeResolver;
   		}
   ```

   只有"Accept-Language"是null 时,defaultLocale生效

   ```
   		if (defaultLocale != null && request.getHeader("Accept-Language") == null) {
   			return defaultLocale;
   		}
   ```

   

4. ~~将本地语言进行缓存~~

5. 通过 MessageResource获取国际化信息

   ```
   @Autowired
   private MessageSource messageSource;
   ...
   
   messageSource.getMessage("user.query.success",null, LocaleContextHolder.getLocale())
   ```

   若中文乱码 先看下fileEncoding

6. 自定义localeResolve

   ```
   @Bean
   public LocaleResolver localeResolver(){
       CookieLocaleResolver localeResolver = new CookieLocaleResolver();
       localeResolver.setCookieName("localCookie");
       localeResolver.setCookieMaxAge(60*60);
       return localeResolver;
   }
   
   @Override
   public void addInterceptors(InterceptorRegistry registry) {
       //request.getParameter 通过?locale=
       registry.addInterceptor(new LocaleChangeInterceptor()).addPathPatterns("/**");
   }
   ```

# 异常处理

内置BasicErrorController统一处理error

```
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response)
	
	
		@RequestMapping
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request)
```

只有当Accept是 text/html(\*/*也不行)时走errorHtml,其它走error

分析errorHtml方法:

<li>{@code '/<templates>/error/404.<ext>'}</li>

<li>{@code '/<static>/error/404.html'}</li>

<li>{@code '/<templates>/error/4xx.<ext>'}</li>

<li>{@code '/<static>/error/4xx.html'}</li>

1. getErrorAttributes获得错误属性

2. DefaultErrorViewResolver.resolveErrorView

3. templateAvailabilityProviders.getProvider 找到可用的视图模板 如FreeMarker or Thymeleaf

4. 若没有模板视图 this.resources.getStaticLocations()(sb的静态资源目录) if exists errorCode.thml

   如resources/static下有error/500.html就会走自定义的错误页面

5. 若还是没有 继续找同系列的view 重复3,4

   如400.html没有 去找4xx.html



自定义errorController extends AbstractErrorController 

```
@Bean
@ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,ObjectProvider<ErrorViewResolver> errorViewResolvers)
```



@ControllerAdvice+@ExceptionHandler





# 嵌入Servlet容器

spring-boot-starter-web 导入spring-boot-starter-tomcat 默认tomcat

```
	@Bean
	@ConditionalOnClass(name = "org.apache.catalina.startup.Tomcat")
	public TomcatServletWebServerFactoryCustomizer tomcatServletWebServerFactoryCustomizer(
			ServerProperties serverProperties) {
		return new TomcatServletWebServerFactoryCustomizer(serverProperties);
	}
```

修改配置的两种方式

1. server.tomcat.xxx..
2. 写一个组件实现WebServerFactoryCustomizer接口

##  注册servlet三大组件

1. servlet3.0

   ```
   @WebServlet(urlPatterns = {"/HelloServlet"})
   public class HelloServlet extends HttpServlet 
   
   @SpringBootApplication
   @ServletComponentScan
   public class WebApplication 
   ```

   

2. sb

   ```
   public class ServletBean extends HttpServlet 
   
       @Bean
       public ServletRegistrationBean<ServletBean> registrationBean(){
           return new ServletRegistrationBean<ServletBean>(new ServletBean(),"/ServletBean");
       }
   
   ```



## 切换其他嵌入式servlet容器

1. spring-boot-starter-web exclude spring-boot-starter-tomcat
2. 导入其他容器  spring-boot-starter-xxx



```
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
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```



### 内置Servlet自动配置原理

ServletWebServerFactoryAutoConfiguration

```
@Import({ ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistrar.class,
      ServletWebServerFactoryConfiguration.EmbeddedTomcat.class,
      ServletWebServerFactoryConfiguration.EmbeddedJetty.class,
      ServletWebServerFactoryConfiguration.EmbeddedUndertow.class })
```



自定义属性配置

WebServerFactoryCustomizer implements ServletWebServerFactoryCustomizer

怎么调用WebServerFactoryCustomizer :

BeanPostProcessorsRegistrar#registerBeanDefinitions

```
registerSyntheticBeanIfMissing(registry, "webServerFactoryCustomizerBeanPostProcessor",
      WebServerFactoryCustomizerBeanPostProcessor.class,
      WebServerFactoryCustomizerBeanPostProcessor::new);
```

WebServerFactoryCustomizerBeanPostProcessor

```
private void postProcessBeforeInitialization(WebServerFactory webServerFactory) {
   LambdaSafe.callbacks(WebServerFactoryCustomizer.class, getCustomizers(), webServerFactory)
         .withLogger(WebServerFactoryCustomizerBeanPostProcessor.class)
         .invoke((customizer) -> customizer.customize(webServerFactory));
}
```

BeanPostProcessor

​	postProcessBeforeInitialization 在Bean初始化时init方法之前

​	postProcessAfterInitialization 在Bean初始化时init方法之后



servlet容器怎么启动

WebServerFactory#getWebServer 负责对应容器的创建和启动

在spring应用容器启动,就会调用容器onFresh方法



### 使用外部servlet

​	打成war包部署到外部servlet容器

​	缺陷:能解析web.xml webservlet等注解,但不误解析springboot配置(并没有启动spring容器)

​	原因: spring-boot-maven-plugin会生成Menifest.xml 指定主类

​	解决: 创建一个继承SpringBootServletInitializer的类,并重写confifure方法,指定sources 主类



###  原理

 	根据SPI 从WEB-INF/lib下jar包的   META_INF中找到ServletContainerInitializer的实现类 即SpringServletContainerInitializer,在容器启动时实例化并调用startup方法

HandlesTypes(WebApplicationInitializer.class)

​	容器会自动在classpath下找到WebApplicationInitializer类的实例并注入到startup的参数中

```
for (WebApplicationInitializer initializer : initializers) {
   initializer.onStartup(servletContext);
}
```

![image-20220722134152672](..\spring\springBoot.assets\image-20220722134152672.png)

1. SpringBootServletInitializer.createRootApplicationContext相当于

			SpringApplication.run(WebApplication.class);

2. AbstractContextLoaderInitializer相当于web.xml ContextLoaderListener配置
3. AbstractDispatcherServletInitializer相当于web.xml DispatcherServlet配置



# 模板引擎

以freeMarker为例

1. 导入依赖

   ```
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-freemarker</artifactId>
   </dependency>
   ```

2. 配置属性

   ```
   spring.freemarker.suffix=.html
   spring.freemarker.template-loader-path=classpath:/templates/          //,分隔
   ```

3. @controller+html

# 集成Mybatis

## 整合druid数据源

1. 导入依赖

   ```
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-web</artifactId>
           </dependency>
            
           <dependency>
               <groupId>com.alibaba</groupId>
               <artifactId>druid-spring-boot-starter</artifactId>
               <version>1.2.1</version>
           </dependency>
           
                 <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.18</version>
           </dependency>
   
   ```

   

2. 配置属性

   ```
   spring:
     datasource:
       type: com.alibaba.druid.pool.DruidDataSource
       username: root
       password: root
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3306/pagehelper?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true
       # Schema (DDL) script resource references
   #    schema: classpath:person.sql
       # Data (DML) script resource references.
       data: classpath:person.sql
       # Mode to apply when determining if DataSource initialization should be performed using the available DDL and DML scripts.
       # 每次启动springboot都会执行
       initialization-mode: always
   ```

3. 创建bean(DruidDataSourceAutoConfigure有了就不需要了)

   ```
   @Configuration
   @ConditionalOnBean(DataSource.class)
   public class DruidDataSourceConfig {
   /*    //方式一: 通过ConfigurationProperties导入全部spring.datasource属性
       @Bean
       @ConfigurationProperties(prefix = "spring.datasource")
       public DruidDataSource dataSource(){
           return new DruidDataSource();
       }*/
   
       //方式二: 直接注入 DataSourceProperties  通过DataSourceBuilder
       @Bean
       public DataSource dataSource(DataSourceProperties props){
           return props.initializeDataSourceBuilder().build();
       }
   }
   ```

druid monitor 

```
 //注册后台界面Servlet bean , 用于显示后台界面
    @Bean
    public ServletRegistrationBean statViewServlet() {
        //创建StatViewServlet，绑定到/druid/路径下
        //开启后，访问localhost/druid就可以看到druid管理后台
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> param = new HashMap<String, String>();
        param.put("loginUsername", "admin");
        param.put("loginPassword", "123456");
        param.put("allow", "");//哪些IP允许访问后台“”代表所有地址
//        param.put("deny", "33.31.51.88");//不允许这个IP访问
        bean.setInitParameters(param);
        return bean;
    }

    //用于监听获取应用的数据 ， Filter用于收集数据, Servlet用于展现数据
    //Filter 对 传给Servlet 容器的 Web 资源request 对象和 response 对象进行检查和修改
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter()); //设置过滤器
        bean.addUrlPatterns("/*");
        Map<String, String> param = new HashMap<String, String>();
        //排除静态资源
        param.put("exclusions", "*.png,*.woff,*.js,*.css,/druid/*");
        bean.setInitParameters(param);
        bean.addInitParameter("profileEnable", "true");
        return bean;
    }

//endregion
```

```sql
# 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙



spring.datasource.filters=stat,wall,log4j
```

 

4.启动项目后, 输入URL ip:port/项目



**注意：sb的设计优先加载自定义配置Configuration，然后再考虑AutoConfiguration**



## 代码生成器

1. 添加插件

   ```
   <build>
           <plugins>
               <plugin>
                   <groupId>org.mybatis.generator</groupId>
                   <artifactId>mybatis-generator-maven-plugin</artifactId>
                   <version>1.4.1</version>
                   <configuration>
                       <configurationFile>${basedir}/src/main/resources/generatorConfig.xml</configurationFile>
                       <verbose>true</verbose>
                       <overwrite>true</overwrite>
                   </configuration>
                   <dependencies>
                       <dependency>
                           <groupId>mysql</groupId>
                           <artifactId>mysql-connector-java</artifactId>
                           <version>8.0.28</version>
                       </dependency>
                   </dependencies>
               </plugin>
           </plugins>
       </build>
   ```

   ​     注意：

   0. mysql-connector-java dependency必须加，哪怕之前已经导入了mysql-connector-java

   1. 默认xml文件生成时都是每次累加的

   

      2. overwrite is only used for java files ,xml files is always be merged if suppressAllComment=true

         解决：

         ```
         <dependency>
             <groupId>org.mybatis.generator</groupId>
             <artifactId>mybatis-generator-core</artifactId>
             <version>1.4.1</version>  must >1.3.8
         </dependency>
         ```

         在generator.xml 添加 <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin"/>

2. 配置文件generatorConfig.xml

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   <!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
           "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
   <generatorConfiguration>
       <!--引入外部配置 not support yaml-->
   <!--    <properties>application.yml</properties>-->
   
       <context id="default" defaultModelType="flat" targetRuntime="MyBatis3">
           <property name="beginningDelimiter" value="`"/>
           <property name="endingDelimiter" value="`"/>
           <property name="javaFileEncoding" value="UTF-8"/>
   
           <!--阻止注释的生成-->
           <commentGenerator>
               <property name="suppressAllComments" value="true"/>
               <property name="suppressDate" value="true"/>
               <property name="addRemarkComments" value="true"/>
           </commentGenerator>
   
           <!--数据库连接配置-->
   <!--        <jdbcConnection dirverClass="${spring.datasource.driverClassName}"
                           connectionURL="${spring.datasource.url}"
                           userId="${spring.datasource.username}"
                           password="${spring.datasource.password}">
           </jdbcConnection>-->
           <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                           connectionURL="jdbc:mysql://localhost:3306/houdunren?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true"
                           userId="root"
                           password="root"/>
   
           <!--实体类自动生成配置-->
           <javaModelGenerator targetPackage="org.jinjianou.myspringbootstarter1.domain" targetProject="src/main/java">
               <property name="constructorBased" value="false"/>
               <property name="enableSubPackages" value="true"/>
               <!--if true field is final-->
               <property name="immutable" value="false"/>
               <property name="trimStrings" value="true"/>
           </javaModelGenerator>
   
           <!--mapper自动生成配置-->
           <sqlMapGenerator targetPackage="org.jinjianou.myspringbootstarter1.mapper" targetProject="src/main/resources">
   <!--            是否允许子包-->
               <property name="enableSubPackages" value="true"/>
           </sqlMapGenerator>
   
           <!--dao层接口自动生成配置-->
   <!--        采用xml的方式-->
           <javaClientGenerator type="XMLMAPPER" targetPackage="org.jinjianou.myspringbootstarter1.dao" targetProject="src/main/java">
               <property name="enableSubPackages" value="true"/>
           </javaClientGenerator>
   
           <!--指定数据表-->
   <!--        针对所有数据表自动生成代码 则可将值设为%-->
           <table tableName="test">
   <!--            sqlStatement：指定主键的查询语句，如果值为MySql，则使用SELECT LAST_INSERT_ID() 语句来查询主键-->
   <!--            identity：当值为true时，selectKey标签的order属性为AFTER-->
               <generatedKey column="id" sqlStatement="MySql" identity="true"/>
           </table>
       </context>
   </generatorConfiguration>
   ```

3. 执行goal  mybatis-generator:generate

## 整合mybatis

1. 依赖

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

2. 配置

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

属性：

  ConfigLocation --->mybatis-config.xml

ConfigurationProperties --> mybatis-config.xml中的Properties顺序

interceptors  --> 实现Interceptor的component 自动注入到mybatisAutoConfiguration 
TypeAliasesPackage -- > 包下类别名
TypeAliasesSuperType --> 父类的所有子类设置别名
TypeHandlersPackage --> 类型处理器
MapperLocations --> mapper.xml location

更多配置 mybatis.configuration或者ConfigurationCustomizer(需要ConfigLocation即mybatis全局配置文件为空)

3. 接口和mapper文件

接口上增加@Mapper注解**(或者在springApplicaiton上添加@MapperScan 配置需要扫描的包**)

建立配置中的mapper-locations目录，并添加xml(**namespace必须与接口一致**，文件名可以不同)

4. service/controller

  





# SpringBoot启动原理

# SpringBoot 自定义starter 





# 问题

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
