package org.cycles.repositories;

import javax.enterprise.context.ApplicationScoped;

import org.cycles.entites.User;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class UserPanache implements PanacheRepository<User> {
    
}
