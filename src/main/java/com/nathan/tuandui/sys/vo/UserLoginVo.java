package com.nathan.tuandui.sys.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nathan.tuandui.sys.common.consts.Constast;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserLoginVo  implements Serializable {


        private static final long serialVersionUID=1L;

        /**
         * 用户id
         */
        @TableId(value = "id", type = IdType.AUTO)
        private Integer id;
        /**
         * 用户唯一标识
         */
        private String openId;

        /**
         * 用户姓名
         */
        private String name;

        /**
         * 登录名
         */
        private String loginName;

        /**
         * 密码
         */
        private String password;
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
        /**
         * 盐加密
         */
        private String salt;
        /**
         * 注册时间
         */
        @DateTimeFormat(pattern = Constast.TIME_FORM)
        private Date registeredTime;

        /**
         * 厂家编号（唯一）
         */
        private String factoryNumber;

    /**
     * token
     */

    private String token;




}
