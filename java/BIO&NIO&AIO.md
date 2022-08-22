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





# 概述

网络编程的本质就是**进程间通信**,通信的基础是**IO模型**



## IO stream

1. DataInputStream: A data input stream lets an application read primitive java data types from an underlying(底层的) input stream in a machine-independent way.

2. 装饰器模式

   