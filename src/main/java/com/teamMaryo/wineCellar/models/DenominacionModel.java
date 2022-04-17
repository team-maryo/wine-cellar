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
import jakarta.validation.constraints.Size;

@Entity
@Table(name="DENOMINACION")
public class DenominacionModel implements Clientelable {
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=0, max=64)
    @Column(name="DENOMINACION")
    private String denominacion;

    @Column(name="DESCRIPTION")
    private String description;

    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    private ClientModel client;

    public DenominacionModel() {}

    public DenominacionModel(Long id, @Size(min = 0, max = 64) String denominacion, String description,
            ClientModel client) {
        this.id = id;
        this.denominacion = denominacion;
        this.description = description;
        this.client = client;
    }
    public ClientModel getClient() {
        return client;
    }
    public void setClient(ClientModel client) {
        this.client = client;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        DenominacionModel other = (DenominacionModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
    
