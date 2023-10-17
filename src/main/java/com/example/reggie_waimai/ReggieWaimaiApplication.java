package com.example.reggie_waimai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan  //spring中不包含web的三大特性，需要额外引入
@SpringBootApplication
public class ReggieWaimaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieWaimaiApplication.class, args);
    }

}
