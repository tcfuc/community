package com.libra.community.mapper;

import com.libra.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhou
 * @date 2020/2/20
 * @time 16:22
 */
@Mapper
public interface CommentExtMapper {
    void incCommentCount(Comment comment);
}
