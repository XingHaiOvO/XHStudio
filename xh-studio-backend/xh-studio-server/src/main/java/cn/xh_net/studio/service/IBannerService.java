package cn.xh_net.studio.service;

import cn.xh_net.studio.entity.Banner;

import java.util.List;

/**
 * 首页banner服务接口
 * @author XingHai
 * @date 2026/7/12 19:44
 * @description 首页banner服务接口
 */
public interface IBannerService {

    /**
     * 获取首页banner列表
     * @return 首页banner列表
     */
    List<Banner> getBanners();

}
