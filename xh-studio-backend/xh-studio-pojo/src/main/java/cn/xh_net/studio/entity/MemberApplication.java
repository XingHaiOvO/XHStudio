package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  @author XingHai
 *  @date 2026/7/12 14:44
 *  @description 成员申请实体类
 *  @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberApplication implements Serializable {

    // 主键
    private Long id;

    // 登录账号
    private String loginAccount;

    // 昵称
    private String nickname;

    // 邮箱
    private String email;

    // 手机号
    private String phone;

    // 个人介绍
    private String intro;

    // 申请编号
    private String applyNo;

    // 状态(0:待审核,1:已通过,2:已拒绝)
    private Integer status;

    // 拒绝原因
    private String rejectReason;

    // 处理人ID
    private Long processedId;

    // 处理时间
    private LocalDateTime processedTime;

    // 是否删除(0:未删除,1:已删除)
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
