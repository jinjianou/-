- Error:Kotlin: Module was compiled with an incompatible version of Kotlin. The binary version of its metadata is 1.5.1, expected version is 1.1.15.

  解决: build->rebuild project

- .iml缺失

  解决：maven reimport

- .idea 缺失

  解决：重启

- 启动类 nothig here

  解决：maven clean + compile   invalidate caches/restart

  实在不行 新建个application配置并将之启动

- idea中模块文件夹右下角没有蓝色小方块

  解决： project structure ->  import module -> import module from maven

- pom文件显示橘色，是因为在右侧maven中没有指定pom文件

  解决：添加pom文件

- 启动配置参数缺失

  解决： use classpath of module -> dlvbusi-backend-service

- Failed to execute goal org.apache.maven.plugins:maven-resources-plugin:3.2.0:resources (default-resources) on project redis-demo: Input length = 1 -> [Help 1]

  解决：修改或添加version或者 ignore爆红