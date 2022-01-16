package com.collegevol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 活动标签表 
 * </p>
 *
 * @since 2019-07-20
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EventTag implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增长用户标签名
     */
    @TableId(value = "et_id", type = IdType.AUTO)
    private Integer etId;

    /**
     * 标签名
     */
    private String tagName;


}
