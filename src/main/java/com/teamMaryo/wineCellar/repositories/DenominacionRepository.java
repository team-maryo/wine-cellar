package com.teamMaryo.wineCellar.repositories;

import com.teamMaryo.wineCellar.models.DenominacionModel;

import org.springframework.stereotype.Repository;

@Repository
public interface DenominacionRepository extends UserCrudRepository<DenominacionModel, Long> {
    
}