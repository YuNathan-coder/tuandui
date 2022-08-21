package com.nathan.tuandui.sys.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nathan.tuandui.sys.common.consts.Constast;
import com.nathan.tuandui.sys.common.enums.ErrorCodeEnums;
import com.nathan.tuandui.sys.common.utils.OptResult;
import com.nathan.tuandui.sys.entity.User;
import com.nathan.tuandui.sys.mapper.UserMapper;
import com.nathan.tuandui.sys.service.UserService;
import com.nathan.tuandui.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserSerciceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public User findByLoginName(String loginName) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name",loginName);
        return super.getOne(queryWrapper,false);
    }

    @Override
    public User findById(Integer id) {
        return super.getById(id);
    }

    @Override
    public OptResult addUser(UserVo userVo) {

        User user = new User();
        if(findByLoginName(userVo.getLoginName()) != null) {
            return OptResult.error(ErrorCodeEnums.PHONE_REGISTERED);
        }
        user.setName(userVo.getName());
        user.setLoginName(userVo.getLoginName());
        user.setAddress(userVo.getAddress());
        user.setPhone(userVo.getPhone());
        user.setType(userVo.getType());
        user.setRegisteredTime(new Date());



        //设置盐
        String salt = IdUtil.simpleUUID().toUpperCase();
        user.setSalt(salt);

        //设置密码 哈希迭代次数为2次
        user.setPassword(new Md5Hash(Constast.USER_DEFAULT_PWD,salt, Constast.HASHITERATIONS).toString());

        super.save(user);
        return OptResult.ok(user);
    }

    @Override
    public OptResult restPassword(Integer id) {

        User user = new User();
        user.setId(id);

        //设置盐  32位(大写英文字母(A-Z)加数字(0-9))
        String salt = IdUtil.simpleUUID().toUpperCase();
        user.setSalt(salt);

        user.setPassword(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());

        super.updateById(user);
        return OptResult.ok("重置成功！");
    }

    @Override
    public OptResult changePassword(Integer id, String oldPassword, String newPassword) {
        User user = new User();
        user = userMapper.selectById(id);
        String salt = user.getSalt();
        if (!user.getPassword().equals(new Md5Hash(oldPassword,salt,Constast.HASHITERATIONS).toString())) {
            return OptResult.error(ErrorCodeEnums.OLD_PASSWORD_ERROR);
        }
        user.setPassword(new Md5Hash(newPassword, salt, Constast.HASHITERATIONS).toString());

        super.updateById(user);


        return OptResult.ok("修改成功!");
    }



    @Override
    public List<User> findAllUser(User user) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //根据电话进行模糊查询
        if (StringUtils.isNotBlank(user.getLoginName())) {
            queryWrapper.like("phone", user.getPhone());
        }
        //根据姓名进行模糊查询
        if (StringUtils.isNotBlank(user.getName())) {
            queryWrapper.like("name",user.getName());
        }
        //根据厂家编号查询用户
        if (StringUtils.isNotBlank(user.getFactoryNumber())) {
            queryWrapper.eq("factory_number", user.getFactoryNumber());
        }
        List<User> usersList = super.list(queryWrapper);


        return usersList;
    }

    @Override
    public Boolean findUserByFactoryNumber(String factoryNumber) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("factory_number",factoryNumber);
        User user = super.getOne(queryWrapper,false);
        if (user == null) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean setFactoryNumber(Integer id, String factoryNumber) {
        User user = new User();
        user.setId(id);
        user.setFactoryNumber(factoryNumber);
        return super.updateById(user);

    }
}
