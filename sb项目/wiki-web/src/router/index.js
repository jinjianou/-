import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

const NOTFOUND = {
  template: `<h1>NOT FOUND!!</h1>`
}

export default new Router({
  routes: [
    {
      path: '/',
      redirect:'/index',
   
    },
    {
      path: '/index',
      name: 'Index',
      component: ()=>import('@/components/Index.vue'),
      children:[{
        path: 'testRouteMeta',
        name: 'TestRouteMeta',
        component: ()=>import('@/components/TestRouteMeta.vue'),
        meta:{
        },
        }
      ]
    },
    
    {
      path: '*',
      component: NOTFOUND
    }
  ]
})
