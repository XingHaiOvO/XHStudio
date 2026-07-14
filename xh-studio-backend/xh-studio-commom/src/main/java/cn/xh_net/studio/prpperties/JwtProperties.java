package cn.xh_net.studio.prpperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jwt 配置属性
 * @author XingHai
 * @date 2026/7/12 23:26
 * @description 用于配置 jwt 令牌的签名加密密钥、过期时间和前端传递的令牌名称
 */
@Component
@Data
@ConfigurationProperties(prefix = "xh.jwt")
public class JwtProperties {

    // jwt签名加密使用的密钥
    private String secretKey;

    // jwt过期时间
    private Long ttl;

    // 前端传递的令牌名称
    private String tokenName;

}
