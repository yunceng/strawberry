package com.strawberry.conf;

import com.strawberry.interceptor.PagePluginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能：注入与MyBatis相关的实例
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.27
 */
@Configuration
public class MyBatisConfig {

    /**
     * 注入分页插件的实例
     *
     * @return
     */
    @Bean
    public PagePluginInterceptor setPagePlugin() {
        return new PagePluginInterceptor();
    }
}
