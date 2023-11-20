package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggie_waimai.common.BaseContext;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.AddressBook;
import com.example.reggie_waimai.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 地址簿管理
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook, HttpServletRequest request) {
        Long userid=Long.valueOf(request.getHeader("user"));
        addressBook.setUserId(userid);
        addressBook.setCreateTime(LocalDateTime.now());
        addressBook.setUpdateTime(LocalDateTime.now());
        addressBook.setUpdateUser(userid);
        addressBook.setCreateUser(userid);
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook,HttpServletRequest request) {
        Long userid=Long.valueOf(request.getHeader("user"));
        log.info("addressBook:{}", addressBook);
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, userid);
        wrapper.set(AddressBook::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBookService.update(wrapper);
        LambdaUpdateWrapper<AddressBook> lambdaQueryWrapper=new LambdaUpdateWrapper<>();
        lambdaQueryWrapper.eq(AddressBook::getId,addressBook.getId());
        lambdaQueryWrapper.set(AddressBook::getIsDefault, 1);
        lambdaQueryWrapper.set(AddressBook::getUpdateTime,LocalDateTime.now());
        lambdaQueryWrapper.set(AddressBook::getUpdateUser, userid);
        //SQL:update address_book set is_default = 1 where id = ?
        boolean b = addressBookService.update(lambdaQueryWrapper);
        return R.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("当前用户暂未添加地址");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault(HttpServletRequest request) {
        Long userid=Long.valueOf(request.getHeader("user"));
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, userid);
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook,HttpServletRequest request) {
        Long userid=Long.valueOf(request.getHeader("user"));
        addressBook.setUserId(userid);
        log.info("addressBook:{}", addressBook);

        //条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        //SQL:select * from address_book where user_id = ? order by update_time desc
        return R.success(addressBookService.list(queryWrapper));
    }

    /**
     * 更改地址信息
     */
    @PutMapping
    public R<String> updateAddres(@RequestBody AddressBook addressBook){
      LambdaUpdateWrapper<AddressBook> wrapper =new LambdaUpdateWrapper<>();
      wrapper.set(AddressBook::getUpdateTime,LocalDateTime.now());
      wrapper.set(AddressBook::getPhone,addressBook.getPhone());
      wrapper.set(AddressBook::getDetail,addressBook.getDetail());
      wrapper.set(AddressBook::getLabel,addressBook.getLabel());
      wrapper.eq(AddressBook::getId,addressBook.getId());
      boolean update = addressBookService.update(wrapper);
      if(update){
          return R.success("更新成功");
      }else {
          return R.error("数据更新失败，请刷新重新体积试试");
      }

    }
}
