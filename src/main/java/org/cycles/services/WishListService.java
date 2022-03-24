package org.cycles.services;

import io.smallrye.mutiny.Uni;
import org.cycles.dto.WishListDto;
import org.cycles.entites.WishList;
import org.cycles.entites.WishListPK;

import org.cycles.repositories.WishListRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class WishListService {
    @Inject
    WishListRepository wishListRepository;

    @Transactional
    public Uni<Response> addWishList(WishListDto wishListDto){
        Stream<WishList> wishLists = wishListDto.getProductsIds().stream().map( id -> {
            WishList wishList = new WishList();
            WishListPK wishListPK = new WishListPK();
            wishListPK.setUserId(wishListDto.getUserId());
            wishListPK.setProductId(id);
            wishList.setWishListPK(wishListPK);
            return wishList;
        });
        
        return wishListRepository.persist(wishLists).chain(()->{
            return wishListRepository.flush();
        }).replaceWith(Response.ok(wishListDto).build());
    }

    @Transactional
    public Uni<List<WishList>> getWishListByUserId(Long id){
        return wishListRepository.find("SELECT DISTINCT u.products FROM User u, WishList ws WHERE ws.wishListPK.userId = ?1", id)
                .list();
        // return userRepository.findById(id).map((user)->user.getProducts());
    }

    @Transactional
    public Uni<Response> deleteWishlist(Long userId, Long productId){
        return wishListRepository.findByComposedKey(userId,productId)
                .chain(wishList -> wishListRepository.delete(wishList))
                .chain(wishList -> wishListRepository.flush())
                .replaceWith(Response.ok().build());
    }
}
