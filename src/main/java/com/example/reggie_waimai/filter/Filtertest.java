//package com.example.reggie_waimai.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.example.reggie_waimai.common.R;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import com.example.reggie_waimai.common.BaseContext;
//
//import javax.servlet.annotation.WebFilter;
//import java.io.IOException;
//
//@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
//@Slf4j
//public class Filtertest implements Filter {
//    //路径匹配器，支持通配符
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        //1、获取本次请求的URI
//        String requestURI = request.getRequestURI();// /backend/index.html
//        log.info("拦截到请求：{}",requestURI);
//
//        //定义不需要处理的请求路径
//        String[] urls = new String[]{
//                "/employee/login",
//                "/employee/logout",
//                "/backend/**",
//                "/front/**",
//                "/common/**",
//                "/user/sendMsg",
//                "/user/login",
//                "/plugins/**",
//                "/styles/**",
//                "/swagger-ui.html",
//                "/favicon.ico",
//                "/doc.html",
//                "/webjars/**",
//                "/swagger-resources",
//                "/v2/api-docs"
//
//        };
//
//        //2、判断本次请求是否需要处理
//        boolean check = check(urls, requestURI);
//
//        //3、如果不需要处理，则直接放行
//        if (check) {
//            //设置新的映射解决一些小bug
//            if ("/backend/page/page/login/login.html".equals(requestURI)) {
//                response.sendRedirect("/backend/page/login/login.html");
//                return;
//            }
//            log.info("本次请求{}不需要处理", requestURI);
//            filterChain.doFilter(request, response);
//            return;
//        }
////
////        4-1、判断登录状态，如果已登录，则直接放行
////        优化一个商家端或者用户端登录一个都可以放行的bug
//        String[] segments = requestURI.split("/");
//        //
//        if (segments.length > 1 || segments[1].equals("user")||segments[0].equals("front")) {
//            //4-2、判断登录状态，如果已登录，则直接放行
//            if(request.getSession().getAttribute("user") != null){
//                log.info("用户已登录用户端，用户id为：{}",request.getSession().getAttribute("user"));
//                log.info("用户已登录用户端，用户id为：{}",request.getSession().getAttribute("user"));
//                Long userId = (Long) request.getSession().getAttribute("user");
//                BaseContext.setCurrentId(userId);
//                filterChain.doFilter(request,response);
//                return;
//            }
//
//        } else {
//            if(request.getSession().getAttribute("employee") != null){
//                log.info("用户已登录商家端，用户id为：{}",request.getSession().getAttribute("employee"));
//                log.info("用户已登录商家端，用户id为：{}",request.getSession().getAttribute("employee"));
//                //http://127.0.0.1:8080/front/index.html
//                Long empId = (Long) request.getSession().getAttribute("employee");
//                BaseContext.setCurrentId(empId);
//                filterChain.doFilter(request,response);
//                return;
//            }
//        }
//
//        if(request.getSession().getAttribute("user") != null){
//                log.info("用户已登录用户端，用户id为：{}",request.getSession().getAttribute("user"));
//                log.info("用户已登录用户端，用户id为：{}",request.getSession().getAttribute("user"));
//                Long userId = (Long) request.getSession().getAttribute("user");
//                BaseContext.setCurrentId(userId);
//                filterChain.doFilter(request,response);
//                return;
//            }
//
//        if(request.getSession().getAttribute("employee") != null){
//                log.info("用户已登录商家端，用户id为：{}",request.getSession().getAttribute("employee"));
//                log.info("用户已登录商家端，用户id为：{}",request.getSession().getAttribute("employee"));
//                //http://127.0.0.1:8080/front/index.html
//                Long empId = (Long) request.getSession().getAttribute("employee");
//                BaseContext.setCurrentId(empId);
//                filterChain.doFilter(request,response);
//                return;
//            }
//
//        log.info("用户未登录");
//        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//        return;
//
//    }
//
//    /**
//     * 路径匹配，检查本次请求是否需要放行
//     * @param urls
//     * @param requestURI
//     * @return
//     */
//    public boolean check(String[] urls,String requestURI){
//        for (String url : urls) {
//            boolean match = PATH_MATCHER.match(url, requestURI);
//            if(match){
//                return true;
//            }
//        }
//        return false;
//    }
//
//}
