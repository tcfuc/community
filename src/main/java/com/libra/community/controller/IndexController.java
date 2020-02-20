package com.libra.community.controller;

import com.libra.community.dto.PaginationDTO;
import com.libra.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
@Controller
public class IndexController {

    private QuestionService questionService;

    @Autowired
    public void constructor (QuestionService questionService){
        this.questionService = questionService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "currentPage", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "2") Integer size,
                        Model model) {
//        返回问题列表
        PaginationDTO paginationDTO = questionService.list(page, size);
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }
}
