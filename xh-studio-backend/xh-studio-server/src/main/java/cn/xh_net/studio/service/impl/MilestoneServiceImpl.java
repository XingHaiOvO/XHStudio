package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.entity.Milestone;
import cn.xh_net.studio.mapper.MilestoneMapper;
import cn.xh_net.studio.service.IMilestoneService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 里程碑服务实现类
 * @author XingHai
 * @date 2026/7/24
 * @description 里程碑服务实现类
 */
@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl implements IMilestoneService {

    private final MilestoneMapper milestoneMapper;

    /**
     * 根据项目ID获取项目下的所有里程碑
     * @param projectId 项目ID
     * @return 里程碑列表
     */
    @Override
    public List<Milestone> getMilestoneListByProjectId(Long projectId) {
        return milestoneMapper.selectList(new LambdaQueryWrapper<Milestone>().eq(Milestone::getProjectId, projectId));
    }
}
