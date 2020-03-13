package com.libra.community.controller;

import com.libra.community.provider.CorrectDataBaseProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhou
 * @date 2020/3/5
 * @time 10:19
 */
@Controller
public class CorrectController {

    private CorrectDataBaseProvider correctDataBaseProvider;

    @Autowired
    public void constructor (CorrectDataBaseProvider correctDataBaseProvider){
        this.correctDataBaseProvider = correctDataBaseProvider;
    }

    @RequestMapping(value = "/correct")
    public String correct(){
        correctDataBaseProvider.correct();
        return "correct";
    }

    @RequestMapping(value = "/test")
    public String test(){
        correctDataBaseProvider.correct();
        return "test";
    }
}
