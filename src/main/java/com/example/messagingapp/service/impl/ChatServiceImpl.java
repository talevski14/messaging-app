package com.example.messagingapp.service.impl;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.Message;
import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.ChatDoesntExistException;
import com.example.messagingapp.repository.ChatRepository;
import com.example.messagingapp.repository.MessageRepository;
import com.example.messagingapp.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    public ChatServiceImpl(ChatRepository chatRepository, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Chat> findChatsByUser(User user) {
        return chatRepository.findAllByUser1OrUser2(user, user);
    }

    @Override
    public Chat getChat(String id) {
        return chatRepository.findById(id).orElseThrow(ChatDoesntExistException::new);
    }

    @Override
    public void saveMessageInChat(Message message, String id) {
        messageRepository.save(message);
        Chat chat = this.getChat(id);
        chat.getMessages().add(message);
        chatRepository.save(chat);
    }
}
