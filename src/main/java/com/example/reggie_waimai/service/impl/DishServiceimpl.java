package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.dto.DishDto;
import com.example.reggie_waimai.mapper.DishMapper;
import com.example.reggie_waimai.popj.Dish;
import com.example.reggie_waimai.popj.DishFlavor;
import com.example.reggie_waimai.service.DishFlavorService;
import com.example.reggie_waimai.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceimpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /*
新加菜品
* */
    //保持数据的一致性和完整性 @Transactional
    @Transactional
    public void adddish(DishDto dishDto, HttpServletRequest request) {
        Long userid= (Long) request.getSession().getAttribute("employee");
        if(userid==null){
            userid= 1L;
        }

        dishDto.setCreateUser(userid);
        dishDto.setUpdateUser(userid);
        dishDto.setCreateTime(LocalDateTime.now());
        dishDto.setUpdateTime(LocalDateTime.now());
        //保存菜品的基本信息
        this.save(dishDto);

        Long id=dishDto.getId();
        //保持dish的口味
        List<DishFlavor> list=dishDto.getFlavors();
        //将获得菜品id添加到dishFlavor中
        //forEach() 用于对流中的每个元素进行操作，但不会返回一个新的流或集合。而 map() 方法则可以将流中的每个元素映射到
        // 新的元素，并返回一个新
        //的流。在这种情况下，使用 map() 更符合你的需求，可以保留每个元素的修改，并生成一个新的 List 集合。
        Long finalUserid = userid;
        list= list.stream().map((item)->{
            item.setDishId(id);
            item.setUpdateTime(LocalDateTime.now());
            item.setCreateTime(LocalDateTime.now());
            item.setCreateUser(finalUserid);
            item.setUpdateUser(finalUserid);
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(list);

    }

    @Transactional
    public void uplodadish(DishDto dishDto, HttpServletRequest request) {
        Long userid= (Long) request.getSession().getAttribute("employee");
        if(userid==null){
            userid= 1L;
        }

        dishDto.setUpdateUser(userid);
        dishDto.setUpdateTime(LocalDateTime.now());
        //保存菜品的基本信息
        this.updateById(dishDto);

        Long id=dishDto.getId();
        //保持dish的口味
        List<DishFlavor> list=dishDto.getFlavors();
        //将获得菜品id添加到dishFlavor中
        //forEach() 用于对流中的每个元素进行操作，但不会返回一个新的流或集合。而 map() 方法则可以将流中的每个元素映射到
        // 新的元素，并返回一个新
        //的流。在这种情况下，使用 map() 更符合你的需求，可以保留每个元素的修改，并生成一个新的 List 集合。
        Long finalUserid = userid;
        list= list.stream().map((item)->{
            item.setDishId(id);
            item.setUpdateTime(LocalDateTime.now());
            item.setUpdateUser(finalUserid);
            return item;
        }).collect(Collectors.toList());

//        dishFlavorService.saveBatch(list);
        dishFlavorService.saveOrUpdateBatch(list);
    }

}
