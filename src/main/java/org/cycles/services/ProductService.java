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
        if (productDto == null || productDto.getProductId() != null) {
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
        .call(() -> productRepository.flush()).replaceWith(Response.ok().status(200)::build);
    }

    public Uni<Response> updateProductName(Long id, ProductDto productDto) {
        if (productDto.getProductName() == null) {
            throw new WebApplicationException("Product name was not set on request.", 422);
        }

        return productRepository.findById(id)
                                .onItem()
                                .ifNotNull()
                                .invoke(entity -> entity.setProductName(productDto.getProductName()))
                                .call(() -> productRepository.flush())
                                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                                .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);
    }
    public Uni<Response> addCountToProductStock(Long id, ProductDto productDto){
        if (productDto.getProductStock() == null || productDto.getProductStock() < 0) {
            throw new WebApplicationException("Invalid amount to increase on request", 422);
        }
        return productRepository.findById(id)
                                .onItem()
                                .ifNotNull()
                                .invoke(entity -> entity.addCountToStock(productDto.getProductStock()))
                                .call(() -> productRepository.flush())
                                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                                .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);

    }

    public Uni<Response> updatePriceOfProduct(Long id, ProductDto productDto){
        if (productDto.getProductPrice() < 0) {
            throw new WebApplicationException("Invalid price on request", 422);
        }
        return productRepository.findById(id)
                                .onItem()
                                .ifNotNull()
                                .invoke(entity -> entity.setProductPrice(productDto.getProductPrice()))
                                .call(() -> productRepository.flush())
                                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                                .onItem().ifNull().continueWith(Response.ok().status(Response.Status.NOT_FOUND)::build);

    }
}
