package org.cycles.dto;

import java.io.Serializable;

public class ProductDto implements Serializable{
    private Long productId;
    
    private String productName;
    
    private Integer productStock;
    private double productPrice; 

    public ProductDto(){

    }

    public ProductDto(Long productId, String productName, Integer productStock, double productPrice) {
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
