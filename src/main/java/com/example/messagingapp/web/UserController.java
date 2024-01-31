package com.example.messagingapp.web;

import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.*;
import com.example.messagingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/login"})
    public String getLogInPage() {
        return "login";
    }

    @GetMapping({"/signup"})
    public String getSignUpPage() {
        return "signup";
    }

    @PostMapping({"/login"})
    public String logIn(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpServletRequest request) {
        User user;
        try {
            user = userService.logIn(username, password);
        } catch (InvalidArgumentsException | UserDoesntExistException | WrongPasswordException exception) {
            model.addAttribute("error", exception.getMessage());
            return "login";
        }

        request.getSession().setAttribute("user", user.getUsername());
        return "redirect:/home";
    }

    @PostMapping({"/signup"})
    public String signUp(@RequestParam String fullName,
                         @RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String repeatPassword,
                         Model model) {
        User user;
        try {
            user = userService.createUser(fullName, username, password, repeatPassword);
        } catch (InvalidArgumentsException | PasswordsDontMatchException | UserExistsException exception) {
            model.addAttribute("error", exception.getMessage());
            return "signup";
        }
        return "redirect:/login";
    }
}
