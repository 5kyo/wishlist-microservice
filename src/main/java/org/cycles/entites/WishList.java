package org.cycles.entites;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;

@Embeddable
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
