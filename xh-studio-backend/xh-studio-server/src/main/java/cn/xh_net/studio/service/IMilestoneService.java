package cn.xh_net.studio.service;

import cn.xh_net.studio.entity.Milestone;

import java.util.List;

/**
 * 里程碑服务接口
 * @author XingHai
 * @date 2026/7/24
 * @description 里程碑服务接口
 */
public interface IMilestoneService {

    /**
     * 获取项目下的里程碑列表
     * @param projectId 项目ID
     * @return 里程碑列表
     */
    List<Milestone> getMilestoneListByProjectId(Long projectId);
}
