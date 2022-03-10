package org.cycles.services;

import io.smallrye.mutiny.Uni;
import org.cycles.dto.WishListDto;
import org.cycles.entites.Product;
import org.cycles.entites.WishList;
import org.cycles.entites.WishListPK;
import org.cycles.repositories.UserRepository;
import org.cycles.repositories.WishListRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Set;
import java.util.stream.Stream;

@ApplicationScoped
public class WishListService {
    @Inject
    WishListRepository wishListRepository;
    @Inject
    UserRepository userRepository;

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
    public Uni<Set<Product>> getWishListByUserId(Long id){
        return userRepository.findById(id).map((user)->user.getProducts());
    }
}
