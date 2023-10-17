package com.example.reggie_waimai.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.popj.Employee;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.service.EmployeeService;
import com.example.reggie_waimai.utils.JwtUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
//import com.example.reggie_waimai.filter.test;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    /*
    用户登录
    */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //解析密码
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据前端的传入的数据进行数据库的查询
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<Employee>()
                .eq(Employee::getUsername, employee.getUsername())
                .eq(Employee::getPassword, password);
        Employee emp = employeeService.getOne(lambdaQueryWrapper);
        //判断传入数据是否为空
        if (emp == null) {
            return R.error("登录失败");
        }
        if (emp.getStatus() == 0) {
            return R.error("账号已冻结");
        }
        Map<String, Object> claims=new HashMap<>();
        claims.put("id", emp.getId());
        claims.put("username",emp.getPassword());
        claims.put("password",emp.getPassword());
        JwtUtils jwtUtils=new JwtUtils();
        String jwt= jwtUtils.generateJwt(claims);
        log.info("当前jwt：{}",jwt);
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    /*
    退出登录
    */
    @PostMapping("logout")
    public R<String> login_tuichu(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
    /*
   添加用户
   */
    @PostMapping
    public R<String> adduser(HttpServletRequest request, @RequestBody Employee employee){
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        Long userid= (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(userid);
        employee.setUpdateUser(userid);
        employeeService.save(employee);
        return R.success("员工增加成功");
    }
    /*
    查询用户
    */

    @GetMapping("/page")
    public R<Page> selectuser(int page,int pageSize,String name){
        log.info("page{}pagesize{}name{}",page,pageSize,name);
        Page page1=new Page(page,pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        if(name!=null){
            lambdaQueryWrapper.like(Employee::getName,name);

        }
        Page page2= employeeService.page(page1,lambdaQueryWrapper);
        return R.success(page2);
    }


    /*
    更改用户账号状态
    */
    @PutMapping
    public R<String> UpdateUserS(HttpServletRequest request, @RequestBody Employee employee){
        Long userid= (Long) request.getSession().getAttribute("employee");
        if(userid==null){
            userid= 1L;
        }
        log.info("{}",userid);
        employee.setUpdateUser(userid);
        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("状态更改成功");
    }
    /*
    信息回读
    */
    @GetMapping("/{id}")
    public R<Employee> UpdateUser(@PathVariable Long id){
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getId,id);
        Employee employee=employeeService.getOne(lambdaQueryWrapper);
        return R.success(employee);
    }

}