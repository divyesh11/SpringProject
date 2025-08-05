package com.spring.project.controller;

import com.spring.project.entity.User;
import com.spring.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
//        userService.sendNotification(new EmailNotification("My Email"));
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUserById(@RequestHeader Map<String, ?> queryParams) {
        User user = null;
        if (queryParams.get("username") != null) {
            user = userService.getUserByUsername(queryParams.get("username").toString());
        } else if (queryParams.get("id") != null) {
            Optional<User> userData = userService.getUserByUserId(queryParams.get("id").toString());
            if (userData.isPresent()) {
                user = userData.get();
            }
        } else {
            return new ResponseEntity<String>("Username or UserId should be passed as query params", HttpStatus.BAD_REQUEST);
        }
        if (user == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User updatedUserDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User oldUser = userService.getUserByUsername(authentication.getName());
        oldUser.setUsername(updatedUserDetails.getUsername());
        oldUser.setPassword(updatedUserDetails.getPassword());
        userService.saveNewUser(oldUser);
        return new ResponseEntity<>(oldUser, HttpStatus.OK);
    }
}
