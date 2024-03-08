package com.example.messagingapp.web;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.*;
import com.example.messagingapp.service.ChatService;
import com.example.messagingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping({"/chat/{id}"})
    public String getChatPage(@PathVariable String id, Model model, HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        Chat chat = null;
        try {
            chat = chatService.getChatByUser(id, currentUser);
        } catch (InvalidArgumentsException | ChatDoesntExistException | UserNotInChatException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        model.addAttribute("messagesChat", chat.getMessages());
        model.addAttribute("chatID", id);
        model.addAttribute("isGroup", chat.getGroup());

        return "chat";
    }
}
