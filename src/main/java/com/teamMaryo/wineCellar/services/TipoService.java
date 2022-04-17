package com.teamMaryo.wineCellar.services;

import java.util.List;

import com.teamMaryo.wineCellar.models.TipoModel;

public interface TipoService {
    List<TipoModel> retrieveAll(Long clientId);
    TipoModel create(Long clientId, TipoModel tipo);
    TipoModel update(Long clientId, Long tipoId, TipoModel tipo);
    TipoModel retrieve(Long clientId, Long tipoId);
    void destroy(Long clientId, Long tipoId);
}
