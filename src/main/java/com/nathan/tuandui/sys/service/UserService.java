package com.nathan.tuandui.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nathan.tuandui.sys.common.utils.OptResult;
import com.nathan.tuandui.sys.entity.User;
import com.nathan.tuandui.sys.vo.UserVo;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 根据用户名查找用户
     * @param loginName
     * @return
     */
    User findByLoginName (String loginName);

    User findById (Integer id);

    /**
     * 添加用户
     * @param
     * @return
     */
    OptResult addUser (UserVo userVo);


    /**
     * 根据用户id重置用户密码
     * @param id
     * @return
     */
    OptResult restPassword (Integer id);

    /**
     * 修改密码
     * @param id
     * @return
     */
    OptResult changePassword (Integer id, String oldPassword, String newPassword);

    /**
     * 查询所有用户
     * 根据电话进行模糊查询
     * 根据姓名进行模糊查询
     * 根据厂家编号查询用户
     * @param user
     * @return
     */
    List<User> findAllUser (User user);

    /**
     * @Author:NathanYu
     * @Description: 根据厂家查询是否有是否有对应的用户
     * @Date: 2022/7/22 6:31
     * @param factoryNumber
     * @return Boolean
     */
    Boolean findUserByFactoryNumber (String factoryNumber);

    Boolean setFactoryNumber (Integer id, String factoryNumber);

}
