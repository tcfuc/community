package com.tcfuc.community.mapper;

import com.tcfuc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhou
 * @date 2020/1/22
 * @time 21:11
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}) ")
    void insert(User user);
}
