package com.collegevol.controller;


import com.collegevol.entity.UserInfo;
import com.collegevol.service.impl.QrcodeServiceImpl;
import com.collegevol.service.impl.UserInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 *
 * @since 2019-11-04
 */
@RestController
@RequestMapping("/api/qrcode")
public class QrcodeController {
    @Autowired
    private UserInfoServiceImpl userInfoService;

    @Autowired
    private QrcodeServiceImpl qrcodeService;

    @RequestMapping("/getUserCode")
    public String getUserCode(HttpSession session) throws Exception{
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        String qrCodeUrl=qrcodeService.getUserCode(userInfo);
        return qrCodeUrl;
    }
}
