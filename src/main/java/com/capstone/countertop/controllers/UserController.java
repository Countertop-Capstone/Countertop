package com.capstone.countertop.controllers;

import com.capstone.countertop.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.capstone.countertop.models.User;
import com.capstone.countertop.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    //CHANGES
    @GetMapping("users/{id}")
    public String userProfilePage(@PathVariable long id, Model model){
        model.addAttribute("user", userDao.getOne(id));
        return "/users/profiles";
    }

    @GetMapping("profile")
    public String profilePages(Model model){
        String username = "username";
        model.addAttribute("username", username);
        return "/users/myprofile";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        userDao.save(user);
        model.addAttribute("user", userDao.findAll());
        return "redirect:/profile";
    }
    //CHANGES END

    @GetMapping("/users/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "/register";
    }

    @PostMapping("users/register")
    public String registerUser(@ModelAttribute User user) {
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }
}
