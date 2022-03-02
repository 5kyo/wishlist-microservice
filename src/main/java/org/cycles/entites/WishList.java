package org.cycles.entites;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Cacheable
public class WishList implements Serializable{
    
    @EmbeddedId
    private WishListPK wishListPK;

    public WishListPK getWishListPK() {
        return this.wishListPK;
    }

    public void setWishListPK(WishListPK wishListPK) {
        this.wishListPK = wishListPK;
    }
}
