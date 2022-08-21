package com.nathan.tuandui.sys.authentication;


import com.google.gson.Gson;
import com.nathan.tuandui.sys.common.enums.ErrorCodeEnums;
import com.nathan.tuandui.sys.common.utils.OptResult;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:NathanYu
 * @Description: 自定义的认证过滤器
 * @Date: 2022/7/24 11:15
 * @param

 */
@Slf4j
public class CustomizeShiroFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {
        String token = getToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        return new CustomizeAuthenticationToken(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS)) {
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        {
            String token = getToken((HttpServletRequest) request);
            // 没有token
            if (StringUtils.isBlank(token)) {
                log.info("请求头中没有 token...");
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setContentType("application/json;charset=utf-8");
                httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
                httpResponse.setHeader("Access-Control-Allow-Origin"
                        , ((HttpServletRequest) request).getHeader("Origin"));

                String json = new Gson().toJson(
                        new OptResult(ErrorCodeEnums.TOKEN_NULL_ERROR.code,
                                ErrorCodeEnums.TOKEN_NULL_ERROR.message));
                httpResponse.getWriter().print(json);
                return false;
            }
            // 走到自定义的realm
            return executeLogin(request, response);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin",
                ((HttpServletRequest) request).getHeader("Origin"));
        try{
            OptResult result = null;
            if (e instanceof ExpiredCredentialsException) {
                result = new OptResult(ErrorCodeEnums.TOKEN_EXPIRED.code,ErrorCodeEnums.TOKEN_EXPIRED
                        .message);
            }
            String json = new Gson().toJson(result);
            httpResponse.getWriter().print(json);
        } catch (IOException exception) {

        }
        return false;
    }

    /**
     * 在请求头中获取token，如果没有就去参数找
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        return token;
    }
}
