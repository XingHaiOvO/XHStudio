package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色实体类
 * @author XingHai
 * @date 2026/7/12 16:21
 * @description 角色实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {

    // 主键
    private Integer id;

    // 角色名称
    private String roleName;

    // 角色编码
    private String roleCode;

    // 角色描述
    private String description;

    // 删除状态（0：未删除，1：已删除）
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
