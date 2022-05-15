package com.teamMaryo.wineCellar.repositories;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.WineModel;

import org.springframework.data.repository.CrudRepository;

public interface WineRepository extends CrudRepository<WineModel, Long> {
    public Iterable<WineModel> findByUserId(Long userId);
     public Optional<WineModel> findByUserIdAndWineId(Long userId, Long wineId);
}
