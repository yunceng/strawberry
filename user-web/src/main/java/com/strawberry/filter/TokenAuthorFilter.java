package com.strawberry.filter;

import com.alibaba.fastjson.JSON;
import com.strawberry.common.FilterConstants;
import com.strawberry.conf.JWTAudienceConfig;
import com.strawberry.util.JwtHelper;
import com.strawberry.vo.ResultInfo;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 功能：根据token值进行拦截，如果没有token，则跳转到登录页面，否则不拦截
 *
 * @author alan.wang
 * @version 1.0.0
 * @since 2018.09.29
 *
 */
//Order中的value越小，优先级越高。
@Order(value = 2)
@WebFilter(urlPatterns = {"/ads/*"}, filterName = "tokenAuthorFilter")
public class TokenAuthorFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorFilter.class);

    @Autowired
    private JWTAudienceConfig jwtAudienceConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String token = req.getHeader(FilterConstants.REQUEST_HEADER_TOKEN_KEY);//header方式
        ResultInfo resultInfo = new ResultInfo();
        boolean isFilter = false;

        String method = ((HttpServletRequest) request).getMethod();

        if (method.equals("OPTIONS")) {
            rep.setStatus(HttpServletResponse.SC_OK);
        } else {
            if (null == token || token.isEmpty()) {
                resultInfo.setCode(ResultInfo.CodeType.UN_AUTHORIZED);
                resultInfo.setMsg("用户授权认证没有通过，客户端请求参数中无token信息");
            } else {
                Claims claims = JwtHelper.parseJWT(token, jwtAudienceConfig.getBase64Secret());
                if (claims != null) {
                    resultInfo.setCode(ResultInfo.CodeType.SUCCESS);
                    resultInfo.setMsg("用户授权认证通过");
                    isFilter = true;
                } else {
                    resultInfo.setCode(ResultInfo.CodeType.UN_AUTHORIZED);
                    resultInfo.setMsg("用户授权认证没有通过!客户端请求参数token信息无效");
                }
            }
            if (resultInfo.getCode() == ResultInfo.CodeType.UN_AUTHORIZED) {// 验证失败
                PrintWriter writer = null;
                OutputStreamWriter osw = null;
                try {
                    osw = new OutputStreamWriter(response.getOutputStream(),
                            "UTF-8");
                    writer = new PrintWriter(osw, true);
                    String jsonStr = JSON.toJSONString(resultInfo);
                    writer.write(jsonStr);
                    writer.flush();
                    writer.close();
                    osw.close();
                } catch (UnsupportedEncodingException e) {
                    LOG.error("过滤器返回信息失败:" + e.getMessage(), e);
                } catch (IOException e) {
                    LOG.error("过滤器返回信息失败:" + e.getMessage(), e);
                } finally {
                    if (null != writer) {
                        writer.close();
                    }
                    if (null != osw) {
                        osw.close();
                    }
                }
                return;
            }
            if (isFilter) {
                LOG.info("token filter过滤ok!");
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
