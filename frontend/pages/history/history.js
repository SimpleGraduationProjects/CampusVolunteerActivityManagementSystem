// pages/history/history.js
import { BASE_URL } from '../../utils/api.js';
Page({
  data: {
    current: 1,
    totalPage: 0,
    size: 5,
    imgUrls: [],
    indicatorDots: true,
    autoplay: true,
    interval: 3000,
    duration: 1000,
    circular:true,
    historyActList:[]
  },
  getActList() {
    var that = this
    wx.request({
      url: BASE_URL + '/api/qryEventForPage/history',
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
        that.setData({
          historyActList: res.data.records,
          totalPage: res.data.pages
        })
      }
    })  
  },
  getImg() {
    var that = this
    wx.request({
      url: BASE_URL + '/api/picture/getPictureTopSize/3',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie': wx.getStorageSync('cookie') //请求带cookie
      },
      success: (res) => {
        console.log("heredata0:", res)
        let imgUrl = []
        res.data.records.forEach(item=>{
          imgUrl.push(item.imgUrl)
        })
        // console.log("imgUrls:", imgUrl)
        that.setData({
          imgUrls: imgUrl,
        })
      }
    })
  },
  historyAct:function(e) {
    // console.log(e.currentTarget.id)
    var eventId = e.currentTarget.id
    // let actList = this.data.historyActList
    // let eventInfo
    // for (let item of actList) {
    //   if (item.eventId == eventId) {
    //     eventInfo = item
    //   }
    // }
    // console.log("event:", eventInfo)
    wx.navigateTo({
      url: '/pages/historyActInfo/historyActInfo?eventId=' + eventId,
    })
  },
  changePage({ detail }) {
    const type = detail.type;
    if (type === 'next') {
      this.setData({
        current: this.data.current + 1
      });
    } else if (type === 'prev') {
      this.setData({
        current: this.data.current - 1
      });
    }
    this.getActList()
  },
  onLoad() {
    this.getActList()
    this.getImg()
  }
})