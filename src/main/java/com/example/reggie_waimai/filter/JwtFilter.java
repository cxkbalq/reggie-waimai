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
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;

import com.example.reggie_waimai.common.R;
import org.springframework.web.bind.annotation.CrossOrigin;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class JwtFilter implements Filter {

    //定义不需要拦截的路径
    private static final String[] Path = {"/login", "/common", "/sendMsg", "/mendian"};
    //定义拦截区分用户端和商家端
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取请求路径，以及当前请求方法
        String url = request.getRequestURI();
        String method = request.getMethod();

        log.info("当前拦截url为：" + url + method);

//        if ("OPTIONS".equals(method)) {
//            log.info("当前为CORS验证请求，直接放行");
//            filterChain.doFilter(servletRequest, servletResponse);
//            return;
//        }
////        设置跨域资源共享
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");

        //放行登录页面
        if (Arrays.stream(Path).anyMatch(url::contains)) {
            log.info("放行登录页面...");
            filterChain.doFilter(servletRequest, servletResponse);
//            writeErrorResponse(response, "NOT_LOGIN");
            return;
        }

        //验证设置的信息是否完整，防止人为删除报错
        String userAgent = request.getHeader("User-Agent");
        //用户端
        if (userAgent.contains("Linux")) {
            String userHeader = request.getHeader("user");
            if (userHeader == null || Long.valueOf(userHeader) == null) {
                log.info("用户端信息不完整，跳转到登录页面");
                writeErrorResponse(response, "NOT_LOGIN");
                return;
            }
        }
        //电脑端
        else {
            String employee= request.getHeader("Employee");
            String mendian=request.getHeader("mendian");
            if (employee == null || Long.valueOf(employee) == null) {
                log.info("用户端信息不完整，跳转到登录页面");
                writeErrorResponse(response, "NOT_LOGIN");
                return;
            }
            if (mendian == null || Long.valueOf(mendian) == null) {
                log.info("用户端信息不完整，跳转到登录页面");
                writeErrorResponse(response, "NOT_LOGIN");
                return;
            }
        }




        String jwt = request.getHeader("Jwttoken");

        if (!StringUtils.hasLength(jwt)) {
            log.info("令牌不存在当前{}，直接返回错误请求", jwt);
            writeErrorResponse(response, "NOT_LOGIN");
            return;
        }

        jwt = jwt.substring(1, jwt.length() - 1);

        try {
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.parseJWT(jwt);
            log.info("令牌解析成功...放行");
        } catch (Exception e) {
            log.info("JWT解析失败");
            writeErrorResponse(response, "NOT_LOGIN");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    //    private void writeErrorResponse(HttpServletResponse response, String message) throws IOException {
//        R result = R.success("jwt_exp");
//        result.setMsg(message);
//        response.getWriter().write(JSONObject.toJSONString(result));
//    }
    private void writeErrorResponse(HttpServletResponse response, String message) throws IOException {
        R result = R.success("jwt_exp");
        result.setMsg(message);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(JSONObject.toJSONString(result).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

}