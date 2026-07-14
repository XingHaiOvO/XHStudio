package cn.xh_net.studio.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 验证码VO
 * @author XingHai
 * @date 2026/7/14 20:26
 * @description 图形验证码VO
 */
@Data
@AllArgsConstructor
public class CaptchaVO {

    // 验证码Redis键值
    private String captchaId;

    // 验证码图片
    private String captchaImg;
}
