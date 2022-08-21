package com.nathan.tuandui.sys.common.utils;

import com.nathan.tuandui.sys.entity.User;
import com.nathan.tuandui.sys.vo.UserLoginVo;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author shizewang
 * @version 1.0
 * @description：
 * @date 2021/2/21 1:36 上午
 */
public class UserToken2UserLoginVoConverter {

    public static UserLoginVo convert(User user, String token)  {

        UserLoginVo userLoginVo = new UserLoginVo();
        BeanUtils.copyProperties(user,userLoginVo);
        userLoginVo.setToken(token);
        return userLoginVo;
    }
}
