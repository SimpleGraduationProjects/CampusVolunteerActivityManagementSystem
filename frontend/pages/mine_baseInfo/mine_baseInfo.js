// pages/mine_baseInfo/mine_baseInfo.js
import { BASE_URL } from '../../utils/api.js';
Page({
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: BASE_URL + '/api/user/qryUser',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      success: (res) => {
        console.log("heredata4:", res.data)
        that.setData({
          stuId: res.data.stuId,
          userName: res.data.userName,
          userSchool: res.data.userSchool,
          userClass: res.data.userClass
        })
      }
    })
  },
  data: {
    stuId: '',
    userName: '',
    userSchool: '',
    userClass: '',
    snoLen: ''
  },
  snoInput: function (e) {
    this.data.stuId = e.detail.value;
  },
  // snoBlur: function () {
  //   this.setData({
  //     snoLen: this.data.sno.length
  //   })
  // },
  schoolInput: function (e) {
    this.data.userSchool = e.detail.value;
  },
  classInput: function (e) {
    this.data.userClass = e.detail.value;
  },
  nameInput: function (e) {
    this.data.userName = e.detail.value;
  },
  saveChange:function(e){
    console.log('savechange')
    let that = this
    if(that.data.stuId.length !== 9) {
      wx.showModal({
        title: '信息填写错误',
        content: '学号应为9位数数字',
        success(res) {
          if (res.confirm) {
            console.log('用户点击确定')
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    } else if(!that.data.stuId || !that.data.userClass || !that.data.userName || !that.data.userSchool) {
      console.log(that.data.stuId)
      console.log(that.data.userClass)
      console.log(that.data.userName)
      console.log(that.data.userSchool)
      wx.showModal({
        title: '信息填写不完整',
        // content: '',
        success(res) {
          if (res.confirm) {
            console.log('用户点击确定')
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    } else {
      wx.request({
        url: BASE_URL + '/api/user/update',
        method: 'POST',
        header: {
          'content-type': 'application/json',
          'cookie' : wx.getStorageSync('cookie') //请求带cookie
        },
        data: {
          stuId: that.data.stuId,
          // password: that.data.password,
          // role: parseInt(wx.getStorgeSync('role')),
          userName: that.data.userName,
          userClass: that.data.userClass,
          userSchool: that.data.userSchool
        },
        success: (res) => {
          console.log(res)
          if(res.statusCode === 200) {
            wx.showToast({
              title: res.data.msg,
              icon: 'success',
              duration: 1000
            })
            wx.switchTab({
              url: '/pages/mine/mine'
            })
          } else {
            wx.showToast({
              title: res.data.msg,
              icon: 'error',
              duration: 1000
            })
          }
        }
      })
    }
  }   
})