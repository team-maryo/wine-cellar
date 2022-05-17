package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.models.PurchaseModel;
import com.teamMaryo.wineCellar.services.PurchaseService;
import com.teamMaryo.wineCellar.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PurchaseController{

    @Autowired
    PurchaseService service;

    @Autowired 
    UserService userService;

    @GetMapping("/extended/purchases")
    public ResponseEntity<Iterable<PurchaseExtendedModel>> retrievePurchaseExtended(@AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        return ResponseEntity.ok().body(service.retrieveAllExtended(userId));
    }

    @GetMapping("/purchases")
    public ResponseEntity<Iterable<PurchaseModel>> retrieveCompras(@AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        Iterable<PurchaseModel> compras = service.retrieveAll(userId);
        return ResponseEntity.ok().body(compras);
    }

    @PostMapping("/purchases")
    public ResponseEntity<PurchaseModel> createCompra(@RequestBody PurchaseModel compra, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        PurchaseModel newCompra = service.create(userId, compra);
        return ResponseEntity.ok().body(newCompra);
    }    
    
    @GetMapping("/purchases/{purchaseId}")
    public ResponseEntity<PurchaseModel> retrieveCompra(@PathVariable("purchaseId") Long purchaseId, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        PurchaseModel compra = service.retrieve(userId,purchaseId);
        return ResponseEntity.ok().body(compra);
    }

    @GetMapping("/extended/purchases/{purchaseId}")
    public ResponseEntity<PurchaseExtendedModel> retrieveExtendedPurchase(@PathVariable("purchaseId") Long purchaseId, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        PurchaseExtendedModel purchase = service.retrieveExtended(userId,purchaseId);
        return ResponseEntity.ok().body(purchase);
    }

    @PutMapping("/purchases/{purchaseId}")
    public ResponseEntity<PurchaseModel> updateCompra(@PathVariable("purchaseId") Long purchaseId, @RequestBody PurchaseModel newPurchase, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        PurchaseModel purchase = service.update(userId, purchaseId, newPurchase);
        return ResponseEntity.ok().body(purchase);
    }

    @DeleteMapping("/purchases/{purchaseId}")
    public ResponseEntity<PurchaseModel> deleteCompra(@PathVariable("purchaseId") Long purchaseId, @AuthenticationPrincipal User user) {
        Long userId = userService.retrieveIdFromUsername(user.getUsername());
        service.destroy(userId, purchaseId);
        return ResponseEntity.noContent().build();
    }

}