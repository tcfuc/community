package com.libra.community.advice;

import com.libra.community.dto.ResultDTO;
import com.libra.community.exception.CustomizeErrorCode;
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
    Object handle(HttpServletRequest request,
                        Throwable e,
                        Model model) {
        String contentType = request.getContentType();

//        if ("application/json".equals(contentType)) {
//            // 返回JSON
//            if (e instanceof CustomizeException) {
//                return ResultDTO.errorOf((CustomizeException)e);
//            } else {
//                return ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
//            }
//        } else {
//            // 错误页面跳转
//            if (e instanceof CustomizeException) {
//                model.addAttribute("message", e.getMessage());
//            } else {
//                model.addAttribute("message", "服务器炸了！");
//            }
//            return new ModelAndView("error");
//        }

        if (e instanceof CustomizeException) {
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", "服务器炸了！");
        }
        return new ModelAndView("error");
    }
}
