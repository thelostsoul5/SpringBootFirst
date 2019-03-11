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
};

export const dealWithCookies = function(to, from, next) {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!sessionValid()) {
      next({
        path: '/Login',
        query: { redirect: to.fullPath } // 把要跳转的地址作为参数传到下一步
      })
    } else {
      next()
    }
  } else {
    if (to.query && to.query.redirect) {
      if (sessionValid()) {
        next({path: to.query.redirect})
      } else {
        next()
      }
    } else {
      next() // 确保一定要调用 next()
    }
  }
};

function sessionValid() {
  let session = router.app.$cookies.get("SESSION");
  return session!=null && session !== '';
}
