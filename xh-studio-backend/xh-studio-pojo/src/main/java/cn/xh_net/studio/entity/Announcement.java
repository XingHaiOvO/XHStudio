package cn.xh_net.studio.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公告实体类
 * @author XingHai
 * @date 2026/7/12 12:38
 * @description 公告实体类
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Announcement implements Serializable {

    // 主键
    private Long id;

    // 标题
    private String title;

    // 内容(富文本)
    private String content;

    // 是否置顶(0:否,1:是)
    @TableField("is_top")
    private Integer top;

    // 发布时间
    private LocalDateTime publishTime;

    // 是否删除(0:否,1:是)
    private Integer deleted;

    // 创建时间
    private LocalDateTime createTime;

    // 更新时间
    private LocalDateTime updateTime;
}
