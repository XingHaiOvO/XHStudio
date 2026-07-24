package cn.xh_net.studio.mapper;

import cn.xh_net.studio.entity.ProjectMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 项目成员映射接口
 * @author XingHai
 * @date 2026/7/24
 * @description 项目成员映射接口
 */
@Mapper
public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {

    /**
     * 根据项目ID查询项目成员ID列表
     * @param projectId 项目ID
     * @return 项目成员ID列表
     */
    @Select("select user_id from project_member where project_id = #{projectId}")
    List<Long> selectUserIdListByProjectId(Long projectId);

}
