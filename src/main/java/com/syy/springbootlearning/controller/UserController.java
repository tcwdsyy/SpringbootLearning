package com.syy.springbootlearning.controller;

import com.syy.springbootlearning.entity.Token;
import com.syy.springbootlearning.entity.User;
import com.syy.springbootlearning.mapper.TokenMapper;
import com.syy.springbootlearning.mapper.UserMapper;
import com.syy.springbootlearning.utils.CookieUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@Controller
//@RestController
@RequestMapping("/account")
public class UserController {
    public static final int EXPIRE_DATE = 300000;
    private static final String TOKEN_SALT = "HELLO123";
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenMapper tokenMapper;

    @RequestMapping("/home")
    public String show(HttpServletRequest req){
        System.out.println("主页");

        // 获取token的cookie
        Cookie cookie = CookieUtils.getCookie(req.getCookies(),"token");
        String token = cookie.getValue();
//        if(verify(token)){
//
//        }


        return "home";
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
            String tokenValue = createToken(loginUser.getId());
            Token token = new Token(loginUser.getId(), tokenValue);
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

    // 创建token
    public String createToken(int id){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Date dateAfter = new Date(date.getTime()+EXPIRE_DATE);//5min
        Object endTime = sdf.format(dateAfter);
        String msg = "id:"+id+",endTime:"+endTime + ";" + TOKEN_SALT;

        // encode
        byte[] encodedBytes = Base64.getEncoder().encode(msg.getBytes(StandardCharsets.UTF_8));
        return new String(encodedBytes);
    }

    // 验证token
    public boolean verify(String token){

        return false;
    }

}
