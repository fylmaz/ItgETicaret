package com.itgeticaret.controllers;

import com.itgeticaret.models.User;
import com.itgeticaret.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/activation")
public class ActivationController {

    private final UserService userService;
    @Autowired
    public ActivationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showActivationForm(Model model) {
        model.addAttribute("activationCode", "");
        return "activationCode.html";
    }

    /*@PostMapping
    public ResponseEntity<Object> activateAccount(@RequestParam("activationCode") String activationCode) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();
        User user = userService.getUserByEmail(email);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        boolean isValid = userService.validateActivationCode(activationCode, user);
        if (isValid) {
            userService.activateUser(user);
            return new ResponseEntity<>("Your account has been activated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid activation code", HttpStatus.BAD_REQUEST);
        }
    }*/
    @PostMapping
    public String activateAccount(@RequestParam("activationCode") String activationCode, Model model) {
        User user = userService.getUserByActivationCode(activationCode);
        if (user == null) {
            model.addAttribute("errorMessage", "Invalid activation code");
            return "activationError";
        }

        userService.activateUser(user);
        model.addAttribute("successMessage", "Your account has been activated successfully");
        return "mainPage.html";
    }




}
