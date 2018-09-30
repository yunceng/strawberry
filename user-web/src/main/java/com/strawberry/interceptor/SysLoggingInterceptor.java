package com.strawberry.interceptor;

import com.strawberry.util.GeoIpUtils;
import com.strawberry.util.RequestMessageUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能：记录系统访问日志
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.28
 */
public class SysLoggingInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("记录系统访问日志拦截开始");
        String ip = RequestMessageUtils.getIpAddress(request);

        String countryCode = GeoIpUtils.getCountryCode(ip);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("记录系统访问日志拦截结束");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("记录系统访问日志拦截销毁");
        super.afterCompletion(request, response, handler, ex);
    }
}
