package com.collegevol.entity;

import lombok.Data;

@Data
public class EventApplyInfo extends EventApply{
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

    private String stuId;
}
