# 初识

> npm install typescript -g
>
> tsc -v

问题： 无法加载文件 E:\npmRepo\tsc.ps1，因为在此系统上禁止运 行脚本

解决： 以管理员身份运行 PowerShell，并执行命令set-ExecutionPolicy RemoteSigned将PowerShell的执行策略更改为RemoteSigned (get-ExecutionPolicy查看是否生效)



> tsc app.ts  //生成app.js
>
> node app.js
>
> 流程优化 nodejs环境执行ts
>
> npm i ts-node -g
>
> npm i @types/node ---save-dev
>
> ts-node app.ts  //不会生成app.js中间文件







# 数据类型

## 基础类型

string number boolean 
注意：小写表示基本类型,大写是对象

```  let str:String='123' //ok
	 let flag:Boolean=true //ok
	  let flag:boolean=new Boolean(1) //error
```



void 空值类型 包含 undefined/null

```
let u:void=undefined
let n:void=null

function fnVoid():void{

}
```

和 undefined/null的区别

```
let u:void=undefined
let u2:undefined/null=undefined
let str:string='123'

str=u  //error
str=u2  //ok
```



## Any

顶级类型： Any 类型和unknown 

共同点：

1. 能够赋任意值



不同：

1. unknown 是不能调用属性和方法

```
let uk:unknown={a:1}
let av:any={a:1}
uk.a // error
av.a //ok
```

2. 不能将unkown类型赋值给除any/unkown外的其他类型

   ```
   let uk:unknown={a:1}
   let av:any={a:1}
   let str:string
   str=uk //error
   str=av //ok
   ```

   

## 接口和对象类型

```
interface A{
	name: string
}

let a:A={
	name:'jinjianou'
}
```

1. 不允许额外定义

   ```
   interface A{
   	name: string
   }
   
   let a:B={
   	name:'jinjianou', 
   	age:10,  //error
   }
   ```

   

2. 存在多个接口定义将会合并

   ```
   interface A{
   	name: string
   }
   interface A{
   	age: number
   }
   等价于
   interface A{
   	name: string
   	age: number
   }
   ```

   

3. 可以用？表示可选字段，否则必须赋值

   ```
   interface A{
   	name: string
   	age?: number
   }
   
   
   //it's ok
   let a:A={  
   	name:'jinjianou'
   }
   
   ```

4. 添加任意额外属性

   ```
   interface A{
   	name: string
   	age?: number
   	//如果指定类型 如string 这A中其他属性也得是string
   	//[property: string]:any
   	[property: string]:string|number
   }
   
   //it's ok
   let a:A={
   	name:'jinjianou', 
   	abc: 123
   }
   ```



4. readonly

   ```
   interface A{
   	readonly name: string
   	age?: number
   	[property: string]:any
   }
   
   let a:A={
   	name:'jinjianou', 
   	abc: 123
   }
   
   a.name='jinjianou2' //error
   ```

   

5. 添加函数

   ```
   interface A{
   	readonly name: string
   	age?: number
   	[property: string]:any,
   	cb():void,
   }
   
   //可以覆盖声明函数的返回值
   let a:A={
   	name:'jinjianou', 
   	abc: 123,
   	cb: ()=>{
   		return 123
   	}
   }
   ```

   

6. 允许继承

```
interface A{
	name: string
}
interface B extends A{
	age: number
}
等价于
interface B{
	name: string
	age: number
}
```





## 数组

方式一

```
let arr:number[]=[1,2,3]
let arr2:any[]=[1,'str',true]
```

方式二

```
let arr:Array<number>=[1,2,3]
let arr2:Array<any>=[1,'str',true]
```



1. 多维数组

   ```
   let arr:number[][]=[[1,2],[3]]
   let arr2:any[][]=[[1,'str'],[true]]
   
   let arr:Array<Array<number>>=[[1,2],[3]]
   let arr2:Array<Array<any>>=[[1,'str'],[true]]
   ```

   

2. 自定义类数组结构

   ```
   interface ArrayNumber{
   	[index:number]:string
   }
   let a:ArrayNumber=['1','2','3']
   console.log(a); //[ '1', '2', '3' ]
   ```



## 函数扩展

1. 默认值

2. ? 可选参数

3. interface 参数列表

   ## 函数重载

   定义与java相同，方法名相同，参数不同，返回值可以相同也可以不同

   1. 参数类型不同，操作函数参数类型应设置为any

      ```
      		function getResult (input: string): number 
      		function getResult (input: number): string 
      		function getResult <T>(input: T): T 
      		function getResult (input: any): any { 
      			if (typeof input === 'string') return input.length 
      			else if (typeof input === 'number') return input.toString() 
      			else return input 
      		}
      ```

      

   2. 参数数量不同，可以将不同的参数设置为可选

      ```
      function getResult (input: string): number 
      function getResult (input: number,input2: string): string 
      function getResult (input: any,input2?: any): any { ... }   //无这个参数则为undefinend
      ```

   

   

   # 类扩展

   ## 抽象类

   跟 java 的概念基本相同
   

```
abstract class A {
	name:string
	constructor(name:string) {
		this.name=name;
	}
	setName(name:string):void{
		this.name=name;
	}
	abstract getName():string
}

class B extends A {
	constructor(name:string) {
		super(name);
	}
	getName():string{
		return this.name;
	}
}
```



## 元组类型

**元组是数组的变种，是固定数量的不同类型的元素的组合**

```
let arr:[string,number]=["1",2]

//只能是定义时的类型 即string|number
arr.push("3")
```



## 枚举



### 数字枚举

```
enum Color{
	red,
	blue, 
	green
}
```

默认red从0开始，blue=1,green=2

给前面的设置初始值  如 red=1 则blue=2,green=3

还可以全部枚举值自定义   如red=10,blue=5,green=3



### 字符串枚举

必须将所有枚举项设置值

### 异构枚举

字符串和数字的混合

```
enum Color{
	yes='yes',
	no=0
}
```



### 接口枚举

```
enum Color{
	yes='yes',
	no=0
}

interface A{
	color:Color
}

let a:A={
	color:Color.yes
}
```



### const 枚举

```
[const] enum Color{
	success,
	fail
}
let code:number=0
if(code==Color.fail){}
```

默认情况（不带const）编译后

```
var Color;
(function (Color) {
    Color[Color["success"] = 0] = "success";
    Color[Color["fail"] = 1] = "fail";
})(Color || (Color = {}));
var code = 0;
if (code == Color.fail) { }

```

也就是Color["success"] =0 && Color[0] ="success"



带const编译后

```
var code = 0;
if (code == 0 /* Color.success */) { }
```

直接替换



### 反向映射

也就是上述说的 key->value   value->key

```
 enum Color{
	success,
	fail
}

let key:number=Color.success
let val=Color[key]
```

注意： 必须保证key是number类型，而不是string类型等



## 类型推论

### 类型推论

> let str='123'  //推论成string

### 类型别名

```
type s=string
let str:s='123'  //等价于let str:string='123'
```

```
type cb=()=> string
const fn:cb=()=>'jinjianou'
```

```
 type T='a'|true|1
 let v:T=true
```



## never类型

TS使用never类型来表示不应该存在的状态

```
 type T=string & number //等价于type T=never
 
  function fn(msg:string):never {
	throw new Error(msg)
}

 function fn():never {
	while(true){
		
	}
}
 
 let a:never='123' //error never不能赋任何值
 
```



