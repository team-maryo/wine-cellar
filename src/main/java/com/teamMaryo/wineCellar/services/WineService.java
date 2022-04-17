package com.teamMaryo.wineCellar.services;

import java.util.List;
import java.util.Optional;

import com.teamMaryo.wineCellar.joins.DenominacionWineJoin;
import com.teamMaryo.wineCellar.joins.TipoWineJoin;
import com.teamMaryo.wineCellar.models.WineModel;

public interface WineService {
    List<WineModel> retrieveAll(Long clientId);
    WineModel create(Long clientId, WineModel wine);
    WineModel update(Long clientId, Long wineId, WineModel wine);
    WineModel retrieve(Long clientId, Long wineId);
    void destroy(Long clientId, Long wineId);

    List<TipoWineJoin> retreiveTipoWines();
    List<DenominacionWineJoin> retreiveDenominacionWines();
}
