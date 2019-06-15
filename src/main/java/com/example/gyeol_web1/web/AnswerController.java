package com.example.gyeol_web1.web;

import com.example.gyeol_web1.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUsersFromSession(session);
        Question question = questionRepository.findById(questionId).get();
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }



}
