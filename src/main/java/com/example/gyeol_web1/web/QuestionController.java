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
        try {
            Question question = questionRepository.findById(id).get();
            hasPermission(session, question);
            model.addAttribute("question", question);
            return "/qna/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents,Model model, HttpSession session) {
        try {
            Question question = questionRepository.findById(id).get();
            hasPermission(session, question);
            question.update(title, contents);
            questionRepository.save(question);
            return String.format("redirect:/questions/%d", id);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
        try {
            Question question = questionRepository.findById(id).get();
            hasPermission(session, question);
            questionRepository.delete(question);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }
    // 중복된 에러메세지를 try/catch 문으로 클린코드화 한다.
    private boolean hasPermission(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        User loginUser = HttpSessionUtils.getUsersFromSession(session);
        if (!question.isSameWriter(loginUser)) {
            throw new IllegalStateException("자신이 쓴 게시물만 수정, 삭제가 가능합니다.");
        }
        return true;
    }
}
