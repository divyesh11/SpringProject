package com.spring.project.service;

import com.spring.project.dto.NetworkResponse;
import com.spring.project.dto.NetworkResponseType;
import com.spring.project.entity.User;
import com.spring.project.notification.interfaces.Notification;
import com.spring.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public NetworkResponse saveNewUser(User user) {
        try {
            if (user.getUsername().isBlank())
                return new NetworkResponse(NetworkResponseType.FAILURE, "Username is blank");
            if (user.getPassword().isBlank())
                return new NetworkResponse(NetworkResponseType.FAILURE, "Password is blank");
            if (userRepository.findByUsername(user.getUsername()) != null) {
                return new NetworkResponse(NetworkResponseType.FAILURE, "User already exits");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
            return new NetworkResponse(NetworkResponseType.SUCCESS, "");
        } catch (Exception e) {
            log.debug("Error creating user : ", e);
            return new NetworkResponse(NetworkResponseType.FAILURE, e.getMessage());
        }
    }

    public void saveNewAdminUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByUserId(String id) {
        return userRepository.findById(new ObjectId(id));
    }

    public void sendNotification(Notification notification) {
        notification.notifyMe();
    }
}
