package com.collegevol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 *
 * @since 2019-11-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Qrcode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "q_id", type = IdType.AUTO)
    private Integer qId;

    private Integer userId;

    private String qCodeUrl;


}
