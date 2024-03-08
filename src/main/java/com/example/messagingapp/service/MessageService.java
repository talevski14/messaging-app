package com.example.messagingapp.service;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.Message;

public interface MessageService {
    public void saveMessageInChat(Message message, Chat chat);
}
