package com.example.messagingapp.web;


import com.example.messagingapp.model.*;
import com.example.messagingapp.model.exceptions.*;
import com.example.messagingapp.service.ChatService;
import com.example.messagingapp.service.MessageService;
import com.example.messagingapp.service.UserService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class MessageController {
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;

    public MessageController(UserService userService, ChatService chatService, MessageService messageService) {
        this.userService = userService;
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @MessageMapping("/send-message/{id}")
    @SendTo("/chat/{id}")
    public ClientMessage webSocketMessage(ServerMessage serverMessage, @DestinationVariable String id) {
        return new ClientMessage(serverMessage.getSender(), serverMessage.getBody(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    @PostMapping({"/chat/{id}"})
    public String saveMessage(@RequestBody ServerMessage message, @PathVariable String id, Model model) {
        User user = null;

        try {
            user = userService.findCurrentUser(message.getSender());
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            return exception.getMessage();
        }

        Message messageObj = new Message(message.getBody(), LocalDateTime.now(), user);
        Chat chat = null;

        try {
            chat = chatService.getChatByUser(id, user);
        } catch (InvalidArgumentsException | ChatDoesntExistException | UserNotInChatException exception) {
            return exception.getMessage();
        }

        try {
            messageService.saveMessageInChat(messageObj, chat);
        } catch (InvalidArgumentsException | ChatDoesntExistException exception) {
            return exception.getMessage();
        }

        return "";
    }
}
