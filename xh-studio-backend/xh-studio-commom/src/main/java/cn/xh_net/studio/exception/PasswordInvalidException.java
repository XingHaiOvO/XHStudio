package cn.xh_net.studio.exception;

/**
 * 密码无效异常
 * @author XingHai
 * @date 2026/7/15 22:01
 * @description 密码无效异常
 */
public class PasswordInvalidException extends RuntimeException {
    public PasswordInvalidException(String message) {
        super(message);
    }
}
