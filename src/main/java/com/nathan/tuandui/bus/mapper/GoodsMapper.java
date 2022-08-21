package com.nathan.tuandui.bus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nathan.tuandui.bus.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:NathanYu
 * @Description: bus_goods数据表接口
 * @Date: 2022/7/22 7:13
 * @param

 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

}
