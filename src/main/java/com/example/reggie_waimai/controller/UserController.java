package com.example.reggie_waimai.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reggie_waimai.common.BaseContext;
import com.example.reggie_waimai.popj.CodeLogin;
import com.example.reggie_waimai.common.R;
import com.example.reggie_waimai.popj.User;
import com.example.reggie_waimai.service.UserService;
import com.example.reggie_waimai.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.example.reggie_waimai.utils.SMSUtils;
import com.example.reggie_waimai.utils.ValidateCodeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        SMSUtils smsUtils=new SMSUtils();

        if(StringUtils.isNotEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用阿里云提供的短信服务API完成发送短信
            //smsUtils.sendMessage("阿里云短信测试",phone,code);

            //将生成的验证码储存到redis中，并设置有效时间为5分钟
            redisTemplate.opsForValue().set(phone,"1234",5, TimeUnit.MINUTES);
            //需要将生成的验证码保存到redis
            session.setAttribute(phone,code);
            return R.success("手机验证码短信发送成功");
        }
        return R.error("短信发送失败");
    }
    //登录
    @PostMapping("/login")
    public R<String> login(@RequestBody CodeLogin codeLogin, HttpSession session){

        // 获取输入的值
        String code = (String) codeLogin.getCode();
        String phone1 = (String) redisTemplate.opsForValue().get(codeLogin.getPhone());
        //获取手机号
        String phone = codeLogin.getPhone();
        if(code.equals(phone1)&&code!=null){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //创建jwt令牌，并返回给前端，为了减少代码量，将jwt储存在msg里了
            Map<String, Object> claims=new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username",user.getPhone());
            JwtUtils jwtUtils=new JwtUtils();
            String jwt= jwtUtils.generateJwt(claims);
            log.info("用户端jwt为：{}",jwt);
            //如果登录成功删除键值
            redisTemplate.delete("phone");
            R r=R.success("登录成功");
            user.setEmployeeId(0l);
            r.setMsg(jwt);
            r.setCode(1);
            r.setData(user);
            return r;
        }
        else {
            session.setAttribute("erro","1");
            return R.error("验证码或号码错误");
        }
    }
    //退出登录
    @PostMapping("/loginout")
    public R<String> loyout(){
        return R.success("退出成功");
    }

}
