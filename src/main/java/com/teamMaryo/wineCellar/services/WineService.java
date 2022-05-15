package com.teamMaryo.wineCellar.services;

import com.teamMaryo.wineCellar.joins.WineExtendedJoin;
import com.teamMaryo.wineCellar.models.WineModel;

public interface WineService{
    Iterable<WineModel> retrieveAll(Long userId);
    WineModel create(Long userId, WineModel wine);
    WineModel update(Long userId, Long wineId, WineModel wine);
    WineModel retrieve(Long userId, Long wineId);
    boolean exists(Long userId, Long wineId);
    void destroy(Long userId, Long wineId);

    Iterable<WineExtendedJoin> retrieveAllExtended(Long userId);
}