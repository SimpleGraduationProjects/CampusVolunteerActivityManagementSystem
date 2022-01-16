package com.collegevol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.collegevol.entity.EventApply;
import com.collegevol.dao.EventApplyMapper;
import com.collegevol.service.EventApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.collegevol.vo.EventVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 * 活动申请表  服务实现类
 * </p>
 *
 * @since 2019-07-20
 */
@Service
public class EventApplyServiceImpl extends ServiceImpl<EventApplyMapper, EventApply> implements EventApplyService {

    @Resource
    private EventApplyMapper eventApplyMapper;

    public void eventApply(EventApply eventApply){
        eventApplyMapper.insert(eventApply);
    }


    public List<EventApply> selectList(Integer eventId){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("event_id",eventId);
        return eventApplyMapper.selectList(queryWrapper);
    }



}
