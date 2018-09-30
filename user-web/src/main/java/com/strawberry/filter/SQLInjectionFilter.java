package com.strawberry.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * 功能：防止SQL注入
 *
 * @author alan.wnag
 * @version 1.0.0
 * @since 2018.09.30
 *
 */
@Order(value = 1)
@WebFilter(urlPatterns = {"/*"}, filterName = "sqlInjectionFilter")
public class SQLInjectionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
