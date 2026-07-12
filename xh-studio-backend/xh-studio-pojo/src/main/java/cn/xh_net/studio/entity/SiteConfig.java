package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 站点配置实体类
 * @author XingHai
 * @date 2026/7/12 16:25
 * @description 站点配置实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiteConfig implements Serializable {

    // 主键
    private Integer id;

    // 配置项
    private String configKey;

    // 配置值
    private String configValue;

    // 配置描述
    private String description;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
