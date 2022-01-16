package com.collegevol.vo;

import com.collegevol.entity.EventComment;
import lombok.Data;

@Data
public class EventCommentUserInfoVo extends EventComment {
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
