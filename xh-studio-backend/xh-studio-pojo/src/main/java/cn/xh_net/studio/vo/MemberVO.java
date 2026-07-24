package cn.xh_net.studio.vo;

import cn.xh_net.studio.entity.GrowthExperience;
import cn.xh_net.studio.entity.Work;
import lombok.Data;

import java.util.List;

/**
 * 成员VO
 * @author XingHai
 * @date 2026/7/24
 * @description 成员VO
 */
@Data
public class MemberVO {

    // 成员ID
    private Long id;

    // 成员昵称
    private String nickname;

    // 成员头像
    private String avatar;

    // 成员角色
    private String role;

    // 成员手机号
    private String phone;

    // 成员邮箱
    private String email;

    // 成员QQ
    private String qq;

    // 成员介绍
    private String intro;

    // 成员加入时间份
    private Integer joinYear;

    // 成员荣誉勋章
    private String honorBadge;

    // 成员作品
    private List<Work> works;

    // 成员成长经历
    private List<GrowthExperience> growthExperiences;

}
