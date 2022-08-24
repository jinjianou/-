## 集群部署

0. 多台机器 

   - jdk

   - /etc/hosts 配置结点映射文件

   - /etc/profile  ~/.bashrc 环境变量

     **/etc/profile**： **此文件为系统的每个用户设置环境信息**,当用户第一次登录时,该文件被执行。是系统全局针对终端环境的设置，它是login时最先被系统加载的，是它调用了/etc/bashrc

     **~/.bashrc**:**是用户相关的终端（shell）的环境设置**，通常打开一个新终端时，默认会load里面的设置，在这里的设置不影响其它人。

     **/etc/bashrc**: 是**系统全局针对终端环境的设置**，修改了它，会影响所有用户的终端环境，这里一般配置终端**如何与用户进行交互的增强功能等**（比如sudo提示、命令找不到提示安装什么包等），新开的终端，已经load了这个配置，最后才load用户自己的 ~/.bashrc。

*  时钟同步

  > yum install -y ntp
  >
  > ntpdate cn.pool.ntp.org
  >
  > 或
  >
  > ntpdate ntp[1-7].aliyun.com  //选择一个时钟源 如ntp1
  >
  > clock -w

* zookeeper

  修改配置

  >broker.id=0...n # 根据序列来
  >
  >listeners
  >
  >zookeeper.connect=CenterOSA:2181,CenterOSB:2181.....

