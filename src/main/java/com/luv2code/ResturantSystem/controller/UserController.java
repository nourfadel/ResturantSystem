package com.luv2code.ResturantSystem.controller;

import com.luv2code.ResturantSystem.entity.User;
import com.luv2code.ResturantSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> creatUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @GetMapping("/{username}")
    public User getUserByName(@PathVariable String username){
         return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }

    // add endpoint for login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> loginData){

        String email = loginData.get("email");
        String password = loginData.get("password");
        String token = userService.login(email,password);

        return ResponseEntity.ok("{\"token\": \"" + token + "\"}");

    }

}
