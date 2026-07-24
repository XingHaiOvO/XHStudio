package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.entity.Banner;
import cn.xh_net.studio.mapper.BannerMapper;
import cn.xh_net.studio.service.IBannerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.xh_net.studio.constant.CommonConstant.ENABLED;

/**
 * 首页banner服务实现类
 * @author XingHai
 * @date 2026/7/12 19:49
 * @description 首页banner服务实现类
 */
@Service
public class BannerServiceImpl implements IBannerService {

    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 获取首页banner列表
     * @return 首页banner列表
     */
    @Override
    public List<Banner> getBanners() {
        return bannerMapper.selectList(new LambdaQueryWrapper<Banner>()
                .eq(Banner::getEnabled, ENABLED));
    }
}
