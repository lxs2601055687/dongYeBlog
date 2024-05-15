package cn.bitoffer.lottery.controller;

import cn.bitoffer.lottery.dto.UserInfoReq;
import cn.bitoffer.lottery.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/lottery")
public class UserInfoController {
    @Resource
    private TUserService tUserService;



    @PostMapping("/userInfo/update")
    public Integer updateOrInsertUserInfo(@RequestBody UserInfoReq userInfoReq) {


        return tUserService.updateOrInsertUserInfo(userInfoReq);
    }


}
