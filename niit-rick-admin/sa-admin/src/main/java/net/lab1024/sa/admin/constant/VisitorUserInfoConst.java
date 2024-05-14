package net.lab1024.sa.admin.constant;

import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuVO;

import java.util.Arrays;
import java.util.List;

import java.time.LocalDateTime;


/**
 *游客登录基本信息常量
 *
 */
public class VisitorUserInfoConst {
    public static final String USERNAME = "游客";

    public static final Long USERID = 1L;

    public static final String AVATAR = "";

    public static final String NICKNAME = "游客";

    public static final List<MenuVO> MENU_LIST = null;

    public static final String CAPTCHA_UUID = "visitor-login-uuid";;
}
