package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.bo.LoginUser;
import cn.xh_net.studio.entity.Role;
import cn.xh_net.studio.entity.User;
import cn.xh_net.studio.mapper.RoleMapper;
import cn.xh_net.studio.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.xh_net.studio.constant.CommonConstant.NOT_DELETED;

/**
 * 用户详情服务实现类
 */
@Service("userDetailsServiceForUser")
public class UserUserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    public UserUserDetailsServiceImpl(UserMapper userMapper, RoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    /**
     * 根据用户名加载用户详情
     * @param username 用户名
     * @return 用户详情
     * @throws UsernameNotFoundException 如果用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名查询用户
        QueryWrapper<User>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("deleted", NOT_DELETED);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 查询用户权限封装到 LoginUser 中
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("id", user.getRoleId());
        roleQueryWrapper.eq("deleted", NOT_DELETED);
        Role role = roleMapper.selectOne(roleQueryWrapper);
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }

        List<String> permissions = new ArrayList<>();
        permissions.add(role.getRoleCode());

        return LoginUser.builder()
                .user(user)
                .permissions(permissions)
                .build();
    }
}
