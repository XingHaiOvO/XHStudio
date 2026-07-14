package cn.xh_net.studio.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.xh_net.studio.exception.CaptchaExpiredException;
import cn.xh_net.studio.exception.CaptchaInvalidException;
import cn.xh_net.studio.prpperties.CaptchaProperties;
import cn.xh_net.studio.service.ICaptchaService;
import cn.xh_net.studio.utils.RedisUtil;
import cn.xh_net.studio.vo.CaptchaVO;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务实现类
 * @author XingHai
 * @date 2026/7/14 20:29
 * @description 验证码服务实现类
 */
@Service
public class CaptchaServiceImpl implements ICaptchaService {

    private final RedisUtil redisUtil;

    private final CaptchaProperties captchaProperties;

    public CaptchaServiceImpl(RedisUtil redisUtil, CaptchaProperties captchaProperties) {
        this.redisUtil = redisUtil;
        this.captchaProperties = captchaProperties;
    }

    /**
     * 获取图形验证码
     * @return 验证码VO
     */
    @Override
    public CaptchaVO getCaptcha() {

        // 生成验证码Redis键值
        String captchaId = UUID.randomUUID().toString().replace("-", "");

        // 生成验证码图片
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(captchaProperties.getWidth(), captchaProperties.getHeight());

        // 获取验证码文本
        String captchaCode = captcha.getCode();

        // 将验证码文本存储到Redis中
        redisUtil.setCacheObject(captchaId, captchaCode, captchaProperties.getTtl(), TimeUnit.MINUTES);

        // 获取验证码图片Base64编码
        String captchaImage = "data:image/png;base64," +captcha.getImageBase64();

        return new CaptchaVO(captchaId, captchaImage);
    }

    /**
     * 校验验证码
     * @param captchaId 验证码Redis键值
     * @param captchaCode 验证码文本
     */
    public void verifyCaptcha(String captchaId, String captchaCode) {

        // 从Redis中获取验证码文本
        String cacheCaptchaCode = redisUtil.getCacheObject(captchaId);

        // 判断缓存验证码是否为空
        if (cacheCaptchaCode == null) {
            throw new CaptchaExpiredException("验证码过期");
        }

        // 判断验证码是否一致
        if (!cacheCaptchaCode.equalsIgnoreCase(captchaCode)) {
            throw new CaptchaInvalidException("验证码错误");
        }

        // 删除Redis中的验证码
        redisUtil.deleteCacheObject(captchaId);

    }
}
