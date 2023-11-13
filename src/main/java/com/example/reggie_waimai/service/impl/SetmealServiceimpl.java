package com.example.reggie_waimai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.dto.SetmealDto;
import com.example.reggie_waimai.mapper.SetmealMapper;
import com.example.reggie_waimai.popj.DishFlavor;
import com.example.reggie_waimai.popj.Setmeal;
import com.example.reggie_waimai.popj.SetmealDish;
import com.example.reggie_waimai.service.CategoryService;
import com.example.reggie_waimai.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SetmealServiceimpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishServiceimpl setmealDishServiceimpl;
    @Autowired
    private CategoryService categoryService;
    //开启事务回滚，保证数据的统一性
    @Transactional
    public void save(HttpServletRequest request, SetmealDto setmealDto) {
        //补充缺少的信息setmeal
        Long root_id=(Long)request.getSession().getAttribute("employee");
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setCreateTime(LocalDateTime.now());
        if(root_id==null){
            root_id=1l;
        }
        setmealDto.setUpdateUser(root_id);
        setmealDto.setCreateUser(root_id);
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDto,setmeal);
        //保存setmeal信息
        this.save(setmeal);
        //获取setmeal_id
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Setmeal::getName,setmealDto.getName());
        Long setmealid = this.getOne(lambdaQueryWrapper).getId();
        //保存
        List<SetmealDish> setmealDishList1=null;
        Long finalRoot_id = root_id;
        setmealDishList1= setmealDto.getSetmealDishes().stream().map((item)->{
            //补充缺少的信息setmealDish
            item.setUpdateTime(LocalDateTime.now());
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateUser(finalRoot_id);
            item.setCreateUser(finalRoot_id);
            item.setSetmealId(setmealid);
            return item;
        }).collect(Collectors.toList());
        //保存添加的菜品信息
        setmealDishServiceimpl.saveBatch(setmealDishList1);

    }

    @Transactional
    public R<String> delete(List<Long> list) {
        //删除单条情况
        if(list.size()==1){
            Long i=list.get(0);
            Integer str=this.getById(i).getStatus();
            if(str==0){
                this.removeById(i);
                LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(SetmealDish::getSetmealId,i);
                setmealDishServiceimpl.remove(lambdaQueryWrapper);
                return R.success("删除成功");
            }
            else {
                return R.error("当前套餐为起售状态，无法删除");
            }
        }
        else {
            Integer flag=0;
            //删除多条情况
            for (int i = 0; i < list.size(); i++) {
                Integer str=this.getById(list.get(i)).getStatus();
                if(str==0){
                    this.removeById(list.get(i));
                    LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
                    lambdaQueryWrapper.eq(SetmealDish::getSetmealId,list.get(i));
                    setmealDishServiceimpl.remove(lambdaQueryWrapper);
                }
                else {
                    flag=1;
                    log.info("当前套餐状态为起售，无法删除");
                }
            }
            if(flag==1){
                return R.success("当前删除数据中有起售套餐，已自动为你跳过");
            }
            else {
                return R.success("删除成功");
            }
        }

    }

    @Transactional
    public void upload(HttpServletRequest request, SetmealDto setmealDto) {
        //补充缺少的信息setmeal
        Long root_id=(Long)request.getSession().getAttribute("employee");
        setmealDto.setUpdateTime(LocalDateTime.now());
        setmealDto.setUpdateUser(root_id);
        if(root_id==null){
            root_id=1l;
        }

        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDto,setmeal);
        Long id=setmealDto.getId();
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getId,id);
        this.update(setmeal,lambdaQueryWrapper1);
        //获取setmeal_id
        //保持dish的口味
        List<SetmealDish> list=setmealDto.getSetmealDishes();
        //将获得菜品id添加到dishFlavor中
        //forEach() 用于对流中的每个元素进行操作，但不会返回一个新的流或集合。而 map() 方法则可以将流中的每个元素映射到
        // 新的元素，并返回一个新
        //的流。在这种情况下，使用 map() 更符合你的需求，可以保留每个元素的修改，并生成一个新的 List 集合。

        //删除setmealdish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper12=new LambdaQueryWrapper<>();
        lambdaQueryWrapper12.eq(SetmealDish::getSetmealId,id);
        setmealDishServiceimpl.remove(lambdaQueryWrapper12);
        Long finalRoot_id1 = root_id;
        list= list.stream().map((item)->{
            item.setSetmealId(id);
            item.setUpdateTime(LocalDateTime.now());
            item.setCreateTime(LocalDateTime.now());
            item.setUpdateUser(finalRoot_id1);
            item.setCreateUser(finalRoot_id1);
            return item;
        }).collect(Collectors.toList());
        setmealDishServiceimpl.saveOrUpdateBatch(list);
    }

    @Transactional
    public void uploadsta(HttpServletRequest request, List<Long> list,Integer sta) {

        list.stream().forEach(i->{
            Setmeal setmeal=new Setmeal();
            setmeal=this.getById(i);
            setmeal.setUpdateTime(LocalDateTime.now());
            Long root_id=(Long)request.getSession().getAttribute("employee");
            if(root_id==null){
                root_id=1l;
            }
            setmeal.setUpdateUser(root_id);
            setmeal.setStatus(sta);
            LambdaQueryWrapper<Setmeal> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Setmeal::getId,i);
            this.update(setmeal,lambdaQueryWrapper);

        });
    }

    @Override
    public R<Page> showmeal(Integer page, Integer pageSize, String name,HttpServletRequest request) {
        //获得管理id
        Long userid=(Long)request.getSession().getAttribute("employee");
        log.info("{}{}",page.toString(),pageSize.toString());
        //构造分页查询器
        Page<Setmeal> page1=new Page<>(page,pageSize);
        //添加查询条件
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1=new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCreateUser,userid);
        if(name!=null){
            lambdaQueryWrapper1.like(Setmeal::getName,name);
        }

        Page<Setmeal> page2 = this.page(page1,lambdaQueryWrapper1);
        Page<SetmealDto> dtoPage=new Page<>();
        BeanUtils.copyProperties(page2,dtoPage,"records");
        //创建空集合以用来接收page1
        List<SetmealDto> list= page2.getRecords().stream().map(i->{
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(i,setmealDto);
            log.info("{}", i.getCategoryId());
            String categoryName= categoryService.getById(i.getCategoryId()).getName();
            //获得套餐id
            Long taochanid=i.getId();
            LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SetmealDish::getSetmealId,taochanid);
            List<SetmealDish> setmealDishList= setmealDishServiceimpl.list(lambdaQueryWrapper);
            setmealDto.setCategoryName(categoryName);
            setmealDto.setSetmealDishes(setmealDishList);
            return setmealDto;
        }).collect(Collectors.toList());
        log.info("{}",list);
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @Override
    public R<SetmealDto> huidu_setmeal(HttpServletRequest request, Long id) {
        //操作的账户id
        Long root_user=(Long) request.getSession().getAttribute("employee");

        Setmeal setmeal=new Setmeal();
        List<SetmealDish> list=new ArrayList<>();
        SetmealDto setmealDto=new SetmealDto();
        setmeal=this.getById(id);
        String categoryName= categoryService.getById( setmeal.getCategoryId()).getName();
        //获得套餐id
        Long taochanid=setmeal.getId();
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId,taochanid);
        List<SetmealDish> setmealDishList= setmealDishServiceimpl.list(lambdaQueryWrapper);
        setmealDto.setCategoryName(categoryName);
        setmealDto.setSetmealDishes(setmealDishList);
        BeanUtils.copyProperties(setmeal,setmealDto);
        return R.success(setmealDto);
    }
    @Override
    public R<SetmealDto> huidu_setmeal2(Long id) {
        Setmeal setmeal=new Setmeal();
        List<SetmealDish> list=new ArrayList<>();
        SetmealDto setmealDto=new SetmealDto();
        setmeal=this.getById(id);
        String categoryName= categoryService.getById( setmeal.getCategoryId()).getName();
        //获得套餐id
        Long taochanid=setmeal.getId();
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId,taochanid);
        List<SetmealDish> setmealDishList= setmealDishServiceimpl.list(lambdaQueryWrapper);
        setmealDto.setCategoryName(categoryName);
        setmealDto.setSetmealDishes(setmealDishList);
        BeanUtils.copyProperties(setmeal,setmealDto);
        return R.success(setmealDto);
    }
}
