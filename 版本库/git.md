* git branch --set-upstream-to=origin/remote_branch  your_branch 

或

git push -u origin remote_branch  

本地分支与远程分支关联 ，关联目的是在执行git pull, git push操作时就不需要指定对应的远程分支 









# 错误

* src refspec master does not match any

  1. 本地git仓库目录下为空
  2. 没有add
  3. 本地仓库add后未[commit](https://so.csdn.net/so/search?q=commit&spm=1001.2101.3001.7020)
  4. git init错误

  