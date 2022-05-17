package com.teamMaryo.wineCellar.models;

import javax.validation.constraints.Min;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("PURCHASE")
public class PurchaseModel {
    @Id
    @Column("PURCHASE_ID")
    private Long purchaseId;

    @Column("COUNT")
    @Min(0)
    private int count;

    @Column("WINE_ID")
    private Long wineId;

    @Column("USER_ID")
    private Long userId;

    public PurchaseModel() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void incrementCount() {
        this.count += 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + count;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        PurchaseModel other = (PurchaseModel) obj;
        if (count != other.count)
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (wineId == null) {
            if (other.wineId != null)
                return false;
        } else if (!wineId.equals(other.wineId))
            return false;
        return true;
    }

    
}