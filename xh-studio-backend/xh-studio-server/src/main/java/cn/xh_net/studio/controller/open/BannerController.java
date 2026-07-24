package cn.xh_net.studio.controller.open;

import cn.xh_net.studio.entity.Banner;
import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.IBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 首页banner接口
 * @author XingHai
 * @date 2026/7/12 19:41
 * @description 首页banner接口
 */
@RestController
@RequestMapping("/public/banner")
@Slf4j
public class BannerController {

    @Autowired
    private IBannerService bannerService;

    /**
     * 获取首页banner列表
     * @return 首页banner列表
     */
    @GetMapping("/list")
    public Result<List<Banner>> getBanners() {
        log.info("获取所有已启用banner图");
        return Result.success(bannerService.getBanners());
    }

}
