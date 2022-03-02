package org.cycles.resources;

import io.smallrye.mutiny.Uni;

import org.cycles.entites.User;
import org.cycles.services.UserService;
import org.jboss.resteasy.reactive.RestPath;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class UserResource {

    @Inject
    UserService userService;

    @GET
    public Uni<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public Uni<User> getSingleUser(@RestPath Long id) {
        return userService.getSingleUser(id);
    }

    @POST
    public Uni<Response> createUser(User user) {
        return userService.createUser(user);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> deleteUser(@RestPath Long id) {
        return userService.deleteUser(id);
    }

    @PUT
    public Uni<Response> updateUser(User user) {
        return userService.updateUser(user);
    }

    // @GET
    // @Path("/{id}/products")
    // public Uni<User> getWishList(@RestPath Long id) {
    //     return userService.getWishList(id);
    // }

    // @POST
    // @Path("/{id}/products/{productId}")
    // public Uni<User> addProductToWishList(@PathParam("id") Long id, 
    //                                         @PathParam("productId") Long productId) {
    //     return userService.addProductToWishList(id, productId);

    // }

}
