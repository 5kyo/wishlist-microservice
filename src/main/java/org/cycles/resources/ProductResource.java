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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cycles.dto.ProductDto;
import org.cycles.entites.Product;
import org.cycles.services.ProductService;

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
    public Uni<Product> getSingleProduct(@PathParam("id") Long id){
        return productService.getSingleProduct(id);
    }

    @POST
    //@RolesAllowed({"admin"})
    public Uni<Response> createProduct(ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @PATCH
    @Consumes("text/plain")
    @Path("{id}")
    //@RolesAllowed({"admin"})
    public Uni<Response> updateProductName(@PathParam("id") Long id, String productName) {
        return productService.updateProductName(id, productName);
    }

    @PATCH
    @Consumes("text/plain")
    @Path("productStock/{id}")
    //@RolesAllowed({"admin"})
    public Uni<Response> addCountToProductStock(@PathParam("id") Long id, Integer productStock) {
        return productService.addCountToProductStock(id, productStock);
    }

    @PATCH
    @Consumes("text/plain")
    @Path("productPrice/{id}")
    //@RolesAllowed({"admin"})
    public Uni<Response> updatePriceOfProduct(@PathParam("id") Long id, double productPrice) {
        return productService.updatePriceOfProduct(id, productPrice);
    }

    @DELETE
    @Path("{id}")
    // @RolesAllowed({"admin"})
    public Uni<Response> deleteProduct(@PathParam("id") Long id) {
        return productService.deleteProduct(id);
    }
}
