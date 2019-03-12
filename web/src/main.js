import Vue from 'vue'
import Antd from 'ant-design-vue'
import Qs from 'qs'
import axios from 'axios'
import cookie from 'vue-cookies'
import "ant-design-vue/dist/antd.css"
import App from './App'
import router from './router'
import base from './base'

Vue.config.productionTip = false;
Vue.prototype.axios = axios.create();
Vue.prototype.qs = Qs;
Vue.use(Antd);
Vue.use(cookie);

router.beforeEach(base.dealWithCookies);

/* 错误处理 */
Vue.prototype.axios.interceptors.response.use(function(response) {
  console.log('resp:', response);
  return response;
}, base.catchError);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
