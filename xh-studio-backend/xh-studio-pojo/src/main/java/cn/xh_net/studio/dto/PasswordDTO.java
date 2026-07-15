package cn.xh_net.studio.dto;

import lombok.Data;

@Data
public class PasswordDTO {

    // 邮箱
    private String email;

    // 验证码
    private String code;

    // 新密码
    private String newPassword;

}
