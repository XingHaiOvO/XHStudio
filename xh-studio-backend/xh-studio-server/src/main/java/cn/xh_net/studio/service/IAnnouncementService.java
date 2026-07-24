package cn.xh_net.studio.service;

import cn.xh_net.studio.dto.AnnouncementDTO;
import cn.xh_net.studio.entity.Announcement;
import cn.xh_net.studio.result.PageResult;

/**
 * 公告服务接口
 * @author XingHai
 * @date 2026/7/24
 * @description 公告服务接口
 */
public interface IAnnouncementService {

    /**
     * 获取公告列表
     * @return 公告列表
     */
    PageResult<Announcement> getAnnouncementList(AnnouncementDTO announcementDTO);

    /**
     * 获取公告详情
     * @param id 公告ID
     * @return 公告详情
     */
    Announcement getAnnouncementDetail(Long id);
}
