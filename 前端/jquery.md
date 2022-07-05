# jquery

jquery.js本质是一个立即执行函数

## 基础

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

- 语法

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

- 区别:
  - 原生的后面的onload会覆盖前面的,jquery则不会
  - 原生的等文档+图片加载后才触发，jquery等文档加载好就触发（清空缓存+liveServer）
- 冲突问题

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

- 核心函数的使用

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

  

- jquery对象是个伪数组，有length属性，可迭代

  通过get(0)或者[0] 把JQuery对象转为DOM对象 

- 静态方法+实例方法

  ```js
          class Aclass {
              //静态方法 方式1
              // static aStaticMethod() {
              //     alert('aStaticMethod')
              // }
          }
          //静态方法 方式2
          Aclass.bStaticMethod = function () {
              alert('bStaticMethod')
          }
          // Aclass.aStaticMethod()
          Aclass.bStaticMethod()
  
          class BClass { }
          //实例方法
          BClass.prototype.instanceMethod = function () {
              alert('instanceMethod')
          }
  
          let bClass = new BClass
          bClass.instanceMethod()
  ```

## jquery的静态方法

* each $.each(obj,(indexOrKey,value)=>{.....})  obj=数组+伪数组，返回值是遍历的对象（原生只有数组 {value,index} 返回值是undefined)
* map $.map(arr, (val, indexOrKey) => {....})  obj=数组+伪数组（原生只有数组  {value,index,array})
* $.trim(string):string   去除str左右两端的空格
* $.isWindow(any):Boolean判断传进来的是不是window
* $.isArray(any):Boolean 判断传进来的是不是真数组
* $.isFunction(any):Boolean 判断传进来的是不是函数
* $.holdReady(Boolean) 控制ready函数的执行 true暂停 false执行

## 选择器

- \$("tagName")  如 $("div")

- $("#id") 

- $(".className")

- 层级 $("selector1 selector2")  或 $("selector1>selector2") 

- \$(selector:first) 满足选择器条件的第一个元素 $(selector:last) 满足选择器条件的最后一个元素 

- \$(selector:odd) 满足选择器条件的奇数元素 $(selector:even) 满足选择器条件的偶数元素 

  因为是基零的，所以第一排的下标其实是0(是偶数) 

- \$(selector:hidden) 满足选择器条件的不可见的元素 $(selector:visible) 满足选择器条件的可见的元素 注； div:visible 和div :visible(有空格)是不同的意思 div:visible 表示选中可见的div div :visible(有空格) 表示选中div下可见的元素 

- \$(selector[attribute]) 满足选择器条件的有某属性的元素 $(selector[attribute=value]) 满足选择器条件的属性等于value的元素 $(selector[attribute!=value]) 满足选择器条件的属性不等于value的元素 $(selector[attribute^=value]) 满足选择器条件的属性以value开头的元素 $(selector[attribute$=value]) 满足选择器条件的属性以value结尾的元素 $(selector[attribute*=value]) 满足选择器条件的属性包含value的元素 

  $("div\[id][id!='pink']") .toggleClass("border"); 

  注： 一般不要使用[class=className] 而应该使用.className 因为使用$("[class='className']") 如 $("class='a'").toggleClass("anotherClassName")  b会导致class变成className anotherClassName a b,再次 使用 [class=className] 就无法选中了 而.className没有这个问题。 

- :input 会选择所有的输入元素，不仅仅是input标签开始的那些，还包括[textarea](https://how2j.cn/k/html/html-textarea/196.html#step370),[select](https://how2j.cn/k/html/html-select/195.html#step366)和[button](https://how2j.cn/k/html/html-button/206.html) 

  :button 会选择type=button的input元素和button元素 

  :radio 会选择单选框 

  :checkbox会选择复选框

   :text会选择文本框，但是不会选择文本域

   :submit会选择提交按钮

   :image会选择图片型提交按钮 :reset会选择重置按钮 

  

  ​            let value1=$(this).val();

  ​            $("td[rowspan!=13] "+value1).toggle("slow");

- :enabled会选择可用的输入元素 注：输入元素的默认状态都是可用 :disabled会选择不可用的输入元素 :checked会选择被选中的单选框和复选框 注： checked在部分浏览器上(火狐,chrome)也可以选中selected的option :selected会选择被选中的option元素 

### 内容选择器

* :empty 筛选既没有文本也没有子元素的元素 如$("div:empty")
* :parent筛选有文本或子元素的元素 如$("div:parent")
* :contains(text) 筛选包含文本内容的元素 如$("div:contains(texStr')")
* :has(selector) 筛选指定selector的子元素的元素 如$("div:has('span:empty')")

## 筛选器

- first() 第1个元素 last() 最后一个元素 eq(num) 第num个元素 注: num基0 
- parent() 选取最近的一个父元素 parents() 选取所有的祖先元素 (包括父元素)
- children(): 筛选出儿子元素(不包含文本节点) find(selector): 筛选出后代元素 注: find() 必须使用参数 selector 
- siblings(): 同级(同胞)元素 

## 属性操作

* 属性节点 ：DOM元素的属性代表的kv对，存在attributes中 

   获取 element.getAttributes(key) 设置 element.setAttribute(key,value)

* attr(name|properties|key,value|fn)

  只有一个参数为获取属性 两个为设置属性

  获取时只返回获取集合的第一个（不存在为undefined） 设置时则设置所有符合的，若key不存在则新增属性节点kv

  function(index, attrValue)  第一个参数为当前元素的索引值，第二个参数为原先的属性值 

  $('div').attr('class')      

  $('div').eq(0).attr('class','box3')    

* $('div').removeAttr(name)    有多个则用空格分隔

* prop方法和attr方法一致 

  ```js
         console.log($('input').attr('checked')) //checked undefined
          console.log($('input').prop('checked')) //true false
  官方推荐操作属性节点时，具有true和false属性的,如checked selected disabled 使用prop() 其他的用attr()
  ```

* val([val|fn|arr])  无参时获取值 有参数设置可以是任意类型数据，回调函数**function(index, value)**的返回值，或者用于check/select  的数组

* html([val|fn]) 空参获取第一个  有参设置所有 **function(index, html)**

* text([val|fn]) 空参获取所有符合元素包含的文本内容组合起来的文本 有参设置所有  **function(index, text)**

### class操作

* addClass(class|fn) 如果有多个用空格隔开 **function(index, value)**返回一个或多个class

* removeClass(class|fn) 如果有多个用空格隔开 **function(index, value)**返回一个或多个class

* toggleClass(class|fn[,switch]) 存在删除，不存在增加 如果有多个用空格隔开/ **function(index, value,switch)**返回一个或多个class  /switch Boolean

  注意switch为true时传入class时一个整体 也就是除非都存在才删除，否则就会添加缺失的

  false则toggleClass无效

### css操作

* css(name|property) 获取第一个 设置所有 推荐使用property 键值对写法

* width(val|fn) 获取第一个 设置所有

* offset({top,left}) 获取/设置匹配元素相对窗口的偏移

* position() 获取匹配元素相对父元素的偏移

* scrollTop([value]) 获取/设置相对滚动条顶部的偏移 

  注意：在获取网页滚动的偏移时，为了保证兼容性，可以写成

  $('body').scrollTop()+$('html').scrollTop()

  $('body').scrollTop(300)；('html').scrollTop(300)

## 事件操作

* 绑定事件

  * eventName(fn)  部分jquery未实现 如$('div').click(fn)

  * on(events,[selector],[data],fn)  selector为空时事件总是触发 data会传递给event即event.data  

    ```js
          $('#btn').on('click', { 'a': 1 }, function (event) {
                    console.log(event.data);
                })
    ```

  以上两种方式均可以绑定多个相同或不同的事件

* off(events,[selector],[fn] )

  无参 移除匹配对象上所有事件

  events 移除events的事件

  events +fn  移除events+fn事件

  如$('btn').off('click','clcikFn1') click有clcikFn1，clcikFn2两个事件 则只移除clcikFn1

* 事件冒泡：子组件向父类传递该事件的一种行为,如子有click，父有click，则在子触发的同时父也会触发

  默认行为：组件自带的未监听便存在事件 如a的跳转，submit的提交行为

  ```js
              $('.son').on('click', function (event) {
                  alert('son')
                  //阻止冒泡的两种方式
                  // return false;
                  event.stopPropagation();
              })
              $('.father').on('click', function (event) {
                  alert('father')
              })
  
              //阻止默认事件的两种方式
              $('a').on('click', function (event) {
                  // return false
                  event.preventDefault()
              })
  ```

* trigger  自动触发 不会阻止事件冒泡，一般不会阻止默认事件(a除外，如果想实现不阻止默认行为，在a里包层span,自动触发span) 

  例子$("input[type='submit']").trigger('click')  

  triggerHandler  自动触发 会阻止事件冒泡，会阻止默认事件

* 自定义自动触发的事件

  ```js
              $('.son').on('myClick', function () {
                  alert('myClick')
              })
              $('.son').triggerHandler('myClick')
  ```

* 事件命名空间 eventName.namespace

  若自动触发带命名空间的则只会触发子父相同事件+命名空间的

  ​	$('.son').trigger('click.a') 会触发son的click.a和father的click.a

  若自动触发不带命名空间的 则会触发子父所有该事件

  ​	$('.son').trigger('click') 会触发son的click,click.a[,click.b....]和father的click,click.a[,click.b....]

* 事件委托

  将事件委托给其他处理，将结果反馈回来

  **通过核心函数找到的元素不止一个，则添加事件的时候,jq会对所有元素生效**

  ```js
              $('button').click(function () {
                  //向每个匹配的元素内部追加内容 append(content:String, Element, jQuery|fn)
                  $('ul').append('<li>这是新增的li</li>')
              })
  
              //由于通过按钮新增的li在document加载完毕时并不存在，所以该click事件对他们无效
              // $('li').click(function () {
              //     console.log($(this).html());
              // })
  
              //可采用事件委托实现
              //将li的click事件委托给ul，此时的this仍然是li，只是委托给ul
              $('ul').delegate('li', 'click', function () {
                  console.log($(this).html());
              })
  
  
  
  <body>
      <ul>
          <li>这是第1个li</li>
          <li>这是第2个li</li>
          <li>这是第3个li</li>
      </ul>
      <button>添加</button>
  </body>
  ```

  ```js
   $('a').click(function () {
                  let $mask = $("<div class='mask'>\
                      <div class='login'>\
                      <img src='./b.jpg'>\
                          <span></span>\
                      </div>\
                  </div >")
                  $('body').append($mask)
                  $('body').delegate('.login>span', 'click', function () {
                      //remove([expr]) 从dom中删除匹配元素，但会保留jq对象,expr：string进行再一步的筛选
                      $mask.remove()
                  })
                  return false;
              })
  ```

### 节点操作

### 新增

* append 向每个匹配的元素内部追加内容 append(content:String, Element, jQuery|fn)
* prepend 将内容添加在最前面
* appendTo a.appendTo(b) 将a添加到b的尾部
* prependTo a.prependTo (b) 将a添加到b的首部
* after a.after(b)  在a之后插入内容b b是a的next_sibling  a要是已经创建的dom
* before 则跟after相反
* isnertAfter a.isnertAfter(b)  在b之后插入内容a
* isnertBefore  a.isnertBefore(b)  在b前面插入内容a

### 删除

* remove([expr]) 删除选定的元素 expr对选定元素做进一步的筛选

* empty() 清空选定元素的内容和子节点

* detach([expr]) 与remove功能相同 

  与remove区别：remove会保存该jq对象但事件,附加的数据都会被删除,detach则会都保留

### 替换

* replaceWith(content|fn)  a.replaceWith(b) b替换所有a
* replaceAll(selector)  a.replaceAll(b) a替换b

### 复制

* clone([Even[,deepEven]])   Even:Boolean 事件处理函数是否会被复制 false不会 true会

## 效果

* 显示 隐藏 切换 分别通过show(), hide(),toggle()实现 也可以加上毫秒数，表示延时操作,比如show(2000) 

* 向上滑动 向下滑动 滑动切换 分别通过slideUp(), slideDown(),slideToggle()实现 也可以加上毫秒数，表示延时操作，比如slideUp(2000) 

* 淡入 淡出 淡入淡出切换 指定淡入程度 分别通过fadeIn(), fadeOut(),fadeToggle() fadeTo()实现 也可以加上毫秒数，表示延时操作，比如fadeIn(2000) fadeTo跟的参数是0-1之间的小数。 0表示不淡入，1表示全部淡入 

   $("#d2").fadeTo("slow",0.5); 

* 通过animate 可以实现更为丰富的动画效果 

  animate()第一个参数为css样式 animate()第二个参数为延时毫秒 

  注： 默认情况下，html中的元素都是固定，并且无法改变的位置的。 为了使用animate()自定义动画效果，需要通过css把元素的position设置为relative、absolute或者fixed。 

  var div = $("#d");

  ​           $("#b1").click(function(){

  ​            div.animate({left:'100px'},2000);

  ​            div.animate({left:'0px',top:'50px',height:'50px'},2000,function(){

  ​                alert("动画结束")

  ​            });

  ​           });

   div{ 

  position:relative; 

  }

## php5

###简介

- PHP 文件可包含文本、HTML、JavaScript代码和 PHP 代码

- PHP 代码在服务器上执行，结果以纯 HTML 形式返回给浏览器

- PHP 文件的默认文件扩展名是 ".php"

- 弱类型语言

- 安装：

  * web服务器 wampServer
  * php
  * mysql

- 变量

  以 $ 符号开始 

  只能包含字母、数字以及下划线 

  以字母或者下划线字符开始 

  区分大小写 

- PHP 有四种不同的变量作用域：

  - local
  - global
  - static
  - parameter

  函数外定义的变量无法在函数内使用，需要使用 global 关键字 (函数内访问全局变量需要 global 关键字 )

  $x=5; $y=10; function myTest() { global $x,$y; $y=$x+$y; } 

  function myTest() { static $x=0; echo  $ x; $x++; echo PHP_EOL;    // 换行符 } 

  myTest(); //0

  myTest();//1

   myTest(); //2

- echo 和 print 区别:

  - echo - 可以输出一个或多个字符串 **不能输出集合类型** 可用print_r()
  - print - 只允许输出一个字符串，返回值总为 1

- EOF

  PHP 定界符 EOF 的作用就是按照原样，包括换行格式什么的，输出在其内部的东西； 

  在 PHP 定界符 EOF 中的任何特殊字符都不需要转义； 

  ```PHP
  <?php
  $name="变量会被解析";
  $a=<<<EOF
  $name<br><a>html格式会被解析</a><br/>不带引号与带双引号效果一致，解释内嵌的变量和转义符号
  "双引号外所有被排列好的格式都会被保留"
  "但是双引号内会保留转义符的转义效果,比如table:\t和换行：\n下一行"
  EOF;
  echo $a;
  ?>  
  ```

- String（字符串）, Integer（整型）, Float（浮点型）, Boolean（布尔型）, Array（数组）, Object（对象）, NULL（空值） 

  字典定义： array((key=>value)*n)

- 类型比较 == ===

- 常量 bool define ( string $name , mixed $value [, bool $case_insensitive = false ] )

  define("GREETING", "欢迎访问 Runoob.com"); 

- 字符串连接. $txt1 . "  " . $txt2;   相当于+

- if/三目/switch/for/while 与java相同

 





* vscode配置

  1. 安装插件 PHP intellisense for codeigniter 、PHP Intelephense 、PHP Debug  、PHP Xdebug 
  2. 安装php5
  3. 文件->首选项->设置   copy setting id 
  4. 在settings.json里加入 "php.validate.executablePath": "D:/downloads/php5.6.40/php-5.6.40-Win32-VC11-x64/php.exe" 
  5. 打开xdebug 输入

* 使用xamp

  php保存在xamp安装目录 页面在www文件夹中

* \$__GET /\$__POST 可以拿到前台穿过来的name(k)-v字典

* \$FILES 上传文件

  ```php+HTML
      <form action="demo1.php" method="post" enctype="multipart/form-data">
          <label for="file">文件名：</label>
          <input type="file" name="file" id="file"><br>
          <input type="submit" name="submit" value="提交">
      </form>
  
      <?php
      print_r($_FILES); //此时tmp_name并没有文件
  
      $file = $_FILES['file'];
      $file_name = $file['name'];
      $pre_dir = $file['tmp_name'];
      // $pre_dir_file=$pre_dir.$file_name;
  
      echo move_uploaded_file($pre_dir, './source/' . $file_name) 
      ?>
  ```

  大文件上传可以修改 php.ini  C:\wamp64\bin\apache\apache2.4.46\bin

  upload_tmp_dir

  upload_max_filesize

  max_execution_time  单位s

## ajax

* 与服务器进行数据交互并更新部分页面，无需加载整个页面

* 原生js

        window.onload = function (env) {
                    var xmlhttp;
                    //1.创建对象
                    if (window.XMLHttpRequest) {
                        //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                        xmlhttp = new XMLHttpRequest();
                    }
                    else {
                        // IE6, IE5 浏览器执行代码
                        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
        
                    /*2.设置连接
                        method
                        url
                        true 异步 false 同步
                    */
                    //ie浏览器中，通过ajax发送get请求，默认一个url只有一个结果
                    xmlhttp.open("GET", "demo2.php?t="+(new Date().getTime()), true);


    ​	
    	            //3.监听状态变化
    	            xmlhttp.onreadystatechange = function () {
    	                if (xmlhttp.readyState == 4){
    	                	if(xmlhttp.status == 200) {
    	                    	console.log(xmlhttp.responseText);
    	                    }
    	                }
    	            }
    	
    	            //4.发送请求
    	            xmlhttp.send();
    	        }


    ​	        
    	        demo2.php
    	            <?php
    	            	echo '1111'
    	            ?>

* 封装

  ```js
  function obj2Str(obj) {
      let param_list = [];
      for (const key in obj) {
          let param
              = encodeURIComponent(key) + '=' + encodeURIComponent(obj[key]) //将中文转成浏览器能识别的,字母，'='等符号不需要
          param_list.push(param)
      }
      param_list.push('t=' + new Date().getTime())
      return param_list.join('&')
  }
  
  
  function ajax(url, timeout, obj, success, error) {
      let params = obj2Str(obj)
      console.log(params);
      let xmlhttp;
      //1.创建对象
      if (window.XMLHttpRequest) {
          //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
          xmlhttp = new XMLHttpRequest();
      }
      else {
          // IE6, IE5 浏览器执行代码
          xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
      }
  
      /*2.设置连接
          method
          url
          true 异步 false 同步
      */
      //xmlhttp.open("GET", url + '?' + params, true);
       xmlhttp.open("POST", "demo2.php", true);
      xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  	xmlhttp.send("fname=Henry&lname=Ford");
  
      //3.监听状态变化
      xmlhttp.onreadystatechange = function () {
          if (xmlhttp.status == 200) {
              clearInterval(timer);
              if (xmlhttp.readyState == 4) {
                  success(xmlhttp)
              }
          } else {
              error(xmlhttp)
          }
      }
  
      //4.发送请求
      xmlhttp.send();
      //如果设置了超时时间
      if (timeout) {
          timer = setInterval(() => {
              console.log('超时');
              xmlhttp.abort();
              clearInterval(timer);
          }, timeout)
      }
  
  }
  ```

  ```php
   window.onload = function (env) {
              //key=value&key=value
              ajax(
                  'demo2.php',
                  3000, //超时时间
                  {
                      'username': '金建欧',
                      'password': 123456
                  },
                  function success(xhr) {
                      alert(xhr.responseText)
                  },
                  function error(xhr) {
                      alert('请求失败')
                  },
              )
          }
  ```

  ```php
  <?php
  
  //sleep(5)
  echo $_GET['username'];
  echo '\n';
  echo $_GET['password'];
  ```

* jquery的ajax

  ```js
    $.ajax({
                      type: 'GET',
                      url: 'demo3.php',
                      data: "name=tx",
                      success(xhr) {
                          console.log(xhr);
                      },
                      error(xhr) {
                          console.log(xhr.status);
                      }
  
                  })
  ```

* 返回xml

  ```php
  注意php文件没有缩进
  <?php
  header("Content-Type: text/xml; charset=UTF-8");
  echo file_get_contents('info.xml');
  
  
  
  xhr.querySelector('name')
  ```

* 返回json

  ```php+HTML
  <?php
  
  echo file_get_contents('test.json');
  
  
  
                      //在低版本的ie中，没有JSON.parse方法，可以使用json2.js插件
                          console.log(JSON.parse(xhr));
  ```

* window.location.hash=num  url后面会带一个#num

* 最简单:

  $.ajax({

     url: page,

     data:{"name":value},

     success: function(result){

  ​      $("#checkResult").html(result);

     }

  });

* $.get(

  ​            page,

  ​            {"name":value},

  ​            function(result){

  ​              $("#checkResult").html(result);

  ​            }

  );

* $.post(

  ​    page,

  ​    {"name":value},

  ​    function(result){

  ​        $("#checkResult").html(result);

  ​    }

  );

  $.post(

  ​    page,

  ​    {"name":value},

  ​    function(result){

  ​        $("#checkResult").html(result);

  ​    }

  );

* load比起 [$.get](https://how2j.cn/k/jquery/jquery-ajax/474.html#step1000),[$.post](https://how2j.cn/k/jquery/jquery-ajax/474.html#step999) 就更简单了 $("#id").load(page,[data]); id: 用于显示AJAX服务端文本的元素Id page: 服务端页面 data: 提交的数据 

  url,[data,[callback]]  默认使用 GET 方式 - 传递附加参数时自动转换为 POST 方式。 

* serialize()： 格式化form下的输入数据 有的时候form下的输入内容比较多，一个一个的取比较麻烦，就可以使用serialize() 把输入数据格式化成字符串  如test1.html:52 name=a&age=ss&mobile=c



## 数组

* $.each 遍历一个数组 第一个参数是数组 第二个参数是回调函数 i是下标，n是内容 
* $.unique(arr) 去掉重复的元素  注意 ： 执行unique之前，要先[调用sort对数组的内容进行排序](https://how2j.cn/k/javascript/javascript-array/441.html#step1137)。 
* $.inArray (ele,arr)  返回元素在数组中首次的位置 ，如果不存在返回-1 



## 字符串

* $.trim() 去除首尾空白 

* $.parseJSON()    将JSON格式的字符串，转换为JSON对象 



## DOM对象和JQUERY对象的转化

* 通过get(0)或者[0] 把JQuery对象转为DOM对象 
* 通过$() 把DOM对象转为JQuery对象 

