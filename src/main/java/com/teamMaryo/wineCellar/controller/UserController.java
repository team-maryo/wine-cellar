package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.models.UserModel;
import com.teamMaryo.wineCellar.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @GetMapping("/users")
    public ResponseEntity<UserModel> retrieveAllUsers(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(service.retrieve(user.getUsername()));
    }

    @PostMapping("/users")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel newUser) {
        String hashedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(hashedPassword);
        return ResponseEntity.ok().body(service.create(newUser));
    }

    @PutMapping("/users")
    public ResponseEntity<UserModel> updateUser(@AuthenticationPrincipal User user, @RequestBody UserModel userModel) {
        Long userId = service.retrieveIdFromUsername(user.getUsername());
        return ResponseEntity.ok().body(service.update(userId, userModel));
    }
}
