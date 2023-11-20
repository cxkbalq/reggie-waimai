package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reggie_waimai.common.BaseContext;
import com.example.reggie_waimai.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.reggie_waimai.service.shoppingCartService;
import com.example.reggie_waimai.popj.ShoppingCart;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/shoppingCart")
@RestController
public class shoppingCartController {
    @Autowired
    private shoppingCartService shoppingCartService;
    @PostMapping("/add")
    public R<ShoppingCart> addShopping(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        //17856796238
        Long userid=Long.valueOf(request.getHeader("user"));
        R<ShoppingCart> stringR = shoppingCartService.addShoppings(shoppingCart, userid);
        return stringR;
    }
    @PostMapping("/sub")
    public R<ShoppingCart> subShopping(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        //17856796238
        Long userid=Long.valueOf(request.getHeader("user"));
        R<ShoppingCart> stringR = shoppingCartService.subShoppings(shoppingCart, userid);
        return stringR;
    }



    @GetMapping("/list")
    public R<List<ShoppingCart>> listR(HttpServletRequest request){
        Long userid=Long.valueOf(request.getHeader("user"));
      //  Long userid=BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userid);
        List<ShoppingCart> list = shoppingCartService.list(lambdaQueryWrapper);
        return R.success(list);

    }

    @DeleteMapping("/clean")
    public R<String> clean(HttpServletRequest request){
        Long userid=Long.valueOf(request.getHeader("user"));
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,userid);
        shoppingCartService.remove(lambdaQueryWrapper);
        return R.success("购物车清除成功");
    }
}
