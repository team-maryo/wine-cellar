package com.teamMaryo.wineCellar.services;

import java.util.List;

import com.teamMaryo.wineCellar.models.OriginModel;

public interface OriginService {
    List<OriginModel> retrieveAll(Long clientId);
    OriginModel create(Long clientId, OriginModel denominacion);
    OriginModel update(Long clientId, Long denominacionId, OriginModel denominacion);
    OriginModel retrieve(Long clientId, Long denominacionId);
    void destroy(Long clientId, Long denominacionId);
}
