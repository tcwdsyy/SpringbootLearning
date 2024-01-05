package com.syy.springbootlearning.mapper;

import com.syy.springbootlearning.entity.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public interface UserMapper {
    @Select("SELECT id,username FROM user WHERE username = #{username} AND password = #{password}")
    User login(User user) throws SQLException;
}
