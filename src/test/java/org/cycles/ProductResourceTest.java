package org.cycles;

import org.cycles.entites.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductResourceTest {

    @Test
    @Order(1)
    @TestSecurity(authorizationEnabled = false)
    public void testCreateProduct(){
        Product product = new Product();
        product.setProductName("Calculadora");
        product.setProductPrice(100);
        product.setProductStock(2);
        given() 
            .contentType("application/json")
            .body(product)
            .post("/products")
            .then()
            .statusCode(201);
    }

    @Test
    @Order(2)
    public void testListAllProducts(){
        given() 
            .when()
            .get("/products")
            .then()
            .statusCode(200);
    }

    @Test
    @Order(3)
    public void testFindProductById(){
        given() 
            .when()
            .get("/products/{id}")
            .then()
            .statusCode(200);
    }

    @Test
    @AfterAll
    @TestSecurity(authorizationEnabled = false)
    public void testDeleteById(){
        given() 
            .when()
            .delete("/products/{id}")
            .then()
            .statusCode(200);
    }
}
