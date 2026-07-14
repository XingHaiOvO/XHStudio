package cn.xh_net.studio.controller.member;

import cn.xh_net.studio.dto.UserDTO;
import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.ILoginService;
import cn.xh_net.studio.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 成员登录控制器
 * 用于处理登录相关请求
 * @author XingHai
 * @date 2026/7/13
 */
@Slf4j
@RestController("memberLoginController")
@RequestMapping("/member")
public class LoginController {

    private final ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * 成员登录
     * @param userDTO 登录信息
     * @return 令牌信息及用户信息
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody UserDTO userDTO) {
        return Result.success(loginService.userLogin(userDTO));
    }

    /**
     * 成员退出登录
     * @return 退出登录响应结果
     */
    @PostMapping("/logout")
    public Result logout() {
        loginService.logout();
        log.info("成员退出登录");
        return Result.success();
    }
}
