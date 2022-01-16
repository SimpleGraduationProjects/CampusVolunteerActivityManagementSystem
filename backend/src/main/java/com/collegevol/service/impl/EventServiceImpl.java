package com.collegevol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.collegevol.dao.EventCommentMapper;
import com.collegevol.dao.EventLikeMapper;
import com.collegevol.dao.EventMapper;
import com.collegevol.dao.EventPictureMapper;
import com.collegevol.entity.Event;
import com.collegevol.entity.EventComment;
import com.collegevol.entity.EventLike;
import com.collegevol.entity.EventPicture;
import com.collegevol.service.EventService;
import com.collegevol.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 志愿者活动表  服务实现类
 * </p>
 *
 * @since 2019-07-20
 */
@Service
@Slf4j
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
    @Resource
    private EventMapper eventMapper;

    @Resource
    private EventCommentMapper eventCommentMapper;

    @Resource
    private EventLikeMapper eventLikeMapper;

    @Resource
    private EventPictureMapper eventPictureMapper;

    public IPage<EventVo> qryEventForPageIndex(Page page) {
        Page page1 = eventMapper.qryEventIngForPage(page);
        return page1;
    }

    public IPage<EventVo> qryEventForPageHistory(Page page) {
        Page page1 = eventMapper.qryEventEndForPage(page);
        return page1;
    }

    public EventVo qryEventById(EventVo vo) {
        return eventMapper.qryEventById(vo);
    }

    public Page qryEventByTitle(Page page, EventVo eventVo) {
        return page.setRecords(eventMapper.qryEventByTitle(page, eventVo));
    }

    public Page qryEventByUserId4Page(Page page, EventVo eventVo) {
        return page.setRecords(eventMapper.qryEventByUserId4Page(page, eventVo));
    }


    public Page qryEventUserInfoVoForPage(Page page) {
        return page.setRecords(eventMapper.qryEventUserInfoVoForPage(page));
    }

    public List<EventUserInfoVo> qryEventUserInfoVoByStatus(EventVo eventVo) {
        return eventMapper.qryEventUserInfoVoByStatus(eventVo);
    }


    public EventUserInfoVo qryEventUserInfoVoByEventId(EventVo eventVo) {
        return eventMapper.qryEventUserInfoVoByEventId(eventVo);
    }

    @Override
    public void addEvent(EventVo eventVo) {
        eventMapper.insert(eventVo);
    }

    public void updateStatus(EventVo eventVo) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.set("status", eventVo.getStatus());
        updateWrapper.set("event_summary", eventVo.getEventSummary());
        updateWrapper.eq("event_id", eventVo.getEventId());
        eventMapper.update(eventVo, updateWrapper);
    }


    /**
     * 定时任务
     */
    public void updateTask() {
        Event event = new Event();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        Date endTime = new Date();
        //7天后结束活动
        endTime = new Date(endTime.getTime() + 7 * 3600 * 24 * 1000);
        updateWrapper.le("end_time", endTime);
        updateWrapper.ne("status", EventCode.ENDED);
        updateWrapper.set("status", EventCode.TIMEOUT);
        eventMapper.update(event, updateWrapper);

        updateWrapper = new UpdateWrapper();
        updateWrapper.gt("start_time", endTime);
        updateWrapper.eq("status", EventCode.UNDER_REVIEW);
        updateWrapper.set("status", EventCode.TIMEOUT);
        eventMapper.update(event, updateWrapper);
    }


    public void addEventComment(EventComment eventCommentPo) {
        eventCommentMapper.insert(eventCommentPo);
    }

    public void addEventPictrue(EventPicture eventPicturePo) {
        eventPictureMapper.insert(eventPicturePo);
    }


    public EventVo qryEventMoreInfo(EventVo eventVo) {
        eventVo = eventMapper.qryEventById(eventVo);
        EventLike eventLikePo = new EventLike();
        eventLikePo.setEventId(eventVo.getEventId());
        List<EventLikeUserInfoVo> eventLikeList = eventLikeMapper.qryEventLikeUserInfoVoByEventId(eventVo.getEventId());
        EventComment commentPo = new EventComment();
        commentPo.setEventId(eventVo.getEventId());
        List<EventCommentUserInfoVo> commentPoList = eventCommentMapper.qryEventCommentUserInfoVoByEventId(eventVo.getEventId());
        QueryWrapper<EventPicture> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("event_id", eventVo.getEventId());
        List<EventPicture> eventPictureList = eventPictureMapper.selectList(queryWrapper3);
        eventVo.setComments(commentPoList);
        eventVo.setLikes(eventLikeList);
        eventVo.setLikeNum(eventLikeList.size());
        eventVo.setCommentNum(commentPoList.size());
        eventVo.setResultPictures(eventPictureList);
        return eventVo;
    }

    public List<EventVo> qryUserNeedCommentEvent(Integer userId) {
        return eventMapper.qryUserNeedCommentEvent(userId);

    }


    public List<EventVo> qryUserEvent(Integer userId) {
        List<EventVo> al = eventMapper.qryUSerEvent(userId);
        EventVo eventVo = new EventVo();
        eventVo.setUserId(userId);
        List<EventVo> alAdmin = eventMapper.qryEventByUserId(eventVo);
        for (EventVo vo : alAdmin) {
            vo.setAdmin(true);
        }
        for (EventVo vo : al) {
            vo.setAdmin(false);
        }
        alAdmin.addAll(al);
        EventVo []arr=new EventVo[alAdmin.size()];
        alAdmin.toArray(arr);
        Arrays.sort(arr);
        alAdmin=Arrays.asList(arr);
        return alAdmin;
    }

    public String qryEventUserPhone(Integer eventId) {
        return eventMapper.qryEventUserPhone(eventId);
    }


    public int qryEventVoByStatusBetween(String status, Date startDate, Date endDate) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        queryWrapper.gt("start_time", startDate);
        queryWrapper.lt("start_time", endDate);
        return eventMapper.selectCount(queryWrapper);
    }


    public List<Event> qryAllEventVos(String status) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        if (!status.equals("")) {
            queryWrapper.eq("status", status);
        }
        return eventMapper.selectList(queryWrapper);
    }


    public int qryEventVoBetween(Date startDate, Date endDate) {
        QueryWrapper<Event> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("start_time", startDate);
        queryWrapper.lt("start_time", endDate);
        return eventMapper.selectCount(queryWrapper);
    }
}
