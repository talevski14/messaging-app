package com.example.messagingapp.service;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.User;

import java.util.List;

public interface ChatService {
    List<Chat> findChatsByUser(User user);
}
