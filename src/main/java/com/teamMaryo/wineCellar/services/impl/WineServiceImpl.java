package com.teamMaryo.wineCellar.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.teamMaryo.wineCellar.joins.WineExtendedJoin;
import com.teamMaryo.wineCellar.models.OriginModel;
import com.teamMaryo.wineCellar.models.TipoModel;
import com.teamMaryo.wineCellar.models.WineExtendedModel;
import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.repositories.OriginRepository;
import com.teamMaryo.wineCellar.repositories.TypeRepository;
import com.teamMaryo.wineCellar.repositories.WineRepository;
import com.teamMaryo.wineCellar.services.WineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WineServiceImpl implements WineService {
    @Autowired
    private WineRepository repository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private OriginRepository originRepository;

    public WineExtendedModel getExtendedModel(WineModel model) {
        Optional<TipoModel> tipoModel = typeRepository.findById(model.getTipoId());
        Optional<OriginModel> originModel = originRepository.findById(model.getOriginId());

        if (!tipoModel.isPresent() || !originModel.isPresent()) {
            return null;
        }

        WineExtendedModel extendedModel = new WineExtendedModel(
            model,
            tipoModel.get(),
            originModel.get()
        );

        return extendedModel;
    }

    @Override
    public Iterable<WineExtendedModel> retrieveAll(Long userId){
        Iterable<WineModel> models = repository.findByUserId(userId);
        List<WineExtendedModel> extendedModels = new ArrayList<>();

        for (WineModel model : models) {
            WineExtendedModel extendedModel = getExtendedModel(model);
            if (extendedModel == null) {
                continue;
            }

            extendedModels.add(extendedModel);
        }


        return extendedModels;
    }

    @Override
    public WineModel create(Long userId, WineModel wine) {
        wine.setWineId(null);
        wine.setUserId(userId);
        return repository.save(wine);
    }

    @Override
    public WineModel update(Long userId, Long wineId, WineModel wine) {
        WineModel oldWine = getByUserIdAndWineId(userId, wineId);
        if (!oldWine.getUserId().equals(userId)) {
            return null;
        }

        wine.setWineId(wineId);
        wine.setUserId(userId);

        return repository.save(wine);
    }

    public WineModel getByUserIdAndWineId(Long userId, Long wineId) {
        Optional<WineModel> wine = repository.findByUserIdAndWineId(userId,wineId);
        if (wine.isPresent()) {
            return wine.get();
        } else {
            return null;
        }
    }

    @Override 
    public WineExtendedModel retrieve(Long userId, Long wineId){
        WineModel wine = getByUserIdAndWineId(userId, wineId);
        if (wine != null) {
            return getExtendedModel(wine);
        }
        return null;
    }

    @Override
    public void destroy(Long userId, Long wineId) {
        WineModel wineModel = getByUserIdAndWineId(userId, wineId);
        if (wineModel == null) {
            return;
        }

        repository.delete(wineModel);
    }

    @Override
    public boolean exists(Long userId, Long wineId) {
        WineModel wineModel = getByUserIdAndWineId(userId, wineId);
        if (wineModel == null) {
            return false;
        }
        return true;
    }

    @Autowired
    private JdbcTemplate template;

    @Override
    public Iterable<WineExtendedJoin> retrieveAllExtended(Long userId) {
        String query = "SELECT * FROM WINES "
            +"INNER JOIN TIPOS ON WINES.TIPO_ID = TIPOS.TIPO_ID "
            +"INNER JOIN ORIGINS ON WINES.ORIGIN_ID = ORIGINS.ORIGIN_ID "
            +"WHERE WINES.USER_ID=" + userId.toString();

        Iterable<WineExtendedJoin> extendedWines = template.query(
            query,
            (data, rowNum) -> {
                WineExtendedJoin extendedWine = new WineExtendedJoin();
                extendedWine.setWineId(data.getLong("WINES.WINE_ID"));
                extendedWine.setWineName(data.getString("WINES.NOMBRE"));
                extendedWine.setWineDescription(data.getString("WINES.DESCRIPTION"));
                extendedWine.setQuantity(data.getLong("WINES.QUANTITY"));
                extendedWine.setPrice(data.getFloat("WINES.PURCHASE_PRICE"));
                extendedWine.setLocation(data.getString("WINES.LOCATION"));
                extendedWine.setYear(data.getLong("WINES.FROM_YEAR"));
                extendedWine.setRating(data.getLong("WINES.RATING"));
                extendedWine.setTypeId(data.getLong("TIPOS.TIPO_ID"));
                extendedWine.setTypeName(data.getString("TIPOS.NOMBRE"));
                extendedWine.setTypeDescription(data.getString("TIPOS.DESCRIPTION"));
                extendedWine.setOriginId(data.getLong("ORIGINS.ORIGIN_ID"));
                extendedWine.setOriginName(data.getString("ORIGINS.NOMBRE"));
                extendedWine.setUserId(data.getLong("WINES.USER_ID"));
                return extendedWine;
            }
        );

        return extendedWines;
    }

}