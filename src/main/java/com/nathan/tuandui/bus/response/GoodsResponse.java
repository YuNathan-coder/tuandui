package com.nathan.tuandui.bus.response;

import com.nathan.tuandui.bus.entity.Goods;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data

public class GoodsResponse extends Goods {

    private Map<String,String> photolist;
}
