package cn.xh_net.studio.dto;

import lombok.Data;

/**
 * 用户查询参数DTO
 * @author XingHai
 * @date 2026/7/24
 * @description 用于接收用户查询参数
 */
@Data
public class UserDTO {

    // 当前页码
    private Long page;

    // 每页数量
    private Long size;

    // 角色
    private String role;

    // 搜索关键词
    private String keyword;

}
