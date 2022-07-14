# 引言

​	官网:  https://element.eleme.io/#/zh-CN

## 定义

 网站快速成型工具   &   桌面端组件库

 基于vue的一个ui框架

## 安装

1.  npm i  element-ui -g

   vue@2: npm i  element-ui -S [--force] 

   vue@3: npm i  element-plus -S [--force] 

   注意: vue -V看的是vue-cli的版本  npm list vue 看vue的版本(需要进入项目目录)

2. 引入

   ```
   vue-cli@3
   vue add element
   
   
   main.js
   import Vue from 'vue';
   import ElementUI from 'element-ui';
   import 'element-ui/lib/theme-chalk/index.css';
   import App from './App.vue';
   
   Vue.use(ElementUI);
   
   new Vue({
     el: '#app',
     render: h => h(App)
   });
   ```

   



### 问题

1. npm ERR! path E:\vProject\elementui\node_modules\node-sass
   使用 vue-cli安装node-sass 默认请求外网，导致下载失败
   卸载node-sass npm uninstall node-sass
   1. npm uninstall node-sass --force
   2. npm -> cnpm
       cnpm i  element-ui -S

2. eslint error

   ```
   找到vue.config.js,如果没有你可以自己新建一个，新增配置
   lintOnSave: false,
   ```

   

# 组件

## button

type(决定按钮颜色): info primary warning success danger

​	type="text" 文本按钮 没有边框和背景色

plain(无背景颜色,mouseover有)

round(圆角)

icon(含图标按钮)

disabled 禁用

按钮组:

```
      <el-button-group>
        <el-button type="warning">警告按钮</el-button>
        <el-button type="danger">危险按钮</el-button>
      </el-button-group>
```

加载中:

```
:loading="true"
```



尺寸:

size="xx"  large/medium/small/mini



是否默认聚焦

autofocus: boolean  default false



 原生 type 属性

native-type: string="button / submit / reset"



## Link 文字链接

| 参数      | 说明           | 类型    | 可选值                                      | 默认值  |
| :-------- | :------------- | :------ | :------------------------------------------ | :------ |
| type      | 类型           | string  | primary / success / warning / danger / info | default |
| underline | 是否下划线     | boolean | —                                           | true    |
| disabled  | 是否禁用状态   | boolean | —                                           | false   |
| href      | 原生 href 属性 | string  | —                                           | -       |
| icon      | 图标类名       | string  | —                                           | -       |

非原生属性  如underline属性需要通过 v-bind绑定

原生则不需要 如href

string类型不需要  Number/boolean/object需要

## Layout (栅格)布局

通过基础的 24(更多的分配方案) 分栏(超过的部分换row展示)，迅速简便地创建布局

###  Row Attributes

| 参数    | 说明                                  | 类型   | 可选值                                                       | 默认值 |
| :------ | :------------------------------------ | :----- | :----------------------------------------------------------- | :----- |
| gutter  | 栅格间隔(行内)                        | number | —                                                            | 0      |
| type    | 布局模式，可选 flex，现代浏览器下有效 | string | —                                                            | —      |
| justify | flex 布局下的水平排列方式             | string | start/end/center/space-around(两端对齐)/space-between(每个元素左右间距相同) | start  |
| align   | flex 布局下的垂直排列方式             | string | top/middle/bottom                                            | —      |
| tag     | 自定义元素标签                        | string | *                                                            | div    |

### Col Attributes

| 参数   | 说明                                   | 类型                                        | 可选值 | 默认值 |
| :----- | :------------------------------------- | :------------------------------------------ | :----- | :----- |
| span   | 栅格占据的列数                         | number                                      | —      | 24     |
| offset | 栅格左侧的间隔格数                     | number                                      | —      | 0      |
| push   | 栅格向右移动格数                       | number                                      | —      | 0      |
| pull   | 栅格向左移动格数                       | number                                      | —      | 0      |
| xs     | `<768px` 响应式栅格数或者栅格属性对象  | number/object (例如： {span: 4, offset: 4}) | —      | —      |
| sm     | `≥768px` 响应式栅格数或者栅格属性对象  | number/object (例如： {span: 4, offset: 4}) | —      | —      |
| md     | `≥992px` 响应式栅格数或者栅格属性对象  | number/object (例如： {span: 4, offset: 4}) | —      | —      |
| lg     | `≥1200px` 响应式栅格数或者栅格属性对象 | number/object (例如： {span: 4, offset: 4}) | —      | —      |
| xl     | `≥1920px` 响应式栅格数或者栅格属性对象 | number/object (例如： {span: 4, offset: 4}) | —      | —      |
| tag    | 自定义元素标签                         | string                                      | *      | div    |

在main.js 导入import 'element-ui/lib/theme-chalk/display.css';

提供了一系列隐藏类

- `hidden-xs-only` - 当视口在 `xs` 尺寸时隐藏
- `hidden-sm-only` - 当视口在 `sm` 尺寸时隐藏
- `hidden-sm-and-down` - 当视口在 `sm` 及以下尺寸时隐藏
- `hidden-sm-and-up` - 当视口在 `sm` 及以上尺寸时隐藏
- `hidden-md-only` - 当视口在 `md` 尺寸时隐藏
- `hidden-md-and-down` - 当视口在 `md` 及以下尺寸时隐藏
- `hidden-md-and-up` - 当视口在 `md` 及以上尺寸时隐藏
- `hidden-lg-only` - 当视口在 `lg` 尺寸时隐藏
- `hidden-lg-and-down` - 当视口在 `lg` 及以下尺寸时隐藏
- `hidden-lg-and-up` - 当视口在 `lg` 及以上尺寸时隐藏
- `hidden-xl-only` - 当视口在 `xl` 尺寸时隐藏



## container

用于布局的容器组件，方便快速搭建页面的基本结构：

`<el-container>`：外层容器。当子元素中包含 `<el-header>` 或 `<el-footer>` 时，全部子元素会垂直上下排列，否则会水平左右排列。

`<el-header>`：顶栏容器。

`<el-aside>`：侧边栏容器。

`<el-main>`：主要区域容器。

`<el-footer>`：底栏容器。

### Container Attributes

| 参数      | 说明             | 类型   | 可选值                | 默认值                                                       |
| :-------- | :--------------- | :----- | :-------------------- | :----------------------------------------------------------- |
| direction | 子元素的排列方向 | string | horizontal / vertical | 子元素中有 `el-header` 或 `el-footer` 时为 vertical，否则为 horizontal |

### [¶](https://element.eleme.io/#/zh-CN/component/container#header-attributes)Header Attributes

| 参数   | 说明     | 类型   | 可选值 | 默认值 |
| :----- | :------- | :----- | :----- | :----- |
| height | 顶栏高度 | string | —      | 60px   |

### [¶](https://element.eleme.io/#/zh-CN/component/container#aside-attributes)Aside Attributes

| 参数  | 说明       | 类型   | 可选值 | 默认值 |
| :---- | :--------- | :----- | :----- | :----- |
| width | 侧边栏宽度 | string | —      | 300px  |

### [¶](https://element.eleme.io/#/zh-CN/component/container#footer-attributes)Footer Attributes

| 参数   | 说明     | 类型   | 可选值 | 默认值 |
| :----- | :------- | :----- | :----- | :----- |
| height | 底栏高度 | string | —      | 60px   |



## Radio

### Radio Attributes

| 参数            | 说明                                 | 类型                      | 可选值                | 默认值 |
| :-------------- | :----------------------------------- | :------------------------ | :-------------------- | :----- |
| value / v-model | 绑定值                               | string / number / boolean | —                     | —      |
| label           | Radio 的 value                       | string / number / boolean | —                     | —      |
| disabled        | 是否禁用                             | boolean                   | —                     | false  |
| border          | 是否显示边框                         | boolean                   | —                     | false  |
| size            | Radio 的尺寸，仅在 border 为真时有效 | string                    | medium / small / mini | —      |
| name            | 原生 name 属性                       | string                    | —                     | —      |

### [¶](https://element.eleme.io/#/zh-CN/component/radio#radio-events)Radio Events

| 事件名称 | 说明                   | 回调参数              |
| :------- | :--------------------- | :-------------------- |
| change   | 绑定值变化时触发的事件 | 选中的 Radio label 值 |

### [¶](https://element.eleme.io/#/zh-CN/component/radio#radio-group-attributes)Radio-group Attributes

| 参数            | 说明                                                       | 类型                      | 可选值                | 默认值  |
| :-------------- | :--------------------------------------------------------- | :------------------------ | :-------------------- | :------ |
| value / v-model | 绑定值                                                     | string / number / boolean | —                     | —       |
| size            | 单选框组尺寸，仅对按钮形式的 Radio 或带有边框的 Radio 有效 | string                    | medium / small / mini | —       |
| disabled        | 是否禁用                                                   | boolean                   | —                     | false   |
| text-color      | 按钮形式的 Radio 激活时的文本颜色                          | string                    | —                     | #ffffff |
| fill            | 按钮形式的 Radio 激活时的填充色和边框色                    | string                    | —                     | #409EFF |

### [¶](https://element.eleme.io/#/zh-CN/component/radio#radio-group-events)Radio-group Events

| 事件名称 | 说明                   | 回调参数              |
| :------- | :--------------------- | :-------------------- |
| change   | 绑定值变化时触发的事件 | 选中的 Radio label 值 |

### [¶](https://element.eleme.io/#/zh-CN/component/radio#radio-button-attributes)Radio-button Attributes

| 参数     | 说明              | 类型            | 可选值            | 默认值 |
| :------- | :---------------- | :-------------- | :---------------- | :----- |
| label    | Radio 的 value    | string / number | —                 | —      |
| disabled | 是否禁用          | boolean         | —                 | false  |
| name     | 原生 name 属性    | string          | —                 | —      |
| size     | radio-button 尺寸 | string          | medium/small/mini |        |





## checkbox

### Checkbox Attributes

| 参数            | 说明                                                         | 类型                      | 可选值                | 默认值 |
| :-------------- | :----------------------------------------------------------- | :------------------------ | :-------------------- | :----- |
| value / v-model | 绑定值                                                       | string / number / boolean | —                     | —      |
| label           | 选中状态的**值**（只有在`checkbox-group`或者绑定对象类型为`array`时有效） | string / number / boolean | —                     | —      |
| true-label      | 选中时的值                                                   | string / number           | —                     | —      |
| false-label     | 没有选中时的值                                               | string / number           | —                     | —      |
| disabled        | 是否禁用                                                     | boolean                   | —                     | false  |
| border          | 是否显示边框                                                 | boolean                   | —                     | false  |
| size            | Checkbox 的尺寸，仅在 border 为真时有效                      | string                    | medium / small / mini | —      |
| name            | 原生 name 属性                                               | string                    | —                     | —      |
| checked         | 当前是否勾选                                                 | boolean                   | —                     | false  |
| indeterminate   | 设置 indeterminate 状态，只负责样式控制                      | boolean                   | —                     | false  |

### [¶](https://element.eleme.io/#/zh-CN/component/checkbox#checkbox-events)Checkbox Events

| 事件名称 | 说明                     | 回调参数   |
| :------- | :----------------------- | :--------- |
| change   | 当绑定值变化时触发的事件 | 更新后的值 |

### [¶](https://element.eleme.io/#/zh-CN/component/checkbox#checkbox-group-attributes)Checkbox-group Attributes

| 参数            | 说明                                                         | 类型    | 可选值                | 默认值  |
| :-------------- | :----------------------------------------------------------- | :------ | :-------------------- | :------ |
| value / v-model | 绑定值                                                       | array   | —                     | —       |
| size            | 多选框组尺寸，仅对按钮形式的 Checkbox 或带有边框的 Checkbox 有效 | string  | medium / small / mini | —       |
| disabled        | 是否禁用                                                     | boolean | —                     | false   |
| min             | 可被勾选的 checkbox 的最小数量                               | number  | —                     | —       |
| max             | 可被勾选的 checkbox 的最大数量                               | number  | —                     | —       |
| text-color      | 按钮形式的 Checkbox 激活时的文本颜色                         | string  | —                     | #ffffff |
| fill            | 按钮形式的 Checkbox 激活时的填充色和边框色                   | string  | —                     | #409EFF |

### [¶](https://element.eleme.io/#/zh-CN/component/checkbox#checkbox-group-events)Checkbox-group Events

| 事件名称 | 说明                     | 回调参数   |
| :------- | :----------------------- | :--------- |
| change   | 当绑定值变化时触发的事件 | 更新后的值 |

### [¶](https://element.eleme.io/#/zh-CN/component/checkbox#checkbox-button-attributes)Checkbox-button Attributes

| 参数        | 说明                                                         | 类型                      | 可选值 | 默认值 |
| :---------- | :----------------------------------------------------------- | :------------------------ | :----- | :----- |
| label       | 选中状态的值（只有在`checkbox-group`或者绑定对象类型为`array`时有效） | string / number / boolean | —      | —      |
| true-label  | 选中时的值                                                   | string / number           | —      | —      |
| false-label | 没有选中时的值                                               | string / number           | —      | —      |
| disabled    | 是否禁用                                                     | boolean                   | —      | false  |
| name        | 原生 name 属性                                               | string                    | —      | —      |
| checked     | 当前是否勾选                                                 | boolean                   | —      | false  |

两种方式:

1. 为每个checkbox v-model绑定属性 true/false
2. 给一组checkbox v-model绑定属性 array



:indeterminate="isIndeterminate" 用以表示 checkbox 的不确定状态，一般用于实现全选的效果

```\
<template>
  <div class="about">
    <el-checkbox v-model="selectAll"
                 :indeterminate="isIndeterminate"
                 @change="selectAllChange">全选</el-checkbox>
    <el-checkbox-group v-model="selectedCities"
                       @change="checkboxChange">
      <el-checkbox v-for="(city, index) in cities"
                   :label="city"
                   :key="index"></el-checkbox>
    </el-checkbox-group>
  </div>
</template>

<script>
export default {
  data () {
    return {
      radio: 2,
      checkbox: [1],
      cities: ["上海", "北京", "广州", "深圳"],
      selectedCities: [],
      selectAll: false,
      isIndeterminate: false,
    };
  },
  methods: {
    checkboxChange (newVal) {
      if (newVal.length == this.cities.length) {
        this.selectAll = true;
        this.isIndeterminate = false;
      } else {
        this.isIndeterminate = true;
      }
    },
    selectAllChange (newVal) {
      if (newVal) this.selectedCities = this.cities;
      else this.selectedCities = [];
      this.isIndeterminate = false;
    },
  },
};
```





## Input

slot使用方式

```
  <el-input
    placeholder="请选择日期"
    v-model="input3">
    <i slot="suffix" class="el-input__icon el-icon-date"></i>
  </el-input>
```

或者前置文本

```
<div slot="prepend">Http://</div>
```



输入建议 el-autocomplete

`fetch-suggestions` 是一个返回输入建议的方法属性

```
<template>
  <div class="about">
    <el-autocomplete v-model="input"
                     :fetch-suggestions="fetchSuggestion"
                     placeholder="请输入内容"
                     //是否在focus的时候显示建议列表
                     :trigger-on-focus="false">
    </el-autocomplete>
  </div>
</template>

<script>
export default {
  data () {
    return {
      input: "",
      suggestions: [],
    };
  },
  methods: {
    fetchSuggestion (queryString, callback) {
      let res = queryString
        ? this.suggestions.filter(this.createFilter(queryString))
        : this.suggestions;
      callback(res);
    },
    createFilter (queryString) {
      return (suggestion) => suggestion.value.indexOf(queryString) == 0;
    },
    getAll () {
      return [
        { value: "三全鲜食（北新泾店）", address: "长宁区新渔路144号" },
        {
          value: "Hot honey 首尔炸鸡（仙霞路）",
          address: "上海市长宁区淞虹路661号",
        },
        {
          value: "新旺角茶餐厅",
          address: "上海市普陀区真北路988号创邑金沙谷6号楼113",
        },
        { value: "泷千家(天山西路店)", address: "天山西路438号" },
        {
          value: "胖仙女纸杯蛋糕（上海凌空店）",
          address: "上海市长宁区金钟路968号1幢18号楼一层商铺18-101",
        },
        { value: "贡茶", address: "上海市长宁区金钟路633号" },
        {
          value: "豪大大香鸡排超级奶爸",
          address: "上海市嘉定区曹安公路曹安路1685号",
        },
        {
          value: "茶芝兰（奶茶，手抓饼）",
          address: "上海市普陀区同普路1435号",
        },
        { value: "十二泷町", address: "上海市北翟路1444弄81号B幢-107" },
        { value: "星移浓缩咖啡", address: "上海市嘉定区新郁路817号" },
        { value: "阿姨奶茶/豪大大", address: "嘉定区曹安路1611号" },
        { value: "新麦甜四季甜品炸鸡", address: "嘉定区曹安公路2383弄55号" },
        {
          value: "Monica摩托主题咖啡店",
          address: "嘉定区江桥镇曹安公路2409号1F，2383弄62号1F",
        },
        {
          value: "浮生若茶（凌空soho店）",
          address: "上海长宁区金钟路968号9号楼地下一层",
        },
        { value: "NONO JUICE  鲜榨果汁", address: "上海市长宁区天山西路119号" },
        { value: "CoCo都可(北新泾店）", address: "上海市长宁区仙霞西路" },
        {
          value: "快乐柠檬（神州智慧店）",
          address: "上海市长宁区天山西路567号1层R117号店铺",
        },
        {
          value: "Merci Paul cafe",
          address: "上海市普陀区光复西路丹巴路28弄6号楼819",
        },
        {
          value: "猫山王（西郊百联店）",
          address: "上海市长宁区仙霞西路88号第一层G05-F01-1-306",
        },
        { value: "枪会山", address: "上海市普陀区棕榈路" },
        { value: "纵食", address: "元丰天山花园(东门) 双流路267号" },
        { value: "钱记", address: "上海市长宁区天山西路" },
        { value: "壹杯加", address: "上海市长宁区通协路" },
        {
          value: "唦哇嘀咖",
          address: "上海市长宁区新泾镇金钟路999号2幢（B幢）第01层第1-02A单元",
        },
        { value: "爱茜茜里(西郊百联)", address: "长宁区仙霞西路88号1305室" },
        {
          value: "爱茜茜里(近铁广场)",
          address:
            "上海市普陀区真北路818号近铁城市广场北区地下二楼N-B2-O2-C商铺",
        },
        {
          value: "鲜果榨汁（金沙江路和美广店）",
          address: "普陀区金沙江路2239号金沙和美广场B1-10-6",
        },
        {
          value: "开心丽果（缤谷店）",
          address: "上海市长宁区威宁路天山路341号",
        },
        { value: "超级鸡车（丰庄路店）", address: "上海市嘉定区丰庄路240号" },
        { value: "妙生活果园（北新泾店）", address: "长宁区新渔路144号" },
        { value: "香宜度麻辣香锅", address: "长宁区淞虹路148号" },
        {
          value: "凡仔汉堡（老真北路店）",
          address: "上海市普陀区老真北路160号",
        },
        { value: "港式小铺", address: "上海市长宁区金钟路968号15楼15-105室" },
        { value: "蜀香源麻辣香锅（剑河路店）", address: "剑河路443-1" },
        { value: "北京饺子馆", address: "长宁区北新泾街道天山西路490-1号" },
        {
          value: "饭典*新简餐（凌空SOHO店）",
          address: "上海市长宁区金钟路968号9号楼地下一层9-83室",
        },
        {
          value: "焦耳·川式快餐（金钟路店）",
          address: "上海市金钟路633号地下一层甲部",
        },
        { value: "动力鸡车", address: "长宁区仙霞西路299弄3号101B" },
        { value: "浏阳蒸菜", address: "天山西路430号" },
        { value: "四海游龙（天山西路店）", address: "上海市长宁区天山西路" },
        {
          value: "樱花食堂（凌空店）",
          address: "上海市长宁区金钟路968号15楼15-105室",
        },
        { value: "壹分米客家传统调制米粉(天山店)", address: "天山西路428号" },
        {
          value: "福荣祥烧腊（平溪路店）",
          address: "上海市长宁区协和路福泉路255弄57-73号",
        },
        {
          value: "速记黄焖鸡米饭",
          address: "上海市长宁区北新泾街道金钟路180号1层01号摊位",
        },
        { value: "红辣椒麻辣烫", address: "上海市长宁区天山西路492号" },
        {
          value: "(小杨生煎)西郊百联餐厅",
          address: "长宁区仙霞西路88号百联2楼",
        },
        { value: "阳阳麻辣烫", address: "天山西路389号" },
        {
          value: "南拳妈妈龙虾盖浇饭",
          address: "普陀区金沙江路1699号鑫乐惠美食广场A13",
        },
      ];
    },
  },
  created () {
    this.suggestions = this.getAll();
  },
};
```

自定义建议模板

```
<el-autocomplete v-model="input"
                         :fetch-suggestions="fetchSuggestion"
                         placeholder="请输入内容">
          <template slot-scope="{ item }">
            <div class="name">{{ item.value }}</div>
            <div class="addr">{{ item.address }}</div>
          </template>
        </el-autocomplete>
```

异步(模拟远程搜索)

```
    fetchSuggestionAsyn (queryString, callback) {
      let res = queryString
        ? this.suggestions.filter(this.createFilter(queryString))
        : this.suggestions;

      clearTimeout(this.timeout);
      this.timeout = setTimeout(() => {
        callback(res);
      }, 1000);
    },
```



## select

`v-model`的值为当前被选中的`el-option`的 value 属性值

```
<el-select v-model="value" placeholder="请选择">
```



```
options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      value:''
```

:disabled="item.disabled"



自定义模板(在el-option标签里)

```
    <el-option
      v-for="item in cities"
      :key="item.value"
      :label="item.label"
      :value="item.value">
      <span style="float: left">{{ item.label }}</span>
      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span>
    </el-option>
```





分组

```
options: [{
          label: '热门城市',
          options: [{
            value: 'Shanghai',
            label: '上海'
          }, {
            value: 'Beijing',
            label: '北京'
          }]
        }, {
          label: '城市名',
          options: [{
            value: 'Chengdu',
            label: '成都'
          }, {
            value: 'Shenzhen',
            label: '深圳'
          }, {
            value: 'Guangzhou',
            label: '广州'
          }, {
            value: 'Dalian',
            label: '大连'
          }]
        }],
```



filterable 可搜索功能

远程搜索:

```
   filterable
    remote
    :remote-method="remoteMethod"
```



创建新条目:

```
 filterable
 allow-create
 default-first-option //按下回车自动选择第一条,不需要鼠标选中
```



## switch

 active-color  inactive-color  "#13ce66"

active-text inactive-text



```
<el-tooltip :content="'Switch value: ' + value" placement="top">
  <el-switch active-value inactive-value/>
```



## DatePicker 日期选择器

el-date-picker



带快捷选项

```
 :picker-options="pickerOptions"
 
  pickerOptions: {
          disabledDate(time) {
            return time.getTime() > Date.now();
          },
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              picker.$emit('pick', new Date());
            }
          }, {
            text: '昨天',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit('pick', date);
            }
          }, {
            text: '一周前',
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', date);
            }
          }]
```

type="daterange"

picker.$emit('pick', [start, end]);





选择周

 type="week"      format="yyyy 第 WW 周"

year/month/date/dates(多个时间)/ week/datetime/datetimerange/ daterange/monthrange

### 日期格式

使用`format`指定输入框的格式；使用`value-format`指定绑定值的格式。

默认情况下，组件接受并返回`Date`对象。以下为可用的格式化字串，以 UTC 2017年1月2日 03:04:05 为例：

请注意大小写

| 格式        | 含义             | 备注                                             | 举例          |
| :---------- | :--------------- | :----------------------------------------------- | :------------ |
| `yyyy`      | 年               |                                                  | 2017          |
| `M`         | 月               | 不补0                                            | 1             |
| `MM`        | 月               |                                                  | 01            |
| `W`         | 周               | 仅周选择器的 `format` 可用；不补0                | 1             |
| `WW`        | 周               | 仅周选择器的 `format` 可用                       | 01            |
| `d`         | 日               | 不补0                                            | 2             |
| `dd`        | 日               |                                                  | 02            |
| `H`         | 小时             | 24小时制；不补0                                  | 3             |
| `HH`        | 小时             | 24小时制                                         | 03            |
| `h`         | 小时             | 12小时制，须和 `A` 或 `a` 使用；不补0            | 3             |
| `hh`        | 小时             | 12小时制，须和 `A` 或 `a` 使用                   | 03            |
| `m`         | 分钟             | 不补0                                            | 4             |
| `mm`        | 分钟             |                                                  | 04            |
| `s`         | 秒               | 不补0                                            | 5             |
| `ss`        | 秒               |                                                  | 05            |
| `A`         | AM/PM            | 仅 `format` 可用，大写                           | AM            |
| `a`         | am/pm            | 仅 `format` 可用，小写                           | am            |
| `timestamp` | JS时间戳         | 仅 `value-format` 可用；组件绑定值为`number`类型 | 1483326245000 |
| `[MM]`      | 不需要格式化字符 | 使用方括号标识不需要格式化的字符 (如 [A] [MM])   | MM            |



 unlink-panels 取消两个结点联动



:default-time="['00:00:00', '23:59:59']" 控制一天的起始时间



## upload

```
<el-upload action="https://jsonplaceholder.typicode.com/posts/"
               multiple
               :limit="3"
               :on-preview="handlePreview"
               :on-remove="handleRemove"
               :before-remove="beforeRemove"
			   :on-exceed="handleExceed"
               :file-list="fileList">
      <el-button>点击上传</el-button>
      <div slot="tip">只能上传jpg/png文件,且不超过500kb</div>
    </el-upload>
```

on-exceed超出limit的回调,on-preview点击文件的回调



before-upload  function(file) 控制file类型和大小

this.$message.error()



list-type 控制上传列表样式  text/picture/picture-card



on-change 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用

function(file, fileList)



drag  是否启用拖拽上传

 

手动上传:

```
:auto-upload="false"

  <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
  
        submitUpload() {
        this.$refs.upload.submit();
      },
```

1. ref写在标签上,this.$refs.xxx获取的是标签对应的dom元素
2. ref写在组件上,this.$refs.xxx获取的是对应组件



## Form表单

在 Form 组件中，每一个表单域由一个 Form-Item 组件构成，表单域中可以放置各种类型的表单控件，包括 Input、Select、Checkbox、Radio、Switch、DatePicker、TimePicker

```
    <el-form :model="form"
             label-width="80px">
      <el-form-item label="活动名称">
        <el-input v-model="form.name"></el-input>
        ...
      </el-form-item>
    </el-form>
```



inline 行内



label-position 改变表单域标签的位置 right/left/top



rules status-icon 表单验证

给需要添加的form-item设置prop="xxx"(与form表单字段名称相同)

最简单的 之间在form-item设置required属性

```js
        rules: {
          name: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
       }
```

更多验证规则 [yiminghe/async-validator: validate form asynchronous (github.com)](https://github.com/yiminghe/async-validator)





## Alert 警告

页面中的非浮层元素，不会自动消失(可关闭)。



## [¶](https://element.eleme.io/#/zh-CN/component/message#message-xiao-xi-ti-shi)Message 消息提示

常用于主动操作后的反馈提示。与 Notification 的区别是后者更多用于系统级通知的被动提醒。

```
<template>
  <el-button :plain="true" @click="open">打开消息提示</el-button>
  <el-button :plain="true" @click="openVn">VNode</el-button>
</template>

<script>
  export default {
    methods: {
      open() {
        this.$message('这是一条消息提示');
      },

      openVn() {
        const h = this.$createElement;
        this.$message({
          message: h('p', null, [
            h('span', null, '内容可以是 '),
            h('i', { style: 'color: teal' }, 'VNode')
          ])
        });
      }
    }
  }
</script>
```



## Table

```
 <el-table
      :data="tableData"
      style="width: 100%">
      <el-table-column
        prop="date"
        label="日期"
        width="180">
      </el-table-column>
      ...
      </el-table>
```

row-class-name 为row设置className     Function({row, rowIndex})/String

固定表头 通过:height='xxx' 或 max-height="xxx" 即可实现



固定列 在需要固定的列上添加fixed

列是否固定在左侧或者右侧，true 表示固定在左侧

true, left, right



多级表头

嵌套 el-table-column，就可以实现多级表头

tableData按照普通table的模式,不需要有层级;因此,合并列不需要设置prop



新增序列号  新增el-table-column type=index

自定义 index   when `type=index` is set   number, Function(index):number



单选数据行样式

1. row-click    row, column, event 只能获取选中行数据 样式需要通过event复杂设置

2. highlight-current-row 高亮当前行

   current-change 当表格的当前行发生变化的时候会触发该事件 currentRow, oldCurrentRow

   setCurrentRow  设定某一行为选中行,如果调用时不加参数，则会取消目前高亮行的选中状态   row





多选 checkbox

新增 el-table-column  type="selection"

selection-change  当选择项发生变化时会触发该事件   selection

toggleRowSelection 切换某一行的选中状态，如果使用了第二个参数，则是设置这一行选中与否（selected 为 true 则选中）row, selected

clearSelection

show-overflow-tooltip(column) 当内容过长被隐藏时显示 tooltip  default false

tooltip-effect   tooltip `effect` 属性 dark/light





排序

sortable 对应列是否可以排序，如果设置为 'custom'，则代表用户希望远程排序，需要监听 Table 的 sort-change 事件

​	case when sortable  is true  sort-method Function(a, b):number

​	case when sortable  is true  && !sort-method   sort-by 按照哪个属性进行排序   String/Array/Function(row, index)

default-sort(table) 默认的排序列的 prop 和顺序。它的`prop`属性指定默认的排序的列，`order`指定默认排序的顺序 (ascending, descending)

sort-change  当表格的排序条件发生变化的时候会触发该事件   { column, prop, order }

formatter 用来格式化内容 Function(row, column, cellValue, row_index)



筛选

filters 数据过滤的选项，数组格式，数组中的元素需要有 text 和 value 属性。 Array[{ text, value }]
filter-method 对每条记录都会比对 Function(value, row, column)

​		column['property']就是props  value就是filters里的value

column-key: column 的 key，如果需要使用 filter-change 事件，则需要此属性标识是哪个 column 的筛选条件



自定义列模板 自定义列的显示内容

      <template slot-scope="scope">       
      <i class="el-icon-time"></i>       
      <span style="margin-left: 10px">{{ scope.row.xxx }}</span>      </template>

scope的属性{ row, column, $index }



展开行

新增一列 el-table-column type='expand' 配合slot-scope 渲染展开数据



树状结构

row-key  行数据的 Key(tableData的字段);在使用 reserve-selection 功能与显示树形数据时，该属性是必填的 Function(row)/String

tree-props 渲染嵌套数据的配置选项 {children: 'children', hasChildren: 'hasChildren'}

数据格式: {

​	....

children:[{....},{....} ...]

}

default-expand-all  当 Table 包含展开行存在或者为树形表格时有效

lazy 是否懒加载子节点数据

load when lazy is true  Function(row, treeNode, resolve) 配合hasChildren

​	resolve接受children数组



自定义表头

el-table-column配合slot='header'   { column, $index }



表尾合计行

show-summary 是否在表尾显示合计行

sum-text 合计行第一列的文本 默认"合计"

summary-method   自定义的合计计算方法   Function({ columns, data }):array



合并行或列

span-method  合并行或列的计算方法 

Function({ row, column, rowIndex, columnIndex }):array[rowspan,colspan]/{rowspan:xxxx,colspan:xxx}

```
    spanMethod ({ row, column, rowIndex, columnIndex }) {
      if (rowIndex % 2 === 0) {
        if (columnIndex === 0) {
          return [1, 2];
        } else if (columnIndex === 1) {
          return [0, 0];
        }
      }
    },
```



### NavMenu 导航菜单

顶栏

```\
 <el-menu :default-active="activeIndex"
           background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b"
           mode="horizontal"
           @select="xxx">
    <el-menu-item index="1">处理中心</el-menu-item>
    <el-submenu index="2">
      <template slot="title">我的工作台</template>
      <el-menu-item index="2-1">选项1</el-menu-item>
      <el-menu-item index="2-2">选项2</el-menu-item>
      <el-submenu index="2-3">
        <template slot="title">选项3</template>
        <el-menu-item index="2-3-1">选项3-1</el-menu-item>
        <el-menu-item index="2-3-2">选项3-2</el-menu-item>
      </el-submenu>
    </el-submenu>
    <el-menu-item index="3"
                  disabled>消息中心</el-menu-item>
    <el-menu-item index="4">
      <el-link href="https://www.ele.me"
               target="_blank">订单管理</el-link>
    </el-menu-item>
  </el-menu>
```



## carousel 走马灯/轮播图



# 案例

主页

```
<template>
  <el-container>
    <el-header>
      <el-menu :default-active="activeIndex"
               mode="horizontal"
               @select="handleSelect">
        <el-menu-item index="/home"> 首页 </el-menu-item>
        <el-menu-item index="/user"> 用户管理 </el-menu-item>
        <el-menu-item index="4"
                      disabled>
          <el-link href="https://www.ele.me"
                   target="_blank">订单管理</el-link>
        </el-menu-item>
        <el-button-group style="float: right; margin: 10px 10px">
          <el-button type="primary"
                     round
                     style="margin-right: 5px">登录</el-button>
          <el-button type="info"
                     round>注册</el-button>
        </el-button-group>
      </el-menu>
    </el-header>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script>
export default {
  data () {
    return {
      activeIndex: "/home",
    };
  },
  methods: {
    handleSelect (key) {
      if (this.$route.path != key) {
        this.$router.push(key);
      }
    },
  },
};
</script>

```


