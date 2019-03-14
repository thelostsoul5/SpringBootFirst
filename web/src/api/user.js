import http from './setting'

export default {
  login(user) {
    return http.post('/api/login', user);
  },
}
