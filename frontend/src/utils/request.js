import axios from 'axios'
import {
  message,
  Modal,
  notification
} from 'ant-design-vue'
import moment from 'moment'
import store from '../store'
import db from 'utils/localstorage'
moment.locale('zh-cn')

//let request_url= 'http://192.168.64.115:1099/';
//let request_url= 'http://whuhmedical.qm.whuh.com:1099/';
let request_url= 'http://localhost:1099/';
// 统一配置
let FEBS_REQUEST = axios.create({
  //baseURL: 'http://whuhmedical.qm.whuh.com:1099/',
  baseURL: request_url,
  responseType: 'json',
  validateStatus(status) {
    // 200 外的状态码都认定为失败
    return status === 200
  }
})


let isRefreshing = false



// 观察者
let subscribers = [];
// 已经刷新了token，将所有队列中的请求进行重试
function onAccessTokenFetched(token) {
    subscribers.forEach((callback)=>{
        callback(token);
    })
    subscribers = [];
}
// 将resolve放进队列，用一个函数形式来保存，等token刷新后直接执行
function addSubscriber(callback) {
    subscribers.push(callback)
}
// 拦截请求
FEBS_REQUEST.interceptors.request.use((config) => {
  let expireTime = store.state.account.expireTime
  let now = moment().format('YYYYMMDDHHmmss')

  // 刷新token和登录 可以直接操作  不带token
  if (config.url.indexOf('/refreshToken') >= 0) {
    if (store.state.account.token && (now - expireTime)<=-60) {
      config.headers.Authentication = store.state.account.token
      return config;
    }
    
  }
  // 让token早10秒种过期，提升“请重新登录”弹窗体验
  if (now - expireTime >= -60) {
    Modal.error({
      title: '登录已过期',
      content: '很抱歉，登录已过期，请重新登录',
      okText: '重新登录',
      mask: false,
      onOk: () => {
        return new Promise((resolve, reject) => {
          db.clear()
          location.reload()
        }).catch(function (reason) {
          console.log('catch:', reason)
        })
      }
    })
    if (store.state.account.token) {
      config.headers.Authentication = store.state.account.token
    }
    return config
  }
 
  if (store.state.account.token) {
    console.info("时间：" + (now - expireTime))
    if ((now - expireTime >= -(10 * 6 * 20)) && (now - expireTime <= -60)) {
      if (!isRefreshing) {
        isRefreshing = true
        request.post('/refreshToken', {}).then(data2 => {
          let data =data2.data.data
          console.info(data.exipreTime);
          db.save('USER_TOKEN', data.token)
          store.state.account.token = data.token
          db.save('EXPIRE_TIME', data.exipreTime)
          store.state.account.expireTime = data.exipreTime
          // store.state.account.setExpireTime(data.exipreTime)
          config.headers.Authentication = data.token
      
          isRefreshing = false
          return config

        }).catch(res=>{

        }).finally(()=>{
          onAccessTokenFetched(store.state.account.token);
        })
      } else {
        const retryOriginalRequest = new Promise((resolve) => {
          addSubscriber((token)=> {
            config.headers.Authentication = token
            resolve(config)
          })
        });
        return retryOriginalRequest;
      }

    }
  }

  // 有 token就带上
  if (store.state.account.token) {
    config.headers.Authentication = store.state.account.token
  }
  return config
}, (error) => {
  return Promise.reject(error)
})

// 拦截响应
FEBS_REQUEST.interceptors.response.use((config) => {
  return config
}, (error) => {
  if (error.response) {
    let errorMessage = error.response.data === null ? '系统内部异常，请联系网站管理员' : error.response.data.message
    switch (error.response.status) {
      case 404:
        notification.error({
          message: '系统提示',
          description: '很抱歉，资源未找到',
          duration: 4
        })
        break
      case 403:
      case 401:
        notification.warn({
          message: '系统提示',
          description: '很抱歉，您无法访问该资源，可能是因为没有相应权限或者登录已失效',
          duration: 4
        })
        break
      default:
        notification.error({
          message: '系统提示',
          description: errorMessage,
          duration: 4
        })
        break
    }
  }
  return Promise.reject(error)
})
const request = {
 // baseURL: 'http://whuhmedical.qm.whuh.com:1099/',
  baseURL: request_url,
  post(url, params) {
    return FEBS_REQUEST.post(url, params, {
      transformRequest: [(params) => {
        let result = ''
        Object.keys(params).forEach((key) => {
          if (!Object.is(params[key], undefined) && !Object.is(params[key], null)) {
            result += encodeURIComponent(key) + '=' + encodeURIComponent(params[key]) + '&'
          }
        })
        return result
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
  },
  put(url, params) {
    return FEBS_REQUEST.put(url, params, {
      transformRequest: [(params) => {
        let result = ''
        Object.keys(params).forEach((key) => {
          if (!Object.is(params[key], undefined) && !Object.is(params[key], null)) {
            result += encodeURIComponent(key) + '=' + encodeURIComponent(params[key]) + '&'
          }
        })
        return result
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
  },
  get(url, params) {
    let _params
    if (Object.is(params, undefined)) {
      _params = ''
    } else {
      _params = '?'
      for (let key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null) {
          _params += `${key}=${params[key]}&`
        }
      }
    }
    return FEBS_REQUEST.get(`${url}${_params}`)
  },
  delete(url, params) {
    let _params
    if (Object.is(params, undefined)) {
      _params = ''
    } else {
      _params = '?'
      for (let key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null) {
          _params += `${key}=${params[key]}&`
        }
      }
    }
    return FEBS_REQUEST.delete(`${url}${_params}`)
  },
  export (url, params = {}) {
    message.loading('导出数据中')
    return FEBS_REQUEST.post(url, params, {
      transformRequest: [(params) => {
        let result = ''
        Object.keys(params).forEach((key) => {
          if (!Object.is(params[key], undefined) && !Object.is(params[key], null)) {
            result += encodeURIComponent(key) + '=' + encodeURIComponent(params[key]) + '&'
          }
        })
        return result
      }],
      responseType: 'blob',
      timeout: 90000
    }).then((r) => {
      const content = r.data
      const blob = new Blob([content])
      const fileName = `${new Date().getTime()}_导出结果.xlsx`
      if ('download' in document.createElement('a')) {
        const elink = document.createElement('a')
        elink.download = fileName
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href)
        document.body.removeChild(elink)
      } else {
        navigator.msSaveBlob(blob, fileName)
      }
    }).catch((r) => {
      console.error(r)
      message.error('导出失败')
    })
  },
  download(url, params, filename) {
    message.loading('文件传输中')
    return FEBS_REQUEST.post(url, params, {
      transformRequest: [(params) => {
        let result = ''
        Object.keys(params).forEach((key) => {
          if (!Object.is(params[key], undefined) && !Object.is(params[key], null)) {
            result += encodeURIComponent(key) + '=' + encodeURIComponent(params[key]) + '&'
          }
        })
        return result
      }],
      responseType: 'arraybuffer',
      timeout: 90000
    }).then((r) => {
      debugger
      const content = r.data
      const blob = new Blob([content])
      if ('download' in document.createElement('a')) {
        const elink = document.createElement('a')
        elink.download = filename
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href)
        document.body.removeChild(elink)
      } else {
        navigator.msSaveBlob(blob, filename)
      }
    }).catch((r) => {
      console.error(r)
      message.error('下载失败')
    })
  },
  upload(url, params) {
    return FEBS_REQUEST.post(url, params, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}

export default request
