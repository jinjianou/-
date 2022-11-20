'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

//process.env nodejs的一个环境变量，保存着系统的环境的变量信息
// console.log(process.env);
//这里配置的HOST 不在process.env中
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  VUE_APP_PORT: 9090,
})
