package cn.xh_net.studio.controller.open;

import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.ICaptchaService;
import cn.xh_net.studio.vo.CaptchaVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证码控制器
 * @author XingHai
 * @date 2026/7/14 20:31
 * @description 验证码控制器
 */
@RestController
@RequestMapping("/public/captcha")
public class CaptchaController {

    private final ICaptchaService captchaService;

    public CaptchaController(ICaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    /**
     * 获取图形验证码
     * @return 验证码VO
     */
    @GetMapping
    public Result<CaptchaVO> getCaptcha() {
        return Result.success(captchaService.getCaptcha());
    }

}
