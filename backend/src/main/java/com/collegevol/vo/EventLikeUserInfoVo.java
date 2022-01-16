package com.collegevol.vo;

import com.collegevol.entity.EventLike;
import lombok.Data;

@Data
public class EventLikeUserInfoVo extends EventLike {
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

}
