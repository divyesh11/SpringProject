package com.spring.project.service;

import com.spring.project.entity.User;
import com.spring.project.notification.interfaces.Notification;
import com.spring.project.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public void saveUser(User user) {
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
