// pages/actAssess/actAssess.js
import { BASE_URL } from '../../utils/api.js';

Page({
  data: {
    actInfo: []
  },
  onLoad: function (options) {
    console.log(JSON.parse(options.actInfo))
    let actInfo = JSON.parse(options.actInfo)
    this.setData({
      actInfo:actInfo
    })
  },
  signUp:function(){
    var that = this
    wx.showModal({
      title: '提示',
      content: '确认报名此次活动？',
      success(res) {
        if (res.confirm) {
          wx.request({
            url: BASE_URL + '/api/eventApply',
            method: 'POST',
            header: {
              'content-type': 'application/json',
              'cookie': wx.getStorageSync('cookie') //请求带cookie
            },
            data: {
              eventId: that.data.actInfo.eventId
            },
            success: (res) => {
              console.log(res.data)
              if (res.data.statusCode === 200) {
                wx.showToast({
                  title: res.data.msg,
                  icon: 'success',
                  duration: 1500
                })
              } else {
                wx.showToast({
                  title: res.data.msg,
                  icon: 'none',
                  duration: 1500
                })
              }
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  },
  callUs() {
    var that = this
    wx.request({
      url: BASE_URL + '/api/qryEventUserPhone',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      data: {
        eventId: that.data.actInfo.eventId
      },
      success: (res) => {
          wx.makePhoneCall({
            phoneNumber: res.data.toString()
          })
      }
    })
  }
})