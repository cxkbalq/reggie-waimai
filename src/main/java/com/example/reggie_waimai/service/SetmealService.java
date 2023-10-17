package com.example.reggie_waimai.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.dto.SetmealDto;
import com.example.reggie_waimai.popj.Setmeal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface SetmealService extends IService<Setmeal> {


    public void save(HttpServletRequest request, SetmealDto setmealDto);
    public R<String> delete(List<Long> list);

    public void upload(HttpServletRequest request, SetmealDto setmealDto);

    public void uploadsta(HttpServletRequest request,List<Long> list,Integer sta);


    public R<Page> showmeal(Integer page, Integer pageSize, String name);

    public R<SetmealDto> huidu_setmeal(HttpServletRequest request,Long id);
    public R<SetmealDto> huidu_setmeal2(Long id);

}
