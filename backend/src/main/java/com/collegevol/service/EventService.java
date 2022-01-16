package com.collegevol.service;

import com.collegevol.entity.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import com.collegevol.vo.EventVo;

/**
 * <p>
 * 志愿者活动表  服务类
 * </p>
 *
 *
 * @since 2019-07-20
 */
public interface EventService extends IService<Event> {

    void addEvent(EventVo eventVo);
}
