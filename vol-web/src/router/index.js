import Vue from 'vue'
import Router from 'vue-router'
import routes from './routes'
import Utils from '@/common/util.js'
Vue.use(Router)

const router = new Router({
  mode: 'hash',
  routes
})

console.log(Utils.getCookie('sessionId'))
// 注册全局前置守卫
router.beforeEach((to, from, next) => {
  // 通过检查是否有cookie获取登录状态，保存在一个常量里
  const HAS_LOGINED = Boolean(Utils.getCookie('sessionId'))
  if (to.name !== 'Login') {
    if (HAS_LOGINED) next()// 如果已经登录，直接跳转
    else next({ name: 'Login' })// 如果没有登录，跳转到login页面
  } else {
    if (HAS_LOGINED) next({ name: 'index' })
    else next() // 进入该路由
  }
})

export default router
