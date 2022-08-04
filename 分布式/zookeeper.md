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