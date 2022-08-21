package com.nathan.tuandui.bus.controller;

import com.nathan.tuandui.bus.entity.Goods;
import com.nathan.tuandui.bus.response.GoodsResponse;
import com.nathan.tuandui.bus.service.GoodsService;
import com.nathan.tuandui.bus.vo.GoodsVo;
import com.nathan.tuandui.sys.common.enums.ErrorCodeEnums;
import com.nathan.tuandui.sys.common.utils.OptResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;


    @PostMapping("/add")
    public OptResult addGoods (@RequestBody GoodsVo goodsVo) {
        if (goodsService.addGoods(goodsVo)) {
            return OptResult.ok("商品添加成功");
        }
        return OptResult.error("商品添加失败");


    }
    @GetMapping("/findAll")
    public OptResult allGoods (String styleNumber, String coler) {
        List<GoodsResponse> goodsList = new LinkedList<>();
        goodsList = goodsService.loadAllGoods(styleNumber,coler);
        return OptResult.ok(goodsList);
    }


}
