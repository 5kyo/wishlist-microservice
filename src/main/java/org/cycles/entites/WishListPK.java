package org.cycles.entites;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity
@Cacheable
public class WishListPK implements Serializable{

    private Long userId;
    private Long productId;

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WishListPK)) {
            return false;
        }
        WishListPK wishListPK = (WishListPK) o;
        return Objects.equals(userId, wishListPK.userId) && Objects.equals(productId, wishListPK.productId);
    }

}
