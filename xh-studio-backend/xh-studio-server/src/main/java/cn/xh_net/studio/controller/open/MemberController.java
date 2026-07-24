package cn.xh_net.studio.controller.open;

import cn.xh_net.studio.dto.UserDTO;
import cn.xh_net.studio.entity.User;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.IUserService;
import cn.xh_net.studio.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XingHai
 * @date 2026/7/24
 * @description 成员控制器
 */
@Slf4j
@RestController("publicMemberController")
@RequestMapping("/public/member")
@RequiredArgsConstructor
public class MemberController {

    private final IUserService userService;

    /**
     * 获取成员列表
     * @param userDTO 成员查询参数
     * @return 成员列表
     */
    @GetMapping("/list")
    public Result<PageResult<User>> getMemberList(UserDTO userDTO) {
        return Result.success(userService.getMemberList(userDTO));
    }

    @GetMapping("/{id}")
    public Result<MemberVO> getMemberDetail(@PathVariable Long id) {
        return Result.success(userService.getMemberDetail(id));
    }

}
