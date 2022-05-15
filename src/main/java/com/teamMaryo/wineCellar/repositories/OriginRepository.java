package com.teamMaryo.wineCellar.repositories;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.OriginModel;

import org.springframework.data.repository.CrudRepository;

public interface OriginRepository extends CrudRepository<OriginModel, Long>{
    public Iterable<OriginModel> findByUserId(Long userId);
    public Optional<OriginModel> findByUserIdAndOriginId(Long userId, Long originId);
}
