package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.mapper.EmployeeMapper;
import com.example.reggie_waimai.popj.Employee;
import com.example.reggie_waimai.service.EmployeeService;
import org.springframework.stereotype.Service;
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
