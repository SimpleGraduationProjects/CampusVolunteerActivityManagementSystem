import axios from 'axios'
import qs from 'qs'
import { ApiError } from '../common'
const TIMEOUT = 10000
axios.defaults.withCredentials = true
axios.defaults.headers['Content-Type'] = 'application/json'
export class Http {
  constructor (timeout = TIMEOUT) {
    this.Axios = axios.create({
      baseURL: 'http://127.0.0.1:8888/',
      // baseURL: 'http://127.0.0.1:8888/',
      timeout: timeout
    })
  }
  post (url, params) {
    console.log('params', params)
    console.log('qs_params', qs.stringify(params))
    console.log(url)
    console.log('document.cookie', document.cookie)
    // 用参数形式写入url中
    return this.Axios.post(url + '?' + document.cookie, params).catch(e => {
      console.log(url + '请求失败')
      return Promise.reject(new ApiError(
        'httpClass network error', -1
      ))
    })
  }

  get (url) {
    // url = process.env.API_DOMAIN + '/' + url
    return this.Axios.get(url, {
      headers: {
        'Content-type': 'application/json'
      }
    }).catch(e => {
      return Promise.reject(new ApiError(
        'network error', -1
      ))
    })
  }

  // 上传图片的请求方法
  uploadFile (url, params) {
    console.log('httpclass:', params)
    return this.Axios.post(url, params, {}).catch(e => {
      console.log(url + '请求失败')
      return Promise.reject(new ApiError(
        'httpClass network error', -1
      ))
    })
  }
}
const http = new Http()
export default http
