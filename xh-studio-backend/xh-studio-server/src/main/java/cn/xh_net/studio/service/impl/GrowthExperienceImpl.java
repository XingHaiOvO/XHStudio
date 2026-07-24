package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.entity.GrowthExperience;
import cn.xh_net.studio.mapper.GrowthExperienceMapper;
import cn.xh_net.studio.service.IGrowthExperienceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.xh_net.studio.constant.CommonConstant.NOT_DELETED;
import static cn.xh_net.studio.constant.CommonConstant.PUBLIC;

/**
 * 成长经历服务实现类
 * @author XingHai
 * @date 2026/7/24
 * @description 成长经历服务实现类
 */
@Service
@RequiredArgsConstructor
public class GrowthExperienceImpl implements IGrowthExperienceService {

    private final GrowthExperienceMapper growthExperienceMapper;

    /**
     * 根据成员ID获取公开成长经历列表
     * @param userId 成员ID
     * @return 公开成长经历列表
     */
    @Override
    public List<GrowthExperience> getPublicGrowthExperienceListByUserId(Long userId) {
        return growthExperienceMapper.selectList(new LambdaQueryWrapper<GrowthExperience>()
                .eq(GrowthExperience::getUserId, userId)
                .eq(GrowthExperience::getPublicStatus, PUBLIC)
                .eq(GrowthExperience::getDeleted, NOT_DELETED));
    }
}
