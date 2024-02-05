package com.example.messagingapp.web;


import com.example.messagingapp.model.ClientMessage;
import com.example.messagingapp.model.Message;
import com.example.messagingapp.model.ServerMessage;
import com.example.messagingapp.model.User;
import com.example.messagingapp.service.ChatService;
import com.example.messagingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class MessageController {
    private final UserService userService;
    private final ChatService chatService;

    public MessageController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @MessageMapping("/send-message/{id}")
    @SendTo("/chat/{id}")
    public ClientMessage webSocketMessage(ServerMessage serverMessage, @DestinationVariable String id) {
        return new ClientMessage(serverMessage.getSender(), serverMessage.getBody(), LocalDateTime.now().toString());
    }

    @PostMapping({"/chat/{id}"})
    public void saveMessage(@RequestBody ServerMessage message, @PathVariable String id) {
        User user = userService.findCurrentUser(message.getSender());
        Message messageObj = new Message(message.getBody(), LocalDateTime.now(), user);
        chatService.saveMessageInChat(messageObj, id);
    }
}
