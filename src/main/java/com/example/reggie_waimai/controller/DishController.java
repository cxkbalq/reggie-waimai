package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.dto.DishDto;
import com.example.reggie_waimai.popj.Category;
import com.example.reggie_waimai.popj.Dish;
import com.example.reggie_waimai.popj.DishFlavor;
import com.example.reggie_waimai.service.CategoryService;
import com.example.reggie_waimai.service.DishFlavorService;
import com.example.reggie_waimai.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private RedisTemplate redisTemplate;

    /*
     显示菜品信息
    */
    @GetMapping("/page")
    public R<Page> selectmdish(int page, int pageSize,String name,HttpServletRequest request) {

        Long userid=Long.valueOf(request.getHeader("Employee"));;
        Long mendianID= Long.valueOf(request.getHeader("mendian"));
        //设置分页参数
        Page<Dish> page3 = new Page<>(page, pageSize);
        //构建查询条件
        LambdaQueryWrapper<Dish> lambdaQueryWrapper3=new LambdaQueryWrapper<>();
//        lambdaQueryWrapper3.eq(Dish::getCreateUser,userid);
        if(name!=null){
            lambdaQueryWrapper3.like(Dish::getName,name);
        }
        lambdaQueryWrapper3.eq(Dish::getMendianId,mendianID);
        Page<Dish> page1 = dishService.page(page3,lambdaQueryWrapper3);
        Page<DishDto> pagedto =new Page<>();

        LambdaQueryWrapper<Dish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        //复制dish属性到dishdto
        //在如果你从数据库中查询到一堆数据，并且想要将这些数据分
        // 页展示给用户，那么可以将这堆数据封装成 Page 对象，其中 records 就是数据记录的集合
        BeanUtils.copyProperties(page1,pagedto,"records");
        List<Dish> dishList=page1.getRecords();


        List<DishDto> dishDtos=dishList.stream().filter(item -> item.getCategoryId() != null)// 过滤掉id为空的元素
                .map((itme)->{
            DishDto dishDto=new DishDto();
            //复制dish属性到dishdto
            BeanUtils.copyProperties(itme,dishDto);
            //获取分类id以及对应name
            Long id= itme.getCategoryId();
//            LambdaQueryWrapper <Category> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
//            lambdaQueryWrapper1.eq(Category::getMendianId,mendianID);
//            lambdaQueryWrapper1.eq(Category::getId,id);
//            Category category=categoryService.getOne(lambdaQueryWrapper1);
                    Category category=categoryService.getById(id);
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).filter(dish -> dish.getCategoryName() != null) // 过滤掉category为空的元素
                .collect(Collectors.toList());
        pagedto.setRecords(dishDtos);

        //修复页面显示错误bug
        //pagedto.setTotal(dishDtos.size());
        return R.success(pagedto);
    }

    /*
     回读菜品信息
     url: http://127.0.0.1:8080/dish/1397849739276890114
    */
    @GetMapping("/{id}")
    public R<DishDto> update(HttpServletRequest  request,@PathVariable("id") Long id) {
        Dish dish =dishService.getById(id);
        List<DishFlavor> list=new ArrayList<>();
        //构建菜品口味查询条件
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        list= dishFlavorService.list(lambdaQueryWrapper);
        DishDto dishDto = new DishDto();
        //将dish复制给dishdto
        BeanUtils.copyProperties(dish,dishDto);
        Category category=categoryService.getById(id);
        dishDto.setFlavors(list);
//        dishDto.setCategoryId(category.getId());
//        dishDto.setCategoryName(category.getName());
        //补充其他信息

        log.info("当前回传值为：{}",dish.toString());
        return R.success(dishDto);

    }


    /*
   添加菜品信息
   url: http://127.0.0.1:8080/dish/ids=1397849739276890114
   */
    @PostMapping
    public R<String> addDish(HttpServletRequest request, @RequestBody DishDto dishDto){
        dishService.adddish(dishDto,request);
        //构建dish_key
        String key="dish_"+dishDto.getCategoryId()+"_"+dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("菜品添加成功");
    }

    @PutMapping
    public R<String> uploaddish(HttpServletRequest request, @RequestBody DishDto dishDto){
        dishService.uplodadish(dishDto,request);
        //构建dish_key
        String key="dish_"+dishDto.getCategoryId()+"_"+dishDto.getStatus();
        redisTemplate.delete(key);
        return R.success("菜品更改成功");
    }


    /*
    删除菜品信息
    url: http://127.0.0.1:8080/dish/ids=1397849739276890114
    */
    @DeleteMapping()
    public R<String> delete1(@RequestParam("ids")  List<Long> ids){
        Dish dish = dishService.getById(ids.get(0));
        String key="dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        redisTemplate.delete(key);
        ids.stream().forEach(i->
                dishService.removeById(i)
                );
        return R.success("菜品删除成功");
    }
    /*
    修改菜品状态信息
    url:http://127.0.0.1:8080/dish/status/0?ids=1711563689663619074
    */
    @PostMapping("/status/{status}")
    public R<String> upolad(HttpServletRequest request,
                            @PathVariable("status") Integer status,
                            @RequestParam("ids") List<Long> ids){
        Long userid= Long.valueOf(request.getHeader("Employee"));
        List<Dish> list=new ArrayList<>();
        list= ids.stream().map((item) -> {
            Dish dish = new Dish();
            dish = dishService.getById(item);
            dish.setUpdateTime(LocalDateTime.now());
            if(userid!=null){
                dish.setUpdateUser(userid);
            }
            dish.setStatus(status);

            return dish;
        }).collect(Collectors.toList());
        list.stream().forEach(i->dishService.updateById(i));
        return R.success("菜品删除成功");

    }
    /*
    套餐管理dish信息回读
    url:http://127.0.0.1:8080/dish/status/0?ids=1711563689663619074
    */
    @GetMapping("/list")
    public R<List<DishDto>> huiduDish(Dish dish){

        //构建dish_key
        String key="dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        //查询redis中是否存在
        List<DishDto> dishDtos1= (List<DishDto>) redisTemplate.opsForValue().get(key);

        //如果纯在时
        if(dishDtos1!=null){
            return R.success(dishDtos1);
        }
        //如果redis中没有缓存

        Long id1= dish.getCategoryId();
        List<Dish> list=new ArrayList<>();
        LambdaQueryWrapper<Dish>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId,id1);
        lambdaQueryWrapper.eq(Dish::getStatus,1);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        list= dishService.list(lambdaQueryWrapper);
        List<DishDto> dishDtos=list.stream().map((itme)->{
            DishDto dishDto=new DishDto();
            //复制dish属性到dishdto
            BeanUtils.copyProperties(itme,dishDto);
            //获取分类id以及对应name
            Long id= itme.getCategoryId();
            Category category=categoryService.getById(id);
           // dishDto.setCategoryName(category.getName());
            Long dishid=itme.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(DishFlavor::getDishId,dishid);
            List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper1);
            dishDto.setFlavors(dishFlavors);
            return dishDto;
        }).collect(Collectors.toList());
        //将sql查询的数据储存到redis
            redisTemplate.opsForValue().set(key,dishDtos,30, TimeUnit.MINUTES);

        return R.success(dishDtos);
    }



}
