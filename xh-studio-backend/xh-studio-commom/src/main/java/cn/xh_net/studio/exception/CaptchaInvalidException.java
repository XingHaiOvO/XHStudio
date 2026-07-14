package cn.xh_net.studio.exception;

/**
 * 验证码错误异常
 * @author XingHai
 * @date 2026/7/14 21:39
 * @description 验证码错误异常
 */
public class CaptchaInvalidException extends RuntimeException {
    public CaptchaInvalidException(String message) {
        super(message);
    }
}
