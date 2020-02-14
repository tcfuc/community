package com.libra.community.service;

import com.libra.community.dto.PaginationDTO;
import com.libra.community.dto.QuestionDTO;
import com.libra.community.mapper.QuestionMapper;
import com.libra.community.mapper.UserMapper;
import com.libra.community.model.Question;
import com.libra.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//        查询问题
        Integer totalCount = questionMapper.count();
        Integer offset = size * (currentPage - 1);
        List<Question> questions = questionMapper.list(offset, size);

        PaginationDTO paginationDTO = page(currentPage, size, totalCount, questions);
        return paginationDTO;
    }
    public PaginationDTO list(Integer currentPage, Integer size, String accountId) {
//        查询问题
        Integer totalCount = questionMapper.countByAccountId(accountId);
        Integer offset = size * (currentPage - 1);
        List<Question> questions = questionMapper.listByAccountId(offset, size, accountId);

        PaginationDTO paginationDTO = page(currentPage, size, totalCount, questions);
        return paginationDTO;
    }

    public PaginationDTO page(Integer currentPage, Integer size,Integer totalCount, List<Question> questions){
        Integer totalPage;
//        计算问题页数
        totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
//        对当前页进行范围判断
        currentPage = currentPage < 1 ? 1 : currentPage;
        currentPage = currentPage > totalPage ? totalPage : currentPage;
//        查询问题
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setTotalPage(totalPage);
        paginationDTO.setCurrentPage(currentPage);
        paginationDTO.setPagination(paginationDTO.getTotalPage(), paginationDTO.getCurrentPage());

        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }
}
