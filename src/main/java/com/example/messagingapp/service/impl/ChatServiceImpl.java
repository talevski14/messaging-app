package com.example.messagingapp.service.impl;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.Message;
import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.*;
import com.example.messagingapp.repository.ChatRepository;
import com.example.messagingapp.repository.MessageRepository;
import com.example.messagingapp.repository.UserRepository;
import com.example.messagingapp.service.ChatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ChatServiceImpl(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Chat createGroup(String groupName, User currentUser) {
        if (groupName == null || groupName.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        Chat chat = new Chat(currentUser, groupName);
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> findChatsByUser(User user) {
        return chatRepository.findAllByMembersContaining(user).stream()
                .filter(i -> !i.getGroup())
                .toList();
    }

    @Override
    public Chat getChat(String id) {
        if(id == null || id.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return chatRepository.findById(id).orElseThrow(ChatDoesntExistException::new);
    }

    @Override
    public List<Chat> findGroupsByUser(User user) {
        return chatRepository.findAllByMembersContaining(user).stream()
                .filter(Chat::getGroup)
                .toList();
    }

    @Override
    public void sendInvite(User user, String id) {
        Chat chat = this.getChat(id);
        if (chat.getInvitedMembers().contains(user)) {
            throw new UserAlreadyInvitedException();
        }
        if (chat.getMembers().contains(user)) {
            throw new UserAlreadyInChatException();
        }

        chat.getInvitedMembers().add(user);
        chatRepository.save(chat);
    }

    @Override
    public List<User> searchFriendsNotInChat(String usernameToInvite, String id, User currUser) {
        Chat chat = this.getChat(id);
        List<User> matchingUsernames = userRepository.findAllByUsernameContainsIgnoreCase(usernameToInvite);
        List<User> chatMembers = chat.getMembers();
        List<User> currUserFriends = currUser.getFriends();
        return matchingUsernames.stream()
                .filter(i -> !chatMembers.contains(i))
                .filter(currUserFriends::contains)
                .toList();
    }

    @Override
    public List<Chat> findGroupsWhereInvited(User user) {
        return chatRepository.findAllByInvitedMembersContaining(user);
    }

    @Override
    public void acceptGroupRequest(User currentUser, String id) {
        Chat chat = this.getChat(id);

        if(!chat.getGroup()) {
            throw new NotAGroupException();
        }

        if(!chat.getInvitedMembers().contains(currentUser)) {
            throw new UserNotInvitedException();
        }

        chat.getInvitedMembers().remove(currentUser);
        chat.getMembers().add(currentUser);
        chatRepository.save(chat);
    }

    @Override
    public void declineGroupRequest(User currentUser, String id) {
        Chat chat = this.getChat(id);

        if(!chat.getGroup()) {
            throw new NotAGroupException();
        }

        if(!chat.getInvitedMembers().contains(currentUser)) {
            throw new UserNotInvitedException();
        }

        chat.getInvitedMembers().remove(currentUser);
        chatRepository.save(chat);
    }

    @Override
    public Chat getChatByUser(String id, User user) {
        Chat chat = this.getChat(id);
        if(!chat.getMembers().contains(user)) {
            throw new UserNotInChatException();
        }
        return chat;
    }

    @Override
    public void checkIfGroup(String id) {
        Chat chat = this.getChat(id);
        if(!chat.getGroup()) {
            throw new NotAGroupException();
        }
    }
}
