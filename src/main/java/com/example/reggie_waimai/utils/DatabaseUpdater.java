package com.example.reggie_waimai.utils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.reggie_waimai.popj.Mendian;
import com.example.reggie_waimai.service.MendianService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

//这个是用于开启数据库定时更新的，用于营业额的自动变更
@Component
@Slf4j
public class DatabaseUpdater {
    @Autowired
    private MendianService mendianService;
    // 每天0点触发
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateDatabase() {
        log.info("今日开始营业额重置");
        LambdaUpdateWrapper<Mendian>lambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(Mendian::getTodaymony, BigDecimal.ZERO);
        // 更新所有记录
        mendianService.update(lambdaUpdateWrapper);
    }
}
