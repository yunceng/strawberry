package com.strawberry.filter;

import com.alibaba.fastjson.JSON;
import com.strawberry.util.TokenUtil;
import com.strawberry.vo.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

/**
 * 功能：根据token值防止重复提交
 *
 * @author alan.wnag
 * @version 1.0.0
 * @since 2018.09.29
 */
//Order中的value越小，优先级越高。
@Order(value = 1)
@WebFilter(urlPatterns = {"/ads/*"}, filterName = "tokenAuthorFilter")
public class TokenAuthorFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(TokenAuthorFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        rep.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        rep.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        rep.setHeader("Access-Control-Max-Age", "3600");
        rep.setHeader("Access-Control-Allow-Headers", "token,Origin, X-Requested-With, Content-Type, Accept");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String token = req.getHeader(TokenUtil.REQUEST_HEADER_TOKEN_KEY);//header方式
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
                TokenUtil.submitTokenCheck(token, req);
                if (true) {
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
