package com.spring.project.controller;

import com.spring.project.entity.User;
import com.spring.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user.getUsername().isBlank()) {
            return new ResponseEntity<String>("Username cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        userService.saveNewUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}
