package com.teamMaryo.wineCellar.services;

import com.teamMaryo.wineCellar.models.UserModel;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserModel create(UserModel user);
    UserModel update(String username, UserModel user);
    UserModel updatePassword(String username, UserModel user);
    UserModel retrieve(String username);
    UserModel retrieve(Long userId);
    Long retrieveIdFromUsername(String username);
    void destroy(Long userId);
    
}