package com.example.gyeol_web1.web;

import com.example.gyeol_web1.domain.Question;
import com.example.gyeol_web1.domain.QuestionRepository;
import com.example.gyeol_web1.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUsersFromSession(session);
        System.out.println("세션 확인 : " +sessionedUser);
        Question newQuestion = new Question(sessionedUser, title, contents);
        questionRepository.save(newQuestion);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());
        return "/qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUsersFromSession(session);
        Question question = questionRepository.findById(id).get();

        if (!question.isSameWriter(loginUser)) {
            return "/users/loginForm";
        }

        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUsersFromSession(session);
        Question question = questionRepository.findById(id).get();

        if (!question.isSameWriter(loginUser)) {
            return "/users/loginForm";
        }

        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "/users/loginForm";
        }

        User loginUser = HttpSessionUtils.getUsersFromSession(session);
        Question question = questionRepository.findById(id).get();

        if (!question.isSameWriter(loginUser)) {
            return "/users/loginForm";
        }
        questionRepository.delete(question);
        return "redirect:/";
    }
}
