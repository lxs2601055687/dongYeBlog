package cn.bitoffer.lottery.service.impl;

import cn.bitoffer.api.dto.lottery.UserInfoParam;
import cn.bitoffer.lottery.dto.UserInfoReq;
import cn.bitoffer.lottery.mapper.TUserMapper;


import cn.bitoffer.lottery.model.TUser;
import cn.bitoffer.lottery.service.TUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 李祥生
* @description 针对表【t_user(用户表)】的数据库操作Service实现
* @createDate 2024-05-15 15:57:02
*/
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser>
    implements TUserService {

    @Autowired
    private TUserMapper tUserMapper;
    @Override
    public Integer updateOrInsertUserInfo(UserInfoParam userInfoReq) {
        TUser tUser = new TUser();
        tUser.setId(userInfoReq.getId());
        tUser.setEmployeeId(userInfoReq.getEmployeeId());
        tUser.setUserName(userInfoReq.getUserName());
        LambdaQueryWrapper<TUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TUser::getEmployeeId, userInfoReq.getEmployeeId());
        TUser tUser1 = tUserMapper.selectOne(queryWrapper);
        if (tUser1 == null) {
            return tUserMapper.insert(tUser);
        }
        return tUserMapper.update(tUser, queryWrapper);
    }
}




