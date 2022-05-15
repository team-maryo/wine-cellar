package com.teamMaryo.wineCellar.services;

import com.teamMaryo.wineCellar.models.TipoModel;


public interface TipoService {
    Iterable<TipoModel> retrieveAll(Long userId);
    TipoModel create(Long userId, TipoModel type);
    TipoModel update(Long userId, Long typeId, TipoModel type);
    TipoModel retrieve(Long userId, Long typeId);
    void destroy(Long userId, Long typeId);
}
