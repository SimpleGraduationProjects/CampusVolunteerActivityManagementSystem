package com.collegevol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 志愿者活动表 
 * </p>
 *
 *
 * @since 2019-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 活动id
     */
    @TableId(value = "event_id", type = IdType.AUTO)
    private Integer eventId;

    /**
     * 活动的图片链接
     */
    private String eventImgUrl;

    /**
     * 活动开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动地点
     */
    private String location;

    /**
     * 发起人
     */
    private Integer userId;

    /**
     * 活动介绍
     */
    private String description;

    /**
     * 活动结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 积分
     */
    private Integer eventScore;

    /**
     * 举办方
     */
    private String supplyName;

    /**
     * 状态 报名中/进行中/已结束
     */
    private String status;

    private Integer maxnum;

    private String tags;

    private String eventSummary;
}
