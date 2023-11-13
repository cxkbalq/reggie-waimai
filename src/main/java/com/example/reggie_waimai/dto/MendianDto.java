package com.example.reggie_waimai.dto;

import com.example.reggie_waimai.popj.Employee;
import com.example.reggie_waimai.popj.Mendian;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class MendianDto extends Mendian{
    private List<Employee> EmployeeList;
    private List<Mendian> MendianList;
}
