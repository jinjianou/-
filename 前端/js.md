# JS

## html

* 分组框的效果

  ​    <fieldset>

  ​        <legend>用户注册</legend>

  ​    </fieldset>       

* selectElement.selectedIndex 返回被选中的索引

## BOM

* location.reload() 刷新当前页面
* location.assign("/")  跳转到首页
* confirm/ prompt
* setTimeout (function,milliSeconds)  在milliSeconds执行function 只执行一次
* let t=setInterval(function,milliSeconds)  每隔milliSeconds执行一次function
* clearInterval(t) 注意clear是js保留字 不能用作自定义函数名

## DOM

整个文档 是一个节点  document

元素 是节点  var div1=document.getElementId("div1")

元素属性 是节点 div1.attribute[0]

 元素内容 是节点  div1.childNode[0]



* document.getElementById 

* getElementsByTagName 

* getElementsByClassName 

* getElementsByName : ELEMENT[]

* 因为javascript是解释语言，是顺序执行的。 在执行到 document.getElementById的时候，div标签还没有加载，所以无法获取。

   

  ​    <script>

  ​        var  div1 = document.getElementById("d1");

  ​        document.write(div1);

  ​        </script>

   

  <div id="d1">hello HTML DOM</div>

* nodeName表示一个节点的名字

   document.nodeName 文档的节点名，是 固定的#document 

  div1.nodeName 元素的节点名，是对应的标签名 div 

  div1.attributes[0].nodeName 属性的节点名，是对应的属性名 id

  div1.childNodes[0].nodeName 内容的节点名，是固定的 #text 

`<``div` `id``=``"d1"``>hello HTML DOM</``div``>` 

* nodeValue表示一个节点的值 

   document.nodeValue 文档的节点值，是 null 

  div1.nodeValue 元素的节点值，是null

   div1.attributes[0].nodeValue 属性的节点值，是对应的属性值 d1 

  div1.childNodes[0].nodeValue 内容的节点值，是内容 即：hello HTML DOM 

* ![节点类型](https://stepimagewm.how2j.cn/1177.png) nodeType表示一个节点的类型 

*  元素上的属性，比如id,value 可以通过 . 直接访问

  如果是自定义属性，那么可以通过如下两种方式来获取:

  getAttribute("test")

  attributes["test"].nodeValue

  

  注: 

  class需要通过className获取

* 正则表达式

  text.search(regexp) 并返回子串的起始位置  没有返回-1

  text.replace(regexp,new)

  

  test() 方法是一个正则表达式方法。

  test() 方法用于检测一个字符串是否匹配某个模式，如果字符串中含有匹配的文本，则返回 true，否则返回 false。

  regexp.test(text)

* 一个元素节点的style属性即对应的css 

  d.style.display="none" 

  ` ``d1.style.backgroundColor` 

* ​       <tr >

  ​            <th>id</th>

  ​         </tr>

  ​        table{

  border-collapse:collapse;

  }

  tr{

  border-bottom:solid 1px gray;

  }

  会出现底部连续横线的效果

* 事件

  当组件获取焦点的时候，会触发onfocus事件 当组件失去焦点的时候，会触发onblur事件 



​	当在组件上鼠标按下的时候，会触发onmousedown事件

 	当在组件上鼠标弹起的时候，会触发onmouseup事件



   	当在组件上鼠标经过的时候，会触发onmousemove事件

​    	当在组件上鼠标进入的时候，会触发onmouseover事件

​    	当在组件上鼠标退出的时候，会触发onmouseout事件

​    	注: 当鼠标进入一个组件的时候，onmousemove和onmouseover都会被触发，区别在于无论鼠标在组件上如何移动，onmouseover只会触发一次，onmousemove每次移动都会触发 



记得要先用鼠标选中该组件，然后敲击键盘 :

​	当在组件上键盘按下的时候，会触发onkeydown事件 

​	当在组件上键盘按下的时候，也会触发onkeypress事件

​	 当在组件上键盘弹起的时候，会触发onkeyup事件 



​	当在组件上单击的时候，会触发onclick事件 

​	当在组件上双击的时候，会触发ondblclick事件
注2: 自定义函数不要使用click()，这是保留函数名。 



​	当组件的值发生变化的时候，会触发onchange事件 

注：对于输入框而言，只有在失去焦点的时候，才会触发onchange，所以需要点击一下"按钮" 造成输入框失去焦点 

​	可以在form元素上，监听提交事件 当form元素@提交的时候，会触发onsubmit事件 

​	**当整个文档加载成功，或者一个图片加载成功**，会触发加载事件 当body元素或者img@加载的时候，**会触发onload事件** 

​	this表示触发事件的组件，可以在调用函数的时候，作为参数传进去（Event对象）

​	阻止事件发生E:

​	<form method="post" action="/study/login.jsp" onsubmit="return login()">
账号：<input id="name" type="text" name="name"> <br/>
密码：<input type="password" name="password" > <br/>
<input type="submit" value="登录">
</form>

<script>
  function login(){
   var name = document.getElementById("name");
   if(name.value.length==0){
     alert("用户名不能为空");
     return false;
   }
   return true;
    
  }
</script>

​	



* parentNode 

  previousSibling /nextSibling 

  ​	标签之间有任何字符、空白、换行都会产生文本元素。 所以获取到的节点是#text. 

  ​	previousElementSibling 则不然

  childNodes 

  ​	开始/结束标签之间有文本、空格、换行，那么firstChild第一个子节点将会是文本节点，不会是第一个元素节点 

  children 

  ​	children 会排除文本节点

  

* createElement(tag) 创建元素节点  appendChild(ele)

  createTextNode  创建文本节点 

  createAttribute 创建属性节点 

* parentNode.removeChild (childNode) 

  node.removeAttribute(attr)

* parentNode.replaceChild (new,old)

  replaceChild 第一个参数是保留的节点，第二个参数是被替换的节点 

* appendChild追加节点。 追加节点一定是把新的节点插在最后面 

  insertBefore  追加节点一定是把新的节点插在指定元素之前

## JSON

* JSON对象由 名称/值对组成 名称和值之间用冒号:隔开 名称必须用双引号" 包含起来 值可以是任意javascript数据类型，字符串，布尔，数字 ，数组甚至是对象 不同的名称/值对之间用 逗号 , 隔开 

* 通过 点.  或 [键]访问JSON对象的属性 

* JavaScript对象 分内置对象([Number](https://how2j.cn/k/javascript/javascript-number/438.html),[String](https://how2j.cn/k/javascript/javascript-string/439.html),[Array](https://how2j.cn/k/javascript/javascript-array/441.html),[Date](https://how2j.cn/k/javascript/javascript-date/440.html),[Math](https://how2j.cn/k/javascript/javascript-math/520.html))和[自定义对象](https://how2j.cn/k/javascript/javascript-object/442.html) JSON就是自定义对象，只不过是以JSON这样的数据组织方式表达出来 所以不存在JSON对象与JavaScript对象的转换问题 

* 字符串转成JSON对象: 转换的时候注意,eval 函数要以（ 开头，）结尾  如:

  ​    let s='{"a":"1","b":2}';

  ​    let obj=eval("("+s+")");

  ​    console.log(obj);

  ​    console.log(typeof(obj));

* JSON对象转字符串:json 对象因为是一个javascript对象，所以如果直接打印(document.write)的话，看不到里面的内 

   JSON.stringify 

# ajax

* 通过AJAX Asynchronous JavaScript And XML 实现异步刷新 
* ![AJAX 请求和相应图示](https://stepimagewm.how2j.cn/1232.png) 



## 基础

* try{ 

  }catch(err){

  }

* 引入外部文件在标签体内的脚本不会执行

* 放在header的js会导致body延迟加载

* 变量提升：解析器会先解析代码，然后把声明的变量的声明提升到最前 
  方法体内提升至在方法有效(针对var)

* let/const 若未声明就是会用会造成TDZ

  如

  ```
  console.log(x);
  let x=1;
  ```

* 没有任何修饰是全局作用域,容易受到污染

* var没有块级作用域，只有函数作用域,let/const则都有

* let声明后的变量不允许在同一作用域中重新声明（var可以）

* Object.freeze(obj) obj的内容不允许修改

* 未定义(赋值)全是defined 使用为声明的变量会报错. 函数参数为传入或无返回值是为undefined

  

## 运算符

* ==	强制类型转换比较
  ===	不强制类型转换比较

* innerText是元素内的文本节点 多用于非表单节点;value是元素的属性,多用于表单节点

* checked/selected/required相应元素可以直接使用

*  for-of适合数组（取其中的值 iterable通用）
   for-in适合对象（数组取的索引 key）

  

## 基本类型

* number/string/boolean/undefined

* typeof的结果有 number/string/boolean/undefined+Object+function

* instanceof 判断是否为某个对象的实例

* 模板字面量 反引号 `后盾人网址是${url}`

* String(null)会返回字符串"null" 

* string的常用函数:

  substring  start/end是负数就都变成0

  slice start/end负数从后面开始数

  substr 第二个参数为长度 

  lastIndexOf(a) 从左到右数最后一个a的位置 不是从有往左数的索引
  splt(seperator,n) 只保留前n个

  replace(old,new) 只会替换第一个 要想替换所有 replace(new RegExp(old,'g'),new)

* Boolean注意点

  数值当与boolean类型比较时，会转为数字1或0比较。字符串在与Boolean比较时，两边都为转换为数值类型后比较

  如 '10'==true => false

  其他类型   1.使用 !! 转换布尔类型  2.使用 Boolean 函数

* Number:

  Number.isInteger 判断是不是整数

  toFixed(2) 小数点2位 四舍五入

  NaN不能使用 == 比较(NAN跟任何比较都是false)

  Number.parseInt / pasrseFloat 字符串里可以有非数字 会进行截取

  Number()和parseInt()一样，都可以用来进行数字的转换 区别在于，当转换的内容包含非数字的时候，Number() 会返回NaN(Not a Number) 

  toString(n)  n进制

  八进制 let x=012 //10

  十六进制 let y=0xA //10

  科学计数法 let z=3.14e2   //314  314.toExponential (3)  //3.140e+2 

  new Number("123") .valueOf()  //number

  Number.MIN_VALUE/Number.MAX_VALUE 

* Date

  date*1 date.getTime() 会将dat转成时间戳

  Date.now()当前时间

  date.getMonth()+1

  getDay()  一个星期的第几天 0-6

  

## 数组

* 创建数组的方式 

  * new Array
  * 字面量
  * Array.of( ...item )  包含item的数组
  * Array.from 可将类数组转换为数组，类数组指包含 length 属性或可迭代的对象

* 可以 outOfBounds 

* 检测：Array.isArray()

* 数组->字符串  toString/join

  字符串->数组- split

* 解构 
  let[a,b]=[1,2].split(",")

   const [...a] = "houdunren.com"

* 管理元素：

  添加: push(ele)返回length /unshift(ele) 

  删除: pop()返回element/shift()

  slice：不改变原数组
  splice（a,b,c） a是开始删除的位置 b删除的数量 c在a出插入数据

* slice：不改变原数组 是原数组的视图
  splice（a,b,c） a是开始删除的位置,b删除的数量,c在a删除数据后再插入数据

* 清空数组的方式:

  1.arr.length=0 	2.arr=[] 	3.arr.splice(0,arr,length)  4.while(x.pop()){} 

* arr.copyWithin(target, start, end)  复制[start,end) 覆盖 从target这个位置开始的数据

* arr.find(lambda) 返回item或undefined 

  arr.findIndex(lambda) 返回元素在数组中的位置或-1

  includes 包含与否\

  find/finIndex可以用在饮用类型上

* 迭代的方式

  1.fori	2.for-of/for-in	配合entries()/keys()/values()

* x.sort(comparator):number[] x也会变成排序后的

* x.slice()

* x.reverse()

* 重要的函数
  every/some 所有元素都符合某条件/存在符合的

  filter  留下符合的元素形成新数组,不会对原数组产生影响

  map 在数组的所有元素上应用函数，用于映射出新的数组

  reduce 第一个参数是执行函数，第二个参数为初始值

  ```
  array.reduce(function(pre, currentValue, currentIndex, arr), initialValue)
  ```

  