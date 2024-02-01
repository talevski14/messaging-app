package com.example.messagingapp.web;

import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.InvalidArgumentsException;
import com.example.messagingapp.model.exceptions.UserDoesntExistException;
import com.example.messagingapp.model.exceptions.UserNotLoggedInException;
import com.example.messagingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/home"})
    public String getHomePage(Model model, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("user");
        User user;
        try {
            user = userService.findUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        model.addAttribute("friends", user.getFriends());
        model.addAttribute("friendRequests", user.getFriendRequests());

        return "home";
    }
}
