package org.cycles.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cycles.dto.ProductDto;
import org.cycles.entites.Product;
import org.cycles.services.ProductService;
import org.jboss.resteasy.reactive.RestPath;

import io.smallrye.mutiny.Uni;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    @Inject 
    ProductService productService;

    @GET
    @PermitAll
    public Uni<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GET
    @Path("{id}")
    @PermitAll
    public Uni<Product> getSingleProduct(@RestPath Long id){
        return productService.getSingleProduct(id);
    }

    @POST
    // @RolesAllowed({"admin"})
    public Uni<Response> createProduct(ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @PATCH
    @Path("{id}")
    // @RolesAllowed({"admin"})
    public Uni<Response> updateProductName(@RestPath Long id, ProductDto productDto) {
        return productService.updateProductName(id, productDto);
    }

    @PATCH
    @Path("productStock/{id}")
    // @RolesAllowed({"admin"})
    public Uni<Response> addCountToProductStock(@RestPath Long id, ProductDto productDto) {
        return productService.addCountToProductStock(id, productDto);
    }

    @PATCH
    @Path("productPrice/{id}")
    // @RolesAllowed({"admin"})
    public Uni<Response> updatePriceOfProduct(@RestPath Long id, ProductDto productDto) {
        return productService.updatePriceOfProduct(id, productDto);
    }

    @DELETE
    @Path("{id}")
    // @RolesAllowed({"admin"})
    public Uni<Response> deleteProduct(@RestPath Long id) {
        return productService.deleteProduct(id);
    }
}
