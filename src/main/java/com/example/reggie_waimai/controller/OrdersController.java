package com.example.reggie_waimai.controller;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.dto.DishDto;
import com.example.reggie_waimai.popj.Category;
import com.example.reggie_waimai.popj.Dish;
import com.example.reggie_waimai.popj.Orders;
import com.example.reggie_waimai.service.OrdersService;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private RedisTemplate redisTemplate;

    //提交订单
    //提交订单
    @PostMapping("/submit")
    public R<String> submittest(@RequestBody Orders orders, HttpServletRequest request) throws AlipayApiException {
        R<String> submittest = ordersService.submittest(orders, request);
        return submittest;
    }
    //取消订单
    @PostMapping("/errorOrder/{orderId}")
    public R<String> errorOrder(@PathVariable("orderId")  Long OrederId,HttpServletRequest request) throws AlipayApiException {
        Long userid = Long.valueOf(request.getHeader("user"));
        String key1 = "order" + OrederId;
        String key2 = "orderDetail" + OrederId;
        String key3 = "order" + userid;
        redisTemplate.delete(key1);
        redisTemplate.delete(key2);
        redisTemplate.delete(key3);
        return R.success("订单取消成功");
    }
    //管理端订单明细
    @GetMapping("/page")
    public R<Page> selectmdish(int page,
                               int pageSize,
                               Long number,
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date beginTime,
                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                               HttpServletRequest request) {
        Long mendianID = Long.valueOf(request.getHeader("mendian"));
        //设置分页参数
        Page<Orders> page1 = new Page<>(page, pageSize);
        //构建查询条件
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Orders::getOrderTime);
        lambdaQueryWrapper.eq(Orders::getMendianId, mendianID);
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
                                @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                HttpServletRequest request
    ) {
        Long userid = Long.valueOf(request.getHeader("user"));
        //设置分页参数
        Page<Orders> page1 = new Page<>(page, pageSize);
        //构建查询条件
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Orders::getOrderTime);
        //  添加查询条件
        if (number != null) {
            lambdaQueryWrapper.like(Orders::getUserId, number);
        }
        if (beginTime != null && endTime != null) {
            lambdaQueryWrapper.between(Orders::getOrderTime, beginTime, endTime);
        }
        Page pagedto = ordersService.page(page1, lambdaQueryWrapper);
        //获得redis里还没有支付的订单
        if (redisTemplate.opsForValue().get("order" + userid.toString()) != null) {
            //获得当前用户未支付的订单
            Orders orders = (Orders) redisTemplate.opsForValue().get("order" + userid.toString());
            //补全分页数据
            if (pagedto.getRecords().size() < 5) {
                List records = pagedto.getRecords();
                records.add(orders);
                pagedto.setRecords(records);
            }
        }
        return R.success(pagedto);
    }

    //管理端订单状态更改
    @PutMapping
    public R<String> statusup(@RequestBody Orders orders) {
        Long id = orders.getId();
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getId, id);
        ordersService.update(orders, lambdaQueryWrapper);
        return R.success("状态更改成功");
    }


    //获得创建订单的剩余支付时间
    @PostMapping("/getOredesTime/{orderId}")
    private R<Long> getorderstime(@PathVariable("orderId")  Long OrederId) {
         //减一定时间防止网速太卡，造成延迟bug
         Long expireTime = redisTemplate.getExpire("order" + OrederId.toString(), TimeUnit.SECONDS)-3;
         if(expireTime<3){
             return R.success(0l);
         }
        return R.success(expireTime);
    }
    @PostMapping("/payorder/{orderId}")
    public R<String> payorder(@PathVariable("orderId")  Long OrederId,HttpServletRequest request) throws AlipayApiException {
        //根据订单id
        R<String> stringR = ordersService.payOreder(OrederId, request);
        return stringR;
    }

}
