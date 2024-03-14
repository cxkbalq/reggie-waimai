package com.example.reggie_waimai.controller;

import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.Orders;
import com.example.reggie_waimai.service.AliPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

@Slf4j
@Controller
public class AliPayController {
     @Autowired
     private AliPayService aliPayService;
      /**
     * 处理支付宝支付请求
     * @throws Exception
     */
    @GetMapping("/pay") // &subject=xxx&traceNo=xxx&totalAmount=xxx
    public R<String> pay(@RequestBody Orders orders, HttpServletRequest request) throws Exception {
        return null;
    }

    /**
     * 处理支付宝回调请求
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/notify")  // 注意这里必须是POST接口
    public R<String> payNotify(HttpServletRequest request) throws Exception {
        log.info("支付宝controller接受请求参数:{}", request);
        if(aliPayService.Notify(request)){
            return R.success("支付成功");
        }
        String message = "订单未支付或支付异常，请重新支付！";
        return R.error(message);
    }

}
