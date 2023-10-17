package com.example.reggie_waimai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggie_waimai.popj.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
