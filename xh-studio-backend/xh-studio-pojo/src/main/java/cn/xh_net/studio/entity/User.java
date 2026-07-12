package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * @author XingHai
 * @date 2026/7/12 16:30
 * @description 用户实体类
 * /
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    // 主键
    private String id;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 昵称
    private String nickname;

    // 头像url
    private String  avatar;

    // 手机号
    private String phone;

    // 邮箱
    private String email;

    // QQ号
    private String qq;

    // 角色id
    private Long roleId;

    // 状态(0:启用,1:禁用)
    private Integer status;

    // 加入年份
    private Long joinYear;

    // 个人介绍
    private String intro;

    // 荣誉勋章
    private String honorBadge;

    // 是否删除(0:未删除,1:已删除)
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
