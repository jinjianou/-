

## 架构

* 单体

  * 优点 
    * 迭代周期短，速度快
    * 打包方便，运维省事
  * 缺陷
    * 单节点宕机导致所有服务不可用
    * 耦合度太高（迭代，测试，部署）
    * 单节点并发能力有限

* 集群

  * 优点
    * 提高系统性能
    * 提高系统可用性

  * 注意点
    * 用户会话
    * 定时任务
    * 内网互通

## 概述

enginx(engine x) 高性能Http和**反向代理web服务器**

通过配置文件实现集群和负载均衡

![1](C:\Users\Administrator\Desktop\复习\素材\pic\nginx\1.png)

### 常见服务器

* MS IIS asp.net
* weblogic、Jboss 传统行业 ERP/物流/电信/金融
* Tomcat,Jetty j2ee
* Apache、Nginx 静态服务、反向代理

##  Weblogic

* JNDI 在 J2EE 中的角色就是“交换机” —— **J2EE 组件在运行时间接地查找其他**组件、**资源**或服务的通用机制。JNDI 在 J2EE 应用程序中的主要角色就是提供间接层，这样组件就可以发现所需要的资源，而不用了解这些间接性。

* vs tomcat

  * 全面支持J2ee规范，web service,ssl,xml,,ejb等 vs 只支持部分j2ee规范
  * Web控制器进行组件、jdbc、管理和配置 vs 差
    * 较好的支持热部署(debug) vs 差
  * 收费 vs 免费

  

## 代理

* 正向代理
  * 客户端请求目标服务器之间的一个代理服务器
* 反向代理
  * 用户访问目标服务器，由代理服务决定访问哪个ip
  * 例  报名表(用户请求）->教务处（反向代理服务器）->班级(目标服务器)
  * 路由功能
    * Routing is *the* *process* *of* *selecting* *a* *path* *for* *traffic* *in* *a* *network* *or* *between* *or* *across* *multiple* *networks*。路由发生在OSI网络参考模型中的第三层即网络层。

## 安装

* 在线linux环境

  * [Online Bash Compiler - Online Bash Editor - Online Bash IDE - Bash Coding Online - Practice Bash Online - Execute Bash Online - Compile Bash Online - Run Bash Online (tutorialspoint.com)](https://www.tutorialspoint.com/unix_terminal_online.php)

  * [JS/UIX - Terminal (masswerk.at)](https://www.masswerk.at/jsuix/index.html) 用户名 guest





mkdir -p /usr/local/nginx
若防火墙开启，默认inux80端口访问不到，可让端口号开放

查看防火墙是否开启

​	systemctl status firewalld

​	systemctl restart  firewalld

查看开放的端口号

​	firewall-cmd --list-all

设置开放的端口号

firewall-cmd --zone=public  --add-port=80/tcp --permanent 

firewall-cmd  --add-service=http --permanent 

重启防火墙

​	firewall-cmd --reload

​	



这个配置文件一共由三部分组成，分别为**全局块、events块和http块**。在http块中，又包含http全局块、多个server块。每个server块中，可以包含server全局块和多个location块。 

配置文件支持大量可配置的指令，绝大多数指令不是特定属于某一个块的。 同一个指令放在不同层级的块中，其作用域也不同，一般情况下，高一级块中的指令可以作用于自身所在的块和此块包含的所有低层级块。如果某个指令在两个不同层级的块中同时出现，则采用“就近原则”，即以较低层级块中的配置为准。比如，某指令同时出现在http全局块中和server块中，并且配置不同，则应该以server块中的配置为准。 

整个配置文件的结构大致如下：

```shell
#全局块
#user  nobody;
worker_processes  1;

#event块
events {
    worker_connections  1024;
}

#http块
http {
    #http全局块
    include       mime.types;
    default_type  application/octet-stream;
    sendfile        on;
    keepalive_timeout  65;
    #server块
    server {
        #server全局块
        listen       8000;
        server_name  localhost;
        #location块
        location / {
            root   html;
            index  index.html index.htm;
        }
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
    }
    #这边可以有多个server块
    server {
      ...
    }
}
```

## 全局块

主要设置一些影响Nginx服务器整体运行的配置指令，因此，这些指令的作用域是Nginx服务器全局。 

通常包括配置运行Nginx服务器的用户（组）、允许生成的worker process数、Nginx进程PID存放路径、日志的存放路径和类型以及配置文件引入等。 

```shell
# 指定可以运行nginx服务的用户和用户组，只能在全局块配置
# user [user] [group]
# 将user指令注释掉，或者配置成nobody的话所有用户都可以运行
# user nobody nobody;
# user指令在Windows上不生效，如果你制定具体用户和用户组会报小面警告
# nginx: [warn] "user" is not supported, ignored in D:\software\nginx-1.18.0/conf/nginx.conf:2

# 指定工作线程数，可以制定具体的进程数，也可使用自动模式，这个指令只能在全局块配置
# worker_processes number | auto；
# 列子：指定4个工作线程，这种情况下会生成一个master进程和4个worker进程
# worker_processes 4;

# 指定pid文件存放的路径，这个指令只能在全局块配置
# pid logs/nginx.pid;

# 指定错误日志的路径和日志级别，此指令可以在全局块、http块、server块以及location块中配置。(在不同的块配置有啥区别？？)
# 其中debug级别的日志需要编译时使用--with-debug开启debug开关
# error_log [path] [debug | info | notice | warn | error | crit | alert | emerg] 
# error_log  logs/error.log  notice;
# error_log  logs/error.log  info;
```

##  Events

events块涉及的指令主要影响Nginx服务器与用户的网络连接。常用到的设置包括是否开启对多worker process下的网络连接进行序列化，是否允许同时接收多个网络连接，选取哪种事件驱动模型处理连接请求，每个worker process可以同时支持的最大连接数等。

这一部分的指令对Nginx服务器的性能影响较大，在实际配置中应该根据实际情况灵活调整。

```
# 当某一时刻只有一个网络连接到来时，多个睡眠进程会被同时叫醒，但只有一个进程可获得连接。如果每次唤醒的进程数目太多，会影响一部分系统性能。在Nginx服务器的多进程下，就有可能出现这样的问题。
# 开启的时候，将会对多个Nginx进程接收连接进行序列化，防止多个进程对连接的争抢
# 默认是开启状态，只能在events块中进行配置
# accept_mutex on | off;

# 如果multi_accept被禁止了，nginx一个工作进程只能同时接受一个新的连接。否则，一个工作进程可以同时接受所有的新连接。 
# 如果nginx使用kqueue连接方法，那么这条指令会被忽略，因为这个方法会报告在等待被接受的新连接的数量。
# 默认是off状态，只能在event块配置
# multi_accept on | off;

# 指定使用哪种网络IO模型，method可选择的内容有：select、poll、kqueue、epoll、rtsig、/dev/poll以及eventport，一般操作系统不是支持上面所有模型的。
# 只能在events块中进行配置
# use method
# use epoll

# 设置允许每一个worker process同时开启的最大连接数，当每个工作进程接受的连接数超过这个值时将不再接收连接
# 当所有的工作进程都接收满时，连接进入logback，logback满后连接被拒绝
# 只能在events块中进行配置
# 注意：这个值不能超过超过系统支持打开的最大文件数，也不能超过单个进程支持打开的最大文件数，具体可以参考这篇文章：https://cloud.tencent.com/developer/article/1114773
# worker_connections  1024;

```

## http

https://www.cnblogs.com/54chensongxia/p/12938929.html#:~:text=Nginx%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E8%AF%A6%E8%A7%A3.%20Nginx%E7%9A%84%E4%B8%BB%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E6%98%AFnginx.conf%EF%BC%8C%E8%BF%99%E4%B8%AA%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E4%B8%80%E5%85%B1%E7%94%B1%E4%B8%89%E9%83%A8%E5%88%86%E7%BB%84%E6%88%90%EF%BC%8C%E5%88%86%E5%88%AB%E4%B8%BA%20%E5%85%A8%E5%B1%80%E5%9D%97%E3%80%81events%E5%9D%97%E5%92%8Chttp%E5%9D%97%20%E3%80%82.%20%E5%9C%A8http%E5%9D%97%E4%B8%AD%EF%BC%8C%E5%8F%88%E5%8C%85%E5%90%ABhttp%E5%85%A8%E5%B1%80%E5%9D%97%E3%80%81%E5%A4%9A%E4%B8%AAserver%E5%9D%97%E3%80%82.,%E6%AF%8F%E4%B8%AAserver%E5%9D%97%E4%B8%AD%EF%BC%8C%E5%8F%AF%E4%BB%A5%E5%8C%85%E5%90%ABserver%E5%85%A8%E5%B1%80%E5%9D%97%E5%92%8C%E5%A4%9A%E4%B8%AAlocation%E5%9D%97%E3%80%82.%20%E5%9C%A8%E5%90%8C%E4%B8%80%E9%85%8D%E7%BD%AE%E5%9D%97%E4%B8%AD%E5%B5%8C%E5%A5%97%E7%9A%84%E9%85%8D%E7%BD%AE%E5%9D%97%EF%BC%8C%E5%90%84%E4%B8%AA%E4%B9%8B%E9%97%B4%E4%B8%8D%E5%AD%98%E5%9C%A8%E6%AC%A1%E5%BA%8F%E5%85%B3%E7%B3%BB%E3%80%82.%20%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6%E6%94%AF%E6%8C%81%E5%A4%A7%E9%87%8F%E5%8F%AF%E9%85%8D%E7%BD%AE%E7%9A%84%E6%8C%87%E4%BB%A4%EF%BC%8C%E7%BB%9D%E5%A4%A7%E5%A4%9A%E6%95%B0%E6%8C%87%E4%BB%A4%E4%B8%8D%E6%98%AF%E7%89%B9%E5%AE%9A%E5%B1%9E%E4%BA%8E%E6%9F%90%E4%B8%80%E4%B8%AA%E5%9D%97%E7%9A%84%E3%80%82.%20%E5%90%8C%E4%B8%80%E4%B8%AA%E6%8C%87%E4%BB%A4%E6%94%BE%E5%9C%A8%E4%B8%8D%E5%90%8C%E5%B1%82%E7%BA%A7%E7%9A%84%E5%9D%97%E4%B8%AD%EF%BC%8C%E5%85%B6%E4%BD%9C%E7%94%A8%E5%9F%9F%E4%B9%9F%E4%B8%8D%E5%90%8C%EF%BC%8C%E4%B8%80%E8%88%AC%E6%83%85%E5%86%B5%E4%B8%8B%EF%BC%8C%E9%AB%98%E4%B8%80%E7%BA%A7%E5%9D%97%E4%B8%AD%E7%9A%84%E6%8C%87%E4%BB%A4%E5%8F%AF%E4%BB%A5%E4%BD%9C%E7%94%A8%E4%BA%8E%E8%87%AA%E8%BA%AB%E6%89%80%E5%9C%A8%E7%9A%84%E5%9D%97%E5%92%8C%E6%AD%A4%E5%9D%97%E5%8C%85%E5%90%AB%E7%9A%84%E6%89%80%E6%9C%89%E4%BD%8E%E5%B1%82%E7%BA%A7%E5%9D%97%E3%80%82.%20%E5%A6%82%E6%9E%9C%E6%9F%90%E4%B8%AA%E6%8C%87%E4%BB%A4%E5%9C%A8%E4%B8%A4%E4%B8%AA%E4%B8%8D%E5%90%8C%E5%B1%82%E7%BA%A7%E7%9A%84%E5%9D%97%E4%B8%AD%E5%90%8C%E6%97%B6%E5%87%BA%E7%8E%B0%EF%BC%8C%E5%88%99%E9%87%87%E7%94%A8%22%E5%B0%B1%E8%BF%91%E5%8E%9F%E5%88%99%22%EF%BC%8C%E5%8D%B3%E4%BB%A5%E8%BE%83%E4%BD%8E%E5%B1%82%E7%BA%A7%E5%9D%97%E4%B8%AD%E7%9A%84%E9%85%8D%E7%BD%AE%E4%B8%BA%E5%87%86%E3%80%82.