# Redis

## 安装

* 去redis.io 下载redis.tar.gz

* 解压后，进入redis目录

* yum install -y gcc-c++

* make 

  *GNU Make* is a tool which controls the generation of executables and other non-source files of a program from the program's source files. 

* make install

* 默认安装路径 /usr/local    二进制文件路径在/usr/local/bin

* 备份redis.conf  cp /opt/redis-6.2.6/redis.conf jinjianou/

* 修改成后台运行 jinjianou/redis.conf  daemonize no->daemonize yes

  redis-server jinjianou/redis.conf  			shutdown

  redis-cli -p 6379

  

  ### benchmark	

  ![1](C:\Users\Administrator\Desktop\复习\素材\pic\redis\1.jpg)

## 介绍

Redis 是一个开源（BSD许可）的，**内存**中的**数据结构存储**系统，它可以用作**数据库、缓存和消息中间件**。 它支持多种类型的数据结构，如 [字符串（strings）](http://redis.cn/topics/data-types-intro.html#strings)， [散列（hashes）](http://redis.cn/topics/data-types-intro.html#hashes)， [列表（lists）](http://redis.cn/topics/data-types-intro.html#lists)， [集合（sets）](http://redis.cn/topics/data-types-intro.html#sets)， [有序集合（sorted sets）](http://redis.cn/topics/data-types-intro.html#sorted-sets) 与范围查询， [bitmaps](http://redis.cn/topics/data-types-intro.html#bitmaps)， [hyperloglogs](http://redis.cn/topics/data-types-intro.html#hyperloglogs) 和 [地理空间（geospatial）](http://redis.cn/commands/geoadd.html) 索引半径查询。 Redis 内置了 [复制（replication）](http://redis.cn/topics/replication.html)，[LUA脚本（Lua scripting）](http://redis.cn/commands/eval.html)， [LRU驱动事件（LRU eviction）](http://redis.cn/topics/lru-cache.html)，[事务（transactions）](http://redis.cn/topics/transactions.html) 和不同级别的 [磁盘持久化（persistence）](http://redis.cn/topics/persistence.html)， 并通过 [Redis哨兵（Sentinel）](http://redis.cn/topics/sentinel.html)和自动 [分区（Cluster）](http://redis.cn/topics/cluster-tutorial.html)提供高可用性（high availability）。 

**不区分大小写命令**

![2](C:\Users\Administrator\Desktop\复习\素材\pic\redis\2.jpg)

## 数据类型（基本）

* Redis-key

  The empty string is also a valid key ，The maximum allowed key size is 512 MB 

  * flushall(clear all db) flushdb(clear current db )

  * keys *

    info keyspace 所有db key的数量

    keys *统计的是当前db有效的key，而dbsize统计的是所有未被销毁的key（有效和未被销毁是不一样的，具体可以了解[redis](http://www.itdaan.com/keywords/Redis查询当前库有多少个+key.html)的过期策略）

  * exists key

  * move key db

  * expire key sencond

  * ttl key(Time To Live )
    
    * -1 未设置过期时间 -2已过期 not exists

* Binary-safe strings （this means that you can use any binary sequence ） Map<String,String> 

  * set key value

  * get key

  * append key value        :length

  * strlen key

  * incr key    incrby key step

  * decr key   decr key step

  * getrang key start end(can be negative)   [start,end]  start step by 0 

  * setrange key offset value  :length(覆盖操作)

  * setex key expire value

  * **setnx(set if not exists) key value  unsuccess  if exists**

  * mset  key value[key value]

  * mget key[key]

  * **msetnx key value[key value] 原子性操作**

  * getset key value   set if key is not exists    get if key exists

  * **:的使用**

    **set user:1 {name:zhangsan,age:18}  key是user:1 value是json字符串**

* Lists: collections of string elements **sorted according to the order of insertion**. They are basically ***linked lists(implements deque)*.** Map<String,LinkedList>   

  * lpush key  value [value]                  left
  * lrange  key start end            left->right
  * rpush ey  value [value]               right
  * lpop key  [count]
  * rpop   key   [count]                                right    brpop设置timeout
  * lindex key index
  * llen key
  * lrem key count value
  * ltrim key start end：list变成key[start :end]
  * rpoplpush key1 key2   pop the right of key1 into left of  key2 若key1就一个元素 操作便不存在
  * exists key
  * lset key index value :   数组或下标不存在报错
  * linsert key before|after element value

* Sets: collections of unique, unsorted **string elements**  Map<String,Set>  

  * sadd key  member [member ...]
  * sismember key member
  * scard  key                       获取set元素数量
  * srem   key member [member ...]
  * srandmember key [count]
  * smembers key
  * spop  key [count]  随机删除
  * smove source destination member
  * sdiff key1 [key...]  获取key1不在后续key union  中的元素
  * sinter key [key...]
  * sunion key [key...]

* Sorted sets, similar to Sets but where every string element is associated to a floating number value, called *score*. The elements are always taken sorted by their score, so unlike Sets it is possible to retrieve （尝试）a range of elements 

  或者是zset z代表xyzsets with another dimension 

  Map<String,Set+z> Set<JavaBean>  String element,Double score

  * zadd key score member

  * zrange key min max [withScores]  从小到大

    0 -1

  * zrangeByScore key min max \[withScores][limit offset count] 从小到大

    -inf +inf

    (1 5    --> (1,5]

    (1 (5    --> (1,5)

    zrangeByScore zset1 -inf +inf withScores limit 0 1

  * zrem key member [member ...]

  * zcard key

  * zrevrange key start stop [WITHSCORES] 从大到小

  * zrevrangeByscore key **max min** \[withScores][limit offset count]

  * zcount key min max

* Hashes, which are maps composed of fields associated with values. Both the field and the value are strings.  Map<String,Map<String,String>>

  * hset key field value[field value...]

  * hget  key  field

  * hmset key field value[field value...]

  * hmget key  field [field...]

  * hgetall key

  * hdel key field[field]

  * hlen key

  * hexists key field

  * hkeys key

  * hvals key

  * hincrby key field step

  * hsetnx

    

  redis所有的set族都是有则覆盖 无则新建

  

## 三种特殊数据类型

* Bit arrays (or simply bitmaps): it is possible, using special commands, to handle String values like an array of bits: you can set and clear individual bits, count all the bits set to 1, find the first set or unset bit, and so forth.
* HyperLogLogs: this is a probabilistic data structure which is used in order to estimate the cardinality(基数) of a set 
* geospatial 
* Streams: append-only collections of map-like entries that provide an abstract log data type. 

## geospatial 地理位置

> geoadd

geoadd key  longitude经度,latitude纬度,member名称  经纬度写错 可能会报错

> geopos 获取经纬度

geopos key memeber[member....]

> geodist 返回两个给定位置之间的距离

geodist key member1 member2 [m/km/mi/ft]  默认单位m

> georadius 以给顶的经纬度为中心 找出某半径的元素

georadius key  longitude经度,latitude纬度 radius \[m/km/mi/ft][withcoord]

\[withdist] [count n]  

georadius china:city 118 35 1000 km withcoord withdist count 1

> georadiusbymember 同georadius  只是中心点由坐标变成元素

> geohash 返回一个或多个位置元素的geohash表示 返回11个字符的串

geohash key member[member ...]



**geo本质是基于zset实现的** 于是可以使用zset的api  score是欧式



## Hypeloglog 基数统计(不重复)

集合中元素的个数叫基数 (刻画集合的大小)

**允许一定的容错**

> pfadd 增加元素

pfadd key member[member....]

> pfcount 统计元素个数

pfcount key

> pfmerge 合并元素集合

pfmerge destkey sourcekey [sourcekey ...]



**本质是一个set,但不能使用set的api**

### BitMap位图 

* setbit key offeset value

* getbit key offeset 

* bitcount key [start end] The number of bits set to 1 

  ```shell
  redis> SET mykey "foobar"
  OK
  redis> BITCOUNT mykey //根据字节分析 f 二进制有 4个1 一次类推
  (integer) 26
  redis> BITCOUNT mykey 0 0  //获取第一个字节 即f
  (integer) 4
  redis> BITCOUNT mykey 1 1 //获取第二个字节 即o
  (integer) 6
  
  ```

  



## 数据一致性

![ ](.\Redis.assets\image-20220802102551766.png)

　同步异步是针对**调用者**来说的，调用者发起一个请求后，**一直干等**被调用者的反馈就是同步，**不必等去做别的事**就是异步。

　　阻塞非阻塞是针对**被调用者**来说的，被调用者收到一个请求后，**做完**请求任务后才给出反馈就是阻塞，收到请求**直接给出反馈**再去做任务就是非阻塞。



1. **数据的分布式存储是导致出现一致性的唯一原因**





## 事务

* **Redis单条命令保证原子性，但Redis事务不保证原子性**

* **没有隔离级别的概念**

* 一个事务中的所有命令都会被序列化且在执行过程中按照顺序执行且无法被其他事务干扰。**所有命令在事务中，并没有直接被执行，只有在发起执行命令的时候才会执行**Exec

  1. 开启事务（multi）
  2. 命令入队
  3. 执行事务(Exec)

  取消事务 discard 之前多有命令都不会生效

  异常类型：

  1. 命令错误 事务中存在错误命令则其他所有命令都不会被执行

  2. 运行时异常 语法没问题 ，**除这条命令外其他命令都会被正常执行**

     （比如 incr string）

* 测试多线程更新数据，使用watch可以当作redis的乐观锁（对事务生效）

  **事务的一次监控执行结束后(包括失败导致的) watch观测的keys失效**

  如果执行过 [EXEC](https://www.redis.com.cn/commands/exec.html) 或`DISCARD`（会取消所有对 key 的监视 ），无需再执行 [UNWATCH](https://www.redis.com.cn/commands/unwatch.html) 

## Redis.conf



1. memory size   unit单位大小写不敏感

2. 可以包含其他配置文件

3. 网络

   bind `127.0.0.1 \#绑定ip

   protected-mode yes \#保护模式

   port 6379 #端口设置

4. general

   daemonize yes #守护进程

   supervised

   * If you run Redis from upstart or systemd, Redis can interact with your supervision tree

   * The supervision tree is a hierarchical arrangement(登记安排) of an application into workers and supervisors

   pidfile /var/run/redis_6379.pid #如果以守护进程的方式运行 则需要指定pid文件

   日志：

   databases 16  

   * Set the number of database

   always-show-logo no  

   * normally a logo is displayed only in interactive sessions

5. snapshot（rdb）

   Save the DB to disk 持久化

   save <seconds> <changes>

   ```shell
   # save 3600 1
   # save 300 100
   # save 60 10000
   
   # * After 3600 seconds (an hour) if at least 1 key changed
   #   * After 300 seconds (5 minutes) if at least 100 keys changed
   #   * After 60 seconds if at least 10000 keys changed
   
   ```

   ```shell
   # By default Redis will stop accepting writes if RDB snapshots are enabled# (at least one save point) and the latest background save failed.
   # This will make the user aware (in a hard way) that data is not persisting# on disk properly, otherwise chances are that no one will notice and some# disaster will happen.
   #
   # If the background saving process will start working again Redis will# automatically allow writes again.
   #
   # However if you have setup your proper monitoring of the Redis server and persistence, you may want to disable this feature so that Redis will continue to work as usual even if there are problems with disk permissions, and so forth(等等)
   stop-writes-on-bgsave-error yes
   ```

   **you may want to disable this feature so that Redis will continue to work as usual even if there are problems with disk permissions**

   

   Compress string objects using LZF when dump .rdb databases

   **rdbcompression yes**

   

   Since version 5 of RDB a CRC64 checksum is placed at the end of 
   the file.RDB files created with checksum enabled

   **rdbchecksum yes**

   

   Enables or disables full sanitation checks for ziplist and listpack etc when loading an RDB or RESTORE payload

   **dbfilename dump.rdb**

   

   The working directory.The DB will be written inside this directory, with the filename specified above using the 'dbfilename' configuration directive

   **dir ./**

6. replication（复制） 主从复制

   ```shell
   # Master-Replica replication. Use replicaof to make a Redis instance a copy of
   # another Redis server. A few things to understand ASAP about Redis replication.
   #
   #   +------------------+      +---------------+
   #   |      Master      | ---> |    Replica    |
   #   | (receive writes) |      |  (exact copy) |
   #   +------------------+      +---------------+
   #
   # 1) Redis replication is asynchronous, but you can configure a master to
   #    stop accepting writes if it appears to be not connected with at least
   #    a given number of replicas.
   # 2) Redis replicas are able to perform a partial resynchronization with the
   #    master if the replication link is lost for a relatively small amount of
   #    time. You may want to configure the replication backlog size (see the next
   #    sections of this file) with a sensible value depending on your needs.
   # 3) Replication is automatic and does not need user intervention. After a
   #    network partition replicas automatically try to reconnect to masters
   #    and resynchronize with them.
   #
   # replicaof <masterip> <masterport>
   
   # If the master is password protected (using the "requirepass" configuration
   # directive below) it is possible to tell the replica to authenticate before
   # starting the replication synchronization process, otherwise the master will
   # refuse the replica request.
   #
   # masterauth <master-password>
   #
   # However this is not enough if you are using Redis ACLs (for Redis version
   # 6 or greater), and the default user is not capable of running the PSYNC
   # command and/or other commands needed for replication. In this case it's
   # better to configure a special user to use with replication, and specify the
   # masteruser configuration as such:
   #
   # masteruser <username>
   #
   # When masteruser is specified, the replica will authenticate against its
   # master using the new AUTH form: AUTH <username> <password>.
   
   # When a replica loses its connection with the master, or when the replication
   # is still in progress, the replica can act in two different ways:
   #
   # 1) if replica-serve-stale-data is set to 'yes' (the default) the replica will
   #    still reply to client requests, possibly with out of date data, or the
   #    data set may just be empty if this is the first synchronization.
   #
   # 2) If replica-serve-stale-data is set to 'no' the replica will reply with
   #    an error "SYNC with master in progress" to all commands except:
   #    INFO, REPLICAOF, AUTH, PING, SHUTDOWN, REPLCONF, ROLE, CONFIG, SUBSCRIBE,
   #    UNSUBSCRIBE, PSUBSCRIBE, PUNSUBSCRIBE, PUBLISH, PUBSUB, COMMAND, POST,
   #    HOST and LATENCY.
   #
   replica-serve-stale-data yes
   
   ```

   

7. security

   *  config get requirepass 默认没密码   
   * config set  requirepass  "" 临时(以服务为单位)
   * auth password //登陆

8. clients

   * maxClients 10000 连接redis的最大客户端数量

9. memory management

   * maxmemory <bytes>

   * **maxmemory-policy noeviction** 

     **how Redis will select what to remove when maxmemory is reached**

     ```java
     # volatile-lru：只对设置了过期时间的key进行LRU
     # allkeys-lru ： 删除lru算法的key   
     # volatile-random：随机删除即将过期key   
     # allkeys-random：随机删除   
     # volatile-ttl ： 删除即将过期的   
     # noeviction（驱逐） ： 永不过期，返回错误
     ```

10. Append only mode

    * appendonly no 默认不开启AOF

    * no-appendfsync-on-rewrite 

      * 如果该参数设置为no，是最安全的方式，不会丢失数据，但是要忍受阻塞的问题 (bgrewriteaof往往会涉及大量磁盘操作 )
      * yes 不会造成阻塞 但是如果这个时候redis挂掉，就会丢失数据 ,在linux的操作系统的默认设置下，最多会丢失30s的数据。 

    * appendfilename "appendonly.aof"  

      * The name of the append only file

    * ```shell
      # appendfsync always 每次有修改
      appendfsync everysec   并完成磁盘同步
      # appendfsync no
      
      
      # The default is "everysec", as that's usually the right compromise(妥协) between
      # speed and data safety. It's up to（取决于） you to understand if you can relax this to
      # "no" that will let the operating system flush the output buffer when
      # it wants, for better performances (but if you can live with the idea of
      # some data loss consider the default persistence mode that's snapshotting),
      # or on the contrary, use "always" that's very slow but a bit safer than
      # everysec.
      ```

      * The fsync() call tells the Operating System to actually write data on disk instead of waiting for more data in the output buffer

      * right compromise(妥协) between speed and data safety.

      * 为什么缓冲能提升性能

        * 所有存储设备都有IOps （IO per Second） 瓶颈	

          * 缓存的作用之一就是把**零散的IO合并成大块的IO**，降低IO请求的个数，提高性能 
          * 预读和数据重用 
          * 合并读写

        * 内存地址对齐，是一种在计算机内存中排列数据、访问数据的一种方式，包含了两种相互独立又相互关联的部分：基本数据对齐和结构体数据对齐。当今的计算机在计算机内存中读写数据时都是按字(word)大小块来进行操作的(**在32位系统中**，**数据总线宽度为32，每次能读取4字节**，地址总线宽度为32，因此最大的寻址空间为2^32=4GB，**但是最低2位A[0],A[1]是不用于寻址**，A[2-31]才能存储器相连，**因此只能访问4的倍数地址空间**，但是总的寻址空间还是2^30*字长=4GB，因此在内存中所有存放的基本类型数据的首地址的最低两位都是0，除结构体中的成员变量)。基本类型数据对齐就是数据在内存中的偏移地址必须等于一个字的倍数，按这种存储数据的方式，可以提升系统在读取数据时的性能。为了对齐数据，可能必须在上一个数据结束和下一个数据开始的地方插入一些没有用处字节，这就是结构体数据对齐。

          ​      举个例子，假设计算机的字大小为4个字节，因此变量在内存中的首地址都是满足4地址对齐，CPU只能对4的倍数的地址进行读取，而每次能读取4个字节大小的数据。假设有一个整型的数据a的首地址不是4的倍数(如下图所示)，不妨设为0X00FFFFF3，则该整型数据存储在地址范围为0X00FFFFF3~0X00FFFFF6的存储空间中，而CPU每次只能对4的倍数内存地址进行读取，因此想读取a的数据，CPU要分别在0X00FFFFF0和0X00FFFFF4进行两次内存读取，而且还要对两次读取的数据进行处理才能得到a的数据，而一个程序的瓶颈往往不是CPU的速度，而是取决于内存的带宽，因为CPU得处理速度要远大于从内存中读取数据的速度，因此减少对内存空间的访问是提高程序性能的关键。从上例可以看出，采取内存地址对齐策略是提高程序性能的关键

        * buffer 提高**内存和硬盘（其他IO设备）**之间的数据交换的速度而设计

          cache 提高**cpu和内存**之间的数据交换速度而设计

        

## Redis持久化(只做缓存不需要任何持久化)

Redis内存数据库,断电即失,所以需要持久化

### RDB（redis database）

![9](C:\Users\Administrator\Desktop\复习\素材\pic\redis\9.jpg)

* 在指定的时间间隔内将内存中的数据集快照写入磁盘,也就是snapshot快照,恢复时将快照文件读到内存中

  redis会单独创建(fork)一个子进程,会将数据先写入一个临时文件中,待持久化的过程结束了,再用这个临时文件替换上次持久化好的文件.这个过程中,朱进程不进行任何I/O操作

  **dump.rdb** 生产环境可能会对备份

  save保存对配置文件的修改

* 在操作系统的基本概念中进程是程序的一次执行，且是拥有资源的最小单位和调度单位（在引入线程的操作系统中，线程是最小的调度单位）。在Linux系统中创建进程有两种方式：一是由操作系统创建，二是**由父进程创建进程（通常为子进程）。系统调用函数fork()是创建一个新进程的唯一方式**，当然vfork()也可以创建进程，但是实际上其还是调用了fork()函数。[fork()函数](https://www.zhihu.com/search?q=fork%28%29%E5%87%BD%E6%95%B0&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra=%7B%22sourceType%22%3A%22article%22%2C%22sourceId%22%3A130873706%7D)是Linux系统中一个比较特殊的函数，其一次调用会有两个返回值，下面是fork()函数的声明：

  ```
  #include <unistd.h>
  // On success, The PID of the process is returned in the parent, and 0 is returned in the child. On failure,
  // -1 is returned in the parent, no child process is created, and errno is set appropriately.
  
  pid_t fork (void);
  ```

  **fork success parent return The PID of the childProcess,child return 0**

  ​	**failure  parent return -1 ,no child process is created**

  当程序调用fork()函数并返回成功之后，程序就将变成两个进程，调用fork()者为父进程，后来生成者为子进程。这两个进程将执行相同的程序文本，但却各自拥有不同的栈段、数据段以及堆栈拷贝。子进程的栈、数据以及栈段开始时是父进程内存相应各部分的完全拷贝，因此它们互不影响。从性能方面考虑，父进程到子进程的数据拷贝并不是创建时就拷贝了的，而是采用了写时拷贝（[copy-on -write](https://www.zhihu.com/search?q=copy-on+-write&search_source=Entity&hybrid_search_source=Entity&hybrid_search_extra=%7B%22sourceType%22%3A%22article%22%2C%22sourceId%22%3A130873706%7D)）技术来处理。调用fork()之后，父进程与子进程的执行顺序是我们无法确定的（即调度进程使用CPU），意识到这一点极为重要，因为在一些设计不好的程序中会导致资源竞争，从而出现不可预知的问题。

**触发机制**:

1.满足save条件

2.flushall(生成默认rdb)

3.启动/退出redis或修改后save（如果没有）



**恢复rdb文件**

将dump.db放在redis启动目录下 redis启动的时候会自动检查dump.rdb文件恢复数据

**优点**:

1)适合大规模的对数据完整性要求不高数据恢复

**缺点**

1 需要一定的时间间隔操作(save seconds changes）所以如果redis宕机了，并没有成功触发最后一次rdb save，这部分数据就会丢失

2 fork进程的时候 会占有一定的内存空间



### AOF(append only file)

<img src="C:\Users\Administrator\Desktop\复习\素材\pic\redis\8.jpg" alt="8" style="zoom:200%;" />

**将所有写命令记录下来 history ,恢复的时候就把这个文件再执行一遍**

appendonly.aof

默认不开启

 redis-check-aof 检查修复文件

./redis-check-aof --fix appendonly.aof 

优点:

每次修改都会同步,文件的完整性更好

最多丢失1s的数据

缺点：

aof文件远大于rdb 恢复速度慢

aof有大量写操作 运行效率也低于rdb

rewrite 如果aof文件大于64mb，fork一个新的进程进行重写



## Jedis

Redis官方推荐的java连接工具

maven导包会把它直接和间接依赖的都导入

- slf4j仅仅是一个为Java程序提供日志输出的**统一接口，并不是一个具体的日志实现方案**，就比如JDBC一样，只是一种规则而已

  单独使用 比如log4j2 只用导入log4j2核心包

  需要同时使用slf4j和log4j，需要引入桥接包

  log4j-slf4j-impl-2.7.jar 使用slf4j的api，但是底层实现是基于log4j2.

  slf4j-log4j12-1.6.1.jar 使用slf4j的api，但是底层实现是基于log4j.

- SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
  SLF4J: Defaulting to no-operation (NOP) logger implementation
  SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.

  原因：缺少slf4j.impl 导入个桥接包即可

  需要同时配置日志输出，如log4j-slf4j-impl-2.7.jar对应log4j2

  需要在**resources新建log4j2.xml**

  ```
  <?xml version="1.0" encoding="UTF-8"?>
  <Configuration status="OFF" monitorInterval="1800">
      <!-- 该配置文件只可修改 baseLogPath 和 applicationName 其他值请勿动    -->
      <Properties>
          <Property name="rootLoggerLevel">INFO</Property>
          <Property name="bizLoggerLevel">INFO</Property>
          <Property name="warnLoggerLevel">WARN</Property>
          <Property name="exceptionLoggerLevel">ERROR</Property>
          <!-- 日志根目录 -->
          <Property name="baseLogPath">logs/</Property>
          <!-- 日志格式 -->
          <Property name="logPattern">%d{yyyy-MM-dd HH:mm:ss:SSS} - %p - %msg%ex%n</Property>
          <Property name="applicationName">applicationName</Property>
      </Properties>
      <Appenders>
          <!-- 控制台输出，请在调试环境下使用 -->
          <Console name="Console" target="SYSTEM_OUT">
              <PatternLayout pattern="${logPattern}"/>
          </Console>
          <!-- 日志信息输出到文件配置 -->
          <RollingFile name="RollingFileInfo" fileName="${baseLogPath}/${applicationName}.log"
                       filePattern="${baseLogPath}/INFO/app-%d{yyyy-MM-dd}-%i.log.gz">
              <PatternLayout pattern="${logPattern}" />
              <Policies>
                  <TimeBasedTriggeringPolicy />
                  <SizeBasedTriggeringPolicy size="50 MB"/>
              </Policies>
              <DefaultRolloverStrategy max="100">
                  <Delete basePath="${baseLogPath}" maxDepth="2">
                      <IfFileName glob="*/app-*.log.gz">
                          <IfLastModified age="7d">
                          </IfLastModified>
                      </IfFileName>
                  </Delete>
              </DefaultRolloverStrategy>
          </RollingFile>
  
          <RollingFile name="RollingFileError" fileName="${baseLogPath}/error.log"
                       filePattern="${baseLogPath}/error/error-%d{yyyy-MM-dd}-%i.log.gz">
              <PatternLayout pattern="${logPattern}" />
              <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
              <Policies>
                  <!--每天分割一次-->
                  <TimeBasedTriggeringPolicy />
                  <!--文件每满50MB分割一次-->
                  <SizeBasedTriggeringPolicy size="50 MB"/>
              </Policies>
  
              <DefaultRolloverStrategy max="100">
                  <Delete basePath="${baseLogPath}" maxDepth="2">
                      <IfFileName glob="*/error-*.log.gz">
                          <IfLastModified age="7d">
                          </IfLastModified>
                      </IfFileName>
                  </Delete>
              </DefaultRolloverStrategy>
          </RollingFile>
  
      </Appenders>
  
      <Loggers>
          <logger name="com.mmtrix.dao" level="debug" additivity="false">
              <appender-ref ref="Console"></appender-ref>
          </logger>
          <Root level="${rootLoggerLevel}">
              <appender-ref ref="Console"/>
              <appender-ref ref="RollingFileInfo"/>
              <appender-ref ref="RollingFileError"/>
          </Root>
      </Loggers>
  </Configuration>
  ```

- 代码块 ctrl+alt+t

### 测试

- ```
  Jedis jedis = new Jedis("127.0.0.1", 6379);
  ```

- ```java
  Jedis jedis = new Jedis("127.0.0.1", 6379);
          jedis.flushDB();
  
          Transaction tx = jedis.multi();
          try {
              JSONObject jo = new JSONObject();
              jo.put("name","jin");
              jo.put("age",18);
              tx.set("user1",jo.toJSONString());
              tx.set("user2",jo.toJSONString());
  //            int i=1/0;
              tx.exec();
          } catch (Exception e) {
              tx.discard();
              e.printStackTrace();
          } finally {
              tx.close();
              System.out.println(jedis.get("user1"));
              System.out.println(jedis.get("user2"));
              jedis.close();
          }
  ```

## spingBoot整合

spring data是和spring boot同级的项目，而不是其子项目

- **spring-boot-starter-data-redis或spring-data-redis**

- springBoot 2.0之后，底层采用lettuce代替jedis;spring-data-redis还是采用jedis

  Lettuce 和 jedis 的都是连接 Redis Server的客户端.

  Jedis 在实现上是直连 redis server，多线程环境下非线程安全，除非使用连接池，为每个 redis实例增加 物理连接。

    Lettuce 是 一种可伸缩，线程安全，完全非阻塞的Redis客户端，多个线程可以共享一个RedisConnection,它利用Netty NIO 框架来高效地管理多个连接，从而提供了异步和同步数据访问方式，用于构建非阻塞的反应性应用程序。

  |        | 优点                                                         | 缺点                                                         |
  | ------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | 直连   | 简单方便，适用于少量长期连接的场景                           | 1. 存在每次新建/关闭TCP连接开销 2. 资源无法控制，极端情况下出现连接泄漏 3. Jedis对象线程不安全(Lettuce对象是线程安全的) |
  | 连接池 | 1. 无需每次连接生成Jedis对象，降低开销 2. 使用连接池的形式保护和控制资源的使用 | 相对于直连，使用更加麻烦，尤其在资源的管理上需要很多参数来保证，一旦规划不合理也会出现问题 |

- @configuration-proxyBeanMethods

  true-EnhancerBySpringCGLIB 检测容器中是不是有了这样的组件，如果有，则不再新建组件，直接将已经有的组件返回。如果说没有的话，才会新建组件.

  false-commn Object 不会检测，直接创建新的组件了

- boundforxxxops opsForxxx

- @ConditionalOnSingleCandidate

  only matches when a bean of the specified class is already contained in the BeanFactory and a single candidate can be determined.**本质上等同于Autowired**.the condition match if auto-wiring a bean with the defined type will succeed

  1. beanFactory只有一个类型bean
  2. 有多个,但指定了primary

- 序列化器

  ![2](C:\Users\Administrator\Desktop\复习\素材\pic\redis\2.png)

  - 1. 在保存数据时需要把java对象序列化为byte，在读取数据时需要把byte反序列化为java对象；
    2. 不同的java数据类型序列化和反序列化的逻辑不一样；
    3. java对象序列化后的文本格式不一样。常见的有java对象字节码、json字符串，xml字符串；
    4. spring把上述逻辑封闭在redis序列化器中，接口为RedisSerializer；

  - 默认序列化器 JDK

    ```java
    if (this.defaultSerializer == null) {
        this.defaultSerializer = new JdkSerializationRedisSerializer(this.classLoader != null ? this.classLoader : this.getClass().getClassLoader());
    }
    ```

  - ```
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String, Object> rt = new RedisTemplate<>();
        rt.setConnectionFactory(connectionFactory);
    
        rt.setKeySerializer(RedisSerializer.string());
        rt.setValueSerializer(RedisSerializer.json());
    
        rt.afterPropertiesSet();
    
        return rt;
    }
    ```



问题: 

1. 自定义@ConditionalOnSingleCandidate(RedisConnectionFactory.class)无效

   spring会先扫描自定义的configurations,而我们未定义RedisConnectionFactory,于是便失效了.

1. 泛型

   ​	容器中有Type<T1>  Type<T2> 两个bean 

   - 自动注入Type失败(这两个都是Type类型) 
   - 但当指定了泛型后就会精确匹配 如自动注入Type<T1>就会成功

2. 继承关系

   如有A,B extends A类

   当容器中有A,B两个bean 自动注入A类型就会失败

   

   

## 订阅发布

![10](C:\Users\Administrator\Desktop\复习\素材\pic\redis\10.jpg)

* 命令

     channel不存在会新建 

  1) "subscribe" #类型
  2) "jinjianou" #channel
  3) (integer) 1 #responseCode

  * 发布

    publish channel message #此时必须保证subscribe channel未退出

  * 订阅

    subscribe channel[channel...]

    psubscribe pattern[pattern...]

  * 退订

    unsubscribe [channel[channel...]]

    punsubscribe [pattern[pattern...]]

  * 查看订阅与发布系统状态

    pubsub subcommand[arg[arg...]] 只能检测到subscribe而不是punsubscribe 

    如 pubsub channels

  

## 主从复制

主从复制，是指将一台Redis服务器的数据，复制到其他的Redis服务器。前者称之为master 后者是slave

**数据的复制是单向的**，从主到次，**Master以写为主,slave以读为主**

默认情况下，每台Redis服务器都是主节点，每个主节点可以有0个或多个从节点，但每个从节点只能有一个主节点

主从复制作用：

  1. 数据冗余 实现数据的热备份

     **热备份**是系统处于正常运转状态下的**备份**。 通常采用archivelog mode方式**备份数据库**的方法 

  2. 故障恢复：主节点出问题，可以由从节点提供服务，实现快速的故障恢复，实际是服务的一种冗余

  3. 负载均衡：在主从复制基础上，配合读写分离

  4. 高可用的基础：主从复制是哨兵和集群能实施的基础



需要多台Redis服务器：

1. 从结构上说，单个Redis服务器会发生单点故障，请求负载过大
2. 从容量上，单个Redis服务器内存容量有限，**单个Redis服务器使用内存应该不超过20G**



环境配置：

1. 建三个redis-conf文件，修改对应文件配置项

   * port
   * pidfile
   * log
   * dbfilename

2. 启动 redis-server jinjianou/redis79.conf 

3. info replication

4. 设置master & slave

   * 临时的 slaveof host port

     * 重启后变回主机

   * 修改配置文件 

     replicaof <masterip><masterport>

     masterauth <master-password>

     * If the master is password protected (**using the "requirepass" configurationdirective below**) it is possible to tell the replica to authenticate before starting the replication synchronization process

   * 重启生效

     kill -9 pid

  细节：

 1. 主机可以写，从机只能读

 2. 主机中的所有信息和数据，都会被从机保存

 3. 复制原理：

   **slave启动成功连接到master**后会发送一个sync命令，master接到命令后，启动后台存盘进程，同时收集所有接收到的用于修改数据集命令，在后台进程执行完毕后.master将传送整个数据文件到slave，并**完成一次完全同步**。

   全量复制：slave服务接收到数据文件数据后，将其存盘并加载到内存中。

   增量复制：master继续将**新的所有收集到的修改命令依次**传给slave，完成同步

另一种模式：链路结构  A<-B<-C  A是B的master B是C的master 此时B依旧是slave，不能写入

* masterd宕机 配置主节点（手动）

  slaveof no one

  配置后变成master，但没有slave

  原master修复了，是master但没slave

* 哨兵模式

  哨兵是一个独立的进程，通过发送命令，等待Redis服务器响应，从而监控多个Redis实例![11](C:\Users\Administrator\Desktop\复习\素材\pic\redis\11.jpg)

  假设matser宕机，哨兵1先检测到这个结果，系统并不会马上进行failover过程，仅仅认为哨兵1主观认为master不可用（**主观下线**）。当后面的哨兵也检测到了master不可用,**达到一定数量**后，那么哨兵之间会发起一次投票，并进行failover故障转移。切换成功后，通过发布订阅模式，让哨兵们把自己监控的从服务器实现切换主机，这个过程叫做**客观下线**

  操作：

  1. 新建sentinel.conf
     * port 26379 #sentinel实例运行端口默认26379
     * dir /tmp #工作目录
     * sentinel monitor mymaster 127.0.0.1 6379 2 #j监控redis主节点的ip port /  master-name可以自己命名 /quonum 当改数量的哨兵任务master失联，那么客观上认为主节点失联了
     * sentinel down-after-milliseconds <master-name> <milliseconds> #主观下线的心跳时间
     * sentinel auth-pass <master-name> <password>  #当redis实例开启了requirepass时启用
     * sentinel parallel-syncs <master-name> <numslaves> #指定每次failover主备切换时有多少geslave不能处理命令请求，值越小，完成failover需要时间越多(同步耗时)
  2. redis-sentinel sentinel.conf
  3. 原master修复完毕后，哨兵监测到会自动将其变成新matser的slave

  缺点： **Redis较难支持在线扩容**（**受到物理内存的限制** ），在集群容量达到上限时在线扩容会变得很复杂。 



总结:

为了让服务高可用,分布式服务出现

常见的三种集群方案:

1. 主从模式

   缺点: master节点故障后服务，需要人为的手动将slave节点切换成为maser节点后服务才恢复。

   

   ![3686185d629f40dd81019c640ac7d8da](.\Redis.assets\3686185d629f40dd81019c640ac7d8da.png)

2. 哨兵模式

   针对方案1的缺陷,哨兵模式能在master节点故障后能自动将salve节点提升成master节点，不需要人工干预操作就能恢复服务可用

   缺点:主从模式、哨兵模式都没有达到真正的数据sharding存储，**每个redis实例中存储的都是全量数据**

   **不是绝对的实时同步,可能连最终一致性都谈不上**

   - sharding

     **在分布式存储系统中，数据需要分散存储在多台设备上，数据分片（Sharding）就是用来确定数据在多台存储设备上分布的技术。数据分片要达到三个目的：**

     - 分布均匀，即每台设备上的数据量要尽可能相近；
     - 负载均衡，即每台设备上的请求量要尽可能相近；
     - 扩缩容时产生的数据迁移尽可能少。

     

   ![4cb99d608cf241c891b6836427935258](.\Redis.assets\4cb99d608cf241c891b6836427935258-1659406617439.png)

3. 分片集群

   1. 集群中有多个master，每个master保存不同数据

   2. 每个master都可以有多个slave节点

   3. master之间通过ping监测彼此健康状态

   4. 客户端请求可以访问集群任意节点，最终都会被转发到正确节点

      

      ![3ff087780483478e91b87ff9ca044ef4](.\Redis.assets\3ff087780483478e91b87ff9ca044ef4-1659406687828.png)

   

## 缓存穿透与雪崩

* 缓存穿透

  用户想要查询一个数据，发现redis数据库没有（缓存未命中），向持久层数据库查询，如果也没有，于是本次查询失败，当用户量很多，缓存都没有命中，都会去请求持久层，这会对其造成很大的压力。

  * 存储空对象

    ![12](C:\Users\Administrator\Desktop\复习\素材\pic\redis\12.jpg)

    存在问题：

    1. 需要更多的空间存储空键值对
    2. 即使增加了过期时间，也会存在缓存层和持久曾一段时间窗口的数据不一致，对需要保证一致性的业务会造成影响

  * 布隆过滤器

    ![13](C:\Users\Administrator\Desktop\复习\素材\pic\redis\13.jpg)

    假设有这样一个场景：

    原本有10亿个号码，现在又来了10万个号码，要快速准确判断这10万个号码是否在10亿个号码库中？ 

    解决办法一：将10亿个号码存入数据库中，进行数据库查询，准确性有了，但是速度会比较慢。 

    解决办法二：将10亿号码放入内存中，比如Redis缓存中，这里我们算一下占用内存大小：10亿*8字节=8GB，通过内存查询，准确性和速度都有了，但是大约8gb的内存空间，挺浪费内存空间的。 

    　那么对于类似这种，大数据量集合，如何准确快速的判断某个数据是否在大数据量集合中，并且不占用内存，**布隆过滤器**应运而生了。 

    布隆过滤器：一种数据结构，是由一串很长的二进制向量组成，可以将其看成**一个二进制数组**。 

    当要向布隆过滤器中添加一个元素key时，我们通过多个hash函数，算出一个值 

    ![13](C:\Users\Administrator\Desktop\复习\素材\pic\redis\13.png)

    　　优点：优点很明显，二进制组成的数组，占用内存极少，并且插入和查询速度都足够快。

    　　缺点：随着数据的增加，误判率会增加；还有**无法判断数据一定存在**(**能判断某个数据一定不存在**）；另外还有一个重要缺点，无法删除数据。

    实现：

    1. bitmap

       set k1 "big"

       setbit k1 7 1

       "b"的二进制表示为0110 0010，我们将第7位（从0开始）设置为1，那0110 0011 表示的就是字符“c”，所以最后的字符 “big”变成了“cig” 

       getbit key offset

       <dependency>    <groupId>org.springframework.boot</groupId>    <artifactId>spring-boot-starter-data-redis</artifactId></dependency>

       ```
       @Import({ LettuceConnectionConfiguration.class, JedisConnectionConfiguration.class })
       RedisAutoConfiguration
       
       @ConditionalOnProperty(name = "spring.redis.client-type", havingValue = "lettuce", matchIfMissing = true)
       LettuceConnectionConfiguration
       
       ```

       @Autowired时 泛型是计算在注入规则的（如有A<String> A<Object>会被认为是两个不同的类型而成功注入）

       **字符串中全部位位0先普通表示一个字符串 再set 0**

       \ ' "有特殊的含义 当我们想让它表示普通字符在前面加\ 如\\\

       ```
        for (int i = 23; i >=0; i--) {
                   redisTemplate.opsForValue().setBit("k1",i,false);
               }
       
               //随机加入一百万次
               for (int i = 0; i < 1; i++) {
                   //假设有4个hash函数 随机生成0-23的hash值
       //            boolean flag=true;
                   Integer[] rands=new Integer[4];
                   for (int j = 0; j < 4; j++) {
                       int rand = new Random().nextInt(24);
                       rands[j]=rand;
                       System.out.println(rand);
                   }
                   for (int l = 0; l < rands.length; l++) {
                       if(!redisTemplate.opsForValue().getBit("k1",rands[i])){
                           Arrays.stream(rands).forEach(
                                   rand->redisTemplate.opsForValue()
                                           .setBit("k1",rand,true));
                           break;
                       }
                   }
       ```

    2. redisson(jedit?)

    3. guava

* 缓存击穿

  一个key非常热点，大并发集中的对这个点进行访问，当这个key在失效的失效的瞬间，继续大并发请求就会穿透缓存，直接请求数据库并写回缓存，瞬间压力过大

  解决方案：

  1. 设置热点不过期

  2. 加互斥锁

     分布式锁：保证对每个key同时只有一个线程取查询后端服务，其他服务没有获取分布式锁的权限，只能等待

* 缓存雪崩

  某个时间段缓存集中过期失效（如Redis宕机）

  解决方案

  1. 高可用 集群（异地多活）

  2. 限流降级

  3. 数据预热

     先把可能的数据预先访问一遍，这样可能大量访问都会被加载到缓存中，并设置不同的过期时间