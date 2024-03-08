package com.example.messagingapp.service.impl;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.Message;
import com.example.messagingapp.model.exceptions.InvalidArgumentsException;
import com.example.messagingapp.repository.ChatRepository;
import com.example.messagingapp.repository.MessageRepository;
import com.example.messagingapp.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    public MessageServiceImpl(MessageRepository messageRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public void saveMessageInChat(Message message, Chat chat) {
        if(message.getBody() == null || message.getBody().isEmpty()) {
            throw new InvalidArgumentsException();
        }

        messageRepository.save(message);
        chat.getMessages().add(message);
        chatRepository.save(chat);
    }
}
