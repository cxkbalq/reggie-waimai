package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.ShoppingCart;
import com.example.reggie_waimai.service.shoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.reggie_waimai.mapper.shoppingCartMapper;

import java.time.LocalDateTime;

@Service
@Slf4j
public class shoppingCartServiceimpl extends ServiceImpl<shoppingCartMapper, ShoppingCart> implements shoppingCartService {

    @Override
    public R<ShoppingCart> addShoppings(ShoppingCart shopping, Long userid) {
        //初始化查询，减少代码量
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断当前是菜品还是套餐
        //为菜品时
        if (shopping.getDishId() != null) {
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, shopping.getDishId());
        }
        //为套餐时
        else {
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shopping.getSetmealId());
        }
        //查询当前菜品是否已经存在

        ShoppingCart one = this.getOne(lambdaQueryWrapper);

        //如果存在
        if (one != null) {
            //获得当前这个菜品的数量并加一
            shopping.setNumber(one.getNumber() + 1);
            LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            //对两种情况进行分别处理
            if (shopping.getDishId() != null) {
                lambdaQueryWrapper1.eq(ShoppingCart::getDishId, shopping.getDishId());
                //更新数据
                this.update(shopping, lambdaQueryWrapper1);
                return R.success(shopping);
            } else {
                lambdaQueryWrapper1.eq(ShoppingCart::getSetmealId, shopping.getSetmealId());
                //更新数据
                this.update(shopping, lambdaQueryWrapper1);
                return R.success(shopping);
            }
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
        //初始化查询，减少代码量
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //判断当前是菜品还是套餐
        //为菜品时
        if (shopping.getDishId() != null) {
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, shopping.getDishId());
        }
        //为套餐时
        else {
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shopping.getSetmealId());
        }
        //查询当前菜品是否已经存在

        ShoppingCart one = this.getOne(lambdaQueryWrapper);
        if(one.getNumber()<=0){
            return R.error("不能在减少了哦，已经为0了");
        }
        //如果存在
        if (one != null) {
            //获得当前这个菜品的数量并加一
            shopping.setNumber(one.getNumber() - 1);
            LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            //对两种情况进行分别处理
            if (shopping.getDishId() != null) {
                lambdaQueryWrapper1.eq(ShoppingCart::getDishId, shopping.getDishId());
                //更新数据
                this.update(shopping, lambdaQueryWrapper1);
                return R.success(shopping);
            } else {
                lambdaQueryWrapper1.eq(ShoppingCart::getSetmealId, shopping.getSetmealId());
                //更新数据
                this.update(shopping, lambdaQueryWrapper1);
                return R.success(shopping);
            }
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
}
