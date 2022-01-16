package com.collegevol.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.collegevol.dao.UserInfoMapper;
import com.collegevol.entity.UserInfo;
import com.collegevol.service.UserInfoService;
import com.collegevol.utils.MD5Utils;
import com.collegevol.utils.RedisUtils;
import com.collegevol.vo.VariableParam;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户信息表  服务实现类
 * </p>
 *
 * @since 2019-07-20
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public int login(UserInfo userInfo) {
        String password = MD5Utils.convert(userInfo.getPassword());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", userInfo.getStuId());
        queryWrapper.eq("password", password);
        UserInfo res = userInfoMapper.selectOne(queryWrapper);
        if (res != null) {
            if (res.getRole() >= userInfo.getRole()) {
                return 1;
            }
            return 2;
        }
        return 0;
    }

    @Override
    public int addUser(UserInfo userInfo) throws DataAccessException {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public void update(UserInfo userInfo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("stu_id", userInfo.getStuId());
        userInfoMapper.update(userInfo, queryWrapper);
    }


    @Override
    public int count(String stuId) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", stuId);
        return userInfoMapper.selectCount(queryWrapper);
    }

    @Override
    public UserInfo qryUserByStuId(String stuId) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stu_id", stuId);
        return userInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public Page askUserOnline(Page page) {
        Collection<String> stuIdList = RedisUtils.getTemplate().opsForHash().keys(VariableParam.LOGIN_HASH);
        String searchArray = "";
        for (String stuId : stuIdList) {
            searchArray += "'" + stuId + "',";
        }
        searchArray = searchArray.substring(0, searchArray.length() - 1);
        UserInfo userInfo = new UserInfo();
        userInfo.setSearchSql(searchArray);
        List<UserInfo> userInfoList = userInfoMapper.qryUserInfoByStuIdForPage(page, userInfo);
        for (UserInfo info : userInfoList) {
            info.setPassword(null);
        }
        return page.setRecords(userInfoList);
    }

    @Override
    public void kickUserByStuId(String stuId) {
        RedisUtils.delete(VariableParam.LOGIN_HASH, stuId);
    }

    @Override
    public UserInfo selectOne(String stuId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("stu_id", stuId);
        return userInfoMapper.selectOne(queryWrapper);
    }

    public UserInfo selectOne(Integer userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return userInfoMapper.selectOne(queryWrapper);
    }


    public ArrayList<UserInfo> qryUsersInfoByStuId(UserInfo userInfo) {
        Page page = new Page();
        page.setSize(100);
        page.setCurrent(1);
        return userInfoMapper.qryUserInfoByUserIdForPage(page, userInfo);
    }


    public IPage qryAllUser(Page page) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        return userInfoMapper.selectPage(page,queryWrapper);
    }

    @Override
    public void updateBaseScoreTask() {
        userInfoMapper.updateUserScore(VariableParam.MIN_SCORE);
    }
}
