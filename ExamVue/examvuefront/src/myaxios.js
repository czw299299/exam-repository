import Vue from 'vue'
import axios from 'axios'
import app from '../public/appconfig'
console.log(axios);
axios.defaults.baseURL = process.env.NODE_ENV == "development" ? "http://localhost:8181/api" : app.app.apiServer;
// axios.defaults.headers["token"] = sessionStorage.getItem("token");

// axios.defaults.transformRequest = [(
//     (data, headers) => {
//         console.log(data);
//         console.log(headers.toString());
//         return JSON.stringify(data);
//     })
// ]
//请求拦截器 在请求头中加token
axios.interceptors.request.use(
    config => {
        if (sessionStorage.getItem('token')) {
            config.headers.token = sessionStorage.getItem('token');
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
)

Vue.prototype.$axios = axios;