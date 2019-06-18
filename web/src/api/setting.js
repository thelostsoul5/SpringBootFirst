import Qs from 'qs'
import axios from 'axios'
import {message} from 'ant-design-vue';

const http = axios.create();

/* 错误处理 */
http.interceptors.response.use(function(response) {
  return response;
}, catchError);

function catchError(error) {
  if (error.response) {
    switch (error.response.status) {
      case 400:
        message.error('请求参数异常:' || error.response.data);
        break;
      case 401:
        message.error(error.response.data);
        if (router.path !== '/Login') {
          router.push('/Login');
        }
        break;
      case 403:
        message.error('无访问权限，请联系企业管理员:' || error.response.data);
        break;
      case 404:
        message.error('认证失效或者没有访问权限:' || error.response.data);
        break;
      default:
        message.error(error.response.data);
    }
  }
  return Promise.reject(error);
}

export default http;
