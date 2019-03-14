import router from 'vue-router';

function dealWithCookies(to, from, next) {
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
}

function sessionValid() {
  let session = router.app.$cookies.get("SESSION");
  return session!=null && session !== '';
}

export default {
  dealWithCookies
}
