package com.syy.springbootlearning.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Token {
    private Integer userID;
    private String token;

    public Token(Integer userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    public Token(){

    }
}
