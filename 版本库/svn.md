## 安装

* TortoiseSVN 客户端

  * command line client tools : entire feature.....

* VisualSVN 服务端

  * 配置安装路径和仓库路径 端口80 不走ssl
  * 安装完毕，右键Repositories -> create new Repository
    * regular fsfs
    * empty repository
    * all user ... access

  * create user

  * create group 把user加进去

  * 修改配置文件 

    * Repository_location\repo_instance\conf\svnserve.conf 

      anon-access = read
       auth-access = write

    * Repository_location\repo_instance\conf\passwd

      root=123456

    * Repository_location\repo_instance\conf\authz

      test(group)=（root）user1，user2...

      (root)user=rw

      @test（group）=rw

  * 测试 浏览器键入url account/passwd

## 使用

* 新建本地仓库

  * create repository here

    the folder is empty and not write protected

  * 选中项目文件夹 checkout 

* 更新

  * 绿色小勾 已经是最新版本或每更改过
  * 红色感叹号 更改过未曾提交到服务器
  * 没有标识 新增待提交到服务器

* 提交

  * 第一次提交到服务端（服务端为空） tortoisesvn->import
    * 导入成功后，还得重新检出，重新检出的项目才是受SVN控制的（在其他顶级目录下 如E: 默认会检出**与仓库名相同**的文件夹）
  * svn add->svn commit

* 如果文件和文件夹状态没有

  右键->TortoiseSVN->Settings->Icon Overlays->Status cache

* revert 针对本次修改（距离上一次update）

  update to revision 回撤到历史版本（工作目录本次修改保留，remote不生效）

* branches

  trunk文件夹: 主干，我们一般把项目提交到此文件夹里面,在trunk中开发。

  branches文件夹：分支，我们一般把那些需要打分支,但是有可能会修改的项目代码，打分支到此目录。

  tags文件夹：分支，我们一般把那些阶段性(如迭代各期)的项目代码,打分支到此目录。

  如 path: branches/dev

  **check create intermediate folders**

  **check working copy to new branch/tag**

  **切换分支前必须先提交代码或暂存**

* merge

  A 上 点选 merge B  B->A

  * merge a range of revisions 其他分支的版本

    merge后是added to be committed

  * merge two different trees

     This method covers the case when you want to merge the differences of two different branches into your working copy.

    只对当前工作目录有修改，two different trees不会更改

* 删除分支

  ```
  repo browser -> 左侧branches删除需要的分支
  ```

* 切换版本库 relocate

* 代码暂存 shelve 存储到本地的数据  一个shelve只能存一个分支数据（同名后面覆盖前者）

* patch 

  在working copy的修改提交前，可以利用客户端自带的create patch功能，将本次代码修改生成一个patch。然后在任何与修改前同版本的working copy上，通过apply patch将生成的patch进行应用以实现复刻。这么做的优点是只需要维护一个基线版版本


## idea集成svn

1. setting->vesion control->subversion  

   * tortoiseSvn_path
   * check use  custom configuration directory

2. vcs->subversion

   * update

   * commit

   * 忽略上传target

     set Property  key:  svn:ignore  value: target

## 问题

1. working copy xxx locked

   由于上次提交命令失败，导致整个文件夹下都被锁定了，根据提示cleanup一下整个目录

2. 