package cn.xh_net.studio.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.xh_net.studio.dto.UserDTO;
import cn.xh_net.studio.entity.GrowthExperience;
import cn.xh_net.studio.entity.User;
import cn.xh_net.studio.entity.Work;
import cn.xh_net.studio.mapper.UserMapper;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.service.IGrowthExperienceService;
import cn.xh_net.studio.service.IUserService;
import cn.xh_net.studio.service.IWorkService;
import cn.xh_net.studio.vo.MemberVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.xh_net.studio.constant.CommonConstant.*;

/**
 * 用户服务实现类
 * @author XingHai
 * @date 2026/7/15 21:52
 * @description 用户服务实现类
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    private final IWorkService workService;

    private final IGrowthExperienceService growthExperienceService;

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

    /**
     * 获取成员列表
     * @param userDTO 成员查询参数
     * @return 成员列表
     */
    @Override
    public PageResult<User> getMemberList(UserDTO userDTO) {
        // 构造分页查询参数
        Page<User> page = new Page<>(userDTO.getPage(), userDTO.getSize());

        // 构造角色id列表
        List<Long> roleIds = new ArrayList<>();
        if ("admin".equals(userDTO.getRole())) {
            roleIds.add(ROLE_ADMIN);
            roleIds.add(ROLE_SUPER_ADMIN);
        } else if ("member".equals(userDTO.getRole())) {
            roleIds.add(ROLE_MEMBER);
        }

        // 构造查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!roleIds.isEmpty()) {
            wrapper.in("role_id", roleIds);
        }

        // 昵称查询
        String keyword = userDTO.getKeyword();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like("nickname", keyword);
        }

        // 用户是否删除
        wrapper.eq("deleted", NOT_DELETED);

        userMapper.selectPage(page, wrapper);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 获取成员详情
     * @param id 成员ID
     * @return 成员详情
     */
    @Override
    public MemberVO getMemberDetail(Long id) {

        MemberVO memberVO = new MemberVO();

        // 从数据库查询用户
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        BeanUtil.copyProperties(user, memberVO);

        // 查询该成员公开作品
        List<Work> workList = workService.getPublicWorkListByUserId(id);
        memberVO.setWorks(workList);

        // 查询该成员公开成长经历
        List<GrowthExperience> growthExperienceList = growthExperienceService
                .getPublicGrowthExperienceListByUserId(id);
        memberVO.setGrowthExperiences(growthExperienceList);

        return memberVO;
    }
}
