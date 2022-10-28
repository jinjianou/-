# java基础

## idea

idea project is similiar to eclipse project

idea module is similiar to eclipse project

### 安装

2020. 3.4 https://confluence.jetbrains.com/display/IDEADEV/IDEA+2020.3+latest+builds

      激活  1. 使用30天重置  ide-eval-resetter-2.1.6.jar

      2. 直接激活  BetterIntelliJ.zip  拖入窗口后 重启  help->edit custom vm option 

         ```
         -javaagent:C:\Users\Public\.BetterIntelliJ\BetterIntelliJ-1.15.jar
         ```

         表示已经成功激活了

### 常用设置

* view> appearance > toolbar，tool window bar 

  tool window: alt + num   

   setting：

* 设置主题  appearance  
* 设置字体 editor -> font（固定） editor -> general mouse control（滚轮） 
* 鼠标放移到代码提示    editor -> general > code editing  quick documentation  
* 自动导包  editor -> general > auto import java
* show line numbers/show method seperators  editor -> general >  appearance  
* match case  editor -> general >  code completion   match case
* editor tabs editor -> general >  editor tabs 
* color schema  editor ->color schema  -> general (界面颜色的主题)
* code  templates  editor -> File and code  templates 选中class 在includes里编辑
* power save  File->Power save mode(代码提示等消失)
* serialVesionUID editor -> inspections  serializable class without  'serialVesionUID' 勾选 severity=error 在类上alter+enter



### 快捷键

* ctrl+alt+H  call hierarchy(the charater is usered in which file)

  ctrl+alt+F7 show usage = call hierarchy + position 

* 万能 alt+enter  

* 复制行 ctrl+D

* 删除行 ctrl+y

* move statement up ctrl+shift 上/下

* 重命名 shift +f6

* 搜索文件 Ctrl+N

* 代码块包围  ctrl+alt+t

## 代码模板

**配置常用代码缩写，预定义固定模式代码**

1. settings>editor>live Template  可以自定义
2. Settings → Editor → General → Postfix Completion  只能使用100.fori这类后缀模式

常用代码模板

​	soutm 打印当前方法 soutv 打印距离最近变量 soutp 打印最近形参

​	forr 反向  iter增强for

​	ifn if null   inn  if not  null

​	psf  public static final   prsf  private static final 

​	

## 调试

shared memory settings->Build,extention,deployment->debugger java 可以节约内存

dump：数据->静态形式



step over(F8)  不会进入任何方法
step into(F7)  不会进入系统方法 会进入自定义方法

force step into(ctrl+shift+F7)   会进入任何方法

step out(shift+F8)  跳出方法/循环



条件判断 ：在断点处右键 输入condition 不满足不会在该断点停留



alt+F8(evaluate expression) 查看表达式的值

​	

## 离散知识点

* SE

  1. 基本数据类型自动类型转化（兼容且源取值范围<目标）： byte→short→int→long→float→double

     或char→int 

     强制类型转化：精度确实

  2. default/friendly 同一包下可见 protected 他同一包下或子孙类可见   public>protected>default>private

  3. java中只有按值传递，并没有所谓的按引用传递 

     只是**对于引用数据类型，传递的是对象的地址**

  4. ^ 异或 相当于**不进位二进制加法**

  5. hash碰撞 两个字符串的hash函数值相同 map里指的是放在同一个bucket中

  6. 装载因子 0.75  offers a good  tradeoff(折中) between time and spcace costs.Higher values decrease  the space overhead(loss) but increase lookup cost  同时加大hash碰撞的概率

       

     ```
     newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
     
     threshold = newThr;
     
     if (size > threshold || (tab = table) == null ||
         (n = tab.length) == 0)
         n = (tab = resize()).length;
     ```

  7. map bucket length Is  2^n

     hash值不能直接使用（太大) 于是想到% ; 如果除数是2^n 则hash % length等价于 hash & (length -1) 效率比%高

  8. serialVesionUID 若不配置则**在运行时根据类内部细节自动生成**，若变更则看可能发生变化，建议显式声明（配合ObjectStream测试）

     * 被序列化的类内部所有属性必须序列化（基本数据类型可序列化）
     * transient修饰的属性会被序列化

  * 基本数据类型 如果是成员变量则在堆上创建；局部变量在线程的操作数栈上

## 泛型

* 实例化时不明确指定类的泛型，则此泛型为Object类型

* 父类已指定泛型 子类可以不指定

  父类不指定泛型 子类需要指定且相同

* 泛型方法，这个方法的泛型参数类型和当前类的泛型无关（当前类可为普通类）

  ```
  public <S extends Comparable<S>> void  b(S s1, S s2)
  ```

* String[]可以看做Object[]的子类（类型兼容）

* 通配符？ 内部遍历或读取时用Object 不允许写入 除非是null

  ```
  ParameterizedType
  
  Type[] getActualTypeArguments:Returns an array of Type objects representing the actual type arguments to this type
  如T<A,B>-> A,B
  
  Type getRawType();
  
  Type getOwnerType(); Returns a Type object representing the type that this type is a member of. For example, if this type is O<T>.I<S>  I<S>.getOwnerType()-> O<S>
  ```

* 获取实际泛型类型，如List< String >的String而不是E

  ```
   ParameterizedType type=(ParameterizedType)getStrList().getClass().getGenericSuperclass();
          Type[] actualTypeArguments = type.getActualTypeArguments();
          System.out.println("type is "+Arrays.toString(actualTypeArguments));
          
          
           static List<String> getStrList(){
          return new ArrayList<String>(){
              private static final long serialVersionUID = 4486604239167882738L;
          };
      }
  ```

  ​	**注意： 由于实例化的时候只会指定自己的泛型类型而没有指定父类的具体泛型，所以需要匿名实例化父类**

## 集合

### LikedList(1.8)

* offer:Boolean==add :Boolean 
* poll(1.5): null / remove:IndexOutOfBoundsException
* element: NoSuchElementException == getFirst NoSuchElementException  peek:null

## 注解

* 生成javaDoc:  Tools -> generate javadoc -encoding utf-8 -charset utf-8

  ![1](C:\Users\Administrator\Desktop\复习\素材\pic\java\1.jpg)

* value属性不用写name（只传递这一个属性值且其他属性均有默认值）

* 元注解

  Retention(保留) 生命周期source/class/runtime 默认class

  Documented 默认javadoc不包括注解，加上这个注解就会识别
  Inherited 由此注解修饰的注解类 能被子类自动继承

  Target TYPE,FIELD,METHOD,PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE,ANNOTATION_TYPE..

* 枚举类：枚举值必须在类最前面 用,隔开，最后加上;

  ```
  public enum EnumTest {
      A(1,"a"),
      B(2,"b");
  
      //属性扩展
      private Integer age;
      private String name;
  
  
      EnumTest(Integer age, String name) {
          this.age = age;
          this.name = name;
      }
  }
  
      System.out.println(EnumTest.A.name()); //枚举值的字面量 A
          System.out.println(EnumTest.B.ordinal()); //枚举值的序号 1
          System.out.println(EnumTest.A.toString()); //return name A
  ```

## IO流

* 转化流：将字节流和字符流进行转化 如 InputStreamReader 字节输入流->字符输入流 

  InputStreamReader 需保证两个流编码一致，否则会乱码；不传默认是程序的编码方式

  OutputStreamReader  可以指定任何需要的编码

* Scanner 扫描器 可以连接任何字节输入管道

* DataInputStream/DataOutputStream 应用于基本数据类型和String

## 反射

```
用junit 最好不用main测试
Class<NetworkTest> cls= NetworkTest.class;
        Constructor<NetworkTest> constructor= null;
        try {
            constructor = cls.getDeclaredConstructor(String.class,String.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        NetworkTest net= null;
        try {
            net = constructor.newInstance("a","b");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(net);
```

## 多线程

参照多线程.md

# UML

* 类图

  ![1](C:\Users\Administrator\Desktop\复习\素材\pic\UML\1.jpg)

* 关系

  ![3](C:\Users\Administrator\Desktop\复习\素材\pic\UML\3.jpg)

* 关系&多样性

  ![2](C:\Users\Administrator\Desktop\复习\素材\pic\UML\2.jpg)

# Maven

* 远程仓库：所有一切不在本地的仓库，分为中央仓库和私服仓库

* 镜像仓库：如果仓库A可以提供仓库B存储的所有内容，那么可以认为A是B的一个镜像

  本地仓库->[私服仓库]->中央仓库或镜像仓库 

  ```shell
  	<mirror>
  		<id>nexus-aliyun</id>
  		<mirrorOf>central</mirrorOf>
  		<name>Nexus aliyun</name>
  		<url>http://maven.aliyun.com/nexus/content/groups/public</url>
      </mirror>
  ```

  **配置文件优先级：pom.xml> user settings（~/.m2） > global settings(maven安装目录)** 

  	<build>
  		<plugin>
  	          <artifactId>maven-compiler-plugin</artifactId>
  	          <version>3.8.0</version>
  	          <configuration>
  	            <source>maven.compiler.source</source>
  	            <target>maven.compiler.target</target>
  	          </configuration>
  	        </plugin>
  	</build>

* packing

  * POM 逻辑工程（一般不会在里面写业务代码），用在父级工程或聚合工程中，用来做jar包管理
  * Jar 会打包成jar包，供模块间的调用 默认
  * War 会打包成war包 发布在服务器上的工程

* Maven基于POM(Projrct Object Model) 项目对象模型实现的 对象之间有依赖(组合)，继承，聚合

  * 依赖 导jar包 可以自动解决jar包冲突

    * 传递依赖 自定义依赖>传递依赖 

      * 针对同一个起点A（同一个项目/模块）  

        第一原则 最短路径优先  A->B->C->D(1.0) A->E->D(2.0) 则2.0版本的D会被依赖

         在第一原则无法确定时 第二原则 最先声明原则奏效 如 A->B->D(1.0) A->E->D(2.0) 则1.0版本的D会被依赖 

      * 依赖排除 在depedency里加入exclusions配置

    * depedency scope

      * compile(默认) 编译和运行时都生效

      * provided 此jar包需要由jdk，tomcat等容器提供 如 servlet.jar

      * runtime 运行时生效 如jdbc驱动实现jdbc.jar 编译时只需要JDK的jdbc接口

      * system 本地系统路径的jar  需要通过systemPath显式定义文件路径

      * test  编译测试代码和运行测试代码时需要

      * import 只能在depedencyManagement里，表明指定的POM**推荐**使用该依赖

        parent+<relativePath>../mavenDemo/pom.xml</relativePath>

        import是直接插入到子代，不存在传递依赖  无import存在传递依赖

  * 聚合本质也是继承，一个项目有多个模块

* 插件

  * 编译器

    ```
    <build>
    	<pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
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
             </plugins>
          </pluginManagement> 
        </build>
    ```

  * 资源拷贝

    **只有在src/main/resources下的配置文件**才会被打包到traget/classes下

    ```
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.xml</include> //包括子目录
                </includes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.xml</include>  
                </includes>
            </resource>
        </resources>
    </build>
    ```

  * tomcat插件

    ```
    <build>
          <plugins>
            <plugin>
              <groupId>org.apache.tomcat.maven</groupId>
              <artifactId>tomcat7-maven-plugin</artifactId>
              <version>2.2</version>
              <configuration>
                <port>8082</port>
    <!--            application context-->
                <path>/</path>
              </configuration>
            </plugin>
          </plugins>
      </build>
    ```

    tomcat7:run

* maven命令

  * clean 清除编译信息
  * compile 编译
  * package clean+resources(copy)+compile+testresources+testcompile+test+jar
  * install package+install(安装到本地仓库)
  * deploy install +deploy(**远程maven私服仓库** )

* Maven 内置的三套生命周期：
      (1) clean 清理项目
      (2) default 构建、发布项目

  1	validate	
  2	initialize	
  3	generate-sources	
  4	process-sources	
  5	generate-resources	
  6	process-resources	resources:resources
  7	compile	compiler:compile
  8	process-classes	
  9	generate-test-sources	
  10	process-test-sources	
  11	generate-test-resources	
  12	process-test-resources	resources:testResources
  13	test-compile	compiler:testCompile
  14	process-test-classes	
  15	test surefire:test	
  16	prepare-package	
  17	package	
  18	pre-integration-test	
  19	integration-test	
  20	post-integration-test	
  21	verify	
  22	install	install:install
  23	deploy deploy:deploy	deploy:deploy

  ​    (3) site 生成项目站点

  生命周期clean有3个phase:

  1. pre-clean
  2. clean
  3. post-clean

  A Build **Phase** is Made Up of **Plugin Goals**

  mvn 插件：目标 如 mvn clean:clean

  **mvn install会同时执行之前的21个阶段 mvn install:install则不会**

  红色波浪线：先把依赖移除，reimport->依赖加回来，reimport

# 计算机网络

## 五层模型（实际）

1. 物理层

   电脑要联网，当然要把电脑连起来，可以用光缆、电缆、双绞线、无线电波等方式。 

   **物理层中，将数据的0、1转换为电压和脉冲光（电特性）传输给物理的传输介质** 

2. 数据链路层

   假设两台主机A和B，A和B已经有了连接的介质，现在A给B传输数据，这些数据其实就是一堆的0和1，B主机收到这一堆0和1 要怎么解析呢？多少个电信号算一组？每个信号位有何意义？到底是按8位还是按16位解析？如何解析就是数据链路层干的事情 （解析电信号）

   以太网规定，一组电信号构成一个数据包，叫做"帧"（Frame）。每一帧分成两个部分：标头（Head）和数据（Data）。 最大1518字节

   head包含：（固定18个字节）

   - 发送者（源地址，6个字节）   MAC地址（网卡的地址）
   - 接收者（目标地址，6个字节） MAC地址（网卡的地址）
   - 数据类型（6个字节） 

   发送方式为本网络内广播，通过目标地址识别

3. 网络层

   以太网协议，依靠MAC地址发送数据，但是这仅局限于局域网，也就是一个小的网络段 。

   IP地址和子网掩码做AND运算 区分不同的计算机是否属于同一个子网络

   **所以网络层主要就是IP地址管理和路由选择，以IP协议为主，在标头添加Ip地址** 

4. 传输层

   **传输层就是建立"端口到端口"的通信。相比之下，"网络层"的功能是建立"主机到主机"的通信。只要确定主机和端口，我们就能实现程序之间的交流。** 

   传输层的协议主要有UDP和TCP 

5. 应用层

   就是使用不同的协议，如HTTP，FTP等，这些协议规定了具体的数据解析方式 

   ![img](https://img-blog.csdnimg.cn/20190428142430890.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3djYzI3ODU3Mjg1,size_16,color_FFFFFF,t_70) 

![2](C:\Users\Administrator\Desktop\复习\素材\pic\java\2.jpg)



![2](C:\Users\Administrator\Desktop\复习\素材\pic\java\3.jpg)

![2](C:\Users\Administrator\Desktop\复习\素材\pic\java\4.jpg)



![img](https://pic3.zhimg.com/80/v2-0a749b8af6d8facd140874859b681ef6_1440w.jpg) 

* nc ncat - Concatenate(Connect) and redirect sockets

  ncat \[OPTIONS...]\[hostname][port]

  nc www.baidu.com 80

  GET / HTTP/1.0 两个回车

  

  

  netstat -antp 

  -t tcp

* 三次握手

  ![002](C:\Users\Administrator\Desktop\复习\素材\pic\java\002.png)

  这里有一个问题，A与B就A的初始序列号达成了一致，这里是1000。但是B无法知道A是否已经接收到自己的同步信号,如果这个同步信号丢失了，A和B就B的初始序列号将无法达成一致。

  于是TCP的设计者将SYN这个同步标志位SYN设计成占用一个字节的编号(FIN标志位也是)，既
  然是一个字节的数据，按照TCP对有数据的TCP segment必须确认的原则，所以在这里A必须给B一个确认，以确认A已经接收到B的同步信号。

  如果A发给B的确认丢了，该如何？
  A会超时重传这个ACK吗？不会！TCP不会为没有数据的ACK超时重传。
  那该如何是好？B如果没有收到A的ACK，会超时重传自己的SYN同步信号，一直到收到A的ACK为止。

  第一个包，即A发给B的SYN 中途被丢，没有到达B

  A会周期性超时重传，直到收到B的确认

  第二个包，即B发给A的SYN +ACK 中途被丢，没有到达A

  B会周期性超时重传，直到收到A的确认

  第三个包，即A发给B的ACK 中途被丢，没有到达B

  A不会超时重传，B会周期性超时重传，直到收到A的确认

  a.假定此时双方都没有数据发送，B会周期性超时重传，直到收到A的确认，收到之后B的TCP连接也为Established状态，双向可以发包。

  b.假定此时A有数据发送，B收到A的Data + ACK，自然会切换为established状态，并接受A的Data。

  c. 假定B有数据发送，数据发送不了，会一直周期性超时重传SYN + ACK，直到收到A的确认才可以发送数据。

  

  

  

  


  为了防止**已失效的连接请求报文**段突然又传送到了服务端，因而产生错误 

  ![5](C:\Users\Administrator\Desktop\复习\素材\pic\java\5.jpg)

  ![6](C:\Users\Administrator\Desktop\复习\素材\pic\java\6.jpg)

* 四次分手

  ![7](C:\Users\Administrator\Desktop\复习\素材\pic\java\7.jpg)

  ![8](C:\Users\Administrator\Desktop\复习\素材\pic\java\8.jpg)

  tcpdump - dump traffic on a network

  tcpdump [options]

  [ -i interface ]

   -nn    Don't convert protocol and port numbers etc. to names either.‘

  tcpdump  -nn -i ens33  port 80

  ```shell
  三次握手：
  14:33:06.909173 IP 192.168.162.100.43910 > 220.181.38.150.80: Flags [F.], seq 444915096, ack 878026631, win 49640, length 0
  14:33:06.909314 IP 220.181.38.150.80 > 192.168.162.100.43910: Flags [.], ack 1, win 64239, length 0
  14:34:21.868311 IP 192.168.162.100.58378 > 220.181.38.149.80: Flags [S], seq 1444554020, win 29200, options [mss 1460,sackOK,TS v
  al 5375352 ecr 0,nop,wscale 7], length 0
  
  四次挥手：
  14:34:21.931991 IP 192.168.162.100.58378 > 220.181.38.149.80: Flags [F.], seq 78, ack 2782, win 33580, length 0
  14:34:21.932098 IP 220.181.38.149.80 > 192.168.162.100.58378: Flags [.], ack 79, win 64239, length 0
  14:34:21.962534 IP 220.181.38.149.80 > 192.168.162.100.58378: Flags [FP.], seq 2782, ack 79, win 64239, length 0
  14:34:21.962562 IP 192.168.162.100.58378 > 220.181.38.149.80: Flags [.], ack 2783, win 33580, length 0
  
  ```

  

  


# JVM

![1](C:\Users\Administrator\Desktop\复习\素材\pic\jvm\1.jpg)



```java
public class Math {
    public static final int intData=666;
    public static User user=new User();

    public static void main(String[] args) {
        Math math=new Math();
        math.compute();
    }

    private int compute() {
        int a=1;
        int b=2;
        int c=(a+b)*10;
        return c;
    }
}

```

反编译的指令

```JAVA
Compiled from "Math.java"
public class Math {
  public static final int intData;

  public static User user;

  public Math();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: new           #2                  // class Math
       3: dup
       4: invokespecial #3                  // Method "<init>":()V
       7: astore_1
       8: aload_1
       9: invokespecial #4                  // Method compute:()I
      12: pop
      13: return

  static {};
    Code:
       0: new           #5                  // class User
       3: dup
       4: invokespecial #6                  // Method User."<init>":()V
       7: putstatic     #7                  // Field user:LUser;
      10: return
      
    public int compute();
    Code:
       0: iconst_1  //O 代表this 将int类型常量1压入栈
       1: istore_1
       2: iconst_2
       3: istore_2
       4: iload_1
       5: iload_2
       6: iadd
       7: bipush        10
       9: imul
      10: istore_3
      11: iload_3
      12: ireturn
  
}

```

![3](C:\Users\Administrator\Desktop\复习\素材\pic\jvm\3.jpg)

1. String st1 = new String(“abc”); 创建 了几个对象

   1或2个 当JVM遇到上述代码时，会先检索常量池中是否存在“abc”，如果不存在“abc”这个字符串，则会先在常量池中创建这个一个字符串。然后再执行new操作，会在堆内存中创建一个存储“abc”的String对象，对象的引用赋值给str2。此过程创建了2个对象。若常量池里存在,则1个
   
2. String str = "abc" + "def";   1个 JVM会优化
3. String str = "abc" + new String("def"); 5个
	4个字符串对象:常量池中分别有“abc”和“def”，堆中对象new String(“def”)和“abcdef”+1个StringBuiler对象
	
	
	由于JVM编译的时候同样会优化,会创建一个StringBuilder来进行字符串的拼接，实际效果类似：
	String s = new String("def");
	new StringBuilder().append("abc").append(s).toString();
   
    StringBuilder的toString()方法:
    @Override
public String toString() {
    // Create a copy, don't share the array
    return new String(value, 0, count);
}
会在堆上创建对象,而不是常量池

   

   - 常量应该在常量池中创建 

   - ​    public String(String original) {

       	 this.value = original.value;

     ​	...

     }

   

2. 一个方法对应一块栈帧内存

3. ![3](C:\Users\Administrator\Desktop\复习\素材\pic\jvm\3.png)区域

动态链接：  在Java源文件被编译到字节码文件时，所有的变量和方法引用都作为符号引用（Symbilic Reference）保存在class文件的常量池里。 **动态链接的作用就是为了将这些符号引用转换为调用方法的直接引用** 

3. GC ROOTS: 线程（虚拟机）栈的本地变量，静态变量，本地方法栈的变量等等
4. 对象头

![2](C:\Users\Administrator\Desktop\复习\素材\pic\jvm\2.jpg)

5. jvisualvm

   jvisualvm 工具->插件->安装 visual gc

   ![4](C:\Users\Administrator\Desktop\复习\素材\pic\jvm\4.jpg)

6. Athas

   https://arthas.aliyun.com/doc/ `Arthas` 是Alibaba开源的Java诊断工具，深受开发者喜爱。 

   1. 启动需要测试的jar包

      curl -O https://arthas.aliyun.com/math-game.jar
      java -jar math-game.jar

      打自己的jar包：

      编辑META-INF/MANIFEST.MF，给jar包指定程序入口main函数 Main-Class: xxx.Hello 

       jar cvfm classes.jar mymanifest -C foo/ .

      -c 创建新档案

       -v  在标准输出中生成详细输出

      -f  指定档案文件名

      -C  更改为指定的目录并包含以下文件

       -m  包含指定清单文件中的清单信息

      

      Error: Could not find or load main class 注意包路径

      

   1. 启动arthas

      curl -O https://arthas.aliyun.com/arthas-boot.jar
      java -jar arthas-boot.jar -v

      - 如果attach不上目标进程，可以查看`~/logs/arthas/` 目录下的日志。

      - java -jar arthas-boot.jar -h 打印更多参数信息 

   2. 选择java进程  第n个 就输入n

   3. 查看  dashboard   dashboard 回车

   4. thread n会打印线程ID n的栈

   5. 反编译 jad demo.MathGame 

   6. watch查看返回值   watch class（全限定） function returnObj 

   7. exit | stop

   

7. minor gc full gc

   full gc触发的情况：

   #### 1、System.gc()方法的调用

   　　此方法的调用是建议JVM进行Full GC,虽然只是建议而非一定,但很多情况下它会触发 Full GC,从而增加Full GC的频率,也即增加了间歇性停顿的次数。强烈影响系建议能不使用此方法就别使用，让虚拟机自己去管理它的内存，可通过通过-XX:+ DisableExplicitGC来禁止RMI调用System.gc。

   #### 2、老年代代空间不足

   老年代空间只有在新生代对象转入及创建为大对象、大数组时才会出现不足的现象，当执行Full GC后空间仍然不足，则抛出如下错误：
   java.lang.OutOfMemoryError: Java heap space 
   为避免以上两种状况引起的Full GC，调优时应尽量做到让对象在Minor GC阶段被回收、让对象在新生代多存活一段时间及不要创建过大的对象及数组。

   #### 3、永生区空间不足

   JVM规范中运行时数据区域中的方法区，在HotSpot虚拟机中又被习惯称为永生代或者永生区，Permanet Generation中存放的为一些class的信息、常量、静态变量等数据，当系统中要加载的类、反射的类和调用的方法较多时，Permanet Generation可能会被占满，在未配置为采用CMS GC的情况下也会执行Full GC。如果经过Full GC仍然回收不了，那么JVM会抛出如下错误信息：
   java.lang.OutOfMemoryError: PermGen space 
   为避免Perm Gen占满造成Full GC现象，可采用的方法为增大Perm Gen空间或转为使用CMS GC。

   #### 4、CMS GC时出现promotion failed和concurrent mode failure

   对于采用CMS进行老年代GC的程序而言，尤其要注意GC日志中是否有promotion failed和concurrent mode failure两种状况，当这两种状况出现时可能会触发Full GC。

   promotion failed是在进行Minor GC时，survivor space放不下、对象只能放入老年代，而此时老年代也放不下造成的；concurrent mode failure是在执行CMS GC的过程中同时有对象要放入老年代，而此时老年代空间不足造成的（有时候“空间不足”是CMS GC时当前的浮动垃圾过多导致暂时性的空间不足触发Full GC）。

   对措施为：增大survivor space、老年代空间或调低触发并发GC的比率，但在JDK 5.0+、6.0+的版本中有可能会由于JDK的bug29导致CMS在remark完毕

   后很久才触发sweeping动作。对于这种状况，可通过设置-XX: CMSMaxAbortablePrecleanTime=5（单位为ms）来避免。

   详细见《[垃圾收集器之：CMS收集器](http://www.cnblogs.com/duanxz/p/6098922.html)》中的“CMS 晋升失败（promotion failed）和并发模式失效（concurrent mode failure）产生的原因以及解决的方案”中介绍。

   #### 5、统计得到的Minor GC晋升到老年代的平均大小大于老年代的剩余空间

   这是一个较为复杂的触发情况，Hotspot为了避免由于新生代对象晋升到旧生代导致旧生代空间不足的现象，在进行Minor GC时，做了一个判断，如果之前统计所得到的Minor GC晋升到旧生代的平均大小大于旧生代的剩余空间，那么就直接触发Full GC。

   例如程序第一次触发Minor GC后，有6MB的对象晋升到旧生代，那么当下一次Minor GC发生时，首先检查旧生代的剩余空间是否大于6MB，如果小于6MB，则执行Full GC。

   当新生代采用PS GC时，方式稍有不同，PS GC是在Minor GC后也会检查，例如上面的例子中第一次Minor GC后，PS GC会检查此时旧生代的剩余空间是否大于6MB，如小于，则触发对旧生代的回收。

   除了以上4种状况外，对于使用RMI来进行RPC或管理的Sun JDK应用而言，默认情况下会一小时执行一次Full GC。可通过在启动时通过- java -Dsun.rmi.dgc.client.gcInterval=3600000来设置Full GC执行的间隔时间或通过-XX:+ DisableExplicitGC来禁止RMI调用System.gc。

   #### 6、堆中分配很大的对象

   所谓大对象，是指需要大量连续内存空间的java对象，例如很长的数组，此种对象会直接进入老年代，而老年代虽然有很大的剩余空间，但是无法找到足够大的连续空间来分配给当前对象，此种情况就会触发JVM进行Full GC。

   为了解决这个问题，CMS垃圾收集器提供了一个可配置的参数，即-XX:+UseCMSCompactAtFullCollection开关参数，用于在“享受”完Full GC服务之后额外免费赠送一个碎片整理的过程，内存整理的过程无法并发的，空间碎片问题没有了，但提顿时间不得不变长了，JVM设计者们还提供了另外一个参数 -XX:CMSFullGCsBeforeCompaction,这个参数用于设置在执行多少次不压缩的Full GC后,跟着来一次带压缩的。

   **除了以上6种状况外，对于使用RMI来进行RPC或管理的Sun JDK应用而言，默认情况下会一小时执行一次Full GC。可通过在启动时通过- java-Dsun.rmi.dgc.client.gcInterval=3600000来设置Full GC执行的间隔时间或通过-XX:+ DisableExplicitGC来禁止RMI调用System.gc。**

   minor gc full gc 都可能产生stw  stop the world：停止所有用户线程 专心gc

   * 为什么需要stw：

     ​	做Minor GC和Full GC的时候，需要去标记或者计算对象是否还在被引用，如果在标记、计算、复制的过程中如果还有新对象产生或者老的对象的偏移，难以处理

   * 

8. 新生代->老年代

   (1)、Eden区满时，进行Minor GC，当Eden和一个Survivor区中依然存活的对象无法放入到Survivor中，则通过分配担保机制提前转移到老年代中。 

   (2)、若对象体积太大, 新生代无法容纳这个对象，-XX:PretenureSizeThreshold即对象的大小大于此值, 就会绕过新生代, 直接在老年代分配, 此参数只对Serial及ParNew两款收集器有效。

   (3)、长期存活的对象将进入老年代。

           虚拟机对每个对象定义了一个对象年龄（Age）计数器。当年龄增加到一定的临界值时，就会晋升到老年代中，该临界值由参数：-XX:MaxTenuringThreshold来设置。
       
           如果对象在Eden出生并在第一次发生MinorGC时仍然存活，并且能够被Survivor中所容纳的话，则该对象会被移动到Survivor中，并且设Age=1；以后每经历一次Minor GC，该对象还存活的话Age=Age+1。

   (4)、动态对象年龄判定。

           虚拟机并不总是要求对象的年龄必须达到MaxTenuringThreshold才能晋升到老年代，如果在Survivor区中相同年龄（设年龄为age）的对象的所有大小之和超过Survivor空间的一半，年龄大于或等于该年龄（age）的对象就可以直接进入老年代，无需等到MaxTenuringThreshold中要求的年龄。

### 垃圾回收器

引用计数法：对象被引用时，其引用计数器+1,0被回收 缺：无法识别对象间互相引用（ detect the unreachable objects ）

垃圾收集算法：

Any garbage collection algorithm must perform 2 basic operations. One, it should be able to detect all the unreachable objects and secondly, it must reclaim(回收) the heap space used by the garbage objects and make the space available again to the program. 

* 标记-清除（mark-sweep）：标记阶段+清除阶段 内存碎片 

  * **Mark Phase** 

    When an object is created, its mark bit is set to 0(false). In the Mark phase, we set the marked bit for all the reachable objects to 1(true)   初始化是假 标记阶段root可达的对象都是真

  * **Sweep Phase** 

    it clears the heap memory for all the unreachable objects.  reachable objects  is set to false  清除不可达对象的空间，可达对象标记为false

* 复制算法 

  复制算法就是将内存空间二等分, 每次只使用其中一块. 当执行GC时, 将A部分的所有活动对象集体移到B中, 就可以将A全部释放 mark-copy-sweep

* 标记-整理（mark-compact）：mark-compact-sweep


分代收集：
新生代：新创建的对象先放入新生代（复制算法）

​	新生代对象大多朝生夕灭，存活时间短，占内存小，minor gc非常频繁，所以用copy算法以较小的空间换时间是合理的。 

老年代：经过几次gc仍存活，放入老年代，常驻内存（标记-清除或标记-整理）

​	老年代对象大而且生存时间长

### parent delegation Model

Parent delegation model is a mechanism(机制) for Java to load classes ()

![10](C:\Users\Administrator\Desktop\复习\素材\pic\jvm\10.jpg)

* Bootstrap ClassLoader：Responsible for loading virtual machine-recognized (name-by-name) **class libraries into JVM** on paths specified by parameters in the**% JAVA_HOME%/lib directory or - Xboot classpath** 

* Extension ClassLoader: responsible for loading all class libraries in**% JAVA_HOME%/lib/ext**

* Application ClassLoader: Responsible for class libraries **in ClassPath**

  1.  the loading request will eventually be delegated to Bootstrap ClassLoader ，ensures that all loaders load the same class of Object classes 
  2. Every class loader will try to load from top to bottom 

  

   	1.First, check if the class has already been loaded

   2. if not，get parent classLoader 

  	3. repeat step 2 until parent is null,then return bootstrap classLoader 

  	4. from top to bottom find class wheter is in scope

      ![4](C:\Users\Administrator\Desktop\复习\素材\pic\jvm\4.png)

      ```
      protected Class<?> loadClass(String name, boolean resolve)
          throws ClassNotFoundException
      {
          synchronized (getClassLoadingLock(name)) {
              // First, check if the class has already been loaded
              Class<?> c = findLoadedClass(name);
              if (c == null) {
                  long t0 = System.nanoTime();
                  try {
                      if (parent != null) {
                          c = parent.loadClass(name, false);
                      } else {
                          c = findBootstrapClassOrNull(name);
                      }
                  } catch (ClassNotFoundException e) {
                      // ClassNotFoundException thrown if class not found
                      // from the non-null parent class loader
                  }
      
                  if (c == null) {
                      // If still not found, then invoke findClass in order
                      // to find the class.
                      long t1 = System.nanoTime();
                      c = findClass(name);
      
                      // this is the defining class loader; record the stats
                      sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                      sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                      sun.misc.PerfCounter.getFindClasses().increment();
                  }
              }
              if (resolve) {
                  resolveClass(c);
              }
              return c;
      ```

  * System.out.println(System.getProperty("java.class.path"));  

     lib、target/classes、.m2\repository

  * 模块class libray加载的顺序 jdk->modlue souce->depdency

    ![6](C:\Users\Administrator\Desktop\复习\素材\pic\git\6.jpg)

  * ClassNotFoundException Vs NoClassDefFoundError 

    1.  前者 当动态加载Class的时候找不到类会抛出该异常  

       后者 程序**在编译时可以找到所依赖的类，但是在运行时找不到指定的类文件**，运行过程中Class找不到导致抛出该错误 

    2. 前者 一般在执行Class.forName()、ClassLoader.loadClass()或ClassLoader.findSystemClass()的时候抛出 

       后者J VM或者ClassLoader实例尝试加载类的时候，找不到类的定义而发生，通常在**import和new一个类的时候触发** 

    3. Exception vs Error

       原因： 要加载的类不存在；类名书写错误  vs jar包缺失(classpath)；**调用初始化失败的类** 

# 设计模式

* 概念 

  1.设计模式是前人对代码开发经验的总结,是解决特定问题的一系列套路.不是语法规定.

  2.gof  gang of four 一般有23种 

  3.本质是面向对象设计原则的实际运用

  4.分类:创建者模式  5/结构型 7/行为型 11 

* oop七大原则

  **开闭原则:**对扩展开放,对修改关闭

  **里氏替换原则**:继承必须保证超类拥有的性质在子类种依然成立

  **依赖倒置原则**: 要面向接口编程,而不是面向实现编程

  **单一职责原则**:控制类的粒度 低耦合,高内聚

  **接口隔离原则**:控制接口的粒度,通过组合的方式或则需要的

  迪米特法则:只与你的直接朋友交谈,不跟'陌生人'讲话

  合成复用原则:优先考虑组合/聚合 其次才是继承

* 单例

  构造方法私有化

  * 饿汉模式

    JVM保证每个class load到内存只有一次 static修饰的在类加载的时候马上初始化  JVM安全的

  * lazy loading

    多线程访问不能保证获取的是同一个对象，从而破坏单例约束

    * synchronized 效率下降

      每个类实例对应一把锁 ，synchronized 方法都必须获得调用该方法的类实例的锁方能执行 （方法锁也是对象锁 ）

    * DCL double check lock 存在**对象半初始化**问题（概率小） volatitle

    * 静态内部类 JVM加载内部类的时机其实就跟**加载任何类**（包括顶层类与内部类）**的时机一样**：要表现为是在**第一次主动使用时加载**。主动使用包括new、getstatic、putstatic、invokestatic，以及Class.forName()作为一种特例 

      **和外部类的加载没有必然的关系**

    * **枚举单例** 解决线程同步，防止序列化 （枚举类没有构造方法）

  * 

* 策略模式

   策略模式用一个成语就可以概括 —— 殊途同归。当我们做同一件事有多种方法时，就可以将每种方法封装起来，在不同的场景选择不同的策略，调用不同的方法。

  > 策略模式（Strategy Pattern）：定义了一系列算法，并将每一个算法封装起来，而且使它们还可以相互替换。策略模式让算法独立于使用它的客户而独立变化。

  - 使用策略模式时，程序只需选择一种策略就可以完成某件事。也就是说每个策略类都是完整的，都能独立完成这件事情，如上文所言，强调的是`殊途同归`。
  - 使用状态模式时，程序需要在不同的状态下不断切换才能完成某件事，每个状态类只能完成这件事的一部分，需要所有的状态类组合起来才能完整的完成这件事，强调的是`随势而动`。

  ![1](C:\Users\Administrator\Desktop\复习\素材\pic\stragegy\1.jpg)

* 工厂模式

  **任何可以产生对象**的方法或类 ，都可以叫工厂 单例也是一种工厂

  简单工厂（产品好扩展）：

  * implements Movable 在里面创建各种交通工具
  * xxxFatory.create()  定制生产过程

  

  抽象工厂：

  产品族：一系列不同产品的组合 比如产品族A：Food1 Vechiel1 Weapon1 产品族B：Food2 Vechiel2 Weapon2（真实存在abstract class 特别抽象的 interface）

  ![2](C:\Users\Administrator\Desktop\复习\素材\pic\stragegy\2.jpg)

  问题：产品扩展困难

* 装饰模式

  装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构 

  使用继承方式实现，随着扩展功能的增多，子类数量会很膨胀 

  ![3](C:\Users\Administrator\Desktop\复习\素材\pic\stragegy\3.jpg)

  调用 Shape shape=new RedShapeDecorator(new Circle());  shape.draw();

* Builder 构造复杂对象

  分离复杂对象的构建和表示

  比如一个对象有很多属性

  ![4](C:\Users\Administrator\Desktop\复习\素材\pic\stragegy\4.jpg)

* 责任链模式 chain of responsibility

  设计模式的关键就在于**封装变化**

  ```
  public class CorDemo {
      public static void main(String[] args) {
  
          Article article = new Article("大家好:)<script>我们都是996</script>");
          FilterChain fc1 = new FilterChain();
          fc1.add(new Filter1())
             .add(new Filter2());
          FilterChain fc2 = new FilterChain();
          fc2.add(new Filter3())
             .add(new Filter4());
          fc1.add(fc2);
          fc1.doFilter(article);
          System.out.println(article);
      }
  }
  
  class Article{
      String name;
      String Msg;
  
      public String getMsg() {
          return Msg;
      }
  
      public void setMsg(String msg) {
          Msg = msg;
      }
  
      public Article(String msg) {
          Msg = msg;
      }
  
      @Override
      public String toString() {
          return "Article{" +
                  "Msg='" + Msg + '\'' +
                  '}';
      }
  }
  interface Filter<T>{
      void doFilter(T obj);
  }
  
  class Filter1 implements Filter<Article>{
  
      @Override
      public void doFilter(Article article) {
         article.setMsg(article.getMsg().replaceAll("<","["));
      }
  }
  
  class Filter2 implements Filter<Article>{
  
      @Override
      public void doFilter(Article article) {
          article.setMsg(article.getMsg().replaceAll(">","]"));
      }
  }
  
  class Filter3 implements Filter<Article>{
  
      @Override
      public void doFilter(Article article) {
          article.setMsg(article.getMsg().replaceAll("996","955"));
      }
  }
  
  class Filter4 implements Filter<Article>{
  
      @Override
      public void doFilter(Article article) {
          article.setMsg(article.getMsg().replaceAll(":[)]","^V^"));
      }
  }
  
  //通过实现Filter来轻松实现多个链条的整合
  class FilterChain implements Filter<Article>{
      List<Filter<Article>> filters=new ArrayList<Filter<Article>>();
  
      public FilterChain add(Filter<Article> filter){
          filters.add(filter);
          return this;
      }
      @Override
      public void doFilter(Article article) {
          for (Filter<Article> filter : filters) {
            filter.doFilter(article);
          }
      }
  }
  ```

  问题一：怎么由某个filter决定是不是继续往下执行

  bool doFIlter(..)

  if(!doFilter(..)) return false;

  return true;

 

​     问题二： 模拟javax.servlet.Filter

​     假设由两个过滤器 A，B 每个过滤器都能对request和response做过滤，且保证request顺序A,B response顺序		B,A

```
public class SevletFilterDemo {
    public static void main(String[] args) {
        Request request = new Request();
        request.str="req_1";
        Response response = new Response();
        response.str="resp_1";
        FilterChain chain = new FilterChain();
        chain.add(new FilterA())
             .add(new FilterB());
        chain.doFilter(request,response);
        System.out.println(request.str); //B_req_2
        System.out.println(response.str); //A_resp_2
    }
}

class Request{
    String str;
}
class Response{
    String str;
}

interface Filter{
    //处理当前filter的req,再通过chain调用下一个Filter.doFilter
    // 以此往复 直到最后一个filter 执行response
    boolean doFilter(Request request,Response response,FilterChain chain);
}

class FilterA implements Filter{

    @Override
    public boolean doFilter(Request request,Response response,FilterChain chain) {
        request.str="A_req_2";
        chain.doFilter(request,response);
        response.str="A_resp_2";
      return true;
    }
}

class FilterB implements Filter{

    @Override
    public boolean doFilter(Request request,Response response,FilterChain chain) {
        request.str="B_req_2";
        chain.doFilter(request,response);
        response.str="B_resp_2";
        return true;
    }
}


//通过实现Filter来轻松实现多个链条的整合
class FilterChain{
   List<Filter> filters= new ArrayList<Filter>();
   int index=0;

    public FilterChain add(Filter filter){
        filters.add(filter);
        return this;
    }

    public boolean doFilter(Request request,Response response) {
        if (index<filters.size()) {
            return filters.get(index++).doFilter(request, response, this);
        }
        return false;
    }
}
```

# 排序算法（更多见数据结构.md）

* 判断算法的优劣

   Big O

  * 时间 nanoTime high-resolution time source, in nanoseconds.

    不考虑必须要做的 赋初值/程序初始化等

  * 空间

* 排序

  冒泡 稳定 o(n^2) 0(1)
  选择 不稳定 o(n^2) 0(1)
  插入 稳定 o(n^2) 0(1)
  希尔 不稳定 o(n^1.3) 0(1)

  归并 稳定 o(nlogn) o(n)

  快排 不稳定 o(nlogn) o(logn)

  计数 稳定 o(n+k) o(n+k)

  桶 不定 o(n+k) o(n+k)

  基数 稳定  o(k*n) k是常数 o(n+k)

  ![5](C:\Users\Administrator\Desktop\复习\素材\pic\stragegy\5.jpg)

  * 选择排序(selection)

    每次选取剩余未排序元素中最小（最大）的一个，与当前索引元素交换

    **数组不稳定 如{5,5,2}** 链表稳定 但一般提到排序算法默认数组实现

    改进：每次遍历同时获取最小和最大的元素下标

    ```
    for (int i=0;i< arr.length/2;i++){
        int min=i;
        int max=i;
        for (int j = i+1; j < arr.length-i; j++) {
            if(arr[j]<arr[min]) min=j;
            else if(arr[j]>arr[max]) max=j;
        }
        if(min!=i){
            swap(arr,i,min);
        }
        if(max!=i){
            swap(arr,arr.length-i-1,max);
        }
    }
    ```

  * 冒泡算法（Bubble）

    这个算法的名字由来是因为越小的元素会经由交换慢慢"浮"到数列的顶端 

    重复访问要比较的数列,每次比较两个元素,直到排序结束 **每次都会有当前一轮需要排序的数列中最大的沉到底部**

  * 插入算法（insertion）

    对基本有序的数组最好用

    第n轮排序（从第二个元素开始排起） 把前n个元素当成一个有序序列，把第n+1个元素插入其中形成新的有序序列

    ```
    //第n轮排序（从第二个元素开始排起） 把前n个元素当成一个有序序列，把第n+1个元素插入其中形成新的有序序列
    for (int i = 1; i < arr.length; i++) {
        int idx=i;
        for(int j=0;j<i;j++){
            if (arr[i]<arr[j]){
                idx=j;
                break;
            }
        }
    
        if(idx!=i){
            moveArray(arr,i,idx);
        }
    }
    
     int temp=arr[newIdx];
     		/*        for(int i=newIdx-1;i>=moveIdx;i--){
                arr[i+1]=arr[i];
            }*/
            int[] moves = Arrays.copyOfRange(arr, moveIdx, newIdx);
            System.arraycopy(moves,0,arr,moveIdx+1,moves.length);
            arr[moveIdx]=temp;
    ```

    或者

    ```
    int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);
    
    // 从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
    for (int i = 1; i < arr.length; i++) {
    
        // 记录要插入的数据
        int tmp = arr[i];
    
        // 从已经排序的序列最右边的开始比较，找到比其小的数
        int j = i;
        while (j > 0 && tmp < arr[j - 1]) {
            arr[j] = arr[j - 1];
            j--;
        }
    
        // 存在比其小的数，插入
        if (j != i) {
            arr[j] = tmp;
        }
    
    }
    return arr;
    ```

  * 希尔排序

    希尔排序，也称递减增量排序算法，是插入排序的一种更高效的改进版本 

    **先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序 再对全体记录进行依次直接插入排序** 

    间隔（gap）大移动次数小 间隔小移动距离少

    **最后的gap一定是1，由此保证排序结果的有序性**

    选择：

    * Knuth序列

      ​	h=1; h=3*h+1;   1 4 13....

      ​	选择h=ceil((arr.length-1)/3)  比如10 选择3 1

    

  * 归并排序 JAVA内部对象排序

    递归地分别对待排序序列左右部分做排序。分为分解（直到left>=right）和合并两部分操作。

    ```java
    merge(arr,0,arr.length-1);
    
    	static void merge(int[] arr,int left,int right){
    	    if(left==right) return;
    	    int mid=left+((right-left)>>1);
    	    merge(arr,left,mid);
    	    merge(arr,mid+1,right);
    	    mergeSort(arr,left,mid,right);
    	}
    
    static void  mergeSort(int[] arr,int left,int mid,int right){
    	  int[] arr2=new int[right-left+1];
    	  int i=left,j=mid+1,k=0;
    	  while(i<=mid&&j<=right){
    	      arr2[k++]=arr[i]<arr[j]?arr[i++]:arr[j++];
    	  }
    	  while(i<=mid) arr2[k++]=arr[i++];
    	  while(j<=right) arr2[k++]=arr[j++];
    	  for(int index=0;index<arr2.length;index++){
    	      arr[index+left]=arr2[index];
    	  }
    	}
    ```

    

  * 快速排序

    再待排序数列中，首先找到一个基准数字（一般选取第一个），小于基准数的元素移动到待排序的数列的左边，大于基准数的元素移动到待排序的数列的右边。 然后继续对每个分区执行上述操作，直到各个分区只有一个数字为止。

    ```java
    quickSort(arr,0,arr.length-1);
    
    static void quickSort(int[] arr,int left,int right){
    	    if(left>=right) return;
    	    int key=arr[left];
    	    int i=left,j=right;
    	    
    	    while(i<j){
        	    while(i<j&&arr[j]>=key) j--;
        	    if(i<j){
        	        arr[i]=arr[j];
        	        i++;
        	    }
        	    while(i<j&&arr[i]<=key) i++;
        	    if(i<j){
        	        arr[j]=arr[i];
        	        j--;
        	    }
    	    }
    	    arr[i]=key;
    	    quickSort(arr,left,i-1);
    	    quickSort(arr,i+1,right);
    
    	}
    ```

    改进算法 双轴快排 也就是Array.sort所使用的排序算法

  

  * 计数排序
    * 牺牲内存空间获取较低的时间复杂度
    * 不基于比较

  ```java
  countSort(arr);
  
  	static void countSort(int[] arr){
  	   // 获取数组极值
  	   int max=arr[0],min=arr[0];
  	   for(int i=1;i<arr.length;i++){
  	       if(max<arr[i]) max=arr[i];
  	       if(min>arr[i]) min=arr[i];
  	   }
  	   //将数组元素分别放入桶中
  	   int[] bucket=new int[max-min+1];
  	   for(int i=0;i<arr.length;i++){
  	       bucket[arr[i]-min]+=1;
  	   }
  	   //将桶从小到大依次取出元素
  	   int index=0;
  	    for(int i=0;i<bucket.length;i++){
  	        for(int j=0;j<bucket[i];j++){
  	            arr[index++]=i+min;
  	        }
  	    }
  	}
  ```

  * 桶排序

    将数组分到有限数量的桶里。每个桶再个别排序，最后依次把各个桶中的记录列出来即得到有序序列。使用的不多。

    基数排序(多关键字)

    原理是将整数按位数切割成不同的数字，然后按每个位数分别比较。基数排序的方式可以采用LSD（Least significant digital）或MSD（Most significant digital），LSD的排序方式由键值的最右边开始，而MSD则相反，由键值的最左边开始。

    * **MSD**：先从高位开始进行排序，在每个关键字上，可采用计数排序
    * **LSD**：先从低位开始进行排序，在每个关键字上，可采用桶排序

    ① 将所有待比较数值（正整数）统一为同样的数位长度，数位较短的数前面补零。
    ② 从最低位开始，依次进行一次排序。
    ③ 这样从最低位排序一直到最高位排序完成以后, 数列就变成一个有序序列。

    ```
    static void radixSort(int[] arr){
        //根据每位数字（关键字）做一个桶排序（假设都是三位数字）
        for (int i=0;i<3 ;i++ ){
            List[] buckets=new List[10];
            for (int j=0;j<arr.length ;j++ ){
                final int curNum=arr[j];
                int key=getNum(curNum,i);
                if(null==buckets[key]){
                     buckets[key]=new LinkedList<Integer>(){{
                        add(curNum);
                    }};
                }else{
                   buckets[key].add(curNum); 
                }
            }
            int index=0;
            for (int k=0;k<10 ; k++){
                if(null!=buckets[k]){
                    List<Integer> bucket=(List<Integer>)buckets[k];
                    for(Integer element:bucket){
                        arr[index++]=element;
                    }
                }
            } 
        } 
      
    }
    
    	static int getNum(int curNum,int round){
    	    int n=0;
    	    while(round>n){
    	        curNum/=10;
    	        round--;
    	    }
    	    return curNum%10;
    	}
    ```

  * 堆排序

    * 完全二叉树
    * 每个节点都>=子节点值 最大堆；反之，最小堆。
    * 存储使用数组 下标i的节点父节点是（i-1）/2 左右子节点是2i+1,2i+2;

  

# JDK8新特性

* 函数式编程 lambda  默认方法/静态方法  方法引用
  问题: lambda或匿名函数为什么要求外部变量为final
  因为在java设计之初为了保护数据的一致性而规定的,对引用变量来说是引用地址的一致性，对基本类型来说就是值的一致性。
  1. 在编译期以强制手段确保用户不会在 lambda 表达式中做修改原变量值的操作
  2.  lambda 表达式的支持是拥抱函数式编程，而函数式编程本身不应为函数引入状态的
  解决:可以通过定义一个相同类型的变量b，然后将该外部变量赋值给b，匿名内部类引用b就行了
  * 方法引用 
    * 静态方法 Class::staticMethodName
    * 特定对象的实例方法 obj::instanceMethodName
    * 任意对象的实例方法 Class::instanceMethodName
    * 构造函数 Class::new
* stream 增强类型推断
* 修改HashMap和ConcurrentHashMap实现 
  * 链表 ->链表+红黑树
  * Entry[] -> Node[] + TreeNode[]
* 修改JVM内存模型，使用metaSpace代替永久代  新增了JVM工具



* JDK8仍然是最受欢迎的版本
* JDK1.9之后的版本 每6个月发布一个版本（LTS long term support 长期支持版本 每3年发布）

# Stream

## stream特点

- No storage. **A stream is not a data structure that stores elements; instead, it conveys（传送） elements from a source such as a data structure**, an array, a generator function, or an I/O channel, through a pipeline of computational operations.
- Functional in nature（功能性）. **An operation on a stream produces a result, but does not modify its source（源不会受到影响）**. For example, filtering a `Stream` obtained from a collection produces a new `Stream` without the filtered elements, rather than removing elements from the source collection.
- Laziness-seeking（懒惰）. **Many stream operations**, such as filtering, mapping, or duplicate removal, **can be implemented lazily, exposing opportunities for optimization**（优化机会）. For example, "find the first `String` with three consecutive vowels" need not examine all the input strings. Stream operations are divided into intermediat（中间）e (`Stream`-producing) operations and terminal (value- or side-effect-producing) operations. Intermediate operations are always lazy.
- Possibly unbounded. While collections have a finite（有限） size, streams need not. Short-circuiting operations such as `limit(n)` or `findFirst()` can allow **computations on infinite streams to complete in finite time**.
- Consumable. **The elements of a stream are only visited（访问） once during the life of a stream.** Like an [`Iterator`](https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html), a new stream must be generated to revisit the same elements of the source

## Streams can be obtained in a number of ways

- From a [`Collection`](https://docs.oracle.com/javase/8/docs/api/java/util/Collection.html) via the `stream()` and `parallelStream()` methods;
- From an array via [`Arrays.stream(Object[])`]
- From static factory methods on the stream classes, such as [`Stream.of(Object[])`], [`IntStream.range(int, int)`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/IntStream.html#range-int-int-) or [`Stream.iterate(Object, UnaryOperator)`](https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#iterate-T-java.util.function.UnaryOperator-)
- he lines of a file can be obtained from [`BufferedReader.lines()`](https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html#lines--) 
- Streams of file paths can be obtained from methods in [`Files`](https://docs.oracle.com/javase/8/docs/api/java/nio/file/Files.html);
- Streams of random numbers can be obtained from [`Random.ints()`](https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#ints--);
- Numerous other stream-bearing methods in the JDK, including [`BitSet.stream()`](https://docs.oracle.com/javase/8/docs/api/java/util/BitSet.html#stream--), [`Pattern.splitAsStream(java.lang.CharSequence)`](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#splitAsStream-java.lang.CharSequence-), and [`JarFile.stream()`](https://docs.oracle.com/javase/8/docs/api/java/util/jar/JarFile.html#stream--).

## Stream operations and pipelines

* Stream operations  = *intermediate* + *terminal* operations 

* stream pipeline = source +  zero or more intermediate operations() such as `Stream.filter` or `Stream.map`)  +a terminal operation (such as `Stream.forEach` or `Stream.reduce` )

* Intermediate operations return a new stream. They are always *lazy*; 

  divied  

  * *stateless* when processing a new element -- each element can be processed independently of operations on other elements 
  * *stateful*   such as `distinct` and `sorted`, may incorporate(包含) state from previously seen elements when processing new elements 。Stateful operations may need to process the entire input before producing a result. 

* Terminal operations, such as `Stream.forEach` or `IntStream.sum`, may traverse(遍历) the stream to produce a result or a side-effect（副作用，往往针对non-thread-safet ） After the terminal operation is performed, the stream pipeline is considered consumed, and can no longer be used; if you need to traverse the same data source again, you must return to the data source to get a new stream. 

## pallel

Processing elements with an explicit `for-`loop is inherently(本质上) serial. Streams facilitate（促进） parallel execution by reframing（重构） the computation as **a pipeline of aggregate operation**s, rather than as imperative（势在必行） operations on each individual element.  

## api

* 中间流（转化流）

  * **Stream<T> filter(Predicate<? super T> predicate)**

  * **<R> Stream<R> map(Function<? super T, ? extends R> mapper)**

    * Returns a stream consisting of the results of applying the given function to the elements of this stream

  * IntStream mapToInt(ToIntFunction<? super T> mapper)

    LongStream mapToLong(ToLongFunction<? super T> mapper);
    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);

    * ToIntFunction<T> 	int applyAsInt(T value);

  * //Returns a stream consisting of the results of replacing each element of this stream with **the contents of a mapped stream** produced by applying the provided mapping function to each element **用每个元素应用了mapper function后返回的Stream内容来代替该元素** 

    **<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);**

    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);

  * //Returns a stream consisting of the distinct elements (according to Object.equals(Object)) of this stream.

     **Stream<T> distinct();**

  * //Returns a stream consisting of the elements of this stream, sorted according to **natural order**/provided Comparator.
    **Stream<T> sorted();**

    **Stream<T> sorted(Comparator<? super T> comparator);**

  * //Returns a stream consisting of the elements of this stream, **additionally performing the provided action on each element** as elements are consumed from the resulting stream.
    **Stream<T> peek(Consumer<? super T> action);

  * //Returns a stream consisting of the elements of this stream, truncated to be no longer than maxSize in length
    **Stream<T> limit(long maxSize);**

    //Returns a stream consisting of the remaining elements of this stream after **discarding the first n elements of the stream.** If this stream contains fewer than n elements then an empty stream will be returned.
    **Stream<T> skip(long n);**

  * //Performs an action for each element of this stream.
    **void forEach(Consumer<? super T> action);**

    //Performs an action for each element of this stream, in the encounter(相遇) order of the stream if the stream has a defined encounter order.
    void forEachOrdered(Consumer<? super T> action);

  * Returns an array containing the elements of this stream.

    Object[] toArray();

     Person[] men = people.stream()
                              .filter(p -> p.getGender() == MALE)
                              .toArray(Person[]::new);
    <A> A[] toArray(IntFunction<A[]> generator);

  * 

* boolean allMatch(Predicate<? super T> predicate)

  boolean noneMatch(Predicate<? super T> predicate)

* optional<T> findFirst()

  //This is to allow for maximal performance in parallel operations

  //the cost is that multiple invocations on the same source may not return the same result

  optional<T> findAny()

* 生成流

* ```
         //Returns an empty sequential Stream.
         static<T> Stream<T> empty()
       
         //Returns a sequential Stream containing a single element/whose elements are the specified values
         static<T> Stream<T> of(T t)
         static<T> Stream<T> of(T... value)
       
         //Returns an infinite sequential ordered Stream produced 
         static<T> Stream<T> iterate(T seed,UnaryOperator<T> f)
             UnaryOperator<T> extends Function<T, T>
         //Returns an infinite sequential unordered stream where each element is generated by the provided Supplier.	
         static<T> Stream<T> generate(Supplier<T> s)
         
         
     	//Creates a lazily concatenated stream whose elements are all the elements of the first stream followed by all the elements of the second stream.
     	static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
     	
     ```
  ```
  
  ```



* reduce

  ```
  Optional<T> reduce(BinaryOperator<T> accumulator);
  	
  T reduce(T identity, BinaryOperator<T> accumulator);
  The identity value is a value, such that b op identity = b
  e.g.
  reduce("", String::concat)
  reduce(true, (a,b) -> a&&b)
  reduce(false, (a,b) -> a||b)
  reduce(Collections.emptySet(),
         (a,b)->{ Set<X> s=new HashSet<>(a); s.addAll(b); return s; })
  reduce(Double.POSITIVE_INFINITY, Math::min)
  reduce(Double.NEGATIVE_INFINITY, Math::max)

  <U> U reduce(U identity,
                   BiFunction<U, ? super T, U> accumulator,
                   BinaryOperator<U> combiner);
  前两种实现有一个缺陷，它们的计算结果必须和stream中的元素类型相同。第三个重载方法的
  第三个参数是为了解决这个问题 指定返回类型，匹配第二个参数 如(a,b)-> 0L
  ```

  Function<T,R>

  	R apply(T t);

  BiFunction<T,U,R>

  	R apply(T t,U u)

  BinaryOperator<T> extends  BiFunction<T,T,T> 实际等同于 T apply(T t)


  		

* Optional

  A container object which may or may not contain **a non-null value.** If a value is present, isPresent() will return true and get() will return the value.
  Additional methods that depend on the presence or absence of a contained value are provided, such as orElse() (return a default value if value not present) and ifPresent() (execute a block of code if the value is present).
  This is a value-based class; **use of identity-sensitive operations** (including reference equality (==), identity hash code, or synchronization) on instances of Optional may have unpredictable results and **should be avoided**.

  

  ```
  
  ```
# lambda

- 函数式接口: 只包含**唯一一个** **抽象**方法  对于函数式接口可以采用lambda表达式

- 演进过程 外部类->静态内部类->局部内部类->匿名内部类(没有名称,必须借助接口或父类)->lambda表达式

  ```java
  public class Lambda {
      //3.静态内部类
      static class Like2 implements ILike{
          @Override
          public void lambda() {
              System.out.println("I like lambda2");
          }
      }
  
      public static void main(String[] args) {
          ILike like=new Like1();
          like.lambda();
  
          like=new Like2();
          like.lambda();
  
          //4.局部内部类
          class Like3 implements ILike {
              @Override
              public void lambda() {
                  System.out.println("I like lambda3");
              }
          }
          like=new Like3();
          like.lambda();
  
          //5.匿名内部类
          like=new ILike() {
              @Override
              public void lambda() {
                  System.out.println("I like lambda4");
              }
          };
          like.lambda();
  
          //6.lambda
          like=()-> System.out.println("I like lambda5");
          like.lambda();
  
      }
  }
  
  //1.定义个函数式接口
  interface ILike{
      void lambda();
  }
  //2.外部类
  class Like1 implements ILike{
      @Override
      public void lambda() {
          System.out.println("I like lambda1");
      }
  }
  ```

  # 估算k的根号数

  

1. x^2-ky^2=1   x/y的解即近似数 先估算第一组(x1,y1)    x2=x1^2+k^2+y1^2;y2=2x1y1
2. 连分数 形如  x1+1/(x2+1/x3+1/+.....1/k+xn)

