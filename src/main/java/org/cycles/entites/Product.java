package org.cycles.entites;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=false)
@Cacheable
@Table(name = "product")
public class Product extends PanacheEntity{

    @Column(length = 40, unique = true)
    private String productName;
    
    private Integer productStock;
    private double productPrice; 

    public Product(){

    }

    public Product(String productName, Integer productStock, double productPrice) {
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
    }


    public void addCountToStock(Integer productStock){
        this.productStock+=productStock;
    }

    @ManyToMany(mappedBy = "products")
    private Set<User> users;

}
