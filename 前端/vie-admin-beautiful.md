# 介绍

可以在完全不依赖后台的情况下独立开发完成项目，以及接口自动模拟生成，支持JAVA、PHP、NODE、.NET、Django等常用所有后台对接

## 安装

```
git clone -b vue3.0-antdv https://github.com/chuzhixin/vue-admin-beautiful.git

```

问题1: 'vue-cli-service' 不是内部或外部命令，也不是可运行的程序
解决:

问题2: GET https://registry.npmmirror.com/vue-cli-service - [NOT_FOUND] vue-cli-service not found

解决:看看有没有配置代理

​	npm config get registry /proxy

npm config set registry https://registry.npm.taobao.org

npm config set proxy="http://192.168.1.1:8080"