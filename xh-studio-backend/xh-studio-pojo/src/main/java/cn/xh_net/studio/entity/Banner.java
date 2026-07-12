package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 轮播图实体类
 * @author XingHai
 * @date 2026/7/12 12:42
 * @description 轮播图实体类
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banner implements Serializable {

    // 主键
    private Long id;

    // 轮播图图片URL
    private String imageUrl;

    // 轮播图链接URL
    private String linkUrl;

    // 排序
    private Integer sortOrder;

    // 是否启用
    private Integer enabled;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
