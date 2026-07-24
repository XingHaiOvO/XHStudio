package cn.xh_net.studio.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 作品实体类
 * @author XingHai
 * @date 2026/7/12 16:35
 * @description 作品实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Work implements Serializable {

    // 主键
    private String id;

    // 用户id
    private Long userId;

    // 作品标题
    private String title;

    // 作品封面url
    private String coverUrl;

    // 作品描述
    private String description;

    // 作品链接
    private String link;

    // 作品标签
    private String tags;

    // 作品公开状态(0:不公开,1:公开)
    @TableField("is_public")
    private Integer publicStatus;

    // 作品删除状态(0:未删除,1:已删除)
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
