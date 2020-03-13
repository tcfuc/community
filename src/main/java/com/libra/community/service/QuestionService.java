package com.libra.community.service;

import com.libra.community.dto.PaginationDTO;
import com.libra.community.dto.QuestionDTO;
import com.libra.community.exception.CustomizeErrorCode;
import com.libra.community.exception.CustomizeException;
import com.libra.community.mapper.QuestionExtMapper;
import com.libra.community.mapper.QuestionMapper;
import com.libra.community.mapper.UserMapper;
import com.libra.community.model.Question;
import com.libra.community.model.QuestionExample;
import com.libra.community.model.User;
import com.libra.community.model.UserExample;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhou
 * @date 2020/2/5
 * @time 13:17
 */
@Service
public class QuestionService {

    @Resource
    UserMapper userMapper;

    @Resource
    QuestionMapper questionMapper;

    @Resource
    QuestionExtMapper questionExtMapper;

    public PaginationDTO list(Integer currentPage, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        PaginationDTO paginationDTO = page(questionExample, currentPage, size);
        return paginationDTO;
    }

    public PaginationDTO listByAccountId(Integer currentPage, Integer size, String accountId) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(accountId);
        questionExample.setOrderByClause("gmt_create desc");
        PaginationDTO paginationDTO = page(questionExample, currentPage, size);
        return paginationDTO;
    }

    public List<Question> listById(Long id) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andIdEqualTo(id);
        return questionMapper.selectByExample(questionExample);
    }

    public PaginationDTO page(QuestionExample questionExample, Integer currentPage, Integer size) {
//        计算问题页数
        Integer totalCount = Math.toIntExact(questionMapper.countByExample(questionExample));
        Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
//        对当前页进行范围判断
        currentPage = currentPage < 1 ? 1 : currentPage;
        currentPage = currentPage > totalPage ? totalPage : currentPage;
//        查询问题
        Integer offset = size * (currentPage - 1);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
//        添加页面元素
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalPage(totalPage);
        paginationDTO.setCurrentPage(currentPage);
        paginationDTO.setPagination(paginationDTO.getTotalPage(), paginationDTO.getCurrentPage());
//        查询问题所属用户信息
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            UserExample userExample = new UserExample();
            userExample.createCriteria()
                    .andAccountIdEqualTo(question.getCreator());
            List<User> users = userMapper.selectByExample(userExample);
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(users.get(0));
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    public QuestionDTO getById(Long id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        BeanUtils.copyProperties(question, questionDTO);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        questionDTO.setUser(users.get(0));
        List<String> tags = Arrays.stream(StringUtils.split(question.getTag(),",")).collect(Collectors.toList());
        questionDTO.setTags(tags);
        return questionDTO;
    }

    public void incViewCount(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incViewCount(question);
    }

    public void createOrUpdate(Question question, HttpServletRequest request) {
        Question checkQuestion = (Question) request.getSession().getAttribute("question");

        if (checkQuestion == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {
            question.setId(checkQuestion.getId());
            question.setGmtModified(System.currentTimeMillis());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria()
                    .andIdEqualTo(question.getId());
            int result = questionMapper.updateByExampleSelective(question, questionExample);
            if (result != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            request.getSession().removeAttribute("question");
        }
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO){
        if (StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(),",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexTag);
        List<Question> questions= questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO regexQuestionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, regexQuestionDTO);
            return regexQuestionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
