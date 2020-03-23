package com.libra.community.controller;

import com.libra.community.dto.PaginationDTO;
import com.libra.community.model.User;
import com.libra.community.service.NotificationService;
import com.libra.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller
public class IndexController {

    private QuestionService questionService;

    private NotificationService notificationService;

    @Autowired
    public void constructor(QuestionService questionService, NotificationService notificationService) {
        this.questionService = questionService;
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "currentPage", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "7") Integer size,
                        HttpServletRequest request,
                        Model model) {
//        返回问题列表
        PaginationDTO paginationDTO = questionService.list(page, size);
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            request.getSession().setAttribute("totalNotification", notificationService.countNotification(user.getAccountId()));
        }
        model.addAttribute("paginationDTO", paginationDTO);
        return "index";
    }
}
