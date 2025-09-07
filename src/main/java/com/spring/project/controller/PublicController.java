package com.spring.project.controller;

import com.spring.project.dto.NetworkResponse;
import com.spring.project.dto.NetworkResponseType;
import com.spring.project.dto.UserDto;
import com.spring.project.service.UserService;
import com.spring.project.utils.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Public APIs")
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
    public ResponseEntity<?> signUp(@RequestBody UserDto user) {
        if (user.getUsername().isBlank()) {
            return new ResponseEntity<>("Username cannot be empty!", HttpStatus.BAD_REQUEST);
        }
        if (user.isSentimentAnalysis() && (user.getEmail() == null || user.getEmail().isBlank())) {
            return new ResponseEntity<>("Sentiment analysis enable should provide valid email", HttpStatus.BAD_REQUEST);
        }
        NetworkResponse response = userService.saveNewUser(user.toUserEntity());
        if (response.getResponseType() == NetworkResponseType.SUCCESS) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto user) {
        try {
            if (user.getUsername().isBlank()) {
                return new ResponseEntity<>("Username cannot be empty!", HttpStatus.BAD_REQUEST);
            }
            if (user.getPassword().isBlank()) {
                return new ResponseEntity<>("Password cannot be empty!", HttpStatus.BAD_REQUEST);
            }
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
