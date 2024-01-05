package com.syy.springbootlearning.interceptor;

import com.syy.springbootlearning.utils.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Order(1)
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器:");

        if (CookieUtils.getCookie(request.getCookies(),"token")!=null) {
            System.out.println("持有token");
            return true;
        } else {
            System.out.println("不好意思您还没有登录！还请您登录");
            //response.sendRedirect("loginForm");
            return false;
        }
    }
}
