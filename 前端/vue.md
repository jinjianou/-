# Vue

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

    v-on:click.ctrl.exact 有且只有 Ctrl 被按下的时候才触发

  用 `v-on:click.prevent.self` 会阻止**所有的点击**，而 `v-on:click.self.prevent` 只会阻止对元素自身的点击。

* **v-model**

  在表单 `<input>`、`<textarea>` 及 `<select>` 元素上创建双向数据绑定

  * `v-model` 会忽略所有表单元素的 `value`、`checked`、`selected` attribute 的初始值而总是将 Vue 实例的数据作为数据来源

  * 单选框 <input type="radio" v-model='sex' id='male' value='male'/> 男

  * 复选框

    <input type="checkbox" value="A" v-model='checkModel'>A

    <input type="checkbox" value="B" v-model='checkModel'>B

    checkModel:[],

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

  * .lazy 默认每次input事件触发后数据同步--> change事件后同步

    .number 自动将输入转为数值

    .trim 自动去掉首尾空白符

    

    

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

* **v-for**

  ```jsx
  <ul id="example-1">
    <li v-for="(item,index) in items" :key="item.message">
      {{ item}}
    </li>
  </ul>
  ```

  在 `v-for` 块中，我们可以访问所有父作用域的 property

  可以用 `of` 替代 `in` 作为分隔符，因为它更接近 JavaScript 迭代器的语法。同样的可以对 对象 进行遍历

  ```js
  <div v-for="(value, name, index) in object">
    {{ index }}. {{ name }}: {{ value }}
  </div>
  ```

  为了给 Vue 一个提示，以便它能跟踪每个节点的身份，从而重用和重新排序现有元素，你需要为每项提供一个唯一 `key` attribute(基本数据类型和String)

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

  给模版传递参数

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

* 通过props机制（只能将**父组件传递给子组件**数据**单向传递**）向子组件传递数据

  一个组件默认可以拥有任意数量的 prop，任何值都可以传递给任何 prop，在组件实例中访问这个值，就像访问 `data` 中的值一样。

  a. 传递静态数据  在子组件使用标签处 声明静态数据  key=value  子组件定义内部使用props数组接收

  ​	子组件定义内部data不能重复声明

  b.传递动态属性  v-bind:props_attibute=parent_comp_attibute

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

## 传递参数的方式

```
<body>
    <div id="app">
        <h3>用户</h3>
        
        <!-- 地址栏传递参数分为两种
            1. queryString this.$route.query.key
            2. restful this.$route.params.key
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



# axios

1. npm install axios --save(在项目目录下)或下载js

2. import axios from 'axios'

3. ​    axios.get(`http://localhost:9090/demo`)

   ​    .then(response => console.log(response))

   ​    .catch(err=>console.log(err))

   问题：Access to XMLHttpRequest at 'http://localhost:9090/demo' from origin 'http://localhost:8080' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.

   跨域问题 可以添加@CrossOrigin

4. post/put 第二个参数json对象

推荐创建方式 

​		let instance=**axios.create([config])**

​		instance.get...

## 拦截器

```
  instance.interceptors.request.use((config) => {  console.log(config)  return config})instance.interceptors.response.use((response) => {  return response.data})
```



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

## 路由切换

标签切换

```jsx
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

   resolve: {
   // 路径别名
   alias: {
   '@': resolve('src'),
   'vue$': 'vue/dist/vue.esm.js'
   }
   }

5. 配置环境变量，初始化项目

   vue-init webpack hello

   在当前目录下创建hello项目 采用webpack打包方式

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





