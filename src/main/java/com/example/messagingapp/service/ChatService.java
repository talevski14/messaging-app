package com.example.messagingapp.service;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.Message;
import com.example.messagingapp.model.User;

import java.util.List;

public interface ChatService {
    List<Chat> findChatsByUser(User user);
    Chat getChat(String id);
    void saveMessageInChat(Message message, String id);
}
