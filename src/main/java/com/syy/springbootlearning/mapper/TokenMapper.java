package com.syy.springbootlearning.mapper;

import com.syy.springbootlearning.entity.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenMapper {
    @Insert("INSERT INTO token (userID,token) VALUES ( #{userID}, #{token} )" +
            "ON DUPLICATE KEY UPDATE token=VALUES(token)")
    void insert(Token token);

    @Select("SELECT * FROM token WHERE userID=#{userID} AND token=#{token}")
    Token selectOne(Token token);
}
