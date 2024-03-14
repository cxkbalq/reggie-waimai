package com.example.reggie_waimai.service;

import com.alipay.api.AlipayApiException;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.Orders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
@Service
public interface AliPayService {

    //调起支付宝支付接口
    public String aliPay(Orders orders, HttpServletRequest request) throws AlipayApiException;

    //接收回调处理信息
    public Boolean Notify(HttpServletRequest request) throws AlipayApiException;

}
