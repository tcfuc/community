package com.libra.community.service;

import com.libra.community.dto.PaginationDTO;
import com.libra.community.dto.QuestionDTO;
import com.libra.community.mapper.QuestionMapper;
import com.libra.community.mapper.UserMapper;
import com.libra.community.model.Question;
import com.libra.community.model.QuestionExample;
import com.libra.community.model.User;
import com.libra.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin2.main.server.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhou
 * @date 2020/2/5
 * @time 13:17
 */
@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public PaginationDTO list(Integer currentPage, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        PaginationDTO paginationDTO = page(questionExample, currentPage, size);
        return paginationDTO;
    }

    public PaginationDTO listByAccountId(Integer currentPage, Integer size, String accountId) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(accountId);
        PaginationDTO paginationDTO = page(questionExample, currentPage, size);
        return paginationDTO;
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

    public QuestionDTO getById(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.selectByPrimaryKey(id);
        BeanUtils.copyProperties(question, questionDTO);
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(question.getCreator());
        List<User> users = userMapper.selectByExample(userExample);
        questionDTO.setUser(users.get(0));
        return questionDTO;
    }

}
