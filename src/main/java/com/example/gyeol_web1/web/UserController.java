package com.example.gyeol_web1.web;

import com.example.gyeol_web1.domain.User;
import com.example.gyeol_web1.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 로그인 화면 으로 가기
    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    // 로그인
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        // user Table 에 입력한 email 이 있는지 조회
        User user = userRepository.findByUserId(userId);

        // 입력 값이 없을 경우 로그인 실패
        if (user == null) {
            System.out.println("Login Failed..");
            return "redirect:/users/loginForm";
        }

        // 패스워드가 다를 경우 로그인 실패
        if (!user.matchPassword(password)) {
            System.out.println("Login Failed..");
            return "redirect:/users/loginForm";
        }
        System.out.println("Login Success!");

        // 세션에 입력값 저장
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY,user);

        System.out.println("로그인시 세션 저장 : "+ session);
        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    // 회원가입 화면 이동
    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    // 회원가입
    @PostMapping("")
    public String create(User user) {
        System.out.println("user : " + user);
        // 입력한 값으로 포스트 처리 하여 회원가입
        userRepository.save(user);
        return "redirect:/";
    }

    // 회원 리스트 불러오기
    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }
    // 회원 정보 수정 페이지로 가기
    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUsersFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalArgumentException("You can't update the anther user");
        }

        User user = userRepository.findById(id).get();
        System.out.println(user);
        //User user = userRepository.findOne(id);
        model.addAttribute("users", user);
        return "/user/updateForm";
    }
    // 회원정보 수정
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        if (!HttpSessionUtils.isLoginUsers(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUsersFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalArgumentException("You can't update the anther user");
        }

        User user = userRepository.findById(id).get();
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
