package cn.xh_net.studio.service.impl;

import cn.xh_net.studio.entity.Work;
import cn.xh_net.studio.mapper.WorkMapper;
import cn.xh_net.studio.service.IWorkService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.xh_net.studio.constant.CommonConstant.NOT_DELETED;
import static cn.xh_net.studio.constant.CommonConstant.PUBLIC;

/**
 * 作品服务实现类
 * @author XingHai
 * @date 2026/7/24
 * @description 作品服务实现类
 */
@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements IWorkService {

    private final WorkMapper workMapper;

    /**
     * 获取公开作品列表
     * @param userId 成员ID
     * @return 公开作品列表
     */
    @Override
    public List<Work> getPublicWorkListByUserId(Long userId) {
        return workMapper.selectList(new LambdaQueryWrapper<Work>()
                .eq(Work::getUserId, userId)
                .eq(Work::getPublicStatus, PUBLIC)
                .eq(Work::getDeleted, NOT_DELETED));
    }
}
