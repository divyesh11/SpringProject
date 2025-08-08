package com.spring.project.controller;

import com.spring.project.api.response.WeatherResponse;
import com.spring.project.entity.User;
import com.spring.project.service.UserService;
import com.spring.project.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final WeatherService weatherService;

    @Autowired
    UserController(UserService userService, WeatherService weatherService) {
        this.userService = userService;
        this.weatherService = weatherService;
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

    @GetMapping("/greetings")
    public ResponseEntity<?> greetings() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String weatherDescription = "";
        if (weatherResponse != null) {
            weatherDescription = "Weather feels like : " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hello, " + username + " " + weatherDescription, HttpStatus.OK);
    }
}
