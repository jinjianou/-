'use strict'
module.exports = {
  NODE_ENV: '"production"',
  HOST: '"http://www.haha2.com"',
  PORT: 9091,
  // proxyTable: {
  //   '/test': {
  //     // 后台接口真实地址
  //     target: 'http://localhost:8088/',
  //     changeOrigin: true,
  //     pathRewrite: {
  //        // 将 步骤1 中设置的请求中的 /api/ 重写为 /project/
  //        // 加上上面的target， 请求最终会变为 http://localhost:8080/project/
  //        '^/test': 'test'
  //      }
  //   }
  // },
}
