package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.mapper.OrderDetailMapper;
import com.example.reggie_waimai.popj.OrderDetail;
import com.example.reggie_waimai.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceimpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
