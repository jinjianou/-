# 介绍&安装

A Distributed Coordination Service for Distributed Applications

1. The namespace provided by ZooKeeper is much like that of a standard file system

2. 主从复制

   

   ![ZooKeeper Service](assets/zkservice.jpg)

假设leader挂掉->服务就会不可用->不可靠的集群

事实,zk集群及其高可用->有一种方式可以快速恢复

![Reliability in the Presence of Errors](assets/zkperfreliability.jpg)

3,5 leader失败后快速恢复



3. 读写分离 且读越多性能越高

   

   ![ZooKeeper Throughput as the Read-Write Ratio Varies](assets/zkperfRW-3.2.jpg)

   

4. 节点

   zk是个目录树->节点->持久节点&临时节点(session) 两者都支持序列化

   不要把zk当作数据库使用(一般每个节点存储不超过1M,对外提供快速服务,减少网络带宽和通信时延)

5. 保证
   1. 顺序一致性(写) 主从模型-leader单机通过排队轻松实现
   2. 原子性 最终一致性而非强一致性
   3. 单系统镜像 主从模型同步
   4. 可靠性 持久化
   5. 及时性 最终一致性



![1659876034767](assets/1659876034767.png)

## 实例

1. 创建当前快照，转到虚拟机初始化状态（保证有jdk），并据此快照完整克隆3台主机

   vi /etc/sysconfig/network-scripts/ifcfg-ens33

   修改ip为192.168.162.151~154 

   source&重启查看是否生效

2. 安装zk

   wget https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.7.1/apache-zookeeper-3.7.1-bin.tar.gz

   **以下均为3.7.1版本结果**

3. cp zoo_sample.cfg zoo.cfg

4. 修改配置文件

   ```
   dataDir=/var/zookeeper
   
   server.1=zk_node01:2888:3888
   server.2=zk_node02:2888:3888
   server.3=zk_node03:2888:3888
   server.4=zk_node04:2888:3888
   ```

5. scp分发

   scp -r /opt/apache-zookeeper-3.7.1-bin/ root@zk_node02:`pwd`   此时的目录路径是/opt

6. 新建myid

   mkdir -p /var/zookeeper
   echo 1 > /var/zookeeper/myid 

   同样的把其他主机的myid同样配置好

7. 配置zk环境变量

8. node1->4依次启动，由于3个就可以投票选举leader（2个 looking），所以id最大的node3是leader,后面启动的4自动变成follower

   zkServer.sh start-foreground

   zkServer.sh status

   node1->3启动会报Connection refused (Connection refused)

9. 停止node3 此时node4会被选举为new leader



问题：ssh: Could not resolve hostname zk_node02: Name or service not known
解决： 
在/etc/hosts配置好主机名和ip的映射，并分发到其他主机
scp /etc/hosts root@zk_node02:/etc/


# 配置文件详解

### 最低配置

| 参数名                                | 默认    | 描述                                                         |
| ------------------------------------- | ------- | ------------------------------------------------------------ |
| clientPort                            |         | 服务的监听端口                                               |
| dataDir                               |         | 用于存放内存数据快照的文件夹，同时用于集群的myid文件也存在这个文件夹里 |
| tickTime                              | 2000    | Zookeeper的时间单元。**Zookeeper中所有时间都是以这个时间单元的整数倍去配置的。**例如，session的最小超时时间是2*tickTime。（单位：毫秒） |
| **dataLogDir**                        |         | 事务日志写入该配置指定的目录，而不是“ dataDir ”所指定的目录。这将允许使用一个专用的日志设备并且帮助我们避免日志和快照之间的竞争 |
| globalOutstandingLimit                | 1000    | 最大请求堆积数。默认是1000。Zookeeper运行过程中，尽管Server没有空闲来处理更多的客户端请求了，但是还是允许客户端将请求提交到服务器上来，以提高吞吐性能。当然，为了防止Server内存溢出，这个请求堆积数还是需要限制下的。 |
| preAllocSize                          | 64M     | 预先开辟磁盘空间，用于后续写入事务日志。默认是64M，每个事务日志大小就是64M。如果ZK的快照频率较大的话，建议适当减小这个参数。 |
| snapCount                             | 100,000 | 每进行snapCount次事务日志输出后，触发一次快照， 此时，Zookeeper会生成一个snapshot.*文件，同时创建一个新的事务日志文件log.*。默认是100,000. |
| raceFile                              |         | 用于记录所有请求的log，一般调试过程中可以使用，但是生产环境不建议使用，会严重影响性能。 |
| maxClientCnxns                        | 10      | 最大并发客户端数，用于防止DOS的，默认值是10，设置为0是不加限制 |
| clientPortAddress / maxSessionTimeout |         | 对于多网卡的机器，可以为每个IP指定不同的监听端口。默认情况是所有IP都监听 clientPort 指定的端口 |
| minSessionTimeout                     |         | Session超时时间限制，如果客户端设置的超时时间不在这个范围，那么会被强制设置为最大或最小时间。默认的Session超时时间是在2 *  tickTime ~ 20 * tickTime 这个范围 |
| fsync.warningthresholdms              | 1000ms  | 事务日志输出时，如果调用fsync方法超过指定的超时时间，那么会在日志中输出警告信息。默认是1000ms。 |
| autopurge.snapRetainCount             |         | 参数指定了需要保留的事务日志和快照文件的数目。默认是保留3个。和autopurge.purgeInterval搭配使用 |
| autopurge.purgeInterval               |         | 在3.4.0及之后版本，Zookeeper提供了自动清理事务日志和快照文件的功能，这个参数指定了清理频率，单位是小时，需要配置一个1或更大的整数，默认是0，表示不开启自动清理功能 |
| syncEnabled                           |         | Observer写入日志和生成快照，这样可以减少Observer的恢复时间。默认为true。 |
|                                       |         |                                                              |
|                                       |         |                                                              |
|                                       |         |                                                              |
|                                       |         |                                                              |



### 集群选项

参数名  默认  描述

| 参数名                            | 默认   | 描述                                                         |
| --------------------------------- | ------ | ------------------------------------------------------------ |
| electionAlg                       |        | 之前的版本中， 这个参数配置是允许我们选择leader选举算法，但是由于在以后的版本中，只有“FastLeaderElection ”算法可用，所以这个参数目前看来没有用了。 |
| initLimit                         | 10**** | **Observer和Follower启动时，从Leader同步最新数据时，Leader允许initLimit * tickTime的时间内完成**。如果同步的数据量很大，可以相应的把这个值设置的大一些。 |
| leaderServes                      | yes    | 默 认情况下，Leader是会接受客户端连接，并提供正常的读写服务。但是，如果你想让Leader专注于集群中机器的协调，那么可以将这个参数设置为 no，这样一来，会大大提高写操作的性能。一般机器数比较多的情况下可以设置为no，让Leader不接受客户端的连接。默认为yes |
| server.x=[hostname]:nnnnn[:nnnnn] |        | **“x”是一个数字，与每个服务器的myid文件中的id是一样的。hostname是服务器的hostname，右边配置两个端口，第一个端口用于Follower和Leader之间的数据同步和其它通信，第二个端口用于Leader选举过程中投票通信**。 |
| syncLimit                         |        | 表示Follower和Observer与Leader交互时的最大等待时间，只不过是**在与leader同步完毕之后，进入正常请求转发或ping等消息交互时的超时时间** |
| group.x=nnnnn[:nnnnn]             |        | “x”是一个数字，与每个服务器的myid文件中的id是一样的。对机器分组，后面的参数是myid文件中的ID |
| weight.x=nnnnn                    |        | “x”是一个数字，与每个服务器的myid文件中的id是一样的。机器的权重设置，后面的参数是权重值 |
| cnxTimeout                        | 5s     | 选举过程中打开一次连接的超时时间，默认是5s                   |
| standaloneEnabled                 |        | 当设置为false时，服务器在复制模式下启动                      |
|                                   |        | https://blog.csdn.net/qianshangding0708/article/details/50067483 |







认证和授权选项
只有在3.2之后才支持。

参数名

默认

描述

 

zookeeper.DigestAuthenticationProvider.superDigest

disabled	启用超级管理员的用户去访问znode.
可 以使用org.apache.zookeeper.server.auth.DigestAuthenticationProvider来生成一个 superDigest，参数格式为："super:<password>"，一旦当前连接addAuthInfo超级用户验证通过，后续所 有操作都不会checkACL。
实验选项/功能
Read Only Mode Server
    (java系统属性: readonlymode.enabled)
    在3.4.0之后支持，设置这个值为true，就支持只读模式。
不安全选项
以下的变量都是非常有用的，但是使用的时候还是要注意。

参数名

默认

描述

forceSync


该参数确定了是否需要在事务日志提交的时候调用 FileChannel .force来保证数据完全同步到磁盘。对应的java系统属性：zookeeper.forceSync

jute.maxbuffer

 	该参数只能设置为java系统属性。没有zookeeper前缀。它指定了Znode可以存储最大的数据量的大小。默认是1M。如果要改变该配置，就必须在所有服务器和客户端中设置。
skipACL

 

跳过ACL检查，这样可以是Zookeeper的吞吐量增加。只是会使所有用户都有访问权限。对应的java系统属性：zookeeper.skipACL

quorumListenOnAllIPs

false

该参数设置为true，Zookeeper服务器将监听所有可用IP地址的连接。他会影响ZAB协议和快速Leader选举协议。默认是false。

性能调整选项
只有在3.5.0之后才支持。

参数名

默认

描述

zookeeper.nio.numSelectorThreads


NIO选择器的线程数量。建议使用多个选择器线程来扩大客户端的连接数，默认值是（CPU核心数/2）

 

zookeeper.nio.numWorkerThreads


NIO工作线程数。如果工作线程数设置为0，那么选择器线程就可以直接输出。默认值是（CPU核心数 * 2）

zookeeper.commitProcessor.numWorkerThreads

 

提交处理器工作线程数。如果该工作线程数设置为0，那么主线程就直接处理请求。默认是（CPU核心数）

AdminServer配置
AdminServer只有在3.5.0之后才支持。

参数名

默认

描述

admin.enableServer

true

设置为“false”禁用AdminServer。默认情况下，AdminServer是启用的。对应java系统属性是：zookeeper.admin.enableServer

admin.serverPort

8080

Jetty服务的监听端口，默认是8080。对应java系统属性是：zookeeper.admin.serverPort

admin.commandURL

"/commands"

访问路径







# zkCli 命令

```shell
addWatch [-m mode] path # optional mode is one of [PERSISTENT, PERSISTENT_RECURSIVE] - default is PERSISTENT_RECURSIVE
	addauth scheme auth
	close 
	config [-c] [-w] [-s]
	connect host:port
	create [-s] [-e] [-c] [-t ttl] path [data] [acl]
	delete [-v version] path
	deleteall path [-b batch size]
	delquota [-n|-b|-N|-B] path
	get [-s] [-w] path
	getAcl [-s] path
	getAllChildrenNumber path
	getEphemerals path
	history 
	listquota path
	ls [-s] [-w] [-R] path
	printwatches on|off
	quit 
	reconfig [-s] [-v version] [[-file path] | [-members serverID=host:port1:port2;port3[,...]*]] | [-add serverId=host:port1
:port2;port3[,...]]* [-remove serverId[,...]*]	redo cmdno
	removewatches path [-c|-d|-a] [-l]
	set [-s] [-v version] path data
	setAcl [-s] [-v version] [-R] path acl
	setquota -n|-b|-N|-B val path
	stat [-w] path
	sync path
	version 
	whoami
```

跟linux命令用法一致



1. get 

   老版除了存储的数据，还包含

   - cZxid=0x200000002

     创建节点的事务id,后32位表示步进器（即第几次操作 c,m,p），前32表示leader的纪元（第几个leader）

   - mZxid modify

   - pZxid 当前节点下最大的create事务id

     注意：sessionId的创建和销毁都会占用事务id，也就是说不仅仅zk节点是统一视图（每个客户端看到的节点数据都一致），client上的sessionId也会随着关联的follower变化而变化

2. create

   -e  ephemeral

   ephemeralOwner 0x0  表示没有临时归属 即持久节点

   ephemeralOwner sessionId 



   -s  sequence

  多个客户端写同一 个文件， file0...01  file0...02 以此类推

1. 

## ACL

访问控制列表ACL（Access Control List）是由一条或多条规则组成的集合。所谓规则，是指描述报文匹配条件的判断语句，这些条件可以是报文的源地址、目的地址、端口号等。

ACL本质上是一种报文过滤器，规则是过滤器的滤芯。设备基于这些规则进行报文匹配，可以过滤出特定的报文，并根据应用ACL的业务模块的处理策略来允许或阻止该报文通过。