package com.teamMaryo.wineCellar.joins;

public class TipoWineJoin {
    private Long tipoId;
    private Long wineId;
    private String tipo;
    private String wineName;
    
    public TipoWineJoin(Long tipoId, Long wineId, String tipo, String wineName) {
        this.tipoId = tipoId;
        this.wineId = wineId;
        this.tipo = tipo;
        this.wineName = wineName;
    }
    
    public Long getTipoId() {
        return tipoId;
    }
    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }
    public Long getWineId() {
        return wineId;
    }
    public void setWineId(Long wineId) {
        this.wineId = wineId;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getWineName() {
        return wineName;
    }
    public void setWineName(String wineName) {
        this.wineName = wineName;
    }
}