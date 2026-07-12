package cn.xh_net.studio.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 成长经历实体类
 * @author XingHai
 * @date 2026/7/12 14:02
 * @description 成长经历实体类
 * @version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GrowthExperience implements Serializable {

    // 主键
    private Long id;

    // 用户ID
    private Long userId;

    // 发生日期
    private LocalDate happenDate;

    // 标题
    private String title;

    // 内容(富文本)
    private String content;

    // 图片URL
    private String imageUrl;

    // 排序
    private Integer sortOrder;

    // 是否公开(0:否,1:公开)
    @TableField("is_public")
    private Integer publicStatus;

    // 删除状态(0:未删除,1:已删除)
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
