package cn.xh_net.studio.config;

import cn.xh_net.studio.filter.JwtAuthenticationTokenFilter;
import cn.xh_net.studio.handler.AccessDeniedHandlerImpl;
import cn.xh_net.studio.handler.AuthenticationEntryPointImpl;
import cn.xh_net.studio.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

/**
 * 安全配置类
 * @author XingHai
 * @date 2026/7/13 14:10
 * @description 安全配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Qualifier("userAuthenticationManager")
    private final AuthenticationManager userAuthenticationManager;

    @Qualifier("adminAuthenticationManager")
    private final AuthenticationManager adminAuthenticationManager;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private final AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    private final AuthenticationEntryPointImpl authenticationEntryPointImpl;

    public SecurityConfig(@Qualifier("userAuthenticationManager") AuthenticationManager userAuthenticationManager,
                          @Qualifier("adminAuthenticationManager") AuthenticationManager adminAuthenticationManager,
                          RedisUtil redisUtil,
                          AccessDeniedHandlerImpl accessDeniedHandlerImpl,
                          AuthenticationEntryPointImpl authenticationEntryPointImpl) {
        this.userAuthenticationManager = userAuthenticationManager;
        this.adminAuthenticationManager = adminAuthenticationManager;
        this.jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter(redisUtil);
        this.accessDeniedHandlerImpl = accessDeniedHandlerImpl;
        this.authenticationEntryPointImpl = authenticationEntryPointImpl;
    }

    /**
     * 公共接口安全过滤链
     */
    @Bean
    @Order(0)
    public SecurityFilterChain publicSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/public/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        return http.build();
    }

    /**
     * 用户端安全过滤链
     */
    @Bean
    @Order(1)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/member/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/member/login").permitAll()
                            .anyRequest().authenticated();
                })
                .authenticationManager(userAuthenticationManager)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtAuthenticationTokenFilter, ExceptionTranslationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandlerImpl)
                        .authenticationEntryPoint(authenticationEntryPointImpl));
        return http.build();
    }


    /**
     * 管理员端安全过滤链
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    @Order(2)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/admin/login").permitAll()
                            .anyRequest().authenticated();
                })
                .authenticationManager(adminAuthenticationManager)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(jwtAuthenticationTokenFilter, ExceptionTranslationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandlerImpl)
                        .authenticationEntryPoint(authenticationEntryPointImpl));
        return http.build();
    }

}