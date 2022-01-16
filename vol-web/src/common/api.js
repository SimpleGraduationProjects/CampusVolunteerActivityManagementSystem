import { ApiError, HttpClass } from '../common'

export class Api extends HttpClass {
  constructor (timeout = 100000) {
    super(timeout)
  }

  call (api, params) {
    const url = api
    const par = this._params(params)
    return this.post(url, par).then(response => {
      if (!response || !response.data) {
        return Promise.reject(new ApiError('network error response', -1))
      }
      const data = response.data
      if (!data || typeof data !== 'object') {
        return Promise.reject(new ApiError('network error format', -1))
      }
      if (data) {
        // if (data.statusCode && data.statusCode === 200) {
        return Promise.resolve(data)
      }
      return Promise.reject(new ApiError(data.msg, data.statusCode))
    })
  }

  _params (params) {
    if (!params || typeof params !== 'object') {
      params = {}
    }
    return params
  }
}
const api = new Api(100000)
export default api
