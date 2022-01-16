import { BASE_URL } from '../../utils/api.js';

Page({
  data:{
    userId:'',
    userPwd:'',
    role:''
  },
  getUserId:function(e){
    this.data.userId = e.detail.value;
  },
  getUserPwd:function(e){
    this.data.userPwd=e.detail.value;
  },
  getUserRole:function(e){
    this.data.role = e.detail.value;
  },
  login:function(e){
    var username = this.data.userId;
    var password = this.data.userPwd;
    var role = this.data.role;
    // if(role == 1) {
    //   wx.showToast({
    //     title: '管理员请通过后台登录',
    //     icon: 'none',
    //     duration: 2000
    //   })
    //   return
    // }
    wx.request({
      url: BASE_URL + '/api/user/login',
      method: 'POST',
      header: {
        'content-type': 'application/json',
        'cookie' : wx.getStorageSync('cookie') //请求带cookie
      },
      data: {
        stuId: username,
        password: password,
        role: parseInt(role)
      },
      success: function (res) {
        var data=res.data;
        console.log(data);
        if(data.statusCode === 200){
          wx.setStorageSync('cookie', data.data)
          wx.setStorageSync('role', role)
          wx.showToast({
            title: data.msg,
            icon: 'success',
            duration: 2000
          })
          wx.redirectTo({
            url: '/pages/index/index',
          })
        }else{
          wx.showToast({
            title: data.msg,
            icon:'none',
            duration: 2000
          })
        }        
      },
      fail: function(e) {
        wx.showToast({
          title: e,
          icon: 'none',
          duration: 2000
        })
      }
    })
  },
  register:function(){
    wx.navigateTo({
      url: '/pages/register/register',
    })
  }
})