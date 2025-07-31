package com.spring.project.controller;

import com.spring.project.EmailNotification;
import com.spring.project.entity.User;
import com.spring.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        userService.sendNotification(new EmailNotification("My Email"));
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getUserById(@RequestParam Map<String, ?> queryParams) {
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user.getUsername().isBlank()) {
            return new ResponseEntity<String>("Username cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
