package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.Employee;
import com.example.reggie_waimai.popj.Mendian;
import com.example.reggie_waimai.service.EmployeeService;
import com.example.reggie_waimai.service.MendianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public R<Page> pageR(int page, int pageSize, String name, HttpServletRequest request) {
        log.info("page{}pagesize{}", page, pageSize);
//        Long userid= Long.valueOf(request.getHeader("Employee"));
        Long mendianID = Long.valueOf(request.getHeader("mendian"));
        Page page1 = new Page(page, pageSize);
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Mendian::getTodaymony);
        //修复处理只有root账号才显示，门店的bug
        //根据当前门店id，查询createid，获得正确的门店列表
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Mendian::getId, mendianID);
        Mendian one = mendianService.getOne(lambdaQueryWrapper1);
        Long userid = one.getCreateUser();
        lambdaQueryWrapper.eq(Mendian::getCreateUser, userid);
        if (name != null) {
            lambdaQueryWrapper.like(Mendian::getName, name);
        }
        Page page2 = mendianService.page(page1, lambdaQueryWrapper);
        return R.success(page2);
    }

    //状态更改
    @Transactional  //保证数据的一致性
    @PutMapping()
    public R<String> UpdateUserS(HttpServletRequest request, @RequestBody Mendian mendian) {
        Long userid = Long.valueOf(request.getHeader("Employee"));
        //防止出现空指针异常
        if (userid == null) {
            userid = 1L;
        }
        //查询当账号的权限级别，这个前端以及进行了一次权限判断，因此少了部分情况，在前端以及进行考虑
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Employee::getId, userid);
        Employee one = employeeService.getOne(lambdaQueryWrapper);
        //如果为root 1，直接进行更改
        if(one.getRoot()==1){
            mendian.setUpdateUser(userid);
            mendian.setUpdateTime(LocalDateTime.now());
            mendianService.updateById(mendian);
            return R.success("状态更改成功");
        }else {
            //判断当前操作的门店，是不是自己负责的
            //如果是
            if(one.getMendianId()==mendian.getId()){
                mendian.setUpdateUser(userid);
                mendian.setUpdateTime(LocalDateTime.now());
                mendianService.updateById(mendian);
                return R.success("状态更改成功");
            }else {
                return R.error("这不是你的门店，无法进行操作");
            }
        }
    }

    //回读员工信息,同一root下的所有门店员工
    @GetMapping("/supersonList/{id}")
    public R<List<Employee>> supersonList(@PathVariable Long id) {
        //根据门店id查询当前门店的root账号
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Mendian::getId,id);
        Mendian mendian = mendianService.getOne(lambdaQueryWrapper);
        Long RootId=mendian.getCreateUser();
        //查询这个root账户下所有门店
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Mendian::getCreateUser,RootId);
        List<Mendian> mendianList = mendianService.list(lambdaQueryWrapper1);
        //创建员工集合
        List<Employee>employeeList=new ArrayList<>();
        mendianList.stream().forEach(item->{
            //遍历这个集合，获得所有门店的id，获得所有员工
            LambdaQueryWrapper<Employee> lambdaQueryWrapper2=new LambdaQueryWrapper<>();
            lambdaQueryWrapper2.eq(Employee::getMendianId,item.getId());
            lambdaQueryWrapper2.ne(Employee::getRoot, 1);
            lambdaQueryWrapper2.notIn(Employee::getRoot, 1);
            //获得所有员工，添加到employeeList里
            List<Employee> list = employeeService.list(lambdaQueryWrapper2);
            employeeList.addAll(list);
        });
        return R.success(employeeList);
    }

    //查询单个门店信息，用于信息回读
    @GetMapping("/{id}")
    public R<Mendian> GetMendian(@PathVariable Long id) {
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Mendian::getId, id);
        Mendian one = mendianService.getOne(lambdaQueryWrapper);
        return R.success(one);
    }

    //店面展示手机端
    @GetMapping()
    public R<List<Mendian>> Getmain(Long id) {
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如何登录的为root账户，会添加这个条件，保证只进入自己管理的门店
        if (id != null) {
            lambdaQueryWrapper.eq(Mendian::getCreateUser, id);
        }
        lambdaQueryWrapper.eq(Mendian::getStastus, 1);
        List<Mendian> list = mendianService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    //门店保存
    @PostMapping()
    public R<String> savemendian(@RequestBody Mendian mendian, HttpServletRequest request) {
        Long userid = Long.valueOf(request.getHeader("Employee"));
        //理论上要设置成负责人的id，这里为默认值，创建用户的id
        mendian.setSupersonId(userid);
        mendian.setUpdateTime(LocalDateTime.now());
        mendian.setCreateTime(LocalDateTime.now());
        mendian.setCreateUser(userid);
        mendian.setAllmony(BigDecimal.ZERO);
        mendian.setTodaymony(BigDecimal.ZERO);
        mendian.setUpdateUser(userid);
        mendianService.save(mendian);
        return R.success("门店添加成功");
    }
    //用于搜索
    @GetMapping("/search")
    public R<List<Mendian>> Serch(String name){
        String namet=name.toString();
        LambdaQueryWrapper<Mendian> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Mendian::getName,namet);
        List<Mendian> list = mendianService.list(lambdaQueryWrapper);
        if(list.size()==0){
            return R.error("为查询到商品");
        }
        return R.success(list);
    }
    //更新操作
    @Transactional  //保证数据的一致性
    @PostMapping("/update")
    public R<String> Update(@RequestBody Mendian mendian ,HttpServletRequest request){
        Long userid = Long.valueOf(request.getHeader("Employee"));
        LambdaUpdateWrapper<Mendian> lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Mendian::getAddress,mendian.getAddress());
        lambdaUpdateWrapper.set(Mendian::getUpdateTime,LocalDateTime.now());
        lambdaUpdateWrapper.set(Mendian::getUpdateUser,userid);
        lambdaUpdateWrapper.set(Mendian::getImage,mendian.getImage());
        //对当前的管理人员进行处理，我们默认一个门店只有一个负责人，一个权限为2的用户为负责人，如果被更换了，那么需要更改他的权限级别
        //查询当前门店的一些信息
        Mendian mendian1 = mendianService.getById(mendian.getId());
        //如果管理人员没有改变
        if(mendian1.getSupersonId()== mendian.getSupersonId()){
            //不做处理
        }
        //如果发生的更新
        else {
            //将原有的负责人进行权限降级
            LambdaUpdateWrapper<Employee> lambdaUpdateWrapper1=new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper1.eq(Employee::getId,mendian1.getSupersonId());
            lambdaUpdateWrapper1.set(Employee::getRoot,3);
            employeeService.update(lambdaUpdateWrapper1);
            //设置当前店铺新的负责人，进行权限升级，并验证这个门店和需要修改的负责人id是否相同
            //获得当前新的传入的负责人信息
            Employee employeeServiceById = employeeService.getById(mendian.getSupersonId());
//            //如果也是管理人员，直接返回错误
//            if(employeeServiceById.getRoot()==2){
//                return R.error("这个人以及是管理人员啦");
//            }
            //初始化一些语句，减少代码量
            LambdaUpdateWrapper<Employee> lambdaUpdateWrapper2=new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper2.set(Employee::getRoot,2);
            lambdaUpdateWrapper2.set(Employee::getMendianId,mendian.getId());
            lambdaUpdateWrapper2.eq(Employee::getId,employeeServiceById.getId());
            //如果这个新的负责人是同一个门店
            if(employeeServiceById.getMendianId()==mendian.getId()){
                //不做如何处理
            }
            //如果不是一个，更新为当前的门店id
            else {
                lambdaUpdateWrapper2.set(Employee::getMendianId,mendian.getId());
        }
            employeeService.update(lambdaUpdateWrapper1);
            employeeService.update(lambdaUpdateWrapper2);
            lambdaUpdateWrapper.set(Mendian::getSupersonId,mendian.getSupersonId());
        }
        lambdaUpdateWrapper.set(Mendian::getName,mendian.getName());
        lambdaUpdateWrapper.eq(Mendian::getId,mendian.getId());
        lambdaUpdateWrapper.set(Mendian::getJianjie,mendian.getJianjie());
        mendianService.update(lambdaUpdateWrapper);
//        mendianService.update()
        return R.success("成功");
    }

}
