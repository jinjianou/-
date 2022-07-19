git clone xxx

npm install

npm run serve



 Fix the upstream dependency conflict

npmV7之前的版本遇到依赖冲突会忽视依赖冲突，继续进行安装
npmV7版本开始不会自动进行忽略，需要用户手动输入命令
有两个命令可以解决此问题
一是 --force 无视冲突，强制获取远端npm库资源 （覆盖之前）
二是 --legacy-peer-deps 忽视依赖冲突,继续安装（不覆盖之前）





Error: Rule can only have one resource source (provided resource and test + include + exclude) in {}

原因：某些新版本的库要求 webpack@5，更新依赖时，根据依赖选择的规则，就以 webpack@5 作为主依赖安装。然而 @vue/cli 依赖 webpack@4，它自带的 webpack 配置无法兼容 webpack@5 ，于是就报错，不能继续编译。如果你也在使用 @vue/cli，那么请不要贸然升级 webpack@5。

解决：调低webpack版本



与后台交互，以登录接口为例

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
   
       @PostMapping("getUserInfo")
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

   

