package cn.xh_net.studio.result;

import lombok.Data;

import java.util.List;

/**
 * 分页响应结果
 */
@Data
public class PageResult {
    // 总记录数
    private Integer total;

    // 分页数据
    private List<?> data;
}
