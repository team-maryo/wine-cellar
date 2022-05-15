package com.teamMaryo.wineCellar.services;

import com.teamMaryo.wineCellar.models.OriginModel;

public interface OriginService {
    Iterable<OriginModel> retrieveAll(Long userId);
    OriginModel create(Long userId, OriginModel origin);
    OriginModel update(Long userId, Long originId, OriginModel origin);
    OriginModel retrieve(Long userId, Long originId);
    void destroy(Long userId, Long originId);
}
