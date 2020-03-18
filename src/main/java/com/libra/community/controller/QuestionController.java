package com.libra.community.controller;

import com.libra.community.dto.CommentDTO;
import com.libra.community.dto.QuestionDTO;
import com.libra.community.enums.CommentTypeEnum;
import com.libra.community.service.CommentService;
import com.libra.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhou
 * @date 2020/2/14
 * @time 18:40
 */
@Controller
public class QuestionController {

    private QuestionService questionService;

    private CommentService commentService;

    @Autowired
    public void constructor(QuestionService questionService, CommentService commentService) {
        this.questionService = questionService;
        this.commentService = commentService;
    }

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        questionService.incViewCount(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOS);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }

    @RequestMapping(value = "/question/comment/{id}", method = RequestMethod.GET)
    public String questionComment(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", commentDTOS);
        return "question::comments";
    }
}
