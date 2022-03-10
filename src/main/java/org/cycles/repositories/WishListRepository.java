package org.cycles.repositories;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import org.cycles.entites.WishList;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WishListRepository implements PanacheRepository<WishList> {
    public Uni<WishList> findByComposedKey(long userId, long productId){
        return this.find("FROM WishList ws WHERE ws.wishListPK.userId = ?1 AND ws.wishListPK.productId = ?2",userId, productId).firstResult();
    }
}
