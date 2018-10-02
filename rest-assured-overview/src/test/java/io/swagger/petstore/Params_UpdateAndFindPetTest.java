package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.swagger.petstore.base.BaseTest;
import org.testng.annotations.Test;

public class Params_UpdateAndFindPetTest extends BaseTest {

    @Test
    public void testFindPetByStatus() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .queryParam("status", "pending")
                .when()
                .get("https://petstore.swagger.io/v2/findByStatus")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();
    }
}
