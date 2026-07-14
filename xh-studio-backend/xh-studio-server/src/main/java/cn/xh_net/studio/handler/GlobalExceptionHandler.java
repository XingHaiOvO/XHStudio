package cn.xh_net.studio.handler;

import cn.xh_net.studio.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static cn.xh_net.studio.constant.StatusConstant.UNAUTHORIZED;

/**
 * 全局异常处理类
 * @author XingHai
 * @date 2026/7/13 13:30
 * @description 全局异常处理类，用于处理全局异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理认证异常
     * @param e 认证异常
     * @return 认证异常响应结果
     */
    @ExceptionHandler
    public Result<Void> handleAuthenticationException(AuthenticationException e) {
        if (e instanceof BadCredentialsException) {
            log.error("用户名或密码错误");
            return Result.error(UNAUTHORIZED, "用户名或密码错误");
        } else {
            log.error(e.getMessage());
            return Result.error(UNAUTHORIZED, e.getMessage());
        }
    }

    /**
     * 处理账号状态异常
     * @param e 账号状态异常
     * @return 账号状态异常响应结果
     */
    @ExceptionHandler
    public Result<Void> handleAccountStatusException(AccountStatusException e) {
        if (e instanceof DisabledException) {
            log.error("账号已被禁用", e);
            return Result.error(UNAUTHORIZED, "账号已被禁用");
        } else {
            log.error(e.getMessage(), e);
            return Result.error(UNAUTHORIZED, e.getMessage());
        }
    }
}
