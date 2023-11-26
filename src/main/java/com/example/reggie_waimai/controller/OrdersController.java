package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.dto.DishDto;
import com.example.reggie_waimai.popj.Category;
import com.example.reggie_waimai.popj.Dish;
import com.example.reggie_waimai.popj.Orders;
import com.example.reggie_waimai.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    //提交订单
    //提交订单
    @PostMapping("/submit")
    public R<String> submittest(@RequestBody Orders orders, HttpServletRequest request) {
        ordersService.submittest(orders, request);
        return R.success("下单成功");
    }

    //管理端订单明细
    //管理端订单明细
    @GetMapping("/page")
    public R<Page> selectmdish(int page,
                               int pageSize,
                               Long number,
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                               HttpServletRequest request) {
        Long mendianID= Long.valueOf(request.getHeader("mendian"));
        //设置分页参数
        Page<Orders> page1 = new Page<>(page, pageSize);
        //构建查询条件
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Orders::getOrderTime);
        lambdaQueryWrapper.eq(Orders::getMendianId,mendianID);
        //  添加查询条件
        if (number != null) {
            lambdaQueryWrapper.like(Orders::getNumber, number);
        }
        if (beginTime != null && endTime != null) {
            lambdaQueryWrapper.between(Orders::getOrderTime, beginTime, endTime);

        }
        Page pagedto = ordersService.page(page1, lambdaQueryWrapper);
        return R.success(pagedto);
    }

    //用户端订单明细
    //用户端订单明细
    @GetMapping("/userPage")
    public R<Page> selectmdish1(int page,
                                int pageSize,
                                Long number,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime
                               ) {

        //设置分页参数
        Page<Orders> page1 = new Page<>(page, pageSize);
        //构建查询条件
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Orders::getOrderTime);
        //  添加查询条件
        if (number != null) {
            lambdaQueryWrapper.like(Orders::getNumber, number);
        }
        if (beginTime != null && endTime != null) {
            lambdaQueryWrapper.between(Orders::getOrderTime, beginTime, endTime);

        }

        Page pagedto = ordersService.page(page1, lambdaQueryWrapper);
        return R.success(pagedto);
    }
    //管理端订单状态更改
    @PutMapping
    public R<String> statusup(@RequestBody Orders orders){
        Long id=orders.getId();
        LambdaQueryWrapper<Orders>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getId,id);
        ordersService.update(orders,lambdaQueryWrapper);
        return R.success("状态更改成功");
    }


}
