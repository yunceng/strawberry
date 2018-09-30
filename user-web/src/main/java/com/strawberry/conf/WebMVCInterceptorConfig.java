package com.strawberry.conf;

import com.strawberry.interceptor.AccessSystemInterceptor;
import com.strawberry.interceptor.LoginInterceptor;
import com.strawberry.interceptor.SysLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * 功能：注册拦截器
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.27
 */
@Configuration
public class WebMVCInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public AccessSystemInterceptor getAccessSystemInterceptor() {
        return new AccessSystemInterceptor(redisTemplate);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册访问系统拦截器
        registry.addInterceptor(getAccessSystemInterceptor())
                .addPathPatterns("/ads/**");

        registry.addInterceptor(new SysLoggingInterceptor())
                .addPathPatterns("/ads/**");

        //注册登录拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns("api/path/login")
                .addPathPatterns("/login/**");
    }
}
