package com.example.reggie_waimai.controller;

import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.Orders;
import com.example.reggie_waimai.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @PostMapping("/submitt")
    public R<String> submittest(@RequestBody Orders orders, HttpServletRequest request){
        ordersService.submittest(orders,request);
        return R.success("下单成功");
    }


}
