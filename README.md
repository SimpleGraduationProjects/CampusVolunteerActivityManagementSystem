
# 83.CampusVolunteerActivityManagementSystem

<p>群: 123300273(大佬群 2TB学习资料,讲解)(入群获取sql文件)</p>
<p>QQ: 1095737364(加好友获取sql文件)</p>

<p><h1 align="center">83.校园志愿者活动管理系统</h1></p>


<p align="center">
	<img src="https://img.shields.io/badge/jdk-1.8-orange.svg"/>
    <img src="https://img.shields.io/badge/springboot-5.x-lightgrey.svg"/>
    <img src="https://img.shields.io/badge/vue-3.x-blue.svg"/>
    <img src="https://img.shields.io/badge/小程序-3.x-yellow.svg"/>
</p>

## 简介


> 本代码来源于网络,仅供学习参考使用,请入群(123300273)后联系群主索要sql文件!



## 环境

- <b>IntelliJ IDEA 2009.3</b>

- <b>Mysql 5.7.26</b>

- <b>Tomcat 7.0.73</b>

- <b>JDK 1.8</b>

- <b>maven 3.2.5 </b>

- <b>redis </b>




## 缩略图

![](https://img2020.cnblogs.com/blog/588112/202201/588112-20220116224144715-161084384.png)
![](https://img2020.cnblogs.com/blog/588112/202201/588112-20220116224152749-1093417007.png)
![](https://img2020.cnblogs.com/blog/588112/202201/588112-20220116224158033-1608695951.png)
![](https://img2020.cnblogs.com/blog/588112/202201/588112-20220116224204005-1236958852.png)
![](https://img2020.cnblogs.com/blog/588112/202201/588112-20220116224210887-2008742754.png)
![](https://img2020.cnblogs.com/blog/588112/202201/588112-20220116224218643-2052575123.png)
![](https://img2020.cnblogs.com/blog/588112/202201/588112-20220116224223946-1880777389.png)








#### 【大学生志愿者小程序】

|  小程序模块    |  文件路径    |
| ---- | ---- |
|  小程序后端    |   /backend   |
|  小程序前端    |  /frontend    |
|  web前端    |  /vol-web    |

项目包含有完整的志愿者在小程序端报名活动，管理员在web端审核通过活动，志愿者通过二维码扫码报名签到的功能。图片建议放在   /home/cov/images/  路径下，

二维码图片路径是/home/cov/images/qrcode/，

这样通过  http://{公网IP}:8888/images/ xxx.jpg 就可以访问到图片



#### 【涉及技术】

前端    iview + vue + vuex + axios

后端    springBoot + redis + Mysql



#### 【附件】

|  小程序文件模块    |  文件路径    |
| ---- | ---- |
| 1、数据库设计表     |  /志愿者数据库设计v1.0.doc    |
| 2、小程序Rest接口详细说明 | /志愿者小程序后端接口说明.md   |
|  3、小程序开发文档---开发环境规约和说明    | /志愿者小程序开发文档.docx     |
| 4、数据库脚本 | /covdb.sql |



#### 【参考文档】

ivew  http://v1.iviewui.com/docs/guide/install

小程序  https://developers.weixin.qq.com/miniprogram/dev/framework/



#### 【使用方法】

##### 1、启动后端

##### 【安装后端环境】

后端需要java8，mysql,redis 环境，使用maven管理后端项目



##### 【启动后端项目】

用idea打开backend，maven加载好后，启动项目，后端占用8888端口；


##### 2、启动前端

##### 【启动web后台】

web端进入vol-web，使用如下指令启动前端

需要node.js

```bash
npm install
npm run serve 
```

##### 【启动小程序前端】

将frontend文件夹导入微信小程序开发者工具


## License

##### [个人站点: 全栈九九六(Java全栈知识资料下载)](https://www.blog996.com/)
##### [个人博客: 博客园精品博客](https://www.cnblogs.com/yysbolg/)
##### [更多论文: 精品论文查看](https://www.cnblogs.com/yysbolg/category/1886262.html)
##### [更多论文: 全目录查看](https://www.blog996.com/md/2021-09-22-1632317852192.html)


