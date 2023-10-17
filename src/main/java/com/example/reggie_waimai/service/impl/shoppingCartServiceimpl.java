package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.ShoppingCart;
import com.example.reggie_waimai.service.shoppingCartService;
import org.springframework.stereotype.Service;
import com.example.reggie_waimai.mapper.shoppingCartMapper;

import java.time.LocalDateTime;

@Service
public class shoppingCartServiceimpl extends ServiceImpl<shoppingCartMapper, ShoppingCart> implements shoppingCartService{

    @Override
    public R<ShoppingCart> addShoppings(ShoppingCart shopping, Long userid) {

        //查询当前菜品是否已经存在
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getDishId,shopping.getDishId());
        ShoppingCart one = this.getOne(lambdaQueryWrapper);

        //如果存在
        if(one!=null){
            //获得当前这个菜品的数量并加一
           shopping.setNumber(one.getNumber()+1);
           LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
           lambdaQueryWrapper1.eq(ShoppingCart::getDishId,shopping.getDishId());
           //更新数据
           this.update(shopping,lambdaQueryWrapper1);
            return R.success(shopping);
        }
        //如果不存在
        else {
            shopping.setUserId(userid);
            shopping.setCreateTime(LocalDateTime.now());
            shopping.setNumber(1);
            //保存数据
            this.save(shopping);
            return R.success(shopping);
        }
    }

    @Override
    public R<ShoppingCart> subShoppings(ShoppingCart shopping, Long userid) {

        //查询当前菜品是否已经存在
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getDishId,shopping.getDishId());
        ShoppingCart one = this.getOne(lambdaQueryWrapper);
        //获得当前这个菜品的数量并加一
        shopping.setNumber(one.getNumber()-1);
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(ShoppingCart::getDishId,shopping.getDishId());
        //更新数据
        this.update(shopping,lambdaQueryWrapper1);
        return R.success(shopping);
    }

}
