# Spring

## AOP

1. 切面类

```java
@Component
@Aspect
@EnableAspectJAutoProxy //可以放在spring能扫描到的任何地方 一般放在启动类
public class LogAspect {
    @Pointcut("execution(* org.example.springboot.AspectMethod.*(..))")
    public void point(){}

  
    @Around("point()")
    public Object around1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("==========around1 start=========");
        Object obj=joinPoint.proceed();
  	      System.out.println("==========around1  end=========");
        return obj;
    }

    @Around("point()")
    public void around2(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("==========around2 start=========");
        joinPoint.proceed();
        System.out.println("==========around2  end=========");

    }
}
```

2. 目标对象

   ```java
   @Component //必须要有
   public class AspectMethod {
       public String sayHello(String str){
           return str;
    }
   ```

   同一个ponitCut/不同ponitCu(同时满足多个execution表达式）顺序 类似于FilterChain：

   1. around1（先声明的around） ProceedingJoinPoint.proceed()之前
   2. around2（后声明的around） ProceedingJoinPoint.proceed()之前
   3. ProceedingJoinPoint.proceed()
   4. around2（后声明的around） ProceedingJoinPoint.proceed()之前
   5. around1（后声明的around） ProceedingJoinPoint.proceed()之前

   有返回结果的是around2,around1叠加

## 配置

1. XML

2. Servlet3.0提供了一系列注解来替代原本的xml配置（包括web.xml）

3. SPI（Service Provider Interface），是JDK内置的一种服务提供发现机制，可以用来启用框架扩展和替换组件 

   Java.util.ServiceLoader

   * 在ClassPath下（如resources）建  新建文件夹 "META-INF/services/"

   * 文件名为接口的全限定类名 文件内容为实现的全限定类名（不需要后缀）

   * 实现必须有空参构造

   * 必须保证实现类public修饰

     ```
     ServiceLoader<Iparser> serviceLoader = ServiceLoader.load(Iparser.class);
     Iterator<Iparser> iterator = serviceLoader.iterator();
     while (iterator.hasNext()){
         iterator.next().parser();
     }
     ```

   * ServletContainerIntializer

     1. "META-INF/services/"下建立文件
     2. 实现该接口

     注意点： **将servlet-3.0的依赖放到最前面** 防止最短路径优先

     If an implementation of ServletContainerInitializer specifies this annotation, the Servlet container must pass the Set of application classes that extend, implement, or have been annotated with the class types listed by this annotation to the javax.servlet.ServletContainerInitializer.onStartup method of the ServletContainerInitializer (if no matching classes are found, null must be passed instead)

     控制台中文乱码：-Dfile.encoding=UTF-8

     ```
     @HandlesTypes(value = {IHandleTypesDemo.class}) //must transfer interface instead of class
     public class MyServletContainerIntializer implements ServletContainerInitializer {
         @SneakyThrows
         @Override
         public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
             ArrayList<HandleTypesDemo> lst=new ArrayList<>();
             for (Class cls:set){
                 if(!cls.isInterface() && !Modifier.isAbstract(cls.getModifiers())
                     && IHandleTypesDemo.class.isAssignableFrom(cls)){ //A.isAssignableFrom(B) 用于判定A类或接口是否时B类或接口的同类或者父类
                     lst.add((HandleTypesDemo)cls.newInstance());
                 }
             }
     
             for (HandleTypesDemo typesDemo : lst) {
                 typesDemo.sayHello();
             }
     
     
     
             servletContext.addListener(MyServletListener.class);
             ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("angleServlet", new AngleServlet());
             servletRegistration.addMapping("/angleServlet");
         }
     }
     ```

   * spring-web也有一个ServletContainerIntializer

     ![1](C:\Users\Administrator\Desktop\复习\素材\pic\spring\1.jpg)

   * Spring annotation config

     ![3](C:\Users\Administrator\Desktop\复习\素材\pic\spring\3.jpg)

     ```
     public class MyWebIntializer extends AbstractAnnotationConfigDispatcherServletInitializer {
         @Override
         protected Class<?>[] getRootConfigClasses() {
             return new Class[]{RootWebConfig.class};
         }
     
         @Override
         protected Class<?>[] getServletConfigClasses() {
             return new Class[]{WebMvcConfig.class};
         }
     
         @Override
         protected String[] getServletMappings() {
             return new String[]{"/"};
         }
     }
     ```

     ```
     @Configuration
     @ComponentScan(basePackages = {"org.example.servletContainer"}
                    ,excludeFilters = {
                         @ComponentScan.Filter(type= FilterType.ANNOTATION
                                 ,classes = {Controller.class, RestController.class})
                 })
     public class RootWebConfig {
     }
     ```

     ```
     @Configuration
     @ComponentScan(basePackages = {"org.example.servletContainer"}
                     ,includeFilters = {
                         @ComponentScan.Filter(type = FilterType.ANNOTATION
                                     ,classes = {Controller.class, RestController.class})
     })
     public class WebMvcConfig implements WebMvcConfigurer {
     }
     ```

     ![2](C:\Users\Administrator\Desktop\复习\素材\pic\spring\2.jpg)

     其他4个都是WebApplicationInitializer的抽象实现类







## 源码

![20](C:\Users\Administrator\Desktop\复习\素材\pic\spring\20.jpg)

* interface BeanDefinition

  A BeanDefinition describes a bean instance, which has property values, constructor argument values, and further information supplied by concrete(具体) implementations.

* BeanFactory 

  The root interface for accessing a Spring bean container

  ![21](C:\Users\Administrator\Desktop\复习\素材\pic\spring\21.jpg)




* PostProcessor 以ClassPathXmlApplicationContext为例

  ```
  ClassPathXmlApplicationContext#refresh()
  synchronized (this.startupShutdownMonitor) {
  			StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");
  
  			// Prepare this context for refreshing.
  			prepareRefresh();
  
  			// Tell the subclass to refresh the internal bean factory.
  			//beanFactory
  			  beanDefinitionMap Map[]  value有一个PropertyValues的属性
  			  beanDefinitionNames
  			  beanPostProcessors
  			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
  				refreshBeanFactory(){
                      ...
                      loadBeanDefinitions(beanFactory);
  				}
  
  			// Prepare the bean factory for use in this context.
  			prepareBeanFactory(beanFactory);
  
  			try {
  				// Allows post-processing of the bean factory in context subclasses.
  				//Modify the application context's internal bean factory after its standard initialization. All bean definitions will have been loaded, but no beans will have been instantiated yet.
  				postProcessBeanFactory(beanFactory);
  
  				StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
  				// Invoke factory processors registered as beans in the context.
  				invokeBeanFactoryPostProcessors(beanFactory);
  
  				// Register bean processors that intercept bean creation.
  				registerBeanPostProcessors(beanFactory);
  				beanPostProcess.end();
  				
  				....
  ```

  自定义BeanFactoryPostProcessor(implements)+<bean/>

  ### bean生命周期

  ![22](C:\Users\Administrator\Desktop\复习\素材\pic\spring\22.jpg)

  Bean factory implementations should support the standard bean lifecycle interfaces as far as possible. The full set of initialization methods and their standard order is:

  1. BeanNameAware's setBeanName
  2. BeanClassLoaderAware's setBeanClassLoader
  3. BeanFactoryAware's setBeanFactory
  4. EnvironmentAware's setEnvironment
  5. EmbeddedValueResolverAware's setEmbeddedValueResolver
  6. ResourceLoaderAware's setResourceLoader (only applicable when running in an application context)
  7. ApplicationEventPublisherAware's setApplicationEventPublisher (only applicable when running in an application context)
  8. MessageSourceAware's setMessageSource (only applicable when running in an application context)
  9. ApplicationContextAware's setApplicationContext (only applicable when running in an application context)
  10. ServletContextAware's setServletContext (only applicable when running in a web application context)
  11. postProcessBeforeInitialization methods of BeanPostProcessors
  12. InitializingBean's afterPropertiesSet
  13. a custom init-method definition
  14. postProcessAfterInitialization methods of BeanPostProcessors

  On shutdown of a bean factory, the following lifecycle methods apply:

  1. postProcessBeforeDestruction methods of DestructionAwareBeanPostProcessors
  2. DisposableBean's destroy
  3. a custom destroy-method definition

* Aware

  A marker superinterface indicating that a bean is eligible to(有资格) be notified by the Spring container of a particular framework object through a callback-style method. The actual method signature is determined by individual subinterfaces but should typically consist of just one void-returning method that accepts a single argument.

  spring容器按使用者可以分为两类

  1. 容器对象
  2. 自定义对象 通过实现对应的Aware 如AplicationContextAware获取并操作applicationContext对象

  如BeanNameAware

  作用：让Bean获取自己在BeanFactory配置中的名字（根据情况是id或者name）。 

  示例：（**spring自动调用setBeanName的方法**(自定义配置的beanName无效，取得是在BeanFactory配置中Bean的名字)，但是，Bean之中一定要有个String类型变量来保存BeanName的值，这个是在编写Bean代码时有程序员手工完成的，而不是通过什么特殊的配置。）

  ```
  public class LogginBean implements BeanNameAware {
    private String beanName = null;
    public void setBeanName(String beanName) {
      this.beanName = beanName;
    }
  }
  ```

  Spring自动调用。并且会在Spring自身完成Bean配置之后，且在调用任何Bean生命周期回调（初始化或者销毁）方法之前就调用这个方法。换言之，在程序中使用BeanFactory.getBean(String beanName)之前，Bean的名字就已经设定好了。

  invokeAwareMethods方法中调用定义

* 在实例化之前需要准备 1. BeanFactoryProcessor 2. 监听器（观察者模式）

## tomcat9 

* 中文乱码

  ${CATALINA_HOME}/conf/logging.properties 

  ​	java.util.logging.ConsoleHandler.encoding = UTF-8 ->GBK

  ​	1catalina.org.apache.juli.AsyncFileHandler.encoding =UTF-8 -> GBK

  ​	2localhost.org.apache.juli.AsyncFileHandler.encoding =UTF-8 -> GBK

  ​	3manager.org.apache.juli.AsyncFileHandler.encoding = UTF-8-> GBK

  ​	4host-manager.org.apache.juli.AsyncFileHandler.encoding = UTF-8-> GBK

  如果不行 **配置tomcat的VM添加: -Dfile.encoding=UTF-8** 

* 



## xml

### bean标签

### parent属性

先决条件：

1. 子bean必须与父bean保持兼容，也就是说子bean中必须有父bean定义的所有属性
2. 父bean必须是抽象bean abstract="true"

注意：

1. **此时子bean可以继承其他类**

2. 如果父bean有class属性，而子bean没有，那么子bean的class就和父bean相同

3. **如果父bean没有定义class属性，子bean必须定义class属性，这时候父bean实际上仅仅是一个纯模版或抽象bean**

   ```
   <bean id="a" class="org.example.A"  parent="b"/>
   <bean id="b"  abstract="true">
       <property name="str"  value="xxxx"/>
   </bean>
   
   private String str;
   public void setStr(String str){
   this.str=str;
   }
   
   public String getStr() {
   return str;
   }
   ```

### default-autowire属性(beans标签)

```java
<bean class="org.example.jedis.spel.B" id="b"/>
<bean class="org.example.jedis.spel.C" id="myInterface2"/>
<bean id="a" class="org.example.jedis.spel.A"/>

private I myInterface;

public I getMyInterface() {
    return myInterface;
}

public void setMyInterface2(I myInterface) {
    this.myInterface = myInterface;
}
```

- byName

  自动寻找name='myInterface2'(根据set方法)的bean，找不到则该字段为空

- byType

  多个报错  0个该字段为空

- default = no

  不会自动装配字段，需要手动指定属性

## 注解

* @Order、Ordered不影响类的加载顺序而是影响Bean加载到如IOC容器之后执行的顺序（优先级）； 

  对方法不生效

* @Bean方法参数自动注入@Autowired

  同一个配置类中按照顺序执行，当需要某个方法参数会现在ioc容器找，没有则等待

* 