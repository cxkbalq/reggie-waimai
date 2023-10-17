package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.mapper.UserMapper;
import com.example.reggie_waimai.popj.User;
import com.example.reggie_waimai.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceimpl extends ServiceImpl<UserMapper, User> implements UserService {
}
