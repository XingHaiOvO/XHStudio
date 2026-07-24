package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author XingHai
 * @date 2026/7/12 15:55
 * @description 项目实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project implements Serializable {

    // 主键
    private Long id;

    // 项目标题
    private String title;

    // 项目封面url
    private String coverUrl;

    // 项目状态（0：计划中，1：开发中，2：已完成）
    private Integer status;

    // 项目进度（0-100）
    private Integer progress;

    // 项目摘要
    private String summary;

    // 项目内容(富文本)
    private String content;

    // 开始时间
    private LocalDate startDate;

    // 项目结束时间
    private LocalDate endDate;

    // 项目url
    private String projectUrl;

    // 项目排序
    private Integer sortOrder;

    // 项目是否删除（0：未删除，1：已删除）
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
