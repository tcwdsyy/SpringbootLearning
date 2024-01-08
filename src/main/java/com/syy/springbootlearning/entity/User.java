package com.syy.springbootlearning.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer balance;

    public User() {
    }

    public User(Integer id, String username, String password, Integer balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
