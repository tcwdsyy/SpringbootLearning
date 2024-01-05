package com.syy.springbootlearning.utils;


import jakarta.servlet.http.Cookie;

public class CookieUtils {
    public static Cookie getCookie(Cookie[] cookies, String cookieName){
        if(cookies!=null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie;
                }
            }
        }
        return null;
    }
}
