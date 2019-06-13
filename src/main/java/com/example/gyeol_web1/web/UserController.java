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

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            System.out.println("Login Failed..");
            return "redirect:/users/loginForm";
        }
        if (!password.equals(user.getPassword())) {
            System.out.println("Login Failed..");
            return "redirect:/users/loginForm";
        }
        System.out.println("Login Success!");
        session.setAttribute("sessionedUsers",user);
        System.out.println(user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUsers");
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "/user/form";
    }

    @PostMapping("")
    public String create(User user) {
        System.out.println("user : " + user);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        Object tempUsers = session.getAttribute("sessionedUsers");
        if (tempUsers == null) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = (User)tempUsers;
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalArgumentException("You can't update the anther user");
        }

        User user = userRepository.findById(id).get();
        System.out.println(user);
        //User user = userRepository.findOne(id);
        model.addAttribute("users", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        Object tempUsers = session.getAttribute("sessionedUsers");
        if (tempUsers == null) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = (User)tempUsers;
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalArgumentException("You can't update the anther user");
        }

        User user = userRepository.findById(id).get();
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
