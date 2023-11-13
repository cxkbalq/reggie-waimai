package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.BaseContext;
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
@CrossOrigin(origins = "http://localhost:8081") // 允许跨域访问的前端地址
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    private final BaseContext baseContext;
    @Autowired
    public CategoryController(BaseContext baseContext) {
        this.baseContext = baseContext;
    }

    /*
    显示分类信息
    * */
    @GetMapping("/page")
    public R<Page> selectmenu(int page, int pageSize,HttpServletRequest request) {
        Long userid= Long.valueOf(request.getHeader("Employee"));
//        Long userid = Long.valueOf(request.getHeader("Employee"));
        Page<Category> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category>lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getEmployeeId,userid);
        Long employeeId = baseContext.getEmployeeId();
        Page page2 = categoryService.page(page1,lambdaQueryWrapper);
        return R.success(page2);
    }

    /*
    添加分类信息
    * */
    @PostMapping()
    public R<String> addmenu(HttpServletRequest request, @RequestBody Category category) {
        Long userid = Long.valueOf(request.getHeader("Employee"));
        category.setCreateUser(userid);
        category.setUpdateUser(userid);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setEmployeeId(userid);
        categoryService.save(category);
        return R.success("添加成功");
    }

      /*
    删除分类信息
    对应请求      http://127.0.0.1:8080/category?ids=1710956178803654657
    在spring中如何后面的值和我设置的值变量名相同，他会自动映射，无需添加注解
    * */

    @DeleteMapping()
    public R<String> deletemenu(Long id) {
        boolean t = categoryService.removeById(id);
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
        Long userid = Long.valueOf(request.getHeader("Employee"));
        category.setUpdateUser(userid);
        category.setUpdateTime(LocalDateTime.now());
        categoryService.updateById(category);
        return R.success("信息更改成功");
    }

    @GetMapping("/list")
    public R<List<Category>> menushow(Category category,HttpServletRequest request){
        Long userid = Long.valueOf(request.getHeader("Employee"));
        //条件构造
        LambdaQueryWrapper<Category> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        //当 category.getType() 不为 null 时，才会添加等值条件：即 Category 对象中的 type 属性等于 category.getType()
        lambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //orderByAsc() 方法用于升序排序，orderByDesc() 方法用于降序排序
        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        lambdaQueryWrapper.eq(Category::getEmployeeId,userid);

        List<Category> list=categoryService.list(lambdaQueryWrapper);
        return R.success(list);
    }

}
