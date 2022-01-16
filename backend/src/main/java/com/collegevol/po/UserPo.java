package com.collegevol.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_info")
@Data
public class UserPo {
    @TableField("user_id")
    String userId;

    @TableField("user_class")
    String userClass;

    @TableField("user_school")
    String userSchool;

    @TableField("user_name")
    String userName;

    @TableField("stu_id")
    String stuId;
}
