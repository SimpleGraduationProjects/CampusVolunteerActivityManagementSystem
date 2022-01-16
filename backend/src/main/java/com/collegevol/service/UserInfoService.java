package com.collegevol.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.collegevol.entity.UserInfo;
import org.springframework.dao.DataAccessException;

/**
 * <p>
 * 用户信息表  服务类
 * </p>
 *
 *
 * @since 2019-07-20
 */
public interface UserInfoService extends IService<UserInfo> {


    int login(UserInfo userInfo);

    int addUser(UserInfo userInfo) throws DataAccessException;

    void update(UserInfo userInfo);

    int count(String stuId);

    UserInfo qryUserByStuId(String stuId);

    Page askUserOnline(Page page);

    void kickUserByStuId(String stuId);

    UserInfo selectOne(String stuId);


    void updateBaseScoreTask();

}