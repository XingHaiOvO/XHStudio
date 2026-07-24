package cn.xh_net.studio.service;

import cn.xh_net.studio.dto.UserLoginDTO;
import cn.xh_net.studio.vo.LoginVO;

/**
 * 登录服务接口
 * @author XingHai
 * @date 2026/7/13 14:19
 * @description 登录服务接口
 */
public interface ILoginService {

    /**
     * 管理员登录
     * @param userLoginDTO 登录用户信息
     * @return 登录令牌及用户信息
     */
    LoginVO adminLogin(UserLoginDTO userLoginDTO);

    /**
     * 用户登录
     * @param userLoginDTO 登录用户信息
     * @return 登录令牌及用户信息
     */
    LoginVO userLogin(UserLoginDTO userLoginDTO);

    /**
     * 退出登录
     */
    void logout();

}
