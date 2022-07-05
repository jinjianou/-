# 基础概念

## Gradle 基础

### distibution

​	services.gradle.org 

​	docs.gradle.org 	

 #### src

​	相当于github上源码打包，无法直接运行

#### bin

​	可运行的文件，不包含docs（如samples）

​	bin+lib  启动jvm加载lib下的jar包  

#### all

​	可运行的文件+docs

### wrapper

The Wrapper is a script that invokes a declared version of Gradle, downloading it beforehand(事先) if necessary. As a result, developers can get up and running with a Gradle project quickly without having to follow manual installation processes  

- Standardizes a project on a given Gradle version, leading to more reliable and robust builds.
- Provisioning a new Gradle version to different users and execution environment (e.g. IDEs or Continuous Integration servers) is as simple as changing the Wrapper definition.

![1644667941277](C:\Users\Administrator\Desktop\复习\素材\pic\gradle\1.png)

$ cd tmp

$ mkdir wrapper-demo

$ ./gradle-5.3.1/bin/gradle wrapper (7+ 需要先gradle init to create a new gradle build)

$ tree .

├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties

├── gradle-5.3.1
│   ├── ....

├── gradle-7.4

│   ├── ....

├── gradlew
├── gradlew.bat

- gradle-wrapper.jar  download specific gradle distributes

- gradle-wrapper.properties

  distributionBase=GRADLE_USER_HOME
  distributionPath=wrapper/dists
  distributionUrl=https\://services.gradle.org/distributions/gra
  dle-7.4-bin.zip

  zipStoreBase=GRADLE_USER_HOME
  zipStorePath=wrapper/dists

  * **GRADLE_USER_HOME**

    在windows下是%USERPROFILE%/.gradle

    **在linux下是$HOME/.gradle，例如~/.gradle**

  * zipStoreBase/zipStorePath zip包的安装路径

- ./gradlew xxx(如help) 可以在没有装过gradle的机器上build（需要scp -r wrapper-demo）

  * Downloading https://services.gradle.org/distributions/gradle-5.3.1-bin.zip
  * gradle build
  * 在GRADLE_USER_HOME/wrapper/dists下生成gradle distribution

### Daemon

可选 --no-daemon

默认情况，与maven每次请求都启动一个JVM不同，每个gradle请求会创建一个非常轻量级的client jvm（完成后销毁），一般用于查询并接受Daemon jvm（被缓存，一直存在）。client jvm与Daemon jvm需要保持兼容性

## Groovy基础

​	运行在JVM之上的脚本语言，强类型，支持动态调用（相当于java中的反射调用）

### 动态调用与MOP

元信息=元方法 `MetaMethod` 和元属性 `MetaProperty` 

元编程依赖一种约定 —— 那就是元对象协议 Meta Object Protocol，简称 MOP。Groovy 的 MOP 本身很简单 —— 仅仅用一个 `groovy.lang.MetaObjectProtocol ` 接口规范了如何获取元信息，设置元信息。因此我们的关注点也不在 MOP 本身，而是围绕 MOP 的各种应用：**方法拦截，方法合成，方法注入**

### 闭包

一个函数和对其周围状态（**lexical environment，词法环境**）的引用捆绑在一起（或者说函数被引用包围），这样的组合就是**闭包**（**closure**）。也就是说，闭包让你可以在一个内层函数中访问到其外层函数的作用域 

# Gradle构建

## Build Lifecycle

A Gradle build has three distinct phases.

- Initialization

  Gradle supports single and multi-project builds. During the initialization phase, Gradle determines which projects are going to take part in the build, and creates a [Project](https://docs.gradle.org/current/dsl/org.gradle.api.Project.html) instance for each of these projects.

- Configuration

  During this phase the project objects are configured. The build scripts of *all* projects which are part of the build are executed.

- Execution

  Gradle determines the subset（子集） of the tasks（最小执行单位）, created and configured during the configuration phase, to be executed. The subset is determined by the task name arguments passed to the `gradle` command and the current directory. Gradle then executes each of the selected tasks.

## Hello World

```
task('hello_world',{
    println('hello World')

    doLast {
        println('executing Hello World')
    }
})
```

## Gradle的核心模型

- Project
- Task
- hook

# Building Java Projects with Gradle

1. dependsOn

   ```
   task('first',{
       println('configurating')
   
       doLast {
           println('executing first')
       }
   })
   
   (1..10).each {i->
       task('task'+i){
           if((i&1)==1){
               dependsOn('first')
           }
           doLast {
               println('executing task'+i)
           }
       }
   }
   ```

   > 偶数
   >
   > Configure project :
   > configurating

   > Task :task2
   > executing task2

   奇数：

   > Configure project :
   > configurating

   > ***Task :first***
   > ***executing first***

   > Task :task3
   > executing task3

2. 自定义插件

   ```
   class MyPlugin implements Plugin<Project>{
       @Override
       void apply(Project project) {
           (1..10).each {i->
               project.task('task'+i){ //task->project.task
                   if((i&1)==1){
                       dependsOn('first')
                   }
                   doLast {
                       println('executing task'+i)
                   }
               }
           }
       }
   }
   //apply([plugin:MyPlugin])
   apply plugin:MyPlugin //sugar
   ```

3. 依赖

   ```
   import org.apache.commons.lang3.StringUtils;
   
   //configuration phase
   buildscript {
       repositories {
           mavenCentral()
       }
       dependencies {
           classpath group: 'org.apache.commons', name: 'commons-lang3', version: '3.9'
       }
   }
   
   
   //execution phase- compileJava
   apply plugin:'java'
   repositories {
       mavenCentral()
   }
   dependencies {
       implementation group: 'org.apache.commons', name: 'commons-lang3', version: '3.3.2'
   }
   
   //StringUtils from build script
   if(StringUtils.isNotBlank("xx")){
   
   }
   ```

4. 导入插件

   方式1：

   ```
   buildscript {
       repositories {
           mavenCentral()
       }
       dependencies {
           //method1
           classpath group: 'com.myCompny.plugins',name:'myPlugin',version:"1.0.0"
       }
   }
   apply plugin: 'myPlugin'
   ```

   方式2：

   ​	默认在外层build.gradle执行的时候，约定如果遇到buildSrc(先把这个目录设置为模块)会先build这个模块，并将buildSrc/lib/jar自动隐式添加到外层的buildScript中

5. 发布插件

   1. apply pugin 'xxx.yyy.zzz'
   2. 在buildScript中找对应dependencies 
   3. 在META-INF中找到xxx.yyy.zzz.properties
   4. 在根据文件内容找到指定类，JVM加载
   5. 实例化调用 apply方法

## download Gradle 

- [SDKMAN ](https://sdkman.io/)   software development kit manager

- [Homebrew](https://brew.sh/) (brew install gradle)  **The Missing Package Manager for macOS (or Linux)** 

- git clone <https://github.com/spring-guides/gs-gradle.git> 

  或者 <git://github.com/spring-guides/gs-gradle.git> 

- <https://www.gradle.org/downloads>.  

## install Gradle

* 安装 homebrew

  yum install git -y

  * 查看安装到了哪里

  rpm -qa|grep git

  rpm -ql git-1.8.3.1-23.el7_8.x86_64|egrep -v "^/usr/share"

  /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

  ```
  brew install gradle
  ```

* 安装 sdkman

  * curl -s "https://get.sdkman.io" | bash 

    * 修改安装目录 

      export重启失效

      ```
      export SDKMAN_DIR="/usr/local/sdkman" && curl -s "https://get.sdkman.io" | bash
      ```

  * source "$HOME/.sdkman/bin/sdkman-init.sh"

  * 查看最新软件 sdk version

  * 安装软件 sdk install gradle 7.4 [本地包路径]

  * 支持的软件 sdk list

  * 软件的版本 sdk list gradle

  * 卸载  sdk uninstall gradle 7.4

  * 选择一个版本用于当前终端  sdk use gradle 7.4

  * 查看当前使用版本 sdk current gradle 

  * 升级软件版本  sdk upgrade springboot 

  * sdkman! 版本升级 sdk selfupdate [force]

## verify

![1644667941277](C:\Users\Administrator\Desktop\复习\素材\pic\gradle\1.jpg)

## Find out what Gradle can do

cd project directory which contains build.gradle file 

$ gradle tasks

**Build Setup tasks**

​		init - Initializes a new Gradle build.
		wrapper - Generates Gradle wrapper files.

**Help tasks**

​	buildEnvironment - Displays all buildscript dependencies declared in root project 'gradleDemo'.
	dependencies - Displays all dependencies declared in root project 'gradleDemo'.
	dependencyInsight - Displays the insight into a specific dependency in root project 'gradleDemo'.
	help - Displays a help message.
	javaToolchains - Displays the detected java toolchains.
	outgoingVariants - Displays the outgoing variants of root project 'gradleDemo'.
	projects - Displays the sub-projects of root project 'gradleDemo'.
	properties - Displays the properties of root project 'gradleDemo'.
	tasks - Displays the tasks runnable from root project 'gradleDemo'.

​	To see all tasks and more detail, run gradle tasks --all

​	To see more detail about a task, run gradle help --task <task>



Even though(尽管) these tasks are available, they don’t offer much value without a project build configuration. As you flesh out(充实) the `build.gradle` file, some tasks will be more useful. The list of tasks will grow as you add plugins to `build.gradle`, so you’ll occasionally（偶尔） want to run **tasks** again to see what tasks are available. 

add plugin in build.gradle file

```
apply plugin: 'java'
```

### 命令

* link:initial/build.gradle[]

  

* gradle build 

  This task compiles, tests, and assembles the code into a JAR file 

  output build folder

  including these three notable folders:

  - *classes*. The project’s compiled .class files.

    The classes folder has .class files that are generated from compiling the Java code .

    doesn’t have any **library dependencies**, so there’s nothing in the **dependency_cache** folder. 

  - *reports*. Reports produced by the build (such as test reports).

  - *libs*. Assembled project libraries (usually JAR and/or WAR files).

    The libs folder should contain a JAR file that is named after the project’s folder. 

* 

# Build your project with Gradle Wrapper

The Gradle Wrapper is **the preferred way of starting a Gradle build** 

## install 

**gradle wrapper --gradle-version 6.0.1**

```
└── <project folder>
    └── gradlew
    └── gradlew.bat
    └── gradle
        └── wrapper
            └── gradle-wrapper.jar
            └── gradle-wrapper.properties
```

**./gradlew build**

The first time you run the wrapper for a specified version of Gradle, it downloads and caches the Gradle binaries for that version. 

**No value has been specified for property 'mainClassName'.**

Add this to your `build.gradle` file. 

```
apply plugin: 'application'
mainClassName = 'hello.HelloWorld'
```