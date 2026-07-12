package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 项目里程碑实体类
 * @author XingHai
 * @date 2026/7/12 16:02
 * @description 项目里程碑实体类
 * /
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMilestone  implements Serializable {

    // 主键
    private Integer id;

    // 项目ID
    private Integer projectId;

    // 项目里程碑标题
    private String title;

    // 计划完成时间
    private LocalDate plannedDate;

    // 实际完成时间
    private LocalDate actualDate;

    // 是否完成（0：未完成，1：已完成）
    private Integer completed;

    // 项目里程碑排序
    private Integer sortOrder;

    // 项目里程碑是否删除（0：未删除，1：已删除）
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;

}
