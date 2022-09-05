# checkVue

## what

* **progressive（渐进式） framework**  rather than monolithic (整体) frameworks

* reactive

* MVVM（Model-View-ViewModel）

  ![1](C:\Users\Administrator\Desktop\复习\素材\pic\vue\1.png)

  ![2](C:\Users\Administrator\Desktop\复习\素材\pic\vue\2.png)

  * 用户界面的事件编程方式，GUI前端与后端分离
  * 核心是ViewModel，中转站

* Hello World

  * 新建index文件

  * <script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>

    or

    <script src="https://cdn.jsdelivr.net/npm/vue@2"></script>

  * 新建Vue对象

    ```js
    var data={
        message: 'Hello Vue!'
      }
    
    var app = new Vue({
      el: '#app',
      data: data
    })
    
    <div id="app">
      {{ message }}
    </div>
    
    script要放在dom之后
    {{}} 插值表达式
    外面的指 js语句
    内部的指 解析的是变量 而不是显示文本
    只支持单个js表达式，不支持js语句
    
    data.msg==app.msg --> true
    app.msg='hello'  data.msg->'hello'
    data.msg='hello2'  app.msg->'hello2'
    
    对于新属性，双向绑定失效 因此对于之后可能会用到的属性，可以默认设初值
    
    Object.freeze means the reactivity system can’t track changes
    
    以前缀$的实例属性和方法区分于用户自定义属性
    ```

  * 生命周期及钩子函数（回调函数一种，区别在于出发时机不同）

    1. 初始化阶段  beforeCreate created beforeMount mounted
    2. 运行阶段 beforeUpdate updated
    3. 销毁阶段 beforeDestory destoryed

    beforeCreate 对象还没初始化 data等数据获取不到

    beforeMount 此时template模板还没有渲染

    beforeUpdate data的数据已更新，但页面上还是原始数据

    ![3](C:\Users\Administrator\Desktop\复习\素材\pic\vue\3.png)

    *  **不推荐使用箭头函数**(this 作用域链)
    *  destroyed vue实例销毁之后调用（delete删除对象属性，不直接涉及删除/释放对象）

- beforeCreate
  当前生命周期函数主要用来做初始化工作,在这个生命周期函数中我们可以创建一个loading

   events & lifecycle loaded

- created:(*****)
  创建后:
  1、在当前生命周期函数中我们可以访问到vm身上所有的属性和方法
  2、当前生命周期函数会将data和methods身上所有的属性和方法都挂载在vm的实例身上
  3、当前生命周期函数会将data身上所有的属性添加一个getter/setter方法，因此如果需要进行前后端数据交互的时候必须在当前生命周期中进行(响应式原理),如果数据没有提前在data中进行了绑定那么这个属性身上就不会有getter/setter方法，因此数据也不会动态的进行改变

- beforeMount
  挂载前：
  数据和模板还没有进行相结合，在这个生命周期函数中我们可以进行数据最后的修改.在当前生命周期函数中是访问不到真实的DOM结构

- mounted:(*****)
  挂载后：
  数据和模板已经结合，在这个生命周期函数中我们可以通过this.$refs访问到真实DOM结构 

  $refs
  ref=“值必须是整个VDom中唯一”;

  访问的时候通过this.$refs.值

  ref与document的区别？
  document是从整个页面去查找DOM结构，这个DOM结构肯定是已经插入到页面的DOM结构
  ref是从当前vm的虚拟DOM中找到的当前元素,ref是从内存当中找到的DOM结构  **需要显式声明** 可在标签或组件上声明

- beforeUpdate
  更新前：
  当data中的数据发生了改变的时候就会执行
  1、可以访问到真实的DOM结构
  2、可以对数据做最后的修改
  3、当前生命周期函数中的数据和模板还没有更新完毕

- updated:
  更新后：
  1、数据更新后最新的DOM结构
  2、在当前生命周期函数中需要特别的注意，因为当前函数是一个频繁触发的函数。
  因此如果在当前生命周期函数中做一些事件绑定或者实例化的时候需要做一个提前判断

- beforeDestroy
  销毁前：
  1、在这个生命周期函数中，还是可以继续访问到真实的DOM结构以及data中的数据
  2、一般我们都会在这个生命周期函数中做一些事件解绑/移除的操作

- destroyted
  销毁后：
  1、将DOM与数据之间的关联进行断开
  2、在当前生命周期函数中是访问不到真实的DOM结构

## 版本
如何区分项目是vue3还是vue2
最简单的方法就是在项目中实验一下vue3跟vue2不一样的语法，比如：

1. vue3中的template可以有多个根节点，vue2只能有一个；

2. main.js

```
//vue3
import { createApp } from 'vue'
import App from './App.vue'
import './index.css'

createApp(App).mount('#app')

//vue2
import Vue from 'vue'
import App from './App.vue'

new Vue({
  render: h => h(App),
}).$mount('#app')

```

3. `setup`（取代`data` 和`methods`)

4. 新组件 `Teleport`

   新组件 Teleport
   to代表重新找的父节点，父节点不再是类名为parent的div

   ```
   <div class="parent">
   	<Teleport to="body">
   	       <div class="box" style="position:absolte;z-index:10"></div>
   	 </Teleport>
    </div>
   ```


并且vue-cli的版本和vue的版本没有必然联系


## 通过vue ui创建项目
 vue/cli 3.0之下没有vue ui  等同于 vue create project

1. npm install -g @vue/cli

2. vue -V

   @vue/cli 5.0.8

3. vue ui

   http://localhost:8000/project/select 创建项目

4. 生成项目

   没有config、build等文件夹
   vue.config.js 自定义配置

## 指令

指令的属性值预期是单个js表达式（v-for除外）

2.6.0新增[]表示对js表达式进行动态求值，求得的指作为参数使用

​	<a v-bind:[attributeName]="url"> ... </a>

​	不能出现特殊符号，如空格和引号

* v-once

  执行一次性地插值，当数据改变时，插值处的内容不会更新(model数据改变了,view不变)

  \<span v-once>这个将不会改变：{{msg}}</span>

* v-html 

  直接输出html **不推荐使用,组件更适合作为可重用和可组合的基本单位。**

  ```html
  <p>Using v-html directive: <span v-html="rawHtml"></span></p><p>Using v-html directive: <span v-html="rawHtml"></span></p>
  ```

* **v-bind**

  v-bind:attribute='xxx'  xxx可以是true/false,变量（data中），func()

  缩写  :attribute='xxx' 

* **v-on**

  v-on:event=‘func’

  缩写  @event=‘func’

   **<a href="javascript:;" @click="clearAll">**  **表示走js的处理**

  事件修饰符

  - `.stop` stopPropagation() 阻止冒泡事件

    冒泡 微软提出 从确定到不确定

    捕获 网景提出 从不确定到确定

    先经过捕获再冒泡

    div#div1 @click='log' > div#div2 @click='log'> div#div3 @click='log'

          //target 触发事件的元素（即被点击的元素）
          //currentTarget 绑定事件的元素（与this相等）
           console.log(that.currentTarget.id); 
    点击div#div3 输出3-2-1

    

    div#div1 @click='log' @click.capture='log' > div#div2 @click='log' @click.capture='log' > div#div3 @click='log' @click.capture='log'

    先捕获再冒泡 输出1-2-3-3-2-1

    

    div#div2 @click.stop='log'  输出1-2-3-3-2(包含当前结点)

    div#div2 @click.capture.stop='log' 输出1-2 (连冒泡都没了)

  - `.prevent` preventDefault() 阻止默认事件

  - `.capture`  内部元素触发的事件先在此处理，然后才交由内部元素进行处理

  - `.self` 只当在 event.target 是当前元素自身时触发处理函数

  - `.once`  2.1.4新增 只触发一次

  - `.passive`  2.3.0新增 立刻触发 不用等方法完成

  - [`KeyboardEvent.key`](https://developer.mozilla.org/en-US/docs/Web/API/KeyboardEvent/key/Key_Values) 

    * `.enter`

    * `.tab`

    * `.delete` (捕获“删除”和“退格”键)

    * `.esc`

    * `.space`

    * `.up`

    * `.down`

    * `.left`

    * `.right`

    * `.enter`

    * `.tab`

    * `.delete` (捕获“删除”和“退格”键)

    * `.esc`

    * `.space`

    * `.up`

    * `.down`

      鼠标：

    * `.left`

    * `.right`

  - .exact 精确控制

    v-on:click.exact 没有任何系统修饰符被按下的时候才触发

    v-on:keydown.ctrl.exact 有且只有 Ctrl 被按下的时候才触发
    

* **v-model**

  在表单 `<input>`、`<textarea>` 及 `<select>` 元素上创建双向数据绑定

  * `v-model` 会忽略所有表单元素的 `value`、`checked`、`selected` attribute 的初始值而总是将 Vue 实例的数据作为数据来源

  * 单选框 <input type="radio" v-model='sex' id='male' value='male'/> 男

  * 复选框

    <input type="checkbox" value="A" v-model='checkModel'>A

    <input type="checkbox" value="B" v-model='checkModel'>B

    checkModel:[] 表示选中的项

    checkBox输入value会改变checked属性;同时跟普通input一样,勾选就是输入value.  true->全部勾选  false->都不勾选

  * ​      <select v-model='selectModel' multiple>

    ​        <option disabled value="">请选择</option>

    ​        <option value='A' > A

    ​        <option value='B'> B

    ​      </select>

    or

    <select v-model='selectModel'>

    ​        <option disabled value="">请选择</option>

    ​        <option v-for='option in options' :value='option.value'>{{option.title}}</option>

    ​      </select>

    options:[

    ​        {value:'A',title:'A'},

    ​        {value:'B',title:'B'},

    ​        {value:'C',title:'C'},

    ​      ],

  * ​      <input type="checkbox" 

    ​        true-value="y1" 

    ​        false-value='y2'

    ​        v-model='checkModel'>A

    ​	选中y1  未选中y2

  * .lazy 默认每次input事件触发后数据同步--> change事件后同步,也就是在失去焦点 或者 按下回车键时(且与上次的值不同)才更新

    .number 自动将输入转为数值

    .trim 自动去掉首尾空白符

    <input v-model.lazy="num">

    

* **v-if**

  **直到条件第一次为真，才会渲染条件块**

  适用于运行时条件很少改变事使用

  ```jsx
  <div v-if="type === 'A'">
    A
  </div>
  <div v-else-if="type === 'B'">
    B
  </div>
  <div v-else-if="type === 'C'">
    C
  </div>
  <div v-else>
    Not A/B/C
  </div>
  
  
  v-else-if/v-else元素须紧跟在带 v-if 或者 v-else-if 的元素的后面，否则它将不会被识别
  ```

  **略过:**

  **template标签会保留之前同一组件内容**，如果想这两个元素是完全独立的，不要复用它们，只需添加一个具有唯一值的 `key` attribute 即可

  ```js
  <template v-if="loginType === 'username'">
    <label>Username</label>
    <input placeholder="Enter your username" key="username-input">
  </template>
  <template v-else>
    <label>Email</label>
    <input placeholder="Enter your email address" key="email-input">
  </template>
  ```

  现在，每次切换时，输入框都将被重新渲染。而label并没有key， 所以仍会复用。

* **v-show**

  元素始终总会被渲染（无论条件）会保留在 DOM 中,只是简单切换display

  不支持template

  运行时条件频繁切换时使用

  v-show="true"

* **v-for**

  ```jsx
  <ul id="example-1">
    <li v-for="(item,index) in items" :key="item.message">
      {{ item}}
    </li>
  </ul>
  ```

  在 `v-for` 块中，我们可以访问所有父作用域的 property

  可以用 `of` 替代 `in` 作为分隔符，因为它更接近 JavaScript 迭代器(js in(index,key) of(value) )的语法。同样的可以对 对象 进行遍历

  ```js
  <div v-for="(value, name, index) in object">
    {{ index }}. {{ name }}: {{ value }}
  </div>
  ```

  为了给 Vue 一个提示，以便它能跟踪每个节点的身份(每个结点唯一)，从而重用和重新排序现有元素，你需要为每项提供一个唯一 `key` attribute(基本数据类型和String)

  ```jsx
  div id="app">
          <ul>
              <li v-for="(person,index) in persons">
                  {{person}}
                  <input id='ipt'>
                  <button v-on:click="moveDown(index)">下移</button>
              </li>
          </ul>
      </div>
  
      <script>
          var data={
             persons:[1,2,3,4],
            
          };
          var app=new Vue({
              el: '#app',
              data:data,
              methods:{
                  moveDown(index){
                     const delItem= this.persons.splice(index,1);
                     this.persons.splice(index+1,0,...delItem);
                  }
              } 
          });
      </script>
      
   点击按钮，输入框不随文本一起下移，是因为输入框没有与数据绑定
   加个key就能解决此问题
  ```

  被侦听的数组变更（push pop splice等），视图会触发更新

  接受整数 模板重复对应次数\<div>   \<span v-for="n in 10">{{ n }} </span> </div> 结果是1...10

  同一元素中使用v-for/v-if  v-for优先级高于v-if

  给组件传递参数

  ```jsx
  <my-component
    v-for="(item, index) in items"
    v-bind:item="item"
    v-bind:index="index"
    v-bind:key="item.id"
  ></my-component>
  
  
  Vue.component('todo-item', {
    template: '\
      <li>\
        {{ title }}\
        <button v-on:click="$emit(\'remove\')">Remove</button>\
      </li>\
    ',
    props: ['title']
  })
  ```

  

## 计算属性

对于任何复杂逻辑，你都应当使用**计算属性**。

```js
<div id="app">
        <p>Original Message: {{msg}} </p>
        <p>reversed Message: {{reversedMessage}}</p>

    </div>

    <script>
        var data={
                msg: 'Hello`',
        };
        var app=new Vue({
            el: '#app',
            data:data,
            computed:{
                reversedMessage(){
                    return this.msg.split('').reverse().join('');
                }
            }
        });
```

* 不同的是**计算属性是基于它们的响应式依赖进行缓存的**。只在相关响应式依赖发生改变时它们才会重新求值。这就意味着只要 `message` 还没有发生改变，多次访问 `reversedMessage` 计算属性会立即返回之前的计算结果，而不必再次执行函数。相比之下，每当触发重新渲染时，调用方法(methods)将**总会**再次执行函数.

* **计算属性不适合配合v-on绑定** 属性而非方法

* 当需要在数据变化时执行异步或开销较大的操作时，监听属性 watch

```js
 <div id="watch-example">
        <p>
          Ask a yes/no question:
          <input v-model="question">
        </p>
        <p>{{ answer }}</p>
      </div>
      <div id="watch-example">
  <p>
  <!-- 因为 AJAX 库和通用工具的生态已经相当丰富，Vue 核心代码没有重复 -->
<!-- 提供这些功能以保持精简。这也可以让你自由选择自己更熟悉的工具。 -->
<script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2/dist/vue.js"></script>
<script>
    var watchExampleVM = new Vue({
      el: '#watch-example',
      data: {
        question: '',
        answer: 'I cannot give you an answer until you ask a question!'
      },
      watch: {
        // 如果 `question` 发生改变，这个函数就会运行
        question: function (newQuestion, oldQuestion) {
          this.answer = 'Waiting for you to stop typing...'
          this.debouncedGetAnswer()
        }
      },
      created: function () {
        // `_.debounce` 是一个通过 Lodash 限制操作频率的函数。
        // 在这个例子中，我们希望限制访问 yesno.wtf/api 的频率
        // AJAX 请求直到用户输入完毕才会发出。想要了解更多关于
        // `_.debounce` 函数 (及其近亲 `_.throttle`) 的知识，
        // 请参考：https://lodash.com/docs#debounce
        this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
      },
      methods: {
        getAnswer: function () {
          if (this.question.indexOf('?') === -1) {
            this.answer = 'Questions usually contain a question mark. ;-)'
            return
          }
          this.answer = 'Thinking...'
          var vm = this
          axios.get('https://yesno.wtf/api')
            .then(function (response) {
              vm.answer = _.capitalize(response.data.answer)
            })
            .catch(function (error) {
              vm.answer = 'Error! Could not reach the API. ' + error
            })
        }
      }
    })
    </script>
```



* 计算属性默认只有 getter，不过在需要时你也可以提供一个 setter

```js
reversedMessage:{
                    get(){
                        return Date.now()+this.msg.split('').reverse().join('');
                    },
                    set(newVal){
                        console.log("set reversedMessage");
                        this.msg+=newVal;
                    }  
             }
```

## Class和style

\<div v-bind:class="{ active: isActive,'text-danger': hasError }"></div>

\<div v-bind:class="[{ active: isActive }, {errorClass}]"></div>

​	isActive       true -> active     false-> ''

```jsx
<div v-bind:style="styleObject"></div>

data: {
  styleObject: {
    color: 'red',
    fontSize: '13px'
  }
}

或

<div v-bind:style="[baseStyles, overridingStyles]"></div>
    
    
2.3开始
<div :style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }"></div> 只会渲染数组中最后一个被浏览器支持的值
```

## import/export 模块

1. export default

```jsx
// there is no semi-colon here
export default function() {} 
export default class {}

//示例
class A extends Component{
   ...
}
export default A;

//对应的import示例。
import A from './requireTest'

//default export, 输入 lodash 模块
import _ from 'lodash';

//一条import语句中，同时输入默认方法和其他变量
import _, { each } from 'lodash';

//上述代码对应的export语句
export default function (obj) {
  // ···
}
export function each(obj, iterator, context) {
  // ···
}
export { each as forEach };
```

注意：一个模块仅仅只允许导出一个default对象，实际导出的是一个default命名的变量进行重命名，等价语句如下。所以import后可以是任意变量名称，且不需要{}。

**export default 那么import必须不带{},反之不然** 

**导出后该对象不能再做修改**

2. named

   export命令规定的是对外的接口，必须与模块内部的变量建立一一对应关系。另外，export语句输出的接口，与其对应的值是**动态绑定**关系，即通过该接口，可以取到模块内部实时的值。

   import后面的from指定模块文件的位置，可以是相对路径，也可以是绝对路径，.js路径可以省略.

   ```jsx
   // profile.js
   //第一种export
   export var firstName = 'Michael';
   export function f() {};
   
   //第二种export，优先使用这种写法
   var firstName = 'Michael';
   export {firstName}；
   
   function f() {}
   export {f};
   
   //main.js
   import { firstName, f } from './profile';
   import { firstName as surname } from './profile';
   ```

   必须保证一个模块内导入的变量名唯一



## 组件

Vue推荐 SPA(single page application) 一个应用只有一个vue实例

Vue.componet(name,obj)

* 自定义组件名 字母全小写且必须包含一个连字符

* 由于组件是可复用的Vue实例，所以他们接受和new Vue相同的选项，如`data`、`computed`、`watch`、`methods` 以及生命周期钩子等。el等根实例特有的选项除外。

* The "data" option should be **a function** that returns a **per-instance（每个实例） value** in component definitions

  data(){ 	

  ​	return {}

  }

* 组件也可以有子组件 components

```js
 Vue.component('my-componet',{
            data:function(){
                return{
                    count:1
                }
            },
            template: '<p>count is {{count}}</p>'
        })

1. data必须是个函数
2. template有且只有一个根元素
3. 必须 <my-componet></my-componet> 而不是<my-componet/>否则只重用一次

```

* 可复用性： 每使用一次组件便会创建一个新实例，每个实例都有各自维护的data

* 注册方式
  * 全局注册

    Vue.componet(name,obj)

  * 局部注册

    在new Vue({

    ....

    components:{

    ​	'my-componet':{

    ​		template:...

    ​	}

    }

    })

### 例子

```
comp1.js

export default  {
  template: "<div>{{count}}</div>",
  data: function () {
    return {
      count: 1,
    };
  },
};


index.html
 <script type="module">
      import comp1 from "./comp1.js";
      let vue = new Vue({
        el: "#app",
        data: {},
        components: { comp1 },
      });
    </script>
```

或

```
mycomponet.vue
<template>
    <div class="hello">Hello {{who}}</div>
</template>
<script>
module.exports = {
    data: function() {
        return {
            who: 'world'
        }
    }
}
</script>
<style>
.hello {
    background-color: #ffe;
}
</style>


index.html
<script src="https://unpkg.com/http-vue-loader"></script>
...
 components: {
          'my-component': httpVueLoader('my-component.vue')//加载需要使用的vue文件
        }
```



* 通过props机制（只能将**父组件传递给子组件**数据**单向传递**）向子组件传递数据

  一个组件默认可以拥有任意数量的 prop，任何值都可以传递给任何 prop，在组件实例中访问这个值，就像访问 `data` 中的值一样。

  a. 传递静态数据  在子组件使用标签处 声明静态数据  key=value  子组件定义内部使用props数组接收

  ​	子组件定义内部data不能重复声明

  b.传递动态属性  v-bind:props_attibute=parent_comp_attibute(不能是字面量)

  **注意：不应该在子组件内部改变prop(无法修改成功) 如果一定要修改，可以在data新增  newProp:this.prop**

  ```jsx
    props:['title'],
    template: '<p>title is {{title}}</p>'
  
  
          <my-componet
              v-for='post in posts'
              v-bind:key='post.id'
              v-bind:title='post.title'
          ></my-componet>
           or
         <my-componet
              v-for='post in posts'
              v-bind:key='post.id'
              v-bind:post="post"
          ></my-componet>
  ```

  

* 事件监听 （事件传递）

  @child_method='parent_method'

  this.$emit(child_method)

  **注意：child_method必须是-连接而非驼峰 比如test-child 而非 testChild** 

  ```jsx
  
  <div id="app">
          <div :style="{fontSize: pageSize+'px'}">
              <my-componet
                  v-for='post in posts'
                  v-bind:key='post.id'
                  v-bind:title='post.title'
                  <!-- 父组件注册一个监听器 -->
                  v-on:text-larger='pageSize+=1'
              ></my-componet>
          </div>
         
      </div>
                  
                  
                  
        template: `<div>
                  <p>title is {{title}}</p>
                  <button @click="$emit('text-larger')">enPageLarger</button>
                  </div>
               `          
  ```

  **子组件向父组件传递数据**：

  子组件通过$emit触发一个事件，父级组件就会接收该事件并做出响应。

  $emit第n个（n>=2）参数抛出给父组件通过$**event调用 或 方法入参**

```
<script>
        Vue.component('to-do-li',{
            props:['item','index'],
            template:`
                <li>
                    {{item.title}}
                    <button @click="$emit('remove-item',index)">remove</button>
                </li>
            `
        })
    </script>

    <div id="app">
        <div>
            Add a todo 
            <input v-model='ipt'> 
            <button @click='addTodo'>add ToDo</button>
        </div>
        <ul>
            <to-do-li
                v-for='(toDoItem,index) in toDoList'
                :item='toDoItem'
                :key='toDoItem.id'
                :index='index'
                @remove-item='removeItem'
            ></to-do-li>
        </ul>
    </div>

    <script>
          var data={
            ipt:'xxxx',
            toDoList:[ {
                id: 1,
                title: 'Do the dishes',
            },
            {
                id: 2,
                title: 'Take out the trash',
            },
            {
                id: 3,
                title: 'Mow the lawn'
            }] 
          }
          var app=new Vue({
            el: '#app',
            data:data,
            methods:{
                getMax(){
                    if(this.toDoList.length==0) return 0
                    let max=this.toDoList[0].id;
                    for (const item of this.toDoList) {
                        if(max<item.id) max=item.id;
                    }
                    return max;
                },
                addTodo(){
                    this.toDoList.push({id:this.getMax()+1,title:this.ipt})
                },
                removeItem(index){
                    this.toDoList.splice(index,1)
                }
            }
        })

    </script>
```

* 在组件上使用v-model

  <custom-input v-model="searchText"></custom-input>

   Vue.component('custom-input',{

  ​      props:['searchText'], 

  ​      template:`

  ​        <input :value="searchText" @input="$emit('input',$event.target.value)">

  ​      `

  ​    })

  两个input都是固定语法

* 动态切换 v-bind:is

  :is="A" 表示加载A组件  当值变成B则编程加载B组件

### 插槽 slot

作用 ： 灵活扩展组件 相当于usb接口

```ht
<body>
    <div id="app">
        <login><span slot="aa"> {{username}}</span></login>
    </div>

</body>
<script>
    const login = {
        template: `<div><slot name='bb'></slot><h3>欢迎</h3><slot name='aa'></slot></div>`,
    }

    let data = {
        username: 'jinjianou'

    }
```

默认显示所有slot

<span slot="aa">  表示取消aa的slot显示

## 可复用性&组合

###  混入

一个混入对象可以包含任意组件options。当组件使用混入对象时，所有混入对象的选项将被“混合”进入该组件本身的选项.如果有同名option，data以混入对象为准；钩子函数合并成数组，混入对象优先。

* 全局混入 Vue.mixin 将影响**每一个**之后创建的 Vue 实例
* 自定义 Vue.config.optionMergeStrategies

### 自定义指令

* 注册一个全局自定义指令 `v-focus` Vue.directive('focus',obj)
* 注册一个局部自定义指令 组件中也接受一个 `directives` 的选项directives: {  focus: obj}

### render

```js
Vue.component('my-componet',{
​		props:['title'],
​      render(creatElement){
​        return creatElement('h1',this.title)
​      }
​    })
```

creatElement准确的名字可能是 `createNodeDescription`，因为它所包含的信息会告诉 Vue 页面上需要渲染什么样的节点，包括及其子节点的描述信息。我们把这样的节点描述为“虚拟节点 (virtual node)”,虚拟 DOM”是我们对由 Vue 组件树建立起来的整个 VNode 树的称呼。

param1: {String | Object | Function}一个 HTML 标签名、组件选项对象，或者resolve 了上述任何一种的一个 async 函数。必填项。

param2: 一个与模板中 attribute 对应的数据对象。可选。

param3: {String | Array} 子级虚拟节点 (VNodes)，由 `createElement()` 构建而成，也可以使用字符串来生成“文本虚拟节点”。可选。



## ref

vue2

```
<template>
  <div class="hello">
    <button @click="increment">{{count}}</button>
  </div>
</template>
<script>
module.exports = {
  data: function () {
    return {
      count: 1
    }
  },
	methods:{
		increment(){
			this.count+=1;
		}
	}
}
</script>
```

options Api   data ,methods....都是option

vue3提供一个新的option setup(又被叫做composition api) 

```
import { ref } from 'vue'
module.exports = {
  setup () {
    //响应式 数据变化 视图做相应变化
    let count = ref(0);

    increment = () => {
      count.value =count.value + 1;
    }

    //在模板中使用的数据
    return {
      count,
      increment,
    }
  }
}
```



# axios

1. npm install axios --save(在项目目录下)或下载js

   -g 只安装到全局包仓库

   --save 只安装到本项目

2. import axios from 'axios'

3. ​    axios.get(`http://localhost:9090/demo`)

   ​    .then(response => console.log(response))

   ​    .catch(err=>console.log(err))

   问题：Access to XMLHttpRequest at 'http://localhost:9090/demo' from origin 'http://localhost:8080' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

   跨域问题 后台可以添加@CrossOrigin

4. post/put 第二个参数json对象

推荐创建方式 

​		let instance=**axios.create([config])**  可以无参

​		比如 axios.create({

​			baseURL: 'http://localhost:8088',

​			timeout: 2500

​		})

​		instance.get...



1. 携带token

   instance.defaults.headers.common['Authoriation']=getToken();

2. 封装get方式请求

   ```
   export function get(url, params) {
     return instance({
       method: 'get',
       url,
       params, //get请求时带的参数自动转成k=v
       timeout: 10000,
       headers: {
         'X-Requested-With': 'XMLHttpRequest',
       },
     })
   }
   ```

   

3. 封装post方式请求

   {

   ​	id:1

   }

   ```
   
   export function post(url, data) {
     return instance({
       method: 'post',
       url,
       data:qs.stringify(data), //k=v qs一个数据格式转化库
       timeout: 10000,
       headers: {
         'X-Requested-With': 'XMLHttpRequest',
         'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'
       },
     })
   }
   ```

   {

   ​	id:1,

   ​	arr:[]

   }

   ```
   export function post(url, data) {
     return instance({
       method: 'post',
       url,
       data:qs.stringify(data,{allowDots:true}), //k=v qs一个数据格式转化库
       timeout: 10000,
       headers: {
         'X-Requested-With': 'XMLHttpRequest',
         'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'
       },
     })
   }
   ```

   arrayFormat:

   ​		 indices(默认)   a[0]=b&a[1]=c

   ​		brackets   a[]=b&a[]=c

   ​		repeat或者 indices: false   a=b&a=c
   allowDots:true

   ​	arr\[0][fld]   ->  arr\[0].fld

发送json字符串

​	不需要做任何处理

## 拦截器

```
  instance.interceptors.request.use((config) => {  console.log(config)  return config})instance.interceptors.response.use((response) => {  return response.data})
```

## 全局配置

main.js：

替换vue内部的异步请求组件$http为axios
Vue.prototype.$http=axios  (instance)  
axios.get -> this.$http.get 



route.index.js

Vue.use()的作用是**通过全局方法 Vue.use() 使用插件**,而且它需要在你调用 new Vue() 启动应用之前完成 

**Vue.use(plugin, options) plugin的类型可以是{ object | Function }options是一个可选的对象** 

```
const plugin={
    install(vue,options){....}
}
或者

function(vue,options){....}
```

```
plugin.js
const plugin1={
	install(vue,options){
		console.log("plugin1 第一个参数：",vue);
		console.log("plugin1 第二个参数：",options);
	}
}

export {plugin1}

main.js

Vue.use(plugin1,'插件一')
```





## 前端跨域

一、什么是跨域？

在前端领域中，跨域是指浏览器允许向服务器发送跨域请求，从而克服Ajax只能**同源**使用的限制。

什么是同源策略？

**同源策略**是一种约定，由Netscape公司1995年引入浏览器，它是浏览器最核心也最基本的安全功能，如果缺少了同源策略，浏览器很容易受到XSS、CSFR等攻击。所谓同源是指"协议+域名+端口"三者相同，即便两个不同的域名指向同一个ip地址，也非同源。

同源策略限制以下几种行为：

- Cookie、LocalStorage 和 IndexDB 无法读取
- DOM和JS对象无法获得
- AJAX 请求不能发送



二、常见的跨域场景

![img](../../%E5%A4%8D%E4%B9%A0/spring/assets/v2-27fc250c234aae487e1e80bf3cec7896_1440w.jpg)

比如127.0.0.1 www.haha.com 在前端中配置axios访问www.haha.com：same_port 依旧算跨域



三、9种跨域解决方案

1、JSONP跨域

**jsonp**的原理就是**利用`<script>`标签没有跨域限制**，通过`<script>`标签src属性，发送带有callback参数的GET请求，服务端将接口返回数据拼凑到callback函数中，返回给浏览器，浏览器解析执行，从而前端拿到callback函数返回的数据。
1）原生JS实现：

```
 <script>
    var script = document.createElement('script');
    script.type = 'text/javascript';

    // 传参一个回调函数名给后端，方便后端返回时执行这个在前端定义的回调函数
    script.src = 'http://www.domain2.com:8080/login?user=admin&callback=handleCallback';
    document.head.appendChild(script);

    // 回调执行函数
    function handleCallback(res) {
        alert(JSON.stringify(res));
    }
 </script>
```

2）jquery Ajax实现：

```
$.ajax({
    url: 'http://www.domain2.com:8080/login',
    type: 'get',
    dataType: 'jsonp',  // 请求方式为jsonp
    jsonpCallback: "handleCallback",  // 自定义回调函数名
    data: {}
});
```

3）Vue 实现：

jsonp的缺点：只能发送get一种请求。 

1.

>  npm install vue-jsonp -S



> import { VueJsonp } from 'vue-jsonp'
>
> Vue.use(VueJsonp)
>
> 或者
>
> import { jsonp } from 'vue-jsonp' 

```
this.$jsonp('http://localhost:8088/test/getMsg',{params: 'xxx'}).then(res=>this.msg=res).catch(err=>err);
```

2.

因为 axios 不支持 jsonp，所以我们需要在 axios 上添加 jsonp 方法，另外我们可以通过 promise 来替代回调参数 

```
axios.jsonp = (url,data)=>{
    if(!url)
        throw new Error('url is necessary')
    const callback = 'CALLBACK' + Math.random().toString().substr(9,18)
    const JSONP = document.createElement('script')
          JSONP.setAttribute('type','text/javascript')

    const headEle = document.getElementsByTagName('head')[0]

    let ret = '';
    if(data){
        if(typeof data === 'string')
            ret = '&' + data;
        else if(typeof data === 'object') {
            for(let key in data)
                ret += '&' + key + '=' + encodeURIComponent(data[key]);
        }
        ret += '&_time=' + Date.now();
    }
    JSONP.src = `${url}?callback=${callback}${ret}`;
    return new Promise( (resolve,reject) => {
        window[callback] = r => {
          resolve(r)
          headEle.removeChild(JSONP)
          delete window[callback]
        }
        headEle.appendChild(JSONP)
    })
    
}
```

```
axios.jsonp(url, params)  
     .then(res => console.log(res))        
     .catch(err => console.log(err))
```



问题： Uncaught SyntaxError: Unexpected token '<'

解决： sb 返回的格式跟jsonp不匹配  参照https://blog.csdn.net/feinifi/article/details/116228582





2、跨域资源共享（CORS）

**CORS**是一个W3C标准，全称是"跨域资源共享"（Cross-origin resource sharing）。
它允许浏览器向跨源服务器，发出XMLHttpRequest请求，从而克服了AJAX只能同源使用的限制。
**CORS需要浏览器和服务器同时支持**。目前，所有浏览器都支持该功能，IE浏览器不能低于IE10。
浏览器将CORS跨域请求分为简单请求和非简单请求。
只要同时满足一下两个条件，就属于简单请求
(1)使用下列方法之一：

- head
- get
- post

(2)请求的Heder是

- Accept
- Accept-Language
- Content-Language
- Content-Type: 只限于三个值：application/x-www-form-urlencoded、multipart/form-data、text/plain

不同时满足上面的两个条件，就属于非简单请求。浏览器对这两种的处理，是不一样的。

简单请求

对于简单请求，浏览器直接发出CORS请求。具体来说，就是在头信息之中，增加一个Origin字段。

```
GET /cors HTTP/1.1
Origin: http://api.bob.com
Host: api.alice.com
Accept-Language: en-US
Connection: keep-alive
User-Agent: Mozilla/5.0...
```

上面的头信息中，Origin字段用来说明，本次请求来自哪个源（协议 + 域名 + 端口）。服务器根据这个值，决定是否同意这次请求。

CORS请求设置的响应头字段，都以 Access-Control-开头:

**1）Access-Control-Allow-Origin**：必选
它的值要么是请求时Origin字段的值，要么是一个*，表示接受任意域名的请求。
**2）Access-Control-Allow-Credentials**：可选
它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中。设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。
**3）Access-Control-Expose-Headers**：可选
CORS请求时，XMLHttpRequest对象的getResponseHeader()方法只能拿到6个基本字段：Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma。如果想拿到其他字段，就必须在Access-Control-Expose-Headers里面指定。上面的例子指定，getResponseHeader(‘FooBar’)可以返回FooBar字段的值。

非简单请求

非简单请求是那种对服务器有特殊要求的请求，比如请求方法是PUT或DELETE，或者Content-Type字段的类型是application/json。非简单请求的CORS请求，会在正式通信之前，增加一次HTTP查询请求，称为"预检"请求（preflight）。

预检请求

预检"请求用的请求方法是OPTIONS，表示这个请求是用来询问的。请求头信息里面，关键字段是Origin，表示请求来自哪个源。除了Origin字段，"预检"请求的头信息包括两个特殊字段。

```
OPTIONS /cors HTTP/1.1
Origin: http://api.bob.com
Access-Control-Request-Method: PUT
Access-Control-Request-Headers: X-Custom-Header
Host: api.alice.com
Accept-Language: en-US
Connection: keep-alive
User-Agent: Mozilla/5.0..
```

**1）Access-Control-Request-Method**：必选
用来列出浏览器的CORS请求会用到哪些HTTP方法，上例是PUT。
**2）Access-Control-Request-Headers**：可选
该字段是一个逗号分隔的字符串，指定浏览器CORS请求会额外发送的头信息字段，上例是X-Custom-Header。

预检请求的回应

服务器收到"预检"请求以后，检查了Origin、Access-Control-Request-Method和Access-Control-Request-Headers字段以后，确认允许跨源请求，就可以做出回应。
HTTP回应中，除了关键的是Access-Control-Allow-Origin字段，其他CORS相关字段如下：
**1）Access-Control-Allow-Methods**：必选
它的值是逗号分隔的一个字符串，表明服务器支持的所有跨域请求的方法。注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求。
**2）Access-Control-Allow-Headers**
如果浏览器请求包括Access-Control-Request-Headers字段，则Access-Control-Allow-Headers字段是必需的。它也是一个逗号分隔的字符串，表明服务器支持的所有头信息字段，不限于浏览器在"预检"中请求的字段。
**3）Access-Control-Allow-Credentials**：可选
该字段与简单请求时的含义相同。

**4）Access-Control-Max-Age**：可选
用来指定本次预检请求的有效期，单位为秒。

CORS跨域示例

**1）前端设置**：

- 原生ajax：

```
var xhr = new XMLHttpRequest(); // IE8/9需用window.XDomainRequest兼容

// 前端设置是否带cookie
xhr.withCredentials = true;

xhr.open('post', 'http://www.domain2.com:8080/login', true);
xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
xhr.send('user=admin');

xhr.onreadystatechange = function() {
    if (xhr.readyState == 4 && xhr.status == 200) {
        alert(xhr.responseText);
    }
};
```

- jquery ajax：

```
$.ajax({
    ...
   xhrFields: {
       withCredentials: true    // 前端设置是否带cookie
   },
   crossDomain: true,   // 会让请求头中包含跨域的额外信息，但不会含cookie
    ...
});
```



3、nginx代理跨域

nginx代理跨域，实质和CORS跨域原理一样，通过配置文件设置请求响应头Access-Control-Allow-Origin…等字段。

1）nginx配置解决iconfont跨域

浏览器跨域访问js、css、img等常规静态资源被同源策略许可，但iconfont字体文件(eot|otf|ttf|woff|svg)例外，此时可在nginx的静态资源服务器中加入以下配置。

```
location / {
  add_header Access-Control-Allow-Origin *;
}
```

2）nginx反向代理接口跨域

跨域问题：同源策略仅是针对浏览器的安全策略。服务器端调用HTTP接口只是使用HTTP协议，不需要同源策略，也就不存在跨域问题。

实现思路：**通过Nginx配置一个代理服务器域名（与domain1相同，端口不同）做跳板机**，反向代理访问domain2接口，并且可以顺便修改cookie中domain信息，方便当前域cookie写入，实现跨域访问。
nginx具体配置：

```
#proxy服务器
server {
    listen       81;
    server_name  www.domain1.com;

    location / {
        proxy_pass   http://www.domain2.com:8080;  #反向代理
        proxy_cookie_domain www.domain2.com www.domain1.com; #修改cookie里域名
        index  index.html index.htm;

        # 当用webpack-dev-server等中间件代理接口访问nignx时，此时无浏览器参与，故没有同源限制，下面的跨域配置可不启用
        add_header Access-Control-Allow-Origin http://www.domain1.com;  #当前端只跨域不带cookie时，可为*
        add_header Access-Control-Allow-Credentials true;
    }
}
```





4、nodejs中间件代理跨域

node中间件实现跨域代理，原理大致与nginx相同，都是通过启一个代理服务器，实现数据的转发，也可以通过设置cookieDomainRewrite参数修改响应头中cookie中域名，实现当前域的cookie写入，方便接口登录认证。
**1）非vue框架的跨域**
使用node + express + http-proxy-middleware搭建一个proxy服务器。

- 前端代码：

```
var xhr = new XMLHttpRequest();

// 前端开关：浏览器是否读写cookie
xhr.withCredentials = true;

// 访问http-proxy-middleware代理服务器
xhr.open('get', 'http://www.domain1.com:3000/login?user=admin', true);
xhr.send();
```

- 中间件服务器代码：

```
var express = require('express');
var proxy = require('http-proxy-middleware');
var app = express();

app.use('/', proxy({
    // 代理跨域目标接口
    target: 'http://www.domain2.com:8080',
    changeOrigin: true,

    // 修改响应头信息，实现跨域并允许带cookie
    onProxyRes: function(proxyRes, req, res) {
        res.header('Access-Control-Allow-Origin', 'http://www.domain1.com');
        res.header('Access-Control-Allow-Credentials', 'true');
    },

    // 修改响应信息中的cookie域名
    cookieDomainRewrite: 'www.domain1.com'  // 可以为false，表示不修改
}));

app.listen(3000);
console.log('Proxy server is listen at port 3000...');
```

**2）vue框架的跨域**
node + vue + webpack + webpack-dev-server搭建的项目，跨域请求接口，直接修改webpack.config.js配置。开发环境下，**vue渲染服务和接口代理服务都是webpack-dev-server同一个，所以页面与代理接口之间不再跨域**。
webpack.config.js部分配置：

```
module.exports = {
    entry: {},
    module: {},
    ...
    devServer: {
        historyApiFallback: true,
        proxy: [{
            context: '/login',
            target: 'http://www.domain2.com:8080',  // 代理跨域目标接口
            changeOrigin: true,
            secure: false,  // 当代理某些https服务报错时用
            cookieDomainRewrite: 'www.domain1.com'  // 可以为false，表示不修改
        }],
        noInfo: true
    }
}
```





5、document.domain + iframe跨域

**此方案仅限主域相同，子域不同的跨域应用场景**。实现原理：两个页面都通过js强制设置document.domain为基础主域，就实现了同域。
1）父窗口：([http://www.domain.com/a.html](https://link.zhihu.com/?target=http%3A//www.domain.com/a.html))

```
<iframe id="iframe" src="http://child.domain.com/b.html"></iframe>
<script>
    document.domain = 'domain.com';
    var user = 'admin';
</script>
```

1）子窗口：([http://child.domain.com/a.html](https://link.zhihu.com/?target=http%3A//child.domain.com/a.html))

```
<script>
    document.domain = 'domain.com';
    // 获取父窗口中变量
    console.log('get js data from parent ---> ' + window.parent.user);
</script>
```





6、location.hash + iframe跨域

实现原理： **a欲与b跨域相互通信，通过中间页c来实现**。 三个页面，不同域之间利用iframe的location.hash传值，相同域之间直接js访问来通信。
具体实现：A域：a.html -> B域：b.html -> A域：c.html，a与b不同域只能通过hash值单向通信，b与c也不同域也只能单向通信，但c与a同域，所以c可通过parent.parent访问a页面所有对象。
1）a.html：([http://www.domain1.com/a.html](https://link.zhihu.com/?target=http%3A//www.domain1.com/a.html))

```
<iframe id="iframe" src="http://www.domain2.com/b.html" style="display:none;"></iframe>
<script>
    var iframe = document.getElementById('iframe');

    // 向b.html传hash值
    setTimeout(function() {
        iframe.src = iframe.src + '#user=admin';
    }, 1000);
    
    // 开放给同域c.html的回调方法
    function onCallback(res) {
        alert('data from c.html ---> ' + res);
    }
</script>
```

2）b.html：([http://www.domain2.com/b.html](https://link.zhihu.com/?target=http%3A//www.domain2.com/b.html))

```
<iframe id="iframe" src="http://www.domain1.com/c.html" style="display:none;"></iframe>
<script>
    var iframe = document.getElementById('iframe');

    // 监听a.html传来的hash值，再传给c.html
    window.onhashchange = function () {
        iframe.src = iframe.src + location.hash;
    };
</script>
```

3）c.html：([http://www.domain1.com/c.html](https://link.zhihu.com/?target=http%3A//www.domain1.com/c.html))

```
<script>
    // 监听b.html传来的hash值
    window.onhashchange = function () {
        // 再通过操作同域a.html的js回调，将结果传回
        window.parent.parent.onCallback('hello: ' + location.hash.replace('#user=', ''));
    };
</script>
```





7、window.name + iframe跨域

window.name属性的独特之处：name值在不同的页面（甚至不同域名）加载后依旧存在，并且可以支持非常长的 name 值（2MB）。
1）a.html：([http://www.domain1.com/a.html](https://link.zhihu.com/?target=http%3A//www.domain1.com/a.html))

```
var proxy = function(url, callback) {
    var state = 0;
    var iframe = document.createElement('iframe');

    // 加载跨域页面
    iframe.src = url;

    // onload事件会触发2次，第1次加载跨域页，并留存数据于window.name
    iframe.onload = function() {
        if (state === 1) {
            // 第2次onload(同域proxy页)成功后，读取同域window.name中数据
            callback(iframe.contentWindow.name);
            destoryFrame();

        } else if (state === 0) {
            // 第1次onload(跨域页)成功后，切换到同域代理页面
            iframe.contentWindow.location = 'http://www.domain1.com/proxy.html';
            state = 1;
        }
    };

    document.body.appendChild(iframe);

    // 获取数据以后销毁这个iframe，释放内存；这也保证了安全（不被其他域frame js访问）
    function destoryFrame() {
        iframe.contentWindow.document.write('');
        iframe.contentWindow.close();
        document.body.removeChild(iframe);
    }
};

// 请求跨域b页面数据
proxy('http://www.domain2.com/b.html', function(data){
    alert(data);
});
```

2）proxy.html：([http://www.domain1.com/proxy.html](https://link.zhihu.com/?target=http%3A//www.domain1.com/proxy.html))
中间代理页，与a.html同域，内容为空即可。

3）b.html：([http://www.domain2.com/b.html](https://link.zhihu.com/?target=http%3A//www.domain2.com/b.html))

```
<script>
    window.name = 'This is domain2 data!';
</script>
```

**通过iframe的src属性由外域转向本地域，跨域数据即由iframe的window.name从外域传递到本地域。这个就巧妙地绕过了浏览器的跨域访问限制，但同时它又是安全操作**。



postMessage跨域

postMessage是HTML5 XMLHttpRequest Level 2中的API，且是为数不多可以跨域操作的window属性之一，它可用于解决以下方面的问题：

- 页面和其打开的新窗口的数据传递
- 多窗口之间消息传递
- 页面与嵌套的iframe消息传递
- 上面三个场景的跨域数据传递

用法：postMessage(data,origin)方法接受两个参数：

- **data**： html5规范支持任意基本类型或可复制的对象，但部分浏览器只支持字符串，所以传参时最好用JSON.stringify()序列化。
- **origin**： 协议+主机+端口号，也可以设置为"*"，表示可以传递给任意窗口，如果要指定和当前窗口同源的话设置为"/"。

1）a.html：([http://www.domain1.com/a.html](https://link.zhihu.com/?target=http%3A//www.domain1.com/a.html))

```
<iframe id="iframe" src="http://www.domain2.com/b.html" style="display:none;"></iframe>
<script>       
    var iframe = document.getElementById('iframe');
    iframe.onload = function() {
        var data = {
            name: 'aym'
        };
        // 向domain2传送跨域数据
        iframe.contentWindow.postMessage(JSON.stringify(data), 'http://www.domain2.com');
    };

    // 接受domain2返回数据
    window.addEventListener('message', function(e) {
        alert('data from domain2 ---> ' + e.data);
    }, false);
</script>
```

2）b.html：([http://www.domain2.com/b.html](https://link.zhihu.com/?target=http%3A//www.domain2.com/b.html))

```
<script>
    // 接收domain1的数据
    window.addEventListener('message', function(e) {
        alert('data from domain1 ---> ' + e.data);

        var data = JSON.parse(e.data);
        if (data) {
            data.number = 16;

            // 处理后再发回domain1
            window.parent.postMessage(JSON.stringify(data), 'http://www.domain1.com');
        }
    }, false);
</script>
```





9、WebSocket协议跨域

**WebSocket protocol是HTML5一种新的协议。它实现了浏览器与服务器全双工通信，同时允许跨域通讯，是server push技术的一种很好的实现**。
原生WebSocket API使用起来不太方便，我们使用[http://Socket.io](https://link.zhihu.com/?target=http%3A//Socket.io)，它很好地封装了webSocket接口，提供了更简单、灵活的接口，也对不支持webSocket的浏览器提供了向下兼容。
1）前端代码：

```
<div>user input：<input type="text"></div>
<script src="https://cdn.bootcss.com/socket.io/2.2.0/socket.io.js"></script>
<script>
var socket = io('http://www.domain2.com:8080');

// 连接成功处理
socket.on('connect', function() {
    // 监听服务端消息
    socket.on('message', function(msg) {
        console.log('data from server: ---> ' + msg); 
    });

    // 监听服务端关闭
    socket.on('disconnect', function() { 
        console.log('Server socket has closed.'); 
    });
});

document.getElementsByTagName('input')[0].onblur = function() {
    socket.send(this.value);
};
</script>
```



小结

以上就是9种常见的跨域解决方案，jsonp（只支持get请求，支持老的IE浏览器）适合加载不同域名的js、css，img等静态资源；CORS（支持所有类型的HTTP请求，但浏览器IE10以下不支持）适合做ajax各种跨域请求；Nginx代理跨域和nodejs中间件跨域原理都相似，都是搭建一个服务器，直接在服务器端请求HTTP接口，这适合前后端分离的前端项目调后端接口。document.domain+iframe适合主域名相同，子域名不同的跨域请求。postMessage、websocket都是HTML5新特性，兼容性不是很好，只适用于主流浏览器和IE10+。

vue跨域

```
 proxy:{
      '/test':{
          target: `http://localhost:8088`,//代理地址，这里设置的地址会代替axios中设置的baseURL
          secure: false,
          changeOrigin: true,// 如果接口跨域，需要进行这个参数配置
          ws: true, // proxy websockets
          //pathRewrite方法重写url
          pathRewrite: {
              '^/test': '/test' 
              //pathRewrite: {'^/api': '/'} 重写之后url为 http://192.168.1.16:8085/xxxx
              //pathRewrite: {'^/api': '/api'} 重写之后url为 http://192.168.1.16:8085/api/xxxx
         }
      }
    }
```

**注意： 必须取消axios配置baseURL ，否则proxy失效**





# apipost

后台接口及模拟数据生成  

# vue-router

https://unpkg.com/vue-router@2.0.0/dist/vue-router.js

单页应用 我们需要做的是，将组件 (components) 映射到路由 (routes)，然后告诉 Vue Router 在哪里渲染它们。

![1](C:\Users\Administrator\Desktop\复习\素材\pic\vue\1.jpg)

URL中有#/成为hash路由，路由路径,没有特殊的含义

```jsx
<head>
  	...
    <script src="vue.js"></script>
    <script src="vue-router.js"></script>
</head>

<body>
    <div id="app">
        <h3>============用户========</h3>
        <a href="#/login">用户登录</a>
        <a href="#/user">用户信息</a>
        <router-view></router-view>
    </div>

</body>
<script>

    const login = {
        template: `<div>用户登录</div>`,
    }

    const user = {
        template: `<div>用户信息</div>`,
    }

    // Vue.component('login', login)
    // Vue.component('user', user)

    const notFound = {
        template: `<div>404 NOT FOUND 页面走丢了！！！！！</div>`,
    }

    const router = new VueRouter({
        routes: [
            { path: '/', redirect: '/login' },
            { path: '/user', component: user },
            { path: '/login', component: login },
            { path: '*', component: notFound },
        ]
    })


    let app = new Vue({
        el: '#app',
        data: {},
        router,
    })
</script>
```

其中 <router-view/> **显示当前路由级别下一级的 页面** 

没有name的视图，将default作为其名称,可以有多个视图,通过name确定.

## 路由切换

标签切换

```注意jsx
  <!-- 通过链接的方式切换路由 -->
        <!-- 1.根据请求路径切换显示不同组件 -->
            <a href="#/login">用户登录</a>
            <a href="#/user">用户信息</a>

            <!-- 省略写法 -->
            <router-link to="/user">用户信息</router-link>
            <router-link to="/login">用户登录</router-link>
            <!-- 完整写法 -->
            <router-link :to="{path:'/login'}">用户登录</router-link>
            <router-link :to="{path:'/user'}">用户信息</router-link>
        <!-- 2.根据对象名称切换，只能使用router-link,推荐使用 -->
            <router-link :to="{name:'Login'}">用户登录</router-link>
            <router-link :to="{name:'User'}">用户信息</router-link>


  routes: [
            { path: '/', redirect: '/login' },
            { path: '/user', component: user, name: 'User' },
            { path: '/login', component: login, name: 'Login' },
            { path: '*', component: notFound },
        ]
```
注意： router-link会阻止click事件 所以@click 无效 需要用@click.native **并且跳转会在click之前**

需要通过再方法里push的方式

```
<router-link :to="{}"  @click.native='changeRecord(user)'>修改</router-link></td>
```



在js代码中切换

```jsx
 login() {
                //this.$route 当前路由对象  this.$router 路由管理器对象
                // this.$router.push('/login') //按路径1
                // this.$router.push({ path: "/login" })//按路径2
                //解决方法1
                if (this.$route.name != 'Login') {
                    this.$router.push({ name: "/Login" }) //按命名
                }
        		//解决方法2 
                     const originalPush = VueRouter.prototype.push
                	VueRouter.prototype.push = function push(location) {
                    return originalPush.call(this, location).catch(err=>err)
                	}
            },
                
                
```

## 传递参数的方式

```
<body>
    <div id="app">
        <h3>用户</h3>
        
        <!-- 地址栏传递参数分为两种
            1. queryString this.$route.query.key ?a=1&b=2
            2. restful this.$route.params.key 									#/register/1/jinjianou
            	此时routers path  /register/:id/:name
         -->
       <!-- <a href="#/login?a=1&b=2">用户登录</a>  
         <a href="#/register/1/jinjianou">用户注册</a> -->
        <router-link :to='{name:"Login",query:{a:-1,b:0}}'>用户登录</router-link>
        <router-link :to='{name:"Register",params:{id:1,name:"jinjianou"}}'>用户注册</router-link>

        <router-view/>
    </div>
</body>
<script>
    const login={
        template:`<div>用户登录 {{queryString.a}}-{{queryString.b}}</div>`,
        data(){
            return {
                queryString:this.$route.query
            }
        },
        created(){
            console.log(this.$route.query);
        }

    }
    const register={
        template:`<div>用户注册</div>`,
        created(){
            console.log(this.$route.params);
        }
    }
    const router=new VueRouter({
        routes:[
            {path:'/login',component:login,name:'Login'},
            {path:'/register/:id/:name',component:register,name:'Register'},
        ]
    })
    const app=new Vue({
        el:'#app',
        data:{},
        router,
    })
</script>
```



## 嵌套路由

```
<body>
    <div id="app">
        <h3>用户</h3>
        <router-link :to="{name:'User'}">用户列表</router-link>
        <router-view/>
    </div>

    <template id="user">
        <div>
            <a href="#/user/useradd">添加用户信息</a>
            <table>
                <thead>
                    <tr>
                        <th>id</th>
                        <th>name</th>
                        <th>age</th>
                        <th>salary</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in userList">
                        <td v-for="(val,key) in user">{{val}}</td>
                        <td><a href="">删除</a>&nbsp;&nbsp;<router-link :to="{name:'UserEdit',params:{id:user.id}}">修改</router-link></td>
                    </tr>
                </tbody>  
            </table>
            <router-view/>
        </div>
    </template>
</body>
<script>
    const user={
        template:'#user',
        data(){
            return {
                userList:[
                    {id:5,name:'xiaochen',age:23,salary:2300}
                ]
            }
        }
    }
    //用户添加组件
    const useradd={
        template:`<div><form action="">姓名：<input/><br/>年龄：<input/><br/>
            薪资：<input/><br/> <input type="submit" value="提交"/> </form></div>`
    }
    //用户修改组件
    const userEdit={
        template:`<div><form action="">id: <input v-model="user.id" readonly/><br/>姓名：<input/><br/> 年龄：<input/><br/>
            薪资：<input/><br/> <input type="button" @click="editUser" value="提交"/> </form></div>`,
            data(){
                return {
                    user:{
                        id:this.$route.params.id
                    }
                }
            },
            methods:{
                editUser(){
                    this.user={}
                    this.$router.push({name:'User'})
                }
            }
    }
    const router=new VueRouter({
        routes:[
            {path:'/user',component:user,name:'User',
             children:[
                 //嵌套子路由能用/开头
                 {path:'useradd',component:useradd,name:'UserAdd'},
                 {path:'userEdit/:id',component:userEdit,name:'UserEdit'},
             ]
            },
        ]
    })
    const app=new Vue({
        el:'#app',
        data:{},
        router,
    })
</script>
```

注意点:

1. 同级别(带/)的path切换时会替换,子组件则会出现在父组件内
2. 组件中的data是function 
3. :to="{name:'mod',params:{id:user.id}}" 引用类型传引用
4. this.$route.query 或者 this.$route.params  传引用会改变原数据
5. this.$router.push({name:'User'})



# 脚手架 vue-cli

cli: command line interface

vue cli 仅仅提供几个命令构建脚手架项目

一个运行时依赖（@vue/cli-service），该依赖：

- 可升级
- 基于webpack(前端打包工具)构建，并带有合理的默认配置
- 可以通过项目内的配置文件进行配置
- 可以通过插件进行扩展

流程：

1. 安装node.js(提供npm node package manager)

2. 设置npm本地仓库 

   npm  config set cache "E:\npmRepo" //设置缓存目录

   npm  config set prefix "E:\npmRepo"   //设置全局包目录

   npm config list //查看基本配置

3. 设置镜像

   npm config set registry xxxx

   https://registry.npm.taobao.org

4. npm  install -g @vue/cli   3.x+

   npm  install -g vue-cli   2.x

   -g global else current project

   `@/`这是`webpack`设置的**路径别名**

   webpack.base.conf.js:

   ```
   resolve: {
   // 路径别名
   alias: {
   '@': resolve('src'),
   'vue$': 'vue/dist/vue.esm.js'
   }
   }
   ```

5. 初始化项目

   vue-init webpack hello  （vue-init是bat）

   在当前目录下创建hello项目 采用webpack打包方式

   问题：'webpack-dev-server' 不是内部或外部命令，也不是可运行的程序

   npm install webpack-dev-server@^2.9.1

   `^`指明的版本范围，只要**不**修改 **[major, minor, patch]** 三元组中，**最左侧的第一个非0位**，都是可以的 

   - `^1.2.3`版本包括：>= `1.2.3` 并且 < `2.0.0`
   - `^0.2.3`版本包括：>= `0.2.3` 并且 < `0.3.0`
   - `^0.0.3`版本包括：>= `0.0.3` 并且 < `0.0.4`

    

## 目录

1. build 存放对项目的构建配置（dev,prod环境）

2. config 用来修改dev/prod配置，必须有index.js

   每个目录下的index.js为入口js  ./router/index.js => ./router

3. node_modules 存放当前项目的js依赖

4. src 存放自己开发代码的目录

   - asserts 静态资源
   - components
   - router 配置项目路由规则
   - app.vue 根组件
   - main.js 入口js

5. static 存放静态资源 不过现在的开发习惯已经不再使用这个目录 被src/assets代替

6. babelrc 将es6代码打包->es5

7. index.html spa的入口和唯一页面

8. package.json 管理当前项目中使用的前端依赖 相当与pom.xml  
   npm install根据这儿生成 node_modules

9. package-lock.json 固化当前安装的每个软件包的版本，
   当运行 npm install时，npm 会使用这些确切的版本。
   当运行 npm update 时，package-lock.json 文件中的依赖的版本会被更新

入口 index.html+main.js    App.vue 主组件

vue-cli中一切皆组件

## 代码格式化

1. Vetur（vue tooling）、Prettier(Code formatter using prettier)

   eslint(Integrates ESLint JavaScript into VS Code) 安装插件

   1. ctrl+shift+p > setting.json > prefrerences: open settings(JSON)

   ```json
   {
       "editor.fontSize": 17,//编辑器字体大小
       "[scss]": {
           "editor.defaultFormatter": "michelemelluso.code-beautifier"
       },//scss格式化工具
       "workbench.iconTheme": "vscode-icons",//vscode文件图标主题
       "go.formatTool": "goimports",//golang格式化工具
       "editor.defaultFormatter": "esbenp.prettier-vscode", //编辑器格式化工具
       "[javascript]": {
         "editor.defaultFormatter": "rvest.vs-code-prettier-eslint"
       },//javascript格式化工具
       "[vue]": {
         "editor.defaultFormatter": "octref.vetur"
       },//vue格式化工具
       "editor.insertSpaces": false,
       "workbench.editor.enablePreview": false, //打开文件不覆盖
       "search.followSymlinks": false, //关闭rg.exe进程
       "editor.minimap.enabled": false, //关闭快速预览
       "files.autoSave": "afterDelay", //编辑自动保存
       "editor.lineNumbers": "on", //开启行数提示
       "editor.quickSuggestions": {
         //开启自动显示建议
         "other": true,
         "comments": true,
         "strings": true
       },
       "editor.tabSize": 2, //制表符符号eslint
       "editor.formatOnSave": true, //每次保存自动格式化
       // "eslint.codeActionsOnSave": {
       //     "source.fixAll.eslint": true
       // },
       "prettier.eslintIntegration": true, //让prettier使用eslint的代码格式进行校验
       "prettier.semi": true, //去掉代码结尾的分号
       "prettier.singleQuote": false, //使用单引号替代双引号
       "javascript.format.insertSpaceBeforeFunctionParenthesis": true, //让函数(名)和后面的括号之间加个空格
       "vetur.format.defaultFormatter.html": "js-beautify-html", //格式化.vue中html
       "vetur.format.defaultFormatter.js": "vscode-typescript", //让vue中的js按编辑器自带的ts格式进行格式化
       "vetur.format.defaultFormatterOptions": {
         "js-beautify-html": {
           "wrap_attributes": "force-aligned" //属性强制折行对齐
         },
         "prettier": {
           "semi": false,
           "singleQuote": true
         },
         "vscode-typescript": {
           "semi": false,
           "singleQuote": true
         }
       },
       "eslint.validate": [
         "vue",
         // "javascript",
         "typescript",
         "typescriptreact",
         "html"
       ],
       "editor.codeActionsOnSave": {
         "source.fixAll.eslint": true
       }
   }
   ```

2. 在每次安装Ctrl+S保存代码时，代码的格式会自动格式化

3. 点击右下角的“纯文本”---选择“.vue的配置文件关联”---之后选择“html”（我是前端所以选择了HTML），之后每次进入页面都不需要再次选择 

   问题：You may use special comments to disable some warnings.

   取消ESLint验证规则   webpack.base.conf.js  注释掉      ...(config.dev.useEslint ? [createLintingRule()] : []), 

   **webstorm关闭eslint语法检查**: File-->Setting-->Languages&Frameworks-->Code Quality Tools-->ESLint 把Disable ESlint选项勾选上
   
   

## 前端标准开发

1. 新增views（业务组件），components(公共组件)

2. 组件按需引入 

   components: ()=>import('@/views/Login')

3. 默认路径

   ｛

   ​	path:'/',

   ​	redirect:'/xxx'

   ｝



js有个localstorage对象能永久保存对象(该浏览器)。localstorage.setItem(key,value)

this.$router.go(0) 刷新当前页

## 样式引入

在main.js导入 import 'xxxx.css'





## 自动生成Vue模板

file->preferences->user snippet 输入vue.json

```
{

	// 打印输出

	"Print to console": {

		// 前缀  也就是用户输入的快捷键内容

		"prefix": "vue",

		// 输出内容

		"body": [

			"<!-- $0 -->",

			"<template>",

			"  <div>",

			"",

			"  </div>",

			"</template>",

			"",

			"<script>",

			"export default {",

			"  data () {",

			"    return {",

			"    }",

			"  }",

			"}",

			"</script>",

			"",

			"<style  scoped>",

			"",

			"</style>",

			""

		],

		// 描述

		"description": "Log output to console"

	}

}

```



新建 xxx.vue  输入vue 回车或tab

**注意：要保证文件格式是vue  可以通过 `configure file assocation to .vue`强制指定**



# Vuex

## 概述

### 组件间共享数据的方式

父向子： v-bind绑定属性  props

子向父：v-on 绑定方法  $emit

**兄弟组件**之间：**eventBus**

​	eventBus 事件总线

​	所有组件公用相同的事件中心，可以向该中心注册发送或接收事件

### eventBus 

1.  初始化

   第一种：

   ```
   // event-bus.js
   import Vue from 'vue'
   export const EventBus = new Vue()
   ```

   第二种（全局）：

   ```
   // main.js
   Vue.prototype.$EventBus = new Vue()
   ```

   或

   ```
   //带s覆盖所有属性 不带指的是某个
   //Object.defineProperty(obj, prop, descriptor)
   Object.defineProperty(Vue.prototype,'$EventBus',{
     get(){
       return eventBus;
     }
   });
   ```

   

2. 发送事件
   假设有A,B两个组件，A页面装载按钮上绑定点击事件，发送事件通知B页面

   发送&接收

   ```
   EventBus.$emit(
   	channelL:string,
   	payload1,...
   )
   ```

   ```
   EventBus.$on(
   	channelL:string,
   	callback(payload1,...)
   )
   
   ```

   移除：

   ```
   EventBus.$off(channel, {})
   ```

   

### 由来

![1657418080200](C:\Users\Administrator\Desktop\复习\前端\assets\1657418080200.png)



好处：

1. 便于开发和维护
2. 搞笑实现组件之间的数据共享，提高开发效率
3. 存储在vuex中的数据都是响应式的，能够实时保存数据和页面的同步

## 基本使用

一般来说：

组件私有数据，存在组件data
组件共享，存在vuex



1. 安装vuex依赖
    npm install vuex@3 -g
    npm install vuex@3 --save

  最新4的版本适合vue@3的版本 用3

store.js:
2. 导入vuex包
    import Vue from 'vue'
    import Vuex from 'vuex'
    Vue.use(Vuex)
3. 创建store对象
    export default new Vuex.Store({
    state:{}
    })

main.js
4.挂载store对象到vue实例中
import store from './store'
new Vue({
	el:...
	...
	store
})



cmd>vue ui 可以在线生成可配置的vue-cli（需要配置环境变量）



## 核心概念

### state

提供唯一的公共数据源

访问state数据共有两种方式：

```
	this.$store.state.xx
```

	import {mapState} from 'vuex'
	将当前组件需要的全局数据，映射为当前组件的computed计算属性
	computed:{
		//数组形式 获取state属性名相同的值
		...mapState(['count'])
	
		//对象形式
		...mapState({
			//mode1
			count,
			//mode2
			count:'xxx',
			//mode3
			count(state){
				return xxx;
			}
		})
	}


### mutation

按照原本的想法，直接操作this.$store.state

**vuex规定不允许直接操作$store中的数据** 而是通过mutation直接监控所有数据的变化

```
store.js
mutations:{
	fn_name(state,options){...}
}


xxxx.vue
触发mutations函数的两种方式
1.this.$store.commit('fn_name',options)
2.import {mapMutations} from 'vuex'
methods:{
	...mapMutations(['fn_name'...])
}
```



### chrome Vue调试器

​	vue.js devtools

### Action

**不要在mutations里写异步操作** 如 setTimeOut. 如果要通过异步操作变更操作，必须要通过Action

**Action中还是会通过触发mutaion的方式变更数据**

```
store.js
actions:{
	addNAsync(context,options){
		setTImeout(()=>{
			context.commit('addN',options)
		},1000)
	}
}

xxx.vue两种方式
//触发Action的
1. this.$store.dispatch('addNAsync',options)
2. import {mapActions} from 'vuex'
methods:{
	...mapActions(['addNAsync']),
}
```



### Getter

Getter用于对store中的数据进行加工处理形成新的数据（wrap），但并不会修改原数据

1. store 中的数据发生变化，Getter的数据也会跟着变化（**反之亦然**）

2. 类似计算属性

   

```
Getters:{
	showNum(state){
		return `当前的数量是【${state.count}】`;
	}
}

//使用getters的两种方式
1.this.$store.getters.名称

2.import {mapGetters} from 'vuex'
methods:{
	...mapGetters(['showNum']),
}
```

## namespaced

Vuex由于使用单一状态树，应用的所有状态会集中到一个比较大的对象。当应用变得非常复杂时，store 对象就有可能变得相当臃肿。

   因此，Vuex 允许我们将 store 分割成**模块（module），**每个模块拥有自己的 state、mutation、action、getter、甚至是嵌套子模块。

 默认情况下，模块内部的 action、mutation 和 getter 是注册在**全局命名空间**的

1.   创建模块 

   export default{

   ​	  **\*namespaced：true\*,**

   ​	state:{},

   ​	mutations:{},

   actions:{},

   getters:{}

   }

2. 获取模块概念

   state:

   this.$store.state.modulex.xxxx

   ...mapState({

   ​	count: state=>state.modulex.xxxx

   })

   

   getter:

   this.$store.getters['moduleA/moduleAGetter']

   ...mapGetters('moduleA',['moduleAGetter']),此处的moduleA，不是以前缀的形式出现！！

   

   mutation:

   this.$store.commit('moduleA/moduleAGetter')



​	actions:

​	this.$store.dispatch('moduleA/moduleAGetter')

## 案例

![1657458208156](C:\Users\Administrator\Desktop\复习\前端\assets\1657458208156.png)

TodoList.vue

```
<template>
  <div>
		<el-form :model="todoList.searchForm" inline >
			<el-form-item>
			<el-input v-model="todoList.searchForm.takName" placeholder="请输入任务" ></el-input>
			</el-form-item>
			<el-form-item>
			<el-button type="primary" @click="addTask">添加事项</el-button>
			</el-form-item>
		</el-form>

		<div id="list">
			<li v-for="(task,index) in showTasks()" :key="index">
				<el-checkbox v-model="task.checked" @change="checkedChange(task)">{{task.content}}</el-checkbox>
				<el-button type="text" @click="deleteTask(task)">删除</el-button>
			</li>
		
			<li>
				<span>{{uncompleteTasks.length}}条剩余</span>

				<el-button-group>
					<el-button v-for="btn in todoList.buttons" :type="todoList.activeBtn==btn.id?'primary':''" 
										plain @click="changeBtn(btn)" :key="btn.id">{{btn.label}} </el-button>
				</el-button-group>

				<el-button @click="clearCompleteTasks" type="text">清除已完成</el-button >

			</li>
		</div>

  </div>
</template>

<script>
	import {mapState} from 'vuex'
	import {mapMutations} from 'vuex'
	import {mapGetters} from 'vuex'
export default {
  data(){
		return {

		}
	},
	computed:{
		...mapState({
			completeTasks(state){
			 return state.todoList.completeTasks;
			},
			uncompleteTasks(state){
			 return state.todoList.uncompleteTasks;
			},
			todoList(state){
				return state.todoList;
			}
		})
	},
	methods:{
		...mapMutations(['addTask','changeBtn','clearCompleteTasks','checkedChange','searchTask','deleteTask']),
		...mapGetters(['showTasks'])
	},
	// watch:{
	// 	completeTasks:{
	// 		handler: function(val){
				
	// 	},
	// 	deep:true,
	// 	}
	// },
	// uncompleteTasks:{
	// 		handler: function(val){
				
	// 	},
	// 	deep:true,
	// 	}
	// },
}
</script>

<style>
	div#list{
		margin-top: 10px;
	}
	li {
		Margin-top:-5px;
		border: 3px solid #F2F4F5;
		background-color: white;
		padding: 20px 40px;
		list-style-type: none;
	}
</style>

```

store.js

```
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state:{
		record:{},
		todoList:{
			searchForm:{
				takName:'',
			},
			completeTasks:[{content:'aaa',checked:true}],
			uncompleteTasks:			[{content:'bbb',checked:false},
				{content:'ccc',checked:false}],
			//allTasks+showTasks 通过getters实现关联变更
			// allTasks:[{content:'aaa',checked:true},
			// 					{content:'bbb',checked:false},
			// 					{content:'ccc',checked:false}],
			// showTasks:[{content:'aaa',checked:true},
			// 					{content:'bbb',checked:false},
			// 					{content:'ccc',checked:false}],
			buttons:[
				{id:1,label:'全部'},
				{id:2,label:'未完成'},
				{id:3,label:'已完成'},
			],
			activeBtn:1,

		},
	},
	mutations:{
		changeRecord(state,record){
			state.record=record;
		},
		addTask(state){
			const tl=state.todoList
			let newTask={content:state.todoList.searchForm.takName,checked:false};
			tl.uncompleteTasks.push(newTask);
		},
		changeBtn(state,btn){
			const cur=btn.id;
			if(state.todoList.activeBtn!=cur){
				state.todoList.activeBtn=cur;
			}
			// const tl=state.todoList;
			// switch (prev) {
			// 	case 1:
			// 		[...tl.showTasks]=tl.allTasks;
			// 		break;
			// 		case 2:
			// 			[...tl.showTasks]=tl.uncompleteTasks;
			// 			break;
			// 			case 3:
			// 				[...tl.showTasks]=tl.completeTasks;
			// 				break;
			// 	default:
			// 		break;
			// }
			
		},
		clearCompleteTasks(state){
			state.todoList.completeTasks=[]; //length=0并不会刷新页面数据
		},
	
		checkedChange(state,task){
			Array.prototype.remove=function(arr,task){
				let ind=this.findIndex(element=>(element.content==task.content)&&(element.checked==task.checked));
				if(ind!=-1){
					this.splice(ind,1); //delete arr[ind] length并没有变 只是ind的元素变成empty
					arr.push(task);
					return;
				}
				ind=arr.findIndex(element=>(element.content==task.content)&&(element.checked==task.checked));
				if(ind=-1){
					arr.splice(ind,1); 
					this.push(task);
				}
			}
			//对自定义的remove而言，uncompleteTasks和completeTasks是对称的，
			state.todoList.uncompleteTasks.remove(state.todoList.completeTasks,task);

		},
		searchTask(state){
			const taskName=state.todoList.searchForm.takName;
			//error
			this.getters.showTasks=function(){
				return this.filter(task=>task.content.includes(taskName));  //store.getters.getter 不用调用且read-oinly
			}
		},
		deleteTask(state,task){
			Array.prototype.removeTask=function(arr,task){
				let ind=this.findIndex(element=>(element.content==task.content)&&(element.checked==task.checked));
				if(ind!=-1){
					this.splice(ind,1); //delete arr[ind] length并没有变 只是ind的元素变成empty
					return;
				}
				ind=arr.findIndex(element=>(element.content==task.content)&&(element.checked==task.checked));
				if(ind=-1){
					arr.splice(ind,1); 
				}
			}
			state.todoList.uncompleteTasks.removeTask(state.todoList.completeTasks,task);
		}
	},
	actions:{
		changeRecordAsyn(context,record){
			scontext.commit('changeRecord',record)
		}
	},
	getters:{
		showTasks(state){
			switch (state.todoList.activeBtn) {
				case 1:
					return [...state.todoList.uncompleteTasks,...state.todoList.completeTasks];
				case 2:
					return state.todoList.uncompleteTasks;
					case 3:
						return state.todoList.completeTasks;
			}
		},
		setRemove(state,task){
			Array.prototype.remove=function(arr,task){
				let ind=this.findIndex(element=>(element.content==task.content)&&(element.checked==task.checked));
				if(ind!=-1){
					this.splice(ind,1); //delete arr[ind] length并没有变 只是ind的元素变成empty
					arr.push(task);
					return;
				}
				ind=arr.findIndex(element=>(element.content==task.content)&&(element.checked==task.checked));
				if(ind=-1){
					arr.splice(ind,1); 
					this.push(task);
				}
			}
		},
	}
})
```



尝试优化：

既然已经有checked字段了，就**不用将completeTasks和uncompleteTasks放在两个数组**

```
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
	state:{
		record:{},
		todoList:{
			searchForm:{
				takName:'',
			},

			tasks:[{content:'aaa',checked:true},
								{content:'bbb',checked:false},
								{content:'ccc',checked:false}],

			buttons:[
				{id:1,label:'全部'},
				{id:2,label:'未完成'},
				{id:3,label:'已完成'},
			],
			activeBtn:1,

		},
	},
	mutations:{
		addTask(state){
			let newTask={content:state.todoList.searchForm.takName,checked:false};
			state.todoList.tasks.push(newTask);
		},
		changeBtn(state,btn){
			const cur=btn.id;
			if(state.todoList.activeBtn!=cur){
				state.todoList.activeBtn=cur;
			}
			
		},
		clearCompleteTasks(state){
			state.todoList.tasks=state.todoList.tasks.filter(task=>task.checked!=true);
		},
	
		deleteTask(state,index){
			state.todoList.tasks.splice(index,1);
		}
	},
	actions:{
	},
	getters:{
		showTasks(state){
			switch (state.todoList.activeBtn) {
				case 1:
					return state.todoList.tasks;
				case 2:
					return state.todoList.tasks.filter(task=>task.checked==false);
				case 3:
					return state.todoList.tasks.filter(task=>task.checked==true);
			}
		},
		uncompleteTasksSize(state){
			return state.todoList.tasks.filter(task=>task.checked==false).length;
		}
	}
})
```

```
<template>
  <div>
		<el-form :model="todoList.searchForm" inline >
			<el-form-item>
			<el-input v-model="todoList.searchForm.takName" placeholder="请输入任务" ></el-input>
			</el-form-item>
			<el-form-item>
			<el-button type="primary" @click="addTask">添加事项</el-button>
			</el-form-item>
		</el-form>

		<div id="list">
			<li v-for="(task,index) in showTasks()" :key="index">
				<el-checkbox v-model="task.checked" >{{task.content}}</el-checkbox>
				<el-button type="text" @click="deleteTask(index)">删除</el-button>
			</li>
		
			<li>
				<span>{{uncompleteTasksSize()}}条剩余</span>

				<el-button-group>
					<el-button v-for="btn in todoList.buttons" :type="todoList.activeBtn==btn.id?'primary':''" 
										plain @click="changeBtn(btn)" :key="btn.id">{{btn.label}} </el-button>
				</el-button-group>

				<el-button @click="clearCompleteTasks" type="text">清除已完成</el-button >

			</li>
		</div>

  </div>
</template>
```



#  Vue与后台对接

## 监听路由变化的三种方式

**注意： 当监听对象是一个object时，必须添加deep=true即深度监听，否则对象属性值发生变化（引用未变）并不会监听的到**

	方法一：通过 watch
	// 监听,当路由发生变化的时候执行
	
	watch:{
	
	  $route(to,from){
	
	    console.log(to.path);
	
	  }
	
	},
	
	或者
	
	// 监听,当路由发生变化的时候执行
	
	watch: {
	
	  $route: {
	
	    handler: function(val, oldVal){
	
	      console.log(val);
	
	    },
	
	    // 深度观察监听
	
	    deep: true
	
	  }
	
	},
	
	或者
	
	// 监听,当路由发生变化的时候执行
	
	watch: {
	
	  '$route':'getPath'
	
	},
	
	methods: {
	
	  getPath(){
	
	    console.log(this.$route.path);
	
	  }
	
	}
	
	方法二：：key是用来阻止“复用”的。（推荐，通用性的做法，可以在App.vue的标签下配置）
	
	Vue 为你提供了一种方式来声明“这两个元素是完全独立的——不要复用它们”。只需添加一个具有唯一值的 key 属性即可(Vue文档原话)
	
	使用computed属性和Date()可以保证每一次的key都是不同的，这样就可以如愿刷新数据了。
	
	<router-view :key="key"></router-view>
	
	computed: {
	
	  key() {
	
	    return this.route.name !== undefined? this.route.name +new Date(): this.$route +new Date()
	
	  }
	
	}
	
	方法三：通过 vue-router 的钩子函数 beforeRouteEnter beforeRouteUpdate beforeRouteLeave
	<script>
	
	  export default {
	
	    name: 'app',
	
	    // 监听,当路由发生变化的时候执行
		//next Function: 一定要调用该方法来 resolve 这个钩子。执行效果依赖 next 方法的调用参数
		//next(): 进行管道中的下一个钩子。如果全部钩子执行完了，则导航的状态就是 confirmed （确认的）。
		//next(false): 中断当前的导航。如果浏览器的 URL 改变了（可能是用户手动或者浏览器后退按钮），那么 URL 地址会重置到 from 路由对应的地址。
		//next('/xx') 或者 next({ path: '/xx' }): 跳转到一个不同的地址
		//next(error)航会被终止且该错误会被传递给 router.onError() 注册过的回调
		//一定要确保要调用 next 方法，否则钩子就不会被 resolved。
		//进入 修改 离开
	    beforeRouteEnter (to, from, next) {


​		
​			next(vm=>{...}) //这里的vm就是当前组件的实例 相当于this
​	
​	    },
​	
	    beforeRouteUpdate (to, from, next) {


​	     
​	    },
​	
​	    beforeRouteLeave (to, from, next) {


​	
​	    }
​	
​	</script>




方式三种：

1. 第一次进入A  调用A.beforeRouteEnter
2. A->B 先调用A.beforeRouteLeave在调用B.beforeRouteEnter
3. **父组件对于子组件则没有leave,enter**



## vue cli项目打包和部署

1. npm run build   生成dist生产目录
2. 将dist复制到springboot项目的resources/static目录下
3. 修改index.html中引用的资源文件src/href 为 ${context-path}/dist/...
4. 访问  http://localhost:port/${context-path}/dist/index.html



## 项目过程中的注意点：

1. add之后push了，但不会自动刷新页面，需要监听路由

2. 删除的时候需要刷新页面

   1. location.reload()  或者 this.$router.go(0) 缺点就是相当于ctrl+f5 会出现空白页

   2. provide/inject组合

      ```
      App.vue
      <template>
        <div id="app">
          <router-view v-if="isRouterAlive"/>
        </div>
      </template>
      
      export default{
          provide(){
              return{
                  reload: this.reload
               }
      	},
      	data(){
              return{
                  isRouterAlive:true
              }
      	},
      	methods:{
              reload(){
                  this.isRouterAlive=false
                  this.$nextTick(function(){
                      this.isRouterAlive=true
                  })
              }
      	}
      }
      
      
      xxx.vue
      export default{
          inject:['reload'],
          ...
          this.reload();
      }
      
      ```





# 问题

* live server 无法自动刷新页面

    "liveServer.settings.donotVerifyTags": true,
    "liveServer.settings.CustomBrowser": "chrome",
    "liveServer.settings.AdvanceCustomBrowserCmdLine": "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"

  或者安装live preview

* Cannot use import statement outside a module

  无法在模块外部使用使用import

  **Module 的加载实现的是es6语法**

  script标签加入type="module"属性

* <script type="module" src="./comp1.jsx"></script>

  Access to script at 'file://xxxx'  from origin 'null' has been blocked by CORS policy: Cross origin requests are only supported for protocol schemes: http, data, chrome, chrome-extension, chrome-untrusted, https.

  不支持file协议

  1. 给浏览器传入启动参数（--allow-file-access-from-files）

  2. 使用anywhere（静态文件服务器），可以使用npm 安装

     ```
     npm install anywhere -g 
     ```

     切换到index.html 所在的文件夹，运行 anywhere

     ```
     anywhere
     anywhere -p 8080
     ```

     

  * <script type='text/html' style='display:block'> ......</script>

    不会解析代码 而是直接显示代码文本

  
