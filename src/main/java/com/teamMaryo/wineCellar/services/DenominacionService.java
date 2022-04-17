package com.teamMaryo.wineCellar.services;

import java.util.List;

import com.teamMaryo.wineCellar.models.DenominacionModel;

public interface DenominacionService {
    List<DenominacionModel> retrieveAll(Long clientId);
    DenominacionModel create(Long clientId, DenominacionModel denominacion);
    DenominacionModel update(Long clientId, Long denominacionId, DenominacionModel denominacion);
    DenominacionModel retrieve(Long clientId, Long denominacionId);
    void destroy(Long clientId, Long denominacionId);
}
