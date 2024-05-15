package cn.bitoffer.lottery.service;

import cn.bitoffer.lottery.dto.UserInfoReq;
import cn.bitoffer.lottery.model.TUser;
import com.baomidou.mybatisplus.extension.service.IService;


/**
* @author 26010
* @description 针对表【t_user(用户表)】的数据库操作Service
* @createDate 2024-05-15 15:57:02
*/
public interface TUserService extends IService<TUser> {

    Integer updateOrInsertUserInfo(UserInfoReq userInfoReq);
}
