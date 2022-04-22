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

//     id_wine = models.AutoField(primary_key=True)
//     quantity = models.IntegerField(default=0)
//     nombre = models.CharField(max_length=255, null=True)
//     lavinia_code = models.CharField(max_length=10, null=True)
//     purchase_price = models.DecimalField(max_digits=9, decimal_places=4, null=True)
//     selling_price = models.DecimalField(max_digits=9, decimal_places=4, null=True)
//     location = models.CharField(max_length=50, null=True)
//     origin = models.ForeignKey(Origin, on_delete=models.PROTECT)
//     tipo = models.ForeignKey(Tipo, on_delete=models.PROTECT)

@Entity
@Table(name= "WINE")
public class WineModel implements Clientelable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Size(min=0, max=64)
    @Column(name="NOMBRE")
    private String nombre;
    
    @Column(name="DESCRIPTION")
    @Size(min=0, max=255)
    private String description;

    @Column(name="QUANTITY")
    @Min(0)
    private Integer quantity;

    @Column(name="PURCHASE_PRICE")
    private Float purchasePrice;

    @Column(name="LOCATION")
    private String location;
    
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
    private OriginModel origin;
    
    @ManyToOne
    @OnDelete(action=OnDeleteAction.CASCADE)
    private ClientModel client;

    public WineModel() {}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public OriginModel getOrigin() {
        return origin;
    }

    public void setOrigin(OriginModel origin) {
        this.origin = origin;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }
}
    
