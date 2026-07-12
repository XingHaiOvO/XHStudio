package cn.xh_net.studio.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志实体类
 * @author XingHai
 * @date 2026/7/12 15:47
 * @description 操作日志实体类
 * /
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog implements Serializable {

    // 主键
    private Long id;

    // 用户ID
    private Long userId;

    // 用户名
    private String username;

    // 操作内容
    private String operation;

    // 操作方法
    private String method;

    // 操作参数
    private String params;

    // 操作IP
    private String ip;

    // 操作结果
    private String result;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
