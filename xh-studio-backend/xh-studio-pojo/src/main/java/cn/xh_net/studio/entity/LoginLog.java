package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 登录日志实体类
 * @author XingHai
 * @date 2026/7/12 14:07
 * @description 登录日志实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog implements Serializable {

    // 主键
    private Long id;

    // 用户ID
    private Long userId;

    // 用户名
    private String username;

    // 登录时间
    private LocalDateTime loginTime;

    // 登录IP
    private String ip;

    // 用户浏览器
    private String userAgent;

    // 登录结果
    private String result;

    // 登录失败原因
    private String failReason;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
