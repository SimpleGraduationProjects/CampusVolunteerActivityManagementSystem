// pages/index/index.js
import { BASE_URL } from '../../utils/api.js';
Page({
  data: {
    current: 1,
    totalPage: 0,
    size: 5,
    activityList:[]
  },
  exit() {
    wx.removeStorageSync('cookie')
    wx.redirectTo({
      url: '/pages/login/login',
    })
  },
  actAssess:function(e){
    console.log(e.currentTarget.id)
    var eventId = e.currentTarget.id
    let actList = this.data.activityList
    let eventInfo
    for (let item of actList) {
      if(item.eventId == eventId) {
        eventInfo = item
      }
    }
    wx.navigateTo({
      url: '/pages/actInfo/actInfo?actInfo=' + JSON.stringify(eventInfo)
    })
  },
  getActList() {
    var that = this
    wx.request({
      url: BASE_URL + '/api/qryEventForPage/index',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      data: {
        current: that.data.current,
        size: that.data.size
      },
      success: (res) => {
        console.log("here act:",res.data)
        that.setData({
          activityList:res.data.records,
          totalPage: res.data.pages
        })
        console.log(res)
      }
    })  
  },
  changePage({ detail }) {
    var that = this
    const type = detail.type;
    if (type === 'next') {
      that.setData({
        current: that.data.current + 1
      });
    } else if (type === 'prev') {
      that.setData({
        current: that.data.current - 1
      });
    }
    that.getActList()
  },
  onLoad() {
    this.getActList()
  }
  

})