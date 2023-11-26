package com.example.reggie_waimai.popj;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Mendian implements Serializable {

    private Long id;
    private String name;
    private Integer stastus;  //1营业 0下线中
    private String address; //门店地址
    //当前门店管理员账号
    private Long supersonId;
    //当前门店当日营业额
    private BigDecimal todaymony;
    //当前门店全部营业额
    private BigDecimal allmony;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private String image;
    private String jianjie;

}
