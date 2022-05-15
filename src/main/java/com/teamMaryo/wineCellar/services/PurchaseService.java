package com.teamMaryo.wineCellar.services;

import com.teamMaryo.wineCellar.models.PurchaseModel;

public interface PurchaseService{
    Iterable<PurchaseModel> retrieveAll(Long userId) ;
    PurchaseModel create(Long userId, PurchaseModel purchase);
    PurchaseModel update(Long userId, Long purchaseId, PurchaseModel purchase);
    PurchaseModel retrieve(Long userId, Long purchaseId);
    void destroy(Long userId, Long purchaseId);
    
}