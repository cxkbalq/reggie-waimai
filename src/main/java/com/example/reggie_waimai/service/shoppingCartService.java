package com.example.reggie_waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.ShoppingCart;
import org.springframework.stereotype.Service;

@Service
public interface shoppingCartService extends IService<ShoppingCart> {
    public R<ShoppingCart> addShoppings(ShoppingCart shoppingCart,Long userid);
    public R<ShoppingCart> subShoppings(ShoppingCart shoppingCart,Long userid);
}
