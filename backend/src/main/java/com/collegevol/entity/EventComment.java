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
 * 活动评价表 
 * </p>
 *
 *
 * @since 2019-09-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ec_id
     */
    @TableId(value = "ec_id", type = IdType.AUTO)
    private Integer ecId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 评分
     */
    private String rate;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    private Integer eventId;


}
