package com.nathan.tuandui.sys.authentication;

import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author shizewang
 * @version 1.0
 * @description： 用于校验用户是否登录
 * @date 2021/2/21 5:14 下午
 */
@RequiredArgsConstructor
public class CustomizeAuthenticationToken implements AuthenticationToken {

    private final String token;


    /**
     * 认证
     * @return
     */
    @Override
    public Object getPrincipal() {
        return this.token;
    }

    /**
     * 授权
     * @return
     */
    @Override
    public Object getCredentials() {
        return this.token;
    }
}
