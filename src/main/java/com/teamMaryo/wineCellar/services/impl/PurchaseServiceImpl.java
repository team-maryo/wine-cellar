package com.teamMaryo.wineCellar.services.impl;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.PurchaseModel;
import com.teamMaryo.wineCellar.repositories.PurchaseRepository;
import com.teamMaryo.wineCellar.services.PurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
public class PurchaseServiceImpl implements PurchaseService {
    
    @Autowired
    private PurchaseRepository repository;

    @Override
    public Iterable<PurchaseModel> retrieveAll(Long userId) {
        return repository.findByUserId(userId);
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
        }

        return repository.save(purchase);
    }

    @Override
    public PurchaseModel update(Long userId, Long purchaseId, PurchaseModel purchase) {
        if (!purchase.getUserId().equals(userId)) {
            return null;
        }

        if (!purchase.getPurchaseId().equals(purchaseId)) {
            return null;
        }

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
}
    