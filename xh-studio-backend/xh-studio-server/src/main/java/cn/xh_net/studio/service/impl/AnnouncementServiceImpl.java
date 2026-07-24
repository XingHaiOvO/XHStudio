package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.dto.AnnouncementDTO;
import cn.xh_net.studio.entity.Announcement;
import cn.xh_net.studio.mapper.AnnouncementMapper;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.service.IAnnouncementService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static cn.xh_net.studio.constant.CommonConstant.NOT_DELETED;

/**
 * 公告服务实现类
 * @author XingHai
 * @date 2026/7/24
 * @description 公告服务实现类
 */
@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements IAnnouncementService {

    private final AnnouncementMapper announcementMapper;

    /**
     * 获取公告列表
     * @param announcementDTO 公告DTO
     * @return 公告列表
     */
    @Override
    public PageResult<Announcement> getAnnouncementList(AnnouncementDTO announcementDTO) {
        // 构造分页参数
        Page<Announcement> page = new Page<>(announcementDTO.getPage(), announcementDTO.getSize());

        // 查询公告列表
        announcementMapper.selectPage(page, new QueryWrapper<Announcement>()
                .eq("deleted", NOT_DELETED));

        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 获取公告详情
     * @param id 公告ID
     * @return 公告详情
     */
    @Override
    public Announcement getAnnouncementDetail(Long id) {
        return announcementMapper.selectOne(new LambdaQueryWrapper<Announcement>()
                .eq(Announcement::getId, id)
                .eq(Announcement::getDeleted, NOT_DELETED));
    }
}
