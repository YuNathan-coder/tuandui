package com.nathan.tuandui.sys.common.holder;


import com.nathan.tuandui.sys.entity.User;

/**
 * @author shizewang
 * @version 1.0
 * @description： 保存当前用户的信息
 * @date 2021/2/21 9:52 上午
 */
public class UserHolder {

    private static final ThreadLocal<User> USER_LOCAL = new InheritableThreadLocal<>();
    protected UserHolder(){
    }

    public static void set(User user){
        if (user == null) {
            return;
        }
        USER_LOCAL.set(user);
    }
    public static User get(){
        return USER_LOCAL.get();
    }

    public static void remove(){
        USER_LOCAL.remove();
    }
}
