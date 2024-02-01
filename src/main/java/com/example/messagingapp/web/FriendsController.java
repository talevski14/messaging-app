package com.example.messagingapp.web;

import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.InvalidArgumentsException;
import com.example.messagingapp.model.exceptions.UserDoesntExistException;
import com.example.messagingapp.model.exceptions.UserNotLoggedInException;
import com.example.messagingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FriendsController {
    private final UserService userService;

    public FriendsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/find-friends"})
    public String getFindFriendsPage(Model model, HttpServletRequest request, @RequestParam(required = false) String username) {
        List<User> users = null;

        String usernameCurr = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(usernameCurr);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        try {
            users = userService.findAllUsersById(username, currentUser);
        } catch (InvalidArgumentsException exception) {
            model.addAttribute("error", exception.getMessage());
            return "findFriends";
        }

        model.addAttribute("users", users);
        return "findFriends";
    }

    @PostMapping({"/send-request/{id}"})
    public String sendFriendRequest(Model model, HttpServletRequest request, @PathVariable String id) {
        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        User user;
        try {
            user = userService.findUser(id);
        } catch (InvalidArgumentsException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        userService.sendFriendRequest(currentUser, user);
        return "redirect:/home";
    }

    @PostMapping({"/decline/{id}"})
    public String declineRequest(@PathVariable String id, HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        userService.declineRequest(currentUser, id);
        return "redirect:/home";
    }

    @PostMapping({"/accept/{id}"})
    public String acceptRequest(@PathVariable String id, HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        userService.acceptRequest(currentUser, id);
        return "redirect:/home";
    }
}
