// pages/index.js
import { BASE_URL } from '../../utils/api.js';
Page({
  data: {
    stuId: '',
    name: '',
    yuan: '',
    userClass: '',
    pwd1: '',
    pwd2: '',
    userPhone: '',
    /*stuIdTip:'学号应为9位数',*/
    pwd1Len: '6',
    isSame: false,
    stuIdLen: '9'
  },
  stuIdInput: function (e) {
    this.data.stuId = e.detail.value
  },
  stuIdBlur: function () {
    this.setData({
      stuIdLen: this.data.stuId.length
    })
  },
  userName(e) {
    this.data.userName = e.detail.value
  },
  userSchool(e) {
    this.data.userSchool = e.detail.value
  },
  userClass(e) {
    this.data.userClass = e.detail.value
  },
  userPhone(e) {
    this.data.userPhone = e.detail.value
  },
  pwd1Input: function (e) {
    this.data.pwd1 = e.detail.value;
  },
  pwd1Blur: function () {
    this.setData({
      pwd1Len: this.data.pwd1.length
    })
  },
  pwd2Input: function (e) {
    this.data.pwd2 = e.detail.value;
  },
  pwd2Blur: function () {
    console.log(this.data.pwd1 == this.data.pwd2)
    if (this.data.pwd1 == this.data.pwd2) {
      this.setData({
        isSame: false
      })
    } else {
      this.setData({
        isSame: true
      })
    }
  },
  register() {
    var that = this
    console.log(that.data.stuId, that.data.userName, that.data.userClass, that.data.userSchool, that.data.pwd1, that.data.pwd2, that.data.userPhone)
    if (!that.data.stuId || !that.data.userName || !that.data.userClass || !that.data.userSchool || !that.data.pwd1 || !that.data.pwd2 || !that.data.userPhone) {
      wx.showModal({
        title: '注册失败',
        content: '信息填写不完整',
        success(res) {
          if (res.confirm) {
            return
          } else if (res.cancel) {
            return
          }
        }
      })
    }
    else if (that.data.pwd1 != that.data.pwd2) {
      that.setData({
        isSame: true
      })
      return
    }
    else {
      console.log("123");
      //获得用户头像放入数据库
          //获得微信头像更新后端
          wx.request({
            url: BASE_URL + '/api/user/register',
            method: 'POST',
            header: {
              'content-type': 'application/json',
              'cookie': wx.getStorageSync('cookie') //请求带cookie
            },
            data: {
              stuId: that.data.stuId,
              userName: that.data.userName,
              userClass: that.data.userClass,
              userPhone: that.data.userPhone,
              userSchool: that.data.userSchool,
              password: that.data.pwd1,
              role:0
             
            },
            success: (res) => {
              console.log(res)
              if (res.data.statusCode === 200) {
                wx.showModal({
                  content: res.data.msg,
                  success(res) {
                    if (res.confirm) {
                      wx.redirectTo({
                        url: '/pages/login/login',
                      })
                    } else if (res.cancel) {
                      wx.redirectTo({
                        url: '/pages/login/login',
                      })
                    }
                  }
                })
              }
              else if (res.data.statusCode === 500) {
                wx.showModal({
                  content: res.data.msg,
                  success(res) {
                    if (res.confirm) {
                      return
                    } else if (res.cancel) {
                      return
                    }
                  }
                })
              }
            }
          })
    }
  }
})