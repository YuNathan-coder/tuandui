package com.nathan.tuandui.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.nathan.tuandui.sys.entity.Loginfo;

import com.nathan.tuandui.sys.mapper.LoginfoMapper;

import com.nathan.tuandui.sys.service.LoginfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author:NathanYu
 * @Description:
 * @Date: 2022/7/21 10:03
 * @param

 */
@Service
@Transactional
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements LoginfoService {

    @Autowired
    private LoginfoMapper loginfoMapper;

}
