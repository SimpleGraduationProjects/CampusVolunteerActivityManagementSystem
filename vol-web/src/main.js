import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import iView from 'view-design'
import axios from 'axios'

import 'view-design/dist/styles/iview.css'
import './my-theme/index.less'

Vue.use(iView)
// Vue.use(axios)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  axios,
  render: h => h(App)
}).$mount('#app')
