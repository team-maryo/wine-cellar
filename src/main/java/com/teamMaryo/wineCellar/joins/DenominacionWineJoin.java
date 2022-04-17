package com.teamMaryo.wineCellar.joins;

public class DenominacionWineJoin {
    private Long denominacionId;
    private Long wineId;
    private String denominacion;
    private String wineName;

    
    public DenominacionWineJoin(Long denominacionId, Long wineId, String denominacion, String wineName) {
        this.denominacionId = denominacionId;
        this.wineId = wineId;
        this.denominacion = denominacion;
        this.wineName = wineName;
    }

    public Long getDenominacionId() {
        return denominacionId;
    }
    
    public void setDenominacionId(Long denominacionId) {
        this.denominacionId = denominacionId;
    }

    public Long getWineId() {
        return wineId;
    }

    public void setWineId(Long wineId) {
        this.wineId = wineId;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getWineName() {
        return wineName;
    }
    
    public void setWineName(String wineName) {
        this.wineName = wineName;
    }
}
