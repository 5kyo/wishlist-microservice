package org.cycles.services;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.cycles.entites.User;
import org.cycles.repositories.ProductRepository;
import org.cycles.repositories.UserRepository;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
public class UserService {
    
    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Uni<Response> createUser(User user){
        if (user == null || user.getUserId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }
        user.setUserPassword(BcryptUtil.bcryptHash(user.getUserPassword()));

        return userRepository.persistAndFlush(user)
                    .replaceWith(Response.ok(user).status(Response.Status.CREATED)::build);
    }

    @Transactional
    public Uni<Response> deleteUser(Long id){
        return userRepository.deleteById(id)
            .call(() -> productRepository.flush())
            .replaceWith(Response.ok().status(Response.Status.ACCEPTED)::build);
    }

    @Transactional
    public Uni<List<User>> getAllUsers(){
        return userRepository.listAll(Sort.by("userName"));
    }

    @Transactional
    public Uni<User> getSingleUser(Long id){
        return userRepository.findById(id);
    }
    
    @Transactional
    public Uni<Response> updateUser(User user){
        if(user == null){
            throw new WebApplicationException("User was not send on request.", 422);
        }
        return userRepository.findById(user.getUserId()).chain((pUser)->{
            pUser.setUserId(pUser.getUserId());
            pUser.setUserActive(user.getUserActive());
            pUser.setUserEmail(user.getUserEmail());
            pUser.setUserName(user.getUserName());
            pUser.setUserNickname(user.getUserNickname());
            pUser.setUserRole(user.getUserRole());
            pUser.setUserPassword(user.getUserPassword());
            pUser.setUserSurname(user.getUserSurname());
            pUser.setUserPhoneNumber(user.getUserPhoneNumber());
            return userRepository.persistAndFlush(pUser)
                    .replaceWith(Response.ok(user).status(Response.Status.ACCEPTED)::build);
        });
    }


    // @Transactional
    // public Uni<User> getWishList(Long id){
    //     return userRepository.findById(id)
    //         .onItem()
    //         .invoke(user -> user.getProducts());
    // } 

    // @Transactional
    // public Uni<User> addProductToWishList(Long id, Long productId){
    //     return userRepository.findById(id)
    //                          .onItem()
    //                          .invoke(user -> {
    //                              productRepository.findById(productId)
    //                              .onItem()
    //                              .invoke(product -> {
    //                                 user.setProducts(product);
    //                                 userRepository.persistAndFlush(user);
    //                              });
    //                          });
                            // .onItem()
                            // .ifNotNull()
                            // .invoke(user -> {
                            //     productRepository.findById(productId)
                            //                     .onItem()
                            //                     .ifNotNull()
                            //                     .invoke(product -> {
                            //                         System.out.println(product.getProductName());
                            //                         user.setProducts(product);
                            //                         userRepository.persistAndFlush(user);
                            //                     });
                            // }).replaceWith(Response.ok().status(Response.Status.ACCEPTED)::build);
        // return productRepository.findById(productId)
        //                         .onItem()
        //                         .invoke(entity -> {
        //                             userRepository.findById(id)
        //                             .onItem()
        //                             .invoke(user -> {
        //                                 user.getProducts().add(entity);
        //                                 userRepository.persistAndFlush(user);
        //                             });
        //                         })
}
