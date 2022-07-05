## 概念

[Activiti7基础，最新工作流引擎Activiti7简介与环境搭建 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/337018100)

* wokflow: 

  **the sequence** of industrial, administrative, or other processes through which **a piece of work passes from initiation to completion**.

* workflow engine:

  A workflow engine is the **application** that **runs digital workflow software**. It enables businesses to c**reate and automate workflows** — **in many cases, with low-code visual builders**.

* Activiti可以实现业务流程更改而代码不需更改

* 流程：

  1.  编写业务流程图，本质上就是个XML文件

     规范：

     * BPM business process management
     * BPMN business process model and notation 业务流程模型和符号 一套标准的业务流程建模符号

  2. 读取业务流程图，相当于解析该XML文件并存入mysql数据库

  3. 业务流程的推进，转化为读取mysql表中的数据并处理



## 总体架构

![001](C:\Users\Administrator\Desktop\复习\素材\pic\activiti\001.jpg)

activiti.cfg.xml activiti 的引擎配置文件，包括：ProcessEngineConfiguration 的定义、数据源定义、事务管理器等。
ProcessEngineConfiguration 流程引擎的配置类，通过 ProcessEngineConfiguration 可以创建工作流引擎 ProceccEngine。
ProcessEngine 创建各个 service ，比如RuntimeService,RepositoryService，TaskService，HistoryService等.无需关注FormService和IdentityService，因为在activiti7中已经被删除了。

* RepositoryService
  是 activiti 的资源管理类，提供了管理和控制流程发布包和流程定义的操作。使用工作流建模工具设计的业务流程图需要使用此 service 将流程定义文件的内容部署到计算机。除了部署流程定义以外还可以：查询引擎中的发布包和流程定义。暂停或激活发布包，对应全部和特定流程定义。
* RuntimeService
  它是 activiti 的流程运行管理类。可以从这个服务类中获取很多关于流程执行相关的信息
* TaskService
  是 activiti 的任务管理类。可以从这个类中获取任务的信息。
  HistoryService
* 是 activiti 的历史管理类，可以查询历史信息，执行流程时，引擎会保存很多数据（根据配置），比如流程实例启动时间，任务的参与者， 完成任务的时间，每个流程实例的执行路径，等等。 这个服务主要通过查询功能来获得这些数据
* ManagementService
  是 activiti 的引擎管理类，提供了对 Activiti 流程引擎的管理和维护功能，这些功能不在工作流驱动的应用程序中使用，主要用于 Activiti 系统的日常维护。(主要是了解下，一般用不到)

## 应用

* 开发流程

  1. 整合activiti

     * 导入依赖

       ```xml
        <dependency>
                   <groupId>org.activiti</groupId>
                   <artifactId>activiti-dependencies</artifactId>
                   <version>7.0.0.Beta1</version>
                   <scope>import</scope>
                   <type>pom</type>
               </dependency>
       ```

     * 新增插件 activiti BPM visualizer 或 actiBPM
       * .bpmn20.xml
       * designer

     * 推荐使用 camunda-modeler 外部工具
       * 下载  https://bpmn.io/modeler/ 或 网盘
       * 集成到idea 或者 直接双击使用
         * settings->external tools 
         * 重启
         * 项目右键  external tools 

     * 支持的数据库

       ![002](C:\Users\Administrator\Desktop\复习\素材\pic\activiti\002.jpg)

     * 导入依赖

       ```
           <dependencies>
               <dependency>
                   <groupId>org.activiti</groupId>
                   <artifactId>activiti-dependencies</artifactId>
                   <version>7.0.0.Beta1</version>
                   <scope>import</scope>
                   <type>pom</type>
               </dependency>
               <dependency>
                   <groupId>org.activiti</groupId>
                   <artifactId>activiti-engine</artifactId>
                   <version>${activiti.version}</version>
               </dependency>
               <dependency>
                   <groupId>org.activiti</groupId>
                   <artifactId>activiti-spring</artifactId>
                   <version>${activiti.version}</version>
               </dependency>
               <!-- bpmn 模型处理 -->
               <dependency>
                   <groupId>org.activiti</groupId>
                   <artifactId>activiti-bpmn-model</artifactId>
                   <version>${activiti.version}</version>
               </dependency>
               <!-- mysql驱动 -->
               <dependency>
                   <groupId>mysql</groupId>
                   <artifactId>mysql-connector-java</artifactId>
                   <version>8.0.21</version>
               </dependency>
               <dependency>
                   <groupId>org.apache.logging.log4j</groupId>
                   <artifactId>log4j-slf4j-impl</artifactId>
                   <version>2.13.3</version>
               </dependency>
               <dependency>
                   <groupId>org.slf4j</groupId>
                   <artifactId>slf4j-api</artifactId>
                   <version>1.7.34</version>
               </dependency>
               <dependency>
                   <groupId>hikari-cp</groupId>
                   <artifactId>hikari-cp</artifactId>
                   <version>2.4.0</version>
               </dependency>
               <dependency>
           <groupId>org.slf4j</groupId>
           <artifactId>slf4j-nop</artifactId>
           <version>1.7.35</version>
           <scope>test</scope>
       </dependency>
           </dependencies>
       ```

     * 添加 logj2.properties

     * 添加resources->activiti.cfg.xml(new=>xml configuration file=>spring config)

       ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                           http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/contex
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd">
       </beans>
       ```

       配置processEngineConfiguration

       * 第一种 单独配置 jdbcDriver....

       * 第二种 先配置数据源

         ```
         <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
                 <property name="url" value="jdbc:mysql://localhost:3306/activiti?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true&amp;nullCatalogMeansCurrent=true"/>
                 <property name="username" value="root"/>
                 <property name="password" value="123456"/>
                 
                 
         <!--    processEngineConfiguration名字不可更改-->
             <bean id="processEngineConfiguration"
                   class="org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration">
                 <!-- 引用数据源 上面已经设置好了-->
                 <property name="dataSource" ref="dataSource" />
                 <!-- activiti数据库表处理策略 存在使用，不存在创建(所有表)-->
                 <property name="databaseSchemaUpdate" value="true"/>
             </bean>
         ```

     * ```
       ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
       完成后在系统新建25张表 都是以act为前缀
       第二部分是表示表的用途的两个字母标识。 用途也和服务的 API 对应。 
       ACT_RE ：'RE'表示 repository。 这个前缀的表包含了流程定义和流程静态资源 （图片，规则，等等）。 
       ACT_RU：'RU'表示 runtime。 这些运行时的表，包含流程实例，任务，变量，异步任务，等运行中的数据。 Activiti 只在流程实例执行过程中保存这些数据， 在流程结束时就会删除这些记录。 这样运行时表可以一直很小速度很快。 
       ACT_HI：'HI'表示 history。 这些表包含历史数据，比如历史流程实例， 变量，任务等等。 
       ACT_GE ： GE 表示 general。 通用数据， 用于不同场景下第二部分是表示表的用途的两个字母标识。 用途也和服务的 API 对应。 
       ```

       ![003](C:\Users\Administrator\Desktop\复习\素材\pic\activiti\003.jpg)

     * ProcessEngineConfiguration

       **SpringProcessEngineConfiguration**

       * ```xml
         org.activiti.spring.SpringProcessEngineConfiguration
         
         
         //先构建ProcessEngineConfiguration
         ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
         
         
         //通过ProcessEngineConfiguration创建ProcessEngine，此时会创建数据库
         ProcessEngine processEngine = configuration.buildProcessEngine();
         
         或
         ProcessEngines.getDefaultProcessEngine() //会使用classpath下的activiti.cfg.xml生成库表
         
         //通过ProcessEngine创建Service
         RuntimeService runtimeService = processEngine.getRuntimeService();
         RepositoryService repositoryService = processEngine.getRepositoryService();
         TaskService taskService = processEngine.getTaskService();
          HistoryService historyService = processEngine.getHistoryService();
                 ManagementService managementService = processEngine.getManagementService();
         
         
         ```

         | RepositoryService | 资源管理类     |
         | ----------------- | -------------- |
         | RuntimeService    | 流程运行管理类 |
         | TaskService       | 任务管理类     |
         | HistoryService    | 历史管理类     |
         | ManagerService    | 引擎管理类     |

         

  2. 实现业务流程建模

     * 绘制工具

       1. 插件 --idea 2020之后 去https://plugins.jetbrains.com/idea 搜actiBPM   不兼容idea2021

       2. 借助eclipse

       3. camunda-modeler

          https://camunda.com/download/modeler

          集成到idea: file->setting->tools->external tools添加

          arguments 	\$FilePath$

          项目右键 -> external tools -> camunda-modeler

          ![005](C:\Users\Administrator\Desktop\复习\素材\pic\activiti\5.jpg)

     * BPMN2.0

       **1、流对象（Flow Objects）**：是定义业务流程的主要图形元素，包括三种：事件、活动、网关

       **事件（Events）**：指的是在业务流程的运行过程中发生的事情，分为：

       - 开始：表示一个流程的开始
       - 中间：发生的开始和结束事件之间，影响处理的流程
       - 结束：表示该过程结束

       ![img](https://pic4.zhimg.com/80/v2-36313c37bab54e452e04cf942787110b_1440w.jpg)

       **活动（Activities）**：包括任务和子流程两类。子流程在图形的下方中间外加一个小加号（+）来区分。

       ![img](https://pic3.zhimg.com/80/v2-fe4af1b4136cc94af7b5b7e74043e35a_1440w.jpg)

       **网关（Gateways）**：用于表示流程的分支与合并。

       - 排他网关：只有一条路径会被选择
       - 并行网关：所有路径会被同时选择
       - 包容网关：可以同时执行多条线路，也可以在网关上设置条件
       - 事件网关：专门为中间捕获事件设置的，允许设置多个输出流指向多个不同的中间捕获事件。当流程执行到事件网关后，流程处于等待状态，需要等待抛出事件才能将等待状态转换为活动状态。

       ![img](https://pic1.zhimg.com/80/v2-94eafef2e7a66e2ae8461217a884f360_1440w.jpg)

       **2、数据（Data）**：数据主要通过四种元素表示

       - 数据对象（Data Objects）
       - 数据输入（Data Inputs）
       - 数据输出（Data Outputs）
       - 数据存储（Data Stores）

       **3、连接对象（Connecting Objects）**：流对象彼此互相连接或者连接到其他信息的方法主要有三种

       顺序流：用一个带实心箭头的实心线表示，用于指定活动执行的顺序

       信息流：用一条带箭头的虚线表示，用于描述两个独立的业务参与者（业务实体/业务角色）之间发送和接受的消息流动

       关联：用一根带有线箭头的点线表示，用于将相关的数据、文本和其他人工信息与流对象联系起来。用于展示活动的输入和输出

       **4、泳道（Swimlanes）**：通过泳道对主要的建模元素进行分组，将活动划分到不同的可视化类别中来描述由不同的参与者的责任与职责。

       ![006](C:\Users\Administrator\Desktop\复习\素材\pic\activiti\006.jpg)

       ![007](C:\Users\Administrator\Desktop\复习\素材\pic\activiti\007.jpg)

       大家看完下面两个实例，对泳道的认识就更清晰了。

       BPMN实例

       **实例1**：拍卖服务BPMN模板

       ![img](https://pic3.zhimg.com/80/v2-16176e4ff653436daf61415cf9763276_1440w.jpg)

       **实例2**：书籍销售流程 BPMN

       ![img](https://pic2.zhimg.com/80/v2-d1d2769a122c2e9e88fdea04e905d0b5_1440w.jpg)

       

  3. 部署业务流程到activiti

     * 单个文件部署

       ```java
       //        1、创建ProcessEngine
               ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
       //        2、得到RepositoryService实例
               RepositoryService repositoryService = processEngine.getRepositoryService();
       //        3、使用RepositoryService进行部署
               Deployment deployment = repositoryService.createDeployment()
                       .addClasspathResource("bpmn/evection.bpmn") // 添加bpmn资源
                       .addClasspathResource("bpmn/evection.png")  // 添加png资源
                       .name("出差申请流程")
                       .deploy();
       //        4、输出部署信息
               System.out.println("流程部署id：" + deployment.getId());
               System.out.println("流程部署名称：" + deployment.getName());
       ```

     * 压缩文件部署

       ```java
       // 定义zip输入流
               InputStream inputStream = this
                       .getClass()
                       .getClassLoader()
                       .getResourceAsStream(
                               "bpmn/evection.zip");
               ZipInputStream zipInputStream = new ZipInputStream(inputStream);
               // 获取repositoryService
               RepositoryService repositoryService = processEngine
                       .getRepositoryService();
               // 流程部署
               Deployment deployment = repositoryService.createDeployment()
                       .addZipInputStream(zipInputStream)
                       .deploy();
               System.out.println("流程部署id：" + deployment.getId());
               System.out.println("流程部署名称：" + deployment.getName());
       ```

     流程定义部署后操作activiti的3张表如下：

     act_re_deployment 流程定义部署表，每部署一次增加一条记录

     act_re_procdef 流程定义表，部署每个新的流程定义都会在这张表中增加一条记录 key用来唯一标识一个流程

     act_ge_bytearray 流程资源表

     建议：一次部署一个流程（一次可部署多个流程）

     异常一： Error updating database. Cause: java.sql.SQLSyntaxErrorException: Unknown column ‘VERSION_’ in ‘field list’

     解决：更新activiti新版本

     异常二： Exception in thread "main" java.lang.NoSuchMethodError: org.junit.platform.commons.util.ReflectionUtils.getDefaultClassLoader()Ljava/lang/ClassLoader;

     解决：更新junit新版本

     异常三： org.activiti.engine.ActivitiException: Could not update Activiti database schema: unknown version from database: '7.1.0.0'

     解决: **activiti 相关的jar版本和表 act_ge_property 中 schema.version 所存储的版本不一致导致报错的。**  

  4. 启动流程实例

     ```
     // 1. get processEngine
           ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
           // 2. get runtimeService
           RuntimeService service = engine.getRuntimeService();
           // 3. get processInstance by process key
           ProcessInstance processInstance = service.startProcessInstanceByKey("evection");
           System.out.println("流程定义的id："+processInstance.getProcessDefinitionId());
           System.out.println("流程实例的id: "+processInstance.getId());
           System.out.println("当前活动的id: "+processInstance.getActivityId());
     ```
     act_his_actinstant 流程实例的执行历史

     act_his_identitylink 流程参与用户的历史信息

     act_his_procinst 流程实例历史信息

     act_his_taskinst 流程任务历史信息

     act_ru_execution 流程执行信息

     act_ru_identitylink 流程参与用户的信息

     act_ru_task 流程任务信息

     问题： camunda与activiti的兼容性问题，如对于camunda:assignee 不生效，将生成的bpmn中camunda->activiti (camunda是从activiti发展而来，activiti不兼容camunda)

     解决（对bpmn文件）：

     1. 替换命名空间

        <bpmn2:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                           xmlns:bpmn2="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI"
                           xmlns:dc="http://www.omg.org/spec/DD/20100524/DC"
                           xmlns:di="http://www.omg.org/spec/DD/20100524/DI"
                           xmlns:activiti="http://activiti.org/bpmn"
                           id="sample-diagram"
                           targetNamespace="http://bpmn.io/schema/bpmn"
                           xsi:schemaLocation="http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd">

     2. bpmn: -> bpmn2:

     3. camunda -> activiti

  5. 查询待办任务

     ```
     // 1. get processEngine
     ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
     // 2. get taskService
     TaskService taskService = processEngine.getTaskService();
     // 3. query list of Task by process key and assignee
     List<Task> taskList = taskService.createTaskQuery()
             .processDefinitionKey("evection")
             .taskAssignee("zhangsan")
             .list();
     for (Task task : taskList) {
        System.out.println("流程实例id: "+task.getProcessInstanceId());
        System.out.println("任务id: "+task.getId());
        System.out.println("任务负责人："+task.getAssignee());
        System.out.println("任务名称："+task.getName());
     }
     ```

  6. 处理待办任务

     ```
     // 1. get processEngine
     ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
     // 2. get taskService
     TaskService taskService = processEngine.getTaskService();
     // 3. query singleResult of Task by process key and assignee
     Task task = taskService.createTaskQuery()
             .processDefinitionKey("evection")
             .taskAssignee("zhangsan")
             .singleResult();
     // 4. 完成任务，流程流转到下一个节点
     taskService.complete(task.getId()); 	
     ```

  7. 结束流程

     ```
     ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
     RepositoryService repositoryService = engine.getRepositoryService();
     //resources = classLoader.getResources("activiti.cfg.xml");  get url info of file named activiti.cfg.xml
     //Deletes the given deployment and cascade deletion to process instances, history process instances and jobs
     //success if there's no  exist uncompleted process instance where cascade is true,otherwise failed
     repositoryService.deleteDeployment("1",true);
     ```

  8. 流程的查询

     ```
     // 1. get processEngine
     ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
     // 2. get RepositoryService
     RepositoryService repositoryService = processEngine.getRepositoryService();
     // 3. query list of Task by process key
     List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
             .processDefinitionKey("evection")
             .orderByProcessDefinitionVersion()
             .desc()
             .list();
     ```

  9. 获取流程资源

  ```
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // -> processDefine -> deploymentId -> inputStream
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("evection")
                .singleResult();
        String deploymentId = processDefinition.getDeploymentId();
        // pic -> DiagramResource    bpmn -> Resource
        // resourceName must be not null
  //      InputStream is1 = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
        InputStream is2 = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());
  ```

  10. 查询历史信息

      ```
      // 1. get ProcessEngine
      ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
      // 2. get hisService
      HistoryService historyService = processEngine.getHistoryService();
      // 3. get list of his activity
      List<HistoricActivityInstance> hisInstaceList = historyService.createHistoricActivityInstanceQuery()
              .processDefinitionId("evection:2:17503")
              .orderByHistoricActivityInstanceStartTime()
              .desc()
              .list();
      for (HistoricActivityInstance activityInstance : hisInstaceList) {
         System.out.println(activityInstance.getId()); //act_hi_actinst id
         System.out.println(activityInstance.getActivityId());//evection_task_0x
         System.out.println(activityInstance.getAssignee());
         System.out.println(activityInstance.getActivityType()); //event userTask...
         System.out.println(activityInstance.getEndTime());
         System.out.println("================================");
         System.out.println();
      }
      ```

## Activiti 进阶

#### UEL

​	unified expression language, jee6的规范，分为uel-value和uel-method  

​	如uel-value  ${xxxx}
	    uel-method   ${x.method(y)}  x是spring容器中的一个bean，y作为uel-value传递过去

​            其他   支持解析基础类型、bean、list、array和map，也可作为条件判断

​	

### businessKey

act_ru_exection 用于关联业务表	

### 流程挂起

暂停流程

#### 流程定义挂起

1. 不允许创建新的流程实例

2.  已经创建的流程实例暂停不能继续往下执行/可以继续执行 

   ```
   void suspendProcessDefinitionById(String processDefinitionId, boolean suspendProcessInstances, Date suspensionDate);
   ```

3. 激活

   ```
   void activateProcessDefinitionById(String processDefinitionId, boolean activateProcessInstances, Date activationDate);
   ```

字段 suspension_state 1代表激活状态 2代表挂起状态

```
// 1. get ProcessEngine
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
// 2. get repositoryService
RepositoryService repositoryService = processEngine.getRepositoryService();
// 3. get processDefine
ProcessDefinition process = repositoryService.createProcessDefinitionQuery()
        .processDefinitionKey("evection")
        .orderByDeploymentId()
        .desc()
        .list().get(0);
// 4. get status of processDefine
boolean suspended = process.isSuspended();
// 5. hang up/suspend if not,otherwise activate
if(!suspended){
   repositoryService.suspendProcessDefinitionById(process.getId());
   System.out.println("suspend");
}else{
   // date is null rather than new Date()
   repositoryService.activateProcessDefinitionById(process.getId(),true,null);
   System.out.println("activating");
}
```

#### 流程实例挂起

```
// 2. get runtimeService
RuntimeService runtimeService = processEngine.getRuntimeService();
// 3. get process instance
ProcessInstance instance = runtimeService.createProcessInstanceQuery()
        .processDefinitionKey("evection")
        .processInstanceId("40001")
        .singleResult();
```

### 分配assignee

#### 1. 固定分配

#### 2.uel分配

``` java
Map<String,Object> map=new HashMap<>();
        map.put("assignee0",new String[]{"张三","周六"});
        map.put("assignee1","李四");
        map.put("assignee2","王五");

        // 3. get processInstance by process key
        ProcessInstance processInstance = service.startProcessInstanceByKey("evection-uel",map);
```

#### 3.listener分配

listener可以处理很多流程业务

listener事件类型：

1. create 创建后触发
2. assignee 分配后触发
3. complete 完成后触发
4. all 所有事件触发

```
ExecutionListener#start
TaskListener#create
TaskListener#{assignment}*
TaskListener#{complete, delete}
ExecutionListener#end
```

##### 特点

1. 不需要指定assignee
2. create listener implements TaskListener

```java
public class MyActivitiListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        //id 系统随机分配的数字串
        //name 创建请假单
        if("evection_task_01".equals(delegateTask.getTaskDefinitionKey())
            && "create".equals(delegateTask.getEventName())){
            delegateTask.setAssignee("zhangsan-listener");
        }
    }
}
```



### 查询任务

#### 查询任务负责人的待办任务

```
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
TaskService taskService = processEngine.getTaskService();

Task task = taskService.createTaskQuery()
        .processDefinitionKey("evection")
        .taskDefinitionKey("evection_task_01")
        .taskAssignee("lisi")
        //是否有variables(如分配assignee)
        .includeProcessVariables()
        .singleResult();
System.out.println(task.getName());
```

#### 查询businessKey

```
ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
TaskService taskService = processEngine.getTaskService();
Task task = taskService.createTaskQuery()
        .processDefinitionKey("evection-listener")
        .taskAssignee("zhangsan-listener")
        .singleResult();
// get processInstance
ProcessInstance instance = processEngine.getRuntimeService().createProcessInstanceQuery()
        .processInstanceId(task.getProcessInstanceId())
        .singleResult();
System.out.println("businessKey: "+instance.getBusinessKey());
```

### 办理任务

查询待办任务根据当前用户assignee和需要办理的任务taskId，如果查询到的task != null，则可以办结，否则不允许半截。



### 流程变量

#### 定义

 	决定或影响流程流转

#### 类型

- string
- integer
- short
- long
- double
- boolean
- date
- binary
- serializable

#### 作用域

  可以是流程实例(processinstance)或任务(task)或执行（execution）

1. global

   默认是流程实例，即global

2. local

   任务(task)或执行（execution）

#### 定义

在bpmn连线上设置uel condition  如${entity.props>3}

注意1. entity一定要implements Serializable 2. 数据库字符串字段默认自然排序，也就是100<99 所以要根据创建时间排序

#### 设置流程变量的时间

1. 实例初始化时定义 global

   ```
   ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);
   ```

2. 办理任务的时候设置 global

   ```
   void complete(String taskId, Map<String, Object> variables);
   ```

   该流程变量只有在该节点完成之后其他结点才能使用，如果设置的流程变量的key在流程实例中已经存在则后设置的会覆盖前设置的

3. 当前流程实例设置 global

   通过当前executorId设置，当前流程实例必须未执行完毕且流程变量需要在当前executorId之后生效

   ```
   RuntimeService runtimeService = processEngine.getRuntimeService();
   HashMap<String, Object> map = new HashMap<>();
   map.put("evection",new EvectionEntity(4));
   runtimeService.setVariables("115006",map);
   ```

4. 当前任务设置 global

   taskId必须是当前待办任务id,act_ru_task中存在

   ```
   void setVariables(String taskId, Map<String, ? extends Object> variables);
   ```

5. 当前任务设置 local

   只能在当前任务生效，每个任务可以取相同的名字

   **compete Called when the task is successfully executed,也就是调用complete方法的时候，context已经不存在了之前set的local variable，会导致变量解析失败，不使用**

   ```
   void setVariablesLocal(String taskId, Map<String, ? extends Object> variables);
   ```

#### 获取流程变量的方式

```
1.TaskService
TaskService taskService = processEngine.getTaskService();
Map<String, Object> variables = taskService.getVariables("125005");//taskId
for (Map.Entry<String, Object> map : variables.entrySet()) {
    System.out.println(map.getKey()+": "+map.getValue());
}

2.ruintimeService
RuntimeService runtimeService = processEngine.getRuntimeService();
Map<String, Object> variables = runtimeService.getVariables("122506");//executorId
for (Map.Entry<String, Object> map : variables.entrySet()) {
System.out.println(map.getKey()+": "+map.getValue());
}

```

## 与spring整合

1. 添加依赖

   除了单独使用的依赖，还得添加activiti-spring+spring相关依赖

   <dependency>
       <groupId>org.activiti</groupId>
       <artifactId>activiti-spring</artifactId>
       <version>7.1.0.M2</version>
   </dependency>

2. 添加配置文件

   ```
   <?xml version="1.0" encoding="UTF-8"?>
   
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
              http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
              http://www.springframework.org/schema/context
              http://www.springframework.org/schema/context/spring-context-2.5.xsd
              http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-2.5.xsd
              http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-2.5.xsd">
   
   <!--    1.datasource-->
       <bean id="dataSource" class="com.zaxxer.hikari.HikariDataSource">
           <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
           <property name="jdbcUrl" value="jdbc:mysql://localhost:3306/activiti?characterEncoding=utf8&amp;useSSL=false&amp;serverTimezone=UTC&amp;rewriteBatchedStatements=true&amp;nullCatalogMeansCurrent=true"/>
           <property name="username" value="root"/>
           <property name="password" value="root"/>
       </bean>
   
   
   <!--    2. 获取processEngine 通过SpringProcessEngineConfiguration而不是StandaloneProcessEngineConfiguration-->
       <bean id="processEngineConfiguration"
             class="org.activiti.spring.SpringProcessEngineConfiguration">
           <!-- 引用数据源 上面已经设置好了-->
           <property name="dataSource" ref="dataSource" />
           <property name="transactionManager" ref="transactionManager"/>
   <!--        false 默认值 启动activiti时，会比对数据库中保存的版本，如果没有表或者版本不匹配，则抛出异常（生产常用）
           true activit会对所有表进行更新，如果表不存在，则自动创建（开发常用）
           create-drop  启动activiti时创建表，表存在则抛出异常，在关闭时删除表（需要手动关闭引擎processEngine.close()才能删除表，单元测试常用）
           drop-create 启动activiti时删除原来的旧表，然后创建新表（不需要手动关闭引擎）-->
           <property name="databaseSchemaUpdate" value="create-drop"/>
       </bean>
   
       <bean id="processEngine"  factory-bean="processEngineConfiguration"
             factory-method="buildProcessEngine"/>
   <!--
       或者
       <bean id="processEngine"  class="org.activiti.spring.ProcessEngineFactoryBean">
           <property name="processEngineConfiguration" ref="processEngineConfiguration"/>
       </bean>
   -->
   <!--    3. 获取services-->
           <bean id="repositoryService" factory-bean="processEngine"
                 factory-method="getRepositoryService"/>
   <!--    4.获取transactionManager 这里采用注解方式-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"/>
       </bean>
       <tx:annotation-driven transaction-manager="transactionManager"/>
   <!--    5. 获取切面 通知+切点 这里采用注解方式-->
   
       <context:component-scan base-package="org.example.activiti"/>
       <bean class="org.springframework.jdbc.core.JdbcTemplate" id="jdbcTemplate">
           <property name="dataSource" ref="dataSource"/>
       </bean>
   </beans>
   ```

```
枚举类Propagation是为了结合@Transactional注解使用而设计的，
这个枚举里面定义的事务传播行为类型与TransactionDefinition中定义的事务传播行为类型是对应的
REQUIRED、SUPPORTS、MANDATORY、REQUIRES_NEW、NOT_SUPPORTED、NEVER、NESTED。

Spring中事务的默认实现使用的是AOP，也就是代理的方式，如果大家在使用代码测试时，同一个Service类中的方法相互调用需要使用注入的对象来调用，不要直接使用this.方法名来调用，this.方法名调用是对象内部方法调用，不会通过Spring代理，也就是事务不会起作用
1. @Transactional 方法修饰符为非public时不生效
2. 外部非@Transactional方法调用内部@Transactional方法导致内部事务失效
3.采用注解时没声明注解驱动     <tx:annotation-driven transaction-manager="transactionManager"/>


考虑如下场景
public void testMain(){
    A(a1);  //调用A入参a1
    testB();    //调用testB
}

public void testB(){
    B(b1);  //调用B入参b1
    throw Exception;     //发生异常抛出
    B(b2);  //调用B入参b2
}
没有事务则 a1 b1插入成功 b2失败
REQUIRED 默认 如果当前没有事务，则自己新建一个事务，如果当前存在事务，则加入这个事务
1. 方式1
public void testMain(){
    A(a1);  //调用A入参a1
    testB();    //调用testB
}
@Transactional(propagation = Propagation.REQUIRED)
public void testB(){
    B(b1);  //调用B入参b1
    throw Exception;     //发生异常抛出
    B(b2);  //调用B入参b2
}
外部非@Transactional方法调用内部@Transactional方法导致内部事务失效 a1,b1 插入成功 b2失败
2. 方式二
@Transactional(propagation = Propagation.REQUIRED)
public void testMain(){
    A(a1);  //调用A入参a1
    testB();    //调用testB
}
public void testB(){
    B(b1);  //调用B入参b1
    throw Exception;     //发生异常抛出
    B(b2);  //调用B入参b2
}
从A开始到方法结束都在事务的作用域 所以 a1,b1,b2都失败
```

3. 测试

   ```
   @RunWith(SpringRunner.class)
   @ContextConfiguration("classpath:activiti-cfg.xml")
   public class IntegrationTest {
       @Autowired
       private ProcessEngine processEngine;
       @Autowired
       private RepositoryService repositoryService;
       @Test
       public void test1(){
           System.out.println(repositoryService);
           processEngine.close();
       }
   }
   ```

## 与springboot整合

1. 创建springboot项目

2. 导入依赖

   

3. 配置参数

4. 启动测试