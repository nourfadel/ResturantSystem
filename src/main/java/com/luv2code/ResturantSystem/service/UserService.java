package com.luv2code.ResturantSystem.service;

import com.luv2code.ResturantSystem.config.JwtUtil;
import com.luv2code.ResturantSystem.entity.User;
import com.luv2code.ResturantSystem.repository.UserRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public User addUser(User user){

        if (userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("This email Already Registerd");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    // add method for login
    public String login(String email, String password){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid email or password");
        }

        // هنا نولّد JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        return token;
    }

}
