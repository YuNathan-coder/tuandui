package com.nathan.tuandui.sys.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author shizewang
 * @version 1.0
 * @description： 角色枚举
 * @date 2021/2/21 1:46 上午
 */
@AllArgsConstructor
public enum RoleEnums {

    /**
     * 客户
     */
    CUSTOMER(2, "CUSTOMER"),
    /**
     * 销售管理员
     */
    SALE_MANAGER(1, "SALE_MANAGER"),
    /**
     * 超级管理员
     */
    SUPER_ADMIN(0, "SUPER_ADMIN"),
    /**
     * 厂家
     */
    FACTORY(3, "FACTORY"),
    ;
    public final Integer type;
    public final String role;
}
