package com.example.reggie_waimai.dto;


import com.example.reggie_waimai.popj.Dish;
import com.example.reggie_waimai.popj.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    //菜品对应的口味数据
    private List<DishFlavor> flavors = new ArrayList<>();


    private String categoryName;

    private Integer copies;
}
