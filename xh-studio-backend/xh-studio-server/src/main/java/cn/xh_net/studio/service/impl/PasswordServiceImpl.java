package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.entity.User;
import cn.xh_net.studio.mapper.UserMapper;
import cn.xh_net.studio.prpperties.ResetPasswordProperties;
import cn.xh_net.studio.service.IPasswordService;
import cn.xh_net.studio.utils.CaptchaUtil;
import cn.xh_net.studio.utils.MailUtil;
import cn.xh_net.studio.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 重置密码服务实现类
 * @author XingHai
 * @date 2026/7/15 19:54
 * @description 重置密码服务实现类
 */
@Service
public class PasswordServiceImpl implements IPasswordService {

    private final UserMapper userMapper;

    private final RedisUtil redisUtil;

    private final MailUtil mailUtil;

    private final ResetPasswordProperties resetPasswordProperties;

    @Autowired
    public PasswordServiceImpl(UserMapper userMapper, RedisUtil redisUtil,
                               ResetPasswordProperties resetPasswordProperties,
                               MailUtil mailUtil) {
        this.userMapper = userMapper;
        this.redisUtil = redisUtil;
        this.resetPasswordProperties = resetPasswordProperties;
        this.mailUtil = mailUtil;
    }

    /**
     * 重置密码请求
     * @param email 邮箱
     */
    @Override
    public void resetPasswordRequest(String email) throws MessagingException, UnsupportedEncodingException {

        // 查询Redis中该邮箱请求次数是否超过3次
        String key = "reset_password:count:" + email;
        int count = redisUtil.getCacheObject(key);

        if (count == 0) {    // 第一次请求
            count = 1;
            redisUtil.setCacheObject(key, count, resetPasswordProperties.getCountTtl(), TimeUnit.HOURS);
        } else if (count >= resetPasswordProperties.getMaxCount()) {    // 请求次数超过限制
            throw new RuntimeException("重置密码请求次数超过限制");
        } else {    // 请求次数未超过限制
            count++;
            redisUtil.setCacheObject(key, count);
        }

        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User user = userMapper.selectOne(queryWrapper);

        // 判断是否存在
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 生成验证码
        String captcha = CaptchaUtil.generateDigitCaptcha();

        // 将验证码存入Redis，设置过期时间为5分钟
        Long expireMinutes = resetPasswordProperties.getTtl();
        key = "reset_password:captcha:" + email;
        redisUtil.setCacheObject(key, captcha, expireMinutes, TimeUnit.MINUTES);

        // 构建模板数据
        Map<String, String> content = new HashMap<>();
        content.put("username", user.getUsername());
        content.put("captcha", captcha);
        content.put("expireMinutes", expireMinutes.toString());

        // 发送邮件
        mailUtil.sendMail(email, "reset-password",
                "【" + mailUtil.getStudioName() + "】重置密码验证码", content);

    }
}
