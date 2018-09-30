package com.strawberry.util;

import com.strawberry.exception.TokenException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 功能：Token生成与验证工具类
 *
 * @author alan.wnag
 * @since 2018.09.29
 * @version 1.0.0
 *
 */
public class TokenUtil {

    private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    //token放在session中的key
    public static final String SESSION_TOKEN_KEY = "session_token_key";
    //token放在header中的key
    public static final String REQUEST_HEADER_TOKEN_KEY = "token";

    /**
    * 计算token，将token放置在session里,防止表单重复提交
    * @param submitToken
    * @param request
    */
    public static String getSubmitToken(HttpServletRequest request, HttpServletResponse response) {
        // 计算token，将token放置在session里和返回页面，防止表单重复提交
        String submitToken = UUID.randomUUID().toString();
        request.getSession().setAttribute(SESSION_TOKEN_KEY, submitToken);
        response.addHeader(REQUEST_HEADER_TOKEN_KEY, submitToken);
//        ReflectUtils.reflectSetparam(request, REQUEST_HEADER_TOKEN_KEY, submitToken);
        logger.debug("获取新的token，URI:{}，QueryString:{},RequestURl:{}", request.getRequestURI(), request.getQueryString(), request.getRequestURL());
        logger.debug("获取的新token:{}", submitToken);
        logger.debug("获取的新sessionid:{}", request.getSession().getId());
        return submitToken;
    }

    /**
    * 提交订单验证
    */
    public static void submitTokenCheck(String submitToken, HttpServletRequest request) {
        logger.debug("校验的新sessionid:{}", request.getSession().getId());
        String tokenValue = (String) request.getSession().getAttribute(SESSION_TOKEN_KEY);
        logger.debug("校验token,参数：[submitToken:" + submitToken + ",tokenValue:" + tokenValue + "]");
        if (StringUtils.isBlank(submitToken)) {
            throw new TokenException("表单验证出错，请刷新页面重试！");
        }
        // 参数、session中都没用token值提示错误
        if (StringUtils.isBlank(submitToken) && StringUtils.isBlank(tokenValue)) {
        throw new TokenException("会话验证Token出错，请刷新页面重试！");
        } else if ((!StringUtils.isBlank(submitToken)) && StringUtils.isBlank(tokenValue)) {
        throw new TokenException("会话验证Token出错，请勿重复提交！");
        }

        synchronized (tokenValue) {
            tokenValue = (String) request.getSession().getAttribute(SESSION_TOKEN_KEY);
            if (tokenValue.equalsIgnoreCase(submitToken)) {
                request.getSession().removeAttribute(SESSION_TOKEN_KEY);
                return;
            } else {
                throw new TokenException("表单已经提交，请勿重复提交！");
            }
        }
    }
}
