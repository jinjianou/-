## RMI

RMI是Java提供的分布式应用API，远程方法调用RPC的实现。它的宗旨是，某个JVM下的对象可以调用其他JVM下的远程对象。RMI的底层实现是**构建于TCP协议**之上，将远程对象绑定具体的端口号，监听客户端的请求；客户端与远程对象的通信当中，依赖于预定义的接口，即RMI会生成一个本地Stub代理类，每次客户端调用远程对象的时候，Stub代理类会初始化参数、启动远程连接、将参数进行编组(marshal)，通过网络传输送往服务器端，并对返回的结果进行反编组(unmarshal)。对于客户端调用方来讲，RMI隐藏了对象序列化和网络传输的实现细节。

RMI调用的大体步骤：首先**RMI Server**会通过请求**RMIRegistry**(远程对象联机注册服务)绑定一个远程对象，对象的元数据信息放在一个已有的**Web Server**上面；然后**RMI Client**会发送请求到RMIRegistry获取远程对象的地址，并远程调用该对象的方法

图1

```
public class RMIServer {
    static  IAnimalService obj=new AnimalServiceImpl();
    static IAnimalService stub;//Keep a strong reference  so that it remains reachable
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException, NotBoundException {
        String host="127.0.0.1";
        int port=1099;
//        String host="192.168.220.193";
        System.setProperty("java.rmi.server.hostname",host);
        Registry registry = LocateRegistry.getRegistry(host);
        final String serviceName="animalService";
        stub =(IAnimalService)  UnicastRemoteObject.exportObject(obj,port);  //端口绑定远程对象
        try {
            registry.unbind(serviceName);
        }catch (NotBoundException ex){
            // left blank
        }finally {
            registry.bind(serviceName, stub); //注册服务地址
        }
```

```
public class RMIClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String host="127.0.0.1";
        final String serviceName="animalService";
        Registry registry = LocateRegistry.getRegistry(host);
        //获取动态代理类
        IAnimalService stub=(IAnimalService)registry.lookup(serviceName);
        //远程调用
        String monkeyName = stub.getMonkeyName();
        System.out.println(monkeyName);
    }


}
interface IAnimalService extends Remote {
    public String getMonkeyName() throws RemoteException;
}

class AnimalServiceImpl implements IAnimalService{
    @Override
    public String getMonkeyName() throws RemoteException {
        return "I'm Jacky";
    }
}
```

使用RMI的利弊：

- 优势：面向对象的远程服务模型；基于TCP协议上的服务，执行速度快。
- 劣势：不能跨语言；每个远程对象都要绑定端口，不易维护；不支持分布式事务JTA

## WebService

以SOAP协议实现的Web Service模型为例。首先客户端通过UDDI(发现整合平台)找到对应的Web Service，下载对应WSDL文件，生成本地代理类，继而请求Web Service服务。UDDI的概念一直被弱化，因为客户端一般都知道Web Service的地址。

代码中使用的使用的Web Service第三方库是CXF(http://cxf.apache.org/)，规范使用的是JAX-WS

```
<dependency>
    <groupId>javax.xml.ws</groupId>
    <artifactId>jaxws-api</artifactId>
    <version>2.3.1</version>
</dependency>

<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-core</artifactId>
    <version>3.4.4</version>
</dependency>
<dependency>
    <groupId>org.apache.cxf</groupId>
    <artifactId>cxf-rt-frontend-jaxws</artifactId>
    <version>3.4.4</version>
</dependency>
```

**Binary releases contain computer readable version of the application, meaning it is compiled**. Source releases contain human readable version of the application, meaning it has to be compiled before it can be used**

1. 下载cxf

   (https://www.apache.org/dyn/closer.lua/cxf/3.5.0/apache-cxf-3.5.0.zip)

   **将lib下的jar包导入项目**

2. 环境变量配置

   %CXF_HOME%\bin

3. 服务接口及实现

   ```
   @WebService
   interface IAnimalService2{
       public String getMonkeyName();
   }
   ```

   ```
   //serviceName： 对外发布的服务名
   //endpointInterface 服务接口全路径
   //name 默认情况下，该值是实现XML Web Service的类的名称，wsdl:portType 的名称。缺省值为 Java 类或接口的非限定名称
   //portName 缺省值为 WebService.name+Port
   //wsdlLocation定义 Web Service 的 WSDL 文档的 Web 地址
   @WebService(endpointInterface = "org.example.remoting.webservice.IAnimalService2", serviceName = "AnimalService2Impl")
   public class AnimalService2Impl implements IAnimalService2{
       @Override
       public String getMonkeyName() {
           return "I'm Jacky";
       }
   }
   ```

4. server

   ```
   IAnimalService2 serviceInstance = new AnimalService2Impl();
   final String address = "http://localhost:9000/animalService2"; //服务名称
   Endpoint.publish(address,serviceInstance);  //绑定并发布服务
   ```

5. 在项目src下生成wdsl等文件

   wsdl2java http://localhost:9000/animalService2?wsdl

   会在接口所在目录生成相应的java代码文件

6. client

   ```
   JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
   factory.getInInterceptors().add(new LoggingInInterceptor()); //日志输入拦截器
   factory.getOutInterceptors().add(new LoggingOutInterceptor()); //日志输出拦截器
   factory.setServiceClass(IAnimalService2.class);
   factory.setAddress("http://localhost:9000/animalService2");
   IAnimalService2 client = (IAnimalService2) factory.create();
   System.out.println(client.getMonkeyName());
   ```

## HTTP UDP TCP

一  定义

HTTP 协议：Hyper Text Transfer Protocol（超文本传输协议）的缩写，是用于从万维网（WWW:World Wide Web ）服务器传输超文本到本地浏览器的传送协议。

UDP 是User Datagram Protocol的简称， 中文名是用户数据报协议，是[OSI](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/OSI)（Open System Interconnection，[开放式系统互联](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E5%BC%80%E6%94%BE%E5%BC%8F%E7%B3%BB%E7%BB%9F%E4%BA%92%E8%81%94/562749)） 参考模型中一种无连接的[传输层](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E4%BC%A0%E8%BE%93%E5%B1%82)协议，提供面向事务的简单不可靠信息传送服务，IETF RFC 768是UDP的正式规范。UDP在IP报文的协议号是17。

TCP（Transmission Control Protocol [传输控制协议](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E4%BC%A0%E8%BE%93%E6%8E%A7%E5%88%B6%E5%8D%8F%E8%AE%AE/9727741)）是一种面向连接的、可靠的、基于字节流的[传输层](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E4%BC%A0%E8%BE%93%E5%B1%82/4329536)通信协议，由IETF的RFC 793定义。



二 层次

​	**OSI模型（七层参考模型）、TCP/IP协议模型**

​    应用层、表示层、会话层 -> 应用层

​     传输层-> 传输层

​	  网络层->IP层

​    数据链路层、物理层->网络接口层

​	UDP、TCP处于 OSI 传输层	

​    http协议是在**tcp/ip协议模型上应用层**的一种传输协议。		

三 HTTP特点

1. **基于TCP的可靠通信**

2. 基于客户端与服务端的通信

3. 无状态：是指协议对于事务处理没有记忆能力。缺少状态意味着如果后续处理需要前面的信息，则它必须重传，这样可能导致每次连接传送的数据量增大。另一方面，在服务器不需要先前信息时它的应答就较快。

4. 无连接：其含义是**限制每次连接只处理一个请求**。服务器处理完客户的请求，并收到客户的应答后，即断开连接。采用这种方式可以节省传输时间。

   现在HTTP有两个版本，分别是Http1.0和Http1.1

​	    HTTP 1.0 初衷主要是解决WEB文档在网络中的传输问题，因为传输文件是一个低频的请求，没必要进行长时间连接，所以HTPP 1.0 被设计成短连接，每进行一次HTPP通信后就会断开TCP连接。

​		HTTP 1.1版本随着互联网的发展，HTTP 不再只是传送简单的文件信息,多样化的文本信息开始广泛应用，像html 这样的网页访问的同时会同时附带非常多的图片之类的信息，如果每个请求都要进行TCP连接和断开（三次握手和四次挥手），这样势必会造成很多额外的通信开销。

​		所以为了解决此问题 HTTP 协议2.0版本会在请求的时候，只要任意一端没有明确的提出断开连接则保持TCP连接状态

​	HTTP/1.1相较于 HTTP/1.0 协议的区别主要体现在：

​	缓存处理
​	带宽优化及网络连接的使用
​	错误通知的管理
​	消息在网络中的发送
​	互联网地址的维护
​	安全性及完整性

**四、socket**

网络上的两个程序通过一个双向的通信连接实现数据的交换，这个连接的一端称为一个socket。

- SYN表示建立连接，
- FIN表示关闭连接，
- ACK表示响应，
- PSH表示有 DATA数据传输，
- RST表示连接重置。



**tcp建立连接中的socket三次握手**

![img](https://pic2.zhimg.com/80/v2-6df450780c459ba070a5ebdb2d494aa9_1440w.jpg)

大致流程如下：

- 客户端向服务器发送一个SYN J
- 服务器向客户端响应一个SYN K，并对SYN J进行确认ACK J+1
- 客户端再想服务器发一个确认ACK K+1
  *SYN：同步序列编号（***Synchronize Sequence Numbers\***）。是TCP/IP建立连接时使用的握手信号。在客户机和服务器之间建立正常的TCP网络连接时，客户机首先发出一个SYN消息，服务器使用SYN+ACK应答表示接收到了这个消息，最后客户机再以[ACK](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/ACK)消息响应。这样在客户机和服务器之间才能建立起可靠的TCP连接，数据才可以在客户机和[服务器](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E6%9C%8D%E5%8A%A1%E5%99%A8/100571)之间传递。

**tcp释放连接中的socket四次握手**

![img](https://pic2.zhimg.com/80/v2-aae286966db56d5932944ee35fb8c831_1440w.jpg)

大致流程如下：

- 某个应用进程首先调用close主动关闭连接，这时TCP发送一个FIN M；
- 另一端接收到FIN M之后，执行被动关闭，对这个FIN进行确认。它的接收也作为文件结束符传递给应用进程，因为FIN的接收意味着应用进程在相应的连接上再也接收不到额外数据；
- 一段时间之后，接收到文件结束符的应用进程调用close关闭它的socket。这导致它的TCP也发送一个FIN N；
- 接收到这个FIN的源发送端TCP对它进行确认。



**为什么tcp需要进行三次握手、四次分手？**

因为 tcp 是全双工。



**什么是双工？**

[数据通信](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E6%95%B0%E6%8D%AE%E9%80%9A%E4%BF%A1)中，数据在线路上的传送方式可以分为[单工通信](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E5%8D%95%E5%B7%A5%E9%80%9A%E4%BF%A1)、[半双工通信](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E5%8D%8A%E5%8F%8C%E5%B7%A5%E9%80%9A%E4%BF%A1)和[全双工通信](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E5%85%A8%E5%8F%8C%E5%B7%A5%E9%80%9A%E4%BF%A1)三种。

单工通信信道是单向信道，发送端和接收端的身份是固定的，发送端只能发送信息，不能接收信息；接收端只能接收信息，不能发送信息，数据信号仅从一端传送到另一端，即信息流是单方向的。

半双工通信，即Half-duplex Communication。这种[通信方式](https://link.zhihu.com/?target=https%3A//baike.baidu.com/item/%E9%80%9A%E4%BF%A1%E6%96%B9%E5%BC%8F/4535155)可以实现双向的通信，但不能在两个方向上同时进行，必须轮流交替地进行。也就是说，通信信道的每一段都可以是发送端，也可以是接收端。但同一时刻里，信息只能有一个传输方向。如日常生活中的例子有步话机通信,对讲机等。

全双工（Full Duplex）是 在微处理器与外围设备之间采用发送线和接受线各自独立的方法，可以使数据在两个方向上同时进行传送操作。指在发送数据的同时也能够接收数据，两者同步进行，这好像我们平时打电话一样，说话的同时也能够听到对方的声音。



**5、tcp跟udp区别**

1. TCP面向连接（如打电话要先拨号建立连接）;UDP是无连接的，即发送数据之前不需要建立连接。
2. TCP提供可靠的服务。也就是说，通过TCP连接传送的数据，无差错，不丢失，不重复，且按序到达;UDP尽最大努力交付，即不保证可靠交付
3. TCP面向字节流，实际上是TCP把数据看成一连串无结构的字节流;UDP是面向报文的。UDP没有拥塞控制，因此网络出现拥塞不会使源主机的发送速率降低（对实时应用很有用，如IP电话，实时视频会议等）
4. 每一条TCP连接只能是点到点的;UDP支持一对一，一对多，多对一和多对多的交互通信
5. TCP首部开销20字节;UDP的首部开销小。
6. TCP的逻辑通信信道是全双工的可靠信道，UDP则是不可靠信道。
7. 速度：UDP要比TCP要快！

注意：udp也是全双工，跟tcp一样。但是udp无状态，无连接，不需要进行握手，只管发送，不确认对方是否接收到。

## RPC

RPC（远程过程调用）和RMI（远程方法调用）是两种可以让用户从一台电脑调用不同电脑上面的方法的的机制（也可以称作规范、协议）。两者的主要不同是他们的使用方式或者称作范式，**RMI使用面向对象的范式**，也就是用户需要知道他调用的对象和对象中的方法；RPC不是面向对象也不能处理对象，而是调用具体的子程序。

### Restful API(http+json)

   #### http

http协议是基于tcp协议的，tcp协议是**流式协议(边流动边解析/边读取解析)**，包头部分可以通过多出的\r\n来分界，包体部分如何分界呢？这是协议本身要解决的问题。目前一般有两种方式，第一种方式就是在包头中有个content-Length字段，这个字段的值的大小标识了POST数据的长度，服务器收到一个数据包后，先从包头解析出这个字段的值，再根据这个值去读取相应长度的作为http协议的包体数据。。

#### restful

网站即软件，而且是一种新型的软件，这种"互联网软件"采用客户端/服务器模式，建立在分布式体系上，通过互联网通信，具有高延时（high latency）、高并发等特点。
 　它首次出现在 2000 年 Roy Fielding 的博士论文中，他是 HTTP 规范的主要编写者之一。Representational State Transfer，翻译是”表现层状态转化”，通俗来讲就是：资源在网络中以某种表现形式进行状态转移。
 总结一下什么是RESTful架构：
 　（1）每一个URI代表一种资源；
 　（2）客户端和服务器之间，传递这种资源的某种表现层，比如用JSON，XML，JPEG等；
 　（3）客户端通过四个HTTP动词，对服务器端资源进行操作，实现"表现层状态转化"。

URL定位**资源**，用HTTP动词（GET,POST,DELETE,DETC）描述操作。
 用HTTP协议里的动词来实现资源的添加，修改，删除等操作。即通过HTTP动词来实现资源的状态扭转：
 　GET 用来获取资源，
 　POST 用来新建资源（也可以用于更新资源），
 　PUT 用来更新资源，
 　DELETE 用来删除资源。

### RPC

进程间通信（IPC，Inter-Process Communication），指至少两个进程或线程间传送数据或信号的一些技术或方法。进程是计算机系统分配资源的最小单位。每个进程都有自己的一部分独立的系统资源，彼此是隔离的。为了能使不同的进程互相访问资源并进行协调工作，才有了进程间通信。这些进程可以运行在同一计算机上或网络连接的不同计算机上。 进程间通信技术包括消息传递、同步、共享内存和远程过程调用。 IPC是一种标准的Unix通信机制。

有两种类型的进程间通信(IPC)。

- 本地过程调用(LPC)LPC用在多任务操作系统中，使得同时运行的任务能互相会话。这些任务共享内存空间使任务同步和互相发送信息。
- 远程过程调用(RPC)RPC类似于LPC，只是在网上工作。RPC开始是出现在Sun微系统公司和HP公司的运行UNIX操作系统的计算机中。

为什么RPC呢？就是无法在一个进程内，甚至一个计算机内通过本地调用的方式完成的需求，比如比如不同的系统间的通讯，甚至不同的组织间的通讯。由于计算能力需要横向扩展，需要在多台机器组成的集群上部署应用

**RPC的核心并不在于使用什么协议**。RPC的目的是让你在本地调用远程的方法，而对你来说这个调用是透明的，你并不知道这个调用的方法是部署哪里。**通过RPC能解耦服务**，**这才是使用RPC的真正目的**。RPC的原理主要用到了动态代理模式，至于http协议，只是传输协议而已。简单的实现可以参考spring remoting，复杂的实现可以参考dubbo。

简单的说，

- RPC就是从一台机器（客户端）上通过参数传递的方式调用另一台机器（服务器）上的一个函数或方法（可以统称为服务）并得到返回的结果。
- RPC 会隐藏底层的通讯细节（不需要直接处理Socket通讯或Http通讯） RPC 是一个请求响应模型。
- 客户端发起请求，服务器返回响应（类似于Http的工作方式） RPC 在使用形式上像调用本地函数（或方法）一样去调用远程的函数（或方法）。。

图2

'Stub' is a **class that implements the remote interface** in a way that you can use it as if it were a local one. It handles data marashalling/unmarshalling and sending/receiving to/from the remote service.

### RPC和restful api对比

REST是一种设计风格，它的很多思维方式与RPC是完全冲突的。 RPC的思想是把本地函数映射到API，也就是说一个API对应的是一个function，我本地有一个getAllUsers，远程也能通过某种约定的协议来调用这个getAllUsers。至于这个协议是Socket、是HTTP还是别的什么并不重要； RPC中的主体都是动作，是个动词，表示我要做什么。 而REST则不然，它的URL主体是资源，是个名词。而且也仅支持HTTP协议，规定了使用HTTP Method表达本次要做的动作，类型一般也不超过那四五种。这些动作表达了对资源仅有的几种转化方式。
 RPC的根本问题是耦合。RPC客户端以多种方式与服务实现紧密耦合，并且很难在不中断客户端的情况下更改服务实现。RPC更偏向内部调用，REST更偏向外部调用。

Web 服务应该算是 RPC 的一个子集，理论上 RPC 能实现的功能， 用 Web 服务也能实现，甚至很多 RPC 框架选用 HTTP 协议作为传输层。
 现在很多网站的 API 都是以 HTTP 服务的形式提供的，这也算是 RPC 的一种形式。

区别主要在这 2 个东西设计的出发点不太一样：

- HTTP 是面向浏览器设计的应用层协议，操作的核心在**资源。**我们更多的用 Web 服务在做网站。
- RPC 是为了在像在本地调用一个函数那样调用远程的代码而设计的，所以更关注减少本地调用和远程调用的差异，像 SOAP(简单对象访问协议) 这种东西是可以把对象当参数传的。

我们讨论 RPC 和 Web 的区别，其实是在谈论 2 个东西：序列化协议和传输协议。序列化协议比如常见的 XML，JSON 和比较现代的 Protocol Buffers、Thrift。 传输协议比如 TCP、UDP 以及更高层的 HTTP 1.1、HTTP 2.0。

一般我们考虑用 RPC 而不是 HTTP 构建自己的服务，通常是考虑到下面的因素：

- 接口是否需要 Schema 约束
- 是否需要更高效的传输协议（TCP，HTTP 2.0）
- 是否对数据包的大小非常敏感

比如 HTTP 是基于文本的协议，头部有非常多冗余（对于 RPC 服务而言）。HTTP 中我们用的最多就是 RESTful ，而 RESTful 是个弱 Schema 约束，大家通过文档沟通，但是如果我就是不在实现的时候对接口文档约定的参数做检查，你也不能把我怎么样。这个时候 Thrift 这种序列化协议的优势就体现出来了，由于 Schema 的存在，可以保证服务端接受的参数和 Schema 保持一致

####  序列化

**1.序列化定义**

- 序列化（serialization）就是将对象序列化为二进制形式（字节数组），一般也将序列化称为编码（Encode），主要用于网络传输、数据持久化等；
- [反序列化](https://so.csdn.net/so/search?q=反序列化&spm=1001.2101.3001.7020)（deserialization）则是将从网络、磁盘等读取的字节数组还原成原始对象，以便后续业务的进行，一般也将反序列化称为解码（Decode），主要用于网络传输对象的解码，以便完成远程调用。

**2.序列化的“鼻祖”**

我知道的第一种序列化协议就是Java默认提供的序列化机制，需要序列化的Java对象只需要实现 Serializable / Externalizable 接口并生成序列化ID，这个类就能够通过 ObjectInput 和 ObjectOutput 序列化和反序列化，若对Java默认的序列化协议不了解，或是遗忘了，请参考：[序列化详解](http://blog.csdn.net/baiye_xing/article/details/71809993)

但是Java默认提供的序列化有很多问题，主要有以下几个缺点：

- 无法跨语言：我认为这对于Java序列化的发展是致命的“失误”，因为Java序列化后的字节数组，其它语言无法进行反序列化。；
- 序列化后的码流太大:：相对于目前主流的序列化协议，Java序列化后的码流太大；
- 序列化的性能差：由于Java序列化采用同步阻塞IO，相对于目前主流的序列化协议，它的效率非常差。

**3.影响序列化性能的关键因素**

- 序列化后的码流大小（网络带宽的占用）；
- 序列化的性能（CPU资源占用）；
- 是否支持跨语言（异构系统的对接和开发语言切换）。

**几种流行的序列化协议比较**

**1、XML**

（1）定义：

XML（Extensible Markup Language）是一种常用的序列化和反序列化协议， 它历史悠久，从1998年的1.0版本被广泛使用至今。

（2）优点

- 人机可读性好
- 可指定元素或特性的名称

（3）缺点

- 序列化数据只包含数据本身以及类的结构，不包括类型标识和程序集信息。
- 类必须有一个将由 XmlSerializer 序列化的默认构造函数。
- 只能序列化公共属性和字段
- 不能序列化方法
- **文件庞大，文件格式复杂**，传输占带宽

（4）使用场景

- 当做配置文件存储数据
- 实时数据转换

**2、JSON**

（1）定义：

JSON(JavaScript Object Notation, JS 对象标记) 是一种轻量级的数据交换格式。它基于 ECMAScript (w3c制定的js规范)的一个子集， JSON采用与编程语言无关的文本格式，但是也使用了类C语言（包括C， C++， C#， Java， JavaScript， Perl， Python等）的习惯，简洁和清晰的层次结构使得 JSON 成为理想的数据交换语言。

（2）优点

- 前后兼容性高
- 数据格式比较简单，易于读写
- 序列化后数据较小，可扩展性好，兼容性好
- 与XML相比，其协议比较简单，解析速度比较快

（3）缺点

- 数据的描述性比XML差
- **不适合性能要求为ms级别的情况**
- **额外空间开销比较大**

（4）适用场景（可替代ＸＭＬ）

- 跨防火墙访问
- 可调式性要求高的情况
- 基于Web browser的Ajax请求
- **传输数据量相对小，实时性要求相对低（例如秒级别）的服务**

**3、Fastjson**

（1）定义

Fastjson是一个Java语言编写的高性能功能完善的JSON库。它采用一种“假定有序快速匹配”的算法，把JSON Parse的性能提升到极致。

（2）优点

- 接口简单易用
- 目前java语言中最快的json库

（3）缺点

- 过于注重快，而偏离了“标准”及功能性
- 代码质量不高，文档不全

（4）适用场景

- 协议交互
- Web输出
- Android客户端

**4、Thrift**

（1）定义：

Thrift并不仅仅是序列化协议，而是一个RPC框架。它可以让你选择客户端与服务端之间传输通信协议的类别，即文本(text)和二进制(binary)传输协议, 为节约带宽，提供传输效率，一般情况下使用二进制类型的传输协议。

（2）优点

- 序列化后的体积小, 速度快
- **支持多种语言和丰富的数据类型**
- 对于数据字段的增删具有较强的兼容性
- 支持二进制压缩编码

（3）缺点

- 使用者较少
- 跨防火墙访问时，不安全
- **不具有可读性，调试代码时相对困难**
- **不能与其他传输层协议共同使用（例如HTTP）**
- **无法支持向持久层直接读写数据**，即不适合做数据持久化序列化协议

（4）适用场景

- 分布式系统的RPC解决方案

**5、Avro**

（1）定义：

Avro属于Apache Hadoop的一个子项目。 Avro提供两种序列化格式：JSON格式或者Binary格式。Binary格式在空间开销和解析性能方面可以和Protobuf媲美，Avro的产生解决了JSON的冗长和没有IDL的问题

（2）优点

- 支持丰富的数据类型
- 简单的动态语言结合功能
- 具有自我描述属性
- 提高了数据解析速度
- 快速可压缩的二进制数据形式
- 可以实现远程过程调用RPC
- 支持跨编程语言实现

（3）缺点

- 对于习惯于静态类型语言的用户不直观

（4）适用场景

- 在Hadoop中做Hive、Pig和MapReduce的持久化数据格式

**6、Protobuf**

（1）定义

protocol buffers 由谷歌开源而来，在谷歌内部久经考验。它将数据结构以.proto文件进行描述，通过代码生成工具可以生成对应数据结构的POJO对象和Protobuf相关的方法和属性。

（2）优点

- **序列化后码流小**，性能高
- **结构化数据存储格式**（XML JSON等）
- 通过标识字段的顺序，可以实现协议的前向兼容
- 结构化的文档更容易管理和维护

（3）缺点

- 需要依赖于工具生成代码
- 支持的语言相对较少，官方只支持Java 、C++ 、Python

（4）适用场景

- 对性能要求高的RPC调用
- 具有良好的跨防火墙的访问属性
- 适合应用层对象的持久化

**7、其它**

- protostuff 基于protobuf协议，但不需要配置proto文件，直接导包即可
- Jboss marshaling 可以直接序列化java类， 无须实java.io.Serializable接口
- Message pack 一个高效的二进制序列化格式
- Hessian 采用二进制协议的轻量级remoting onhttp工具
- **kryo 基于protobuf协议，只支持java语言**,需要注册（Registration），然后序列化（Output），反序列化（Input）

**8、性能对比图解**

**时间**

图3

**空间**

图4

**分析上图知：**

- XML序列化（Xstream）无论在性能和简洁性上比较差。
- Thrift与Protobuf相比在时空开销方面都有一定的劣势。
- Protobuf和Avro在两方面表现都非常优越。

**9、选型建议**

**不同的场景适用的序列化协议：**

- 对于公司间的系统调用，如果性能要求在100ms以上的服务，基于XML的SOAP协议是一个值得考虑的方案。
- 基于Web browser的Ajax，以及Mobile app与服务端之间的通讯，JSON协议是首选。对于性能要求不太高，或者以动态类型语言为主，或者传输数据载荷很小的的运用场景，JSON也是非常不错的选择。
- 对于调试环境比较恶劣的场景，采用JSON或XML能够极大的提高调试效率，降低系统开发成本。
- 当对性能和简洁性有极高要求的场景，Protobuf，Thrift，Avro之间具有一定的竞争关系。
- 对于T级别的数据的持久化应用场景，Protobuf和Avro是首要选择。如果持久化后的数据存储在Hadoop子项目里，Avro会是更好的选择。
- 由于Avro的设计理念偏向于动态类型语言，对于动态语言为主的应用场景，Avro是更好的选择。
- 对于持久层非Hadoop项目，以静态类型语言为主的应用场景，Protobuf会更符合静态类型语言工程师的开发习惯。
- 如果需要提供一个完整的RPC解决方案，Thrift是一个好的选择。
- 如果序列化之后需要支持不同的传输层协议，或者需要跨防火墙访问的高性能场景，Protobuf可以优先考虑。

## RPC框架

- Call ID映射。我们怎么告诉远程机器我们要调用Multiply，而不是Add或者FooBar呢？在本地调用中，函数体是直接通过函数指针来指定的，我们调用Multiply，编译器就自动帮我们调用它相应的函数指针。但是在远程调用中，函数指针是不行的，因为两个进程的地址空间是完全不一样的。所以，在RPC中，所有的函数都必须有自己的一个ID。这个ID在所有进程中都是唯一确定的。客户端在做远程过程调用时，必须附上这个ID。然后我们还需要在客户端和服务端分别维护一个 {函数 <--> Call ID} 的对应表。两者的表不一定需要完全相同，但相同的函数对应的Call ID必须相同。当客户端需要进行远程调用时，它就查一下这个表，找出相应的Call ID，然后把它传给服务端，服务端也通过查表，来确定客户端需要调用的函数，然后执行相应函数的代码。
- 序列化和反序列化。客户端怎么把参数值传给远程的函数呢？在本地调用中，我们只需要把参数压到栈里，然后让函数自己去栈里读就行。但是在远程过程调用时，客户端跟服务端是不同的进程，不能通过内存来传递参数。甚至有时候客户端和服务端使用的都不是同一种语言（比如服务端用C++，客户端用Java或者Python）。这时候就需要客户端把参数先转成一个字节流，传给服务端后，再把字节流转成自己能读取的格式。这个过程叫序列化和反序列化。同理，从服务端返回的值也需要序列化反序列化的过程。
- 网络传输。远程调用往往用在网络上，客户端和服务端是通过网络连接的。所有的数据都需要通过网络传输，因此就需要有一个网络传输层。网络传输层需要把Call ID和序列化后的参数字节流传给服务端，然后再把序列化后的调用结果传回客户端。只要能完成这两者的，都可以作为传输层使用。因此，它所使用的协议其实是不限的，能完成传输就行。尽管大部分RPC框架都使用TCP协议，但其实UDP也可以，而gRPC干脆就用了HTTP2。Java的Netty也属于这层的东西。

目前有很多Java的RPC框架，有基于Json的，有基于XML，也有基于二进制对象的。

论复杂度，RPC框架肯定是高于简单的HTTP接口的。但毋庸置疑，HTTP接口由于受限于HTTP协议，需要带HTTP请求头，导致传输起来效率或者说安全性不如RPC

图5

### 常用RPC框架

支持Java最多，golang

* Netty - Netty框架不局限于RPC，更多的是作为一种网络协议的实现框架，比如HTTP，由于RPC需要高效的网络通信，就可能选择以Netty作为基础。

* brpc是一个基于protobuf接口的RPC框架，在百度内部称为“baidu-rpc”，它囊括了百度内部所有RPC协议，并支持多种第三方协议，从目前的性能测试数据来看，brpc的性能领跑于其他同类RPC产品。

* Dubbo是Alibaba开发的一个RPC框架，远程接口基于Java Interface, 依托于Spring框架。

* gRPC的Java实现的底层网络库是基于Netty开发而来，其Go实现是基于net库。

* Thrift是Apache的一个项目([http://thrift.apache.org](http://thrift.apache.org/))，前身是Facebook开发的一个RPC框架，采用thrift作为IDL (Interface description language)。

* jsonrpc

### jsonrpc

python web接口实现 https://www.jianshu.com/p/545acae57e27

区块链项目中用的较多？资料不是很多
 JSON-RPC是一种序列化协议。JSON 是 JS 对象的字符串表示法，它使用文本表示一个 JS 对象的信息，本质是一个字符串。
 非常简单，方便，速度慢
 相关Python 包(直接集成到flask和django)
 Flask-JSONRPC,django-json-rpc；jsonrpcserver,jsonrpcclient

### jsonrpc4j

* alone

  0. 依赖

     ```
     <dependency>
         <groupId>com.github.briandilley.jsonrpc4j</groupId>
         <artifactId>jsonrpc4j</artifactId>
         <version>1.5.3</version>
     </dependency>
      <dependency>
                 <groupId>jakarta.jws</groupId>
                 <artifactId>jakarta.jws-api</artifactId>
                 <version>2.1.0</version>
     </dependency>
     ```

  1. 接口及实现

  2. 创建server

     ```
     class UserServiceServlet
         extends HttpServlet {
     
         private UserService userService;
         private JsonRpcServer jsonRpcServer;
     
         protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
             jsonRpcServer.handle(req, resp);
         }
     
         public void init(ServletConfig config) {
             //this.userService = ...
             this.jsonRpcServer = new JsonRpcServer(this.userService, UserService.class);
         }
     
     }
     ```

  3. 创建client及service代理对象

     ```
     JsonRpcHttpClient client = new JsonRpcHttpClient(
             new URL("http://127.0.0.1:8080/UserService.json"));
     
     UserService userService = ProxyUtil.createClientProxy(
             getClass().getClassLoader(),
             UserService.class,
             client);
     
     User user = userService.createUser("bob", "the builder");
     ```

  4. 调用

* integrate with spring

  0. 依赖

  1. 接口及实现

     ```
     public interface UserService {
         //By default all error message  returned by Exception.getmessage() with a code of 0.
         @JsonRpcErrors({
                /* @JsonRpcError(exception=UserExistsException.class,
                         code=-5678, message="User already exists", data="The Data"),*/
                 @JsonRpcError(exception=Throwable.class,code=-187)
         })
         //JSON-RPC requires named based parameters rather than indexed parameters an annotation can be added to your service interface (this also works on the service implementation for the ServiceExporter)
         User createUser(@JsonRpcParam("username") String username, @JsonRpcParam("password")  String password);
     }
     ```

     ```
     @Service
     public class UserServiceImpl implements UserService{
         @Override
         public User createUser(String username, String password) {
             System.out.println("====calling server createUser method=======");//execute at server
             return new User("A","B"); //return to client
         }
     }
     ```

  2. 创建server

     ```
     @Bean("/UserService.json")
     public RemoteExporter getJsonServiceExporter(UserService service){
         JsonServiceExporter serviceExporter = new JsonServiceExporter();
         serviceExporter.setService(service);
         serviceExporter.setServiceInterface(service.getClass().getInterfaces()[0]);
         return serviceExporter;
     }
     ```

  3. 创建client及service代理对象

     ```
     <!--    This service can be accessed by any JSON-RPC capable client, including the JsonProxyFactoryBean, JsonRpcClient and JsonRpcHttpClient provided by this project-->
         <bean class="com.googlecode.jsonrpc4j.spring.JsonProxyFactoryBean" id="jsonProxy">
             <property name="serviceUrl" value="http://127.0.0.1:8080/UserService.json"/>
             <property name="serviceInterface" value="org.example.remoting.jsonrpc.UserService"/>
         </bean>
     ```

  4. 调用

     ```
     UserService jsonProxy = (UserService)appCtx.getBean("jsonProxy");
     User user = jsonProxy.createUser("A", "B");
     System.out.println(user);
     ```

     service auto discovery edition:

  ```
  @JsonRpcService("/UserService.json")
  public interface UserService {
  .....
  }
  ```

  ```
  @Service
  @AutoJsonRpcServiceImpl
  public class UserServiceImpl implements UserService{
  ...
  }
  ```

```
@Bean
public AutoJsonRpcServiceImplExporter getJsonServiceExporter(){
    return new AutoJsonRpcServiceImplExporter();
}
```

### thift

python rpc https://www.jianshu.com/p/82a6bdaabcd3

###  grpc

官方文档 http://doc.oschina.net/grpc?t=56831

### HTTP2

HTTP/2 是 HTTP 协议自 1999 年 HTTP 1.1 发布后的首个更新，主要基于 SPDY 协议。
 HTTP/2的主要目标是通过启用完整请求和响应复用来减少延迟，通过有效压缩HTTP头字段来最大限度地降低协议开销，并添加对请求优先级和服务器推送的支持;多路复用(同一tcp,多个流)，头部压缩，服务推送。

### protobuf

语法指南 http://colobu.com/2015/01/07/Protobuf-language-guide/

使用和原理 https://www.ibm.com/developerworks/cn/linux/l-cn-gpb/index.html

HTTP/2 是 HTTP 协议自 1999 年 HTTP 1.1 发布后的首个更新，主要基于 SPDY 协议。
 HTTP/2的主要目标是通过启用完整请求和响应复用来减少延迟，通过有效压缩HTTP头字段来最大限度地降低协议开销，并添加对请求优先级和服务器推送的支持;多路复用(同一tcp,多个流)，头部压缩，服务推送。注明出处。

### 选择

**什么时候应该选择gRPC而不是Thrift**
 　需要良好的文档、示例
 　喜欢、习惯HTTP/2、ProtoBuf
 　对网络传输带宽敏感
 **什么时候应该选择Thrift而不是gRPC**
 　需要在非常多的语言间进行数据交换
 　对CPU敏感
 　协议层、传输层有多种控制要求
 　需要稳定的版本
 　不需要良好的文档和示例

总的来说，Python rpc框架选择较少，thrift性能最好，grpc性能比thrift稍差，原因是多了http2，而thrift直接基于tcp，但grpc序列化方案更通用(protobuf)优秀，文档较好；
 jsonrpc 本身基于http/1进行通信，速度最慢，相对于之前速度无提升，只是接口和数据格式更为统一；

### gRPC不足

1）GRPC尚未提供连接池
 2）尚未提供“服务发现”、“负载均衡”机制
 3）因为基于HTTP2，绝大部多数HTTP Server、Nginx都尚不支持，即Nginx不能将GRPC请求作为HTTP请求来负载均衡，而是作为普通的TCP请求。（nginx将会在1.9版本支持）