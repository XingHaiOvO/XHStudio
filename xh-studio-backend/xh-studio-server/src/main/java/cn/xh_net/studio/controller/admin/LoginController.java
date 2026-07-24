package cn.xh_net.studio.controller.admin;

import cn.xh_net.studio.dto.UserLoginDTO;
import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.ILoginService;
import cn.xh_net.studio.vo.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员登录控制器
 * 用于处理管理员登录相关请求
 * @author XingHai
 * @date 2026/7/13 14:17
 */
@Slf4j
@RestController("adminLoginController")
@RequestMapping("/admin")
public class LoginController {

    private final ILoginService loginUserService;

    public LoginController(ILoginService loginUserService) {
        this.loginUserService = loginUserService;
    }

    /**
     * 管理员登录
     * @param userLoginDTO 登录用户信息
     * @return 登录令牌及用户信息
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        return Result.success(loginUserService.adminLogin(userLoginDTO));
    }

    /**
     * 管理员退出登录
     * @return 退出登录响应结果
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        loginUserService.logout();
        log.info("管理员退出登录");
        return Result.success();
    }

}
