package com.libra.community.controller;

import com.libra.community.exception.CustomizeErrorCode;
import com.libra.community.exception.CustomizeException;
import com.libra.community.model.Question;
import com.libra.community.model.User;
import com.libra.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhou
 * @date 2020/2/2
 * @time 13:41
 */
@Controller
public class PublishController {

    private QuestionService questionService;

    @Autowired
    public void constructor(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@Param("title") String title,
                            @Param("description") String description,
                            @Param("tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
//        空值判断
        if (title == null || title.equals("")) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description.equals("")) {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag.equals("")) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
//        判断用户状态
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
//        插入或更新问题
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getAccountId());
        questionService.createOrUpdate(question, request);
        return "redirect:/";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Long id,
                       HttpServletRequest request,
                       Model model) {
        List<Question> questions = questionService.listById(id);
        if (questions.size() == 0) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        Question question = questions.get(0);
        User user = (User) request.getSession().getAttribute("user");
        if (user.getAccountId() == question.getCreator()) {
            request.getSession().setAttribute("question", question);
            model.addAttribute("title", question.getTitle());
            model.addAttribute("description", question.getDescription());
            model.addAttribute("tag", question.getTag());
            return "publish";
        } else {
            return "redirect:/";
        }
    }
}
