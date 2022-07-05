

## 前置知识

* 条件: if-elif-else 必须要带: 除非是推导语句

* '''....''' 既可以表示多行注释 也可以表示多行字符串 (python中' "完全等价)

* Python 模块(Module)，是一个 Python 文件，以 .py 结尾 

  将模块中多有函数导入 from somemodule import *

* for-in/while/while-else(不满足while条件了就会执行else)

* pass之后的语句还会被执行

* python没有++ 数字是不可变类型 改变引用而不是只改变引用的值

* r"...." 字符串中的内容常规显示(转义等失效)

* enumerate(iterak): (index,value) 可以获取索引和值

* global x 表示在当前函数中应用的是全局变量x(不存在则会创建x)

* 文件模式

  r: read 指针文件头部 不存在报错 默认 w: write 存在覆盖 不存在新建 a:append 存在则在文件末尾增加 不存在新建

  rb wb ab 二进制文件

  r+(写的时候在当前指针位置 即文件末尾) w+

  (read 返回为空 存在则覆盖) a+(read 返回为空 存在则追加) 读写两种操作,且以上三种操作方式均不可同时进行读写操作 

  

* try:

  ​	A

  except Exception as result/except (IOError,TimeoutError) as result:

  ​	B

  finally:

  ​	C

  嵌套异常时如果内部嵌套已经捕获处理对外部而言不会再捕获到该异常

* *cURL*是一个利用URL语法在命令行下工作的文件传输工具 

* 普通方法定义函数：def  plus(a,b):
  　　　　　　　　　　　return a+b

  lambda方法  ：　　lambda a,b: a+b  不可嵌套 不能使用 return print if else 

* python没有三目表达式 可以使用 '结果1' if 条件 else '结果2 '

* super(type[, object-or-type])
  Return the superclass of type. If the second argument is omitted(lack) the super object returned is **unbound**. If the second argument is an object, isinstance(obj, type) must be true. If the second argument is a type, issubclass(type2, type) must be true. super() only works for new-style classes

  `super`返回的是一个代理对象 

  python允许多重继承,非显式调用防止多次调用

* 清华大学源  -i https://pypi.tuna.tsinghua.edu.cn/simple





数据模型

* Python 解释器碰到特殊的句法时， 会使用特殊方法去激活一些基本的对 象操作， 这些特殊方法的名字以两个下划线开头， 以两个下划线结尾   如 my_collection[index] 解释器实际会调用my_collection._ _getitem__(index). 特殊方法有被称为magic或dunder(双下)方法

* 实现了 __getitem__ 方法     该数据类型可迭代 

  迭代通常是隐式的， 譬如说一个集合类型没有实现 __contains__ 方法， 那么 in 运算符 就会按顺序做一次迭代搜索。    

* random.shuffle 不能对不可变对象(immutable)

  使用\__setitem__ 方法 -> 洗牌功能    

* repr 就是通过 __repr__ 这个特殊方法来得到一个对象的字 6 6 符串表示形式的。 如果没有实现 __repr__， 当我们在控制台里打印一个向量的实例时， 得到的字符串可能会是 <Vector object at 0x10e100070>    

  _repr__ 和 __str__ 的区别在于， 后者是在 str() 函数被使用， 或是在用 print 函数 打印一个对象的时候才被调用的  .

  如果一个对象 没有 __str__ 函数， 而 Python 又需要调用它的时候， 解释器会用 __repr__ 作为替代    

* 

# 数据结构

* 容器序列 能存放任意类型对象的引用 如list tuple

  扁平序列 存放的是某种类型的值 是一段连续的内存空间 如str bytes等基本数据类型

* 可变 如list 不可变序列 如tuple ![1633962852575](C:\Users\ADMINI~1.SC-\AppData\Local\Temp\1633962852575.png)

  

## 列表

* id(iteral) 等价于 hash

- list comprehension(列表推倒) 如 [ord(symbol) for symbol in symbols]    

  通常的原则是， 只用列表推导来创建新的列表， 并且尽量保持简短。 如果列表推导的代码超过了两行， 你可能就要考虑是不是得用 for 循环重写了。    

  Python 会忽略代码里 []、 {} 和 () 中的换行， 因此如果你的代码里有多行的列表、 列表推导、 生成器表达式、 字典这一类的， 可以省略不太好看的续行符 \。    

  列表推导不会再有变量泄漏的问题    (Python 3 中都有了自己的局部作用域    2.x同样会有变量污染问题)

  多元: 列表里的元素是由输入的可迭代类型的元素对构成的元组    如tshirts = [(color, size) for color in colors for size in sizes]    

- generator expression(生成器表达式)的语法跟列表推导差不多， 只不过把方括号换成圆括号而已    

  生成器表达式逐个产出元素    而不是先建 立一个完整的列表， 然后再把这个列表传递到某个构造函数里.可以节省内存空间

## 元组

元组其实是对数据的记录： 元组中的每个元素都存放了记录中一个字段的数据， 外加这个 字段的位置。     

* unpack(元组拆包)可以应用到任何可迭代对象上， 唯一的硬性要求是， 被可迭代对象中 的元素数量必须要跟接受这些元素的元组的空档数一致。 除非我们用 * 来表示忽略 多余的元素    _ 占位符  表示不感兴趣的单个字段

  另外一个很优雅的写法当属不使用中间变量交换两个变量的值    b, a = a, b    

  函数用 *args 来获取不确定数量的参数算是一种经典写法了    

* collections.namedtuple   除了从普通元组那里继承来的属性之外， 具名元组还有一些自己专有的属性。

  _fields 类属性、 类方法 _make(iterable) (与ClassA(*iterable)作用相同)和实例方法 _asdict()(以 collections.OrderedDict 的形式返回    )。 等     

* 除了跟增减元素相关的方法之外， 元组几乎支持列表的其他所有方法。    

* Python在显示只有1个元素的tuple时，也会加一个逗号`,` 

## 切片

语法：[start:stop:step]

step代表切片步长；切片区间为[start，stop)，包含start但不包含stop

1.step > 0，从左往右切片

2.step <0，从右往左切片

3.start、stop、step 为空值时的理解：

start、stop默认为列表的头和尾，并且根据step的正负进行颠倒；step的默认值为1

4.start、stop为负，无论step正负，start、stop代表的是列表从左到右的倒数第几个元素



* Python 内置的序列类型都是一维的， 因此它们只支持单一的索引， 成对出现的索引是没有 用的。    

所以对多维切片可以转成单维切片



* 给切片赋值: 如果赋值的对象是一个切片， 那么赋值语句的右侧必须是个可迭代对象。    

### + *

通常 + 号两侧的序列由相同类型的数据 所构成， 在拼接的过程中， 两个被操作的序列都不会被修改， Python 会新建一个包含同样 类型数据的序列来作为拼接的结果。    

如果在 a * n 这个语句中， 序列 a 里的元素是对其他可变对象的引用的话    得到的列表里包含 的 3 个元素其实是 3 个引用， 而且这 3 个引用指向的都是同一个列表。     

### += *=

__iadd__ 方法   可变对象会就地改动    而不是产生新的对象 不可变对象无此操作

__imul__ 方法   可变对象会就地改动  不可变对象会创键新对象 然后把原来对象中的元素先复制到新的对象里'



t = (1, 2, [30, 40]) >>> t[2] += [50, 60]    

t 变成 (1, 2, [30, 40, 50, 60])。    因为 tuple 不支持对它的元素赋值， 所以会抛出 TypeError 异常。    

* 不要把可变对象放在元组里面。
*  增量赋值不是一个原子操作。 我们刚才也看到了， 它虽然抛出了异常， 但还是完成了 操作。 
* 查看 Python 的字节码并不难， 而且它对我们了解代码背后的运行机制很有帮助    



### list.sort   

* 如果一个函数或者方法对对象进行的是就地改动， 那它就应该返 回 None， 好让调用者知道传入的参数发生了变动， 而且并未产生新的对象。     
* 与 list.sort 相反的是内置函数 sorted， 它会新建一个列表作为返回值。 这个方法可以 接受任何形式的可迭代对象作为参数， 甚至包括不可变序列或生成器    
* key 一个只有一个参数的函数， 这个函数会被用在序列里的每一个元素上， 所产生的结果 将是排序算法依赖的对比关键字。 比如说， 在对一些字符串排序时， 可以用 key=str.lower 来实现忽略大小写的排序， 或者是用 key=len 进行基于字符串长度的排 序。 这个参数的默认值是恒等函数（identity function） ， 也就是默认用元素自己的值来排 序。   

### bisect 搜索     

bisect.bisect(haystack, needle)    : index

haystack已经排序好的列表

needle需要插入的数据

bisect实际为bisect_right 即与needle相同的后一个索引



bisect.insort(seq, item)     直接将item插入有序的seq队列



## 数组(高效的数值数组)

* 要存放 1000 万个浮点数的话， 数组（array） 的效率要高得多， 因为数组在背后存的并不是 float 对象， 而是数字的机器翻译， 也就是字节表述。    

* 如果我们需要一个只包含数字的列表， 那么 array.array 比 list 更高效。 数组支持所 有跟可变序列有关的操作， 包括 .pop、 .insert 和 .extend。 另外， 数组还提供从文件 读取和存入文件的更快的方法， 如 .frombytes 和 .tofile。

   Python 数组跟 C 语言数组一样精简。 创建数组需要一个类型码， 这个类型码用来表示在 底层的 C 语言应该存放怎样的数据类型。 比如 b 类型码代表的是有符号的字符（signed char） ， 因此 array('b') 创建出的数组就只能存放一个字节大小的整数， 范围从 -128 到 127， 这样在序列很大的时候， 我们能节省很多空间。 而且 Python 不会允许你在数组里 存放除指定类型之外的数据    

* *class* `array.`array(*typecode*[, *initializer*])  如array('d', (random() for i in range(10**7)))    

  ### memoryview 

  它是一个内置类， 它能让用户在不复制内容(在数据结构之间共享内存)的情况下操作同一个数组的不同切片(可以获取同一数组的不同memoryview对象).    

  ### NumPy和SciPy    

  NumPy 实现了多维[同质]数组（homogeneous array） 和矩阵， 

  SciPy 是基于 NumPy 的另一个库， 它提供了很多跟科学计算有关的算法， 专为线性代数、 数值积分和统计学而设计。

  ### deque 线程安全

  ​       deque(iterable=(), maxlen=None)

  ​	rotate: 队列的旋转操作接受一个参数 n， 当 n > 0 时， 队列的最右边的 n 个元素会被移动到 队列的左边。 当 n < 0 时， 最左边的 -n 个元素会被移动到右边。    

  append/appendleft(object)

  extend/extendleft(iterable) 

  pop()/popleft()/remove(object)          

  

  ### queue 线程安全     

  Queue、 LifoQueue 和 PriorityQueue，但是在满员的时候， 这些类不会扔掉旧的 元素来腾出位置。 相反， 如果队列满了， 它就会被锁住， 直到另外的线程移除了某个元素 而腾出了位置。 这一特性让这些类很适合用来控制活跃线程的数量        

# 字典和集合

collections.abc 模块中有 Mapping 和 MutableMapping 这两个抽象基类    

![1634110955177](C:\Users\ADMINI~1.SC-\AppData\Local\Temp\1634110955177.png)

然而， 非抽象映射类型一般不会直接继承这些抽象基类， 它们会直接对 dict 或是 collections.User.Dict 进行扩展。    isinstance(my_dict, abc.Mapping)    用isinstance 而不是type 是因为这个参数有可 能不是 dict， 而是一个比较另类的映射类型。    

标准库里的所有映射类型都是利用 dict 来实现的， 因此它们有个共同的限制， 即只有可 散列的数据类型才能用作这些映射里的键（只有键有这个要求， 值并不需要是可散列的 数据类型） 。    



散列的定义:

如果一个对象是可散列的， 那么在这个对象的生命周期中， 它的散列值是不变 的， 而且这个对象需要实现 __hash__() 方法。 另外可散列对象还要有 __qe__() 方法， 这样才能跟其他键做比较。    

原子不可变数据类型（str、 bytes 和数值类型）      frozenset    包含的所有元素都是可散列类型 的元组

一般来讲用户自定义的类型的对象都是可散列的， 散列值就是它们的 id() 函数的返 回值， 所以所有这些对象在比较的时候都是不相等的。    但如果__eq__    用到了这个对象的内部状态的话,必须保证这些状态不可变    



## 构建方式

* dict(one=1, two=2, three=3)    

* {'one': 1, 'two': 2, 'three': 3}    

* dict({'three': 3, 'one': 1, 'two': 2}) 

* dict([('two', 2), ('one', 1), ('three', 3)])    

* dict(zip(['one', 'two', 'three'], [1, 2, 3]))   

  ​	zip(iter1 [,iter2 [...]]) --> zip object

  ​	Return a zip object whose .__next__() method returns a tuple wherethe i-th element comes from the i-th iterable argument.  IT means a list of tuple

  

if obj in dic 查看dic,key()里是否有obj



## 字典推导

如country: code for code, country in DIAL_CODES}     

{code: country.upper() for country, code in country_code.items()   if code < 66}    

* d.update(m, [**kargs])    函数首先检查 m 是否有 keys 方法， 如果有， 那么 update 函数就把它当作映射对象来处理。 否则， 函数 会退一步， 转而把 m 当作包含了键值对 (key, value) 元素的迭代器。     
* d.setdefault(k, [default])    若字典里有键k， 则把它对应的值不变， 然 后返回这个值； 若无， 则让 d[k] = default， 然后返回 default     



* 某个键在映射里不存在， 我们也希望在通过这个键读取值的时 候能得到一个默认值。 

  *  通过 defaultdict 这 个类型    

    ```python
        # dct=collections.defaultdict(list)
        def listContructor():
            return [1,2]
        dct=collections.defaultdict(listContructor)
        print(dct['new_key'])   #[1,2]
        print(dct.get('new_key2'))   #None
    ```

    把 list 构造方法作为 default_factory 来创建一个 defaultdict。  

    如果 dct并没有 word 的记录， 那么 default_factory 会被调用， 为查询不到的键 创造一个值。 

    **一切背后的功臣其实是特殊方法 missing。 它会在 defaultdict 遇到找不到 的键的时候调用 default_factory**          

    

    __getitem__    不存在调用__missing__     未实现__missing__抛出异常

    get 如果不存在 -> None

  * 自己定义一个 dict 的子类， 然后在子类中实现 __missing__ 方法。     

    ```
    class MyMapping(dict):
        def __missing__(self, key):
            return self[key]
        def get(self, key,default=None):
            try:
                return self[key]
            except KeyError:
                return  default
         def __contains__(self, key):
    	return key in self.keys() or str(key) in self.keys()        
                
                
                myMapping= dict({'a':1})
                print(myMapping['b'])  #KeyError
                print(myMapping.get('c')) #default
    ```

    Python 3 中    dict.keys() 的返回值是一个“视图”。 视图就像 一个集合， 而且跟字典类似的是， 在视图里查找一个元素的速度很快。    Python 2 的 dict.keys() 返回的是个列表 ,  需要扫描整个列表    

  ### dict的子类

  * collections.OrderedDict    这个类型在添加键的时候会保持顺序， 因此键的迭代次序总是一致 的。     my_odict.popitem(last=True)  False删除并返回第一个    

  *    collections.ChainMap    容纳数个不同的映射对象     查找时 每个对象依次内部查找(找到就返回)

  * collections.Counter    这个映射类型会给键准备一个整数计数器。 每次更新一个键的时候都会增加这个计数 器。     most_common([n]) 会按照次序返回映射里 最常见的 n 个键和它们的计数    

  * colllections.UserDict    让用户 继承写子类的。    

    UserDict 继承的是 MutableMapping 而并不是并不是 dict 的子类    

    UserDict 有一个叫 作 data 的属性， 是 dict 的实例， 这个属性实际上是 UserDict 最终存储数据的地方。    

    ```python
    def __contains__(self, key):
    	return str(key) in self.data 
    def __setitem__(self, key, item):
    	self.data[str(key)] = item 
       
    ```



### immutable Mapping

from types import MappingProxyType    

d = {1:'A'}     #允许修改

d_proxy = MappingProxyType(d)    #d的一个只读的映射视图,但是它是动态的   (原映射做出了改动， 我们通过这个视图可以观察到    )   



### 集合论(set|frozenset    )

集合中的元素必须是可散列的， set 类型本身是不可散列的    

a | b 返 回的是它们的合集， a & b (set(needles).intersection(haystack)    intersection_update == a&= )得到的是交集， 而 a - b 得到的是差集。    

* 集合字面量    {1}、 {1, 2}    如果是空集， 那么必须写成 set() 的形式。    ({}    空字典)    
* 字面量会用BUILD_SET 的字节码来创建集合    



s.isdisjoint(z)     查看 s 和 z 是否不相交（没有共同元素）    

e in s    元素 e 是否属于 s     

s <= z /s.issubset(iteral)       s 是否为 z 的子集    

s >= z  /s.issuperset(it)    s 是否为 z 的父集    	





#文本和字节序列



# 函数

# 爬虫

需要用到的库

* urllib3

  1. from urllib import request

  		request.urlopen('http://www.httpbin.org').read().decode('utf-8')

  	​	

  2. [`urllib.parse`](https://docs.python.org/zh-cn/3.7/library/urllib.parse.html#module-urllib.parse) 用于解析 URL,可以将一个URL字符串分割成其组件，或者将URL组件组合成一个URL字符串 

  		`urllib.parse.``urlparse`(*urlstring*, *scheme=''*, *allow_fragments=True*)  Parse a URL into six components. scheme/netloc/path/params/query/fragment

  		`urllib.parse.``urlencode`(*query*, *doseq=False*, *safe=''*, *encoding=None*, *errors=None*, *quote_via=quote_plus*)

  Convert a mapping object or a sequence of two-element tuples, which may contain [`str`](https://docs.python.org/zh-cn/3.7/library/stdtypes.html#str) or [`bytes`](https://docs.python.org/zh-cn/3.7/library/stdtypes.html#bytes) objects, to **a percent-encoded ASCII text string**. If the resultant string is to be used as **a *data* for POST operation with the [`urlopen()`](https://docs.python.org/zh-cn/3.7/library/urllib.request.html#urllib.request.urlopen) function**, then it should be encoded to **bytes**, otherwise it would result in a [`TypeError`](https://docs.python.org/zh-cn/3.7/library/exceptions.html#TypeError).

  ```
  data=bytes(parse.urlencode(dict({'a':1,'b':'2'})),encoding='utf-8')
  ```

  

  3. request.Request(self, url, data=None, headers={},

  ​                 origin_req_host=None, unverifiable=False,
                   method=None)

  *origin_req_host* 应为发起初始会话的请求主机 ,这是用户发起初始请求的主机名或 IP 地址 

  *unverifiable* 应该标示出请求是否无法验证 ,默认值为 `False` 是指用户没有机会对请求的 URL 做验证 

  ```python
      headers={"User-Agent":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36"}
      html=""
      try:
          req=request.Request(url='http://www.douban.com',data=None,headers=headers
                                 ,origin_req_host='192.168.1.66',unverifiable=True,method='GET')
          response= request.urlopen(req)
          html= response.read().decode('utf-8')
      except request.URLError as e:
          if hasattr(e,"code"):
              print(e.code)
          if hasattr(e,'reason'):
              print(e.reason)
      return html
  ```

* bs4 beautifulSoup4

  将html页面转化为复杂的树状结构,每个节点都是python Object 

  ```
  """
  markup: A string or a file-like object representing
   markup to be parsed.
   
  features: Desirable features of the parser to be
   used. This may be the name of a specific parser ("lxml",
   "lxml-xml", "html.parser", or "html5lib") or it may be the
   type of markup to be used ("html", "html5", "xml"). It's
   recommended that you name a specific parser, so that
   Beautiful Soup gives you the same results across platforms
   and virtual environments.
   
   builder:A TreeBuilder subclass to instantiate (or
   instance to use) instead of looking one up based on
   `features`.
   
   parse_only: A filter. Only parts of the document
   matching the filter will be considered.
   
  """
  bs=bs4.BeautifulSoup(html,"html.parser")
  ```

  节点分为以下四类

  * Tag

     bs.tag 获取第一个符合的**标签**

  * NavigableString

    bs.tag.string

    bs.tag.attrs获取所有属性 必须通过attrs['a'] 而非attrs.a

  * BeautifulSoup 表示整个文档 即 bs本身

    遍历

    - [`parent`](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#parent)
    - [`contents`](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#contents)
    - [`string`](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#string)
    - [`nextSibling` and `previousSibling`](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#nextSibling%20and%20previousSibling)  下一个兄弟tag
    - [`next` and `previous`](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#next%20and%20previous)  下一个
    - ....

    搜索

    * `findAll(`[name](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#arg-name), [attrs](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#arg-attrs), [recursive](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#arg-recursive), [text](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#arg-text), [limit](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#arg-limit), [**kwargs](https://www.crummy.com/software/BeautifulSoup/bs3/documentation.zh.html#arg-**kwargs))

      name 是匹配标签的name,可以用字符串,正则,列表,字典(如{'title' : True, 'p' : True}),lambda(lambda tag: len(tag.name) == 1 and not tag.attrs)

      limit: Stop looking after finding this many results.

      recursive: If this is True, find_all() will perform a
                  recursive search of this PageElement's children. Otherwise,
                  **only the direct children will be considered**.

      kwargs: A dictionary of filters on attribute values.

      text: return list of text instead PageElement

    * class Tag(PageElement):has_attr function define if has one sepecified attribute

    * select(self, selector, namespaces=None, limit=None, **kwargs):

      ```
      Perform a CSS selection operation on the current element.
       namespaces: A dictionary mapping namespace prefixes
                 used in the CSS selector to namespace URIs.
                          
                 
      基本：.class #id p * div,p dic>p div p div+p(div之后同级第一个元素) div~p(div下面的所有兄弟元素)
      属性：[target] [target=value] ~=(单词包含)
      |=（包含或以-连接）*=（子串）^= $=
      伪类：a:link(未被访问) a:visited a:hover(鼠标移动上去)
      :empty(没有任何内容，包括空白)
      p:first-child/last（是p标签且是第一个子元素，包括祖先继承）
      p:first-of-type/last
      p:only-of-type/p:only-child
      p:nth-child(2)/p:nth-of-type(2)
      p:nth-last-child(2)/p:nth-last-of-type(2)
      字符伪类：p::first-letter p:：before(在元素前面添加内容)
      ```

    ​      需要注意的是获取到的是ResultSet object 如果后续需要配合正则,一般需要str()

  * Comment

    bs.tag.string NavigableString的子类 获取注释内容

    注意 标签内注释和普通字符串混用,string获取到的是NoneType,需要用contents,获取到的是list

    

  

* re

   . 匹配除了换行的任意字符 

  *+? 默认尽量多的匹配字符串  即贪婪模式; 在修饰符之后添加 ? 将使样式以 非贪婪`方式或者 :dfn:`最小 方式进行匹配

  {m,n}?   非贪婪模式 只匹配m次

  ```py
  re.DEBUG
  显示编译时的debug信息，没有内联标记。
  
  re.I
  re.IGNORECASE
  进行忽略大小写匹配；表达式如 [A-Z] 也会匹配小写字符。Unicode匹配（比如 Ü 匹配 ü）同样有用，除非设置了 re.ASCII 标记来禁用非ASCII匹配。当前语言区域不会改变这个标记，除非设置了 re.LOCALE 标记。这个相当于内联标记 (?i) 。
  
  注意，当设置了 IGNORECASE 标记，搜索Unicode样式 [a-z] 或 [A-Z] 的结合时，它将会匹配52个ASCII字符和4个额外的非ASCII字符： 'İ' (U+0130, 拉丁大写的 I 带个点在上面), 'ı' (U+0131, 拉丁小写没有点的 I ), 'ſ' (U+017F, 拉丁小写长 s) and 'K' (U+212A, 开尔文符号).如果使用 ASCII 标记，就只匹配 'a' 到 'z' 和 'A' 到 'Z' 。
  
  re.L
  re.LOCALE
  由当前语言区域决定 \w, \W, \b, \B 和大小写敏感匹配。这个标记只能对byte样式有效。这个标记不推荐使用，因为语言区域机制很不可靠，它一次只能处理一个 "习惯”，而且只对8位字节有效。Unicode匹配在Python 3 里默认启用，并可以处理不同语言。 这个对应内联标记 (?L) 。
  	
  在 3.6 版更改: re.LOCALE 只能用于byte样式，而且不能和 re.ASCII 一起用。
  
  在 3.7 版更改: 设置了 re.LOCALE 标记的编译正则对象不再在编译时依赖语言区域设置。语言区域设置只在匹配的时候影响其结果。
  
  re.M
  re.MULTILINE
  设置以后，样式字符 '^' 匹配字符串的开始，和每一行的开始（换行符后面紧跟的符号）；样式字符 '$' 匹配字符串尾，和每一行的结尾（换行符前面那个符号）。默认情况下，’^’ 匹配字符串头，'$' 匹配字符串尾。对应内联标记 (?m) 。
  
  re.S
  re.DOTALL
  让 '.' 特殊字符匹配任何字符，包括换行符；如果没有这个标记，'.' 就匹配 除了 换行符的其他任意字符。对应内联标记 (?s) 。
  
  re.X
  re.VERBOSE
  这个标记允许你编写更具可读性更友好的正则表达式。通过分段和添加注释。空白符号会被忽略，除非在一个字符集合当中或者由反斜杠转义，或者在 *?, (?: or (?P<…> 分组之内。当一个行内有 # 不在字符集和转义序列，那么它之后的所有字符都是注释。
  
  ```

```
findall(pattern, string, flags=0)

	Return a list of all non-overlapping(不重叠) matches in the string.

    If one or more capturing groups are present in the pattern, return

    a list of groups; this will be a list of tuples if the pattern

    has more than one group.

    Empty matches are included in the result.

```

```
search(pattern, string, flags=0)

	Scan through string looking for a match to the pattern, returning

    a match object, or None if no match was found.

```

```
sub(pattern, repl, string, count=0, flags=0)

	Return the string obtained by replacing the leftmost

    non-overlapping occurrences of the pattern in string by the

    replacement repl. 

```



* xlwt 操作excel的库

  ```
  workbook=xlwt.Workbook(encoding='utf-8') #创建excel对象
  wooksheet=workbook.add_sheet(sheetname='sheet1',cell_overwrite_ok=True)#创建单表
  cols=('A','B','C')
  wooksheet.write(0,0,'Hello') #在行 列写入数据Hello
  workbook.save('workbook1.xls') #保存excel对象成文件
  ```

  

* Flask python的web框架

  核心 werkzeug(路由分发)+jinjia2(模版引擎渲染)

  ```
  from flask import Flask
  
  '''
    self,
          import_name,
          static_url_path=None,
          static_folder="static",
          static_host=None,
          host_matching=False,
          subdomain_matching=False,
          template_folder="templates",
          instance_path=None,
          instance_relative_config=False,
          root_path=None,
          
          when root_path is none,get the root path of import_name
          __name__ represent the  main method of current file
  '''
  app=Flask(__name__)
  
  @app.route("/",methods=['GET','POST'],endpoint='')
  def init():
      return '<h1>Hello World!</h1>'
  
  app.run()
  ```

  首先根据url找到对应mapping方法(方法名称不同取最早的,相同必须有endpoint属性)

* route

```
@app.route('/user/<id>') id代表任意字符串(不包含/)
def id(id):
```

@app.route('/user/<int:id>')

@app.route('/user/<path:id>') 任意字符串(包含/)



* 自定义url mapping converter 

```
#自定义url mapping converter 
class MyConverter(BaseConverter):
    def __init__(self,map,regex):
        super(MyConverter,self).__init__(map)
        self.regex=regex

app.url_map.converters['re']=MyConverter

@app.route('/index/<re("1\d{2}"):value>') #re参数对应regex
def index(value):
    return value
```

* 前端渲染

```
#前端渲染
app=Flask(__name__)

@app.route('/index',methods=['GET','POST']) #默认method为get
def index():
    print(2)
    return render_template('index.html') #默认情况下，Flask在程序文件夹中的 **templates子文件夹** 中寻找模板。

if __name__ == '__main__':
    app.run()
```

* request

```
对于 Web 应用，与客户端发送给服务器的数据交互至关重要。在 Flask 中由全局的 request 对象来提供这些信息。
从Flask模块导入request：from flask import request

request的属性：下面是request可使用的属性，其中黑体是比较常用的。

Request属相

属性名	解释

form 	一个从POST和PUT请求解析的 MultiDict（一键多值字典）。

args	MultiDict，要操作 URL （如 ?key=value ）中提交的参数可以使用 args 属性:searchword = request.args.get('key', '')

values 	CombinedMultiDict，内容是form和args。 

可以使用values替代form和args。

cookies	请求的cookies，类型是dict。

stream	在可知的mimetype下，如果进来的表单数据无法解码，会没有任何改动的保存到这个 stream 以供使用。很多时候，当请求的数据转换为string时，使用data是最好的方式。这个stream只返回数据一次。

headers 	请求头，字典类型。

data 	包含了请求的数据，并转换为字符串，除非是一个Flask无法处理的mimetype。

files 	MultiDict，带有通过POST或PUT请求上传的文件。

environ 	WSGI隐含的环境配置。

method	请求方法，比如POST、GET。

path	获取请求文件路径：/myapplication/page.html

script_root	 

base_url	获取域名与请求文件路径：http://www.baidu.com/myapplication/page.html

url	获取全部url：http://www.baidu.com/myapplication/page.html?id=1&edit=edit

url_root	获取域名：http://www.baidu.com/

is_xhr	如果请求是一个来自JavaScript XMLHttpRequest的触发，则返回True，这个只工作在支持X-Requested-With头的库并且设置了XMLHttpRequest。

blueprint 	蓝图名字。

endpoint 	endpoint匹配请求，这个与view_args相结合，可是用于重构相同或修改URL。当匹配的时候发生异常，会返回None。

json	如果mimetype是application/json，这个参数将会解析JSON数据，如果不是则返回None。 

可以使用这个替代get_json()方法。

max_content_length	只读，返回MAX_CONTENT_LENGTH的配置键。

module 	如果请求是发送到一个实际的模块，则该参数返回当前模块的名称。这是弃用的功能，使用blueprints替代。

routing_exception = None	如果匹配URL失败，这个异常将会/已经抛出作为请求处理的一部分。这通常用于NotFound异常或类似的情况。

url_rule = None	内部规则匹配请求的URL。这可用于在URL之前/之后检查方法是否允许(request.url_rule.methods) 等等。 

默认情况下，在处理请求函数中写下 

print('request.url_rule.methods', request.url_rule.methods) 

会打印：

request.url_rule.methods {‘GET’, ‘OPTIONS’, ‘HEAD’}

view_args = None	一个匹配请求的view参数的字典，当匹配的时候发生异常，会返回None。

其他方法	

get_json(force=False, silent=False, cache=True)

on_json_loading_failed(e)

```

* 重定向

  ```
  from flask import redirect,url_for
  
  # return redirect("https://www.baidu.com")
  return  redirect(url_for('success'))
  ```

* json

  ```
  from flask import json,make_response,jsonify
  
    data={
          'name':'张三'
      }
  
      # return make_response(data) #application/json response_type:python_dict
      # return make_response(json.dumps(data)) # text/html; charset=utf-8 默认ensure_ascii=True unicode
      # return make_response(json.dumps(data,ensure_ascii=False)) #text/html; 能正常显示中文
  
      # response=make_response(json.dumps(data, ensure_ascii=False))
      # response.mimetype='application/json'
      # return response
  
      """Serialize data to JSON and wrap it in a :class:`~flask.Response`
          with the :mimetype:`application/json` mimetype."""
      app.config['JSON_AS_ASCII']=False
      return jsonify(data)
  
  ```

* abort 在网页中主动抛出异常

  ```
  from flask import abort,Response
  
  # abort(404)  # 404 Not Found
  abort(Response('Hello World')) #depend on response and return directly
  ```

* 自定义errorhandler

  ```
  @app.errorhandler(404)
  def errer_handler_404(err):
      return render_template('404.html')
  ```

* 模板 前端获取后台传过来的数据

  ```
     后端:
     data={
          'name':'张三',
          'age':11,
          'courses':['english','chinese']
      }
      return render_template('index2.html',data=data)
      
      前端:
          {{ data.name }}<br/>
  		{{ data.age }}<br/>
  		{{ data.courses }}<br/>
  ```

* 过滤器(转化器) {{value| func}}

  内置:<http://jinja.pocoo.org/docs/dev/templates/#builtin-filters>   比如 {{ 'abcd'|count }}

  自定义:

  ```
  def list_2(list):
      return list[::2]
  
  ''' def add_template_filter(
          self, f: TemplateFilterCallable, name: t.Optional[str] = None
  '''
  app.add_template_filter(list_2,'list2')
  
  #前端使用和内置一样
  {{ data.courses|list2 }}
  ```

  

* 后端表单

  Install ‘email_validator‘ for email validation support. 回退版本pip install WTForms==2.2.1 

  A secret key is required to use CSRF. 添加app.config['SECRET_KEY']='xxxx'

  校验报错 {'csrf_token': ['The CSRF token is missing.']} 解决方式1.WTF_CSRF_CHECK_DEFAULT = False  

  2.请求的时候将生成csrf_token放进cookie传到前台hidden

  ```
  from wtforms import StringField,PasswordField,SubmitField #组件库
  from flask_wtf import  FlaskForm
  from wtforms.validators import data_required,equal_to,Email
  from flask_wtf.csrf import generate_csrf
  
  app.config['SECRET_KEY']='ABCDEF'
  class RegisterForm(FlaskForm):
      account=StringField(label='账号: ',validators=[Email(message='不符合邮箱规则')])
      password=PasswordField(label='密码: ',validators=[data_required('密码不能为空')])
      password2 = PasswordField(label='再次密码: ', validators=[data_required('再次密码不能为空'),equal_to('password')])
      submit=SubmitField(label='提交')
      
      
      @app.route('/index')
  def index():
      form=RegisterForm() #实例化form
      csrf_token=generate_csrf()
      cookie={'csrf_token':csrf_token}
      return render_template('index3.html',form=form,cookie=cookie)
  
  @app.route('/post_action',methods=['POST'])
  def post_action():
      print(request.form)
      submitted_form=RegisterForm(request.form)
      if submitted_form.validate():
          return 'success'
      else:
          print(submitted_form.errors)
          return 'failed'
  
  #前端:
  <form action="/post_action" method="post">
          <input type="hidden" name="csrf_token" value="{{ cookie.csrf_token }}">
              {{ form.account.label }}
              {{ form.account }}
  
          <br/>
               {{ form.password.label }}
              {{ form.password }}
          <br/>
                       {{ form.password2.label }}
              {{ form.password2 }}
          <br/>
              {{ form.submit }}
          <br/>
      </form>
  ```

* sqlalchemy

   ```python
     from flask import Flask
     from flask_sqlalchemy import SQLAlchemy
     import pymysql #由于MySQLdb还不支持3.x 所以用pymsql代替
     import cryptography #sha256_password or caching_sha2_password auth methods
     import os
     from sqlalchemy import or_,and_
   
   
   app=Flask(name)
   
     pymysql.install_as_MySQLdb()
   
     class SqlAlchemyConfig:
   
         SQLALCHEMY_DATABASE_URI='mysql://root:root@localhost:3306/flaskdb'
   
         SQLALCHEMY_TRACK_MODIFICATIONS=True
   
     app.config.from_object(SqlAlchemyConfig)
   
     db=SQLAlchemy(app) #必须先导入config 在创建对象
   
   #角色表
   
     class Role(db.Model):
   
         tablename='role'
   
         id=db.Column(db.Integer, primary_key=True)
   
         name=db.Column(db.String(80),unique=True)
   
   #用户表
   
     class User(db.Model):
   
         tablename='user'
   
         id=db.Column(db.Integer, primary_key=True)
   
         name=db.Column(db.String(80),unique=True)
   
         password=db.Column(db.String(20))
   
         role_id=db.Column(db.Integer,db.ForeignKey('role.id')) #关联role
   
     if name == 'main':
   
    #清空所有表
   db.drop_all()
    #创建所有表
     db.crete_all()
     #创建对象插入并提交
     role1=Role(name='admin')
     role2 = Role(name='admin2')
     db.session.add(role1)
     db.session.add(role2)
     db.session.commit()
     
     user1=User(name='jinjianou',password='123456',role_id=role1.id)
     user2 = User(name='jinjianou2',password='root', role_id=role2.id)
     db.session.add_all([user1,user2])
     db.session.commit()
     
     os.system("pause")
     
     #查看
     print(User.query.all()) #查看所有数据 .get(1)
     # 根据条件查看数据 不存在会报错 User.query.filter(User.name.like('%j%')
     print(User.query.filter(User.name=='jinjianou')[0])
     print(User.query.filter(User.name=='jinjianou3').first()) #不存在不会报错
     print(User.query.filter(and_(User.name == 'jinjianou',User.role_id==1)).first()) .all()
     print("======================")
     
     #修改
     x_user=User.query.filter(User.name=='jinjianou').first()
     x_user.password='root'
     db.session.commit()
     print(User.query.filter(User.name=='jinjianou').first().password)
     print("======================")
     
     #删除
     x_user = User.query.filter(User.name=='jinjianou').first()
     db.session.delete(x_user)
     db.session.commit()
     print(User.query.filter(User.name=='jinjianou').first())
     print("======================")
   ```

* debug 模式  Debug mode: on

   ![1636473463161](C:\Users\ADMINI~1.SC-\AppData\Local\Temp\1636473463161.png)

* 

# 附录一 使用到的函数

​	def print(self, *args, sep=' ', end='\n', file=None):None 转义对sep不小

​    	input(prompt):str

​	int/int => float     int*int => int   int//int =>int(向下取整) 有一遍是float结果就是float

​	def randint(self, a, b):    """Return random integer in range [a, b], including both end points.    ""

* `collections.namedtuple`(*typename*, *field_names*, *, *rename=False*, *defaults=None*, *module=None*) 

  返回一个新的元组子类，名为 *typename* 。这个新的子类用于创建类元组的对象，可以通过字段名来获取属性值，同样也可以通过索引和迭代获取值。 

  *field_names* 是一个像 `[‘x’, ‘y’]` 一样的字符串序列 

  如果 *rename* 为真， 无效字段名会自动转换成位置名 如关键字'def'会转成'_0'

  *defaults* 可以为 `None` 或者是一个默认值的 [iterable](https://docs.python.org/zh-cn/3/glossary.html#term-iterable) 。如果一个默认值域必须跟其他没有默认值的域在一起出现，*defaults* 就应用到**最右边**的参数。 

  如果 *module* 值有定义，命名元组的 `__module__` 属性值就被设置。 

  \*出现在函数参数中第一种含义可以表示为可变参数，一般写作*args；对于单独出现在参数中的*参数，则表示，*后面的参数(所有)必须为关键字参数(如a=...)的形式 

  

* ### range 对象

  [`range`](https://docs.python.org/zh-cn/3/library/stdtypes.html#range) 类型表示不可变的数字序列，通常用于在 [`for`](https://docs.python.org/zh-cn/3/reference/compound_stmts.html#for) 循环中循环指定的次数。

  - *class* `range`(*stop*)

  - *class* `range`(*start*, *stop*[, *step*])

    range 构造器的参数必须为整数（可以是内置的 [`int`](https://docs.python.org/zh-cn/3/library/functions.html#int) 或任何实现了 `__index__` 特殊方法的对象）。

     如果省略 *step* 参数，其默认值为 `1`。 

    如果省略 *start* 参数，其默认值为 `0`，如果 *step* 为零则会引发 [`ValueError`](https://docs.python.org/zh-cn/3/library/exceptions.html#ValueError)。

    如果 *step* 为正值，确定 range `r` 内容的公式为 `r[i] = start + step*i` 其中 `i >= 0` 且 `r[i] < stop`。

    如果 *step* 为负值，确定 range 内容的公式仍然为 `r[i] = start + step*i`，但限制条件改为 `i >= 0` 且 `r[i] > stop`.

    如果 `r[0]` 不符合值的限制条件，则该 range 对象为空。 range 对象确实支持负索引，但是会将其解读为从正索引所确定的序列的末尾开始索引。

* 多重遍历 [Card(rank,suit)for suit in self.suits    for rank in  self.ranks] 不能在类里 可在__init__方法

* random.[`choices()`](https://docs.python.org/zh-cn/3/library/random.html#random.choices) function returns a list(can be repeated) of elements of specified size from the given population with optional weights 

  `random.choices`(*population*, *weights=None*, ***, *cum_weights=None*, *k=1*) 

  weights a list which size match  *population*'s size

* `timeit.`repeat(*stmt='pass'*, *setup='pass'*, *timer=<default timer>*, *repeat=5*, *number=1000000*, *globals=None*) 

  　stmt: 这个参数就是statement,可以把要进行计算时间的代码放在里面。他可以直接接受字符串的表达式，也可以接受单个变量，也可以接受函数。

　　setup:  这个参数可以将stmt的环境传进去。比如各种import和参数什么的。

​	timer: 这个参数一般使用不到，具体使用可以参看文档。 

​	repeat:specifies how many times to call timeit(), defaulting to 3;
	number:  specifies the timer argument, defaulting
			to one million.(一次测试中默认调用及时语句一百万次)

如 
timeit.repeat(cmd, setup=SETUP, number=TIMES)
cmd1='[ord(s) for s in symbols if ord(s) > 127]'
cmd2='list(filter(lambda c: c > 127, map(ord, symbols)))'
cmd3='list(filter(non_ascii, map(ord, symbols)))'

SETUP = """
symbols = '$¢£¥€¤'
def non_ascii(c):
    return c > 127
"""
TIMES=1000000

从效率上讲 cmd1>cmd3>cmd2

* from time import perf_counter as pc    

  t0 = pc(); floats /= 3; pc() - t0    导入精度和性能都比较高的计时器    

* unicodedata.``name`(*chr[, *default*]) 

  返回chr的名称 没有则返回default 

  {chr(i) for i in range(32, 256) if 'SIGN' in name(chr(i),'')}    

# sqllite

1. 特点:

   * 不需要一个单独的服务器进程或操作的系统（无服务器的) 可以按应用程序需求进行静态或动态连接 

   * 不需要配置,不需要安装或管理 

   * 一个完整的 SQLite 数据库 == 单一的跨平台的磁盘文件 

   * 轻量级的 ，完全配置时小于 400KiB 

   * 不需要任何外部的依赖 

   * ACID 

     　　1、原子性（Atomicity）：事务开始后所有操作，要么全部做完，要么全部不做，不可能停滞在中间环节。事务执行过程中出错，会回滚到事务开始前的状态，所有的操作就像没有发生一样。也就是说事务是一个不可分割的整体，就像化学中学过的原子，是物质构成的基本单位。

     　　 2、一致性（Consistency）：事务开始前和结束后，数据库的完整性约束没有被破坏 。比如A向B转账，不可能A扣了钱，B却没收到。其实一致性也是因为原子型的一种表现

     　　 3、隔离性（Isolation）：同一时间，只允许一个事务请求同一数据，不同的事务之间彼此没有任何干扰。比如A正在从一张银行卡中取钱，在A取钱的过程结束前，B不能向这张卡转账。串行化

     　　 4、持久性（Durability）：事务完成后，事务对数据库的所有更新将被保存到数据库，不能回滚。

2. 软件采用 db brower for sqllite

3. 一般来说SQLite 是**不区分大小写**的  例外的比如**GLOB** 和 **glob** 

   --代表注释

   

4. 存储类型(数据类型)

| 存储类  | 描述                                                         |
| ------- | ------------------------------------------------------------ |
| NULL    | 值是一个 NULL 值。                                           |
| INTEGER | 值是一个带符号的整数，根据值的大小存储在 1、2、3、4、6 或 8 字节中。 |
| REAL    | 值是一个浮点值，存储为 8 字节的 IEEE 浮点数字。              |
| TEXT    | 值是一个文本字符串，使用数据库编码（UTF-8、UTF-16BE 或 UTF-16LE）存储。 |
| BLOB    | 值是一个 blob 数据，完全根据它的输入存储。                   |

亲和类型

| 亲和类型 | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| TEXT     | 数值型数据在被插入之前，需要先被转换为文本格式，之后再插入到目标字段中。 |
| NUMERIC  | 当文本数据被插入到亲缘性为NUMERIC的字段中时，如果转换操作不会导致数据信息丢失以及完全可逆，那么SQLite就会将该文本数据转换为INTEGER或REAL类型的数据，如果转换失败，SQLite仍会以TEXT方式存储该数据。对于NULL或BLOB类型的新数据，SQLite将不做任何转换，直接以NULL或BLOB的方式存储该数据。需要额外说明的是，对于浮点格式的常量文本，如"30000.0"，如果该值可以转换为INTEGER同时又不会丢失数值信息，那么SQLite就会将其转换为INTEGER的存储方式。 |
| INTEGER  | 对于亲缘类型为INTEGER的字段，其规则等同于NUMERIC，唯一差别是在执行CAST表达式时。 |
| REAL     | 其规则基本等同于NUMERIC，唯一的差别是不会将"30000.0"这样的文本数据转换为INTEGER存储方式。 |
| NONE     | 不做任何的转换，直接以该数据所属的数据类型进行存储。         |

SQLite 没有单独的 Boolean 存储类。相反，布尔值被存储为整数 0（false）和 1（true）。 

SQLite 没有一个单独的用于存储日期和/或时间的存储类，但 SQLite 能够把日期和时间存储为 TEXT、REAL 或 INTEGER 值。

| 存储类  | 日期格式                                                     |
| ------- | ------------------------------------------------------------ |
| TEXT    | 格式为 "YYYY-MM-DD HH:MM:SS.SSS" 的日期。                    |
| REAL    | 从公元前 4714 年 11 月 24 日格林尼治时间的正午开始算起的天数。 |
| INTEGER | 从 1970-01-01 00:00:00 UTC 算起的秒数。                      |

5.附加表(等价于use database)  ATTACH DATABASE  ATTACH DATABASE file_name AS database_name;

分离表 DETACH DATABASE 'Alias-Name'; 以别名为单位

6. 运算符 global 与 like不同,like(% _)大小写不敏感,glob(* ?)则反之
7.  CHECK(SALARY > 0) 对SALARY 字段进行校验 不满足不输入 (mysql不支持)



## 操作py

import sqlite3

    '''
    打开一个到 SQLite 数据库文件 database 的链接 :memory:在 RAM 中打开
    如果给定的数据库名称 filename 不存在，则该调用将创建一个数据库
    '''
    conn=sqlite3.connect(':memory:')
    print("Opened database successfully")
    """ Return a cursor for the connection. """
    cursor = conn.cursor()
    cursor.execute('''CREATE TABLE COMPANY
           (ID INT PRIMARY KEY     NOT NULL,
           NAME           TEXT    NOT NULL,
           AGE            INT     NOT NULL,
           ADDRESS        CHAR(50),
           SALARY         REAL);''')
    print("Table created successfully")
    conn.commit()
    conn.close()
cursor.executemany('''
       insert into doubantop(movie_link,pic_link,ranking,inq_text,moivie_name_text,ranking_people,brief)
       values(?,?,?,?,?,?,?)
   ''', lst)

UPDATE sqlite_sequence SET seq = 0 WHERE name = "doubantop"
sqllite没有truncate 采用这种方式清空自增主键

# sql修改密码(忘记)

- \1. 关闭正在运行的 MySQL 服务。
- \2. 打开 DOS 窗口，转到 mysql\bin 目录。
- 输入 mysqld --skip-grant-tables 回车 
- \4. 再开一个 DOS 窗口（因为刚才那个 DOS 窗口已经不能动了），转到 mysql\bin 目录。
- \5. 输入 mysql -uroot回车，如果成功，将出现MySQL提示符 >。
- \6. 连接权限数据库： use mysql; 。
- \6. 改密码：alter user 'root'@'localhost' identified by 'root'; 
- \7. 刷新权限（必须步骤）：flush privileges;　
- \8. 退出 quit。
- \9. 注销系统，再进入，使用用户名 root 和刚才设置的新密码 123 登录。

如果mysqld启动失败则

2、初始化设置

输入：mysqld --initialize-insecure --user=mysql

 

3、创建服务

输入： mysqld --install mysql

 

4、启动服务

输入： net start mysql



## 下载iconfont

1.将选中icon加入购物车,并添加至项目

2.将获取的压缩包解压并复制到项目中

3.导入css 通过class+代码的方式使用 <i class="iconfont" style="color: #ffb459;">&#xe602;</i> 



# jinja2 + html

* {{variable}} 从后台获取的数据
* {% code %} python代码

   {% for item in list %}

		....

​    {% endfor%}

* \#{{name}}  可以制造锚点

  \<a href="#{{pic_name}}">...</a>

  ....

  \<a name="{{pic_name}}"></a>



# Echarts

*  https://www.jsdelivr.com/package/npm/echarts?path=dist

* ```
  <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
  <div id="chart_main" style="width: 600px;height:400px;"></div>
  
   <script type="text/javascript">
              // 基于准备好的dom，初始化echarts实例
              var myChart = echarts.init(document.getElementById('chart_main'));
  
              // 指定图表的配置项和数据
              var option = {
                  title: {
                      text: 'ECharts 入门示例'
                  },
                  tooltip: {},
                  legend: {
                      data: ['销量']
                  },
                  xAxis: {
                      data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
                  },
                  yAxis: {},
                  series: [
                      {
                          name: '销量',
                          type: 'bar',
                          data: [5, 20, 36, 10, 10, 20]
                      }
                  ]
              };
  
              // 使用刚指定的配置项和数据显示图表。
              myChart.setOption(option);
          </script>
  ```

* 初始化

  * 指定父容器大小,图表的大小默认即为该节点的大小 

  * 手动指定

    ```
    var myChart = echarts.init(document.getElementById('main'), color_theme如'dark', {
        width: 600,
        height: 400
      });
    ```

* 响应

  可以监听页面的 `window.onresize` 事件获取浏览器大小改变的事件

   window.onresize = function() {    myChart.resize({...});  }; 注意调整的是在div中的大小 div不变 echarts不变

* 在容器节点被销毁时，总是应调用 [`echartsInstance.dispose`](https://echarts.apache.org/api.html#echartsInstance.dispose) 

  ## 折线图

  serial.type='line'

  legend(图例)与serial配合使用

  legend.data 图例的数据数组。数组项通常为一个字符串，每一项代表一个系列的 `name`

  toolbox  工具栏。内置有[导出图片](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.saveAsImage)[saveAsImage](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.saveAsImage)，[数据视图](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.dataView) [dataView](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.dataView)，[动态类型切换](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.magicType)[magicType](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.magicType)，[数据区域缩放](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.dataZoom)[dataZoom](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.dataZoom)，[重置](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.reset)[restore,选框组件 brush](https://echarts.apache.org/v4/zh/option.html#toolbox.feature.restore)工具。 

  ## 饼图

  1.series.type='pie'

  2.data [{ // 数据项的名称 name: '数据1', // 数据项值8 value: 10 }, { name: '数据2', value: 20 }] 

  ```javascript
  const dataAll = [
    {
      // 数据项的名称
      name: '数据1',
      // 数据项值8
      value: 10
    },
    {
      name: '数据2',
      value: 20
    }
  ];
  
  option = {
    series: {
      type: 'pie',
      data: dataAll,
      label: {
        //文字设置
        show: true,
        formatter: function (arg) {
          //文字展示的样式
          return arg.name + '--' + arg.value;
        }
      },
      // radius:'20%', //参照父容器min(宽高)的百分比
      radius:['50%','70%'], //圆环
      roseType:'radius', //扇区圆心角展现数据的百分比，半径展现数据的大小
      selectedMode:'single', //选中模式 multiple  离开原点效果
      selectedOffset: 10 //离开原点距离
      
    }
  };
  
  ```

  ### 地图

  



# JQUERY(js+query)

```js
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="jquery.min.js"></script>
</head>
<body>
    <div><div>
    <div class="box1"></div>
    <div class="box2"></div>
</body>
<script>
    var $box1=$('div')[0]
    var $box2=$('.box1')[0]
    var $box3=$('.box2')[0]
    console.log($box1);
    console.log($box2);
    console.log($box3);
</script>
</html>
```

1.x 兼容ie678 多以文件比较大 2.x 3.x不兼容

* 语法

```js
    //原生js
    window.onload = function () { }

    //jquery的固定写法
    $(document).ready(
        function () {
            alert("jquery")
        }
    );
	//第二种 $ 换成 jquery
	//第三种
   $(function () {
        alert("xxx")
    })
```

* 区别:
  * 原生的后面的onload会覆盖前面的,jquery则不会
  * 原生的等文档+图片加载后才触发，jquery等文档加载好就触发（清空缓存+liveServer）
* 冲突问题

```js
        /**
         * jquery 可能会存在多框架$冲突问题（依赖于引入顺序，后引入的覆盖前面的）
         * 1.释放$ 之后用jquery jQuery.noConflict()
         * 2.用新的变量代替$
         */
        var _ = jQuery.noConflict()
        _(function () {
            alert('xxx')
        })
```

* 核心函数的使用

  ```js
          /**
           * $() jquery的核心函数
           * 1.接受一个function 入口函数
           * 2.接受selector -> jquery object
           * 3.接受代码片段 -> 包含这个dom元素的jquery object
           * 4.接受原生js元素 ->对应的 jquery object
          */
  
          $(function () {
              var span = $('<p>这是一个段落</p>')
              // $('div').get(0).append(span)//[]下标会把一个jquery object ->  dom object append的是一个对象
              $('div.box1').append(span) //直接定位到唯一的对象
  
          })
  ```

  

* jquery对象是个伪数组，有length属性，可迭代

* 