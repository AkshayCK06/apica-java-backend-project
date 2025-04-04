package com.assignment.usermanagement.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.usermanagement.Entity.User;
import com.assignment.usermanagement.Kafka.KafkaProducerService;
import com.assignment.usermanagement.Kafka.UserEvent;
import com.assignment.usermanagement.Repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
   
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KafkaProducerService kafkaProducerService;
    
    public List<User> getAllUsers() {
       
        List<User> result = userRepository.findAll();
        log.info("Getting all the users::" );
        return result;
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        kafkaProducerService.sendUserEvent(new UserEvent(savedUser.getId(), savedUser.getUsername(),
                savedUser.getEmail(), savedUser.getRole(), "CREATED", null));
        return savedUser;
    }
    
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setRole(userDetails.getRole());
            User updatedUser = userRepository.save(user);
            kafkaProducerService.sendUserEvent(new UserEvent(updatedUser.getId(), updatedUser.getUsername(),
                    updatedUser.getEmail(), updatedUser.getRole(), "UPDATED", null));
            return updatedUser;
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.deleteById(id);
            kafkaProducerService.sendUserEvent(
                    new UserEvent(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), "DELETED", null));
        });
    }
}
