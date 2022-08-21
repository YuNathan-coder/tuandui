package com.nathan.tuandui.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nathan.tuandui.bus.entity.Goods;
import com.nathan.tuandui.bus.response.GoodsResponse;
import com.nathan.tuandui.bus.vo.GoodsVo;
import com.nathan.tuandui.sys.common.utils.OptResult;

import java.util.List;

public interface GoodsService extends IService<Goods> {

    Boolean addGoods (GoodsVo goodsVo);

    Boolean updateMainPhoto (String mainPhoto, String goodsNumber);

    Boolean updatePhotos (String photos, String goodsNumber);

    List<GoodsResponse> loadAllGoods (String styleNumber, String coler);



}
