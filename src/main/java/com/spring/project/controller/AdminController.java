package com.spring.project.controller;

import com.spring.project.entity.User;
import com.spring.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user.getUsername().isBlank()) {
            return new ResponseEntity<>("Username cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        userService.saveNewAdminUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
