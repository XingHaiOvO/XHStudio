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
    private Integer id;

    // 项目ID
    private Integer projectId;

    // 成员ID
    private Integer userId;

    // 成员角色（0：普通成员，1：项目负责人）
    private Integer roleInProject;

    // 成员是否删除（0：未删除，1：已删除）
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
