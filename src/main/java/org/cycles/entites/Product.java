package org.cycles.entites;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Cacheable
public class Product implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    
    private String productName;
    
    private Integer productStock;
    private double productPrice; 

    @ManyToMany(cascade = { CascadeType.MERGE,
                            CascadeType.PERSIST,
                            CascadeType.REFRESH }, 
                fetch = FetchType.LAZY)

    @JoinTable(
            name = "wishlist",
            joinColumns = { @JoinColumn(name = "productId") },
            inverseJoinColumns = {  @JoinColumn(name = "userId") }
    )

    private Set<User> user;

    public Product(){

    }

    public Product(Long productId, String productName, Integer productStock, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductStock() {
        return this.productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public double getProductPrice() {
        return this.productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void addCountToStock(Integer productStock){
        this.productStock+=productStock;
    }

}
