package com.teamMaryo.wineCellar.services.impl;

import java.util.Optional;

import com.teamMaryo.wineCellar.controller.PurchaseExtendedModel;
import com.teamMaryo.wineCellar.models.PurchaseModel;
import com.teamMaryo.wineCellar.repositories.PurchaseRepository;
import com.teamMaryo.wineCellar.services.PurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    
    @Autowired
    private PurchaseRepository repository;

    @Override
    public Iterable<PurchaseModel> retrieveAll(Long userId) {
        return repository.findByUserId(userId);
    }

    @Autowired
    private JdbcTemplate template;

    @Override
    public Iterable<PurchaseExtendedModel> retrieveAllExtended(Long userId) {
        String query = "SELECT * FROM PURCHASE INNER JOIN WINES ON PURCHASE.WINE_ID = WINES.WINE_ID "
        + "WHERE PURCHASE.USER_ID=" + userId.toString();
        
        Iterable<PurchaseExtendedModel> result = template.query(
            query,
            (data, rowNum) -> {
                return new PurchaseExtendedModel(
                    data.getLong("PURCHASE.PURCHASE_ID"),
                    data.getLong("WINES.WINE_ID"),
                    data.getInt("PURCHASE.COUNT"),
                    data.getString("WINES.NOMBRE"),
                    data.getString("WINES.LOCATION"),
                    data.getLong("WINES.QUANTITY"),
                    data.getFloat("WINES.PURCHASE_PRICE")
                );
            }
        );

        return result;
    }

    @Override
    public PurchaseExtendedModel retrieveExtended(Long userId, Long purchaseId) {
        String query = "SELECT * FROM PURCHASE INNER JOIN WINES ON PURCHASE.WINE_ID = WINES.WINE_ID "
        + "WHERE PURCHASE.USER_ID=" + userId.toString() 
        +" AND PURCHASE.PURCHASE_ID= " + purchaseId.toString();
        
        Iterable<PurchaseExtendedModel> result = template.query(
            query,
            (data, rowNum) -> {
                return new PurchaseExtendedModel(
                    data.getLong("PURCHASE.PURCHASE_ID"),
                    data.getLong("WINES.WINE_ID"),
                    data.getInt("PURCHASE.COUNT"),
                    data.getString("WINES.NOMBRE"),
                    data.getString("WINES.LOCATION"),
                    data.getLong("WINES.QUANTITY"),
                    data.getFloat("WINES.PURCHASE_PRICE")
                );
            }
        );

        for (PurchaseExtendedModel purchase : result) {
            return purchase;
        }

        return null;
    }

    @Override
    public PurchaseModel create(Long userId, PurchaseModel purchase) {
        PurchaseModel purchaseModel = retrieve(userId, purchase.getWineId());
        if (purchaseModel != null) {
            purchaseModel.incrementCount();
            return update(userId, purchaseModel.getPurchaseId(), purchaseModel);
        } else {
            purchaseModel.setPurchaseId(null);
            purchaseModel.setUserId(userId);
            purchaseModel.setCount(0);
        }

        return repository.save(purchase);
    }

    @Override
    public PurchaseModel update(Long userId, Long purchaseId, PurchaseModel purchase) {
        purchase.setUserId(userId);
        purchase.setPurchaseId(purchaseId);

        return repository.save(purchase);
    }

    @Override
    public PurchaseModel retrieve(Long userId, Long purchaseId) {
        Optional<PurchaseModel> purchaseModel = repository.findByUserIdAndPurchaseId(userId, purchaseId);
        if (!purchaseModel.isPresent()) {
            return null;
        }
        return purchaseModel.get();
    }

    @Override
    public void destroy(Long userId, Long purchaseId) {
        PurchaseModel purchaseModel = retrieve(userId, purchaseId);
        if (purchaseModel == null) {
            return;
        }

        repository.delete(purchaseModel);
    }
    
    @Override
    public boolean exists(Long userId, Long purchaseId) {
        PurchaseModel purchaseModel = retrieve(userId, purchaseId);
        if (purchaseModel == null) {
            return false;
        }
        return true;
    }
}
    