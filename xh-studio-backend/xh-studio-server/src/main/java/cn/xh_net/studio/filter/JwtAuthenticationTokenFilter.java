package cn.xh_net.studio.filter;

import cn.xh_net.studio.bo.LoginUser;
import cn.xh_net.studio.utils.JwtUtil;
import cn.xh_net.studio.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT认证令牌过滤器
 * @author XingHai
 * @date 2026/7/13 22:05
 * @description 用于认证JWT令牌
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final RedisUtil redisUtil;

    public  JwtAuthenticationTokenFilter(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException, BadCredentialsException {
        // 从请求头中获取JWT令牌
        String token = request.getHeader("Authorization");

        // 如果令牌为空，直接放行
        if (token == null || token.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析令牌获取用户id
        String userId = null;
        try {
            Claims claims = JwtUtil.parseJwt(token);
            userId = claims.getSubject();
        } catch (SignatureException e) {
            throw new BadCredentialsException("非法Token");
        }

        // 从Redis中获取用户信息
        String key = "login:" + userId;
        LoginUser loginUser = redisUtil.getCacheObject(key);

        // 如果用户信息为空，抛出异常
        if (loginUser == null) {
            throw new BadCredentialsException("用户不存在或已过期");
        }

        // 将用户信息设置到SecurityContextHolder中
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 放行
        filterChain.doFilter(request, response);
    }
}
