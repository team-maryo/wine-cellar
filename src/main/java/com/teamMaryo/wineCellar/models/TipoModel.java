package com.teamMaryo.wineCellar.models;

import jakarta.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="TIPO")
public class TipoModel implements Clientelable {
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

    @Size(min=0, max=64)
	@Column(name="NOMBRE")
	private String nombre;
	
    @Size(min=0, max=255)
	@Column(name="DESCRIPTION")
	private String description;
	
    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    private ClientModel client;

    public TipoModel() {}

    public TipoModel(Long id, @Size(min = 0, max = 64) String nombre, @Size(min = 0, max = 255) String description,
            ClientModel client) {
        this.id = id;
        this.nombre = nombre;
        this.description = description;
        this.client = client;
    }
    public ClientModel getClient() {
        return client;
    }
	public void setClient(ClientModel client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
        TipoModel other = (TipoModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}