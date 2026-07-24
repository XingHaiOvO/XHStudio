package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 项目成员实体类
 * @author XingHai
 * @date 2026/7/12 16:02
 * @description 项目成员实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMember  implements Serializable {

    // 主键
    private Long id;

    // 项目ID
    private Long projectId;

    // 成员ID
    private Long userId;

    // 成员角色（PARTICIPANT：普通成员，OWNER：项目负责人）
    private String roleInProject;

    // 成员是否删除（0：未删除，1：已删除）
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

    // 成员昵称
    private String userNickname;

    // 成员头像url
    private String userAvatar;
}
