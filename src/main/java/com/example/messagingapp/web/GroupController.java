package com.example.messagingapp.web;

import com.example.messagingapp.model.Chat;
import com.example.messagingapp.model.User;
import com.example.messagingapp.model.exceptions.*;
import com.example.messagingapp.service.ChatService;
import com.example.messagingapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class GroupController {
    private final UserService userService;
    private final ChatService chatService;

    public GroupController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping({"/group/create"})
    public String createGroup(@RequestParam String groupName, HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        Chat group;
        try {
            group = chatService.createGroup(groupName, currentUser);
        } catch (InvalidArgumentsException exception) {
            model.addAttribute("error", exception.getMessage());
            return "home";
        }

        return "redirect:/home";
    }

    @GetMapping({"/invite/{id}"})
    public String getInvitePage(HttpServletRequest request, @PathVariable String id, @RequestParam(required = false) String usernameToInvite, Model model) {
        Chat chat = chatService.getChat(id);
        if(!chat.getGroup()) {
            model.addAttribute("error", "You can't invite people to personal chats. Create a group!");
            return "errorPage";
        }

        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        List<User> users;
        try {
            users = chatService.searchFriendsNotInChat(usernameToInvite, id, currentUser);
        } catch (InvalidArgumentsException | ChatDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "inviteToChat";
        }

        model.addAttribute("users", users);
        model.addAttribute("chatID", id);

        return "inviteToChat";
    }

    @PostMapping({"/invite/{id}"})
    public String inviteFriends(@RequestParam String friendUsername, Model model, HttpServletRequest request, @PathVariable String id) {
        try {
            chatService.checkIfGroup(id);
        } catch (InvalidArgumentsException | ChatDoesntExistException | NotAGroupException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        User user;
        try {
            user = userService.findUser(friendUsername);
        } catch (InvalidArgumentsException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        try {
            chatService.sendInvite(user, id);
        } catch (InvalidArgumentsException | ChatDoesntExistException | UserAlreadyInvitedException | UserAlreadyInChatException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        return "redirect:/invite/" + id;
    }

    @PostMapping({"/group/accept/{id}"})
    public String acceptInviteToGroup(@PathVariable String id, HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        try {
            chatService.acceptGroupRequest(currentUser, id);
        } catch (InvalidArgumentsException | ChatDoesntExistException | NotAGroupException | UserNotInvitedException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        return "redirect:/chat/" + id;
    }

    @PostMapping({"/group/decline/{id}"})
    public String declineInviteToGroup(@PathVariable String id, HttpServletRequest request, Model model) {
        String username = (String) request.getSession().getAttribute("user");
        User currentUser;
        try {
            currentUser = userService.findCurrentUser(username);
        } catch (UserNotLoggedInException | UserDoesntExistException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }

        try {
            chatService.declineGroupRequest(currentUser, id);
        } catch (InvalidArgumentsException | ChatDoesntExistException | NotAGroupException | UserNotInvitedException exception) {
            model.addAttribute("error", exception.getMessage());
            return "errorPage";
        }
        return "redirect:/home";
    }
}
