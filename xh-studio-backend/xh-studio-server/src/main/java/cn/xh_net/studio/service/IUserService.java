package cn.xh_net.studio.service;

import cn.xh_net.studio.entity.User;

/**
 * 用户服务接口
 * @author XingHai
 * @date 2026/7/15 21:52
 * @description 用户服务接口
 */
public interface IUserService {

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户
     */
    User selectByEmail(String email);

    /**
     * 根据用户更新用户
     * @param user 用户
     */
    void updateByUser(User user);
}
