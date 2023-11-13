package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.dto.DishDto;
import com.example.reggie_waimai.dto.SetmealDto;
import com.example.reggie_waimai.popj.Setmeal;
import com.example.reggie_waimai.popj.Dish;
import com.example.reggie_waimai.popj.Setmeal;
import com.example.reggie_waimai.popj.SetmealDish;
import com.example.reggie_waimai.service.CategoryService;
import com.example.reggie_waimai.service.SetmealDishService;
import com.example.reggie_waimai.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    /*
    保存套餐信息
    */
    @PostMapping()
    @CacheEvict(value = "Setmeal",allEntries = true)
    public R<String> addmeal(HttpServletRequest request,@RequestBody SetmealDto setmealDto){
        setmealService.save(request,setmealDto);
        return R.success("套餐添加成功");
    }

    /*
    分页展示套餐信息
    */
    @GetMapping("/page")
    public R<Page> showmeal(Integer page, Integer pageSize, String name,HttpServletRequest request){
        R<Page> r= setmealService.showmeal(page,pageSize,name,request);
        return r;
    }


    /*
    删除套餐信息
    */
    @DeleteMapping()
    @CacheEvict(value = "Setmeal",allEntries = true)
    public R<String> delete(@RequestParam("ids") List<Long> ids){
        //查询当前菜单状态
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper=new LambdaQueryWrapper<>();

        R r= setmealService.delete(ids);
        return r;
    }


    /*
    回读套餐信息
    */
    @GetMapping("/{id}")
    public R<SetmealDto> huidu_setmeal(HttpServletRequest request, @PathVariable Long id){

        R r= setmealService.huidu_setmeal(request,id);
        return r;

    }

    @PutMapping()
    @CacheEvict(value = "Setmeal",allEntries = true)
    public R<String> upload(HttpServletRequest request, @RequestBody SetmealDto setmealDto){
        setmealService.upload(request, setmealDto);
        return R.success("修改成功");
    }

    @PostMapping("/status/{status}")
    public R<String> upolad(HttpServletRequest request,
                            @PathVariable("status") Integer status,
                            @RequestParam("ids") List<Long> ids){

        setmealService.uploadsta(request,ids,status);
       return R.success("修改成功");
    }


    @Cacheable(value = "Setmeal",key = "#category.categoryId+'_'+#category.categoryId")
    @GetMapping("/list")
    public R<List<Setmeal>> menushow(Setmeal category){
        //条件构造
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //当 category.getType() 不为 null 时，才会添加等值条件：即 Setmeal 对象中的 type 属性等于 category.getType()
        //orderByAsc() 方法用于升序排序，orderByDesc() 方法用于降序排序
        lambdaQueryWrapper.eq(Setmeal::getCategoryId,category.getCategoryId());
        lambdaQueryWrapper.eq(Setmeal::getStatus,category.getStatus());
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list=setmealService.list(lambdaQueryWrapper);
        return R.success(list);
    }


    @GetMapping("/dish/{id}")
   public R<SetmealDto> hui_du2( @PathVariable Long id){

        R r= setmealService.huidu_setmeal2(id);
        return r;

    }




}
