package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.dto.MendianDto;
import com.example.reggie_waimai.popj.Employee;
import com.example.reggie_waimai.popj.Mendian;
import com.example.reggie_waimai.service.EmployeeService;
import com.example.reggie_waimai.service.MendianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mendian")
@Slf4j
public class MendianController {
    @Autowired
    private MendianService mendianService;
    @Autowired
    private EmployeeService employeeService;


    //分页查询
    @GetMapping("/page")
    public R<Page> pageR(int page,int pageSize){
        log.info("page{}pagesize{}",page,pageSize);
        Page page1=new Page(page,pageSize);
        Page page2 = mendianService.page(page1);
        return R.success(page2);
    }

    //状态更改
   @PutMapping()
    public R<String> UpdateUserS(HttpServletRequest request, @RequestBody Mendian mendian){
        Long userid= (Long) request.getSession().getAttribute("employee");
        if(userid==null){
            userid= 1L;
        }
        log.info("{}",userid);
        mendian.setUpdateUser(userid);
        mendian.setUpdateTime(LocalDateTime.now());
        mendianService.updateById(mendian);
        return R.success("状态更改成功");
    }
    //回读员工信息
    @GetMapping("/supersonList/{id}")
    public R<MendianDto> supersonList(@PathVariable Long id){

        //创建dto实列
        MendianDto mendianDto=new MendianDto();
        //将查询得值copy到mendianDto中
        Mendian mendian = mendianService.getById(id);
        BeanUtils.copyProperties(mendian,mendianDto);
        //补全负责人信息
        Long em_id=mendian.getCreateUser();
        LambdaQueryWrapper<Employee> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getCreateUser,em_id);
        List<Employee> list = employeeService.list(lambdaQueryWrapper);
        mendianDto.setEmployeeList(list);
        //补全门店信息
        return R.success(null);
    }
    //查询单个门店信息，用于信息回读
    @GetMapping("/{id}")
    public R<Mendian> GetMendian(@PathVariable Long id){

        LambdaQueryWrapper<Mendian> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Mendian::getId,id);
        Mendian one = mendianService.getOne(lambdaQueryWrapper);
        return R.success(one);
    }


}
