package com.teamMaryo.wineCellar.repositories;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.TipoModel;


import org.springframework.data.repository.CrudRepository;

public interface TypeRepository extends CrudRepository<TipoModel, Long> {
    public Iterable<TipoModel> findByUserId(Long userId);
    
    public Optional<TipoModel> findByUserIdAndTipoId(Long userId, Long typeId);
}
