package com.collegevol.dao;

import com.collegevol.entity.EventComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.collegevol.vo.EventCommentUserInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 活动评价表  Mapper 接口
 * </p>
 *
 *
 * @since 2019-09-05
 */
public interface EventCommentMapper extends BaseMapper<EventComment> {


    @Select("SELECT user_info.stu_id,user_info.user_class,user_info.user_school,user_info.user_avator,user_info.user_name, event_comment.`comment`,event_comment.created_time,event_comment.event_id,event_comment.rate FROM `user_info` \n" +
            "JOIN event_comment ON `user_info`.user_id=event_comment.user_id WHERE `event_comment`.event_id=#{eventId}")
   List<EventCommentUserInfoVo> qryEventCommentUserInfoVoByEventId(@Param("eventId") Integer eventId);
}
