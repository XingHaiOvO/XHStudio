package cn.xh_net.studio.controller.open;

import cn.xh_net.studio.dto.AnnouncementDTO;
import cn.xh_net.studio.entity.Announcement;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.IAnnouncementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 公告控制器
 * @author XingHai
 * @date 2026/7/15 21:52
 * @description 公开公告控制器
 */
@Slf4j
@RestController("publicAnnouncementController")
@RequestMapping("/public/announcement")
@RequiredArgsConstructor
public class AnnouncementController {

    private final IAnnouncementService announcementService;

    /**
     * 获取公告列表
     * @return 公告列表
     */
    @GetMapping("/list")
    public Result<PageResult<Announcement>> getAnnouncementList(AnnouncementDTO announcementDTO) {
        return Result.success(announcementService.getAnnouncementList(announcementDTO));
    }

    /**
     * 获取公告详情
     * @param id 公告ID
     * @return 公告详情
     */
    @GetMapping("/{id}")
    public Result<Announcement> getAnnouncementDetail(@PathVariable Long id) {
        return Result.success(announcementService.getAnnouncementDetail(id));
    }

}
