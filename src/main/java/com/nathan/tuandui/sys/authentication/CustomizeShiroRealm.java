package com.nathan.tuandui.sys.authentication;

import com.nathan.tuandui.sys.common.consts.Constast;
import com.nathan.tuandui.sys.common.enums.RoleEnums;
import com.nathan.tuandui.sys.common.holder.UserHolder;
import com.nathan.tuandui.sys.entity.User;
import com.nathan.tuandui.sys.service.UserService;
import com.nathan.tuandui.sys.vo.UserLoginVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;


/**
 * @Author:NathanYu
 * @Description: 自定义realm 包含认证和授权
 * @Date: 2022/7/24 11:05
 * @param

 */
@Slf4j
@Component
@RequiredArgsConstructor


public class CustomizeShiroRealm extends AuthorizingRealm {
    @Autowired
    private  UserService userService;

    private final RedisTemplate<String, UserLoginVo> userLoginVoRedisTemplate;


    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomizeAuthenticationToken
                || token instanceof UsernamePasswordToken;
    }

    /**
     * 用户授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info  = new SimpleAuthorizationInfo();
        User user = (User) principalCollection.getPrimaryPrincipal();
        Integer roleId = user.getType();
        String roleName = "";

        if (roleId.equals(RoleEnums.SUPER_ADMIN.type) ) {
            roleName = RoleEnums.SUPER_ADMIN.role;
        } else if (roleId.equals(RoleEnums.SALE_MANAGER.type)) {
            roleName = RoleEnums.SALE_MANAGER.role;
        } else if (roleId.equals((RoleEnums.CUSTOMER.type))) {
            roleName = RoleEnums.CUSTOMER.role;
        } else if (roleId.equals(RoleEnums.FACTORY.type)) {
            roleName = RoleEnums.FACTORY.role;
        }

        log.info("用户角色为{}" ,roleName);
        info.setRoles(Collections.singleton(roleName));


        return info;



    }

    /**
     * 用户认证
     *
     * @param authenticationToken 身份认证token,目前使用 UsernamePasswordToken
     * @return AuthenticationInfo 身份认证的信息
     * @throws AuthenticationException 身份失败抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof UsernamePasswordToken) {
            //登录
            return executeLogin((UsernamePasswordToken) authenticationToken);

        } else if (authenticationToken instanceof CustomizeAuthenticationToken) {
            //token
            return executeVerify((CustomizeAuthenticationToken) authenticationToken);
        }


        throw  new AuthenticationException();
    }


    /**
     * @Author:NathanYu
     * @Description: 登录的验证
     * @Date: 2022/7/24 11:33
     * @param usernamePasswordToken
     * @return AuthenticationInfo
     */
    private AuthenticationInfo executeLogin( UsernamePasswordToken usernamePasswordToken) {

        //账号
        String username = (String) usernamePasswordToken.getPrincipal();
        //密码
        String credential = new String((char[]) usernamePasswordToken.getCredentials());
        log.info("用户 {} 登录....",username);

        User user = userService.findByLoginName(username);
        //未有该用户
        if (user == null) {
            log.info("用户不存在");
            throw new UnknownAccountException();
        }

        String salt = user.getSalt();
        String passwordInDB = user.getPassword();
        String nowPassword = new Md5Hash(credential,salt, Constast.HASHITERATIONS).toString();

        //判断密码是否正确
        if (! nowPassword.equals(passwordInDB)) {
            throw new IncorrectCredentialsException();
        }

        UserHolder.set(user);
        return new SimpleAuthenticationInfo(user, credential, getName());

    }

    /**
     * @Author:NathanYu
     * @Description: 校验是否登录
     * @Date: 2022/7/24 12:00
     * @param authenticationToken
     * @return AuthenticationInfo
     */

    private AuthenticationInfo executeVerify (CustomizeAuthenticationToken authenticationToken) {

        //获得token
        String token = (String) authenticationToken.getPrincipal();
        UserLoginVo vo = userLoginVoRedisTemplate.opsForValue().get(token);
        if (vo == null) {
            log.info("token 过期....");
            throw new ExpiredCredentialsException();
        }

        User user = new User();

        user = userService.findById(vo.getId());

        UserHolder.set(user);

        return new SimpleAuthenticationInfo(user, token, getName());


    }

}


