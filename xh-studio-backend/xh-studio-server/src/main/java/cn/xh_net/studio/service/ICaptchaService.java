package cn.xh_net.studio.service;

import cn.xh_net.studio.vo.CaptchaVO;

/**
 * 验证码服务接口
 * @author XingHai
 * @date 2026/7/14 20:29
 * @description 验证码服务接口
 */
public interface ICaptchaService {

    /**
     * 获取图形验证码
     * @return 验证码VO
     */
    CaptchaVO getCaptcha();

    /**
     * 校验图形验证码
     * @param captchaId 验证码Redis键值
     * @param captchaCode 验证码输入值
     */
    void verifyCaptcha(String captchaId, String captchaCode);
}
