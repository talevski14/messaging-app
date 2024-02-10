package com.example.messagingapp.repository;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {
    List<Chat> findAllByMembersContaining(User user);
    List<Chat> findAllByInvitedMembersContaining(User user);
}
