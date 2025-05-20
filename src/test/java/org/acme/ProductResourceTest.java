package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ProductResourceTest {

    @Test
    public void testRegister() {
        String register = """
        {
          "username": "test",
          "password": "test"
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(register)
                .when().post("/auth/register")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLogin() {
        String registerPayload = """
    {
      "username": "testUser",
      "password": "testPassword"
    }
    """;

        given()
                .contentType(ContentType.JSON)
                .body(registerPayload)
                .log().all()
                .when()
                .post("/auth/register")
                .then()
                .log().all()
                .statusCode(anyOf(is(200), is(201)));

        String loginPayload = """
    {
      "username": "testUser",
      "password": "testPassword"
    }
    """;

        given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .log().all()
                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", notNullValue());

    }

    @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    public void testGetAllProducts() {
        given()
                .when().get("/products")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    public void testCreateProduct() {
        String newProductJson = """
        {
          "name": "Test Product",
          "description": "A test item",
          "price": 50000
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(newProductJson)
                .when().post("/products/insertOrUpdateProduct")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Test Product"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    public void testEdit() {
        String ediProductJson = """
        {
          "id" : 2,
          "name": "Test Product Updated",
          "description": "A test item updated",
          "price": 50001
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(ediProductJson)
                .when().post("/products/insertOrUpdateProduct")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("name", equalTo("Test Product Updated"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"user"})
    public void testDeleteNonExistentProduct() {
        String deletedItem = """
        {
          "id" : 4
        }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(deletedItem)
                .when().post("/products/deleteProduct")
                .then()
                .statusCode(200);
    }
}