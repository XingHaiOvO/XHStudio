package cn.xh_net.studio.service;

import cn.xh_net.studio.entity.GrowthExperience;

import java.util.List;

/**
 * 成长经历服务接口
 * @author XingHai
 * @date 2026/7/24
 * @description 成长经历服务接口
 */
public interface IGrowthExperienceService {

    /**
     * 根据成员ID获取公开成长经历列表
     * @param userId 成员ID
     * @return 公开成长经历列表
     */
    List<GrowthExperience> getPublicGrowthExperienceListByUserId(Long userId);

}
