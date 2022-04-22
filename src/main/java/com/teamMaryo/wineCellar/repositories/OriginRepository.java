package com.teamMaryo.wineCellar.repositories;

import com.teamMaryo.wineCellar.models.OriginModel;

import org.springframework.stereotype.Repository;

@Repository
public interface OriginRepository extends UserCrudRepository<OriginModel, Long> {
    
}