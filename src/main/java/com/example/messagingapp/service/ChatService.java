package com.example.messagingapp.service;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.Message;
import com.example.messagingapp.model.User;

import java.util.List;

public interface ChatService {
    Chat createGroup(String groupName, User currentUser);
    List<Chat> findChatsByUser(User user);
    Chat getChat(String id);
    void saveMessageInChat(Message message, String id);

    List<Chat> findGroupsByUser(User user);

    List<User> getFriendsNotInChat(User curentUser, String chatId);

    void sendInvite(User user, String id);

    List<User> searchFriendsNotInChat(String usernameToInvite, String id, User currUser);

    List<Chat> findGroupsWhereInvited(User user);

    void acceptGroupRequest(User currentUser, String id);

    void declineGroupRequest(User currentUser, String id);

    Chat getChatByUser(String id, User user);

}
