package com.teamMaryo.wineCellar.models;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


@Entity
@Table(name= "WINE")
public class WineModel implements Clientelable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=0, max=64)
    @Column(name="NOMBRE")
    private String name;
    
    @Column(name="DESCRIPTION")
    @Size(min=0, max=255)
    private String description;
    
    @Min(1700)
    @Max(2100)
    @Column(name="FROM_YEAR")
    private Long fromYear;
    
    @Min(0)
    @Max(5)
    @Column(name="RATING")
    private Long rating;
    
    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    private TipoModel tipo;
    
    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    private DenominacionModel denominacion;
    
    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    private ClientModel client;

    public WineModel() {}

    public WineModel(Long id, @Size(min = 0, max = 64) String name, @Size(min = 0, max = 255) String description,
            @Min(1700) @Max(2100) Long fromYear, @Min(0) @Max(5) Long rating, TipoModel tipo,
            DenominacionModel denominacion, ClientModel client) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fromYear = fromYear;
        this.rating = rating;
        this.tipo = tipo;
        this.denominacion = denominacion;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public void setId(Long id){
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFromYear() {
        return fromYear;
    }

    public void setFromYear(Long fromYear) {
        this.fromYear = fromYear;
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

    public DenominacionModel getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(DenominacionModel denominacion) {
        this.denominacion = denominacion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
    
