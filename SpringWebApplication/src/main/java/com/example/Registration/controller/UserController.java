package com.example.Registration.controller;

import com.example.Registration.service.UserService;
import com.example.Registration.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Загрузка страницы регистрации");
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        logger.info("Обработка регистрации пользователя: {}", user.getUsername());
        try {
            if (userService.ifUserExist(user.getUsername())) {
                logger.warn("Пользователь {} уже существует", user.getUsername());
                model.addAttribute("error", true);
                return "redirect:/register?error";
            }

            userService.registerUser(user.getUsername(), user.getPassword(), "ROLE_USER");
            model.addAttribute("success", true);
            logger.info("Пользователь {} успешно зарегистрирован", user.getUsername());
            return "redirect:/register?success";

        } catch (Exception e) {
            logger.error("Ошибка регистрации пользователя: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "redirect:/register?error";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        logger.info("Загрузка страницы входа");
        return "login";
    }
}
