package com.nathan.tuandui.sys.config;


import com.nathan.tuandui.sys.authentication.CustomizeShiroFilter;
import com.nathan.tuandui.sys.authentication.CustomizeShiroRealm;
import com.nathan.tuandui.sys.common.consts.Constast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * @Author:NathanYu
 * @Description: shiro 配置器
 * @Date: 2022/7/24 10:58
 * @param

 */
@Configuration
public class ShiroConfig {


    /**
     * 配置会话的管理器
     * @param shiroProperties
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager (@Lazy ShiroProperties shiroProperties) {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        //设置会话的时间
        sessionManager.setGlobalSessionTimeout(shiroProperties.getSessionTimeoutInSeconds());

        return sessionManager;

    }

    /**
     * 配置安全管理器
     * @param defaultWebSessionManager 会话管理器
     * @param customizeShiroRealm realm认证
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(DefaultWebSessionManager defaultWebSessionManager,
                                                     @Lazy CustomizeShiroRealm customizeShiroRealm) {
        customizeShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(defaultWebSessionManager);
        securityManager.setRealm(customizeShiroRealm);

        return securityManager;

    }


    /**
     * @Author:NathanYu
     * @Description: 过滤器管理器
     * @Date: 2022/7/24 11:23
     * @param securityManager
     * @param shiroProperties
     * @return ShiroFilterFactoryBean
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean (
            DefaultWebSecurityManager securityManager,
            @Lazy ShiroProperties shiroProperties
    ) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //之定义过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("customizeShiroFilter", new CustomizeShiroFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //登录的url
        shiroFilterFactoryBean.setLoginUrl(shiroProperties.getLoginUrl());
        //登录成功的url
        shiroFilterFactoryBean.setSuccessUrl(shiroProperties.getSuccessUrl());

        //未授权
        shiroFilterFactoryBean.setUnauthorizedUrl(shiroProperties.getUnauthorizedUrl());

        shiroFilterFactoryBean.setFilterChainDefinitionMap(getFilterChainDefinitionMap(shiroProperties));

        return shiroFilterFactoryBean;

    }

    private LinkedHashMap<String, String> getFilterChainDefinitionMap(ShiroProperties shiroProperties) {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        // 允许匿名访问的URL
        String[] anonUrls = shiroProperties.getAnonUrl().split(",");
        for (String url : anonUrls) {
            map.put(url, "anon");
        }
        map.put(shiroProperties.getLoginUrl(), "anon");
        map.put("/**", "customizeShiroFilter");
        return map;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator =
                new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor advisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    /**
     * 加密设置
     * @return
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(Constast.HASHITERATIONS);

        return credentialsMatcher;
    }






}
