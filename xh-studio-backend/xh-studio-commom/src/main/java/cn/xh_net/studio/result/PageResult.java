package cn.xh_net.studio.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页响应结果
 * @author XingHai
 * @date 2026/7/12
 * @description 分页响应结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    // 总记录数
    private Long total;

    // 分页数据
    private List<T> data;
}
