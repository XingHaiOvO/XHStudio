package cn.xh_net.studio.dto;

import lombok.Data;

/**
 * 用户登录信息DTO
 * @author XingHai
 * @date 2026/7/13 17:28
 * @description 用于接收用户登录信息
 */
@Data
public class UserDTO {

    // 用户名
    private String username;

    // 密码
    private String password;

    // 登录类型
    private String type;

}
