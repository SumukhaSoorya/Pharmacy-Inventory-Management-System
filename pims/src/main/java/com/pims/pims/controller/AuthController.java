package com.pims.pims.controller;

import com.pims.pims.model.User;
import com.pims.pims.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // LOGIN PAGE
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "success", required = false) String success, Model model) {
        if (success != null) {
            model.addAttribute("message", "Registration Successful! Please login.");
        }
        return "login";
    }

    // REGISTER PAGE
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // REGISTER USER
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/login?success=true";
    }

    // DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}