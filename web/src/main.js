import Vue from 'vue'
import Antd from 'ant-design-vue'
import cookie from 'vue-cookies'
import "ant-design-vue/dist/antd.css"
import App from './App'
import router from './router'
import base from './base'
import api from './api'

Vue.config.productionTip = false;
Vue.prototype.$api = api;
Vue.use(Antd);
Vue.use(cookie);

router.beforeEach(base.dealWithCookies);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
