package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.entity.User;
import com.luv2code.ResturantSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("This email Already Registerd");
        }
        user.setCreated_at(LocalDateTime.now());
        user.setUpdated_at(LocalDateTime.now());
        User savedUser =  userRepository.save(user);

        return savedUser;
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
