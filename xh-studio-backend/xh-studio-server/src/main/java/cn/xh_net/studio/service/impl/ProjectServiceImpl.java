package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.dto.ProjectDTO;
import cn.xh_net.studio.entity.Project;
import cn.xh_net.studio.mapper.ProjectMapper;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.service.IProjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static cn.xh_net.studio.constant.CommonConstant.NOT_DELETED;

/**
 * @author XingHai
 * @date 2026/7/24
 * @description 项目服务实现类
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements IProjectService {

    private final ProjectMapper projectMapper;


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
}
