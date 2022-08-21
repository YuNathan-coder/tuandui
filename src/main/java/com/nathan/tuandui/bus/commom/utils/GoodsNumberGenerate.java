package com.nathan.tuandui.bus.commom.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author:NathanYu
 * @Description: 商品编码生成器
 * @Date: 2022/7/22 7:27
 * @param

 */
public class GoodsNumberGenerate {

    public static final String generate (String styleNumber, String coler) {
        if (StringUtils.isBlank(styleNumber) && StringUtils.isBlank(coler)) {
            throw new RuntimeException("款号或者颜色为空");
        }
        return styleNumber + "_" +coler;
    }
}
