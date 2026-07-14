package cn.xh_net.studio.exception;

/**
 * 验证码过期异常
 * @author XingHai
 * @date 2026/7/14 21:38
 * @description 验证码过期异常
 */
public class CaptchaExpiredException extends RuntimeException {
    public CaptchaExpiredException(String message) {
        super(message);
    }
}
