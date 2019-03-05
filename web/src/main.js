// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import Antd from 'ant-design-vue'
import axios from 'axios'
import Qs from 'qs'
import "ant-design-vue/dist/antd.css"
import App from './App'
import router from './router'

Vue.config.productionTip = false;
Vue.prototype.axios = axios;
Vue.prototype.qs = Qs;
Vue.use(Antd);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
});
