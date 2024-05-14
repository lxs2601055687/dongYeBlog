package net.lab1024.sa.admin.module.system.login.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import net.lab1024.sa.admin.module.system.menu.domain.vo.MenuVO;
import net.lab1024.sa.base.common.domain.RequestUser;
import net.lab1024.sa.base.common.enumeration.UserTypeEnum;
import net.lab1024.sa.base.common.swagger.SchemaEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * c端user对象
 * @Author niit：李祥生
 * @Date 2021-12-15 21:05:46
 */
@Data
public class CUserVO implements RequestUser {

    @Schema(description = "c端用户Id")
    private Long cUserId;
    @Schema(description = "c端用户名称")
    private String cUserName;

    @Schema(description = "token")
    private String token;

    @Schema(description = "菜单列表")
    private List<MenuVO> menuList;

    @Schema(description = "上次登录ip地区")
    private String lastLoginIpRegion;

    @Schema(description = "上次登录user-agent")
    private String lastLoginUserAgent;

    @Schema(description = "上次登录时间")
    private LocalDateTime lastLoginTime;

    @Schema(description = "头像")
    private  String avatar;

    @Schema(description = "昵称")
    private  String nickname;

    @SchemaEnum(UserTypeEnum.class)
    private UserTypeEnum userType;

    @Schema(description = "请求ip")
    private String ip;

    @Schema(description = "请求user-agent")
    private String userAgent;

    @Override
    public Long getUserId() {
        return cUserId;
    }

    @Override
    public String getUserName() {
        return cUserName;
    }

}
