package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.bo.LoginUser;
import cn.xh_net.studio.dto.UserLoginDTO;
import cn.xh_net.studio.prpperties.JwtProperties;
import cn.xh_net.studio.service.ICaptchaService;
import cn.xh_net.studio.service.ILoginService;
import cn.xh_net.studio.utils.JwtUtil;
import cn.xh_net.studio.utils.RedisUtil;
import cn.xh_net.studio.vo.LoginVO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 登录服务实现类
 * @author XingHai
 * @date 2026/7/13 17:19
 * @description 用于实现登录服务接口
 */
@Service
public class LoginServiceImpl implements ILoginService {

    private final RedisUtil redisUtil;

    @Qualifier("userAuthenticationManager")
    private final AuthenticationManager userAuthenticationManager;

    @Qualifier("adminAuthenticationManager")
    private final AuthenticationManager adminAuthenticationManager;

    private final JwtProperties jwtProperties;

    private final ICaptchaService captchaService;

    /**
     * 构造函数
     */
    public LoginServiceImpl(@Qualifier("userAuthenticationManager") AuthenticationManager userAuthenticationManager,
                            @Qualifier("adminAuthenticationManager") AuthenticationManager adminAuthenticationManager,
                            RedisUtil redisUtil, JwtProperties jwtProperties,
                            ICaptchaService captchaService) {
        this.userAuthenticationManager = userAuthenticationManager;
        this.adminAuthenticationManager = adminAuthenticationManager;
        this.redisUtil = redisUtil;
        this.jwtProperties = jwtProperties;
        this.captchaService = captchaService;
    }

    /**
     * 管理员登录
     * @param userLoginDTO 登录用户信息
     * @return 登录令牌及用户信息
     */
    @Override
    public LoginVO adminLogin(UserLoginDTO userLoginDTO) {
        return login(userLoginDTO, adminAuthenticationManager);
    }

    /**
     * 普通用户登录
     * @param userLoginDTO 登录用户信息
     * @return 登录令牌及用户信息
     */
    @Override
    public LoginVO userLogin(UserLoginDTO userLoginDTO) {
        return login(userLoginDTO, userAuthenticationManager);
    }

    /**
     * 退出登录
     */
    @Override
    public void logout() {
        // 获取当前登录用户的 ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();

        String key = "login:" + userId;
        // 清除 Redis 中的登录信息
        redisUtil.deleteCacheObject(key);
    }

    /**
     * 统一登录方法
     * @param userLoginDTO 登录信息
     * @param authenticationManager 认证管理器
     * @return 用户登录令牌及用户信息
     */
    private LoginVO login (UserLoginDTO userLoginDTO, AuthenticationManager authenticationManager) {

        // 校验图形验证码
        captchaService.verifyCaptcha(userLoginDTO.getCaptchaId(), userLoginDTO.getCaptchaCode());

        // 封装 Authentication 信息
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());

        // 通过 AuthenticationManager 进行认证
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);


        // 从 Authentication 对象中获取用户信息
        LoginUser loginUser = (LoginUser) authenticated.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        // 生成 JWT 令牌
        String jwt = JwtUtil.createJwt(userId);

        // 将用户信息存入 Redis
        redisUtil.setCacheObject("login:" + userId, loginUser);

        return LoginVO.builder()
                .token(jwt)
                .tokenType(jwtProperties.getTokenName())
                .expiresIn(jwtProperties.getTtl())
                .userInfo(loginUser.getUser())
                .build();
    }
}
