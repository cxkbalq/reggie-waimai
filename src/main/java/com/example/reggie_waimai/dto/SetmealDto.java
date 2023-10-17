package com.example.reggie_waimai.dto;

import com.example.reggie_waimai.popj.Setmeal;
import com.example.reggie_waimai.popj.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
