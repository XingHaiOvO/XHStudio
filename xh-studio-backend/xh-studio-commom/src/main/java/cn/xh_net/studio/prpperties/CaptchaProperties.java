package cn.xh_net.studio.prpperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "xh.captcha")
@Data
public class CaptchaProperties {

    // 验证码过期时间（分钟）
    private Long ttl;

    // 验证码图片宽度
    private Integer width;

    // 验证码图片高度
    private Integer height;
}
