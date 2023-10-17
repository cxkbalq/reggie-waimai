package com.example.reggie_waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.Orders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Service
public interface OrdersService extends IService<Orders> {

    public void submittest(@RequestBody Orders orders, HttpServletRequest request);
}
