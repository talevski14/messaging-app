package com.example.messagingapp.repository;

import com.example.messagingapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByUsernameContainingIgnoreCaseAndFriendsNotContainsAndFriendRequestsNotContains(String username, User user1, User user2);
}
