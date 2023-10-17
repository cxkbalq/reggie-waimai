package com.example.reggie_waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_waimai.dto.DishDto;
import com.example.reggie_waimai.popj.Dish;
import com.example.reggie_waimai.popj.DishFlavor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public interface DishService extends IService<Dish> {


    public void adddish(DishDto dishDto, HttpServletRequest request);
    @Transactional
    public void uplodadish(DishDto dishDto, HttpServletRequest request);

}
