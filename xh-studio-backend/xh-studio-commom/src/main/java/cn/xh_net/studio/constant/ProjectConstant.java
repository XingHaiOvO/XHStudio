package cn.xh_net.studio.constant;

/**
 * 项目常量类
 * @author XingHai
 * @date 2026/7/12 16:05
 * @description 项目常量类
 */
public class ProjectConstant {

    // 项目状态（0：计划中，1：开发中，2：已完成）
    public static final Integer STATUS_PLAN = 0;
    public static final Integer STATUS_DEV = 1;
    public static final Integer STATUS_COMPLETE = 2;

    // 项目进度（0-100）
    public static final Integer PROGRESS_MIN = 0;
    public static final Integer PROGRESS_MAX = 100;

    // 项目成员角色（PARTICIPANT：普通成员，OWNER：项目负责人）
    public static final String ROLE_MEMBER = "PARTICIPANT";
    public static final String ROLE_LEADER = "OWNER";

    // 项目里程碑状态（0：未完成，1：已完成）
    public static final Integer MILESTONE_STATUS_UNCOMPLETED = 0;
    public static final Integer MILESTONE_STATUS_COMPLETED = 1;
}
