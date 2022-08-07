# linux（基于CenterOS 7 & 01linux.pdf）

###计算机硬件体系

计算机硬件由cpu(算术逻辑单元 ,控制单元)，存储器，输入输出系统组成

* 输入输出设备 人们熟悉的信息形式与计算机能识别的信息相转化

* 存储器

  * 计算机内部存储器
    * ram（random access memory） 随机存储 **内存** 速度快 容量小 掉电易失
    * rom(read-only memory) 只读内存（早期技术不成熟 只能读） 断电不会丢失  -> EPROM ->nand flash闪存

  *  **硬盘** 

    * 机械硬盘 

      *硬盘由多个盘面叠在一起，盘面是个圆形，从里到外被划分了许多圈，也就是 磁道 ，每个磁道又被划分了许多个扇形区域，也就是 扇区 ，硬盘的读写都是以扇区为单位进行的，一般情况下一个扇区的容量是***512字节**

      所有文件系统都是基于"簇"的大小 windows中**文件**默认簇大 小为 **4 KB**

      读写时间=磁臂寻道+转速时间+数据传输时间

    * 固态硬盘

      闪存颗粒存储数据、

    * 性能比较

      ![ramdonvssequence](C:\Users\Administrator\Desktop\复习\素材\pic\linux\ramdonvssequence.png)

## 计算机网络

### 要素

An [IP address](https://www.iplocation.net/ip-address) has two components, the network address and the host address 

ip address(4*8bit)=network address+ host address （一般不取0，1，255）![IP-address](C:\Users\Administrator\Desktop\复习\素材\pic\IP-address.png)

**子网掩码**(**subnet mask**,netmask)  A subnet mask separates the IP address into the network and host addresses ,it is used to identify network address of an IP address by perfoming a **bitwise AND operation** on the netmask. 

**默认网关**（**Default Gateway**） A default gateway makes it possible for **devices in one [network](https://www.lifewire.com/what-is-computer-networking-816249) to communicate with devices in another network**. If a computer, for example, requests a web page, the request goes through the default gateway before exiting the [local area network (LAN)](https://www.lifewire.com/what-is-lan-4684071) to reach the internet.

Think of a default gateway as an intermediate（中介） device between the local network and the internet. The default gateway transfers internal data to the internet and back again.

lan的消息也找默认网关，由它决定是向内广播还是向外发送消息



192.168.0.1/24  24指的子网掩码左侧24个1 即255.255.255.0

**Domain Name Server** *(DNS) is a standard protocol that helps Internet users **discover websites using human readable addresses*** 

### 交换机和路由器

1. 外观 不同

2. 交换机连接lan,但无法访问internet

   现在路由器基本集成了交换机,既可以连接lan,也可以连接外网

3.  路由器会在局域网自动分配IP，实现虚拟拨号

   而交换机只是用来分配网络数据的。

### 网络连接方式

* host-only

  1. 需要将真实环境和虚拟环境隔离，可采用host-only
  2. 该模式下，**所有虚拟机之间是相互通信的，但虚拟机和真实网络是相互隔离的**
  3. 该模式下，虚拟系统的tcp/ip配置信息由vmnet1(host-only)虚拟网络的dhcp服务器动态分配

* brige（桥接）

  1. vmware虚拟出来的操作系统就像是局域网中的一台独立主机，可以访问网内任何一台机器

  2. 该模式下,虚拟系统和宿主主机之间的关系，像连接同一个集线器（hub）的两台电脑

     ​	需要防止虚拟机的固定ip和网内其他主机的IP都不相同 

* nat(网络地址转化)

  1. 使用该模式，虚拟机借助nat功能，通过宿主机器所在网络访问公网

  2. 该模式下，虚拟系统的tcp/ip配置信息由vmnet8(nat)虚拟网络的dhcp服务器动态分配

  3. **虚拟系统无法和本局域网内其他真实主机进行通信**

  4. **一台物理机**里面创建多个nat模式的虚拟机，那**虚拟机之间是可以互相访问**的 

     

     ![network](C:\Users\Administrator\Desktop\复习\素材\pic\linux\network.png)

  ### 软件

  * 应用软件

  * 系统软件 和硬件打交道，屏蔽应用软件和硬件的差异

    GNU是一个开源软件组织，unix收费，linux是内核 linux is not unix

    分支

    * redhat

      主要用于服务器版本  centeros 版本**用双不用单**

    * debain

      视窗良好 ubuntu

## 安装

​	https://linux.org/ 选择centeros 7.6

​	https://vault.centos.org/7.6.1810/ 选isos文件夹

![mirror](C:\Users\Administrator\Desktop\复习\素材\pic\linux\mirror.png)

​		rescue 解救/修复

​		安装好后修改虚拟机系统文件存放位置为非系统盘 编辑->首选项->虚拟机默认位置

​		安装时iso需要配置分区 i will configure partitioning

​		/boot 256M

​		swap 2*内存

​		/  不填 默认全部

​	![linux-install](C:\Users\Administrator\Desktop\复习\素材\pic\linux\linux-install.jpg)

​		host name改成和该系统名称（如base01）相同

​		设置密码			

### 虚拟化技术

* 为了更好的利用计算机限制的资源，虚拟多台虚拟机帮助我们执行程序或业务

### 网络配置

![linux-network](C:\Users\Administrator\Desktop\复习\素材\pic\linux\linux-network.jpg)

vi /etc/sysconfig/network-scripts/ifcfg-ens33

![linux-network2](C:\Users\Administrator\Desktop\复习\素材\pic\linux\linux-network2.jpg)

​	vi etc/sysconfig/network-scripts/ifcfg-ens33

​	先启生效配置文件(source)&重启再查看地址

​        selinux: Type enforcement is the part of an* **SELinux** *policy that defines whether a process running with a certain type can access a file labeled with a certain type.*  

![linux_network3](C:\Users\Administrator\Desktop\复习\素材\pic\linux\linux_network3.jpg)





### 关机

* halt 直接拔掉电源
* poweroff  直接关闭机器
* shutdown -h now 马上关闭 但会给其他用户发送信息
* reboot 重启

### 快照与克隆

![linux-snopshot](C:\Users\Administrator\Desktop\复习\素材\pic\linux\linux-snopshot.jpg)

![network-connectjpg](C:\Users\Administrator\Desktop\复习\素材\pic\linux\network-connectjpg.jpg)

xshell 工具-选项  文件-属性-外观

The key difference between Telnet and SSH is that SSH uses encryption(加密), which means that all data transmitted over a network is secure from eavesdropping（窃听）. SSH uses the **public key encryption** for such purposes.

Like Telnet, a user accessing a remote device must have an SSH client installed. On a remote device, an SSH server must be installed and running. SSH uses the TCP port 22 by default.

SSH 和 Telnet 应用领域基本重合  SSH 比 Telnet 更加安全 

* private & public key

  It’s possible to recover the public key if you own the private key. However it’s impossible to find the private key using only the public key **** 私钥生成公钥ok 公钥生成私钥 impossible

  在私钥学中，加密和解密使用相同的密钥（private key）。这个密钥是对称的，因为唯一的密钥是由另一方复制或共享以解密密文。它比公钥密码学更快。 

  在公钥学中，使用两个密钥，一个用于加密，另一个用于解密。一个密钥（公钥）用于加密明文以将其转换为密文，另一个密钥（私钥）用于接收方解密密文以读取消息。

  生成 ：每个用户一个钥匙对

  1. ssh-keygen -t rsa -C "your_email@example.com" 

     ssh-keygen -t rsa -C "jinjianou@163.com" 

     默认存放在~/.ssh/下   id_rsa

  2. **输入passphrase（口令，本步骤可以跳过）** 

  3. 将生成的公钥放在需要的地方

  生成（xshell） ：

  1. 工具-新建用户密钥生成 保存pub文件

  2. 在工具-用户密钥管理 可以看到密钥

  3. chmod 700 .ssh

  4. chmod 600 ~/.ssh/authorized_keys （用公钥文件覆盖或者添加到authorized_keys）

     ​	echo str >> file 追加

     ​	cat >> .ssh/authorized_keys  回车后输入内容 ctrl+d结束  

     ​	> 表示覆盖

  5. vi /etc/ssh/sshd_config 

     去掉RSAAuthentication yes(如果没有就不需要了)

     PubkeyAuthentication yes

     AuthorizedKeysFile  .ssh/authorized_keys

     的注释

     将 PasswordAuthentication  yes->no

     **服务器默认ROOT登录，如果允许ROOT登录，很多IP段都会被弱口令扫描，尝试用ROOT的方式登录 ，需要关闭root登录,用普通用户登录**  permitRootLogin -> no

  6. systemctl restart sshd

* ![commnd1](C:\Users\Administrator\Desktop\复习\素材\pic\linux\commnd1.jpg)

* 常用命令

     whereis 查看命令文件的位置

     file 查看文件的类型（需要输入文件的路径）

     who 查看当前在线的用户

     	 useradd userName
     	
     		passwd userName
     	
     	whoami 我是谁
     	
     	pwd 所在路径 print working directory
     	
     	uname -a 查看内核信息
     	
     	echo 打印语句
     	
     	clear 清屏
     	
     	history 历史  history 
     	
     		-c(clear) 清除历史
     	
     		查找历史 ！his_num 比如!2      优化：ctrl+R  输入命令关键字（从最近开始匹配）

![specialCharacter](C:\Users\Administrator\Desktop\复习\素材\pic\linux\specialCharacter.jpg)）

声明变量不用加$

## 第四章 文件系统

* The standard form of the mount command, is          

     mount -t type device dir   

     This tells the kernel to attach the filesystem found on device (which is of type type) at the directory dir.  The previous contents (if any) and owner and mode of dir become invisible, and as long as this  filesystem  remains  mounted,   **the pathname dir refers to the root of the filesystem on device**.

* linux文件操作

  **cd**  Change directory

  * 改变当前工作目录

  **ls ll** 

  * 显示指定目录下所有文件
  * 【-|d|l】rw-------   -普通文件 d文件夹 l软链接 （快捷方式） 

  **mkdir** [option] diretory

  * mkdir -p a/b/c  make parent directories as needed(可创建多级目录)
  * {}并列   如 mkdir -p shiren{a,b,c} 会创建shirena,shirenb,shirenc三个文件夹

  **rmdir**

  * rmdir dir  **必须保证dir 为空**且是个directory
  * 并没有-f属性可以强制删除 可以递归从子目录一级一级向上删
  * rmdir -p a/b/c 同时删除c b a(假设a只有b b只有c) 哪级为空删到哪级
  * --ignore-fail-on-non-empty 参数 不会报错但也不会操作成功

  **copy files and directories**

  *        cp [OPTION]... [-T] SOURCE DEST
           		cp  -T a.txt b.txt a.txt仍存在
           cp [OPTION]... SOURCE(file). DIRECTORY
           cp [OPTION]... -t DIRECTORY SOURCE(file)...

  ​	-t, --target-directory=DIRECTORY          copy all SOURCE arguments into **DIRECTORY**  

  ​	 -T, --no-target-directory          treat DEST as a **normal file**

  ​	-r  -R  --recursive  可以将文件夹->文件夹 如 cp -r shenren* /opt

		**mv - move (rename) files**
		
			   mv [OPTION]... [-T] SOURCE DEST    
		
					SOURCE **disappear**
		
			   mv [OPTION]... SOURCE... DIRECTORY   
		
			    mv [OPTION]... -t DIRECTORY SOURCE...



​		    mv shiren shiren2  shiren2不存在  **rename** shiren->shiren2   若存在则移动

​		    **mv /a /b/**  或 mv /a /b 把a目录移动到b目录下 

​		   **mv /a /b/c   把a目录移动到b目录下 改名c**

​	**rm** - remove files or directories

​		rm [OPTION]... FILE...

​		无参只能删除文件

​		-r, -R, --recursive

​		-f, --force never prompt

​	**touch** - change file timestamps if exists,otherwise create 

​		配合stat file 可以查看access/modify/change(modify+chmod) timestamps

​	**ln** - make links between files

​		ln [OPTION]... [-T] TARGET LINK_NAME

​		默认是硬链接 原始文件和链接是同一个文件 链接和文件相互独立（链接消失文件还在，文件消失链接还在）

​		-s 软链接 原始文件和链接不是同一个文件 删除链接源文件还在 源文件不在链接失效

​			x -> a(x是link a是源数据）

​		

* 读取文件信息

  * **cat** - concatenate files and print on the standard output

    cat [OPTION]... [FILE]...

    **将文档一次性加载到内存中，并一次性显示**

    后续可以配合管道（传输数据）使用 如cat c.txt|grep xq

    -n number 显示行号

  * tac - concatenate and print files in reverse

    ​	tac [OPTION]... [FILE]...

    ​	将文档一次性加载到内存中，逆序展示

  * more less

    ​	分页查看文档内容

    ​	回车 下一行 、空格 下一页、b 回退、q 退出

    ​	less is more

    ​		more 不能使用方向键

    ​		less不必读整个文档，加载速度比more更快

  * head

    从头读取n行，未指定行数默认是10行

    head -5  file

  * tail

    从末尾读取n行，未指定行数默认是10行

    指定读取第n行 head -n c.txt|tail -1

    -n +num 表示从第num行开始读起   -n num-num表示读取最后num行

           -f, --follow[={name|descriptor}]
                  output appended data as the file grows;
                  an absent（缺席） option argument means 'descriptor'
        
           -F     same as --follow=name --retry

  * find - search for files in a directory hierarchy

    find \[-H]\[-L]\[-P]\[-D debugopts]\[-Olevel]\[path...][expression]

    expression 中可使用的选项有二三十个之多，在此只介绍最常用的部份。

    -mount, -xdev : 只检查和指定目录在同一个文件系统下的文件，避免列出其它文件系统中的文件

    -amin n : 在过去 n 分钟内被读取过

    -anewer file : 比文件 file 更晚被读取过的文件

    -atime n : 在过去n天内被读取过的文件

    -cmin n : 在过去 n 分钟内被修改过

    -cnewer file :比文件 file 更新的文件

    -ctime n : 在过去n天内被修改过的文件

    -empty : 空的文件-gid n or -group name : gid 是 n 或是 group 名称是 name

    -ipath p, -path p : 路径名称符合 p 的文件，ipath 会忽略大小写

    -name name, -iname name : 文件名称符合 name 的文件。iname 会忽略大小写

    -size n : 文件大小 是 n 单位，b 代表 512 位元组的区块，c 表示字元数，k 表示 kilo bytes，w 是二个位元组。

    -type c : 文件类型是 c 的文件。

    d: 目录

    c: 字型装置文件

    b: 区块装置文件

    p: 具名贮列

    f: 一般文件

    l: 符号连结

    s: socket

    

    如 find  . -name *.txt 在当前目录找txt文件

    find . -name \*.jar报错 ind: paths must precedeexpression 

    解决\\*或‘\*’

    find . -ctime -20

    

* VI & Vim

   * 打开方式

     * vi file
     * vi +8 file 打开文件，光标置于第8行
     * vi + file 打开文件，光标置于最后一行
     * vi +/keyword file 光标关键字的那一行

   * 模式

     * 编辑模式

       每个命令按键都有其对应功能 dd删除一行 p 复制一行 100p复制100行

     * 输入模式

       在文本中输入字符

     * 末行（命令行）模式

       输入特定的命令

   * 模式切换

     i 和 a效果相同 O会在当前行上面新建一个空行

     ![mode](C:\Users\Administrator\Desktop\复习\素材\pic\linux\mode.jpg)

     G gg p ZZ u

     ![edit_mode](C:\Users\Administrator\Desktop\复习\素材\pic\linux\edit_mode.jpg)

     s/p1/p2/ 替换当前行p1->p2，且只替换一个

     s/p1/p2/g 替换当前行全部p1->p2

     3,8s/abc/lucky/g 替换指定行

     g/p1/s//p2/g 替换全文p1->p2

     ![commond_mode](C:\Users\Administrator\Desktop\复习\素材\pic\linux\commond_mode.jpg)

* 计算机间传递数据

   **`yum` is the primary tool for getting, installing, deleting, querying, and managing Red Hat Enterprise Linux RPM (redhat package management)software packages from official Red Hat software repositories, as well as other third-party repositories**. `yum` is used in Red Hat Enterprise Linux versions 5 and later. Versions of Red Hat Enterprise Linux 4 and earlier used up2date. 

   * window -- linux

     * lrzsz

       yum install lrzsz -y

       rz(Receive ZMODEM) windows->linux

       sz linux->windows

     * xftp

       如果设置了ssh 需要选择sftp传输

   * linux--linux

     克隆一份系统 修改网络配置和主机名（hostname）

     scp source dest

     如 scp file root@192.168.162.101:/opt   把本地file->root@192.168.162.101下的opt目录

     scp -r test root@192.168.162.100:/opt/ 传文件夹

* 文件大小

   * df - report file system **disk space usage**

      -h, --human-readable
                   print sizes in human readable format

   * du - estimate（*roughly calculate or judge the value* ） **file space usage**

     **默认是递归计算当前目录下所有子孙文件夹及其大小总和**

     --max-depth=N **超过N层数的目录后，予以忽略** 

     比如n=0 只显示当前文件夹大小 n=1显示当前子文件夹大小

     ```
     -d, --max-depth=N
               print  the  total  for a directory (or file, with --all) only if it is N or fewer levels below the command line
               argument;  --max-depth=0 is the same as --summarize
     ```

   * top display linux process

      具体参数见下方

      

* 文件 压缩

   tar \[OPTION...][FILE]...

   -x, --extract extract files from an archive

   -c, --create create a new archive

   -z, --gzip          filter the archive through **gzip**

    -f, --file=ARCHIVE  use archive file or device ARCHIVE

   -C, --directory=DIR change to directory DIR

    -P, --absolute-names don't strip leading `/'s from file name（使用绝对路径时使用）

   压缩 tar -zcPf  -C **/test.tar.gz** ./

   解压 tar -zxf  test.tar.gz  -C /opt/

* zip unzip

   yum install zip unzip -y

   压缩 package and compress (archive) files   zip  \[OPTION] dest source

   ​	  -r Travel the directory structure recursively 

   ​	  zip -r test.zip test

   解压 unzip test.zip

* source filename [arguments]
         **Read and execute commands** from filename in the current shell environment and return the exit status of the last command executed from filename. If filename does not contain a slash(/）,file names in **PATH** are used to find  the directory  containing  filename. 

## linux的网络信息

* 主机名称

  临时修改 hostname host_name

  持久化 vi /etc/hostname

* DNS解析

  windows:C:\Windows\System32\drivers\etc\hosts  

  linux: /etc/hosts

  ip web_url 如 111.111.111.111 www.baidu.com

  127.0.0.1 环网地址(ipv4)

  ::1 环网地址(ipv6）

* 网络相关命令

  * ifconfig

    查看当前网卡的配置信息
    这个命令属于 net-tools中的一个命令，但是Centos7中minimal版并没有集成这个包
    所以7的时候需要自己手动yum安装
    如果没有ifconfig ，可以使用ip addr 临时代替

  * netstat

    Print network connections, routing tables, interface statistics, masquerade connections, and multicast memberships
    一个机器默认有65536个端口号[0,65535]
    这是一个逻辑的概念，将来我们需要使用程序监听指定的端口，等待别人的访问
    一个端口只能被一个程序所监听, 端口已经被占用

    -a --all Show  both  listening  and  non-listening (for TCP this means established connections) sockets.

    -p, --program Show the PID and name of the program to which each socket belongs

    --numeric , -n  Show numerical addresses instead of trying to determine symbolic host, port or user names

    -r Display  the  kernel routing tables.

    netstat -anp
    netstat -r 核心路由表   ==  route

  * ping

    查看与目标IP地址是否能够连通

  * telnet 

    查看与目标IP的**指定端口**是否能够连通
    yum  install telnet -y
    telnet 192.168.31.44 22

  * curl 

    curl - a  tool  to  transfer data from or to a server, using one of the supported protocols (DICT, FILE, FTP, FTPS, GOPHER, HTTP, HTTPS, IMAP, IMAPS, LDAP, LDAPS, POP3, POP3S, RTMP, RTSP, SCP, SFTP, SMTP, SMTPS, TELNET and TFTP).  The command is designed to work without user interaction

    restful 我们所有的资源在网络上中都有唯一的定位
    那么我们可以通过这个唯一定位标识指定的资源
    http://localhost:8080/lucky/user.action/666

     -X, --request <command>

    curl -X GET http://www.baidu.com 

* 防火墙

  防火墙技术是通过有机结合各类用于安全管理与筛选的软件和硬件设备，帮助计算机网络于其内、
  外网之间构建一道相对隔绝的保护屏障，以保护用户资料与信息安全性的一种技术
  在centOS7+中 使用firewalld代替以前的 iptables ；

  systemctl status firewalld.service             

  

  systemctl stop firewalld.service             

  systemctl disable firewalld.service        ##禁止开机启动
  firewall-cmd --state                           ##查看防火墙状态，是否是running
  firewall-cmd --reload                          ##重新载入配置，比如添加规则之后，需要执行此命令
  firewall-cmd --get-zones                       ##列出支持的zone
  firewall-cmd --get-services                    ##列出支持的服务，在列表中的服务是放行的
  firewall-cmd --query-service ftp               ##查看ftp服务是否支持，返回yes或者no
  firewall-cmd --add-service=ftp                 ##临时开放ftp服务
  firewall-cmd --add-service=ftp --permanent     ##永久开放ftp服务
  firewall-cmd --remove-service=ftp --permanent  ##永久移除ftp服务
  开启一个端口的正确操作

## 第五章 加密算法

* 不可逆加密

  可以通过数据计算加密后的结果，但是**通过结果无法计算出加密数据**
  应用场景
  	Hash算法常用在不可还原的密码存储、信息完整性校验。
  	MD5文档、音视频文件、软件安装包等用新老摘要对比是否一样(接收到的文件是否被修改)
  	MD5用户名或者密码加密后数据库存储(数据库大多数不会存储关键信息的明文，就像很多登录功
  能的忘记密码不能找回，只能重置)

* 对称加密

  ![passwd_1](C:\Users\Administrator\Desktop\复习\素材\pic\linux\passwd_1.jpg)

* 非对称加密

  ![passwd_2](C:\Users\Administrator\Desktop\复习\素材\pic\linux\passwd_2.jpg)

* 主机之间相互免秘钥

   authentication key generation, management and conversion

     [-P old_passphrase]

     -f filename  Specifies the filename of the key file.

  ![passwd_3](C:\Users\Administrator\Desktop\复习\素材\pic\linux\passwd_3.jpg)

  如果想登陆谁，就把自己的**公钥**发送给对方，公钥会在对方的**authorized_keys**

  *The known_hosts File is **a client file containing all remotely connected** known hosts, and the ssh client uses this file.* 

  *Authorized keys are Access Credentials（证件）. Authorized keys configure access credentials and grant access to servers.* 

  ssh-copy-id — use locally available keys to authorise logins on a remote machine

  -i identity_file

  ssh-copy-id  -f -i ~/.ssh/id_rsa.pub  root@196.168.162.101

  问题：CentOS出现连接被拒--ssh：connect to host centos-py port 22: Connection refused 

   1. yum -y install openssh-server 

     2.ss -lnt 看22端口是否打开  
     3.查看auth文件是不是有了

  不行就用scp

主要流程包含： 

1. 客户端生成RSA公钥和私钥
2. 客户端将自己的公钥存放到服务器
3. 客户端请求连接服务器，服务器将一个随机字符串发送给客户端
4. 客户端根据自己的私钥加密这个随机字符串之后再发送给服务器
5. 服务器接受到加密后的字符串之后用公钥解密，如果正确就让客户端登录，否则拒绝。

 

  ![passwd_4](C:\Users\Administrator\Desktop\复习\素材\pic\linux\passwd_4.jpg)

## 第六章 日期

![date](C:\Users\Administrator\Desktop\复习\素材\pic\linux\date.jpg)

## 用户-组-权限

* 用户

  * 新增 useradd user 在/home目录下+同名组	

  * 设置密码 passwd user

  * 删除 userdel userdel - delete a user account and related **files**

    -r, --remove

    **Files in the user's home directory will be remove**d along with the home directory itself and the user's mail spool

  * 修改用户信息 usermod

    l, --login NEW_LOGIN
               The name of the user will be changed from LOGIN to NEW_LOGIN.

    L --lock

    ​	  lock a user's password 密码无效

    -U, --unlock
               Unlock a user's password

  * 常用命令

    * /etc/shadow 用户名和密码（加密）

    * /etc/passwd 

      用户名 用户编号 组编号 /home

      6.5编号 系统 0-499 用户500+

      7.6编号 系统 0-999 用户1000+

  * 切换用户 su user

* 组

  * 创建组 groupadd group

  * 删除 groupdel group

  * 修改组名称 groupmod [options] GROUP  

    -n, --new-name NEW_GROUP
               The name of the group will be changed from GROUP to NEW_GROUP name.

    groupmod -n new_group old_group

  * 查看组

    groups - print the groups a user is in 用户对应的组

    groups

    groups [OPTION]... [USERNAME]..

    user: group

  * 修改用户的组

    usermod 

      -g, --gid GROUP       The group name or number of the user's new initial login group. The group must exist. 覆盖主组

    -G, --groups GROUP1[,GROUP2,...[,GROUPN]]] 覆盖附属组
               A list of supplementary groups which the user is also a member of. Each group is separated from the next by acomma, with no intervening whitespace.

    usermod -G libai liqingzhao

* 权限

  * - 修改权限

      - 修改文件所属

        chown - change file owner and group

        chown [OPTION]... \[OWNER][:[GROUP]] FILE...

        -R, --recursive
                      operate on files and directories recursively

        例 chown liqingzhao:bjy test.zip

        修改文件夹 chown -R liqingzhao:bjy test

      - 修改文件的rwx

        chmod ugo+rw test

        ```
           -R, --recursive
                  change files and directories recursively
        ```

        ​	chmod -R 777 test

  * 权限赋予

    将管理员权限赋予普通用户

    vi  /etc/sudoers

    在root    ALL=(ALL)       ALL下面添加 liqingzhao ALL=(ALL)       **ALL**

    切换用户  su liqingzhao 

    使用命令 sudo systemctl restart network 不用再输入root密码

 ## 第八章 管道和重定向

### 管道

|

grep print lines matching a pattern

​	grep [OPTIONS] PATTERN [FILE...]	

​	re: ^ $ . *

### 重定向

​	改变数据输出的位置，方向

​	0 in ; 1 out; 2 err 如 ls abcd 2>lucky
​	结合使用  ls abcd >lucky 2>&1   1&2同时都放进lucky

​	信息黑洞 ll /etc >> /dev/null 2>&1

## 第九章 系统进程

* ps-report a snapshot of the current processes

		-e Select all processes

		-f Do full-format listing. This option can be combined with many other UNIX-style options to add additional columns
		
		ps -ef
		
		UID      PID   PPID  C STIME TTY          TIME CMD
		
		UID当前用户 PID当前进程编号 PPID当前进程编号的父进程编号
		
		C CPU STIME 系统启动时间  TTY登陆者的终端位置  TIME使用的cpu时间 CMD下达的命令



​	-a Select all processes except both session leaders (see getsid(2)) and processes not associated with a terminal

​	 -u userlist  Select by effective user ID (EUID) or name

​	ps -aux

​	USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND

​	VSZ 进程分配的虚拟内存   RSS 是常驻内存集（Resident Set Size），表示该进程分配的内存大小 

​	**ps -aux --sort [+/-]cpu 按照cpu的使用率 pmem 内存占比**

* **top display Linux processes**

  - -d 秒数：指定 top 命令每隔几秒更新。默认是 3 秒；

  - -b：使用批处理模式输出。一般和"-n"选项合用，用于把 top 命令重定向到文件中；

  - -n 次数：指定 top 命令执行的次数。一般和"-"选项合用；

  - -p 进程PID：仅查看指定 ID 的进程；

    在 top 命令的显示窗口中，还可以使用如下按键 

  - P：按照 CPU 的使用率排序，默认就是此选项；
  - M：按照内存的使用率排序；
  - N：按照 PID 排序；
  - T：按照 CPU 的累积运算时间排序，也就是按照 TIME+ 项排序；

* 后台进程 &

  查看 jobs \[-lnprs][ jobspec ... ]   **只能当前发起用户看到**

  **-l  List process IDs in addition to the normal information**

  

  nohup run a command immune(免疫) to hangups（挂起 ）, with output to a non-tty	

  

  terminate a process kill \[-s sigspec | -n signum | -sigspec][pid | jobspec] ...

  kill -15 

  1. 当程序释放相应资源后再停止

  2. 程序可能仍然继续运行

     timaneted > killed

  kill -9 强制停止 killed

  nohup ping www.baidu.com >>Baidu 2>&1 &

## linux软件安装

* 环境变量

  当执行命令的时候，先从当前路径查找，找不到对应的则从环境变量$PATH查找

  $PATH文件在/etc/profile 

  windows 路径之间;**连接 linux : 连接**

  每次修改完文件后，需要重新生效文件  source /etc/profile

* 安装方式

  * 解压使用
  * 使用安装包（window-exe linux-rpm)
    1. 自行下载npm包
    2. 使用统一软件帮助安装
  * 使用源码安装

* RPM

  rpm - RPM Package Manager

         -i, --install                    install package(s)
      -v     Print verbose information - normally routine progress messages will be displayed.
      -h, --hash
                    Print 50 hash marks as the package archive is unpacked.  Use with -v|--verbose for a nicer display.
      
      -q|--query
       -a, --all
                    Query all installed packages
      -e|--erase erase (uninstall) package
  * 安装

    1. 官网下载jdk 传到linux
    2. rpm -ivh jdk-8u261-linux-x64.rpm
    3. 针对RPM包 一般情况下 命令放在/usr/bin或/usr/sbin下 库在/usr/lib下 数据文件在/usr/share/下 

  * 查询

    rpm -qa|grep jdk

    rpm -pql [rpm文件名]，来查看一个rpm包里有哪些文件，即安装的路径 

  * 卸载

    rpm -e   jdk1.8-1.8.0_261-fcs.x86_64

  * 修改环境变量并生效

    vim /etc/profile

    export JAVA_HOME=/usr/java/jdk1.8.0_261-amd64

    export PATH=\$PATH:$JAVA_HOME/bin

    source /etc/prifile

* 压缩包安装

  * 下载压缩包 解压 tar -zxf  apache-tomcat-9.0.37.tar.gz
  * 拷贝到opt  cp -r apache-tomcat-9.0.37 /opt
  * 启动 ./bin/startup.sh

* yum

  `yum` is the primary tool for getting, installing, deleting, querying, and managing Red Hat Enterprise Linux RPM software packages from official Red Hat software repositories, as well as other third-party repositories  类似于maven

  search         Search package details for the given string

  info           Display details about a package or group of packages

  list           List a package or groups of packages

* wget

  *Wget is a* **computer tool created by the GNU Project***. You can use it to retrieve content and files **from various web servers.** The name is a combination of World Wide Web and the word get. It supports downloads via FTP,* [*SFTP*](https://cn.bing.com/search?q=SSH+File+Transfer+Protocol&filters=sid%3a97ff8bf1-b279-46c9-42d2-1bf5edd0909b&form=ENTLNK)*, HTTP, and HTTPS.*

  -O,  --output-document=FILE    write documents to FILE. 

  wget [OPTION]... [URL]...

* 更换yum源

  * yum install  wget -y
  * 失效原始配置文件 mv /etc/yum.repos.d/CentOS-Base.repo  /etc/yum.repos.d/CentOS-Base.repo.backup
  * wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
  * 清空原yum源缓存 yum clean all
  * 获取阿里云缓存 yum makecache

* 安装mysql

  ```js
  #----------安装Mysql依赖【perl net-tools】
  yum install perl net-tools -y
  #----------卸载mariadb
  rpm -qa | grep mariadb
  rpm -e --nodeps mariadb-libs-5.5.60-1.el7_5.x86_64
  #----------安装mysql
  tar -xvf mysql-8.0.18-1.el7.x86_64.rpm-bundle.tar
  rpm -ivh mysql-community-common-8.0.18-1.el7.x86_64.rpm
  rpm -ivh mysql-community-libs-8.0.18-1.el7.x86_64.rpm
  rpm -ivh mysql-community-client-8.0.18-1.el7.x86_64.rpm
  rpm -ivh mysql-community-server-8.0.18-1.el7.x86_64.rpm
  #----------启动mysql
  systemctl start mysqld
  #----------查找密码并登陆Mysql
  cat /var/log/mysqld.log | grep password
  mysql -u root -p
  #----------修改Mysql密码 8.0版本输入命令：
  set global validate_password.policy=LOW;
  set global validate_password.length=6;
  #更改加密方式
  ALTER USER 'root'@'localhost' IDENTIFIED BY '123456' PASSWORD EXPIRE NEVER;
  #更新用户密码
  ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456';
  #刷新权限
  FLUSH PRIVILEGES; 
  #----------修改Mysql密码 5.7版本输入命令：
  set global validate_password_policy=LOW;
  set global validate_password_length=6
  alter user root@localhost identified by '123456';
  #----------修改Mysql链接地址
  use mysql;
  update user set  host='%' where user = 'root';
  commit;
  exit;
  systemctl restart mysqld;
  ```

  ```
  wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
  
  rpm -ivh mysql80-community-release-el7-3.noarch.rpm
  rpm -qlia|grep mysql //查看安装路径
  
  yum -y install mysql-server
  systemctl start mysqld
  systemctl status mysqld
  
  #----------查找密码并登陆Mysql
  cat /var/log/mysqld.log | grep password
  mysql -u root -p
  
  #更改加密方式
  ALTER USER 'root'@'localhost' IDENTIFIED BY 'l,Ht:&DiF0?x' PASSWORD EXPIRE NEVER;
  
  #----------修改Mysql密码 8.0版本输入命令 密码插件：
  set global validate_password.policy=LOW; 
  set global validate_password.length=6;
  
  ALTER USER 'root'@'localhost' IDENTIFIED BY '123456' PASSWORD EXPIRE NEVER;
  #更新用户密码
  ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '123456'; _Jjojinjianou123456
  #刷新权限
  FLUSH PRIVILEGES; 
  
  
  ```

## shell脚本

### shell是什么

​	一个程序（C语言编写） **用户和linux kernel**(根据指令驱动硬件)沟通的桥梁；命令语言+解释性编程语言

### 语法

* 后缀名可有可无，推荐有

* 定义脚本的执行环境  1、#!/usr/bin/bash #!严格意义上讲非注释、

  ​				      2、#!/usr/bin/env python					

* /etc/shells 定义了支持的shell 

  ​	运行方式： 1.直接运行 ./demo1.sh 需要给x权限

  ​			     2.bash|sh 	 demo1.sh 不需要给x权限  命令为脚本里指定的解释器

* cd - 返回上次目录

  ！keyword  执行最后一次含有kw的命令   !!执行上次命令

  $ 变量中取内容

  *匹配所有 ？匹配回车以外的一个字符

  ; 分隔一行内的多个命令

  ``命令中执行命令

  **'' "" 		''不解释变量**

  | 管道 上一个命令的输出作为下一个命令的输入

  < << > >> 输出输入方向不同

* 运算

  数学运算： expr  a + b （a,b都是整数）  let sum=a+b   sum=$((a+b ))

  echo **$? 返回上一个命令的状态，0表示没有错误**，其它任何值表明有错误 

  

  bc是一款交互数学计算器，支持带小数点运算（scale=n）

   echo "内存使用 `echo "scale=2;141*100/7966"|bc`%"

  echo $((100/3))

  * 数学计算方式

    * $(())

    * let

    * bc

      j=\$(echo "${j}+2" | bc -l)

  * $() ``  \${} \$(())区别
    * 并不是所有的类unix系统都支持$() ，但反引号是肯定支持的 
    * 多个$()同时使用也不会有问题 过多使用``会有问题
    * 一般情况下，\$var与\${var}是没有区别的，但是用${ }会比较精确的界定变量名称的范围，且功能比较多：
      * 取后缀
      * 取子串及替换
      * 数组
    * $(()) 除了重新定义()里面的值外，还可以获取十进制的值 \$((N#val)) N表示进制, val表示该进制的值

* 退出 exit Ｎ(1-255) echo $?获取的值就是N

## 格式化输出

* echo 将数据输出到默认显示设备（显示器）
   -n  do not output the trailing newline

  -e enable interpretation of backslash escapes(解释转义)

* 颜色效果

  echo -e "\033[字背景颜色:文字颜色 字符串 \033[效果"

  如echo  -e  "\033[41;37m hehe \033[4m"  //红底白字 下划线

## 交互

* read

  One  line  is  read  from  the standard input, or from the file descriptor fd supplied as an argument to the -u option, and the first word is assigned to the first name, the second word to the second name, and so  on

  -s 不回显输入的内容

  -t 限定时间  超过就退出

  -n 输入字符的个数  超过就退出

  -p 打印内容
  read \[option] variable

## 变量

* 分类 

  * 本地变量 用户私有变量 存放在/home/?/ .bash_profile .bashrc

  * 全局变量 所有用户能用  /etc/profile 和 /etc/.bashrc 开机直接加载

  * 用户自定义变量 脚本结束被回收

    

* 变量名=值（中间不能有空格，变量名建议大写,值用'' ""）

* 取消变量：unset 变量名

* 定义全局变量： export 变量名=值

## 数组

* 数组名称=（元素1 元素2 ....）

* 读取 ${数组名称[索引]}

* 赋值

  * 一个个赋值 数组名称[index]=元素
  * 多个赋值 数组名称=（元素1 元素2 ....）

* 查看所有数组 declare -a  

* 访问元素 

  * 查看数组所有元素：${数组名称[@]}
  * 统计元素个数  ${#数组名称[@]}
  * 获取索引集  ${!数组名称[@]}
  * 从数组下标1开始  ${数组名称[@]:1}
  * 从数组下标1开始，访问两个元素  ${数组名称[@]:1:2}

* 关联数组（可以看成对象）

  * 声明 declare -A 数组名称
  * 赋值 
    * 多个赋值 数组名称=（[index1]=值1  [index2]=值2....）
  * 查看所有数组 declare -A
  * 访问元素 
    - 查看数组所有元素 **值**：${数组名称[@]}
    - 统计元素个数  ${#数组名称[@]}
    - 获取索引集  ${!数组名称[@]}
    - 从数组中第一个元素开始  ${数组名称[@]:1}  //数组是从0开始计算 对象从1开始
    - 从数组中第一个元素开始，访问两个元素  ${数组名称[@]:1:2}

  

## if

* 数学比较              -eq -gt -lt -ge -le -ne(整型)

* 文件运算 

  ![file](C:\Users\Administrator\Desktop\复习\素材\pic\linux\file.jpg)
  如 test -d /tmp/abc

   **test只能在脚本中使用，相当于[ condtion ]**

* 其他运算 

  * 字符串 ==    !=    -z(zero)   -n(not zero)
  * 逻辑 && || ！
  * 赋值运算 =

* if

     注意点：1. if空[空condition空]

  * if [ condtion ]

    ​	then

    ​		commands

    fi

  * if [ condtion ]

    ​	then

    ​		commands

    else

    ​		commands

    fi

  * if [ condtion ]

    ​	then

    ​		commands

    elif

    ​		then

    ​		commands

    else

    fi

    

  if [ $1 -eq $2 ]
     then
          echo "$1 == $2"
     elif [ $1 -gt $2 ]

  ​	then

  ​        echo "$1 > $2"
     else
  ​        echo "$1 < $2"  
  fi

  

* for

  seq  print a sequence of **numbers**

  ​	[start]\[step] end

  value:

   1. \`seq 48 57`

  	2.  $RANDOM   生成[0, 32767]随机数 

      

  * for var in value

    ​	do

    ​		commds

    done

  * for ((变量；条件；自增减运算))

    ​	do

    ​		commds

    done

    

  * continue

  * break n 内循环为1 外循环为2

  扫描局域网里所有的机器

  ​	yum install nmap

  ​	nmap -sL 192.168.162.0/24

  ping -c1 $1 &>/dev/null

  $RANDOM   [0, 32767] 
  for i in \`seq 48 57` `seq 65 90`  `seq 97 122`

* while **不存在++操作符 $J+=1不允许 应该为 J=\$(($J+1)) 或者 y=$(echo "$y+1"|bc -l)

  while [[ condition ]] 或者 (( condition ))
  do

  ​	commands

  done

* until 与 while相反  假循环 真停止

* case(switch功能)

   case $变量 in 

   ​	条件1）

   ​		执行代码块1;;

   ​	条件2）

   ​		执行代码块2;;

  ....

  	*) 
	
  	执行代码块;;

  esac

```shell
case $J in
  20) echo 'J is 20';;
  22) echo 'J is 22';;
  *)  echo 'not matched';;
  esac
```



  **注意：每个语句结束要加;;   esac倒过来就是case**

## 函数

* 函数名（）{

  ​	执行代码块

  }

  调用直接用 函数名

  

  注意：函数返回值在调用该函数后通过 $? 来获得   

     在函数体内部，通过 $n 的形式来获取参数的值 

  func1(){
          echo "apache start $1==================================>[success]"
          return \$(($1+1))
  }


  func1 1 2 3 4
  echo "返回值为 $?"

## 正则

* erep == grep =E

  egrep "^s.*(a|b)$" test_re.txt

  () / []分组 a和b一组

* posix字符

  ​	egrep "^s[[:alnum:]]" test_re.txt  第一个[]代表一个字符 第二个[]代表posix特殊字符

  ​        **shell不支持非贪婪模式**

  ![posix](C:\Users\Administrator\Desktop\复习\素材\pic\linux\posix.jpg)

## 对文件操作 sed

* **如果只是读取筛选文件行数据 用egrep就行了**

* sed是一个行（流）编辑器，非交互式的对文件内容进行**增删改查**操作，在命令行输入

  处理原理：文件的一行->数据在缓存中处理，然后默认输出到屏幕（**默认不会修改源文件**）

* sed - stream editor for filtering and transforming text

  sed [OPTION]... {script-only-if-no-other-script} [input-file]...

  sed [OPTION]... {\[command][flag]} [input-file]...

    -n, --quiet, --silent

      -n, --quiet, --silent 不输出和模式不匹配多余的数据（包括匹配到的原行数据）
      	suppress automatic printing of pattern space
      
         -e script, --expression=script 脚本字符串 用;隔开
      
                add the script to the commands to be executed
      
         -f script-file, --file=script-file 脚本文件
      
                add the contents of script-file to the commands to be executed
         -i[SUFFIX], --in-place[=SUFFIX]
            		edit files in place (makes backup if SUFFIX supplied)
      
         -l N, --line-length=N
                   specify the desired line-wrap length for the `l' command
            -r, --regexp-extended

  command:

  a 在匹配后面添加

  i 在匹配前面添加

  p 打印

  d 删除

  s 查找替换

  c 更改

  y 转换  N D P



​	flag:

​		数字 第n个

​    		g

​		p 重复打印

​		w filename 将替换结果写入文件

* 案例：

  ​    [/正则/]command[/flag]...

  * 打印第10行

    sed -n 10p test_sed.txt 

  * 在2-4行后面插入

    sed "2,4a\hello world" test_sed.txt 

  * 匹配模式的 追加

    **sed "/^2 the/a\hello world" test_sed.txt**

  * 删除包含#号的和空行

    sed [-r] "/(#|^$)/d" sysctl.conf 

  * 替换 匹配模式的 dog->cat

    sed "/2 the/s/dog/cat/" test_sed.txt

  * 替换 匹配模式的

    /3 the/c\hello world" test_sed.txt 

  * 字符集转化

    sed "y/abcd/EFGH/" test_sed.txt

  * 重复打印 第二行 且匹配模式

    sed "/2 the/p" test_sed.txt

  * 替换第二个/全局

    sed "/2 the/s/dog/cat/2" test_sed.txt

    sed "/2 the/s/dog/cat/g" test_sed.txt

  * 将结果保存到文件

    1.  ... \> file
    2.  sed ... | sed -n  "w file"

## Cut

cut-remove sections from each line of files

-f, --fields select  only  these  fields

--complement 提取指定字段之外的列 

-d  指定delimater(默认空格)

echo "nginx-1.20.2.tar.gz"|cut -f1-3 -d"."  //nginx-1.20.2

​	-fa,b,c abc三列

​	-fa-c a-c列





##  AWK

处理数据+产生格式化报表的语言。它认为文件的每一行是一条记录，记录与记录的分隔符为换行符，字段(列)与 字段的分隔符默认是空格或tab制表符。

### 语法 

awk [options][BEGIN] [[condition]{program}][END]{file}

#### options

-F fs 字段分隔符 默认空格

-v var=vale 定义awk程序中使用的变量和默认值

-f file 指定读取的文件

#### program

需要用'' 而非""    ""不会解析变量

运行优先级： BEGIN （开始执行数据流之前执行）-> program（如何处理数据流）-> END （处理完数据流后执行）

#### 操作

* 提取列

  $0 整行文本

  $N 文本行中的第N个数据字段

  $NF 文本行中的最后一个数据字段 

  awk [-F  ":"] '{print $3 "-" $5 "-" $NF}' /etc/passwd

* 提取行

  awk 'NR==3{print $NF}' 11.txt

* BEGIN/END

  不需要数据流

  awk 'END{print "Hello Wold"}'

* 高级用法

  - 内存使用率计算

    head -2 /proc/meminfo

    ​	MemTotal:        4048192 kB
    ​	MemFree:         3240152 kB

    head -2 /proc/meminfo|awk 'NR==1{total=2}; NR==2{free=2};print (total-free)*100/total "%"}'

  - 支持变量，数组

    awk 'BEGIN{array[0]=1; print array[0]}'

  - 支持运算

    ~ 模糊匹配  !~ 模糊不匹配

    awk -F ":" '$1 ~ "ro" {print $0}' /etc/passwd

  - **环境变量 不能用于自定义**

    | FIELDWIDTHS | 定义每个数据字段精确宽度 |
    | ----------- | ------------------------ |
    | FS          | 列分隔符  相当于-F       |
    | OFS         | output File Seperator    |
    | RS          | row seperator            |
    | ORS         | output row seperator     |

    awk 'BEGIN{FS=":",OFS="-"}NR==1{print $1,$2,$3}' /etc/passwd

    行列转化： 

    ​	awk 'BEGIN{RS=""}	{print $1,$2,$3}' /etc/passwd

    ​	以空白行当作行分割符（多个连续的当作一个），此时会将\n作为列 分隔符

  - 流程控制

    - if

      seq 1 10 | awk '{if($1>5) print $0}' 

      seq 1 10 | awk '{if($1>5) print $0*2;else print $0/2}' 

    - for/while/do while

      获得每行的求和：

      awk '{sum=0;for(i=1;i<11;i++){sum+=$i} print sum}'  num

      获取所有行数据之和：

      awk -v sum=0 '{for(i=1;i<11;i++){sum+=$i}}END{print sum}'  num

      或

      awk 'BEGIN{sum=0}{for(i=1;i<11;i++){sum+=$i}}END{print sum}'  num

  - 打印行数

    awk 'END{print NR}' num

  - 打印最后一行

    awk 'END{print $0}' num

  - 打印文本列数（ 最后一行）

    awk 'END{print  NF}' num

* 



## WC

wc - print newline, word, and byte counts for each file

统计文件字节、字符、单词与行的数量

wc [OPTION]... [FILE]...
wc [OPTION]... --files0-from=F

### 参数

-c, --bytes
	仅显示字节数
-m, --chars
	仅显示字符数
-l, --lines
	仅显示行数
--files0-from=F
	从文件 F 中获取以 NULL 字符结尾的文件名作为输入，如果 F 等于连字符 -，则从标准输入读取
-L, --max-line-length
	显示文件中最长行的字符数
-w, --words
	显示单词数，单词以空格分隔
--help
	显示帮助信息并退出
--version
	显示版本信息并退出

### 例子

- wc /etc/passwd 

  43   52 1872 /etc/passwd   行数、单词数、字符数

- wc -l /etc/passwd  统计行数



## GCC

GNU开发的编程语言编译器

目前支持C,java,pascal,Objective c等

-o gcc处理过的文件存为file  可执行a.out;目标文件a.o;汇编文件	source.s

-c 只编译，不链接生成可执行文件

-g 在可执行文件中加入调试信息

![001](C:\Users\Administrator\Desktop\复习\素材\pic\linux\001.jpg)

### 使用make编译程序

* Makefile文件

  指定make编译的规则、生成目标、依赖的源文件

  规则是makefile的基本单元

* mak编译过程

  读取makefile，确定要生成的文件，建立依赖关系

  根据Makefile的规则进行编译、链接

* Hello World:

  main.c

  \#include <stdio.h>

  int main()
  {
      printf("Hello World");

  ​	return 0;

  }

  

  Makefile

  output:dependency_source

  ​	rule

  

   hello:main.o

  ​	gcc main.o -o hello

  main.o:main.c

  ​	gcc -c main.c 

  

  .PHONY:clean

  clean:
          rm main.o

  make clean

## 案例

1. 查看某个pid占用的内存

   awk 'BEGIN{sum=0;}{if(\$1 ~ "Rss:") sum+=$2}END{print sum}' smaps

2. 编写nginx安装脚本

   -X debug 如sh -X xxx.sh

   

   ```shell
   #安装用户 root
   check(){
     if [ $USER != "root" ]
       then
           echo "need to be root so that"
           exit 1
     fi
     # 检查wget
     #if [ ! -x /usr/bin/wget ]
     #  then
     #     echo "cannot find command /usr/bin/wget"
     #     exit 1
     #fi
     
     [ ! -x /usr/bin/wget ] && echo "cannot find command /usr/bin/wget" && exit 1
   }
   #安装前的准备 依赖包 源码包
   install_pre(){
     # 安装依赖
     if ! (yum -y install gcc-* pcre-devel zlib-devel 1>/dev/null)
       then
           echo "ERROR: yun install error"
           exit 1
     fi
   
     # 下载源码包
     if wget https://nginx.org/download/nginx-1.20.2.tar.gz 1>/dev/null
       then
           tar -zxvf nginx-1.20.2.tar.gz
           if [ ! -d nginx-1.20.2 ]
             then
                   echo "ERROR: not found nginx-1.20.2 "
                   exit 1
           fi
     else
           echo "ERROR: wget file nginx-1.20.2.tar.gz fail"
           exit 1
     fi
   }
   
   # 安装 
    install(){
     #1.进入source_dir
     cd /opt/$nginx_source_dir
     echo "nginx configure...."
     #2.运行配置脚本
     if ./configure --prefix=$nginx_install_dir 1>/dev/null
       then echo "nginx make..."
       #3.make 编译安装
       if make 1>/dev/null
           then echo "nginx install..."
           if make install 1>/dev/null
             then echo "nginx make install success"
           else
             echo "ERROR: nginx make install fail "
           fi
        else
           echo "ERROR: nginx make fail "
        fi
     else
       echo "ERROR: nginx configure fail "
     fi
   }
   
   #启动
   nginx_start(){
     if yum -y install elinks && $nginx_install_dir/sbin/nginx
       then echo "nginx start success"
       elinks http://localhost --dump
     else
       echo "nginx syop FAIL"
     fi
   }
   
   #交互callable
   
   ```

   上述代码中可以把之后可能变动的项提取出来成为变量

   nginx_pkg="nginx-1.20.2.tar.gz"
   nginx_source_dir=`echo $nginx_pkg | cut -f1-3 -d"."`
   nginx_install_dir='/usr/local/nginx'

   

   netstat -nptl

   -n, --numeric rather than domain names instead

   -p, --programs           display PID/Program name for socket
   s  如39630/nginx: master

   -t|--tcp  -u|--udp

   -l, --listening          display listening server sockets

    -a, --all                display all sockets (default: conne
   cted)

3. 备份mysql binlog日志到远程服务器

   ```shell
   #1. 确定binlog位置及备份时间间隔 每日
       # /var/lib/mysql
       # ls -a binlog.00*|tail -1 
     	or
   	mysql -uroot -p123456 -e "show master status"|egrep "binlog.[[:digit:]]*"|awk '{print $1}'
         
   #2. 带包binlog 以年-月-日_binlog.tart.gz格式
   #3. 生成校验码 md5sum
   #4. 以年-月-日格式 将校验码+binlog再次打包
   #5. scp 复制到备用服务器
   #6. 备用机解压并校验
      # 不完整 --- 发送邮件给管理员，要求手动备份
   ```

   ```sehll
   backup(){
   #1
   binlog_dir='/var/lib/mysql'
   backup_binlog=`mysql -uroot -p123456 -e "show master status"|egrep "binlog.[[:digit:]]*"|awk '{print $1}'`
   binlog_fullpath=$binlog_dir/$backup_binlog
   mysql -uroot -p123456 -e "flush logs"
   
   #2
   echo "taring binlog.tar.gz"
   tar -zcf `date +%F`.binlog.tar.gz $binlog_fullpath
   
   #3
   md5sum `date +%F`.binlog.tar.gz > `date +%F`_md5sum.txt
   
   #4
   #[ ! -d `date +%F` ]&&mkdir `date +%F`  mv
   tar -zcf `date +%F`.tar.gz `date +%F`.binlog.tar.gz `date +%F`_md5sum.txt
   
   #5
   #提前做ssh 证书信任 与目录创建
   scp `date +%F`.tar.gz liqingzhao@192.168.162.100:/opt/backup
   if [ $? -ne 0 ]
     then echo "ERROR: scp `date +%F`.tar.gz fail";exit 1
   fi
   
   ssh liqingzhao@192.168.162.100 "tar -zxvf /opt/backup/`date +%F`.tar.gz -C /opt/backup/`date +%F`"
   #ssh root@192.168.162.101 "cd /opt/backup/`date +%F`"
   ssh liqingzhao@192.168.162.100 "md5sum -c /opt/backup/`date +%F`__md5sum.txt"
   if [ $? -eq 0 ]
     then echo " md5sunm success"
   else
     echo " md5sunm fail"
   fi
   
   }
   
   ```

   date +%F    完整日期格式，等价于 %Y-%m-%d
   md5sum-Print or check MD5 (128-bit) checksums
   	-c, --check          read MD5 sums from the FILEs and check them

4. 新建user01-user20用户 密码随机6位 取值 a-zA-Z0-9且不能是单一的类型

   * 01 02 ...20

     ```shell
     for i in seq -s ' ' -w  1 20; do
     
       echo $i
     
     done
     
     ```

     

   * 获取a-z0-9

     ```shel
     for i in `seq 1 20`
       do
             echo $i|md5sum|cut -c1-6
     done
     
     ```

   * a-zA-Z0-9

     cat /dev/urandom|strings -6|egrep "^[a-zA-Z0-9]{6}$"|head -20

   ```shell
   for i in `seq -s " " -w 1 20`;do
    useradd user$i
   done
   
   
   if [ ! -e pwd_temp ];then
     touch pwd_temp
   fi
   cat /dev/urandom|strings -6|egrep "^[a-zA-Z0-9]{6}$"|head -20 > pwd_temp
   
   for i in `seq -s " " -w 1 20`;do
     pwd=`head -n $i pwd_temp|tail -1`
     
     echo $pwd|passwd --stdin user$i
     echo -e "user$i\t\t$pwd" >> user_add_result.txt #''不解释变量
   done
   
   cat user_add_result.txt
   
   ```

5. 猜数字

   ```shell
   base=$(($RANDOM%100+1))
   echo "设定值是 $base"
   read -p "请输入数字：" ch
   count=1
   # 可以不用转化 输入的就是整数
   #ch=`echo $c | awk '{print int($0)}'`
   while [[ $ch -ne $base ]];do
    if [ $ch -gt $base ];then
           echo "大了"
    elif [ $ch -lt $base ];then
           echo "小了"
    else break
    fi
    read -p "请输入数字：" c
    ch=`echo $c | awk '{print int($0)}'`
    echo "输入的数字是$ch"
    #count=$(($count+1))
    let count++
   done
   echo "猜了${count}次猜中"
   
   ```

   

## 使用过程中遇到的错误

1. ![7](C:\Users\Administrator\Desktop\复习\素材\pic\redis\7.jpg)

    

   查询相关的资料发现是这是由于在编辑该文件的时异常退出，而vim在编辑文件时会创建一个交换文件swap file以保证文件的安全性。

   所以在再每次打开这个文件都会出现这个警告，为了去掉这个警告，我们只需要**删除这个swap文件**即可。








## 附件1 常用的命令

ls list
cp mv 都能重命名
pwd print working directory
tail +20 file
grep 文本搜索工具
	egrep grep -E 正则表达式
	fgrep 字符串字面量匹配搜索
find  文件查找搜索
startX x-windows 运行在unix的视窗系统
uname [option] 显示系统信息
resize2fs 调整文件系统大小 ext2/ext3/ext4
df disk free 显示磁盘空间使用情况
fdisk 磁盘分区的工具
lsdlk 列出所有可用块设备的信息
hdparm 显示与设定硬盘参数
vfextend 扩展卷组
tftp  传输文件
	tpftp ip
	get file
	put file
curl  c语言编写 利用url规则的文件传输工具 支持http/https/ftp等协议
ssh  secure shell openssh套件中的客户端连接工具,给予ssh加密协议实现安全的远程登录服务器
netstat 显示各种网络相关信息
	-a 查看所有连线中的socket
	-p 显示正在使用socket的程序
	-n 直接使用ip地址，不通过dns
	-u udp
	-i 网卡列表信息
dhclient 动态的配置网络接口的网络参数
ping 测试主机之间网络连通性
	-c 发送报文次数
	-i 时间间隔
	-R 路由过程 
ifconfig network interface configuration 显示和配置网络参数
	down 关闭 up打开
	centeros只有ip addr没有ifconfig
mount命令用于**加载文件系统到指定的加载点**。此命令的最常用于挂载cdrom，使我们可以访问cdrom中的数据，因为你将光盘插入cdrom中，Linux并不会自动挂载，必须使用Linux mount命令来手动完成挂载
压缩：

- zip zipfile source

  -q silent

  -r recusive

- unzip zipfile

  -d  指定解压目录

- gzip gz

  -d 解压

  -r recusive

- tar 

  -c --create

  -C --directory

  -z gzip

  -f 指定备份文件

  -v --verbo--verbose

  -x  --extract

  tar -czvf  gzfile source

  tar -zxvf  gzfile 

top 动态持续监听进程运行状态

- 在top的显示窗口
  - P 按CPU使用率排序
  - M 按内存使用率排序
  - N 按照PID排序