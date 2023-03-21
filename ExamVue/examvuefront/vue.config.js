const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy:{
      '/api':{
        target:'http://localhost:9091',
        changeOrigin:true,
        pathRewrite: {
          '^/api':''
        }
      }
    }
  },
})
