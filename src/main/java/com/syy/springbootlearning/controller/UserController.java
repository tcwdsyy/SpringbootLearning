package com.syy.springbootlearning.controller;

import com.syy.springbootlearning.entity.User;
import com.syy.springbootlearning.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
//@RestController
@RequestMapping("/account")
public class UserController {
    @Autowired
    UserMapper userMapper;

    @RequestMapping("/home")
    public String show(){
        System.out.println("主页");
        return "home";
    }

    @ResponseBody
    @RequestMapping("/login")
    public User index(@RequestParam("username") String username,
                      @RequestParam("password") String password) throws SQLException {
        System.out.println("测试");
        User user = new User();
        user.setUsername("jason");
        user.setPassword("123456");

        System.out.println(username);
        System.out.println(password);

        User loginUser  = userMapper.login(user);

        return loginUser;
    }


}
