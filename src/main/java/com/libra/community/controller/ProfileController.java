package com.libra.community.controller;

import com.libra.community.dto.PaginationDTO;
import com.libra.community.model.User;
import com.libra.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhou
 * @date 2020/2/12
 * @time 18:54
 */
@Controller
public class ProfileController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable String action,
                          @RequestParam(name = "currentPage", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "2") Integer size,
                          HttpServletRequest request,
                          Model model){
        if ("questions".equals(action)){
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的问题");
        } else if ("replies".equals(action)){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        User user = (User) request.getSession().getAttribute("user");
        PaginationDTO paginationDTO = questionService.list(page, size, user.getAccountId());
        model.addAttribute("paginationDTO", paginationDTO);
        return "profile";
    }
}
