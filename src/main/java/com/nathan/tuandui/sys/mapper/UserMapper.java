package com.nathan.tuandui.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nathan.tuandui.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:NathanYu
 * @Description: 数据库User接口
 * @Date: 2022/7/21 4:40
 * @param

 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
