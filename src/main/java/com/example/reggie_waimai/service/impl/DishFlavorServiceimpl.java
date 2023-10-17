package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.mapper.DishFlavorMapper;
import com.example.reggie_waimai.popj.DishFlavor;
import com.example.reggie_waimai.service.DishFlavorService;
import com.example.reggie_waimai.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceimpl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService {
}
