package com.teamMaryo.wineCellar.models;

public class WineExtendedModel {
    private Long wineId;
    private String nombre;
    private String description;
    private Long quantity;
    private float price;
    private String location;
    private Long year;
    private Long rating;
    private Long userId;
    private TipoModel tipo;
    private OriginModel origin;

    public WineExtendedModel() {}

    public WineExtendedModel(WineModel wine, TipoModel tipo, OriginModel origin) {
        this.wineId = wine.getWineId();
        this.nombre = wine.getNombre();
        this.description =wine.getDescription();
        this.quantity =wine.getQuantity();
        this.price =wine.getPrice();
        this.location =wine.getLocation();
        this.year =wine.getYear();
        this.rating =wine.getRating();
        this.userId =wine.getUserId();
        this.tipo=tipo;
        this.origin= origin;
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
    public TipoModel getTipo() {
        return tipo;
    }
    public void setTipo(TipoModel tipo) {
        this.tipo = tipo;
    }
    public OriginModel getOrigin() {
        return origin;
    }
    public void setOrigin(OriginModel origin) {
        this.origin = origin;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "WineExtendedModel [nombre=" + nombre + ", wineId=" + wineId + "]";
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
        WineExtendedModel other = (WineExtendedModel) obj;
        if (wineId == null) {
            if (other.wineId != null)
                return false;
        } else if (!wineId.equals(other.wineId))
            return false;
        return true;
    }
}
