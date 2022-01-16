// pages/mine/mine.js
//sort.js
//獲取應用實例
import { BASE_URL } from '../../utils/api.js';
var app = getApp
Page({
  data: {
    qrCodeSrc: '',
    myCodeDisplay: 'none',
    //用户个人信息
    userInfo: {
      avatarUrl: "",//用户头像
      nickName: "",//用户昵称
    },
    loading:true
  },
  add_address_fun: function () {
    wx.navigateTo({
      url: 'add_address/add_address',
    })
  },
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
          ["userInfo.avatarUrl"]: res.data.userAvator,
          ["userInfo.nickName"]: res.data.userName,
          ["userInfo.userScore"]: res.data.userScore,
          loading: false
        })
      }
    })  
    this.getMyCode()
  },
  baseInfo:function(){
    wx.navigateTo({
      url: '/pages/mine_baseInfo/mine_baseInfo',
    })
  },
  safeCenter:function(){
    wx.navigateTo({
      url: '/pages/mine_safeCenter/mine_safeCenter',
    })
  },
  myAct: function () {
    wx.navigateTo({
      url: '/pages/mine_myAct/mine_myAct',
    })
  },
  aboutUs: function () {
    wx.navigateTo({
      url: '/pages/mine_aboutUs/mine_aboutUs',
    })
  },
  scanButton: function () {
    if (this.data.isClickScan == false) {
      this.setData({display: 'block'})
      this.setData({ isClickScan: true})
    } else {
      this.setData({ display: 'none' })
      this.setData({ isClickScan: false })
    }
  },
  getMyCode: function () {
    wx.request({
      url: BASE_URL + '/api/qrcode/getUserCode',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      success: (res) => {
        this.setData({
          qrCodeSrc: res.data
        })
      },
      fail: (res) => {
        console.log(res)
      }
    })
  },
  myCode: function() {
    if(this.data.myCodeDisplay == 'none') {
      this.setData({ myCodeDisplay: 'block' })
    } else {
      this.setData({ myCodeDisplay: 'none' })
    }
  }
})