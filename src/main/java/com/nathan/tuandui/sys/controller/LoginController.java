package com.nathan.tuandui.sys.controller;

import com.nathan.tuandui.sys.common.consts.Constast;
import com.nathan.tuandui.sys.common.enums.ErrorCodeEnums;
import com.nathan.tuandui.sys.common.holder.UserHolder;
import com.nathan.tuandui.sys.common.utils.OptResult;
import com.nathan.tuandui.sys.common.utils.TokenGenerator;
import com.nathan.tuandui.sys.common.utils.UserToken2UserLoginVoConverter;
import com.nathan.tuandui.sys.entity.User;
import com.nathan.tuandui.sys.service.LoginfoService;
import com.nathan.tuandui.sys.service.UserService;
import com.nathan.tuandui.sys.vo.UserLoginVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@Validated
public class LoginController {

    @Autowired
    private LoginfoService loginfoService;

    @Autowired
    private UserService userService;


    private final RedisTemplate<String, UserLoginVo> userLoginVoRedisTemplate;


    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 如果登录成功, 就会返回用户的信息
     */
    @PostMapping(value = "/login")
    public OptResult login(@RequestParam(value = "loginName") String username,
                           @RequestParam(value = "password") String password) {

        UsernamePasswordToken token = null;

        token = new UsernamePasswordToken(username, password, false);

        try {
            SecurityUtils.getSubject().login(token);
            User user = UserHolder.get();
            // 获取token
            String userToken = TokenGenerator.generateValue();
            UserLoginVo vo = UserToken2UserLoginVoConverter.convert(user, userToken);
            // 放入 redis, 8小时后过期
            userLoginVoRedisTemplate.opsForValue().set(userToken, vo, 8, TimeUnit.HOURS);
            return OptResult.ok(vo);
        } catch (AuthenticationException e) {
            log.info("账号密码错误");
            return OptResult.error(ErrorCodeEnums.USERNAME_OR_PASSWORD_ERROR);
        }
    }



}
