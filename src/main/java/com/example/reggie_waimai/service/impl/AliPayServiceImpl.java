package com.example.reggie_waimai.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.*;
import com.example.reggie_waimai.service.*;
import com.example.reggie_waimai.utils.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private MendianService mendianService;
    @Autowired
    private OrdersService ordersService;
    @Resource
    private WebSocket webSocket;

    @Override
    public String aliPay(Orders orders, HttpServletRequest request) throws AlipayApiException {
        String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC+3pMJE5QXYXYkG9NDRXEurj4lPyh5TK2CwComypKzS5pV501BGtsn9Lz3TJMtt7dNpwxzozEcZkAUG4aAuYgPvjCEcwZOWUJQPxbTTLiAAaso3qMr9fv4BN9jFbb/XlxQeG7QkiXvo/jhy/wRm65YyBhNNXWWFzaRInOxQd1CceocLfB8kyEDK128oPiKrPu0hoRNSyJVFKd6ADYgkmEE2DQz35QaQHEOmchmMIkte+Gsxi3EhlNNsSDNDEN5uC9TKBCBu1edgVYnfRrscZTT9iQ4QioNE5MBvducANXHbtE1PB3tXOKw96Ak2ZgTOVP0+3hpui2KnZMNpqolep7DAgMBAAECggEBAKGwr2J7AXMlDw3bvIY6Z30iAmdPL5xCRqKC47Jk3Q2iOCYZgaprc8hPXV0ps2yBO3k+0B+N2WazgAkIoFKf2RFtDnDFeEwa9UBBkbaCQbG+uB4xLI1rHn6msg6gMJv4db99pnJtvFFb2NR/FxRbi8COEXgml2wEUid0xgkdQLCtkpbM/qsDnnC4imNY6Xt+qxwbcKBezKxkx0wmS3pCO/W9oviATC+Jy55pLBTv2iTwP0DX8Mr+JOMoZc3vpstzBmO2zTxv9b5KWpHuRwILiJrtVZEy8nIpe7e1U3zuWUkALxHi75U5Qf0/SoDl/Zmxss6+39JeQyBeD5TOihXU7DECgYEA+EkIZmc+y5TIupZXVujKfv6yLrIS95zplwz9889tv0Bp3yGksWzx18Q8EHvhSy/52cPsw84DtbsqZ3gd6BHvrFufsWSn0r9e/f+r/FN/HBCJ3j9Lus7haS/w3/HuCnZVi2m2t0DGmNLmuA7dAIWn2dQoKZ1iXbrKuFB6/3/tPe0CgYEAxMzU0dKyjPKNyHQ6WwQOAQu2rBycKKOPHht6GC/vZnDXrw+msw3azJCulf7R8aLAB/W6K7CqeX0uGmKQZcZ8uVjXGO05zLeMQfvljTnTl7rBGzv1tSbPBl98fr98+l8jLU9FGM0G6gJb3bXCdkMe0u1UdmuhCeP6XNxU+mO/OW8CgYEAsQ7j/qMCFQw1WVp9Tm0UexwG1WYIQKyVqDKLp6L1EL5OweCsIhsfHE/ExbySHZxJARLHdZsk6iRfSQpPyX+A+9kbONYfGBuBEoGRlI+2xbzFlMhuqPl/phOaIxnUN4HL32+z7Vs0RSehgQCYehbWbHDvcz3ZOB5NEsPR8wK3nMECgYEAm2ucg1yfj/qaiH1p/Kk2GhNTH5e0p8+L3l4azXFF4qQpYeK9ZtkBO97jUigdS3SZrW+dqJVr/Ggk+cdvfEEGDSahMNlgdVFbnly+DAtoFILzsHto77iHdOQCIOM/Y0exMz5QNmbtF+/m9zBtNBKMDE5MDv2u/22hMqb7IYeW5FcCgYAcISyKPvg1Gk5lOw7wZqiCotF5XdRxrJZ/3htlTi73Vy2aDismjxLW+pO9nkgCyJwUtAqsTVTC2Kzqmb/Lx+RqUGoMV49ZGFcUceFsaD9wAmWbph3CRt0fdeLiN8nd/69jFAi70o3NqMnZUPY6xejrXQGDw6J9F69D3E3qheQk/Q==";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmbI0miKQ2AtxlUOjEZpnoJOepHM/aMdFT/91t7Xs8QK285Zj1NdMzyaTAkjlYA1ZV8htPO6rdGysgaEvfzl+q+YDzjuKiNRyDkcH4oq1xAcpQAw/S9W0Vrx1Rwjn6G/uUY6k35xpiYYhh/LlxBOWZDwx9kbuZCi2XFrQer4dV37sri3OWPfQRYZPBCApnqouKK0+HJGu+EIU9EW4tUyoZ9NxDzr7wKoSgx+3dZJaNSSknfPReiZoRCJGQa8E3LsfhHQo4YgKcZFbO3uxUzbTz1vt7FizhejT4v0VfnSn+EfpjrR8sAUPbQ1g9Wv4xuBCr/+st0y5vvQKk1TkagC9nQIDAQAB";
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId("9021000135613476");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        AlipayClient alipayClient = null;
        try {
            alipayClient = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        AlipayTradeWapPayRequest request1 = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        request1.setNotifyUrl("http://123.60.129.35:88/api/notify");
        //订单编号
        model.setOutTradeNo(String.valueOf(orders.getId()));
        //订单金额
        model.setTotalAmount(String.valueOf(orders.getAmount()));
        //订单名称
        model.setSubject("瑞吉外卖");
        model.setProductCode("QUICK_WAP_WAY");
        //设置收款方id
        model.setSellerId("2088721031536631");
        request1.setBizModel(model);
        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request1, "POST");
        // 如果需要返回GET请求，请使用
//         AlipayTradeWapPayResponse response = alipayClient.pageExecute(request, "GET");
        String pageRedirectionData = response.getBody();
        System.out.println(pageRedirectionData);
        if (response.isSuccess()) {
            log.info("支付宝接口调用成功");
            //将数据保存到redis
            log.info(pageRedirectionData);
            return pageRedirectionData;
        } else {
            log.info("支付宝接口调用失败");
            return "error";
        }
    }


    //这个方法用于回调请求处理
    @Override
    @Transactional
    public Boolean Notify(HttpServletRequest request) throws AlipayApiException {
        log.info("支付回调请求处理");
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
                // System.out.println(name + " = " + request.getParameter(name));
            }

            String outTradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");
            String getAlipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmbI0miKQ2AtxlUOjEZpnoJOepHM/aMdFT/91t7Xs8QK285Zj1NdMzyaTAkjlYA1ZV8htPO6rdGysgaEvfzl+q+YDzjuKiNRyDkcH4oq1xAcpQAw/S9W0Vrx1Rwjn6G/uUY6k35xpiYYhh/LlxBOWZDwx9kbuZCi2XFrQer4dV37sri3OWPfQRYZPBCApnqouKK0+HJGu+EIU9EW4tUyoZ9NxDzr7wKoSgx+3dZJaNSSknfPReiZoRCJGQa8E3LsfhHQo4YgKcZFbO3uxUzbTz1vt7FizhejT4v0VfnSn+EfpjrR8sAUPbQ1g9Wv4xuBCr/+st0y5vvQKk1TkagC9nQIDAQAB";
            String sign = params.get("sign");
            String content = AlipaySignature.getSignCheckContentV1(params);
            boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, getAlipayPublicKey, "UTF-8"); // 验证签名
            // 支付宝验签
            if (checkSignature) {
                // 验签通过
                log.info("交易名称:{} ", params.get("subject"));
                log.info("交易状态:{} ", params.get("trade_status"));
                log.info("支付宝交易凭证号:{} ", params.get("trade_no"));
                log.info("商户订单号: {}", params.get("out_trade_no"));
                log.info("交易金额: {}", params.get("total_amount"));
                log.info("买家在支付宝唯一id:{} ", params.get("buyer_id"));
                log.info("买家付款时间: {}", params.get("gmt_payment"));
                log.info("买家付款金额: {}", params.get("buyer_pay_amount"));
                //订单交易成功
                if (params.get("trade_status").equals("TRADE_SUCCESS")) {
                    //获取reids的缓存
                    String outTradeNo1 = params.get("out_trade_no");
                    String key1 = "order" + outTradeNo1;
                    String key2 = "orderDetail" + outTradeNo1;
                    Orders orders = (Orders) redisTemplate.opsForValue().get(key1);
                    List<OrderDetail> orderDetails = (List<OrderDetail>) redisTemplate.opsForValue().get(key2);
                    orders.setStatus(2);
                    //向订单表插入数据，一条数据
                    ordersService.save(orders);
                    //向订单明细表插入数据，多条数据
                    orderDetailService.saveBatch(orderDetails);
                    //更新当前门店的相关信息
                    Mendian mendian = new Mendian();
                    LambdaQueryWrapper<Mendian> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
                    Long mendianID = orders.getMendianId();
                    BigDecimal amount = orders.getAmount();

                    lambdaQueryWrapper1.eq(Mendian::getId, mendianID);
                    Mendian one = mendianService.getOne(lambdaQueryWrapper1);
                    LambdaUpdateWrapper<Mendian> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    lambdaUpdateWrapper.set(Mendian::getAllmony, one.getAllmony().add(amount));
                    lambdaUpdateWrapper.set(Mendian::getTodaymony, one.getTodaymony().add(amount));
                    lambdaUpdateWrapper.eq(Mendian::getId, mendianID);
                    //更新当前门店的营业额
                    mendianService.update(lambdaUpdateWrapper);
                     redisTemplate.delete("order"+orders.getUserId().toString());
                    redisTemplate.delete(key1);
                    redisTemplate.delete(key2);
                    //单个用户发送 (userId为用户id)
                    webSocket.sendOneMessage(params.get("out_trade_no").toString(), "paysuccess");
//                    //多个用户发送 (userIds为多个用户id，逗号‘,’分隔)
//                    webSocket.sendMoreMessage(userIds, obj.toJSONString());
//                    //创建业务消息信息
//                    webSocket.sendAllMessage("哈哈哈哈哈哈哈");
                    return true;
                } else {
                    //未进行支付，返回失败
                    return false;
                }
            }
        }
        //修改失败，未支付
        return false;

    }
}
