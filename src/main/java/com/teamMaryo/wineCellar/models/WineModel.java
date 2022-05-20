package com.teamMaryo.wineCellar.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("WINES")
public class WineModel {
    @Id
    @Column("WINE_ID")
    private Long wineId;

    @Column("NOMBRE")
    @Size(max=64)
    @NotNull
    private String nombre;

    @Column("DESCRIPTION")
    @Size(max=254)
    private String description;

    @Column("QUANTITY")
    @Min(0)
    private Long quantity;

    @Column("PURCHASE_PRICE")
    @Min(0)
    private float price;

    @Column("LOCATION")
    @Size(max=254)
    private String location;

    @Column("FROM_YEAR")
    @Min(1800)
    @Max(2300)
    private Long year;

    @Column("RATING")
    @Min(0)
    @Max(5)
    private Long rating;

    @Column("TIPO_ID")
    private Long tipoId;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
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
        result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
        result = prime * result + ((originId == null) ? 0 : originId.hashCode());
        result = prime * result + ((tipoId == null) ? 0 : tipoId.hashCode());
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
        if (nombre == null) {
            if (other.nombre != null)
                return false;
        } else if (!nombre.equals(other.nombre))
            return false;
        if (originId == null) {
            if (other.originId != null)
                return false;
        } else if (!originId.equals(other.originId))
            return false;
        if (tipoId == null) {
            if (other.tipoId != null)
                return false;
        } else if (!tipoId.equals(other.tipoId))
            return false;
        return true;
    }

    


}
