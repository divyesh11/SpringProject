package com.spring.project.controller;

import com.spring.project.entity.User;
import com.spring.project.service.UserService;
import com.spring.project.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        if (user.getUsername().isBlank()) {
            return new ResponseEntity<String>("Username cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        userService.saveNewUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while generating token : {}", e.getMessage(), e);
            return new ResponseEntity<>("User not found! Reason : " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
