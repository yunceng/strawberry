package com.strawberry.common;

/**
 * Redis缓存中的key常量管理类
 *
 * @author alan.wang
 *
 * @since 2018.09.27
 *
 * @version 1.0.0
 *
 */
public class RedisKeyConstants {

    //系统配置信息
    public static final String SYSTEM_CONFIG_MESSAGE = "system_config_message";
    //是否允许访问系统
    public static final String IS_ALLOW_ACCESS_SYSTEM = "is_allow_access_system";
    //访问系统的开始时间
    public static final String ALLOW_ACCESS_SYSTEM_START_TIME = "allow_access_system_start_time";
    //访问系统的结束时间
    public static final String ALLOW_ACCESS_SYSTEM_END_TIME = "allow_access_system_end_time";

}
