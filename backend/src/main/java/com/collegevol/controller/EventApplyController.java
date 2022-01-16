package com.collegevol.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.collegevol.entity.*;
import com.collegevol.resolver.MultiRequestBody;
import com.collegevol.service.impl.EventApplyServiceImpl;
import com.collegevol.service.impl.EventServiceImpl;
import com.collegevol.service.impl.UserInfoServiceImpl;
import com.collegevol.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 活动申请表  前端控制器
 * </p>
 */
@Controller
@RequestMapping("/api")
@Slf4j
public class EventApplyController {

    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${image.url}")
    private String imageUrl;

    @Resource
    private EventApplyServiceImpl eventApplyService;

    @Resource
    private UserInfoServiceImpl userInfoService;

    @Resource
    private EventServiceImpl eventService;

    private Lock lock = new ReentrantLock();

    /**
     * 活动报名
     *
     * @param request
     * @param eventVo
     * @return
     */
    @RequestMapping("/eventApply")
    @ResponseBody
    public ReturnData eventApply(HttpServletRequest request, @RequestBody EventVo eventVo) {
        String stuId = (String) request.getSession().getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        if (userInfo.getUserScore() < VariableParam.MIN_SCORE) {
            return new ReturnData(StatusCode.FAIL, "用户诚信积分低于"
                    + VariableParam.MIN_SCORE + "分,不得报名", "");
        }
        lock.lock();
        EventVo event = eventService.qryEventById(eventVo);
        if (event.getUserId().equals(userInfo.getUserId())) {
            lock.unlock();
            return new ReturnData(StatusCode.FAIL, "你是这个活动的发布者，不需要报名", "");
        }
        if (event.getNum() == event.getMaxnum() && event.getMaxnum() != 0) {
            lock.unlock();
            return new ReturnData(StatusCode.FAIL, "报名人数达到最大上限"
                    + event.getMaxnum() + "人,不得报名", "");
        }
        List<EventApply> eventApplyList = eventApplyService.selectList(eventVo.getEventId());
        for (EventApply ea : eventApplyList) {
            if (ea.getUserId().equals(userInfo.getUserId())) {
                lock.unlock();
                return new ReturnData(StatusCode.FAIL, "已经报名，不可重复报名", "");
            }
        }
        EventApply eventApply = new EventApply();
        eventApply.setEventId(eventVo.getEventId());
        eventApply.setUserId(userInfo.getUserId());
        eventApply.setApplyStatus(EventApplyCode.APPYED);
        eventApply.setCreatedTime(new Date());
        eventApplyService.eventApply(eventApply);
        lock.unlock();
        return new ReturnData(StatusCode.SUCCESS, "报名成功", "");
    }

    /**
     * 活动审核报名
     *
     * @param eventVo
     * @param userInfo
     * @return
     */
    @RequestMapping("/checkEventApply")
    @ResponseBody
    public ReturnData checkEventApply(@MultiRequestBody EventVo eventVo,
                                      @MultiRequestBody UserInfo userInfo,
                                      @MultiRequestBody(value = "check") String check) {


        if (check.equals("true")) {
            ArrayList<UserInfo> userInfoArrayList = userInfoService.qryUsersInfoByStuId(userInfo);
            for (int i = 0; i < userInfoArrayList.size(); i++) {
                UpdateWrapper<EventApply> updateWrapper = new UpdateWrapper();
                updateWrapper.set("apply_status", EventApplyCode.APPYED);
                updateWrapper.eq("event_id", eventVo.getEventId());
                updateWrapper.eq("user_id", userInfoArrayList.get(i).getUserId());
                eventApplyService.update(updateWrapper);
            }
        } else {
            UpdateWrapper<EventApply> updateWrapper = new UpdateWrapper();
            userInfo = userInfoService.qryUserByStuId(userInfo.getStuId());
            if (userInfo == null) {
                return new ReturnData(StatusCode.FAIL, "用户stuId不存在", "");
            }
            updateWrapper.set("apply_status", EventApplyCode.APPY_FAIL);
            updateWrapper.eq("event_id", eventVo.getEventId());
            updateWrapper.eq("user_id", userInfo.getUserId());
            eventApplyService.update(updateWrapper);
        }

        return new ReturnData(StatusCode.SUCCESS, "修改成功", "");
    }

    @RequestMapping("/checkEventSignIn")
    @ResponseBody
    public ReturnData checkEventSignIn(@MultiRequestBody EventVo eventVo,
                                       @MultiRequestBody UserInfo userInfo,
                                       @MultiRequestBody(value = "check") String check,
                                       HttpSession session) {
        userInfo = userInfoService.qryUserByStuId(userInfo.getStuId());
        String stuId = (String) session.getAttribute("stuId");
        UserInfo admin = userInfoService.qryUserByStuId(stuId);
        if (userInfo == null) {
            return new ReturnData(StatusCode.FAIL, "用户stuId不存在", "");
        }
        if (eventVo.getEventId() == null) {
            return new ReturnData(StatusCode.FAIL, "活动eventId不存在", "");
        }
        eventVo = eventService.qryEventById(eventVo);
        if (!eventVo.getUserId().equals(admin.getUserId())) {
            return new ReturnData(StatusCode.FAIL, "当前用户不是管理员，不能为活动签到", "");
        }
        List<EventApply> al = eventApplyService.selectList(eventVo.getEventId());
        boolean flag = false;
        for (int i = 0; i < al.size(); i++) {
            EventApply ea = al.get(i);
            if (ea.getUserId().equals(userInfo.getUserId())) {
                if (ea.getApplyStatus().equals(EventApplyCode.APPYED)) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag == false) {
            return new ReturnData(StatusCode.FAIL, "当前用户未报名活动", "");
        }
        Date nowDate = new Date();
        if (nowDate.getTime() > eventVo.getEndTime().getTime() ||
                nowDate.getTime() < eventVo.getStartTime().getTime()) {
            return new ReturnData(StatusCode.FAIL, "时间不在活动范围内", "");
        }
        UpdateWrapper<EventApply> updateWrapper = new UpdateWrapper();
        if (check.equals("true")) {
            updateWrapper.set("apply_status", EventApplyCode.CHECKED_IN);
        } else {
            updateWrapper.set("apply_status", EventApplyCode.NOT_CHECK_IN);
        }
        updateWrapper.eq("event_id", eventVo.getEventId());
        updateWrapper.eq("user_id", userInfo.getUserId());
        eventApplyService.update(updateWrapper);
        return new ReturnData(StatusCode.SUCCESS, "修改成功", "");
    }


    @RequestMapping("/finishEvent")
    @ResponseBody
    public ReturnData finishEvent(@RequestParam(value = "files") MultipartFile[] files, String eventId, String eventSummary, HttpServletRequest request) {
        HttpSession session = request.getSession();
        EventVo eventVo = new EventVo();
        if (files.length == 0) {
            return new ReturnData(StatusCode.FAIL, "图片不能为空", "");
        }
        eventVo.setEventId(Integer.parseInt(eventId));
        eventVo.setEventSummary(eventSummary);
        Event event = eventService.qryEventById(eventVo);
        if (event.getStatus().equals(EventCode.ENDED)) {
            return new ReturnData(StatusCode.FAIL, "活动已经结束,请勿重复结束活动", "");
        }
        if (event.getStatus().equals(EventCode.TIMEOUT)) {
            return new ReturnData(StatusCode.FAIL, "活动已经过期,不能结束活动", "");
        }
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        if (!userInfo.getUserId().equals(event.getUserId())) {
            return new ReturnData(StatusCode.FAIL, "该用户不是活动发布者，不能结束活动",
                    "");
        }
        List<EventApply> eventApplyList = eventApplyService.selectList(eventVo.getEventId());
        for (EventApply eventApply : eventApplyList) {
            if (eventApply.getApplyStatus().equals(EventApplyCode.APPYED)) {
//                return new ReturnData(StatusCode.FAIL, "存在用户通过申请,但未指定是否签到，不能结束活动",
//                        "")
                eventApply.setApplyStatus(EventApplyCode.NOT_CHECK_IN);
            }
        }
        userInfo.setUserScore(userInfo.getUserScore() + event.getEventScore());
        userInfoService.update(userInfo);
        eventVo.setStatus(EventCode.ENDED);
        eventVo.setMaxnum(event.getMaxnum());
        eventService.updateStatus(eventVo);
        for (EventApply eventApply : eventApplyList) {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            if (eventApply.getApplyStatus().equals(EventApplyCode.NOT_CHECK_IN)) {
                UserInfo userInfo1 = userInfoService.selectOne(eventApply.getUserId());
                UserInfo ui = new UserInfo();
                ui.setUserScore(userInfo1.getUserScore() - event.getEventScore());
                updateWrapper.eq("user_id", eventApply.getUserId());
                userInfoService.update(ui, updateWrapper);
            } else if (eventApply.getApplyStatus().equals(EventApplyCode.CHECKED_IN)) {
                UserInfo userInfo1 = userInfoService.selectOne(eventApply.getUserId());
                UserInfo ui = new UserInfo();
                ui.setUserScore(userInfo1.getUserScore() + event.getEventScore());
                updateWrapper.eq("user_id", eventApply.getUserId());
                userInfoService.update(ui, updateWrapper);
            }
        }
        //处理上传的图片
        String URL = "";
        for (MultipartFile file : files) {
            File upload = new File(uploadDir + UUID.randomUUID().toString() + "." +
                    file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1));
            try {
                file.transferTo(upload);
                URL = imageUrl + upload.getName();
                EventPicture eventPicturePo = new EventPicture();
                eventPicturePo.setEventId(eventVo.getEventId());
                eventPicturePo.setImgUrl(URL);
                eventService.addEventPictrue(eventPicturePo);
            } catch (IOException e) {
                log.info("添加图片失败");
            }
        }
        return new ReturnData(StatusCode.SUCCESS, "成功结束活动", "");
    }


    @RequestMapping("/qryUserJoinEvent")
    @ResponseBody
    public List<EventApplyInfo> qryUserJoinEvent(@RequestBody EventVo eventVo) {
        List<EventApply> eventApplyList = eventApplyService.selectList(eventVo.getEventId());
        List<EventApplyInfo> eventApplyInfoList = new ArrayList<>();
        for (EventApply eventApply : eventApplyList) {
            UserInfo userInfo = userInfoService.selectOne(eventApply.getUserId());
            EventApplyInfo eventApplyInfo = new EventApplyInfo();
            eventApplyInfo.setApplyStatus(eventApply.getApplyStatus());
            eventApplyInfo.setUserId(userInfo.getUserId());
            eventApplyInfo.setEventId(eventApply.getEventId());
            eventApplyInfo.setCreatedTime(eventApply.getCreatedTime());
            eventApplyInfo.setStuId(userInfo.getStuId());
            eventApplyInfo.setUserName(userInfo.getUserName());
            eventApplyInfo.setUserClass(userInfo.getUserClass());
            eventApplyInfo.setUserSchool(userInfo.getUserSchool());
            eventApplyInfo.setUserAvator(userInfo.getUserAvator());
            eventApplyInfo.setUserScore(userInfo.getUserScore());
            eventApplyInfoList.add(eventApplyInfo);
        }
        return eventApplyInfoList;
    }

    /**
     * 查询需要评论的活动
     *
     * @param session
     * @return
     */
    @RequestMapping("/qryUserNeedCommentEvent")
    @ResponseBody
    public List<EventVo> qryUserNeedCommentEvent(HttpSession session) {
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        return eventService.qryUserNeedCommentEvent(userInfo.getUserId());
    }


    /**
     * 查询用户参加的活动
     *
     * @param session
     * @return
     */
    @RequestMapping("/qryUserEvent")
    @ResponseBody
    public List<EventVo> qryUserEvent(HttpSession session) {
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        return eventService.qryUserEvent(userInfo.getUserId());
    }
}
