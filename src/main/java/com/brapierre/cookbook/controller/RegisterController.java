package com.brapierre.cookbook.controller;

import com.brapierre.cookbook.entity.User;
import com.brapierre.cookbook.service.UserRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UserRegistrationService authService;

    public RegisterController(UserRegistrationService authService) {
        this.authService = authService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        authService.register(user); // must encode password
        return "redirect:/login";
    }
}
