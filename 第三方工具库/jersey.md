## JAX-RS

java.ws.rs是jax-rs规范中定义的包名

**jax-rs全称Java API for RESTful Services**

jax-rs规范目前版本是2.0规范文档

jax-rs中定义了z
一组启动方式 (以jee作为http容器 还是配合servlet作为http容器)
一组注解@GET, @POST, @DELETE, @PUT, @Consumes … 通过 POJO Resource类 提供Rest服务

如JSR规范定义Servlet是以继承HttpServlet 并重写doGet doPost do…方法一样 遵循这套标准都可以称为Servlet 写的Servlet程序 可以不经过任何修改放到任何实现Servlet容器中运行 写的jax-rs程序可以不经任何修改和任何jax-rs框架配合使用 而**Spring MVC**是以Servlet为http容器 并**自己构建了一套Api没有遵循jax-rs规范**

目前实现jax-rs标准的框架有很多

Apache CXF开源的Web服务框架
**Jersey 由Sun提供的JAX-RS的参考实现**
RESTEasy JBoss的实现
Restlet 由Jerome Louvel和Dave Pawson开发 是最早的REST框架 先于JAX-RS出现
Apache Wink 一个Apache软件基金会孵化器中的项目 其服务模块实现JAX-RS规范

<dependency>
    <groupId>javax.ws.rs</groupId>
    <artifactId>javax.ws.rs-api</artifactId>
    <version>2.1.1</version>
</dependency>





## jersey

### HelloWorld

0. 创建项目 选择maven-webapp
1. 依赖 **JDK1.8**

```
<jersey.version>2.25</jersey.version>

<dependency>
            <groupId>javax.ws.rs</groupId>
            <artifactId>javax.ws.rs-api</artifactId>
            <version>2.1.1</version>
        </dependency>
        <!--<dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>-->
        <dependency>
            <groupId>org.glassfish.jersey.core</groupId>
            <artifactId>jersey-server</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.core</groupId>
            <artifactId>jersey-client</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.containers</groupId>
            <artifactId>jersey-container-servlet</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.inject</groupId>
            <artifactId>jersey-hk2</artifactId>
            <version>2.28</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-json-jackson</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-multipart</artifactId>
            <version>${jersey.version}</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.22</version>
        </dependency>
<!--        <dependency>
            <groupId>jakarta.ws.rs</groupId>
            <artifactId>jakarta.ws.rs-api</artifactId>
            <version>3.0.0</version>
        </dependency>-->
```

1. web.xml配置

   ```
    <servlet>
           <servlet-name>jersey-servlet</servlet-name>
           <servlet-class>org.glassfish.jersey.servlet.ServletContainer</servlet-class>
   <!--        配置可以在这里或config里-->
           <!--<init-param>
            &lt;!&ndash;   继承Application getSingletons（Get a set of root resource, provider and feature instances.）
               作为应用初始化参数&ndash;&gt;
               <param-name>javax.ws.rs.Application</param-name>
               <param-value>org.example.jersey.config.JerseyConfig</param-value>
           </init-param>-->
           
           <init-param>
               <!--        如扫描jersey.config.server.provider.packages
           jersey.config.server.provider.scanning.recursive是否递归扫描-->
               <param-name>jersey.config.server.provider.packages</param-name>
               <param-value>org.example.jersey.rest</param-value>
           </init-param>
           <load-on-startup>1</load-on-startup>
       </servlet>
       <servlet-mapping>
           <servlet-name>jersey-servlet</servlet-name>
           <url-pattern>/*</url-pattern>
       </servlet-mapping>
   ```

2. rest接口

```
@Path("/")
public class PersonRest {
    @GET
//    @Path("/helloWorld")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(){
        return "HelloWorld";
    }
}
```

```
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        //包扫描
        packages("org.example.jersey.rest");
        //注册json功能
        register(JacksonFeature.class);
        //注册文件上传功能
        register(MultiPartFeature.class);
    }
}
```

问题：中文乱码

解决：@Produces("text/plain;charset=utf-8")

问题： url-pattern / /*区别

- /仅替换`servlet容器`的默认内置`servlet`，用于处理所有与其他注册的`servlet`不匹配的请求,所有静态资源（js，css，image,html等）的访问都将交给该servlet处理,**而jsp页面则交给`servlet容器`内置的`JSP servlet(org.apache.jasper.servlet.JspServlet)`处理**  

  如果静态资源的前缀名与`Controller`中处理的请求同名时，会被Controller中的请求覆盖，否则就会返回静态资源本来的页面。 

- /* 会覆盖其他所有的`servlet` 包括`servlet容器`提供的所有`servlet`，如缺省`servlet`和`JSP servlet` 无论你发出什么请求，都会在该`servlet`拦截处理。 

- 空  仅仅只能拦截处理主页请求 

- /** 什么请求都不能拦截处理，相当于没有设置 

jersey / 与 mvc 效果不同

### 返回Json格式数据

### utf-8格式

128个**US-ASCII**字符只需一个字节编码（Unicode范围由**U+0000至U+007F**）。 带有附加符号的拉丁文、希腊文、西里尔字母、亚美尼亚语、希伯来文、阿拉伯文、叙利亚文及它拿字母则需要二个字节编码（Unicode范围由U+0080至U+07FF）。 其他基本多文种平面（BMP）中的字符（这包含了大部分常用字）使用三个字节编码。 其他极少使用的Unicode 辅助平面的字符使用四字节编码。

汉字从0800~FFFF  

 如果一个字节的第一位是0，则这个字节单独就是一个字符；如果第一位是1，则连续有多少个1，就表示当前字符占用多少个字节 。**汉字占3个字节**

**java里char是2个字节，导致一些字不存在**

**GBK/GB2312  ascii 1个字节 汉字2个字节**

* 将二进制字符串转为字符

```

String binStr="1001001110011111";
//1.获取二进制字符串每位 0/1
char[] charArray = binStr.toCharArray();
int[] res=new int[charArray.length];
for (int i = 0; i < charArray.length; i++) {
    res[i]=charArray[i]-'0';
}
//2.每位求和转化为字符
int sum=0;
System.out.println(res.length);
for (int i = 0; i < res.length; i++) {
    sum+=res[res.length-1-i]<<i;
}
System.out.println(sum);
System.out.println((char)sum);
```

* 将字符转为二进制字符串

  ```
   String str = new String("汉".getBytes(StandardCharsets.UTF_8),StandardCharsets.UTF_8 );
          char[] charArray = str.toCharArray();
          for (char c : charArray) {
              System.out.println(Integer.toBinaryString(c));
          }
  ```

### 随机生成中文姓名

```
private String getLastName(){
    /* 598 百家姓 */
    String[] Surname= {"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许",
            "何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦","章","云","苏","潘","葛","奚","范","彭","郎",
            "鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤","滕","殷",
            "罗","毕","郝","邬","安","常","乐","于","时","傅","皮","卞","齐","康","伍","余","元","卜","顾","孟","平","黄","和",
            "穆","萧","尹","姚","邵","湛","汪","祁","毛","禹","狄","米","贝","明","臧","计","伏","成","戴","谈","宋","茅","庞","熊","纪","舒",
            "屈","项","祝","董","梁","杜","阮","蓝","闵","席","季","麻","强","贾","路","娄","危","江","童","颜","郭","梅","盛","林","刁","钟",
            "徐","邱","骆","高","夏","蔡","田","樊","胡","凌","霍","虞","万","支","柯","昝","管","卢","莫","经","房","裘","缪","干","解","应",
            "宗","丁","宣","贲","邓","郁","单","杭","洪","包","诸","左","石","崔","吉","钮","龚","程","嵇","邢","滑","裴","陆","荣","翁","荀",
            "羊","于","惠","甄","曲","家","封","芮","羿","储","靳","汲","邴","糜","松","井","段","富","巫","乌","焦","巴","弓","牧","隗","山",
            "谷","车","侯","宓","蓬","全","郗","班","仰","秋","仲","伊","宫","宁","仇","栾","暴","甘","钭","厉","戎","祖","武","符","刘","景",
            "詹","束","龙","叶","幸","司","韶","郜","黎","蓟","溥","印","宿","白","怀","蒲","邰","从","鄂","索","咸","籍","赖","卓","蔺","屠",
            "蒙","池","乔","阴","郁","胥","能","苍","双","闻","莘","党","翟","谭","贡","劳","逄","姬","申","扶","堵","冉","宰","郦","雍","却",
            "璩","桑","桂","濮","牛","寿","通","边","扈","燕","冀","浦","尚","农","温","别","庄","晏","柴","瞿","阎","充","慕","连","茹","习",
            "宦","艾","鱼","容","向","古","易","慎","戈","廖","庾","终","暨","居","衡","步","都","耿","满","弘","匡","国","文","寇","广","禄",
            "阙","东","欧","殳","沃","利","蔚","越","夔","隆","师","巩","厍","聂","晁","勾","敖","融","冷","訾","辛","阚","那","简","饶","空",
            "曾","毋","沙","乜","养","鞠","须","丰","巢","关","蒯","相","查","后","荆","红","游","郏","竺","权","逯","盖","益","桓","公","仉",
            "督","岳","帅","缑","亢","况","郈","有","琴","归","海","晋","楚","闫","法","汝","鄢","涂","钦","商","牟","佘","佴","伯","赏","墨",
            "哈","谯","篁","年","爱","阳","佟","言","福","南","火","铁","迟","漆","官","冼","真","展","繁","檀","祭","密","敬","揭","舜","楼",
            "疏","冒","浑","挚","胶","随","高","皋","原","种","练","弥","仓","眭","蹇","覃","阿","门","恽","来","綦","召","仪","风","介","巨",
            "木","京","狐","郇","虎","枚","抗","达","杞","苌","折","麦","庆","过","竹","端","鲜","皇","亓","老","是","秘","畅","邝","还","宾",
            "闾","辜","纵","侴","万俟","司马","上官","欧阳","夏侯","诸葛","闻人","东方","赫连","皇甫","羊舌","尉迟","公羊","澹台","公冶","宗正",
            "濮阳","淳于","单于","太叔","申屠","公孙","仲孙","轩辕","令狐","钟离","宇文","长孙","慕容","鲜于","闾丘","司徒","司空","兀官","司寇",
            "南门","呼延","子车","颛孙","端木","巫马","公西","漆雕","车正","壤驷","公良","拓跋","夹谷","宰父","谷梁","段干","百里","东郭","微生",
            "梁丘","左丘","东门","西门","南宫","第五","公仪","公乘","太史","仲长","叔孙","屈突","尔朱","东乡","相里","胡母","司城","张廖","雍门",
            "毋丘","贺兰","綦毋","屋庐","独孤","南郭","北宫","王孙"};

    return   Surname[new Random().nextInt(Surname.length)];
}
private String getFirstName() throws UnsupportedEncodingException {
    int len = new Random().nextInt(2)+1;
    String str="";
    for (int i = 0; i < len; i++) {
        int  highPos = 176 + Math.abs(new Random().nextInt(71));
        int lowPos=161 + Math.abs(new Random().nextInt(94));

        byte[] bytes=new byte[2];
        bytes[0]=Integer.valueOf(highPos).byteValue();
        bytes[1]=Integer.valueOf(lowPos).byteValue();
        str += new String(bytes, "GB2312");
    }
    return str;
}
```

*返回MediaType默认是text/*，如果想返回json数据

```
@Produces("application/json;charset=utf-8")
```
### 注解

- @Path 注解位置 类注解 方法注解 

  标注class时 表明该类是个资源类 凡是资源类必须使用该注解 

  标注method时 表示具体的请求资源的路径 

- @GET @POST @PUT @DELETE 注解位置 方法注解 

  指明接收HTTP请求的方式属于get,post,put,delete中的哪一种 具体指定请求方式 是由客户端发起请求时指定 

- @Consumes  方法注解 

  指定HTTP请求的MIME类型 默认是*/* 表示任意的MIME类型 该注解支持多个值设定 可以使用MediaType来指定MIME类型
  MediaType的类型大致有
  application/xml
  application/atom+xml
  application/json
  application/svg+xml
  application/x-www-form-urlencoded
  application/octet-stream
  multipart/form-data
  text/plain
  text/xml
  text/html

  @Consumes({MediaType.APPLICATION_JSON}) 

  A **media type**  **or MIME type** 

  ```
  type/subtype;parameter=value
  
  Discrete types：
  application
  Any kind of binary data that doesn't fall explicitly into(明确属于) one of the other types; either data that will be executed or interpreted in some way or binary data that requires a specific application or category of application to use. Generic binary data (or binary data whose true type is unknown) is application/octet-stream. Other common examples include application/pdf, application/pkcs8, and application/zip. (Registration at IANA)
  
  audio
  Audio or music data. Examples include audio/mpeg, audio/vorbis. (Registration at IANA)
  
  example
  Reserved for use as a placeholder in examples showing how to use MIME types. These should never be used outside of sample code listings and documentation. example can also be used as a subtype; for instance, in an example related to working with audio on the web, the MIME type audio/example can be used to indicate that the type is a placeholder and should be replaced with an appropriate one when using the code in the real world.
  
  font
  Font/typeface data. Common examples include font/woff, font/ttf, and font/otf. (Registration at IANA)
  
  image
  Image or graphical data including both bitmap and vector still images as well as animated versions of still image formats such as animated GIF or APNG. Common examples are image/jpeg, image/png, and image/svg+xml. (Registration at IANA)
  
  model
  Model data for a 3D object or scene. Examples include model/3mf and model/vrml. (Registration at IANA)
  
  text
  Text-only data including any human-readable content, source code, or textual data such as comma-separated value (CSV) formatted data. Examples include: text/plain, text/csv, and text/html. (Registration at IANA)
  
  video
  Video data or files, such as MP4 movies (video/mp4). (Registration at IANA)
  
  Multipart types：
  message
  A message that encapsulates（封装） other messages. This can be used, for instance, to represent an email that includes a forwarded message as part of its data, or to allow sending very large messages in chunks as if it were multiple messages. Examples include message/rfc822 (for forwarded or replied-to message quoting) and message/partial to allow breaking a large message into smaller ones automatically to be reassembled by the recipient. (Registration at IANA)
  
  multipart
  Data that is comprised of multiple components which may individually have different MIME types. Examples include multipart/form-data (for data produced using the FormData API) and multipart/byteranges (defined in RFC 7233: 5.4.1 and used with HTTP's 206 "Partial Content" response returned when the fetched data is only part of the content, such as is delivered using the Range header). (Registration at IANA)
  ```

- @Produces  方法注解 

  指定HTTP响应的MIME类型 默认是*/* 表示任意的MIME类型 同Consumes使用MediaType来指定MIME类型 

- @PathParam  参数注解  相当于@PathVariable

  配合@Path进行使用 可以获取URI中指定规则的参数 

  @Path("{username"}) 

  public User getUser(@PathParam("username") String userName) { ...}

- @QueryParam  参数注解 

  获得querystring

- @FormParam    application/x-www-form-urlencoded **不兼容@QueryParam**

  ****Binds the value(s) of a form parameter contained within a **request entity body** to a resource method parameter.Values are URL **decoded** unless this is **disabled** **using the Encoded annotation**.

  @Consumes("application/x-www-form-urlencoded") public void post(@FormParam("name") String name) 

- @FormDataParam

  与@FormParam 类似，通常在上传文件的时候

- @HeaderParam

  get header param info from hhtp request

  public Response getUser(@HeaderParam("user-agent") String userAgent) 

- @CookieParam 

  get cookie value from hhtp request

- @MatrixParam

  Binds the value(s) of a URI **matrix parameter** to a resource method parameter,resource class field, or resource class bean property.

  请求url的格式为 url;key1=value1;key2=value2;… 

  如http://localhost:9091/person/birthdayRange4;startDate=2014-05-17;endDate=2016-05-17?pageNum=1&pageSize=8 

  

     public Response getBooks(
              @MatrixParam("author") String author,
              @MatrixParam("country") String country)

- @DefaultValue 

  配合前面的参数注解等使用 用来设置默认值 

- @BeanParam

  The annotation that may be used to inject custom JAX-RS "parameter aggregator" value **object** into a resource class field, property or resource method parameter.

- @Context

  This annotation is used to inject information into a class field, bean property or method parameter.

  类似Autowired

- @Javax.inject.Inject

  <dependency>
      <groupId>javax.inject</groupId>
      <artifactId>javax.inject</artifactId>
      <version>1</version>
  </dependency>

  JSR330

  @Javax.inject.Inject

  Identifies injectable constructors, methods, and fields. May apply to static as well as instance members. An injectable member may have any access modifier (private, package-private, protected, public).

  1. constructors
  2. fields
  3. methods

  1>2>3  

  Fields and methods in superclasses are injected before those in subclasses. Ordering of injection among fields and among methods in the same class is not specified.For constructors,@Inject can apply to at most one constructor per class

### requestParam

* queryString @QueryParam

* /{id} @PathParam("id") **不需要检验**

* application/x-www-form-urlencoded 

  * @FormParam 接收指定参数

  * MultivaluedMap<K, V> extends Map<K, List<V>> 接收所有参数、

    **必须是MultivaluedMap<String,String> 不能是MultivaluedMap<String,Object>**

* queryString+application/x-www-form-urlencoded  @QueryParam+@FormParam+@DefaultValue

  * Comparator是函数式接口，其中equals是Object中的方法，**这种对Object类的方法的重新声明会让方法不再是抽象的**。 
  * **接口能使用Object的所有方法**
  * **Conusme默认是 application/x-www-form-urlencoded**

* json

  * Map<String,Object> paramsMap 接收所有
  * T t 接收一个T类型javaBean

* Cookie  header   key: Cookie  value:  xxx=xxxxx

* @Put 表单+json

* @Delete

## 文件上传

1. 方式一 流

   ```
   @POST
   @Path("/upload")
   @Consumes(MediaType.MULTIPART_FORM_DATA)
   /*
   * FormDataContentDisposition 文件的描述信息 如名称，大小，路径等
   * Context 能获取容器所在上下文信息
    */
   public String upload1(@FormDataParam("file_name")InputStream is,
                         @FormDataParam("file_name") FormDataContentDisposition disposition,
                         @Context ServletContext context) throws IOException {
       System.out.println("表单name: "+disposition.getName()); //上传文件的表单name值
       String fileName = disposition.getFileName();//文件原始名
       String realPath = context.getRealPath("/"); //获取当前项目在文件系统中的位置
       System.out.println("realPath: "+realPath);
       String dir="/static/upload/"; //上传文件存放目录
       DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
       String date = formatter.print(DateTime.now());//生成日期目录
       String uploadDir=realPath+dir+date; //保存文件的目录路径
       System.out.println(uploadDir);
       String[] splits = fileName.split("\\.");
       String suffix=splits.length>0?splits[splits.length-1]:"";
       String newFilePrefix=UUID.randomUUID().toString().replaceAll("-","");
       String newFileName=newFilePrefix+"."+suffix; //新文件名字
       File file = new File(uploadDir);
       if(!file.exists()){
           file.mkdirs(); //目录不存在创建
       }
       // inpustream -> file
       File ultimateFile=new File(uploadDir,newFileName);
       System.out.println(ultimateFile.getAbsolutePath());
       FileOutputStream fos = new FileOutputStream(ultimateFile);
       byte[] bytes=new byte[1024];
       int len;
       while ((len=is.read(bytes))!=-1){
           fos.write(bytes,0,len);
       }
       return "upload complete";
   
   }
   ```

2. 方式二

   ```
   public String upload1(FormDataMultiPart multiPart,
                         @Context ServletContext context) throws IOException {
       FormDataBodyPart bodyPart = multiPart.getField("file_name");
       ContentDisposition disposition = bodyPart.getFormDataContentDisposition();
       InputStream is=bodyPart.getValueAs(InputStream.class);
       System.out.println("表单name: "+bodyPart.getName()); //上传文件的表单name值
       String fileName = disposition.getFileName();//文件原始名
       ...
   }
   ```



## Jetty插件 Helloworld

```
<plugin>
  <groupId>org.eclipse.jetty</groupId>
  <artifactId>jetty-maven-plugin</artifactId>
  <version>9.4.12.v20180830</version>
  <configuration>
    <!--        >0    保存后扫描进行热部署的间隔时间内-->
    <scanIntervalSeconds>10</scanIntervalSeconds>
    <webApp>
      <contextPath>/</contextPath>
    </webApp>
    <httpConnector>
      <port>8080</port>
    </httpConnector>
  </configuration>
</plugin>
```

问题： java.lang.TypeNotPresentException: Type org.eclipse.jetty.maven.plugin.JettyRunMojo not present

解决：将jetty版本换成低版本

问题：mvn jetty:run 热部署不生效

## joda-time使用

```
DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy/MM/dd");
//string 转 dateTime
DateTime dateTime = DateTime.parse("2021/01/09",formatter );
// dateTime 转 string
formatter.print(dateTime);
```