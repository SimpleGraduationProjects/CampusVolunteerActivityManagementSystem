package com.collegevol.dao;

import com.collegevol.entity.EventLike;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.collegevol.vo.EventCommentUserInfoVo;
import com.collegevol.vo.EventLikeUserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-05
 */
public interface EventLikeMapper extends BaseMapper<EventLike> {

    @Select("SELECT user_info.stu_id,user_info.user_class,user_info.user_school,user_info.user_avator,user_info.user_name,user_info.user_id, event_like.create_time,event_like.event_id FROM user_info JOIN event_like ON user_info.user_id=event_like.user_id WHERE event_like.event_id=#{eventId}  AND event_like.status!='00X'")
    List<EventLikeUserInfoVo> qryEventLikeUserInfoVoByEventId(@Param("eventId") Integer eventId);
}
