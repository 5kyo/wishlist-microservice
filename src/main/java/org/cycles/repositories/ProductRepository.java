package org.cycles.repositories;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import org.cycles.entites.Product;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product>{

}
