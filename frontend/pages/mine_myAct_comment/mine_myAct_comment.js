// pages/mine_myAct_comment/mine_myAct_comment.js
import { BASE_URL } from '../../utils/api.js';
import { $Message }  from '../../dist/base/index';
Page({
  data: {
    actInfo: {},
    grade: [{
      id: 1,
      name: '非常满意',
    }, {
      id: 2, 
      name: '比较满意'
    }, {
      id: 3,
      name: '比较不满意'
    }, {
      id: 4,
      name: '非常不满意',
    }],
    currentGrade: '',
    position: 'left',
    evaluateTxt: ''
  },
  onLoad(options) {
    this.setData({
      actInfo: JSON.parse(options.actInfo)
    })
    console.log("actInfo",this.data.actInfo)
  },
  handleGradeChange({ detail = {} }) {
    this.setData({
      currentGrade: detail.value
    })
    console.log("detail.value", detail.value)
  },
  goback() {
    wx.navigateBack({
      delta: 1
    })
  },
  bindTextAreainput(e) {
    this.setData({
      evaluateTxt: e.detail.value,
    })
    console.log(e.detail.value)
  },
  publish() {
    if (this.data.currentGrade == '' || this.data.evaluateTxt == '') {
      console.log("this.data.currentGrade:", this.data.currentGrade)
      console.log("this.data.evaluateTxt:", this.data.evaluateTxt)
      wx.showToast({
        title: '满意度或内容不能为空!',
        icon: 'none',
        duration: 1500
      })
      return
    }
    var that = this
    wx.request({
      url: BASE_URL + '/api/comment',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      data: {
        eventId: this.data.actInfo.eventId,  //活动的唯一id
        comment: this.data.evaluateTxt,
        rate: this.data.currentGrade
      },
      success: (res) => {
        wx.showToast({
          title: res.data.msg,
          icon: 'success',
          duration: 1500
        })
        let pages = getCurrentPages();   //获取小程序页面栈
        let beforePage = pages[pages.length - 2];  //获取上个页面的实例对象
        // beforePage.setData({      //直接修改上个页面的数据（可通过这种方式直接传递参数）
        //   txt: '修改数据了'
        // })
        beforePage.getList();   //触发上个页面自定义的go_update方法
        wx.navigateBack({
          delta: 1
        })
      }
    })  
  }
})