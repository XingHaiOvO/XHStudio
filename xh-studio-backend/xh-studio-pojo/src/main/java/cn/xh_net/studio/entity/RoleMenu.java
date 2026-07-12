package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色菜单关联实体类
 * @author XingHai
 * @date 2026/7/12 16:23
 * @description 角色菜单关联实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenu implements Serializable {

    // 主键
    private Integer id;

    // 角色ID
    private Integer roleId;

    // 菜单ID
    private Integer menuId;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
