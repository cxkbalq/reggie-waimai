package com.example.reggie_waimai.common;


//import com.sun.jdi.request.ThreadStartRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice(annotations = {RestController.class, Controller.class})

@ResponseBody
@Slf4j
//@RestControllerAdvice    //定义全局异常处理类
public class GlobalException {
    //定义全局异常处理方法
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R <String>Empexception(SQLIntegrityConstraintViolationException ex){
        return R.error("添加用户错误");
    }
//    @ExceptionHandler(Exception.class)
//    public R <String>Empexception1(Exception ex){
//        ex.printStackTrace();
//        return R.error("添加用户错误");
//    }
}
