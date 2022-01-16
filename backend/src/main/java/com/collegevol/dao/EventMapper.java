package com.collegevol.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.collegevol.entity.Event;
import com.collegevol.vo.EventUserInfoVo;
import com.collegevol.vo.EventVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 志愿者活动表  Mapper 接口
 * </p>
 *
 *
 * @since 2019-07-20
 */
public interface EventMapper extends BaseMapper<Event> {

    /**
     * 用户初始化查询
     *
     * @param page
     * @return
     */
    @Select("SELECT a.event_id, a.event_img_url,  \n" +
            "                                       a.start_time,  \n" +
            "                                       a.end_time,  \n" +
            "                                       a.user_id,  \n" +
            "                                       a.title,  \n" +
            "                                       a.location,  \n" +
            "                                       a.description,  \n" +
            "                                       a.event_score,  \n" +
            "                                       a.supply_name,  \n" +
            "                                       a.status,a.tags,  \n" +
            "                                       maxnum,\n" +
            "(SELECT event_picture.img_url FROM event_picture WHERE event_picture.event_id=a.event_id LIMIT 1) as firstImgUrl, \n" +
            "                                       (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id) as num, \n" +
            "            (SELECT count(1) FROM event_like c WHERE a.event_id = c.event_id and  status ='00A') as likeNum \n" +
            "                                FROM `event` a  \n" +
            "                                where a.status ='审核中' \n" +
            "                                ORDER BY a.start_time DESC,a.event_id DESC")
    Page<EventVo> qryEventIngForPage(Page page);

    @Select("SELECT a.event_id, a.event_img_url,  \n" +
            "                                       a.start_time,  \n" +
            "                                       a.end_time,  \n" +
            "                                       a.user_id,  \n" +
            "                                       a.title,  \n" +
            "                                       a.location,  \n" +
            "                                       a.description,  \n" +
            "                                       a.event_score,  \n" +
            "                                       a.supply_name,  \n" +
            "                                       a.status,a.tags,  \n" +
            "                                       maxnum,\n" +
            "(SELECT event_picture.img_url FROM event_picture WHERE event_picture.event_id=a.event_id LIMIT 1) as firstImgUrl, \n" +
            "                                       (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id) as num, \n" +
            "            (SELECT count(1) FROM event_like c WHERE a.event_id = c.event_id and  status ='00A') as likeNum \n" +
            "                                FROM `event` a  \n" +
            "                                where a.status ='已结束' \n" +
            "                                ORDER BY a.start_time DESC,a.event_id DESC")
    Page<EventVo> qryEventEndForPage(Page page);



    @Select("SELECT a.event_id,\n" +
            "                                            a.event_img_url,\n" +
            "                                            a.start_time,\n" +
            "                                            a.end_time,\n" +
            "                                            a.user_id,\n" +
            "                                            a.title,\n" +
            "                                            a.location,\n" +
            "                                            a.description,\n" +
            "                                            a.event_score,\n" +
            "                                            a.supply_name,\n" +
            "                                            a.tags,\n" +
            "                                            a.status,\n" +
            "                                            a.event_summary,\n" +
            "                                            maxnum,\n" +
            "                                            (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id and b.apply_status!='报名未通过') as num\n" +
            "                                            FROM `event` a\n" +
            "                                                    where a.event_id = #{event.eventId}")
    EventVo qryEventById(@Param("event") EventVo eventVo);

    //查询当前用户申请的活动
    @Select("SELECT a.event_id,\n" +
            "        a.event_img_url,\n" +
            "        a.start_time,\n" +
            "        a.end_time,\n" +
            "        a.user_id,\n" +
            "        a.title,\n" +
            "        a.location,\n" +
            "        a.description,\n" +
            "        a.event_score,\n" +
            "        a.supply_name,\n" +
            "        a.status,\n" +
            "        maxnum,\n" +
            "        (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id) as num\n" +
            "        FROM `event` a\n" +
            "             where a.user_id = #{event.userId}")
    ArrayList<EventVo> qryEventByUserId4Page(Page page, @Param("event") EventVo eventVo);


    @Select("SELECT a.event_id,\n" +
            "                           a.event_img_url,\n" +
            "                           a.start_time,\n" +
            "                           a.end_time,\n" +
            "                           a.user_id,\n" +
            "                           a.title,\n" +
            "                           a.location,\n" +
            "                           a.description,\n" +
            "                           a.event_score,\n" +
            "                           a.supply_name,\n" +
            "                           a.status,\n" +
            "                           maxnum,\n" +
            "                           (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id) as num\n" +
            "                    FROM `event` a\n" +
            "                    where a.title like CONCAT('%',  #{event.title}, '%') ORDER BY a.start_time DESC,a.event_id DESC")
    ArrayList<EventVo> qryEventByTitle(Page page, @Param("event") EventVo eventVo);


    @Select("SELECT a.event_id,a.event_img_url,a.start_time, \n" +
            "                      a.end_time, \n" +
            "                      a.user_id,u.user_name,\n" +
            "                      a.title, \n" +
            "                      a.location, \n" +
            "                      a.description, \n" +
            "                      a.event_score, \n" +
            "                      a.supply_name, \n" +
            "                      a.status, \n" +
            "                      maxnum, \n" +
            "                      (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id) as num \n" +
            "                      FROM `event` a JOIN user_info u on u.user_id=a.user_id where a.status=#{event.status}")
    ArrayList<EventUserInfoVo> qryEventUserInfoVoByStatus(@Param("event") EventVo eventVo);

    @Select("SELECT a.event_id,a.event_img_url,a.start_time, \n" +
            "                      a.end_time, \n" +
            "                      a.user_id,u.user_name,\n" +
            "                      a.title, \n" +
            "                      a.location, \n" +
            "                      a.description, \n" +
            "                      a.event_score, \n" +
            "                      a.supply_name, \n" +
            "                      a.status, \n" +
            "                      maxnum, \n" +
            "                      (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id) as num \n" +
            "                      FROM `event` a JOIN user_info u on u.user_id=a.user_id")
    ArrayList<EventUserInfoVo> qryEventUserInfoVoForPage(Page page);


    EventUserInfoVo qryEventUserInfoVoByEventId(@Param("event") EventVo eventVo);



    @Select("SELECT *,\n" +
            "    (SELECT count(1) FROM event_comment b WHERE b.event_id=a.event_id) as commentNum,\n" +
            "(SELECT count(1) FROM event_like c WHERE c.event_id=a.event_id AND c.`status`='00A') as likeNum\n" +
            "    FROM event a\n" +
            "    WHERE a.`status`='已结束' AND a.event_id IN (\n" +
            "            SELECT event_id FROM event_apply WHERE event_apply.user_id =#{userId}\n" +
            "    and event_apply.event_id not in(\n" +
            "            SELECT event_comment.event_id FROM event_comment WHERE event_comment.event_id AND event_comment.user_id=#{userId}))")
    List<EventVo> qryUserNeedCommentEvent(@Param("userId") Integer userId);



    @Select("SELECT *, (SELECT count(1) FROM event_comment b WHERE b.event_id=a.event_id) as commentNum, \n" +
            "            (SELECT count(1) FROM event_like c WHERE c.event_id=a.event_id AND c.`status`='00A') as likeNum \n" +
            "                FROM event a \n" +
            "                WHERE a.event_id IN ( \n" +
            "                        SELECT event_id FROM event_apply WHERE event_apply.user_id =#{userId})")
    List<EventVo> qryUSerEvent(@Param("userId") Integer userId);




    @Select("SELECT user_info.user_phone FROM user_info JOIN `event` ON `event`.user_id=user_info.user_id WHERE `event`.event_id=#{eventId}")
    String qryEventUserPhone(@Param("eventId") Integer eventId);


    @Select("SELECT a.event_id,\n" +
            "        a.event_img_url,\n" +
            "        a.start_time,\n" +
            "        a.end_time,\n" +
            "        a.user_id,\n" +
            "        a.title,\n" +
            "        a.location,\n" +
            "        a.description,\n" +
            "        a.event_score,\n" +
            "        a.supply_name,\n" +
            "        a.status,\n" +
            "        maxnum,\n" +
            "        (SELECT count(1) FROM event_apply b WHERE a.event_id = b.event_id) as num\n" +
            "        FROM `event` a\n" +
            "             where a.user_id = #{event.userId}")
    ArrayList<EventVo> qryEventByUserId(@Param("event") EventVo eventVo);
}
