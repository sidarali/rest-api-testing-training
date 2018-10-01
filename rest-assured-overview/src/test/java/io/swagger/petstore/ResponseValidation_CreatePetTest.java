package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;

public class ResponseValidation_CreatePetTest {

    private int petId = 91;

    private String petJson = "{" +
            "\"id\":" + petId + "," +
            "\"category\":{" +
            "\"id\":1," +
            "\"name\":\"auto_test_sample_category\"" +
            "}," +
            "\"name\":\"auto_test_sample_pet\"," +
            "\"photoUrls\":[" +
            "\"https://auto-test-sample-photo\"" +
            "]," +
            "\"tags\":[" +
            "{" +
            "\"id\":1," +
            "\"name\":\"auto_test_sample_tag\"" +
            "}" +
            "]," +
            "\"status\":\"available\"" +
            "}";

    @Test
    public void testCreatePet() {
        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .log().everything()
                .post("https://petstore.swagger.io/v2/pet")
                .then()
                .log().everything()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("Date", notNullValue())
                .body("id", equalTo(petId),
                        "name", equalTo("auto_test_sample_pet"),
                        "category.id", equalTo(1),
                        "category.name", equalTo("auto_test_sample_category"),
                        "photoUrls", hasSize(1),
                        "photoUrls", hasItem("https://auto-test-sample-photo"),
                        "tags.id", hasSize(1),
                        "tags.name", hasItem("auto_test_sample_tag"),
                        "status", equalTo("available"));
    }

    @Test
    public void testDeletePet() {
        String responseBody = RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .when()
                .log().everything()
                .delete("https://petstore.swagger.io/v2/pet/" + petId)
                .then()
                .log().everything()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();

        assertThat(responseBody).isEqualTo("");
    }

    @Test
    public void testFindPetByStatus() {

        RestAssured.given()
                .accept(ContentType.JSON)
                .header("api_key", "1234567890")
                .queryParam("status", "pending")
                .when()
                .log().everything()
                .get("https://petstore.swagger.io/v2/findByStatus")
                .then()
                .log().everything()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().asString();
    }
}
