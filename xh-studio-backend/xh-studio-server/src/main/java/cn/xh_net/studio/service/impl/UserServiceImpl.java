package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.entity.User;
import cn.xh_net.studio.mapper.UserMapper;
import cn.xh_net.studio.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * @author XingHai
 * @date 2026/7/15 21:52
 * @description 用户服务实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户
     */
    @Override
    public User selectByEmail(String email) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
    }

    /**
     * 根据用户更新用户
     * @param user 用户
     */
    @Override
    public void updateByUser(User user) {
        userMapper.updateById(user);
    }
}
