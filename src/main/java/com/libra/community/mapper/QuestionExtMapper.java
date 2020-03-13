package com.libra.community.mapper;

import com.libra.community.model.Question;

import java.util.List;

/**
 * @author zhou
 * @date 2020/2/20
 * @time 16:22
 */
public interface QuestionExtMapper {
    void incViewCount(Question question);
    void incCommentCount(Question question);
    List<Question> selectRelated(Question question);
}
