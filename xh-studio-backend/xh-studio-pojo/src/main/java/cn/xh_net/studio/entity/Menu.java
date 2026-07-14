package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜单实体类
 * @author XingHai
 * @date 2026/7/12 14:51
 * @description 菜单实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // 主键
    private Long id;

    // 父菜单ID
    private Long parentId;

    // 菜单名称
    private String menuName;

    // 菜单路径
    private String path;

    // 菜单图标
    private String icon;

    // 排序
    private Integer sortOrder;

    // 菜单类型(0:目录,1:菜单)
    private Integer menuType;

    // 是否可见(0:可见,1:不可见)
    private Integer visible;

    // 是否删除(0:未删除,1:已删除)
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}