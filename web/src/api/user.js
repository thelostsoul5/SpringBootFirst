import http from './setting'

export default {
  login(user) {
    return http.post('/api/login', user);
  },
  user(param) {
    return http.get('/api/user', param);
  },
}
