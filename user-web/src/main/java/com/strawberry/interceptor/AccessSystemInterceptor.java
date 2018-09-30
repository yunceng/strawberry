package com.strawberry.interceptor;

import com.strawberry.common.RedisKeyConstants;
import com.strawberry.common.RedisValueConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

/**
 * 功能：访问系统时的拦截器
 * 1、当系统处于维护期间禁止访问系统，调转到系统维护页面
 * 2、在系统设定时间间隔内允许访问系统，其他时间段禁止
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.28
 */
public class AccessSystemInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(AccessSystemInterceptor.class);

    //允许访问时间段开始
    private int startTime;
    //允许访问时间段结束
    private int endTime;
    //系统是否处于维护期间
    private boolean flag;

    private RedisTemplate redisTemplate;

    public AccessSystemInterceptor(RedisTemplate redisTemplate) {
        super();
        this.redisTemplate = redisTemplate;
        initConfig();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("访问系统拦截开始");
        Calendar cal = Calendar.getInstance();
        //获得当前时间对应的小时数,例如：12:05-->12,13:15-->13
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        if (flag) {
            if (startTime <= hour && hour < endTime) {
                return true;  //通过拦截器，继续执行请求
            } else {
                //给定的时间之外禁止登录
                request.setAttribute("msg", "访问时间不合法，请在规定时间内访问系统");
                request.getRequestDispatcher("/system_maintenance.html").forward(request, response);
                LOG.info("访问时间不合法，请在规定时间内访问系统");
                return false;  //没有通过拦截器，返回登录页面
            }
        } else {
            //给定的时间之外禁止登录
            request.setAttribute("msg", "系统维护中，给您带来了不便，请见谅");
            request.getRequestDispatcher("/system_maintenance.html").forward(request, response);
            LOG.info("系统维护中，给您带来了不便，请见谅");
            return false;  //没有通过拦截器，返回登录页面
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("访问系统拦截结束");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("访问系统拦截销毁");
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 初始化访问拦截器需要的信息
     */
    private void initConfig() {
        redisTemplate.opsForHash().putIfAbsent(RedisKeyConstants.SYSTEM_CONFIG_MESSAGE,
                RedisKeyConstants.IS_ALLOW_ACCESS_SYSTEM,
                RedisValueConstants.DEFAULT_IS_ALLOW_ACCESS_SYSTEM);
        redisTemplate.opsForHash().putIfAbsent(RedisKeyConstants.SYSTEM_CONFIG_MESSAGE,
                RedisKeyConstants.ALLOW_ACCESS_SYSTEM_START_TIME,
                RedisValueConstants.DEFAULT_ACCESS_SYSTEM_START_TIME);
        redisTemplate.opsForHash().putIfAbsent(RedisKeyConstants.SYSTEM_CONFIG_MESSAGE,
                RedisKeyConstants.ALLOW_ACCESS_SYSTEM_END_TIME,
                RedisValueConstants.DEFAULT_ACCESS_SYSTEM_END_TIME);

        this.flag = Boolean.valueOf(redisTemplate.opsForHash().get(RedisKeyConstants.SYSTEM_CONFIG_MESSAGE,
                RedisKeyConstants.IS_ALLOW_ACCESS_SYSTEM).toString());
        try {
            this.startTime = Integer.valueOf(redisTemplate.opsForHash().get(RedisKeyConstants.SYSTEM_CONFIG_MESSAGE,
                    RedisKeyConstants.ALLOW_ACCESS_SYSTEM_START_TIME).toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LOG.error(String.format("访问系统时的拦截器中的开始时间设置出现异常，您设置的该值是：%s, 但是该时间只能是0-24中的任意一个数值",
                    redisTemplate.opsForValue().get(RedisKeyConstants.ALLOW_ACCESS_SYSTEM_START_TIME)));
            this.startTime = Integer.valueOf(RedisValueConstants.DEFAULT_ACCESS_SYSTEM_START_TIME);
        }

        try {
            this.endTime = Integer.valueOf(redisTemplate.opsForHash().get(RedisKeyConstants.SYSTEM_CONFIG_MESSAGE,
                    RedisKeyConstants.ALLOW_ACCESS_SYSTEM_END_TIME).toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LOG.error(String.format("访问系统时的拦截器中的结束时间设置出现异常，您设置的该值是：%s, 但是该时间只能是0-24中的任意一个数值",
                    redisTemplate.opsForValue().get(RedisKeyConstants.ALLOW_ACCESS_SYSTEM_END_TIME)));
            this.endTime = Integer.valueOf(RedisValueConstants.DEFAULT_ACCESS_SYSTEM_END_TIME);
        }
    }
}
