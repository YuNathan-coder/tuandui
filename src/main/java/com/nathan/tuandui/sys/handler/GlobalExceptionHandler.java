package com.nathan.tuandui.sys.handler;


import com.nathan.tuandui.sys.common.enums.ErrorCodeEnums;
import com.nathan.tuandui.sys.common.utils.OptResult;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕捉器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     *认证异常
     */


    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public OptResult handleAuthenticationException (AuthenticationException exception) {
        //用户不存在
        if (exception instanceof UnknownAccountException) {
            return OptResult.error(ErrorCodeEnums.USER_NOT_EXISTS);
        } else if (exception instanceof IncorrectCredentialsException) {
            return OptResult.error(ErrorCodeEnums.USERNAME_OR_PASSWORD_ERROR);
        } else if (exception instanceof ExpiredCredentialsException){
            return OptResult.error(ErrorCodeEnums.TOKEN_EXPIRED);
        }

        return OptResult.error("认证异常");

    }

}
