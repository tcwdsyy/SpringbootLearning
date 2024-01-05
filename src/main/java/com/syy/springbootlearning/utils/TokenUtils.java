package com.syy.springbootlearning.utils;

import com.syy.springbootlearning.entity.Token;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class TokenUtils {
    public static final int EXPIRE_DATE = 300000;
    private static final String TOKEN_SALT = "HELLO123";

    // 创建token
    public static String createToken(int id){
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
    public static Token verify(String token){

        return null;
    }
}
