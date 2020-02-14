package com.libra.community.mapper;

import com.libra.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author zhou
 * @date 2020/1/22
 * @time 21:11
 */
@Mapper
@Repository
public interface UserMapper {
    @Insert("insert into user (name, account_id, token, gmt_create, gmt_modified, avatar_url) " +
            "values (#{name}, #{accountId}, #{token}, #{gmtCreate}, #{gmtModified}, #{avatarUrl}) ")
    void insert(User user);

    @Select("select * " +
            "from user " +
            "where token = #{token} ")
    User findByToken(@Param("token") String token);

    @Select("select u1.* " +
            "from user u1 " +
            "left join user u2 on u1.account_id = u2.account_id and u1.gmt_create < u2.gmt_create " +
            "where u2.id is null and u1.account_id = #{accountId} ")
    User findByAccountId(@Param("accountId") String accountId);
}
