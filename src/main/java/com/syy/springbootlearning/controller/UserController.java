package com.syy.springbootlearning.controller;

import com.syy.springbootlearning.entity.Token;
import com.syy.springbootlearning.entity.User;
import com.syy.springbootlearning.mapper.TokenMapper;
import com.syy.springbootlearning.service.UserService;
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
public class UserController {
    @Autowired
    TokenMapper tokenMapper;
    @Autowired
    UserService userService;

    @RequestMapping("/home")
    public ModelAndView show(HttpServletRequest req) {
        System.out.println("-------------");
        System.out.println("主页:");

        ModelAndView mav = new ModelAndView();

        Integer userID = (Integer) req.getAttribute("id");
        if (userID != null) {
            mav.setViewName("home");
            mav.addObject("id", userID);
        } else {
            System.out.println("找不到User数据");
            mav.setViewName("redirect:loginForm");
        }
        return mav;
    }

    @RequestMapping("/loginForm")
    public String loginForm() {
        return "login";
    }

    @RequestMapping("/login")
    public String index(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse resp) {
        System.out.println("-------------");
        System.out.println("登录:");

        // 验证账户密码
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User loginUser = null;
        try {
            loginUser = userService.login(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 存入token
        if (loginUser != null) {
            // 创建token
            Token token = TokenUtils.createToken(loginUser.getId());
            // 存入数据库
            tokenMapper.insert(token);
            // 存入客户端Cookie
            Cookie cookie = new Cookie("token", token.getToken());
            resp.addCookie(cookie);

            System.out.println("登陆成功:" + loginUser);
            System.out.println("生成Token:" + token.getToken());
            return "redirect:home";
        } else {
            System.out.println("登陆失败");
        }
        return "redirect:loginForm";
    }

    @RequestMapping("/transfer")
    public String transfer(@RequestParam Integer id,
                           @RequestParam Integer amount,
                           HttpServletRequest req) {
        System.out.println("-------------");
        System.out.println("转账:");
        Integer userID = (Integer) req.getAttribute("id");
        if (userID != null) {
            userService.transfer(userID, id, amount);
            System.out.println("Succeed");
        } else {
            System.out.println("Failed");
        }
        return "redirect:home";
    }
}
