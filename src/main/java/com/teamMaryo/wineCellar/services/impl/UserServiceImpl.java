package com.teamMaryo.wineCellar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.teamMaryo.wineCellar.models.UserModel;
import com.teamMaryo.wineCellar.repositories.UserRepository;
import com.teamMaryo.wineCellar.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserModel create(UserModel user){
        // Ensure that the user is non existant
        user.setUserId(null);
        UserModel otherUser = retrieve(user.getUsername());

        if (otherUser != null) {
            UserModel createdUser = repository.save(user);
            return createdUser;
        } else {
            return null;
        }        
    }
    
    @Override
    public UserModel update(String username, UserModel user) {
        UserModel oldUser = retrieve(username);
        user.setUserId(oldUser.getUserId());
        user.setUsername(oldUser.getUsername());
        user.setPassword(oldUser.getPassword());
        
        UserModel updatedUser = repository.save(user);
        return updatedUser;
    } 

    @Override
    public UserModel updatePassword(String username, UserModel user) {
        UserModel oldUser = retrieve(username);
        user.setUserId(oldUser.getUserId());
        user.setUsername(oldUser.getUsername());
        user.setEmail(oldUser.getEmail());

        UserModel updateUser = repository.save(user);
        return updateUser;
    }

    @Override
    public UserModel retrieve(String username) {
        UserModel userModel = repository.findByUsername(username);
        return userModel;
    }
    
    @Override
    public UserModel retrieve(Long userId) {
        Optional<UserModel> user = repository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }
    
    @Override
    public void destroy(Long userId) {
        UserModel user = retrieve(userId);
        if (user == null) return;
        repository.delete(user);
    } 

    @Override
    public Long retrieveIdFromUsername(String username) {
        return repository.findByUsername(username).getUserId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel user = repository.findByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        UserDetails newUser = new User(user.getUsername(), user.getPassword(), authorities);
        return newUser;
    }
}
