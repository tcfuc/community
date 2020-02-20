package com.libra.community.advice;

import com.libra.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhou
 * @date 2020/2/19
 * @time 16:21
 */
@ControllerAdvice
public class CustomizeExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        if ( e instanceof CustomizeException){
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", "服务器炸了！");
        }
        return new ModelAndView("error");
    }

}
