package com.example.reggie_waimai.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.mapper.OrdersMapper;
import com.example.reggie_waimai.popj.*;
import com.example.reggie_waimai.service.*;
import com.example.reggie_waimai.utils.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrdersServiceimpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    private shoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private MendianService mendianService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

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
        request1.setNotifyUrl(notifyUrl);
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

    public R<String> submittest(@RequestBody Orders orders, HttpServletRequest request) throws AlipayApiException {
        Long userid = Long.valueOf(request.getHeader("user"));
        Long mendianID = Long.valueOf(request.getHeader("mendian"));
        User user = userService.getById(userid);
        //判断当前购物车是否为空
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userid);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(lambdaQueryWrapper);
//        BigDecimal amount1 = orders.getAmount();
        if (shoppingCartList == null) {
            return R.error("当前购物车为空，无法下单");
        }

        //判断地址信息是否正确
        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            return R.error("用户地址信息有误，不能下单");
        }

        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCartList.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            orderDetail.setMendianId(mendianID);
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        //补全基本信息
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userid);
        orders.setNumber(String.valueOf(orderId));
        orders.setMendianId(mendianID);
        //如果用户名为空，默认设置为手机号
        if (user.getName() == null) {
            orders.setUserName(user.getPhone());
        } else {
            orders.setUserName(user.getName());
        }

        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        if (redisTemplate.opsForValue().get("order" + userid.toString()) != null) {
            log.info("有未支付的订单");
            return R.error("有未支付的订单，请完成支付或取消订单");
        }
        String key1 = "order" + orders.getId().toString();
        String key2 = "orderDetail" + orders.getId().toString();
        //这个用于订单详细页面使用，减少前端代码量
        String key3 = "order" + userid.toString();
        orders.setStatus(1);
        //将信息储存到redis里
        redisTemplate.opsForValue().set(key1, orders, 15, TimeUnit.MINUTES);
        redisTemplate.opsForValue().set(key2, orderDetails, 15, TimeUnit.MINUTES);
        //多储存一个用于订单详情的支付数据补全
        redisTemplate.opsForValue().set(key3, orders, 15, TimeUnit.MINUTES);
        shoppingCartService.remove(lambdaQueryWrapper);
        R r=new R();
        r.setCode(1);
        r.setData(orderId);
        return r;
    }

    public R<String> payOreder(Long orderid, HttpServletRequest request) throws AlipayApiException {
        String key1 = "order" + orderid;
        Orders orders = (Orders) redisTemplate.opsForValue().get(key1);
        String aliPay = aliPay(orders, request);
        if (!aliPay.equals("error")) {
            log.info("支付请求成功");
            R r = new R();
            //清空购物车数据
            r.setData(aliPay);
            r.setCode(1);
            return r;
        }
        //失败是返回到原有页面，不清空购物车
        else {

            log.info("支付请求失败");
            return R.error("支付失败");
        }
    }


}
