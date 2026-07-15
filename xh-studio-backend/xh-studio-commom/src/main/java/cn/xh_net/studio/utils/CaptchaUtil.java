package cn.xh_net.studio.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 验证码工具类
 * @author XingHai
 * @date 2026/7/15 16:48
 * @description 验证码工具类
 */
public class CaptchaUtil {

    private static final List<Integer> digits = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 0));

    /**
     * 生成6位数字验证码
     * @return 验证码
     */
    public static String generateDigitCaptcha() {
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            Collections.shuffle(digits);
            captcha.append(digits.get(0));
        }
        return captcha.toString();
    }

}
