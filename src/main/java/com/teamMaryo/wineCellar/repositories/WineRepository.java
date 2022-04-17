package com.teamMaryo.wineCellar.repositories;

import com.teamMaryo.wineCellar.models.WineModel;

import org.springframework.stereotype.Repository;

@Repository
public interface WineRepository extends UserCrudRepository<WineModel, Long> {
}