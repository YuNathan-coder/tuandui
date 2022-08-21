package com.nathan.tuandui.sys.common.enums;

import lombok.AllArgsConstructor;

/**
 * @Author:NathanYu
 * @Description: 错误码枚举 ，用于返回前端
 * @Date: 2022/7/21 4:15
 * @param

 */
@AllArgsConstructor
public enum ErrorCodeEnums {

    /**
     * 失败
     */
    /**
     * 用于封装统一返回对象
     */

    CODE_NO(-1, "失败"),
    USERNAME_OR_PASSWORD_ERROR(1001, "用户名或密码错误"),
    USER_NOT_EXISTS(1002, "用户不存在"),
    TOKEN_EXPIRED(1003, "token 过期,请重新登录"),
    PHONE_FORMAT_ERROR(1004, "手机号码格式错误"),
    SMS_CODE_ERROR(1005, "手机验证码输入错误"),
    NO_PERMISSION(1006, "没有权限"),
    OLD_PASSWORD_ERROR(1007, "原密码输入错误"),
    TWO_PASSWORD_NOT_SAME(1008, "确认密码与新密码不同"),
    TOKEN_GENERATE_FAILURE(1009, "token生成失败"),
    PHONE_REGISTERED(1010, "手机号已被其他用户注册"),
    FACTORYNUMBER_EXISTS(1011,"厂家编号已经存在"),
    TOKEN_NULL_ERROR(1012,"token为空"),


    STYLE_NUMBER_NOT_EXISTS(2001, "货号为空"),
    ;





    public final Integer code;
    public final String message;
}
