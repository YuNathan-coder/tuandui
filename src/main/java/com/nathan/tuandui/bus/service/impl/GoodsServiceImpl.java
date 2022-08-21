package com.nathan.tuandui.bus.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nathan.tuandui.bus.commom.constast.Constast;
import com.nathan.tuandui.bus.commom.utils.GoodsNumberGenerate;
import com.nathan.tuandui.bus.entity.Goods;
import com.nathan.tuandui.bus.mapper.GoodsMapper;
import com.nathan.tuandui.bus.response.GoodsResponse;
import com.nathan.tuandui.bus.service.GoodsService;
import com.nathan.tuandui.bus.vo.GoodsVo;
import com.nathan.tuandui.sys.common.utils.OptResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;


    @Override
    public Boolean addGoods(GoodsVo goodsVo) {
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVo,goods);

        goods.setGoodsNumber(GoodsNumberGenerate.generate(goods.getStyleNumber(), goods.getColer()));
        goods.setTotleSale(Constast.DEFAULT_SALE_TOTILE);

        goods.setAddTime(new Date());

        goods.setState(Constast.NO_SALE);


        return super.save(goods);

    }

    @Override
    public Boolean updateMainPhoto(String mainPhoto, String goodsNumber) {
        Goods goods = new Goods();
        goods.setMainPhoto(mainPhoto);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_number" , goodsNumber);
        return super.update(goods,queryWrapper);
    }

    @Override
    public Boolean updatePhotos(String photos, String goodsNumber) {
        Goods goods = new Goods();
        goods.setPhoto(photos);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_number" , goodsNumber);

        return super.update(goods,queryWrapper);


    }

    @Override
    public List<GoodsResponse> loadAllGoods(String styleNumber, String coler) {
        List<Goods> goodsList = new LinkedList<>();
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(styleNumber)){
            queryWrapper.eq("style_number", styleNumber);

        }
        if (StringUtils.isNotBlank(coler)) {
            queryWrapper.eq("coler",coler);
        }
        goodsList = super.list(queryWrapper);

        List<GoodsResponse> goodsResponseList = new LinkedList<>();

       // BeanUtils.copyProperties(goodsResponseList, goodsList);
        for (Goods goods : goodsList) {
            GoodsResponse goodsResponse = new GoodsResponse();
            BeanUtils.copyProperties(goods , goodsResponse);
            goodsResponseList.add(goodsResponse);
        }



        for (GoodsResponse goodsResponse : goodsResponseList) {
            List<String> photolist = null;
            String photosPath = goodsResponse.getPhoto();
            if (!photosPath.equals("")) {
                goodsResponse.setPhotolist(divPhotos(photosPath));
            }

        }



        return goodsResponseList;

    }

    public Map<String,String> divPhotos(String photosPath) {

        Map<String, String> result = new HashMap<>();

        String[] temp = photosPath.split(",");
        for (Integer i = 0; i < temp.length; i++ ) {
            String name = "photo" +  i.toString();
            result.put(name,temp[i]);
        }
        return result;
    }


}
