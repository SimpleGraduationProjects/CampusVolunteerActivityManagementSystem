// pages/mine_myAct/mine_myAct.js
import { BASE_URL } from '../../utils/api.js';

Page({
  data: {
    mineAct: [],
    noCommentAct: [],
    activityTab: true,
    noCommentTab: false,
    currentTab: 'activity',
    current_scroll: 'activity'
  },
  onLoad: function (options) {
    this.getList()
  },
  getList() {
    var that = this
    that.setData({
      commentTab: true
    })
    wx.request({
      url: BASE_URL + '/api/qryUserEvent',
      method: 'get',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      success: (res) => {
        that.setData({
          mineAct: res.data,
          // totalPage: res.data.pages
        })
      }
    })
    wx.request({
      url: BASE_URL + '/api/qryUserNeedCommentEvent',
      method: 'get',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      success: (res) => {
        console.log("res", res)
        that.setData({
          noCommentAct: res.data,
          // totalPage: res.data.pages
        })
      }
    }) 
  },
  handleTabChange({ detail }) {
    var key = detail.key
    this.setData({
      currentTab: detail.key
    })
    if (key === "activity") {
      this.setData({
        activityTab: true,
        noCommentTab: false
      })
    } else if (key === "noCommentActivity") {
      this.setData({
        activityTab: false,
        noCommentTab: true
      })
    }
    // this.setData({
    //   currentTab: detail.key
    // })
  },
  gotoComment(e) {
    console.log(e.target.id)
    var eventId = e.target.id
    let actList = this.data.noCommentAct
    let eventInfo
    for (let item of actList) {
      if (item.eventId == eventId) {
        eventInfo = item
      }
    }
    console.log("event:", eventInfo)
    wx.navigateTo({
      url: '/pages/mine_myAct_comment/mine_myAct_comment?actInfo=' + JSON.stringify(eventInfo)
    })
  },

  scanCode: function (e) {
    let eventId = e.target.id
    wx.scanCode({
      onlyFromCamera: true,
      success(res) {
        console.log("扫描得学号：", res)
        console.log("扫描得学号：", res.result.split("stuId=")[1])
        let stu = res.result.split("stuId=")[1]
        wx.request({
          url: BASE_URL + '/api/checkEventSignIn',
          method: 'POST',
          header: {
            'content-type': 'application/json',
            'cookie': wx.getStorageSync('cookie') //请求带cookie
          },
          data: {
            stuId: stu,
            eventId: eventId,
            check: "true"
          },
          success: (res) => {
            if(res.statusCode == 200) {
              console.log("签到验证成功后:", res)
              wx.showToast({
                title: res.data.msg,
                icon: 'success',
                duration: 2000
              })
            } else {
              wx.showToast({
                title: res.data.msg,
                icon: 'error',
                duration: 2000
              })
            }
          },
          fail: (res)=>{
            wx.showToast({
              title: res.msg,
              icon: 'none',
              duration: 2000
            })
          }
        })
      }
    })
  },
})