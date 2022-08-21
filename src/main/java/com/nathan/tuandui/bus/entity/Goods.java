package com.nathan.tuandui.bus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nathan.tuandui.sys.common.consts.Constast;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("bus_goods")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class Goods implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    /**
     * 款号
     */
    private String styleNumber;
    /**
     * 颜色
     */
    private String coler;
    /**
     * 商品编号 系统设置 每个商品编号唯一
     */
    private String goodsNumber;
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
     * 总销售数量
     */
    private Integer totleSale;
    /**
     * 添加时间
     */
    @DateTimeFormat(pattern = Constast.TIME_FORM)
    private Date addTime;
    /**
     * 状态 0：下架 1：上架
     */
    private Integer state;
    /**
     * 主图片路径
     */
    private String mainPhoto;
    /**
     * 图片路径
     */
    private String photo;
    /**
     * 备注
     */
    private String remarks;
}
