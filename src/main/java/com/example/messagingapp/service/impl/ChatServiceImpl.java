package com.example.messagingapp.service.impl;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.User;
import com.example.messagingapp.repository.ChatRepository;
import com.example.messagingapp.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public List<Chat> findChatsByUser(User user) {
        return chatRepository.findAllByUser1OrUser2(user, user);
    }
}
