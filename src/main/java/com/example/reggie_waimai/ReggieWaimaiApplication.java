package com.example.reggie_waimai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

@ServletComponentScan  //spring中不包含web的三大特性，需要额外引入
@SpringBootApplication
@EnableCaching  //开启缓存
@EnableScheduling //开启spring定时
public class ReggieWaimaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieWaimaiApplication.class, args);
    }

}
