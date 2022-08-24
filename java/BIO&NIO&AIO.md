# 初始

java1.4以前 BIO

java1.4 NIO

java1.7 AIO

![image-20220822145830122](assets/image-20220822145830122.png)



# 前置

1. DNS

![image-20220822150650441](assets/image-20220822150650441.png)

![image-20220822151021973](assets/image-20220822151021973.png)

![image-20220822151229963](assets/image-20220822151229963.png)

![image-20220822152805512](assets/image-20220822152805512.png)

还有一种递归查询

1. 如果当级域名知道请求域名对应的ip,直接返回客户端
2. 否则,交给它所知道的下级域名





2. 网络模型

   ![image-20220822154346021](assets/image-20220822154346021.png)





# 网络编程概述

网络编程的本质就是**进程间通信**,通信的基础是**IO模型**



## IO stream

1. DataInputStream: A data input stream lets an application read primitive java data types from an underlying(底层的) input stream in a machine-independent way.

2. 装饰器模式

   BufferedInpustream(FileInpustream)
   
   

## Scoket

1. scoket也是一种数据源
2. socket是网络通信的端点
3. unix kernal会维护一个文件描述符表(已打开文件的索引),同时每个进程都会维护一个文件描述符表(最终映射到内核的文件描述符)

![image-20220823094420998](assets/image-20220823094420998.png)

1. 生成抽象的socket
2. 找到网卡对应的驱动程序,并将socket和其绑定起来
3. 应用程序发送数据到socket
4. 驱动程序从socket中读取数据,并通过网卡发送到网络

![image-20220823094805586](assets/image-20220823094805586.png)



## 同步/异步,堵塞/非堵塞

同步指两个或两个以上随时间变化的量在变化过程中保持一定的相对关系(通信机制) 简单理解: **保持在线通信**

​	数据同步和过程同步

异步 **调用快速结束 结果需要处理后才能返回**

堵塞 **调用过程中调用者需要等待一个事件的变化而处于某个状态中** ( 调用状态 )



## 线程池

![image-20220823103203906](assets/image-20220823103203906.png)

![image-20220823103653392](assets/image-20220823103653392.png)

reuse threads

CachedThreadPool 和 fiexedThreadPool 的区别在于 

​	create new threads when previously constaructed threads are not available

 ScheduledThreadPool

​	can  schedule commands to run after a given delay or to execute periodicallys





# Scoket

![image-20220823105152392](assets/image-20220823105152392.png)

accept() blocks until a connection is made



需求

1. client由键盘输入,发送到server,server读取打印并回复给client,client接收打印
2. 当client输入quit时 client断开连接

Server

```
public class Server {
    private static final int PORT=8088;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("启动服务器,监听端口"+PORT);
        while (true){
            Socket cli = server.accept();
            System.out.println("[客户端] "+cli.getPort()+"已连接");
            try(BufferedReader reader=new BufferedReader(
                    new InputStreamReader(cli.getInputStream()))){
                try(BufferedWriter writer=new BufferedWriter(
                        new OutputStreamWriter(cli.getOutputStream()))){
                    String msg;
                    //cli直接输入回车,readLine为"" 当cli quit时会发送给server null,readLine为null
                    while ((msg = reader.readLine())!=null){
                        System.out.println("[客户端] "+cli.getPort()+" 发来消息: "+msg);

                        writer.write("[服务器] 回复: ack");
                        writer.newLine();
                        writer.flush();
                    }
                }
            }
        }
    }
}
```



client

```
public class Client {
    private static final int PORT=8088;
    private static final String IP="127.0.0.1";
    private static final String QUIT="quit";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(IP, PORT);
        String msg;
        try(BufferedReader reader=new BufferedReader(
                new InputStreamReader(System.in))){
            try(BufferedWriter writer=new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()))){
                try(BufferedReader serverReader=new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))){
                    while (true){
                        msg = reader.readLine();
                        if(StringUtils.isBlank(msg)) continue;
                        if(Objects.equals(msg.toLowerCase(),QUIT)) break;

                        writer.write(msg);
                        writer.newLine();
                        writer.flush();

                        System.out.println(serverReader.readLine());
                    }

                }
            }
        }
        socket.close();
        System.out.println("关闭socket");
    }
}
```

注意: **退出serverReader等try-with代码块会JVM自动关闭socket 准确的说 scoket的inputstream/outpuststream close,socket都会close** 



# BIO

![image-20220823132048947](assets/image-20220823132048947.png)



## 多人聊天室

需求

1. 基于BIO模型
2. 支持多人在线
3. 每个用户的发言都会被转发给其他在线用户



分析:

1. sever端  main线程充当Acceptor,当有一个client请求,创建一个与之绑定的handler线程

   维护一个List<Socket>

2. client端 由于输入调用也是堵塞的,为了即时接受到其他用户的发言,可以分为2个线程.



![img](assets/487c5e1e2dc91062163853e0e3c1a4c7.png)