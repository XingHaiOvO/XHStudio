package cn.xh_net.studio.mapper;

import cn.xh_net.studio.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * @author XingHai
 * @date 2026/7/13
 * @description 用户Mapper接口
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
