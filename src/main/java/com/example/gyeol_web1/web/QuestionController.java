package com.example.gyeol_web1.web;

import com.example.gyeol_web1.domain.Question;
import com.example.gyeol_web1.domain.QuestionRepository;
import com.example.gyeol_web1.domain.Result;
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
        System.out.println("세션 확인 : " + sessionedUser);
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
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        model.addAttribute("question", question);
        return "/qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        question.update(title, contents);
        questionRepository.save(question);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        Question question = questionRepository.findById(id).get();
        Result result = valid(session, question);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        questionRepository.delete(question);
        return "redirect:/";

    }

    // hasPermission이 지저분하다고 판단되어 만드는 메소드. 클린코드2
    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User loginUser = HttpSessionUtils.getUsersFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            return Result.fail("자신이 쓴 게시물만 수정, 삭제가 가능합니다.");
        }
        return Result.ok();
    }
}
