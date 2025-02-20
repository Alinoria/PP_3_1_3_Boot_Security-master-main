package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
private final UserService userService;

@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUserProfile(Principal principal, Model model) {
    String username = principal.getName();

    model.addAttribute("user", userService.getUserByUsername(username));
    model.addAttribute("role", userService.getUserByUsername(username).getRoles());
    return "user/profile";
    }
}
