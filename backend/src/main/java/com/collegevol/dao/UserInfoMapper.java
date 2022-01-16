package com.collegevol.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.collegevol.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

/**
 * <p>
 * 用户信息表  Mapper 接口
 * </p>
 *
 *
 * @since 2019-07-20
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    @Select("SELECT * FROM `user_info` WHERE  user_id IN (${userInfo.searchSql})")
    ArrayList<UserInfo> qryUserInfoByUserIdForPage(Page page, @Param("userInfo") UserInfo userInfo);

    @Select("SELECT * FROM `user_info` WHERE  stu_id IN (${userInfo.searchSql})")
    ArrayList<UserInfo> qryUserInfoByStuIdForPage(Page page, @Param("userInfo") UserInfo userInfo);

    @Update("UPDATE user_info SET user_score=${baseScore} WHERE user_score < ${baseScore}")
    void updateUserScore(@Param("baseScore") Integer baseScore);


}
