

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