# idea configurations:

```
# VM Arguments 是设置的虚拟机的属性
# VM options
VM options 优先级 高于 Environment variable
VM options 需要以 -D 或 -X 或 -XX 开头，每个参数最好使用空格隔开

-server -XX:MetaspaceSize=512M -XX:MaxMetaspaceSize=1024m -Dfile.encoding=UTF-8
 
# Program arguments的值作为args[] 的参数传入或者作为命令行编译时传入的参数

# Environment variable 环境变量
# 这里不需要-D 参数
元素之间用;隔开
 
-D 系统属性
-X* jvm参数
 
# 两个横杠是springboot参数
--server.port=8088
 

```

# Hello World

org.springframework.expression-3.0.5.RELEASE.jar

```
 //1. 构造解析器
        SpelExpressionParser parser = new SpelExpressionParser();
        //2. 解析器解析字符串表达式
//        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
        Expression expression = parser.parseExpression(
                "[('Hello' + ' World').concat(#end)]"
                , new ParserContext() {
                    public boolean isTemplate() {
                        return true;
                    }

                    public String getExpressionPrefix() {
                        return "[";
                    }

                    public String getExpressionSuffix() {
                        return "]";
                    }
                });
        //3. 构造context
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");;
        //4. 根据context得到表达式运算值
        System.out.println(expression.getValue(context));
```



# SpEL支持如下表达式：

**一、基本表达式：** 字面量表达式、关系，逻辑与算数运算表达式、字符串连接及截取表达式、三目运算及Elivis表达式、正则表达式、括号优先级表达式；

```
parser.parseExpression("'Hello World!'").getValue(String.class) // Hello World!
使用“+”进行字符串连接  如“'Hello ' + 'World!'”得到“Hello World!”
"'Hello World'[1]" //e
Elivis表达式 “表达式1?:表达式2”  表达式1非null返回表达式1 否则返回表达式2
string matches regex "'123' matches '\\d{3}'"
```

**二、类相关表达式：** 类类型表达式、类实例化、instanceof表达式、变量定义及引用、赋值表达式、自定义函数、对象属性存取及安全导航表达式、对象方法调用、Bean引用；

使用“T(Type)”来表示java.lang.Class实例，“Type”必须是类全限定名，“java.lang”包除外

```java
parser.parseExpression("T(String)").getValue(Class.class) //class java.lang.String
parser.parseExpression("T(Integer).MAX_VALUE").getValue(int.class); //2147483647

parser.parseExpression("new String('路人甲java')").getValue(String.class); //路人甲java

parser.parseExpression("'路人甲' instanceof T(String)").getValue(Boolean.class) //true
    
EvaluationContext context = new StandardEvaluationContext();
context.setVariable("end", "!");
parser.parseExpression("#end").getValue(context,String.class) //!
context = new StandardEvaluationContext("我是root对象");
String rootObj = parser.parseExpression("#root").getValue(context, String.class);   //我是root对象
parser.parseExpression("#this").getValue(context, String.class) //this指的是当前上下文中的对象，我是root对象

    
registerFunction/setVariable 注册自定义函数 两者等价
Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
context.registerFunction("parseInt", parseInt);
parser.parseExpression("#parseInt('3')").getValue(context, int.class)
    
    
使用Expression#setValue方法可以给表达式赋值 
context=new StandardEvaluationContext(new User());
parser.parseExpression("#root.username").setValue(context,"jinjianou");
System.out.println(parser.parseExpression("#root").getValue(context));

#user.car.name 可能存在空指针安全问题 可以用#user.car.name代替
    
对象方法调用更简单，跟Java语法一样；如“'haha'.substring(2,4)” // ha
    
SpEL支持使用“@”符号来引用Bean,在引用Bean时需要使用BeanResolver接口实现来查找Bean   
DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
User user = new User();
Car car = new Car();
car.setName("保时捷");
user.setCar(car);
factory.registerSingleton("user", user);
StandardEvaluationContext context = new StandardEvaluationContext();
context.setBeanResolver(new BeanFactoryResolver(factory));
ExpressionParser parser = new SpelExpressionParser();
User userBean = parser.parseExpression("@user").getValue(context, User.class);

    
```

**三、集合相关表达式：** 内联List、内联数组、集合，字典访问、列表，字典，数组修改、集合投影、集合选择；不支持多维内联数组初始化；不支持内联字典定义；

```java
parser.parseExpression("{1,2,3}").getValue(List.class) //返回不可修改的list
parser.parseExpression("{{1+2,2+4},{3,4+4}}").getValue(List.class) //列表中只要有一个不是字面量表达式，将只返回原始List
 parser.parseExpression("new int[2]{1,2}").getValue(int[].class) //一维数组 目前不支持多维数组
      
用“集合[索引]”访问集合元素，使用“map[key]”访问字典元素 通过getValue访问 setValue修改

投影： 在SpEL指根据集合中的元素中通过选择来构造另一个集合，该集合和原集合具有相同数量的元素
    （list|map）.![投影表达式]
     list #this代表每个元素
     map #this代表Set<Map.Entry>中每个Map.Entry  使用value获取值 使用key获取键
    
选择元素： (list|map).?[选择表达式] 	
```

**四、其他表达式**：模板表达式。

```
//1. 构造解析器
SpelExpressionParser parser = new SpelExpressionParser();
ParserContext context = new TemplateParserContext("%{", "}");
//2. 解析器解析字符串表达式
Expression expression = parser.parseExpression("你好:%{#name},我们正在学习:%{#lesson}", context);
//3. 构造表达式计算context,得到表达式运算值
StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
evaluationContext.setVariable("name","jinjianou");
evaluationContext.setVariable("lesson","groovy");
System.out.println(expression.getValue(evaluationContext));
//你好:jinjianou,我们正在学习:groovy

```

**注：SpEL表达式中的关键字是不区分大小写的。**



# spring实践

## 配置文件

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">


</beans>
```

```
<bean id="world" class="java.lang.String">
    <constructor-arg value="#{' World!'}"/>
</bean>

以下三种方式等价
<bean id="hello1" class="java.lang.String">
    <constructor-arg value="#{'Hello'}#{world}"/>
</bean>

<bean id="hello2" class="java.lang.String">
    <constructor-arg value="#{'Hello' + world}"/>
</bean>

<bean id="hello3" class="java.lang.String">
    <constructor-arg value="#{'Hello' + @world}"/>
</bean>
```

模板默认以前缀“#{”开头，以后缀“}”结尾，且**不允许嵌套**，如“#{'Hello'#{world}}”错误

## 注解风格

```java
@Value("#{'Hello' + world}") 
```

## 自定义spel

```
@Component
public class SpelBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanExpressionResolver beanExpressionResolver= beanFactory.getBeanExpressionResolver();
        if(beanExpressionResolver instanceof StandardBeanExpressionResolver){
            StandardBeanExpressionResolver resolver= (StandardBeanExpressionResolver)beanExpressionResolver;
            resolver.setExpressionPrefix("%{");
            resolver.setExpressionSuffix("}");
        }

    }
}
```

```
@ComponentScan
@Configuration
public class MainConfig {
    @Bean
    public String name(){
        return "jinjianou";
    }

    @Bean
    public int age(){
        return 18;
    }
}
```

```
@Component
public class SpelBean {

    // # 指代计算上下文 一般适用于非bean类型 @ Bean
    @Value("hello %{@name} : %{@age}")
    private String value;


    public String getValue() {
        return value;
    }
}
```

```
/*        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        SpelBean bean =(SpelBean) applicationContext.getBean("spelBean");
        or*/
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(MainConfig.class);
        applicationContext.refresh();
        SpelBean bean = applicationContext.getBean(SpelBean.class);
        System.out.println(bean.getValue());
```

## 与${}

* \#{…} 用于执行SpEl表达式，并将内容赋值给属性
* ${…} 主要用于加载外部properties文件中的值
* \#{…} 和{…}   可以混合使用，但是必须#{}外面,#{ '${}' } ，注意单引号，注意不能反过来

```
@PropertySource("classpath:test.properties") //spring
@ConfigurationProperties(prefix)   //springboot
```

- *${ property **:** default_value }*
- *#{ obj.property**? :** default_value }*



