package com.example.messagingapp.web;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.exceptions.ChatDoesntExistException;
import com.example.messagingapp.service.ChatService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping({"/chat/{id}"})
    public String getChatPage(@PathVariable String id, Model model, HttpServletRequest request) {
        Chat chat = null;
        try {
            chat = chatService.getChat(id);
        } catch (ChatDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        model.addAttribute("messagesChat", chat.getMessages());
        model.addAttribute("chatID", id);
        return "chat";
    }
}
