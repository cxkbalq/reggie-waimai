package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.Category;
import com.example.reggie_waimai.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*
    显示分类信息
    * */
    @GetMapping("/page")
    public R<Page> selectmenu(int page, int pageSize) {
        Page<Category> page1 = new Page<>(page, pageSize);
        Page page2 = categoryService.page(page1);
        return R.success(page2);
    }

    /*
    添加分类信息
    * */
    @PostMapping()
    public R<String> addmenu(HttpServletRequest request, @RequestBody Category category) {
        Long userid = (Long) request.getSession().getAttribute("employee");
        category.setCreateUser(userid);
        category.setUpdateUser(userid);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryService.save(category);
        return R.success("添加成功");
    }

      /*
    删除分类信息
    对应请求      http://127.0.0.1:8080/category?ids=1710956178803654657
    在spring中如何后面的值和我设置的值变量名相同，他会自动映射，无需添加注解
    * */

    @DeleteMapping()
    public R<String> deletemenu(Long ids) {
        boolean t = categoryService.removeById(ids);
        if (t) {
            return R.success("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    /*
  修改分类信息
    * */
    @PutMapping()
    public R<String> updatemenu(HttpServletRequest request, @RequestBody Category category) {
        Long userid = (Long) request.getSession().getAttribute("employee");
        category.setUpdateUser(userid);
        category.setUpdateTime(LocalDateTime.now());
        categoryService.updateById(category);
        return R.success("信息更改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> menushow(Category category){
        //条件构造
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //当 category.getType() 不为 null 时，才会添加等值条件：即 Category 对象中的 type 属性等于 category.getType()
        lambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //orderByAsc() 方法用于升序排序，orderByDesc() 方法用于降序排序
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list=categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }

}
