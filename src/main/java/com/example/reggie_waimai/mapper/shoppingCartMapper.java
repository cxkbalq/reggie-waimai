package com.example.reggie_waimai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie_waimai.dto.DishDto;
import com.example.reggie_waimai.popj.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface shoppingCartMapper extends BaseMapper<ShoppingCart> {
}
