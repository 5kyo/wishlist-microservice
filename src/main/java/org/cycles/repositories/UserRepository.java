package org.cycles.repositories;

import org.cycles.entites.Product;
import org.cycles.entites.User;

import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class UserRepository {
    
    @Inject
    UserPanache em;

    @Inject
    ProductRepository productRepository;

    @Transactional
    public Uni<Response> createdUser(User p){
        em.persist(p);
        return Uni.createFrom().item(Response.ok(p).status(Response.Status.CREATED)::build);
    }

    @Transactional
    public Uni<Response> deleteUser(User p){
        em.delete(p);
        // em.remove(em.contains(p) ? p : em.merge(p));
        return Uni.createFrom().item(Response.ok().status(Response.Status.ACCEPTED)::build);
    }

    // @Transactional
    // public Uni<List<User>> listUser(){
    //     List<User> users = em.createQuery("select p from User p").getResultList();
    //     return Uni.createFrom().item(users);
    // }

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
        User user = em.findById(id).
        return Uni.createFrom().item(em.findById(id).getProducts());
    } 

    @Transactional
    public Uni<Boolean> setProductToWishList(Long userId, Long productId){
        Product product = em.find(Product.class, productId);
        return Uni.createFrom().item(em.find(User.class, userId).getProducts().add(product));
        
    }
}
