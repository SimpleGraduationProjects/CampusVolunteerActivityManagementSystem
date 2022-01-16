package com.collegevol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.collegevol.entity.Qrcode;
import com.collegevol.dao.QrcodeMapper;
import com.collegevol.entity.UserInfo;
import com.collegevol.service.QRCodeUserService;
import com.collegevol.service.QrcodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 * @since 2019-11-04
 */
@Service
public class QrcodeServiceImpl extends ServiceImpl<QrcodeMapper, Qrcode> implements QrcodeService {

    private final static String PREFIX_KEY="http://127.0.0.1:5000/#/login?";

    @Resource
    private QRCodeUserServiceImpl qrCodeUserService;


    @Resource
    private QrcodeMapper qrcodeMapper;


    public String getUserCode(UserInfo userInfo){
        QueryWrapper<Qrcode> qrcodeQueryWrapper=new QueryWrapper<>();
        qrcodeQueryWrapper.eq("user_id",userInfo.getUserId());
        Qrcode qrcode=qrcodeMapper.selectOne(qrcodeQueryWrapper);
        if(qrcode!=null){
            return qrcode.getQCodeUrl();
        }else {
            String qrCodeUrl="";
            try {
                qrCodeUrl=qrCodeUserService.generateCodeImg(PREFIX_KEY+"stuId="+userInfo.getStuId());
                Qrcode qrcodeRow=new Qrcode();
                qrcodeRow.setUserId(userInfo.getUserId());
                qrcodeRow.setQCodeUrl(qrCodeUrl);
                qrcodeMapper.insert(qrcodeRow);
            } catch (Exception e) {
                log.error(e.toString());
                log.error("qrcode generate error");
            }
            return qrCodeUrl;
        }
    }



}
