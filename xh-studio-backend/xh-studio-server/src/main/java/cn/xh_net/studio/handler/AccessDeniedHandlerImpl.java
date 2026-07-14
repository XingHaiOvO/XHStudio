package cn.xh_net.studio.handler;

import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.utils.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONValue;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 权限不足处理类
 * @author XingHai
 * @date 2026/7/14 14:07
 * @description 权限不足处理类
 */
@Component
public class AccessDeniedHandlerImpl  implements AccessDeniedHandler {

    /**
     * 处理权限不足异常
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param accessDeniedException AccessDeniedException
     * @throws IOException IOException
     * @throws ServletException ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result<Void> result = Result.error(HttpStatus.FORBIDDEN.value(), "权限不足");
        String json = JSONValue.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}
