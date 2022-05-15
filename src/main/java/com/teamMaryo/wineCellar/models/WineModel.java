package com.teamMaryo.wineCellar.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("WINES")
public class WineModel {
    @Id
    @Column("WINE_ID")
    private Long wineId;

    @Column("NOMBRE")
    private String winename;

    @Column("DESCRIPTION")
    private String description;

    @Column("QUANTITY")
    private Long quantity;

    @Column("PURCHASE_PRICE")
    private Long price;

    @Column("LOCATION")
    private String location;

    @Column("FROM_YEAR")
    private Long year;

    @Column("RATING")
    private Long rating;

    @Column("TYPE_ID")
    private Long typeId;

    @Column("ORIGIN_ID")
    private Long originId;

    @Column("USER_ID")
    private Long userId;

    public WineModel() {
    }

    public Long getWineId() {
        return wineId;
    }

    public void setWineId(Long wineId) {
        this.wineId = wineId;
    }

    public String getWinename() {
        return winename;
    }

    public void setWinename(String winename) {
        this.winename = winename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((wineId == null) ? 0 : wineId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WineModel other = (WineModel) obj;
        if (wineId == null) {
            if (other.wineId != null)
                return false;
        } else if (!wineId.equals(other.wineId))
            return false;
        return true;
    }


}
