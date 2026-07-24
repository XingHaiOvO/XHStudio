package cn.xh_net.studio.service;

import cn.xh_net.studio.dto.ProjectDTO;
import cn.xh_net.studio.entity.Project;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.vo.ProjectVO;


public interface IProjectService {

    /**
     * 获取项目列表
     * @param projectDTO 项目查询参数
     * @return 项目列表
     */
    PageResult<Project> getProjectList(ProjectDTO projectDTO);

    /**
     * 获取项目详情
     * @param id 项目ID
     * @return 项目详情
     */
    ProjectVO getProjectById(Long id);
}
