package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.mapper.mendianMapper;
import com.example.reggie_waimai.popj.Mendian;
import com.example.reggie_waimai.service.MendianService;
import org.springframework.stereotype.Service;

@Service
public class MendianServiceImpl extends ServiceImpl<mendianMapper, Mendian> implements MendianService {
}
