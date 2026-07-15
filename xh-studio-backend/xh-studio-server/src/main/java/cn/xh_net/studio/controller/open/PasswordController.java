package cn.xh_net.studio.controller.open;

import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.IPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 密码重置控制器
 * @author XingHai
 * @date 2026/7/15 19:21
 */
@RestController
@RequestMapping("/public/password")
public class PasswordController {

    private final IPasswordService passwordService;

    @Autowired
    public PasswordController(IPasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * 重置密码请求
     * @param email 邮箱
     * @throws MessagingException 异常
     * @throws UnsupportedEncodingException 异常
     */
    @PostMapping("/reset-request")
    public Result<Void> resetPasswordRequest(@RequestBody Map<String, String> email) throws MessagingException, UnsupportedEncodingException {
        passwordService.resetPasswordRequest(email.get("email"));
        return Result.success();
    }
}
