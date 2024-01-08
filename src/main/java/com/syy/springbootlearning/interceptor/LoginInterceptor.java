package com.syy.springbootlearning.interceptor;

import com.syy.springbootlearning.entity.Token;
import com.syy.springbootlearning.mapper.TokenMapper;
import com.syy.springbootlearning.utils.CookieUtils;
import com.syy.springbootlearning.utils.TokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Order(1)
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    TokenMapper tokenMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("-------------");
        System.out.println("拦截器:");

        // 获取token的cookie
        Cookie cookie = CookieUtils.getCookie(request.getCookies(),"token");

        if (cookie!=null) {
            //System.out.println("持有token");

            String tokenVal = cookie.getValue();
            Token token = TokenUtils.verify(tokenVal);

            if(token!=null){
                // 查看数据库token是否匹配
                Token loginToken = tokenMapper.selectOne(token);
//                Token loginToken = token;
                if (loginToken!=null){
                    System.out.println("身份验证通过");
                    request.setAttribute("id",loginToken.getUserID());
                    return true;
                }else {
                    System.out.println("无法找到token");
                    response.sendRedirect("loginForm");
                    return false;
                }
            }else{
                System.out.println("token不正确");
                response.sendRedirect("loginForm");
                return false;
            }
        } else {
            System.out.println("不好意思您还没有登录！还请您登录");
            response.sendRedirect("loginForm");
            return false;
        }
    }
}
