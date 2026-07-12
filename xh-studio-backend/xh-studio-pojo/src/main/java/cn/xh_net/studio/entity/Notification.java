package cn.xh_net.studio.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知实体类
 * @author XingHai
 * @date 2026/7/12 15:44
 * @description 通知实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

    // 主键
    private Long id;

    // 用户ID
    private Long userId;

    // 通知标题
    private String title;

    // 通知内容
    private String content;

    // 通知状态（0：未读，1：已读）
    @TableField("is_read")
    private Integer readStatus;

    // 通知类型
    private Integer type;

    // 批次ID
    private String batchId;

    // 发送人ID
    private Long senderId;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
