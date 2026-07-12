package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 友情链接实体类
 * @author XingHai
 * @date 2026/7/12 12:44
 * @description 友情链接实体类
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendLink implements Serializable {

    // 主键
    private Long id;

    // 友情链接名称
    private String name;

    // 友情链接URL
    private String url;

    // 友情链接logo
    private String logo;

    // 排序
    private Integer sortOrder;

    // 是否启用
    private Integer enabled;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
