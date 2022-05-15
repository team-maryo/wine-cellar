package com.teamMaryo.wineCellar.repositories;

import com.teamMaryo.wineCellar.models.UserModel;

import org.springframework.data.repository.CrudRepository;

public interface  UserRepository extends CrudRepository<UserModel, Long> {
    public UserModel findByUsername(String username);
}
