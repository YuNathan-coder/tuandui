package com.nathan.tuandui.bus.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nathan.tuandui.sys.common.consts.Constast;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author:NathanYu
 * @Description: 前端添加商品vo
 * @Date: 2022/7/22 8:13
 * @param

 */
@Data
public class GoodsVo {

    /**
     * 款号
     */
    private String styleNumber;
    /**
     * 颜色
     */
    private String coler;

    /**
     * 尺码
     */
    private String size;
    /**
     * 成本
     */
    private String costPrice;
    /**
     * 售价
     */
    private String salePrice;


    /**
     * 备注
     */
    private String remarks;

}
