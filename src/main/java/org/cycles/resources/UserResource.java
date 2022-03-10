package org.cycles.resources;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import org.cycles.dto.UserDto;
import org.cycles.entites.User;
import org.cycles.services.UserService;
import org.jboss.resteasy.reactive.RestPath;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
    @PermitAll
    public Multi<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    public Uni<UserDto> getSingleUser(@PathParam("id") Long userId) {
        return userService.getSingleUser(userId);
    }

    @POST
    // @RolesAllowed({"admin"})
    public Uni<Response> createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DELETE
    @Path("/{id}")
    // @RolesAllowed({"admin"})
    public Uni<Response> deleteUser(@PathParam("id") Long userId) {
        return userService.deleteUser(userId);
    }

    @PATCH
    @Path("/{id}")
    // @RolesAllowed({"admin"})
    public Uni<Response> updateUser(@PathParam("id") Long userId, UserDto userDto) {
        return userService.updateUser(userId, userDto);
    }

}
