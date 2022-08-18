1.基础
导入的三种方式：
link
style
行内style
style内 @import url(xxx.css)

2.选择器
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

3.权重
行内>#（100）>. 属性(10)>标签，伪类（1）
强制优先级：！important
子类会继承父类部分的样式（边框,高度不会继承）

4.文本
字重（粗细）：normal bold
字号（大小）：small medium large em等同于100%
颜色：color:rgb(x,x,x,0.2)
行高：line-height
风格：font-style
组合：必须有font-size+font-family(最后)

文本线条：text-decoration: undeline/none/line-through
文本阴影：text-shadow
空白： white-space:pre(保留空白) nowrap(禁止换行)
文本溢出：  white-space: nowrap;
           overflow: hidden;
           text-overflow: ellipsis;
文本缩进：text-indent
文本对齐：text-align vertical-align: middle(只对行内元素有效 与display: table-cell配合);
word-spcing,letter-spcing,writting-mode

5.盒子模型
相邻元素的纵向外边距会进行合并
box-sizing:包括内边距和边框
border-radius
outline:在元素获得焦点的时候出现
visibility：hidden 空间位仍占有
单行文本溢出：  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
多行文本溢出：
overflow: hidden;
 display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;、
盒子阴影 box-shadow: 10px 10px 5px rgba(100, 100, 100, .5);

6.背景
线性渐变 background: linear-gradient(30deg, red, green);
径向渐变 background: radial-gradient(at bottom left, red, blue);
标识表示的是颜色过渡时的分配
radial-gradient(red 0, yellow 30%, black 60%, black 100%);

7.表格
border-spcing:cell边框和table外边框的距离
border-collapse:cell边框合并
empty-cells: hide; 把没内容的隐藏掉
ul atrr(list-style-type)

8.浮动
浮动是对后面元素的影响，下图中第二个元素设置浮动对第一个元素没有影响
如果只给第一个元素设置浮动，第二个元素不设置，后面的元素会占用第一个元素空间（用clear解决浮动）
浮动元素边界不能超过父元素的padding
元素浮动后会变为块元素
1.在父元素内部最后面添加一个没有高度的了元素，并使用clear:both .clearfix {
      clear: both;
      height: 0;
  }
  2.或者为 父标签 .clearfix::after {
    content: "";
    display: block;
    clear: both;
}

3.子元素使用浮动后将不占用空间 为父元素添加overflow: hidden; 将会使用父元素产生 BFC 机制，即父元素的高度计算会包括浮动元素的高度。

9.定位
position：absolute 绝对于浏览器窗口 （父元素非relative）绝对定义不受文档流影响，就像漂浮在页面中的精灵，绝对定位元素拥有行内块特性。
relative 相对定位是相对于元素原来的位置控制，当元素发生位置偏移时，原位置留白

父子效果相同 但一般用relative-absolute