const path = require('path')
const assetsRoot = path.resolve(__dirname, 'build/resources/main/static')// <-----1.add

module.exports = {
  devServer: {
    proxy: {// <-----7.change
      //代理前台/api开头的请求，代理到8080端口，spring boot的访问端口
      '/api': {
        target: 'http://localhost:8080'
      }
    },
    host: 'localhost',
    port: 4000
  },
  outputDir: assetsRoot,
  assetsDir: 'assets',
  indexPath: path.resolve(assetsRoot, 'index.html'),// <-----2.change
  configureWebpack: {
      resolve: {
          alias: {
            'vue$': 'vue/dist/vue.esm.js' 
          }
        }
  }
  }