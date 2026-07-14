package cn.xh_net.studio.handler;

import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 认证入口点类
 * @author XingHai
 * @date 2026/7/14 14:10
 * @description 认证入口点类
 */
@Component
public class AuthenticationEntryPointImpl  implements AuthenticationEntryPoint {

    /**
     * 处理认证异常
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authException AuthenticationException
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Result<Void> result = Result.error(HttpStatus.UNAUTHORIZED.value(), authException.getMessage());
        String json = JSONValue.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}
