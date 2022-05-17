package com.teamMaryo.wineCellar.controller;

import com.teamMaryo.wineCellar.models.PurchaseModel;
import com.teamMaryo.wineCellar.models.WineModel;

public class PurchaseExtendedModel {
    private Long purchaseId;
    private Long wineId;
    private int count;
    private String nombre;
    private String location;
    private Long quantity;
    private float price;

    public PurchaseExtendedModel(Long purchaseId, Long wineId, int count, String nombre,
        String location, Long quantity, float price) {
        this.purchaseId = purchaseId;
        this.wineId = wineId;
        this.count = count;
        this.nombre = nombre;
        this.location = location;
        this.quantity = quantity;
        this.price = price;
    }

    public PurchaseExtendedModel(PurchaseModel purchase, WineModel wine) {
        this.purchaseId = purchase.getPurchaseId();
        this.wineId = wine.getWineId();
        this.count = purchase.getCount();
        this.nombre = wine.getNombre();
    }

    public Long getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }
    public Long getWineId() {
        return wineId;
    }
    public void setWineId(Long wineId) {
        this.wineId = wineId;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
