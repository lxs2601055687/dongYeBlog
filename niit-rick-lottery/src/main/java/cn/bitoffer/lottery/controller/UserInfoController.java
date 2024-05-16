package cn.bitoffer.lottery.controller;

import cn.bitoffer.api.dto.lottery.UserInfoParam;
import cn.bitoffer.lottery.service.TUserService;
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



    @PostMapping(value = "/userInfo/update", consumes = "application/json; charset=utf-8")
    public Integer updateOrInsertUserInfo(@RequestBody UserInfoParam userInfoReq) {
        return tUserService.updateOrInsertUserInfo(userInfoReq);
    }


}
