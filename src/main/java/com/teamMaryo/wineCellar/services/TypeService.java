package com.teamMaryo.wineCellar.services;

import com.teamMaryo.wineCellar.models.TypeModel;


public interface TypeService {
    Iterable<TypeModel> retrieveAll(Long userId);
    TypeModel create(Long userId, TypeModel type);
    TypeModel update(Long userId, Long typeId, TypeModel type);
    TypeModel retrieve(Long userId, Long typeId);
    void destroy(Long userId, Long typeId);
}
