# SpringMvc

## 环境配置

* 配置文件

  ```
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:application.xml</param-value>
  </context-param>
  
  
  <filter>
    <filter-name>encoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encoding</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>
  
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  
  <servlet>
    <servlet-name>mvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>mvc</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
  ```

  * <url-pattern>/*</url-pattern>

    The `/*` on a servlet **overrides all other servlets, including all servlets provided by the servletcontainer such as the default servlet and the JSP servlet**. Whatever request you fire, it will end up in that servlet. This is thus a bad URL pattern for servlets. Usually, you'd like to use `/*` on a [`Filter`](http://download.oracle.com/javaee/6/api/javax/servlet/Filter.html) only. It is able to let the request continue to any of the servlets listening on a more specific URL pattern by calling [`FilterChain#doFilter()`](http://download.oracle.com/javaee/6/api/javax/servlet/FilterChain.html#doFilter%28javax.servlet.ServletRequest,%20javax.servlet.ServletResponse%29).

    

    The `/` doesn't override any other servlet. **It only replaces the servletcontainer's built in default servlet for all requests which doesn't match any other registered servlet**. This is normally only invoked on static resources (CSS/JS/image/etc) and directory listings. The servletcontainer's built in default servlet is also capable of dealing with HTTP cache requests, media (audio/video) streaming and file download resumes. Usually, you don't want to override the default servlet as you would otherwise have to take care of all its tasks, which is not exactly trivial (JSF utility library [OmniFaces](http://omnifaces.org/) has an [open source](https://github.com/omnifaces/omnifaces/blob/master/src/main/java/org/omnifaces/servlet/FileServlet.java) [example](http://showcase.omnifaces.org/servlets/FileServlet)). This is thus also a bad URL pattern for servlets. As to why JSP pages doesn't hit this servlet, it's because the servletcontainer's built in JSP servlet will be invoked, which is already by default mapped on the more specific URL pattern `*.jsp` 

    <url-pattern></url-pattern>

    Then there's also the empty string URL pattern ` `. **This will be invoked when the context root is requested**. This is different from the <welcome-file> approach **that it isn't invoked when any subfolder is requested.** This is most likely the URL pattern you're actually looking for in case you want a "[home page servlet](https://stackoverflow.com/questions/33248473/change-default-homepage-in-root-path-to-servlet-with-doget/)". I only have to admit that I'd intuitively expect the empty string URL pattern ` ` and the slash URL pattern `/` be defined exactly the other way round, so I can understand that a lot of starters got confused on this. But it is what it is. 

    上述配置配置只有一个servlet，导致所有请求都没有匹配的servlet，全部走这个mvc缺省的servlet（包括静态资源）。

  * 乱码

    request.setCharacterEncoding
    **Overrides the name of the character encoding used in the body of this request**. This method must be called prior to reading request parameters or reading input using getReader(). Otherwise, it has no effect.

    

    response.setCharacterEncoding

    **Sets the character encoding (MIME charset) of the response being sent to the client**, for example, to UTF-8. If the character encoding has already been set by setContentType or setLocale, this method overrides it. Calling setContentType with the String of text/html and calling this method with the String of UTF-8 is equivalent with calling setContentType with the String of text/html; charset=UTF-8. 必须在getWriter执行之前或者response被提交之前 

    

    response.setContentType指定 HTTP 响应的编码,同时指定了浏览器显示的编码.  

    response.setContentType("text/html; charset=UTF-8");

  * 静态资源放行

    ```
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    
    http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd
    ```

    <mvc:resources mapping="/js/**" location="/js/"/>

    location是指webapp下的文件

    mapping 是url /js前缀的都会对应文件夹中找

* idea application update option

  The update options are different depending on:

  - the artifact format, i.e. on whether the application artifact is **exploded** (unpacked) or packed (e.g. WAR, EAR)
  - the run/debug configuration type, i.e. on whether the run/debug configuration is local or remote (see [Local and remote run configurations](https://www.jetbrains.com/help/idea/creating-run-debug-configuration-for-application-server.html#local_and_remote_run_configs))

  | Option                       | Description                                                  | Available for                                                |
  | ---------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | Update resources             | All changed resources are updated (HTML, **JSP**, JavaScript, CSS and image files). | Exploded artifacts in local configurations                   |
  | Update classes and resources | Changed resources are updated; changed Java classes (EJBs, servlets, etc.) are recompiled.**In the debug mode, the updated classes are hot-swapped**. In the run mode, IntelliJ IDEA just updates the changed classes in the output folder. Whether such classes are actually reloaded in the running application, depends on the capabilities of the runtime being used. | Exploded artifacts in local configurations                   |
  | Hot swap classes             | Changed classes are recompiled and reloaded at runtime. This option works only in the debug mode. | Packed artifacts in local configurations; exploded and packed artifacts in remote configurations |
  | Redeploy                     | The application artifact is rebuilt and redeployed. The operation may be time-consuming.(配置文件) | Exploded and packed artifacts in local and remote configurations |
  | Restart server               | The server is restarted. The application artifact is rebuilt and redeployed. The operation may be very time-consuming.（服务器配置） | Exploded and packed artifacts in local configurations        |

* welcome-file-list 

  If the <welcome-file-list> tag is not defined in web.xml or the welcome files defined in the <welcome-file> tags does not exist then the server would look for the following files in the given sequence:

   1) index.html  

  2) index.htm 

  3) index.jsp 

  /test.jsp  /指的是webapp文件夹

  html 404 don't work:

## flow of Spring Web MVC

![1](C:\Users\Administrator\Desktop\复习\素材\pic\spring\1.png)

* HandlerMaping  & handlerAdapter

  The *HandlerAdapter* is basically an interface which facilitates the handling of HTTP requests in a very flexible manner in Spring MVC.

  It's used in conjunction（连着） with the *HandlerMapping*, which maps a method to a specific URL.

  The *DispatcherServlet* then uses a *HandlerAdapter* to invoke this method. The servlet doesn't invoke the method directly – it basically serves as a bridge between itself and the handler objects, leading to a loosely coupling（耦合） design.

  ​	*HandlerMapping* is an interface that defines a mapping between requests and [handler objects](https://www.baeldung.com/spring-mvc-handler-adapters) 

  ​	*HandlerAdapter* doesn't invoke the method directly /bridge  between itself and   				handler objects

  ```java
  public interface HandlerAdapter {
      boolean supports(Object handler);
      
      ModelAndView handle(
        HttpServletRequest request,
        HttpServletResponse response, 
        Object handler) throws Exception;
      
      long getLastModified(HttpServletRequest request, Object handler);
  }
  ```

  

* The DispatcherServlet checks  view resolver configured  ,invokes the specified view component  and render

## 组件

* @requestMapping Annotation for **mapping web requests onto methods** in request-handling classes with flexible method signatures.

  * method没写 默认所有方法都可以
  * params限制了请求参数 如{"account=a","b"}则必须带有请求参数account=a且有b 

* @RequestParam Annotation which indicates that a method parameter should be bound to a web request parameter

  * String name default “” The name of the request parameter to bind to(**请求参数的名字**)
  * boolean required default true;

* @ResponseBody 

  Annotation that **indicates a method return value should be bound to the web response body**. Supported for annotated handler methods

* @RequestBody

  request header Content-Type 判断：

  * 其他格式包括application/json, application/xml等。这些格式的数据，必须使用@RequestBody来处理 

    requestBody接收的是JSON对象的字符串 

  * application/x-www-form-urlencoded @RequestParam, @ModelAttribute也可以处理 

    @RequestBody也能处理 

  * 不能处理 multipart/form-data 

* @PathVariable Annotation which indicates that **a method parameter should be bound to a URI template variable**. **Supported for RequestMapping** annotated handler method

  ```
  @RequestMapping(value = "/param/{id}")
  public String param(@PathVariable("id") Integer id){...}
  ```

* Rest(Representation state transfer)

  * 资源 url
  * transfer requestmethod
  * 表现层 txt pdf...

* @RequestHeader Annotation which indicates that **a method parameter should be bound to a web request header**. @Target(ElementType.**PARAMETER**)

  If the method parameter is **Map<String, String>, MultiValueMap<String, String>**, or HttpHeaders then the map is populated with all header names and values.

* @CookieValue Annotation to indicate that a method parameter is bound to an HTTP cookie

  The method parameter may be declared as type **javax.servlet.http.Cookie** or as **cookie value type** (String, int, etc.).

  <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>2.5</version>
      <scope>provided</scope>
  </dependency>

```
@CookieValue("JSESSIONID") String cookie
@CookieValue("JSESSIONID") Cookie cookies
```

* Stream.Reduce

  ```
  Optional<T> reduce(BinaryOperator<T> accumulator);
  ```

  未定义初始值，从而第一次执行的时候第一个参数的值是Stream的第一个元素，第二个参数是Stream的第二个元素 

  ```
  T reduce(T identity, BinaryOperator<T> accumulator);
  ```

  定义了初始值，从而第一次执行的时候第一个参数的值是初始值，第二个参数是Stream的第一个元素 

  ```
  BinaryOperator<T> accumulator
  R apply(T t, U u);
  ```

* @ModelAttribute

  Annotation that **binds** a method parameter or **method return value to a named model attribute**, exposed to a web view. Supported for controller classes with @RequestMapping methods.

  场景：提交数据不完整，使用数据库默认数据

      @ModelAttribute("user2")
      public User show(String userName){
          User user = new User();
          user.setName(userName);
          user.setId(1);
          user.setDate(new Date());
          return user;
      }或者 public void show(String userName,Map<String,User>map) 
      
       @RequestMapping("/ModelAttribute")
          public void modelandvalue(@ModelAttribute("user2") User user){
              System.out.println(user);
          }或者
              public void modelandvalue(Model model){
              System.out.println(model.getAttribute("user"));
          }

* @SessionAttributes Annotation that indicates the session attributes that a specific handler（controller） uses. @Target({ElementType.TYPE})

  ```
  @SessionAttributes({"a","b"})
  
      @RequestMapping("/putSessionAttribute")
      @ResponseBody
      public void putSessionAttribute(HttpSession session){
          session.setAttribute("a",1);
      }
  
      @RequestMapping("/getSessionAttribute")
      @ResponseBody
      public void getSessionAttribute(HttpSession session){
          System.out.println(session.getAttribute("a"));
      }
      
        @RequestMapping("/removeSessionAttribute")
      @ResponseBody
      public void removeSessionAttribute(HttpSession session,SessionStatus sessionStatus){
          //      session.removeAttribute("a"); //removeAttribute只能在本方法使用，跨方法						（getSessionAttribute）无法生效
          sessionStatus.setComplete();
          System.out.println(session.getAttribute("a"));
      }
  ```

* Model  defines a holder for model attributes. Primarily designed for adding attributes to the model. Allows for accessing the overall(整个) model as a java.util.Map.

  sevlet域对象 request，session，application  model默认是request

### 转化器

 <bean class="org.springframework.context.support.ConversionServiceFactoryBean" id="conversionService">
        <property name="converters">
            <set>
                <bean class="org.jinjianou.demo.springmvc.util.StringToDateConverter"/>
            </set>
        </property>
    </bean>

​    <mvc:annotation-driven conversion-service="conversionService"/>

* StringToDateConverter implements interface:Converter T convert(S s)
* 比如日期 yyyy/MM/dd 不能接受yyyy-MM-dd HH:mm:ss 需要转化器

```
<mvc:annotation-driven conversion-service="conversionService2"/>


 <bean   id="conversionService2"
                class="org.springframework.context.support.ConversionServiceFactoryBean">
               <property name="converters">
                       <set>
                              <bean class="org.jinjianou.spring.String2DateConverter"/>
                       </set>
               </property>
        </bean>
```

### 请求参数的绑定

* **x-www-form-urlencoded**

  These are different Form content types defined by W3C. If you want to send **simple text/ ASCII data**, then  **x-www-form-urlencoded** will work. This is the default.

  But if you have to send **non-text or large binary data**, the **[multiple/]form-data** is for that. 

  You can use **Raw** if you want to send plain text or **JSON** or any other kind of string 

  **Binary** can be used when you want to attach **non-textual data** to the request, e.g. a video/audio file, images, or any other binary data file. 

* 基本数据类型 String   
  * 接收到k-v,k要与方法参数名称相同   form需要配合@RequestParam
  * RequestBody   RequestParam都能拿到
* javaBean
  * 直接使用，会将参数映射到javaBean上 
  * JSON需要@RequestBody使用
* 集合 跟javaBean用法一样
  * 集合是不能直接进行参数绑定的，所以我们需要创建出一个类，然后在类中进行对 集合的参数绑定 
* multiple/formdata
  * 可以上传键值对,可以上传多个文件

## 响应类型和返回视图

配置jsp viewResolver

* Stirng

```
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/jsp/" />
    <property name="suffix" value=".jsp" />
</bean>
```

```
@RequestMapping(value = "/response")
public String response(){
    return "forward:index1"; //redirect:index1
}

@RequestMapping(value = "/index1")
public String index1(){
    return "index1";
}
```

* void

          //请求转发
          request.getRequestDispatcher("index").forward(request,response);
          
          //重定向
          response.sendRedirect(request.getContextPath()+"/index.jsp");
           response.sendRedirect("index1");

* modelAndView

  ModelAbdView mv=new ModelAndView();
  mv.addObject(k,v);
  mv.setViewName(view);  view指的是经过InternalSourceResolver配置过的路径 url_path无效

## 文件上传

* 	<!-- 文件上传所依赖的jar包 -->
  		<dependency>
  			<groupId>commons-fileupload</groupId>
  			<artifactId>commons-fileupload</artifactId>
  			<version>1.3.1</version>
  		</dependency>
  ```
  <form action="bind" method="post" enctype="multipart/form-data">
     <input type="file" name="file">
      <button type="submit">上传</button>
  </form>
  method="post" & enctype="multipart/form-data"
  ```

  ```
  <bean class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
      <property name="maxUploadSize" value="100000000"/>
      <property name="defaultEncoding" value="UTF-8"/>
      <property name="resolveLazily" value="false"/>
  </bean>
  ```

  ```
  public void form(@RequestParam("file") MultipartFile upload) throws IOException {
      String fileName=upload.getOriginalFilename(); //包括后缀名
      upload.transferTo(new File(StringUtils.join("E:\\",fileName)));
  }
  @RequestParam("file") name 必须要跟form的name相同
  ```

* 跨域上传

  ```
  <dependency>
        <groupId>com.sun.jersey</groupId>
        <artifactId>jersey-core</artifactId>
        <version>1.19</version>
      </dependency>
      <dependency>
        <groupId>com.sun.jersey</groupId>
        <artifactId>jersey-client</artifactId>
        <version>1.19</version>
      </dependency>
  ```

  客户端：

  ```
      public void form(@RequestParam("file") MultipartFile upload) throws IOException {
          String path="http://localhost:9090/uploads/"; //定义需要上传图片服务器的url
          Client client=new Client();//创建client
          WebResource resource = client.resource(path+"c.jpg");//和图片服务器进行连接
          resource.put(upload.getBytes());//上传文件
  ```

  服务器端：将uploads文件夹放在target/${projectName}下

  * 404

    1. 删除web.xml

    2. 删除dispatcher servlet

    3. 修改成

       <servlet-mapping>
           <servlet-name>default</servlet-name>
           <url-pattern>*.jpg</url-pattern>
       </servlet-mapping>
       <servlet-mapping>
           <servlet-name>dispatcherServlet</servlet-name>
           <url-pattern>/</url-pattern>
       </servlet-mapping>

  * 403 405 

    conf/web.xml  defaultServlet 处添加

    		<init-param>
    	      <param-name>readonly</param-name>
    	      <param-value>false</param-value>
    	   </init-param>

  * 409 在图片服务器 taget下加上对应文件夹

## 异常处理

默认异常会向上层抛出  如dao->service->controller->dispatcher->浏览器 所以需要配置自定义异常处理器

步骤：

 	1. 创建自定义的异常 implements HandlerExceptionResolver
		2. 在mvc中扫入该类

## 拦截器

**`HandlerInterceptor` is basically similar to a Servlet `Filter`**, but in contrast to the latter it just allows custom pre-processing with the option of prohibiting（forbid） the execution of the handler itself, and custom post-processing. Filters are more powerful, for example they allow for exchanging the request and response objects that are handed down(传递) the chain. Note that a filter gets configured in `web.xml`, a `HandlerInterceptor` in the application context.

As a basic guideline（准线）, fine-grained（细粒度） handler-related pre-processing tasks are candidates for `HandlerInterceptor` implementations, especially factored-out（分解出来的） common handler code and authorization checks. On the other hand, a `Filter` is well-suited for request content and view content handling, like multipart forms and GZIP compression. This typically shows when one needs to **map the filter to certain content types** (e.g. images), or to all requests.

Filter：对用户请求进行预处理，也可以对HttpServletResponse进行后处理，是个典型的处理链 

拦截器：拦截是AOP的一种实现策略 

1. Filter依赖于Servlet容器，而Interceptor不依赖于Servlet容器。
2. Filter对几乎所有的请求起作用，而Interceptor只能对action请求起作用。
3. Interceptor可以访问IOC容器Action的上下文， Filter不能 
4. 过滤器可以修改request/response，而拦截器不能 

步骤：

1. 创建类implements HandlerInterceptor 重写方法

   * boolean preHandler false不放行 在controller方法之前执行
   * void postHandler controller方法执行后/view render之前
   * void afterCompletion 所有都执行了最后执行

2. 配置mvc

   ```
   <mvc:interceptors>
       <mvc:interceptor>
           <mvc:mapping path="/**"/> <!--拦截所有-->
           <bean class="org.jinjianou.spring.MyInterceptor"/>
       </mvc:interceptor>
   </mvc:interceptors>
   ```

## 设计模式

* 工厂模式
* FactoryBean也是一个接口 getObejct()提供了更加灵活的ioc容器中bean的实现，大多使用了工厂模式和装饰着模式

