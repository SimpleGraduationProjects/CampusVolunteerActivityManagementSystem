package com.collegevol.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.collegevol.entity.EventComment;
import com.collegevol.entity.EventLike;
import com.collegevol.entity.UserInfo;
import com.collegevol.resolver.MultiRequestBody;
import com.collegevol.service.impl.EventLikeServiceImpl;
import com.collegevol.service.impl.EventServiceImpl;
import com.collegevol.service.impl.UserInfoServiceImpl;
import com.collegevol.vo.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 志愿者活动表  前端控制器
 * </p>
 */
@RestController
@RequestMapping("/api")
public class EventController {

    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${image.url}")
    private String imageUrl;

    @Resource
    private EventServiceImpl eventService;

    @Resource
    private UserInfoServiceImpl userInfoService;

    @Resource
    private EventLikeServiceImpl eventLikeService;

    @RequestMapping("/qryEventForPage/{status}")
    public IPage<EventVo> qryEventForPageIndex(@RequestBody Page page, @PathVariable("status") String status) {
        if (status.equals("index")) {
            return eventService.qryEventForPageIndex(page);
        } else if (status.equals("history")) {
            return eventService.qryEventForPageHistory(page);
        } else if (status.equals("admin")) {
            EventVo eventVo = new EventVo();
            eventVo.setTitle("");
            return eventService.qryEventByTitle(page, eventVo);
        }
        return eventService.qryEventForPageIndex(page);
    }

    @RequestMapping("/qryEventById")
    public EventUserInfoVo qryEventById(@RequestBody EventVo eventVo) {
        return eventService.qryEventUserInfoVoByEventId(eventVo);
    }

    @RequestMapping("/qryEventByTitleForPage")
    public Page qryEventByTitleForPage(@MultiRequestBody Page page, @MultiRequestBody EventVo eventVo) {
        return eventService.qryEventByTitle(page, eventVo);
    }

    @RequestMapping("/qryEventByUserIdForPage")
    public Page qryEventByUserId4Page(@MultiRequestBody Page page, @MultiRequestBody EventVo eventVo, HttpServletRequest request) {
        String stuId = (String) request.getSession().getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        eventVo.setUserId(userInfo.getUserId());
        return eventService.qryEventByUserId4Page(page, eventVo);
    }


    @RequestMapping("/addEvent")
    @ResponseBody
    public ReturnData addEvent(@MultiRequestBody EventVo eventVo,
                               @MultiRequestBody MultipartFile file, HttpSession session) throws Exception {
        File upload = new File(uploadDir + UUID.randomUUID().toString() + "." +
                file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1));
        file.transferTo(upload);
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.selectOne(stuId);
        if (userInfo.getUserScore() < VariableParam.MIN_SCORE) {
            return new ReturnData(StatusCode.FAIL, "用户诚信积分低于"
                    + VariableParam.MIN_SCORE + "分,不得报名", "");
        }
        eventVo.setUserId(userInfo.getUserId());
        eventVo.setEventImgUrl(imageUrl + upload.getName());
        eventVo.setStatus(EventCode.UNDER_REVIEW);
        eventService.addEvent(eventVo);
        return new ReturnData(StatusCode.SUCCESS, "添加成功", "");
    }


    @RequestMapping("/updateEventStatus")
    public ReturnData updateEvent(@RequestBody EventVo eventVo, HttpSession session) {
        eventService.updateStatus(eventVo);
        return new ReturnData(StatusCode.SUCCESS, "修改成功", "");
    }


    @RequestMapping("/comment")
    public ReturnData comment(@RequestBody EventComment eventComment, HttpSession session) {
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        eventComment.setUserId(userInfo.getUserId());
        eventComment.setCreatedTime(new Date());
        eventService.addEventComment(eventComment);
        EventVo eventVo = new EventVo();
        eventVo.setEventId(eventComment.getEventId());
        eventVo = eventService.qryEventMoreInfo(eventVo);
        return new ReturnData(StatusCode.SUCCESS, "评价成功", eventVo);
    }

    /**
     * 查询当前用户是否喜爱本活动
     *
     * @param eventVo
     * @param session
     * @return
     */
    @RequestMapping("/qryEventInfo")
    public EventVo qryEventInfo(@RequestBody EventVo eventVo, HttpSession session) {
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        EventVo eventVo1 = eventService.qryEventMoreInfo(eventVo);
        for (EventLikeUserInfoVo eventLikeUserInfoVo : eventVo1.getLikes()) {
            if (eventLikeUserInfoVo.getUserId().equals(userInfo.getUserId())) {
                eventVo1.setIslike(true);
                break;
            }
        }
        return eventVo1;
    }


    @RequestMapping("/event/like")
    public ReturnData like(@RequestBody EventLike eventLike, HttpSession session) {
        String stuId = (String) session.getAttribute("stuId");
        UserInfo userInfo = userInfoService.qryUserByStuId(stuId);
        eventLike.setUserId(userInfo.getUserId());
        eventLike.setStatus("00A");
        eventLike.setCreateTime(new Date());
        QueryWrapper<EventLike> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("event_id", eventLike.getEventId());
        queryWrapper.eq("user_id", userInfo.getUserId());
        List<EventLike> eventLikeList = eventLikeService.list(queryWrapper);
        if (eventLikeList == null || eventLikeList.size() == 0) {
            eventLike.setStatus("00A");
            eventLikeService.save(eventLike);
            EventVo eventVo = new EventVo();
            eventVo.setEventId(eventLike.getEventId());
            eventVo = eventService.qryEventMoreInfo(eventVo);
            return new ReturnData(StatusCode.SUCCESS, "我觉得很赞", eventVo);
        } else {
            UpdateWrapper<EventLike> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("event_id", eventLike.getEventId());
            updateWrapper.eq("user_id", userInfo.getUserId());
            if (eventLikeList.get(0).getStatus().equals("00A")) {
                updateWrapper.set("status", "00X");
                eventLikeService.update(updateWrapper);
                EventVo eventVo = new EventVo();
                eventVo.setEventId(eventLike.getEventId());
                eventVo = eventService.qryEventMoreInfo(eventVo);
                eventVo.setIslike(false);
                return new ReturnData(StatusCode.SUCCESS, "取消赞", eventVo);
            } else {
                updateWrapper.set("status", "00A");
                eventLikeService.update(updateWrapper);
                EventVo eventVo = new EventVo();
                eventVo.setEventId(eventLike.getEventId());
                eventVo = eventService.qryEventMoreInfo(eventVo);
                eventVo.setIslike(true);
                return new ReturnData(StatusCode.SUCCESS, "我觉得很赞", eventVo);
            }
        }

    }

    @RequestMapping("/qryEventUserPhone")
    public String qryEventUserPhone(@RequestBody EventVo eventVo) {
        return eventService.qryEventUserPhone(eventVo.getEventId());
    }

}
