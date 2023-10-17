package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.mapper.CategoryMapper;
import com.example.reggie_waimai.popj.Category;
import com.example.reggie_waimai.service.CategoryService;
import org.springframework.stereotype.Service;
@Service
public class CategoryServiceimpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
