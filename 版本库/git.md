

# 特点

版本控制：是一种记录一个或若干文件内容变化，以便将来查阅特定版本修订情况的系统 

发展:本地版本控制系统=>集中版本控制系统=>分布式版本控制系统

**基于差异（delta-based）** 的版本控制 : 将它们存储的信息看作是一组基本文件和每个文件随时间逐步累积的差异 

git（**快照流** ）：对当时的全部文件创建一个快照并保存这个快照的索引 

- 分布式

  分布式版本控制系统根本没有“中央服务器” ，每个人的电脑上都有一个完整的版本库

  安全 

- 全量

  每一次的提交都可以作为一个独立的版本使用 

- 近乎所有操作都是本地执行

- Git 保证完整性 Git 数据库中保存的信息都是以文件内容的哈希值来索引 

  

# 概念

​	工作区是对项目的某个版本独立提取出来的内容。 这些从 Git 仓库的压缩数据库中提取出来的文件，放在磁盘上供你使用或修改。

​	暂存区是一个文件，保存了下次将要提交的文件列表信息，一般在 Git 仓库目录中。 按照 Git 的术语叫做“索引”，不过一般说法还是叫“暂存区”。

​	Git 仓库目录是 Git 用来保存项目的元数据和对象数据库的地方。 这是 Git 中最重要的部分，从其它计算机克隆仓库时，复制的就是这里的数据。

![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/1.jpg?lastModify=1657032719)

​	**已提交（committed）**、**已修改（modified）** 和 **已暂存（staged** ）

​	已修改表示修改了文件，但还没保存到数据库中。

​	已暂存表示对一个已修改文件的当前版本做了标记，使之包含在下次提交的快照中。

​	已提交表示数据已经安全地保存在本地数据库中。![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/2.png?lastModify=1657032719)

​	

# 命令

## 查看/配置账号 git config

git config --list --show-origin 查看所有配置和所在文件

git config ${scope} user.name "xxx"

git config ${scope} [user.email] xxx

- -global(不推荐使用)  该命令只需要运行一次,之后无论你在该系统上做任何事情， Git 都会使用那些信息    ~/.gitconfig

- --system(当前用户)  

  安装目录下 Git/mingw64/etc/gitconfig

- --local(当前项目) 

  当前项目下的 ./git/config

  文本编辑器 ：git config --global core.editor emacs

- 获取帮助 man git-<verb> 或者 git <verb> --help（网页）git <verb> -h

- .ignore 忽略掉不需要git管理的文件

  ```
  # 忽略所有的 .a 文件
  *.a
  
  # 但跟踪所有的 lib.a（但忽略其他所有文件），即便你在前面忽略了 .a 文件
  !lib.a
  
  # 只忽略当前目录下的 TODO 文件，而不忽略 subdir/TODO
  /TODO
  
  # 忽略任何目录下名为 build 的文件夹
  build/
  
  # 忽略 doc/notes.txt，但不忽略 doc/server/arch.txt
  doc/*.txt
  
  # 忽略 doc/ 目录及其所有子目录下的 .pdf 文件
  doc/**/*.pdf
  ```

  ## 获取仓库

  - git-init - Create an empty Git repository or reinitialize an existing one 

  - git clone [<options>][--] <repo> [<dir>]

    repo 远程地仓库地址

    dir 本地仓库目录

    git clone <https://github.com/alibaba/arthas.git> ../arthas_github/

    1. error： git clone xxxxx OpenSSL SSL_read: SSL_ERROR_SYSCALL, errno 10054

       ssh-keygen -t rsa -C '[1969972932@qq.com](mailto:1969972932@qq.com)' 把公钥给代码托管平台  **这时候clone得用ssh url**

       **如果本地是https 源，那么就修改git 仓库地址** 

       1. git remote origin set-url [url]

       2. **git remote rm origin**

          **git remote add origin [url]**

    2. The authenticity of host 'github.com (140.82.121.4)' can't be established.

       ssh-keyscan github.com >> ~/.ssh/known_hosts


## 跟踪远程分支

```
git branch --set-upstream-to=origin/branch branch
或git branch -b origin/branch branch

git remote add  <name 如origin> <远程仓库地址> 增加远程仓库
```

一般我们就用git push --set-upstream origin branch_name来在远程创建一个与本地branch_name同名的分支并跟踪；利用git checkout --track origin/branch_name来在本地创建一个与branch_name同名分支跟踪远程分支。

## 文件状态

你工作目录下的每一个文件都不外乎这两种状态：**已跟踪** 或 **未跟踪**。 已跟踪的文件是指那些被纳入了版本控制的文件 ，否则未跟踪

- 查看文件状态

  新建文件 git status

   -s, --short           show status concisely

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/2.jpg?lastModify=1657032719)

- git add a.txt > git status

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/3.jpg?lastModify=1657032719)

- 此时又对a.txt做修改![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/4.jpg?lastModify=1657032719)

  此时commit的话 只会提交add之后 new file的版本

- 查看文件差异

  - 工作目录中当前文件和暂存区域快照之间的差异   git diff
  - 比对已暂存文件与最后一次提交的文件差异 git diff --stage

## 提交 

git commit -m "version1"

- git commit -a 

  跳过暂存区(git add) 自动把所有已经**跟踪过的文件**暂存起来一并提交 
  
- 移除文件（从暂存区删除）

  - 工作目录存在 git rm --cached a.txt

  - 工作目录也删除 git rm  a.txt

    error：file has changes staged in the index  加上-f  force remove

  因为 Git 有它自己的文件模式扩展匹配方式  所以模式要写成这种 git rm log/*.log

- 重命名 git mv file_from file_to

  实际执行了

  ```
  $ mv README.md README
  $ git rm README.md
  $ git add README
  ```

- 查看commit历史 git log

  不传入任何参数的默认情况下，`git log` 会按时间先后顺序列出所有的提交，最近的更新排在最上面 

  - -p --patch -n 		显示每次提交所引入的差异（按 **补丁** 的格式输出）,并限制显示的日志条目数量 n

  - --stat  每次提交的简略统计信息  

  - 格式 git log --pretty=format:"%h - %an, %ar : %s"

    | 选项  | 说明                                          |
    | ----- | --------------------------------------------- |
    | `%H`  | 提交的完整哈希值                              |
    | `%h`  | 提交的简写哈希值                              |
    | `%T`  | 树的完整哈希值                                |
    | `%t`  | 树的简写哈希值                                |
    | `%P`  | 父提交的完整哈希值                            |
    | `%p`  | 父提交的简写哈希值                            |
    | `%an` | 作者名字                                      |
    | `%ae` | 作者的电子邮件地址                            |
    | `%ad` | 作者修订日期（可以用 --date=选项 来定制格式） |
    | `%ar` | 作者修订日期，按多久以前的方式显示            |
    | `%cn` | 提交者的名字                                  |
    | `%ce` | 提交者的电子邮件地址                          |
    | `%cd` | 提交日期                                      |
    | `%cr` | 提交日期（距今多长时间）                      |
    | `%s`  | 提交说明                                      |

  - --graph 以图像方式展示

  - 限制输出选项

## 撤销操作

- 提交完了才发现漏掉了几个文件没有添加，或者提交信息写错了  git commit --amend

  ```
  $ git commit -m 'initial commit'
  $ git add forgotten_file
  $ git commit --amend
  ```

  最终你只会有一个提交 

- 取消暂存区的文件

  ```
  git reset HEAD file
  ```

- 取消对文件的修改，恢复到上一次提交（或clone时的样子）

  git checkout file

  一般是已追踪但还在工作区（在暂存区先撤回工作目录）

## 远程仓库

- 查看已经配置的远程仓库服务器  git remote 

  如果你已经克隆了自己的仓库，那么至少应该能看到 origin 

   -v, --verbose 

- 查看某个远程仓库服务器 git remote show origin

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/5.jpg?lastModify=1657032719)

- 添加 git remote add <shortname> <url> 

- 拉取数据

  ```
  git fetch <remote>
  ```

  如果你使用 `clone` 命令克隆了一个仓库，命令会自动将其添加为远程仓库并默认以 “origin” 为简写。 所以，`git fetch origin` 会抓取克隆（或上一次抓取）后新推送的所有工作。 必须注意 `git fetch` 命令只会将数据下载到你的本地仓库——它并不会自动合并或修改你当前的工作。 当准备好时你必须手动将其合并入你的工作。 

  如果你的当前分支设置了跟踪远程分支 那么可以用 `git pull` 命令来自动抓取后合并该远程分支到当前分支  抓取数据并自动尝试合并到当前所在的分支 

## 推送到远程仓库

git push <remote> <branch> 

克隆时服务器自动设置成orign

git push <remote> <branch> 

注意: 

1. 如果本地分支与远程分支不同名  

   可通过git branch -vv 查看所有分支版本

   git push origin HEAD:master

   每次都要这样写,有点麻烦,修改配置  git config --global push.default upstream   之后只要git push就行

2. **push之前先pull**

   

- 远程仓库重命名 git remote rename old new

- 删除 git remote remove origin

  一旦你使用这种方式删除了一个远程仓库，那么所有和这个远程仓库相关的远程跟踪分支以及配置信息也会一起被删除 
  
- 打标签

  - 列出标签 git tag

  - Git 支持两种标签：轻量标签（lightweight）与附注标签（annotated）。

    轻量标签很像一个不会改变的分支——它只是某个特定提交的引用。

    而附注标签是存储在 Git 数据库中的一个完整对象， 它们是可以被校验的，其中包含打标签者的名字、电子邮件地址、日期时间， 此外还有一个标签信息，并且可以使用 GNU Privacy Guard （GPG）签名并验证。 通常会建议创建附注标签，这样你可以拥有以上所有信息。但是如果你只是想用一个临时的标签， 或者因为某些原因不想要保存这些信息，那么也可以用轻量标签。

    附注标签 git tag -a vxx -m "xxx" 查看标签 git show tag

    轻量标签 不需要option

- 别名

  git config --global alias.xx command

  输入时 git xx

- 分支

  Git 保存的 是一系列不同时刻的 **快照**  

  Git 的分支，其实本质上仅仅是指向提交对象的可变指针 

  

 ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/3.png?lastModify=1657032719)

- 创建分支 git branch xxx

  创建一个新分支就相当于往一个文件中写入 41 个字节(长度为 40 的 SHA-1 值字符串 + 1 个换行符 ) 

  这会在当前所在的提交对象上创建一个指针。 

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/4.png?lastModify=1657032719)

  Git有一个名为 HEAD 的特殊指针 指向当前所在的本地分支(将 `HEAD` 想象为当前分支的别名 )

- 分支切换git checkout xxx  不切`HEAD` 

  git checkout -b <newbranchname>  切`HEAD` 

- HEAD 分支随着提交操作自动向前移动 ![(C:\Users\Administrator\Desktop\复习\素材\pic\git\4.png)

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/5.png?lastModify=1657032719)

- 新建分支时要注意

  1. 要留意你的工作目录和暂存区里那些还没有被提交的修改， 它可能会和你即将检出的分支产生冲突从而阻止 Git 切换到该分支。 最好的方法是，**在你切换分支之前，保持好一个干净的状态**。 有一些方法可以绕过这个问题（即，暂存（stashing） 和 修补提交（commit amending）） 
  2. 当你切换分支的时候，Git 会重置你的工作目录， 

- 分支合并

  - git merge A  

    1. 情况一 把A合并到当前分支上，如果当你试图合并两个分支时， 如果顺着一个分支走下去能够到达另一个分支 （直接后继） 那么 Git 在合并两者的时候  只会简单的将指针向前推进（指针右移）  fast-forward 

    ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/6.png?lastModify=1657032719)

    ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/7.png?lastModify=1657032719)

    1. 不满足情况一 需要做**三方合并**

       Git 会使用两个分支的末端所指的快照（`C4` 和 `C5`）以及这两个分支的公共祖先（`C2`） 

       Git 将此次三方合并的结果做了一个新的快照并且自动创建一个新的提交指向它。 这个被称作一次合并提交，它的特别之处在于他有不止一个父提交 

       ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/8.png?lastModify=1657032719)

       ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/9.png?lastModify=1657032719)

       

- 合并时发生冲突

  1. git status 查看那些因包含合并冲突而处于未合并（unmerged）状态的文件 
  2. 可以采用 git mergetool图形化界面解决冲突 处理完毕后 git add  标记为冲突已解决 
  3. 当所有冲突都解决了 git commit

- 删除分支 git branch -d xxx‘

  未合并删除会失败

- 获取当前所有分支列表 git branch 

  -v 查看每一个分支及最后一次提交

  --merged  已经合并到当前分支的分支  

  --no-merged 尚未合并到当前分支的分支 

- 分支类型

  - 长期分支 保证稳定性
  - 主题分支 短期分支，对任何规模的项目都适用 

![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/10.png?lastModify=1657032719)

- 远程分支

  - git branch --set-upstream-to=origin/remote_branch  your_branch 

  或

  git push -u origin remote_branch  

  本地分支与远程分支关联 ，关联目的是在执行git pull, git push操作时就不需要指定对应的远程分支 

  

  远程引用是对远程仓库的引用（指针），包括分支、标签等等   `git ls-remote <remote>` 或者 git remote show <remote> 

  远程跟踪分支是远程分支状态的引用  以 `<remote>/<branch>` 的形式命名 

  假设你的网络里有一个在 `git.ourcompany.com` 的 Git 服务器。 如果你从这里克隆，Git 的 `clone` 命令会为你自动将其命名为 `origin`，拉取它的所有数据， 创建一个指向它的 `master` 分支的指针，并且在本地将其命名为 `origin/master`。 Git 也会给你一个与 origin 的 `master` 分支在指向同一个地方的本地 `master` 分支，这样你就有工作的基础 

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/11.png?lastModify=1657032719)

  你保持不与 `origin` 服务器连接（并拉取数据），你的 `origin/master` 指针就不会移动 

  fetch时 本地与远程的工作可能分叉（期间自己和他人都有可能修改） 

- push

  git push <remote> <branch>  git push origin serverfix或者git push origin serverfix:remoteBranchName

  fetch的时候会获取  一个不可以修改的 `origin/serverfix` 指针 -> [new Branch]

- 跟踪分支

  是与远程分支有直接关系的本地分支   如果在一个跟踪分支上输入 `git pull`，Git 能自动地识别去哪个服务器上抓取、合并到哪个分支 

  当克隆一个仓库时，它通常会自动地创建一个跟踪 `origin/master` 的 `master` 分支 

  - 设置 git checkout -b <branch> <remote>/<branch>   

    ==  git checkout --track <remote>/<branch>  

    == git branch -u origin/serverfix

  - 查看所有跟踪分支 

    本地缓存的服务器数据  git branch -vv

- 拉取  git pull=fetch+merge

- 删除远程分支  git push origin --delete serverfix

- 整合分支-变基

  `git rebase <basebranch> <topicbranch>`   topicbranch ->basebranch

  原理是首先找到这两个分支（即当前分支 `experiment`、变基操作的目标基底分支 `master`） 的最近共同祖先 `C2`，然后对比当前分支相对于该祖先的历次提交，提取相应的修改并存为临时文件， 然后将当前分支指向目标基底 `C3`, 最后以此将之前另存为临时文件的修改依序应用。  

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/12.png?lastModify=1657032719)

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/13.png?lastModify=1657032719)

  ```
  git rebase --onto master server client
  ```

  取出 `client` 分支，找出它从 `server` 分支分歧之后的补丁， 然后把这些补丁在 `master` 分支上重放一遍，让 `client` 看起来像直接基于 `master` 修改一样 

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/14.png?lastModify=1657032719)

  ![img](file://C:/Users/Administrator/Desktop/%E5%A4%8D%E4%B9%A0/%E7%B4%A0%E6%9D%90/pic/git/15.png?lastModify=1657032719)

  风险： **如果提交存在于你的仓库之外，而别人可能基于这些提交进行开发，那么不要执行变基** （ensure commit up_to_date）

# github

- 如果你想要参与某个项目，但是并没有推送权限，这时可以对这个项目进行“派生（Fork）”。 当你“派生”一个项目时，GitHub 会在你的空间中创建一个完全属于你的项目副本，且你对其具有推送权限 
- 修改完成之后 可以create pull request,让作者选择合并你的代码



# 错误

* src refspec master does not match any

  1. 本地git仓库目录下为空
  2. 没有add
  3. 本地仓库add后未[commit](https://so.csdn.net/so/search?q=commit&spm=1001.2101.3001.7020)
  4. git init错误

  

* fatal: sha1 file '<stdout>' write error: Broken pipe KiB/s
  fatal: the remote end hung up unexpectedly
  fatal: the remote end hung up unexpectedly

  当推送大量数据时（初始推送大型存储库，使用非常大的文件进行更改）
  git config --global http.postBuffer 157286400