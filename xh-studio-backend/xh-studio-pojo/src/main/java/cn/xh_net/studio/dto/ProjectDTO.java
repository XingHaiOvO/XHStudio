package cn.xh_net.studio.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author XingHai
 * @date 2026/7/24
 * @description 项目查询参数DTO
 */
@Data
public class ProjectDTO {

    // 当前页码
    private Long page;

    // 每页数量
    private Long size;

    // 项目状态
    private Integer status;

    // 搜索关键词
    private String keyword;

}
