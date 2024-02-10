package com.example.messagingapp.web;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.UserDoesntExistException;
import com.example.messagingapp.model.exceptions.UserNotLoggedInException;
import com.example.messagingapp.service.ChatService;
import com.example.messagingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class HomeController {
    private final UserService userService;
    private final ChatService chatService;

    public HomeController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @GetMapping({"/home"})
    public String getHomePage(Model model, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("user");
        User user;
        try {
            user = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        List<Chat> chatList = chatService.findChatsByUser(user);
        List<Chat> groupList = chatService.findGroupsByUser(user);
        List<Chat> invitesList = chatService.findGroupsWhereInvited(user);

        model.addAttribute("chats", chatList);
        model.addAttribute("friendRequests", user.getFriendRequests());
        model.addAttribute("groups", groupList);
        model.addAttribute("groupInvites", invitesList);

        return "home";
    }
}
