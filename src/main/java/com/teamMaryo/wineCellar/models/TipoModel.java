package com.teamMaryo.wineCellar.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("TIPOS")
public class TipoModel {
    @Id
    @Column("TIPO_ID")
    private Long tipoId;

    @Column("NOMBRE")
    @Size(max=64)
    @NotNull
    private String nombre;

    @Column("DESCRIPTION")
    @Size(max=254)
    private String description;

    @Column("USER_ID")
    private Long userId;

    public TipoModel() {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        TipoModel other = (TipoModel) obj;
        if (tipoId == null) {
            if (other.tipoId != null)
                return false;
        } else if (!tipoId.equals(other.tipoId))
            return false;
        return true;
    }

    
}