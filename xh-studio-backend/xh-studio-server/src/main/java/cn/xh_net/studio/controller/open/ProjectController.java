package cn.xh_net.studio.controller.open;

import cn.xh_net.studio.dto.ProjectDTO;
import cn.xh_net.studio.entity.Project;
import cn.xh_net.studio.result.PageResult;
import cn.xh_net.studio.result.Result;
import cn.xh_net.studio.service.IProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author XingHai
 * @date 2026/7/24
 * @description 项目控制器
 */
@Slf4j
@RestController
@RequestMapping("/public/project")
@RequiredArgsConstructor
public class ProjectController {

    private final IProjectService projectService;

    /**
     * 获取项目列表
     * @param projectDTO 项目查询参数
     * @return 项目列表
     */
    @GetMapping("/list")
    public Result<PageResult<Project>> getProjectList (ProjectDTO projectDTO) {
        log.info("获取项目列表，参数：{}", projectDTO);
        return Result.success(projectService.getProjectList(projectDTO));
    }

}
