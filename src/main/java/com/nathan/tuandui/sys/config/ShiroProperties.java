package com.nathan.tuandui.sys.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author shizewang
 * @version 1.0
 * @description： 关于shiro的配置参数
 * @date 2021/2/20 10:19 下午
 */
@Data
@Configuration
public class ShiroProperties {

    /**
     * session超时时间，默认30分钟
     */
    @Value("${shiro.session-timeout}")
    private Long sessionTimeoutInSeconds;
    /**
     * remember me 功能使用
     */
    @Value("${shiro.cookie-timeout}")
    private Long cookieTimeoutInSeconds;
    /**
     * 可以匿名访问的URL，多个使用,隔开
     */
    @Value("${shiro.anon-url}")
    private String anonUrl;
    /**
     * 登录URL
     */
    @Value("${shiro.login-url}")
    private String loginUrl;
    /**
     * 首页URL
     */
    @Value("${shiro.success-url}")
    private String successUrl;
    /**
     * 没有权限跳转URL
     */
    @Value("${shiro.unauthorized-url}")
    private String unauthorizedUrl;
}
