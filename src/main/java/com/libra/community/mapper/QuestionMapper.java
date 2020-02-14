package com.libra.community.mapper;

import com.libra.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhou
 * @date 2020/2/2
 * @time 15:44
 */
@Mapper
@Repository
public interface QuestionMapper {

//    插入问题
    @Insert("insert into question " +
            "(id, title, description, gmt_create, gmt_modified, creator, tag)" +
            "values" +
            "(#{id}, #{title}, #{description}, #{gmtCreate}, #{gmtModified}, #{creator}, #{tag})")
    void create(Question question);

//    查询问题By页数与条数
    @Select("select *" +
            "from question " +
            "limit #{offset}, #{size}")
    List<Question> list(@Param("offset")Integer offset, @Param("size")Integer size);

//    查询问题By页数、条数与userId
    @Select("select *" +
            "from question " +
            "where creator = #{creator}" +
            "limit #{offset}, #{size}")
    List<Question> listByAccountId(@Param("offset")Integer offset, @Param("size")Integer size, @Param("creator")String accountId);

//    查询问题总数
    @Select("select count(1) " +
            "from question ")
    Integer count();

//    查询问题总数ById
    @Select("select count(1) " +
            "from question " +
            "where creator = #{creator}")
    Integer countByAccountId(@Param("creator")String accountId);
}
