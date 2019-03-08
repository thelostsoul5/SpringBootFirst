import router from 'vue-router';
import {message} from 'ant-design-vue';

export const catchError = function(error) {
  if (error.response) {
    switch (error.response.status) {
      case 400:
        message.error('请求参数异常:' || error.response.data);
        break;
      case 401:
        message.error(error.response.data);
        router.push('/');
        break;
      case 403:
        message.error('无访问权限，请联系企业管理员:' || error.response.data);
        break;
      default:
        message.error(error.response.data);
    }
  }
  return Promise.reject(error);
}
