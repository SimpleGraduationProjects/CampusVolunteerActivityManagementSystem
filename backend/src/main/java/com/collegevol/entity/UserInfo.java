package com.collegevol.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 *
 * @since 2019-07-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 班级
     */
    private String userClass;

    /**
     * 学院
     */
    private String userSchool;

    /**
     * 用户头像 头像路径
     */
    private String userAvator;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户积分 100~0
     */
    private Integer userScore;

    /**
     * 用户密码 des加密数据(可逆加密)
     */
    private String password;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户身份
     */
    private Integer role;

    /**
     * 查询的sql
     */
    @TableField(exist = false)
    private String searchSql;

    private String userPhone;
}
