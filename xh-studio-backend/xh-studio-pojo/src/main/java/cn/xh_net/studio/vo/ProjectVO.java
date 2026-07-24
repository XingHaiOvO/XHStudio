package cn.xh_net.studio.vo;

import cn.xh_net.studio.entity.Milestone;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目VO
 * @author XingHai
 * @date 2026/7/24
 * @description 项目VO
 */
@Data
public class ProjectVO {

    // 项目ID
    private Long id;

    // 项目标题
    private  String title;

    // 项目封面
    private String cover;

    // 项目状态
    private Integer status;

    // 项目进度
    private Integer progress;

    // 项目摘要
    private String summary;

    // 项目内容
    private String content;

    // 项目开始时间
    private LocalDate startDate;

    // 项目结束时间
    private LocalDate endDate;

    // 项目封面URL
    private String coverUrl;

    // 项目成员
    private List<ProjectMemberVO> members;

    // 项目里程碑
    private List<Milestone> milestones;

}
