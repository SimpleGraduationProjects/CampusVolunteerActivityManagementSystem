// pages/historyActInfo/historyActInfo.js
import { BASE_URL } from '../../utils/api.js';

Page({
  onLoad(options) {
    // let historyInfo = JSON.parse(options.historyInfo)
    var that = this
    let eventId = options.eventId
    eventId=parseInt(eventId)
    wx.request({
      url: BASE_URL + '/api/qryEventInfo',
      method: 'post',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      data: {
        eventId: eventId
      },
      success: (res) => {
        console.log("这呢",res.data)
        that.setData({
          commentTab: true,
          history: res.data,
          tags: res.data.tags.split(',')
          // likeNum: historyInfo.likeNum
        })
      }
    })
    // this.setData({
    //   commentTab: true,
    //   history: historyInfo,
    //   likeNum: historyInfo.likeNum
    // })
    if (that.data.history.commentNum === 0) {
      that.setData({
        comment_pic: '/image/comment0.jpg'
      })
    } else {
      that.setData({
        comment_pic: '/image/comment1.png'
      })
    }
  },
  data: {
    tags: [],
    history: {},
    zan_pic: '/image/like1.png',
    comment_pic: '/image/comment1.png',
    currentTab: 'comment',
    current_scroll: 'comment'
  },
  clickZan() {
    // if (this.data.zan_pic === '/image/like1.png') {
    //   this.setData({
    //     zan_pic: '/image/like2.png'
    //   })
    // } else {
    //   this.setData({
    //     zan_pic: '/image/like1.png'
    //   })
    // }
    var that = this
    wx.request({
      url: BASE_URL + '/api/event/like',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      data: {
        eventId: that.data.history.eventId
      },
      success: (res) => {
        if (res.data.data.islike === true) {
          this.setData({
            zan_pic: '/image/like2.png'
          })
        } else {
          this.setData({
            zan_pic: '/image/like1.png'
          })
        }
        that.setData({
          // ["history.likeNum"]: res.data.data.likeNum
          history: res.data.data
        })
        wx.showToast({
          title: res.data.msg,
          icon: 'none',
          duration: 1000
        })
      }
    })  
  },
  handleTabChange({ detail }) {
    var key = detail.key
    this.setData({
      currentTab: detail.key
    })
    if (key === "comment") {
      this.setData({
        commentTab: true,
        zanTab: false
      })
    } else if (key === "zan") {
      this.setData({
        commentTab: false,
        zanTab: true
      })
    }
  }
})