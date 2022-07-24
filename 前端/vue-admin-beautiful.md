# 介绍

可以在完全不依赖后台的情况下独立开发完成项目，以及接口自动模拟生成，支持JAVA、PHP、NODE、.NET、Django等常用所有后台对接

## 安装

```
git clone -b vue3.0-antdv https://github.com/chuzhixin/vue-admin-better.git

npm install

npm run serve
```

npm install @vue/cli-service -g

问题1: 卡住

1. 删除node_modules重新(可选) npm install

2. npm install cnpm -g --registry=https://registry.npmmirror.com

   cnpm install

3. 删除proxy npm config delete npm

 

问题2:   Fix the upstream dependency conflict



 Fix the upstream dependency conflict

npmV7之前的版本遇到依赖冲突会忽视依赖冲突，继续进行安装
npmV7版本开始不会自动进行忽略，需要用户手动输入命令
有两个命令可以解决此问题
一是 --force 无视冲突，强制获取远端npm库资源 （覆盖之前）
二是 --legacy-peer-deps 忽视依赖冲突,继续安装（不覆盖之前）



问题3: Error: Rule can only have one resource source (provided resource and test + include + exclude) in {}

原因：某些新版本的库要求 webpack@5，更新依赖时，根据依赖选择的规则，就以 webpack@5 作为主依赖安装。然而 @vue/cli 依赖 webpack@4，它自带的 webpack 配置无法兼容 webpack@5 ，于是就报错，不能继续编译。如果你也在使用 @vue/cli，那么请不要贸然升级 webpack@5。

解决：

1. 调低webpack版本

2. 删掉node_modules 和  package-lock.json

   手动在 package.json 的 devDependencies 里添加 "webpack": "^4.23.0"

    重新install



# 与后台交互

以登录接口为例

1. login/index.vue  handleLogin 

   ```
   this.$store
                 .dispatch('user/login', this.form)
   ```

   

2. store/user.js

   ```
   import { getUserInfo, login, logout } from '@/api/user'
   ...
   async login({ commit }, userInfo) {
   ```

3. @api/user

   ```
   import request from '@/utils/request'
   ...
   export async function login(data) {
     if (loginRSA) {
       data = await encryptedData(data)
     }
     return request({
       url: '/login',
       method: 'post',
       data,
     })
   }
   ```

   注意：需要把loginRSA设为false 或者新建一个publicKey接口

4. @/utils/request

   ```
   import {
     baseURL,
     contentType,
     debounce,
     invalidCode,
     noPermissionCode,
     requestTimeout,
     successCode,
     tokenName,
     loginInterception,
   } from '@/config'
   ```

5. @/config/net.config.js

```
  // 默认的接口地址 如果是开发环境和生产环境走vab-mock-server，当然你也可以选择自己配置成需要的接口地址
  baseURL:
    process.env.NODE_ENV === 'development'
      ? 'http://localhost:8090'
      : 'vab-mock-server',
```

6. response

   ```
   {
       url: '/login',
       type: 'post',
       response(config) {
         const { username } = config.body
         const accessToken = accessTokens[username]
         if (!accessToken) {
           return {
             code: 500,
             msg: '帐户或密码不正确。',
           }
         }
         return {
           code: 200,
           msg: 'success',
           data: { accessToken },
         }
       },
     },
   ```

7. 编写后台接口

   ```
   @RestController
   @RequestMapping("/user")
   @CrossOrigin
   public class UserController {
           @PostMapping("login")
       public Map<String,Object> login(@RequestBody UserInfo userInfo) throws JsonProcessingException {
           ObjectMapper mapper = new ObjectMapper();
           return new HashMap<String,Object>(){{
              put("code",200);
              put("msg","success");
              put("data",mapper.readValue(
                      "{\"accessToken\":\"admin-accessToken\"}",Map.class));
           }};
       }
   
       @PostMapping("userInfo")
       public Map<String,Object> getUserInfo() throws JsonProcessingException {
           ObjectMapper mapper = new ObjectMapper();
           return new HashMap<String,Object>(){{
               put("code",200);
               put("msg","success");
               put("data",mapper.readValue(
                       "{\n" +
                               "  " +
                               "\"permissions\":[\"admin\"],\n" +
                               "  \"username\": \"admin\",\n" +
                               "  \"avatar|1\": [\n" +
                               "    \"https://i.gtimg.cn/club/item/face/img/2/15922_100.gif\",\n" +
                               "    \"https://i.gtimg.cn/club/item/face/img/8/15918_100.gif\"\n" +
                               "  ]\n" +
                               "}",Map.class));
           }};
       }
   }
   ```

   

8. router.beforeResolve(async (to, from, next) => {

   permissions = await store.dispatch('user/getUserInfo')

   }

9. getUserInfo

   ```
       url: '/userInfo',
       type: 'post',
       response(config) {
         const { accessToken } = config.body
         let permissions = ['admin']
         let username = 'admin'
         if ('admin-accessToken' === accessToken) {
           permissions = ['admin']
           username = 'admin'
         }
         if ('editor-accessToken' === accessToken) {
           permissions = ['editor']
           username = 'editor'
         }
         if ('test-accessToken' === accessToken) {
           permissions = ['admin', 'editor']
           username = 'test'
         }
         return {
           code: 200,
           msg: 'success',
           data: {
             permissions,
             username,
             'avatar|1': [
               'https://i.gtimg.cn/club/item/face/img/2/15922_100.gif',
               'https://i.gtimg.cn/club/item/face/img/8/15918_100.gif',
             ],
           },
   ```

   

登录index/index.vue页面，但跳转之前会先经过permissions router.beforeResolve  loginInterception

·

```
    permissions router.beforeResolve:
     	permissions = await store.dispatch('user/getUserInfo')
     
     
    async getUserInfo({ commit, state }) : 
        if (permissions && username && Array.isArray(permissions)) {
          commit('setPermissions', permissions)
          }
                 
    zx-layouts:
    	const permissions = store.getters['user/permissions']
    
    pugins:
        import VabPermissions from 'zx-layouts/Permissions'
        Vue.use(VabPermissions)
        
    main.js:
    	import './plugins'
```





# 路由和侧边栏

本项目路由和侧边栏是绑定在一起的，在@/routes/idndex.js是下面配置对应的路由，侧边栏就能动态生成



## 配置项

```js
hidden: true  //不会在侧边栏出现
redirect: noRedirect; //该路由在面包屑导航栏中不可被点击
component: Layout //vue-admin自带的整体页面布局，组件内筒填充到vab-app-main

//当一个路由下面的children路由>1，会自动变成嵌套（折叠菜单）模式
//如果只有一个，则会将那个子路由当做跟路由(替换掉父路由)显示在侧边栏
//若想显示根路由（父路由，无论children个数），可以设置父路由alwaysShow：true
alwaysShow：true

name: str //必填
meta:{
    roles:[] //设置该路由进入的权限，支持多个权限叠加
    title: str //菜单栏和面包屑的标题
    icon: str  //支持svg和el-icon-xx
    noCache: true //不会被<keep-alive>缓存 default false
    breadcrumb: false //不会在面包屑展示 default true
    affix: true  //会固定(指的是不可删除)在tags-views(导航栏下边的tabs) default false
    
    //高亮对应的侧边栏
    //如点击文章详情页 此时路由article/1 可以高亮article/list
    activeMenu: str
    
}
children:[]
```



## 分类

constantRoutes ： 不需要动态判断权限的路由 如login/401/404...

asyncRoutes :  需要动态判断权限的路由

以上都使用了路由页面**懒加载**





Demo:

```
{
    path: "/test",
    component: Layout,
    redirect: "noRedirect",
    children: [
      {
        path: "test",
        name: "Test",
        component: () => import("@/views/test/index"),
        meta: {
          title: "test",
          icon: "marker",
          permissions: ["admin"],
        },
      },
    ],
  },
```



eslint问题

1. 注释掉 .eslintrc,js

   ```
   module.exports = {
     root: true,
     env: {
       node: true,
     },
     extends: ['plugin:vue/recommended', '@vue/prettier'],
     // rules: {
     //   'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
     //   'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
     //   'vue/no-v-html': 'off',
     //   'vue/multi-word-component-names': 'off',
     //   'vue/no-useless-template-attributes': 'off',
     //   'vue/no-reserved-component-names': 'off',
     // },
     parserOptions: {
       parser: 'babel-eslint',
     },
     // overrides: [
     //   {
     //     files: [
     //       '**/__tests__/*.{j,t}s?(x)',
     //       '**/tests/unit/**/*.spec.{j,t}s?(x)',
     //     ],
     //     env: {
     //       jest: true,
     //     },
     //   },
     // ],
   }
   
   ```



2. 新增.eslintignore 文件  添加/src

# 权限

该项目中权限的实现方式是：通过获取当前用户的权限(/user/getUserInfo(token))去对比路由表(@/router/index.js  asynRoutes),生成当前用户可访问的路由表，通过router.addRoutes动态挂载到routes上

```js
//这个方法称为 路由守卫
router.beforeResolve(async (to, from, next) => {
    //是否显示顶部进度条
  if (progressBar) VabProgress.start()
    //获取token
  let hasToken = store.getters['user/accessToken']

  //如果不做登录拦截，hasToken = true
  if (!loginInterception) hasToken = true

  if (hasToken) {
      //有token如果要跳转到/login,跳回到/
    if (to.path === '/login') {
      next({ path: '/' })
      if (progressBar) VabProgress.done()
    } else {
        //非第一次登录，已经获得permission  getUserInfo时会设置
      const hasPermissions =
        store.getters['user/permissions'] &&
        store.getters['user/permissions'].length > 0
      if (hasPermissions) {
        next()
      } else {
        try {
          let permissions
          if (!loginInterception) {
            //settings.js loginInterception为false时，创建虚拟权限
            await store.dispatch('user/setPermissions', ['admin'])
            permissions = ['admin']
          } else {
              //await 会阻塞后面的代码，等着 Promise 对象 resolve，然后得到 resolve 的值，作为 await 表达式的运算结果
            permissions = await store.dispatch('user/getUserInfo')
          }

          let accessRoutes = []
          //all模式或智能模式
          if (authentication === 'intelligence') {
              //constant+asyn[permissions]
            accessRoutes = await store.dispatch('routes/setRoutes', permissions)
          } else if (authentication === 'all') {
              //constant+asyn
            accessRoutes = await store.dispatch('routes/setAllRoutes')
          }
          accessRoutes.forEach((item) => {
            router.addRoute(item)
          })
          //在addRoutes()之后第一次访问被添加的路由会白屏，这是因为刚刚addRoutes()就立刻访问被添加的路由，然而此时addRoutes()没有执行结束，因而找不到刚刚被添加的路由导致白屏
            //确保addRoutes()时动态添加的路由已经被完全加载上去
            //replace: true只是一个设置信息，告诉VUE本次操作后，不能通过浏览器后退按钮，返回前一个路由
            //如果参数to不能找到对应的路由的话，就再执行一次beforeEach((to, from, next)直到其中的next({ ...to})能找到对应的路由为止。
          next({ ...to, replace: true })
        } catch {
          await store.dispatch('user/resetAccessToken')
          if (progressBar) VabProgress.done()
        }
      }
    }
  } else {
      //没有token
      //在白名单中
    if (routesWhiteList.indexOf(to.path) !== -1) {
    //放行 带参数的则是中断（不会执行router.afterEach(() => {}）当前导航，执行新的导航
      next()
    } else {
        //token失效回退到登录页时是否记录本次的路由
      if (recordRoute) {
        next(`/login?redirect=${to.path}`)
      } else {
        next('/login')
      }

      if (progressBar) VabProgress.done()
    }
  }
  document.title = getPageTitle(to.meta.title)
})
router.afterEach(() => {
  if (progressBar) VabProgress.done()
})
```



实际项目开发，路由表可以由tree组件等方式配置，并存储到后端或数据库。当用户登录时获取roles,前端根据roles去后端请求可访问的路由表，从而动态生成可访问页面。本质时一样的



指定权限(本项目不支持)

```js
	<el-xxx v-permission="['admin']">
        
    @/directive/permission/index.js
```





export function getTreeList(data) {

  return request({

​    url: '/tree/list',

​    method: 'post',

​    data,

  })

}

 