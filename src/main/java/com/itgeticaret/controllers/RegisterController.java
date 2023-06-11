package com.itgeticaret.controllers;

import com.itgeticaret.models.User;
import com.itgeticaret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registerform.html";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("user") User user) {
        try {
            userService.createUser(user);
            userService.sendActivationEmail(user.getEmail(), user.getActivationCode());
            return "redirect:/activation";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error"; // Hata durumunda yönlendirilecek sayfa
        }
    }







}

