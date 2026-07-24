package cn.xh_net.studio.vo;

import lombok.Data;

/**
 * 用户VO
 * @author XingHai
 * @date 2026/7/24
 * @description 用户VO
 */
@Data
public class ProjectMemberVO {

    // 用户ID
    private Long id;

    // 用户昵称
    private String nickname;

    // 用户头像URL
    private String avatar;

    // 用户在项目中的角色
    private String roleInProject;

}
