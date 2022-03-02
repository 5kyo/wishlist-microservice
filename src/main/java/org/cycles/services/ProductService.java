package org.cycles.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.cycles.dto.ProductDto;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import org.cycles.entites.Product;
import org.cycles.repositories.ProductRepository;

import java.util.List;

@ApplicationScoped
public class ProductService {
    
    @Inject
    ProductRepository productRepository;

    @Transactional
    public Uni<List<Product>> getAllProducts() {
        return productRepository.listAll(Sort.by("productName"));
    }

    @Transactional
    public Uni<Product> getSingleProduct(Long id){
        return productRepository.findById(id);
    }

    @Transactional
    public Uni<Response> createProduct(ProductDto productDto) {
        if (productDto == null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductStock(productDto.getProductStock());
        
        return productRepository.persistAndFlush(product)
                    .replaceWith(Response.ok(product).status(Response.Status.CREATED)::build);
    }

    @Transactional
    public Uni<Response> deleteProduct(Long id) {
        return productRepository.deleteById(id)
        .call(() -> productRepository.flush()).replaceWith(Response.ok().status(Response.Status.ACCEPTED)::build);
            // .map(deleted -> deleted
            //             ? Response.ok().status(Response.Status.NO_CONTENT).build()
            //             : Response.ok().status(Response.Status.NOT_FOUND).build());
        // return Panache.withTransaction(() -> Product.deleteById(id))
        //         .map(deleted -> deleted
        //                 ? Response.ok().status(NO_CONTENT).build()
        //                 : Response.ok().status(NOT_FOUND).build());
    }

    public Uni<Response> updateProductName(Long id, Product product) {
        if (product == null) {
            throw new WebApplicationException("Product name was not set on request.", 422);
        }

        return productRepository.findById(id)
                                .onItem()
                                .ifNotNull()
                                .invoke(entity -> entity.setProductName(product.getProductName()))
                                .call(() -> productRepository.flush())
                                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                                .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);
    }
    public Uni<Response> addCountToProductStock(Long id, Product product){
        return productRepository.findById(id)
                                .onItem()
                                .ifNotNull()
                                .invoke(entity -> entity.addCountToStock(product.getProductStock()))
                                .call(() -> productRepository.flush())
                                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                                .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);

    }
        // return Panache
        //         .withTransaction(() -> Product.<Product> findById(id)
        //             .onItem().ifNotNull().invoke(entity -> entity.setProductName(product.getProductName()))
        //         )
        //         .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
        //         .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
}
