package cn.xh_net.studio.prpperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 重置密码配置类
 * @author XingHai
 * @date 2026/7/15 19:34
 * @description 重置密码配置类
 */
@ConfigurationProperties(prefix = "xh.reset-password")
@Component
@Data
public class ResetPasswordProperties {

    // 验证码过期时间（分钟）
    private Long ttl;

    // 重置密码请求次数限制
    private Integer maxCount;

    // 重置密码请求次数限制过期时间（小时）
    private Long countTtl;

}
