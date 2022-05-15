package com.teamMaryo.wineCellar.repositories;

import java.util.Optional;

import com.teamMaryo.wineCellar.models.PurchaseModel;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<PurchaseModel, Long> {
    public Iterable<PurchaseModel> findByWineId(Long wineId);
    public Iterable<PurchaseModel> findByUserId(Long userId);
    public Optional<PurchaseModel> findByUserIdAndPurchaseId(Long userId, Long purchaseId);

}
