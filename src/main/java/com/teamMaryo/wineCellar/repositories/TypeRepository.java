package com.teamMaryo.wineCellar.repositories;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.TypeModel;


import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<TypeModel, Long> {
    public Iterable<TypeModel> findByUserId(Long userId);
    
    public Optional<TypeModel> findByUserIdAndTypeId(Long userId, Long typeId);
}
