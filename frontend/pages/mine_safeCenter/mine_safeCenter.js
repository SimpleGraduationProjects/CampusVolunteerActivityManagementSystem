// pages/mine_safeCenter/mine_safeCenter.js
import { BASE_URL } from '../../utils/api.js';
Page({
  data: {
    oldPwd: '',
    newPwd: '',
    confirmNewPwd: '',
  },
  oldPwdInput(e) {
    this.setData({
      oldPwd: e.detail.value
    })
  },
  newPwdInput(e) {
    this.setData({
      newPwd: e.detail.value
    })
  },
  confirmNewPwdInput(e) {
    this.setData({
      confirmNewPwd: e.detail.value
    })
  },
  saveChange(e) {
    if(this.data.newPwd !== this.data.confirmNewPwd) {
      wx.showModal({
        title: '两次密码输入不一致',
        // content: '学号应为9位数数字',
        success(res) {
          if (res.confirm) {
            console.log('用户点击确定')
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
      // wx.showToast({
      //   title: '两次密码输入不一致',
      //   icon: 'none',
      //   duration: 1500
      // })
      this.setData({
        newPwd: '',
        confirmNewPwd: ''
      })
      return
    }
    var that = this
    wx.request({
      url: BASE_URL + '/api/user/update',
      method: 'post',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      data: {
        password: that.data.newPwd
      },
      success: (res) => {
        wx.showToast({
          title: '密码修改成功',
          icon: 'success',
          duration: 1500
        })
        wx.navigateBack({
          delta: 1
        })
      }
    })
  }
})