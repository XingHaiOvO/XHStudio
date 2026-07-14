package cn.xh_net.studio.vo;

import cn.xh_net.studio.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginVO {

    // 登录令牌
    private String token;

    // 令牌类型
    private String tokenType;

    // 过期时间
    private Long expiresIn;

    // 用户信息
    private User userInfo;

}
