import Vue from 'vue'
import Qs from 'qs'
import axios from 'axios'
import utils from './utils.js';

Vue.prototype.axios = axios.create();
Vue.prototype.qs = Qs;

//instance.defaults.headers.post['Content-Type'] = 'application/json';
//错误处理
Vue.prototype.axios.interceptors.response.use(function(response) {
  return response;
}, utils.catchError);


export default utils;
