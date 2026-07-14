package cn.xh_net.studio.bo;

import cn.xh_net.studio.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import static cn.xh_net.studio.constant.CommonConstant.ENABLED;

/**
 * 登录用户信息
 * @author XingHai
 * @date 2026/7/13 14:08
 * @description 登录用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    // 用户信息
    private User user;

    // 权限列表
    private List<String> permissions;

    // 权限列表(不持久化到 Redis，运行时查询)
    @Transient
    private List<SimpleGrantedAuthority> authorities;

    // 获取用户权限列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            return authorities;
        }

        authorities = permissions.stream().map(SimpleGrantedAuthority::new).toList();
        return authorities;
    }

    // 获取用户密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 获取用户名
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 检查用户账号是否启用
    @Override
    public boolean isEnabled() {
        return ENABLED.equals(user.getStatus());
    }
}
