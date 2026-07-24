package cn.xh_net.studio.dto;

import lombok.Data;

/**
 * 公告DTO传输对象
 * @author XingHai
 * @date 2026/7/24
 * @description 公告DTO传输对象
 */
@Data
public class AnnouncementDTO {

    // 当前页
    private Long page;

    // 每页数量
    private Long size;

}
