package cn.xh_net.studio.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.xh_net.studio.dto.ProjectDTO;
import cn.xh_net.studio.entity.Project;
import cn.xh_net.studio.entity.ProjectMember;
import cn.xh_net.studio.entity.Milestone;
import cn.xh_net.studio.mapper.ProjectMapper;
import cn.xh_net.studio.mapper.ProjectMemberMapper;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.service.IMilestoneService;
import cn.xh_net.studio.service.IProjectService;
import cn.xh_net.studio.vo.ProjectMemberVO;
import cn.xh_net.studio.vo.ProjectVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.xh_net.studio.constant.CommonConstant.NOT_DELETED;
import static cn.xh_net.studio.constant.ProjectConstant.ROLE_MEMBER;

/**
 * @author XingHai
 * @date 2026/7/24
 * @description 项目服务实现类
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectMapper projectMapper;

    private final ProjectMemberMapper projectMemberMapper;

    private final IMilestoneService milestoneService;


    /**
     * 获取项目列表
     * @param projectDTO 项目查询参数
     * @return 项目列表
     */
    @Override
    public PageResult<Project> getProjectList(ProjectDTO projectDTO) {
        // 构造分页查询
        Page<Project> page = new Page<>(projectDTO.getPage(), projectDTO.getSize());

        // 构造查询条件
        QueryWrapper<Project> wrapper = new QueryWrapper<>();

        // 项目状态
        wrapper.eq(projectDTO.getStatus() != null, "status", projectDTO.getStatus());

        // 搜索关键词
        wrapper.like("title", projectDTO.getKeyword());

        // 项目是否删除
        wrapper.eq("deleted", NOT_DELETED);
        projectMapper.selectPage(page, wrapper);
        return new PageResult<>(page.getTotal(), page.getRecords());
    }

    /**
     * 获取项目详情
     * @param id 项目ID
     * @return 项目详情
     */
    @Override
    public ProjectVO getProjectById(Long id) {

        // 构造项目VO
        ProjectVO projectVO = new ProjectVO();

        // 查询项目
        Project project = projectMapper.selectById(id);
        if (project == null) {
            throw new RuntimeException("项目不存在");
        }
        BeanUtil.copyProperties(project, projectVO);

        // 查询项目成员
        List<ProjectMember> projectMemberList = projectMemberMapper.
                selectList(new QueryWrapper<ProjectMember>().eq("project_id", id));
        List<ProjectMemberVO> projectMemberVOList = projectMemberList.stream()
                .map(projectMember -> {
            ProjectMemberVO projectMemberVO = new ProjectMemberVO();
            projectMemberVO.setId(projectMember.getUserId());
            projectMemberVO.setNickname(projectMember.getUserNickname());
            projectMemberVO.setAvatar(projectMember.getUserAvatar());
            projectMemberVO.setRoleInProject(ROLE_MEMBER
                    .equals(projectMember.getRoleInProject()) ? "普通成员" : "负责人");
            return projectMemberVO;
        }).toList();
        projectVO.setMembers(projectMemberVOList);

        // 查询项目里程碑
        List<Milestone> milestoneList = milestoneService.getMilestoneListByProjectId(id);
        projectVO.setMilestones(milestoneList);

        return projectVO;
    }
}
