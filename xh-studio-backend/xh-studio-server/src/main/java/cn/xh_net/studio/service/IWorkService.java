package cn.xh_net.studio.service;

import cn.xh_net.studio.entity.Work;

import java.util.List;

/**
 * 作品服务接口
 * @author XingHai
 * @date 2026/7/24
 * @description 作品服务接口
 */
public interface IWorkService {

    /**
     * 获取公开作品列表
     * @param userId 成员ID
     * @return 公开作品列表
     */
    List<Work> getPublicWorkListByUserId(Long userId);
}
