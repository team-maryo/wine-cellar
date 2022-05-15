package com.teamMaryo.wineCellar.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("ORIGINS")
public class OriginModel {
    @Id
    @Column("ORIGIN_ID")
    private Long originId;

    @Column("NOMBRE")
    @NotNull
    @Size(max=64)
    private String nombre;

    @Column("DESCRIPTION")
    @Size(max=254)
    private String description;

    @Column("USER_ID")
    private Long userId;

    public OriginModel() {
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((originId == null) ? 0 : originId.hashCode());
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
        OriginModel other = (OriginModel) obj;
        if (originId == null) {
            if (other.originId != null)
                return false;
        } else if (!originId.equals(other.originId))
            return false;
        return true;
    }
}