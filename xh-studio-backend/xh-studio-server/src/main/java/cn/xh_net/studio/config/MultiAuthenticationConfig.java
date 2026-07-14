package cn.xh_net.studio.config;

import cn.xh_net.studio.service.impl.AdminUserDetailsServiceImpl;
import cn.xh_net.studio.service.impl.UserUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 多认证源配置类
 * 用于分别配置普通用户和管理员两套独立的认证管理器
 * @author XingHai
 * @date 2026/7/13
 * @description 多认证源配置类
 */
@Configuration
public class MultiAuthenticationConfig {

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户认证管理器
     */
    @Bean
    public DaoAuthenticationProvider userAuthProvider(UserUserDetailsServiceImpl userUserDetailsServiceImpl,
                                                      PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userUserDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager userAuthenticationManager(DaoAuthenticationProvider userAuthProvider) {
        return new ProviderManager(userAuthProvider);
    }

    /**
     * 管理员认证管理器
     */
    @Bean
    public DaoAuthenticationProvider adminAuthProvider(AdminUserDetailsServiceImpl adminUserDetailsServiceImpl,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(adminUserDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    @Primary
    public AuthenticationManager adminAuthenticationManager(DaoAuthenticationProvider adminAuthProvider) {
        return new ProviderManager(adminAuthProvider);
    }
}
