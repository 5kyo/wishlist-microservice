package org.cycles.resources;


// import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
// import io.vertx.core.json.JsonArray;
// import io.vertx.ext.web.client.WebClientOptions;
// import io.vertx.mutiny.core.Vertx;
// import io.vertx.mutiny.ext.web.client.WebClient;
// import lombok.extern.slf4j.Slf4j;
import org.cycles.entites.User;
import org.cycles.services.UserService;
import org.cycles.entites.Product;

// import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

// import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

// import java.util.ArrayList;
import java.util.Set;

// @Slf4j
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class UserResource {

    @Inject
    EntityManager em;

    @Inject
    UserService pr;

//  @Inject
//  Vertx vertx;

//  private WebClient webClient;

//     @PostConstruct
//     void initialize() {
//         this.webClient = WebClient.create(vertx,
//                 new WebClientOptions().setDefaultHost("localhost")
//                         .setDefaultPort(8081).setSsl(false).setTrustAll(true));
//     }

    @GET
    public Uni<List<User>> list() {
        return pr.listUser();
    }

    @GET
    @Path("/{Id}")
    public Uni<User> getById(@PathParam("Id") Long Id) {
        return pr.findUser(Id);
    }

    @GET
    @Path("/{Id}/products")
    public Uni<Set<Product>> getByIdProduct(@PathParam("Id") Long Id) {
        return pr.getWishList(Id);
//        return Uni.combine().all().unis(getUserReactive(Id),getAllProducts())
//                 .combinedWith((v1,v2) -> {
//                     v1.getProducts().forEach(product -> {
//                        v2.forEach(p -> {
//                            if(product.id.equals(p.id)){
//                                product.setName(p.getName());
//                                product.setDescription(p.getDescription());
// ;                           }
//                        });
//                     });
//                     return v1;
//                 });
    }

    @POST
    @Path("/{Id}/products")
    public Uni<Boolean> addProductToWishList(@PathParam("Id") Long Id, Long productId) {
        return pr.setProductToWishList(Id, productId);

    }

    @POST
    public Uni<Response> add(User c) {
        //c.getProducts().forEach(p-> p.setUser(c));
        pr.createdUser(c);
        return Uni.createFrom().item(Response.ok().status(Response.Status.ACCEPTED)::build);
    }

    @DELETE
    @Path("/{Id}")
    public Uni<Response> delete(@PathParam("Id") Long Id) {
        User user = em.find(User.class, Id);
        pr.deleteUser(user);
        return Uni.createFrom().item(Response.ok().status(Response.Status.ACCEPTED)::build);
    }

    @PUT
    public Uni<Response> update(User p) {
        User user = em.find(User.class, p.getId());
        user.setUsername(p.getUsername());
        user.setPassword(p.getPassword());
        user.setSurname(p.getSurname());
        user.setPhone(p.getPhone());
        user.setEmail(p.getEmail());
        user.setProducts(p.getProducts());

        return pr.updateUser(user);
    }


    // private Uni<User> getUserReactive(Long Id){
    //     User user = pr.findUser(Id);
    //     Uni<User> item = Uni.createFrom().item(user);
    //     return item;

// private Uni<List<Product>> getAllProducts(){
//     return webClient.get(8081, "localhost", "/product").send()
//             .onFailure().invoke(res -> log.error("Error recuperando productos ", res))
//             .onItem().transform(res -> {
//                 List<Product> lista = new ArrayList<>();
//                 JsonArray objects = res.bodyAsJsonArray();
//                 objects.forEach(p -> {
//                     log.info("See Objects: " + objects);
//                     ObjectMapper objectMapper = new ObjectMapper();
//                     // Pass JSON string and the POJO class
//                     Product product = null;
//                     try {
//                         product = objectMapper.readValue(p.toString(), Product.class);
//                     } catch (JsonProcessingException e) {
//                         e.printStackTrace();
//                     }
//                     lista.add(product);
//                 });
//                 return lista;
//             });
//     }

}
