package com.teamMaryo.wineCellar.services;

import com.teamMaryo.wineCellar.models.UserModel;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserModel create(Long userId, UserModel user);
    UserModel update(Long userId, UserModel user);
    UserModel retrieve(Long userId);
    Long retrieveIdFromUsername(String username);
    void destroy(Long userId);
    
}