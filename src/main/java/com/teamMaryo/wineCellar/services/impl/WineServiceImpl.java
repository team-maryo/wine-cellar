package com.teamMaryo.wineCellar.services.impl;

import java.util.Optional;

import com.teamMaryo.wineCellar.joins.WineExtendedJoin;
import com.teamMaryo.wineCellar.models.WineModel;
import com.teamMaryo.wineCellar.repositories.WineRepository;
import com.teamMaryo.wineCellar.services.WineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class WineServiceImpl implements WineService {
    @Autowired
    private WineRepository repository;

    @Override
    public Iterable<WineModel> retrieveAll(Long userId){
        return repository.findByUserId(userId);
    }

    @Override
    public WineModel create(Long userId, WineModel wine) {
        wine.setWineId(null);
        wine.setUserId(userId);
        return repository.save(wine);
    }

    @Override
    public WineModel update(Long userId, Long wineId, WineModel wine) {
        if (!wine.getUserId().equals(userId)) {
            return null;
        }

        if (!wine.getWineId().equals(wineId)) {
            return null;
        }

        return repository.save(wine);
    }


    @Override 
    public WineModel retrieve(Long userId, Long wineId){
        Optional<WineModel> wine = repository.findByUserIdAndWineId(userId,wineId);
        if (wine.isPresent()) {
            return wine.get();
        } else {
            return null;
        }
    }
    

    @Override
    public void destroy(Long userId, Long wineId) {
        WineModel wineModel = retrieve(userId, wineId);
        if (wineModel == null) {
            return;
        }

        repository.delete(wineModel);
    }

    @Override
    public boolean exists(Long userId, Long wineId) {
        WineModel wineModel = retrieve(userId, wineId);
        if (wineModel == null) {
            return false;
        }
        return true;
    }

    @Autowired
    private JdbcTemplate template;

    @Override
    public Iterable<WineExtendedJoin> retrieveAllExtended(Long userId) {
        String query = "SELECT * FROM WINE" 
            +"INNER JOIN TYPES ON WINE.WINE_ID = TYPES.WINE_ID"
            +"INNER JOIN ORIGINS ON WINE.WINE_ID = ORIGINS.WINE_ID";

        Iterable<WineExtendedJoin> extendedWines = template.query(
            query,
            (data, rowNum) -> {
                WineExtendedJoin extendedWine = new WineExtendedJoin();
                extendedWine.setWineId(data.getLong("WINE.WINE_ID"));
                extendedWine.setWineName(data.getString("WINE.NOMBRE"));
                extendedWine.setWineDescription(data.getString("WINE.DESCRIPTION"));
                extendedWine.setQuantity(data.getLong("WINE.QUANTITY"));
                extendedWine.setPrice(data.getFloat("WINE.PRICE"));
                extendedWine.setLocation(data.getString("WINE.LOCATION"));
                extendedWine.setYear(data.getLong("WINE.YEAR"));
                extendedWine.setRating(data.getLong("WINE.RATING"));
                extendedWine.setTypeId(data.getLong("TYPES.WINE_ID"));
                extendedWine.setTypeName(data.getString("TYPES.NOMBRE"));
                extendedWine.setTypeDescription(data.getString("TYPES.DESCRIPTION"));
                extendedWine.setOriginId(data.getLong("ORIGINS.ORIGIN_ID"));
                extendedWine.setOriginName(data.getString("ORIGINS.NOMBRE"));
                extendedWine.setUserId(data.getLong("WINES.USER_ID"));
                return extendedWine;
            }
        );

        return extendedWines;
    }

}