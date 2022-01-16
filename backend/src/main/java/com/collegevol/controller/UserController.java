package com.collegevol.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.collegevol.entity.UserInfo;
import com.collegevol.service.impl.UserInfoServiceImpl;
import com.collegevol.utils.MD5Utils;
import com.collegevol.utils.RedisUtils;
import com.collegevol.vo.ReturnData;
import com.collegevol.vo.StatusCode;
import com.collegevol.vo.VariableParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/user")
public class UserController {
    @Value("${upload.dir}")
    private String uploadDir;

    @Resource
    private UserInfoServiceImpl userInfoService;

    @RequestMapping("/login")
    @ResponseBody
    public ReturnData login(@RequestBody UserInfo user, HttpServletRequest request, HttpServletResponse response) {
        int res = userInfoService.login(user);
        if (res != 0) {
            if (res == 1) {
                HttpSession session = request.getSession();
                RedisUtils.set(VariableParam.LOGIN_HASH,
                        user.getStuId(), session.getId());
                ReturnData returnData = new ReturnData(StatusCode.SUCCESS, "登录成功",
                        "sessionId=" + session.getId());
                Cookie cookie=new Cookie("sessionId", session.getId());
                cookie.setPath("/");
                cookie.setMaxAge(3600);
                response.addCookie(cookie);
                return returnData;
            } else {
                return new ReturnData(StatusCode.FAIL, "当前用户不是管理员", "");
            }
        }
        ReturnData returnData = new ReturnData(StatusCode.FAIL, "用户不存在或密码不正确",
                "");
        return returnData;
    }

    private static void downloadPicture(String urlList, String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping("/register")
    @ResponseBody
    public ReturnData register(@RequestBody UserInfo userInfo) {
        ReturnData returnData = null;
        if (userInfoService.count(userInfo.getStuId()) != 0) {
            returnData = new ReturnData(StatusCode.FAIL,
                    "学号已存在", "");
        } else {
            returnData = new ReturnData(StatusCode.SUCCESS, "注册成功", "");
            if (userInfo.getUserAvator() != null) {
                String urlList = userInfo.getUserAvator();
                String path = uploadDir + UUID.randomUUID().toString() + ".jpg";
                downloadPicture(urlList, path);
                userInfo.setCreateTime(new Date());
                String password = MD5Utils.convert(userInfo.getPassword());
                userInfo.setPassword(password);
                userInfo.setUserScore(VariableParam.BASE_SCORE);
            } else {
                userInfo.setCreateTime(new Date());
                String password = MD5Utils.convert(userInfo.getPassword());
                userInfo.setPassword(password);
                userInfo.setUserScore(VariableParam.BASE_SCORE);
            }
            userInfoService.addUser(userInfo);
        }
        return returnData;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ReturnData update(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        String stuId = (String) request.getSession().getAttribute("stuId");
        if (userInfo.getPassword() != null) {
            String password = MD5Utils.convert(userInfo.getPassword());
            userInfo.setPassword(password);
        }
        userInfo.setStuId(stuId);
        userInfoService.update(userInfo);
        return new ReturnData(StatusCode.SUCCESS, "修改成功", "");
    }

    @RequestMapping("/qryUser")
    @ResponseBody
    public UserInfo qryUser(HttpServletRequest request) {
        String stuId = (String) request.getSession().getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        return userInfo;
    }

    /**
     * 查询redis上已登录的用户
     *
     * @param page
     * @return
     */
    @RequestMapping("/askUserOnline")
    @ResponseBody
    public Page askUserOnline(@RequestBody Page page) {
        return userInfoService.askUserOnline(page);
    }

    /**
     * 踢用户下线
     *
     * @param userInfo
     * @return
     */
    @RequestMapping("/kickUser")
    @ResponseBody
    public ReturnData kickUser(@RequestBody UserInfo userInfo) {
        if (userInfo.getStuId() == null) {
            return new ReturnData(StatusCode.FAIL, "学生stuId参数为空", "学生stuId参数为空");
        }
        userInfoService.kickUserByStuId(userInfo.getStuId());
        return new ReturnData(StatusCode.SUCCESS, "用户已下线", "");
    }


    @RequestMapping("/qryAllUser")
    @ResponseBody
    public IPage<UserInfo> qryAllUser(@RequestBody Page page){
        return userInfoService.qryAllUser(page);
    }
}
