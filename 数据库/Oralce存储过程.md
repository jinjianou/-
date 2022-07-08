# What is

> 一组为了完成特定功能的SQL语句集
>
> 经过一次编译后调用不需要再次编译

## 优点

1. 允许标准组件编程

   不需要堆源代码修改,提高程序的可移植性

2. 实现较快的执行速度

   存储过程是预编译的,首次运行时,查询优化器对其进行分析,优化并给出最终被存在系统表中的存储计划

3. 减轻网络流量

4. 一种安全机制

# PL/SQL编程

> 过程化SQL语言(procedural language),是Oracle数据库对SQL语句的扩展.在普通sql语句的使用上增加了编程语言的特点.

## Hello World

PL/SQL程序由三部分组成 声明+执行+异常处理

```pl
DECLARE
    --声明部分:在此声明用到的变量,类型及游标或局部的存储过程和函数 可选
BEGIN
    --执行部分 必须
EXCEPTION
    --异常执行部分 处理错误 可选
END;   
```

## 变量

### 命名规则

4.png

### 变量类型

1. 标量类型(char,varchar2,date,number,boolean....)

   ```
   v_a varchar2(128):='小黑';
   ```

2. %type类型

   与已经定义的某个数据变量类型相同,或者与数据库的某列的数据类型相同,如

   ```
   v_b v_a%type
   
   v_c emp.a%type
   select a into v_c from emp where .... ;
   ```

3. 记录类型

   把逻辑相关数据作为一个单元存储起来,可以简单理解为结构体

   ```
   DECLARE
       type my_struct is record(
       V_a emp.a%type,
       V_b emp.b%type);
       
       v_c my_struct;
       select a,b into v_c from emp where .... ;
   ```

4. %rowtype类型

   其数据结构和数据库表的数据结构相一致

   ```
   DECLARE
       v_user user%rowtype;
   select * into v_user from user where .... ;    
   ```

5. Object类型

   ```
   create or replace type xxx as(
   	field a,
   	filed b,
   	methods
   )
   ```

   注意: 不允许直接插入(ORA-00947) 如select * into ty_xxx  
   
   得按照这种格式插入 select ty_xxx(f1=>t.f1[,f2=>t.f2....]) into ty_xxx
   
   **形参=>实参(字面量/Function)**
   
   **ty_xxx所有的字段都要列出**
   
   或者 select ty_xxx(f1=>t.f1[,f2=>t.f2....])  bulk collect into tyt_xxx

6. SUBTYPE subtype_name IS base_type[(constraint)] [NOT NULL ]; 

7. Function func_name return type [deterministic] [result_cache];

   deterministic它表示一个函数在输入不变的情况下输出是否确定,**但同样的输入不一定会导致同样的输出** 如SYS.dbms_random.value(1,10);在同义词(数据库对象的一个别名)查询使用该函数时性能会有很大的提升

   result_cache 同样的输入一定会导致同样的输出

## 循环控制

### if

    if <布尔表达式1> then
        plsql和sql语句
    elsif <布尔表达式2> then
        其他
    else
        其他
    end if;
### while

    while <布尔表达式1> loop
        plsql和sql语句
    end loop;
### for

    for 循环计数器 in [reverse] 下限 .. 上限 loop
        plsql和sql语句
    end loop;
### loop(until)

```
loop
plsql和sql语句
exit when <条件语句>
end loop;
```

## 游标

> 游标是SQL的一个内存工作区,由系统或用户以变量的形式定义.
>
> 作用就是用于临时存储从数据库中提取的数据块,类似ResultSet

### 分类

1. 隐式游标: 在plsql中进行非查询(或返回单条记录的查询),如delete/insert/update,oracle系统会自动地为这些操作设置游标并创建其工作区,且隐式游标名字为SQL.**PL/SQL管理隐式游标,当查询开始时隐式游标打开,查询结束时隐式游标自动关闭.**

2. 显式游标: 返回多条记录的查询,需要一个显式游标,此时不能使用select into.

   **需要在pl/sql声明部分声明,一般我们所说的游标通常都是显式游标(显式游标需要声明)**

|           | 隐式                                         | 显式                                |
| --------- | -------------------------------------------- | ----------------------------------- |
| %isopen   | SQL%isopen:DML执行过程中为true,结束后为false | true代表游标打开                    |
| %found    | true 代表操作成功                            | 最近的fetch语句返回一行数据则是true |
| %notfound | true 代表操作失败                            | 与%notfound相反                     |
| %rowcount | 代表DML语句成功执行的数据行数                | 获得fetch语句返回的数据行数         |

```
BEGIN
    insert into tab values('a','b');
    --隐式游标,判断是否改变数据
    if SQL%found then
        dbms_output.put_line('op success');
    else
        dbms_output.put_line('op fail');
    end if;
END;
/
```

显式:

```
DECLARE
    -- ref cursor 动态游标(直到运行时才直到这条查询)
    type table_cursor is ref cursor;
    tab_cursor table_cursor;
    v_row tab%rowtype;
BEGIN
    open tab_cursor for select * from tab;
    loop
        exit when tab_cursor%notfound;
        fetch tab_cursor into v_row;
        dbms_output.put_line(v_row.A||'--'||v_row.B);
    end loop;
END;
/
```

最后一条记录会多打印一次

显式2--简化loop:

```
DECLARE
    cursor tab_cursor is select * from tab;
    v_row tab%rowtype;
BEGIN
    open tab_cursor;
    loop
        fetch tab_cursor into v_row;
        dbms_output.put_line(v_row.id||'--'||v_row.A||'--'||v_row.B);
        exit when tab_cursor%notfound;
    end loop;
END;
/
```

显式3--for--解决多打印的问题:

```
DECLARE
    cursor tab_cursor is select * from tab;
BEGIN
    for X in tab_cursor loop
        dbms_output.put_line(X.id||'--'||X.A||'--'||X.B);
    end loop;
END;
/
```

# 存储过程/函数

### 创建规则

```
create or replace procedure 过程名 (参数名 参数类型[,参数名 参数类型]) is/as
--变量的声明 variable type:=val
begin
--执行sql脚本
--exception
end 过程名;
```

注意:

1. 参数列表中的参数不允许重复声明
2. begin之前的参数可以声明赋值 begin之后的只能赋值

### 调用

```
--方式一 test window
begin
    过程名;
end;

--方式二 command window
exec 过程名;

--方式三 sql window
call 过程名(...);
```

### hello world

```
create or replace procedure p_first as
    v_row tab%rowtype;
begin
    select * into v_row from tab where id=2;
    dbms_output.put_line(v_row.A||'--'||v_row.B);
end p_first;

--方式一 test window
begin
    p_first;
end;

--方式二 command window
exec p_first;

--方式三 sql window
call p_first();
```

## 参数

1. 输入: 参数名  in  参数类型(不需要带具体位数 比如varchar2而不是varchar2(30))

2. 输出: 参数名  out  参数类型

3. in out 实际参数值可以传递到子程序中,可读取和写入.过程结束后,控制会返回调用环境,同时将形参内容分配给实际参数

   NOCOPY 只是一个编译器暗示,不一定总是起作用,**传引用**方式,**会在赋值的时候立即生效**(默认是以传值的方式进行调用的) 使得形参和ACTUAL VALUE指向同一内存地址

默认情况下,out和 in out参数通过传值方式,in参数传引用.程序执行前IN OUT参数的实际值将被复制给形参,如果程序正常退出,这些值将被复制回原参数.如果程序异常退出,那么原参数不会改变.且当参数是大数据结构,通过传值方式的copy动作会导致程序执行速度下降,内存增大.
   

```
create or replace procedure p_first(v_a in integer,v_row out tab%rowtype) as
begin
    select * into v_row from tab where id=v_a;
end p_first;
/

declare
    v_row  tab%rowtype;
begin
     p_first(2,v_row);
    dbms_output.put_line(v_row.A||'--'||v_row.B);
end;

```

out参数 可以通过 

1. select v_xx into o_xx from dual;获取
2. 直接赋值 o_xx:=v_xx;



## 方法传递参数

1. 位置标记

   　findMin(a, b, c, d);

2. 命名符号

   ```sql
   findMin(x=>:a, y=>b, z=>c, m=>d);
   ```

   :a表示 引用的项目或参数   设定要输入/输出的字符参数 &a 表示要输入/输出的字符串

3. 混合符号

　　**位置标记应先于指定符号**

​		findMin(a, b, z=>c, m=>d);

## 异常

    --SQLCODE 0成功;-1失;100 没有检索到数据;+1 用户自定义异常
    --SQLERRM 数据库异常信息 如ORA-00001:违反唯一约束条件
    exception
        when others then
                dbms_output.put_line(SQLCODE||'--'||SQLERRM);
## 程序包

> 包是一组相关过程,函数,变量游标,常量等组合,具有变向对象程序设计语言的特点;
>
> 而变量相当于类中的成员变量,过程和函数相当于方法.
>
> 一个程序包分为两部分组成:
>
> 1. 包定义	
> 2. 包主体  实现

```
create or replace package 包名 is
    procedure 过程名(...);
    function  方法名(...);
end;
/


create or replace package body 包名 is
    procedure 过程名(...) is
    begin
        ...
    end;
    function  方法名(...) return xxx is
    begin
        ...
    end;
end;
```

## java调用

```
callableStatement cs=conn.preparecall("{call procedurexx(?,?,?,?)}")
入参  cs.setString(ordinal,xxx);
出参  cs.registerOutParameter(ordinal,xxx);
callableStatement没有获取结果集方法  ResultSet rs=(ResultSet)cs.getObject(parameterIndex) 调查过程参数,从1开始
```

# 触发器

> ​	触发器在数据库中以独立的对象存储,由一个事件启动运行(自动隐式).不能接受参数,不能被显式调用.
>
> ​	事件指的是对数据库表/视图的insert/update/delete,oracle堆这个概念做了扩展,包括数据库打开关闭等.

## 分类

1. DML触发器

   DML操作前或操作后进行触发,可以具体到影响到的每行数据

2. 替代触发器

   一般视图做DML操作是不允许的,这是视图进行DML操作的一种处理方法

3. 系统触发器

   DDL或DB事件触发(如startup,shutdown,logon,logoff数据库时)

## 触发器组成

1. 触发事件: 何种情况下触发trigger

2. 触发时间:trigger是在触发时间发生 before还是after

3. 触发器本身: 触发器要做的事

4. 触发频率:语句级(statement) 和 行级(row)

   statement:  某事件触发后,触发器只执行一次

   row: 某事件触发后,收到该操作影响的每一行,触发器都单独执行一次

### 创建DML触发器

​	每张表最多可以创建12个

```
insert into tab values(1,'a1','b1');
insert into tab values(2,'a2','b2');


create or replace trigger trigger_name
after --after/before
delete --delete/insert/update
on tab
for each row --statement(默认)/row
declare
--local variable
begin
    --:OLD DML操作完成之前的列值 insert=null
    --:NEW DML操作完成之后的列值 delete=null
    --上述只适用于行级
    dbms_output.put_line(:OLD.id||'--'||:OLD.a||'--'||:OLD.b);
end trigger_name;
/

```

## 创建替代触发器

instaed of 选项使oracle激活触发器,而不执行出发时间.只能对视图建立

```
create or replace trigger trigger_name
instead of insert/update/delete
on view_nmae
for each row
begin
    ...
end trigger_name;
/
```

## 系统触发器

```
create or replace trigger trigger_name
before|after create|alter|drop|truncate
on schema|database
....

create or replace trigger trigger_name
before|after startup|shutdown|logon|logoff
on database
....

```

#　Job

DBMS_JOB用于运行编制的pl/sql程序计划

[(7条消息) Oracle 定时任务详解（dbms_job）_鱼丸丶粗面-CSDN博客_oracle查询dbms_job](https://blog.csdn.net/qq_34745941/article/details/99857323)

```
create table dbms_job_history (
  message     varchar2(100),
  create_date date
);


create or replace procedure p_dbms_job_test as
begin
  -- 记录 job 信息
  insert into dbms_job_history
    (message, create_date)
  values
    ('dbms_job', sysdate);

  commit;
end;
declare
  job binary_integer;
begin
  dbms_job.submit(job       => job,
                  what      => 'p_dbms_job_test();',
                  next_date => sysdate, -- 立即执行
                  interval  => 'sysdate + 3/1440' -- 1天 = 24*60*60 = 1440
                  );
  -- 记得哦         
  commit;
end;

```

问题:identifier 'SYS.DBMS_JOB' must be declared

​	grant execute on sys.dbms_job to public;

​	其中 ***\*public\**** 可以替换为具体的数据库用户 <user>

```
procedure submit(job       out binary_integer,
                 what      in varchar2,
                 next_date in date default sysdate,
                 interval  in varchar2 default 'null',
                 no_parse  in boolean default false, -- 是否需要解析与 job 相关的过程
                 instance  in binary_integer default 0, -- 指定哪个实例可以运行 job
                 force     in boolean default false); -- 是否强制运行与 job 相关的实例

说明：参数 no_parse、instance、force 一般不指定
```

# 树状结构

selectStatement [start with condition] connect by [prior] id=parentid

 * start with condition1 是用来限制第一层的数据，或者叫根节点数据；

 * connect by [prior] **id=parentid**  查找第二层的数据时用第一层数据的**id**去跟表里面记录的**parentid**字段进行匹配，如果这个条件成立那么查找出来的数据就是第二层数据，同理查找第三层第四层…  **自顶而下**

 * **id=[prior] parentid**  自底而上,第一层是叶子节点 用第一层数据的parentid去跟表记录里面的id进行匹配

 * level关键字，代表树形结构中的层级编号；第一层是数字1，第二层数字2，依次递增

   如1-10的序列 select level from dual where level<=10;

 * CONNECT_BY_ROOT（字段名） 获取第一层集结点结果集中的任意字段的值

​	

在扫描树结构表时，需要依此访问树结构的每个节点，一个节点只能访问一次，其访问的步骤如下：
    第一步：从根节点开始；
    第二步：访问该节点；
    第三步：判断该节点有无未被访问的子节点，若有，则转向它最左侧的未被访问的子节，并执行第二步，否则执行第四步；
    第四步：若该节点为根节点，则访问完毕，否则执行第五步；
    第五步：返回到该节点的父节点，并执行第三步骤。
    总之：扫描整个树结构的过程也即是**先序/中序遍历树**的过程。

**SELECT . . .
CONNECT BY {PRIOR 列名1=列名2|列名1=PRIOR 裂名2}
[START WITH]  where 条件3;**
   其中：CONNECT BY子句说明每行数据将是按层次顺序检索，并规定将表中的数据连入树型结构的关系中。PRIOR运算符必须放置在连接关系的两列中某一个的前面。对于节点间的父子关系，**PRIOR运算符在一侧表示父节点，在另一侧表示子节点，从而确定查找树结构是的顺序是自顶向下还是自底向上**。

   在连接关系中，除了可以使用列名外，还允许使用列表达式。START WITH 子句为可选项，用来标识哪个节点作为查找树型结构的根节点，若该子句被省略，则表示所有满足查询条件的行作为根节点。
   START WITH：不但可以指定一个根节点，还可以指定多个根节点。

**2．关于PRIOR(前一条记录/父记录 逻辑层面)
**   运算符PRIOR被放置于等号前后的位置，决定着查询时的检索顺序。
   PRIOR被置于CONNECT BY子句中等号的前面时，则强制从根节点到叶节点的顺序检索，即由父节点向子节点方向通过树结构，我们称之为自顶向下的方式。如：
   CONNECT BY PRIOR EMPNO=MGR
   PIROR运算符被置于CONNECT BY 子句中等号的后面时，则强制从叶节点到根节点的顺序检索，即由子节点向父节点方向通过树结构，我们称之为自底向上的方式。例如：
   CONNECT BY EMPNO=PRIOR MGR
   在这种方式中也应指定一个开始的节点。

**3．定义查找起始节点**
   在自顶向下查询树结构时，不但可以从根节点开始，还可以定义任何节点为起始节点，以此开始向下查找。这样查找的结果就是以该节点为开始的结构树的一枝。

**4．使用LEVEL**
   在具有树结构的表中，每一行数据都是树结构中的一个节点，由于节点所处的层次位置不同，所以每行记录都可以有一个层号。层号根据节点与根节点的距离确定。不论从哪个节点开始，该起始根节点的层号始终为1，根节点的子节点为2， 依此类推。图1.2就表示了树结构的层次。

**5．节点和分支的裁剪**
   在对树结构进行查询时，可以去掉表中的某些行，也可以剪掉树中的一个分支，使用WHERE子句来限定树型结构中的单个节点，以去掉树中的单个节点，但它却不影响其后代节点（自顶向下检索时）或前辈节点（自底向顶检索时）。

**6．排序显示**
   像在其它查询中一样，在树结构查询中也可以使用ORDER BY 子句，改变查询结果的显示顺序，而不必按照遍历树结构的顺序。

**7. 实例**

   oracle 提供了start with connect by 语法结构可以实现递归查询。

自顶而下:

``` 
start with orgid=3
connect by parentid=prior orgid
等价于
start with orgid=3
connect by prior orgid=parentid

```

自底而上:

```
start with orgid=3
connect by orgid=prior parentid
等价于
start with orgid=3
connect by prior parentid= orgid
```

# 事务

Oracle数据库的默认事务隔离级别是提交读（Read Committed）。提交数据有三种类型：显式提交、隐式提交及自动提交。

1. SQL>COMMIT;

2. ALTER，AUDIT，COMMENT，CONNECT，CREATE，DISCONNECT，DROP，EXIT，GRANT，NOAUDIT，QUIT，REVOKE，RENAME 隐式提交

3. 若把AUTOCOMMIT设置为ON，则在插入、修改、删除语句执行后，系统将自动进行提交，这就是自动提交

   SET AUTOCOMMIT ON; 

## 自治事务

按照正常的逻辑,当事务失败重新运行时，用来编写日志条目的[INSERT](https://so.csdn.net/so/search?q=INSERT&spm=1001.2101.3001.7020)语句还未完成.针对这种困境，Oracle提供了一种便捷的方法，即自治事务。自治事务从当前事务开始，在其自身的语境中执行。它们能独立地被提交或重新运行，而不影响正在运行的事务。因为自治事务是与主事务相分离的，所以它不能检测到被修改过的行的当前状态。反之,主事务能够检测到已经执行过的自治事务的结果。

is pragma autonomous_transaction

特性:

1. 非自治事务中commmit,rollback是会影响整个事务的
2. 自治事务的控制(commit/rollback)不会对主事务的控制产生影响

MySQL的InnoDB存储引擎不支持自治事务功能

# 函数

* table()

  查询函数返回的结果集，就如同查询普通表一样查询返回的结果集

  oracle内存表在查询和报表的时候用的比较多，它的速度相对物理表要快几十倍

  
