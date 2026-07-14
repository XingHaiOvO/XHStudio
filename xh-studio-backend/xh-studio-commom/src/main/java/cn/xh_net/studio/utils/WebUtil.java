package cn.xh_net.studio.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static cn.xh_net.studio.constant.StatusConstant.SUCCESS;

/**
 * Web工具类
 * @author XingHai
 * @date 2026/7/13 13:30
 * @description Web工具类，用于处理Web相关的操作
 */
public class WebUtil {

    /**
     * 渲染字符串到响应体
     * @param response HttpServletResponse
     * @param content 要渲染的字符串
     * @throws IOException 如果写入响应体时发生错误
     */
    public static void renderString(HttpServletResponse response, String content) throws IOException {
        response.setStatus(SUCCESS);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(content);
    }
}
