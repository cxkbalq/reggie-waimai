package com.example.reggie_waimai.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import com.example.reggie_waimai.utils.JwtUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import com.example.reggie_waimai.common.R;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@WebFilter( urlPatterns = "/*")
//@CrossOrigin(origins = "http://localhost:8081") // 允许跨域访问的前端地址
public class JwtFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        //获取前端返回url
        String url=request.getRequestURI();
        //判断url中是否包含login
        String method = ((HttpServletRequest) request).getMethod();
        log.info("当前拦截url为："+url+method);
        //排除因为跨域问题发送的确认请求
        if(method.equals("OPTIONS")){
            filterChain.doFilter(servletRequest,servletResponse);
            log.info("当前为cors验证请求，直接放行");
            return;
        }
        //设置除了跨域请求的所有请求体,如何对OPTIONS进行了处理，会出现错误
        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有域名跨域访问
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT"); // 允许的请求方法
        response.setHeader("Access-Control-Max-Age", "3600"); // 预检请求的有效期，单位：秒
        //如果请求里包含login直接放行,以及图片请求
        if(url.contains("login")||url.contains("common")||url.contains("sendMsg")){
            log.info("登录页面，放行。。。");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        String jwt=request.getHeader("Jwttoken");
        //判读jwt是否存在
        if(!StringUtils.hasLength(jwt)){
            R result= R.success("jwt_exp");
            result.setMsg("NOT_LOGIN");
            //只有在spring的controller中才会自动转化为json，所有这里需要调用阿里的工具
            String not_login= JSONObject.toJSONString(result);
            log.info("令牌不存在当前{}，直接返回错误请求",jwt);
            response.getWriter().write(not_login);
            return;
        }
        jwt = jwt.substring(1, jwt.length() - 1);
        //如果存在判读jwt是否合法
        try {
            JwtUtils jwtUtils=new JwtUtils();
            jwtUtils.parseJWT(jwt);
            log.info("令牌解析成功。。放行");
        }
        catch (Exception e){
            log.info("jwt解析失败");
            R result1= R.success("jwt_exp");
            result1.setMsg("NOT_LOGIN");
            //只有在spring的controller中才会自动转化为json，所有这里需要调用阿里的工具
            String not_login= JSONObject.toJSONString(result1);
            response.getWriter().write(not_login);
            return;
        }
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

    }

}
