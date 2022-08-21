package com.nathan.tuandui.sys.vo;

import com.nathan.tuandui.sys.common.consts.Constast;
import com.nathan.tuandui.sys.entity.User;
import lombok.Data;

@Data
public class UserVo  {

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;
    /**
     * 用户类型
     * 0：超级管理员
     * 1：销售
     * 2：客户
     * 3：厂家
     */
    private Integer type;



}
