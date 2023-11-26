package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.mapper.OrdersMapper;
import com.example.reggie_waimai.popj.*;
import com.example.reggie_waimai.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
    @Transactional
    public void submittest(@RequestBody Orders orders, HttpServletRequest request) {
        Long userid = Long.valueOf(request.getHeader("user"));
        Long mendianID= Long.valueOf(request.getHeader("mendian"));
        User user = userService.getById(userid);
        //判断当前购物车是否为空
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, userid);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(lambdaQueryWrapper);
//        BigDecimal amount1 = orders.getAmount();
        if (shoppingCartList == null ) {
            R.error("当前购物车为空，无法下单");
        }

        //判断地址信息是否正确
        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            R.error("用户地址信息有误，不能下单");
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
        //向订单表插入数据，一条数据
        this.save(orders);
        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);
        //更新当前门店的营业额
        Mendian mendian=new Mendian();
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Mendian::getId,mendianID);
        Mendian one = mendianService.getOne(lambdaQueryWrapper1);
        LambdaUpdateWrapper<Mendian> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Mendian::getAllmony,one.getAllmony().add(new BigDecimal(amount.get())));
        lambdaUpdateWrapper.set(Mendian::getTodaymony,one.getTodaymony().add(new BigDecimal(amount.get())));
        lambdaUpdateWrapper.eq(Mendian::getId,mendianID);
        mendianService.update(lambdaUpdateWrapper);
        //清空购物车数据
        shoppingCartService.remove(lambdaQueryWrapper);
    }
}
