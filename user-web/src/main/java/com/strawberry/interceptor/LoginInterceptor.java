package com.strawberry.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 功能：登录拦截器
 *      如果已经登录，则直接访问；否则进入登录页面
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.27
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(HandlerInterceptorAdapter.class);

    /**
     * 进入controller层之前拦截请求
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        LOG.info("---------------------开始进入请求地址拦截----------------------------");
        HttpSession session = request.getSession();
        if (!StringUtils.isEmpty(session.getAttribute("username"))) {
            return true;
        } else {
            request.setAttribute("msg", "会话已经过期，请重新登录");
            request.getRequestDispatcher("/login.html").forward(request, response);
            LOG.info("会话已经过期，请重新登录");
            return false;
        }
    }

    //在后端控制器执行后调用
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("--------------处理请求完成后视图渲染之前的处理操作---------------");
        super.postHandle(request, response, handler, modelAndView);
    }

    //整个请求执行完成后调用
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOG.info("---------------视图渲染之后的操作-------------------------0");
        super.afterCompletion(request, response, handler, ex);
    }

}
