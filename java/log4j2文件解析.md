<Appenders> 输出目的地  输出到console file等

<Layouts> 

​	<PatternLayout>

| **Parameter Name**                                           |
| ------------------------------------------------------------ |
| pattern                                                      |
| replace                                                      |
| charset                                                      |
| alwaysWriteExceptions 默认true                               |
| header                                                       |
| footer                                                       |
| noConsoleNoAnsi  默认false  如果为true，且System.console()是null，则不会输出ANSI转义码 |



<Loggers>

​	<Root>

​	<Logger>









# patternLayout

| **参数名**                            | 参数意义                                                     |      |
| ------------------------------------- | ------------------------------------------------------------ | ---- |
| %c{参数}或%logger{参数}               | 输出logger的名称，即语句private static final Logger logger = LogManager.getLogger(App.class.getName())中App.class.getName()的值。也可以使用其他字符串。 |      |
| %C{参数}或%class{参数}                | 输出类名                                                     |      |
| %d{参数}{时区te{参数}{时区            | 输出时间。                                                   |      |
| %F\|%file                             | 输出文件名,仅仅是文件名称，如Test.java，不包含路径名称       |      |
| highlight{pattern}{style}             | 高亮显示结果  windows需要在patternLayout里添加 disableAnsi="false" |      |
| %l                                    | 输出完整的错误位置(包含正常的方法入口位置)，如com.future.ourfuture.test.test.App.tt(App.java:13) 会影响性能 |      |
| %m或%msg或%message                    | 输出信息                                                     |      |
| %M或%method                           | 输出方法名，如“main”，“getMsg”等字符串                       |      |
| %n                                    | 换行符 Windows是”\r\n”，Linux是”\n”                          |      |
| %level{参数1}{参数2}{参数3}           | 参数1用来替换日志信息的级别 格式为：{level=label, level=label, …}，即使用label代替的字符串替换level<br />参数2表示只保留前n个字符<br />参数3表示结果是大写还是小写<br />参数1中指定了label的字符串不受参数2,3的影响 |      |
| %r或%relative                         | 输出自JVM启动以来到log事件开始时的毫秒数                     |      |
| replace{pattern}{regex}{substitution} | 将pattern的输出结果，按照正则表达式regex匹配后，使用substitution字符串替换<br />比如  %replace{%logger }{\.}{/}com.future.ourfuture.test.test.App则输出为com/future/ourfuture/test/test/App |      |
| %sn或%sequenceNumber                  | 自增序号，每执行一次log事件，序号+1                          |      |
| %t或%thread或%tid                     | 创建logging事件的线程名                                      |      |
| %u{RANDOM}                            | 依照一个随机数或当前机器的[MAC](https://www.baidu.com/s?wd=MAC&tn=24004469_oem_dg&rsv_dl=gh_pl_sl_csd)和时间戳来随机生成一个UUID |      |
| %-5level\|%-5p                        | 日志级别                                                     |      |
|                                       |                                                              |      |

1. %c{1}  org.apache.com.te.Foo  ->   Foo

   %c{2}  org.apache.com.te.Foo  ->   te.Foo

   %c{1.} org.apache.com.te.Foo  ->   o.a.c.t.Foo

   %c{1.1!} org.apache.com.te.Foo  ->   o.a.!.!.Foo

   %c{.} org.apache.com.te.Foo  ->   ….Foo

2. 规则与%c完全一致

3. %d{DEFAULT}   2012-11-02 14:34:02,781

   %d{ISO8601} 2012-11-02T14:34:02,781

   %d{dd MMM yyyy HH:mm:ss,SSS}  02 Nov 2012

   14:34:02,781

   %d{HH:mm:ss}{GMT+0}  18:34:02

   %d{UNIX}  1351866842

4. 对齐

   %format pattern

   整数表示右对齐，负数表示左对齐；整数表示右对齐，负数表示左对齐； 如%20.30m 对输出信息(最少20个字符,最多30个,少前面补空格,多右截断)右对齐