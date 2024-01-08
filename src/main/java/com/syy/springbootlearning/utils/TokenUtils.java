package com.syy.springbootlearning.utils;

import com.syy.springbootlearning.entity.Token;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class TokenUtils {
    public static final int EXPIRE_DATE = 3600000;
    private static final String TOKEN_SALT = "HELLO123";
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 创建token
    public static Token createToken(int id) {

        Date date = new Date();
        Date dateAfter = new Date(date.getTime() + EXPIRE_DATE);//5min
        Object endTime = sdf.format(dateAfter);
        String msg = "id:" + id + ",endTime:" + endTime + ";" + TOKEN_SALT;

        // encode
        byte[] encodedBytes = Base64.getEncoder().encode(msg.getBytes(StandardCharsets.UTF_8));
        String tokenVal = new String(encodedBytes, StandardCharsets.UTF_8);
        return new Token(id, tokenVal);
    }

    // 验证token
    public static Token verify(String token) throws ParseException {
        byte[] decodedBytes = Base64.getDecoder().decode(token.getBytes(StandardCharsets.UTF_8));
        String str = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println(str);

        //将token切割成两部分
        String ahead = str.substring(0, str.indexOf(";"));
        String behind = str.substring(str.indexOf(";") + 1);
        // 检查salt有没有被篡改
        if (!behind.equals(TOKEN_SALT)) {
            System.out.println("已被篡改");
            return null;
        }
        //时间从字符串中截取出来
        String ss = ahead.substring(ahead.indexOf("e"));
        String timeStr = ss.substring(ss.indexOf(":") + 1);

        Date date = sdf.parse(timeStr);
        long endTime = date.getTime();
        Date date1 = new Date();
        long curTime = date1.getTime();
        long diff = endTime - curTime;

        if (diff < 0) {
            System.out.println("已过期");
            return null;
        }

        int id = Integer.parseInt(ahead.substring(ahead.indexOf("i") + 3, ahead.indexOf(",")));

        return new Token(id, token);
    }
}
