package org.cycles.services;

import org.cycles.entites.Product;
import org.cycles.entites.User;
import org.cycles.repositories.ProductRepository;
import org.cycles.repositories.UserRepository;

import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserService {
    
    @Inject
    UserRepository em;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Uni<Response> createdUser(User p){
        return em.persistAndFlush(p)
                .replaceWith(Response.ok(p).status(Response.Status.CREATED)::build);
    }

    @Transactional
    public Uni<Response> deleteUser(User p){
        em.delete(p);
        // em.remove(em.contains(p) ? p : em.merge(p));
        return Uni.createFrom().item(Response.ok().status(Response.Status.ACCEPTED)::build);
    }

    @Transactional
    public Uni<List<User>> listUser(){
        return em.listAll();
    }

    @Transactional
    public Uni<User> findUser(Long Id){
        return em.findById(Id);
    }
    
    @Transactional
    public Uni<Response> updateUser(User p){
        em.persist(p);
        // em.merge(p);
        return Uni.createFrom().item(Response.ok(p).status(Response.Status.ACCEPTED)::build);
    }

    @Transactional
    public Uni<Set<Product>> getWishList(Long id){
        User user = (User) em.findById(id);
        return Uni.createFrom().item(user.getProducts());
    } 

    @Transactional
    public Uni<Boolean> setProductToWishList(Long userId, Long productId){
        Product product = (Product) productRepository.findById(productId);
        User user = (User) em.findById(userId);
        return Uni.createFrom().item(user.getProducts().add(product));
        
    }
}
