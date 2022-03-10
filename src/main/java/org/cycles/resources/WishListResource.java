package org.cycles.resources;

import io.smallrye.mutiny.Uni;
import org.cycles.dto.WishListDto;
import org.cycles.entites.WishList;
import org.cycles.services.WishListService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/wishlist")
public class WishListResource{

    @Inject
    WishListService wishListService;

    @POST
    @RolesAllowed("customer")
    public Uni<Response> addWishList(WishListDto wishListDto){
        return wishListService.addWishList(wishListDto);
    }

    @GET
    @Path("/{userId}")
    @RolesAllowed("customer")
    public Uni<List<WishList>> getUserWishList(Long userId){
        return wishListService.getWishListByUserId(userId);
    }

    @DELETE
    @Path("/{userId}/{productId}")
    public Uni<Response> deleteWishlist(Long userId, Long productId){
        return wishListService.deleteWishlist(userId,productId);
    }
}
