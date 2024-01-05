package com.syy.springbootlearning.controller;

import com.syy.springbootlearning.entity.Token;
import com.syy.springbootlearning.entity.User;
import com.syy.springbootlearning.mapper.TokenMapper;
import com.syy.springbootlearning.mapper.UserMapper;
import com.syy.springbootlearning.utils.CookieUtils;
import com.syy.springbootlearning.utils.TokenUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.text.ParseException;


@Controller
//@RestController
@RequestMapping("/account")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenMapper tokenMapper;

    @RequestMapping("/home")
    public ModelAndView show(HttpServletRequest req,
                             HttpServletResponse resp) throws ParseException {
        System.out.println("主页:");
        ModelAndView mav = new ModelAndView();

        // 获取token的cookie
        Cookie cookie = CookieUtils.getCookie(req.getCookies(),"token");
        String tokenVal = cookie.getValue();
        Token token = TokenUtils.verify(tokenVal);

        if(token!=null){
            // 查看数据库token是否匹配
            Token loginToken = tokenMapper.selectOne(token);
            if (loginToken!=null){
                // 更新token
                Token newToken = TokenUtils.createToken(loginToken.getUserID());
                // 存入数据库
                tokenMapper.insert(newToken);
                // 存入客户端Cookie
                Cookie newCookie = new Cookie("token", newToken.getToken());
                resp.addCookie(newCookie);

                mav.setViewName("home");
                mav.addObject("id",loginToken.getUserID());
            }
        }else{
            System.out.println("token不正确");
            mav.setViewName("redirect:loginForm");
        }
        return mav;
    }

    @RequestMapping("/loginForm")
    public String loginForm(){
        return "login";
    }

    @RequestMapping("/login")
    public String index(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse resp) throws SQLException {
        // 验证账户密码
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User loginUser =null;
        try{
            loginUser  = userMapper.login(user);
        } catch (SQLException e){
            e.printStackTrace();
        }

        // 存入token
        if(loginUser!=null){
            // 创建token
            Token token = TokenUtils.createToken(loginUser.getId());
            // 存入数据库
            tokenMapper.insert(token);
            // 存入客户端Cookie
            Cookie cookie = new Cookie("token", token.getToken());
            resp.addCookie(cookie);

            System.out.println("登陆成功");
            System.out.println("生成Token:" + token.getToken());
        }else{
            System.out.println("登陆失败");
        }
        return "redirect:home";
    }
}
