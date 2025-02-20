package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Model model, @AuthenticationPrincipal User authenticatedUser) {
        model.addAttribute("users", userService.getAllUsersAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("user", userService.getUserById(authenticatedUser.getId()));
        return "admin/user-list";
    }

    @GetMapping("/user-create")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/user-create";
    }

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @GetMapping("/edit_user/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "redirect:/admin";
    }

    @PatchMapping("/edit_user/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute User user, @RequestParam(required = false) List<Long> roles) {
        user.setId(id);
        if (roles == null || roles.isEmpty()) {
            user.setRoles(null);
        } else {
            user.setRoles(new HashSet<>(roleService.getRolesById(roles)));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }



    @DeleteMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
